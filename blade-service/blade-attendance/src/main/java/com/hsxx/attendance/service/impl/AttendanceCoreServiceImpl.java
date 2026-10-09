package com.hsxx.attendance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hsxx.attendance.constant.AttendanceConstants;
import com.hsxx.attendance.mapper.TbAtUserScheduleMapper;
import com.hsxx.attendance.mapper.TbAtVacationApplyMapper;
import com.hsxx.attendance.mapper.TbAtWorkTimeConfMapper;
import com.hsxx.attendance.pojo.dto.AccessEventDTO;
import com.hsxx.attendance.pojo.entity.TbAtUserClockRec;
import com.hsxx.attendance.pojo.entity.TbAtUserSchedule;
import com.hsxx.attendance.pojo.entity.TbAtUrkAccessRec;
import com.hsxx.attendance.pojo.entity.TbAtVacationApply;
import com.hsxx.attendance.pojo.entity.TbAtWorkPlanActual;
import com.hsxx.attendance.pojo.entity.TbAtWorkTimeConf;
import com.hsxx.personnel.pojo.vo.EmployeeBasicInfoVO;
import com.hsxx.attendance.service.AttendanceCoreService;
import com.hsxx.attendance.service.TbAtUrkAccessRecService;
import com.hsxx.attendance.service.TbAtUserClockRecService;
import com.hsxx.attendance.service.TbAtWorkPlanActualService;
import com.hsxx.personnel.feign.IEmployeeClient;
import lombok.AllArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * 考勤核心服务实现
 *
 * <p>数据链路：门禁出入采集(TB_AT_URK_ACCESS_REC，唯一数据源) → 按班次窗口聚合打卡(TB_AT_USER_CLOCK_REC)
 * → 结合排班/作息/假期判定考勤状态(TB_AT_WORK_PLAN_ACTUAL)</p>
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class AttendanceCoreServiceImpl implements AttendanceCoreService {

	private final TbAtUrkAccessRecService accessRecService;
	private final TbAtUserClockRecService clockRecService;
	private final TbAtWorkPlanActualService planActualService;
	private final TbAtUserScheduleMapper userScheduleMapper;
	private final TbAtWorkTimeConfMapper workTimeConfMapper;
	private final TbAtVacationApplyMapper vacationApplyMapper;
	private final IEmployeeClient employeeClient;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TbAtUrkAccessRec acceptAccessEvent(AccessEventDTO dto) {
		if (dto.getUserId() == null || dto.getAccessTime() == null) {
			throw new ServiceException("刷脸事件缺少人员或时间参数");
		}
		// 幂等：同人同机同时刻的事件视为重复推送
		TbAtUrkAccessRec existed = accessRecService.lambdaQuery()
			.eq(TbAtUrkAccessRec::getUserId, dto.getUserId())
			.eq(TbAtUrkAccessRec::getAccessTime, dto.getAccessTime())
			.eq(TbAtUrkAccessRec::getUrkCode, dto.getUrkCode())
			.last("LIMIT 1")
			.one();
		if (existed != null) {
			return existed;
		}
		TbAtUrkAccessRec rec = new TbAtUrkAccessRec();
		rec.setUserId(dto.getUserId());
		rec.setUrkCode(dto.getUrkCode());
		rec.setAccessTime(dto.getAccessTime());
		rec.setAccessType(dto.getAccessType());
		rec.setSyncFlag("0");
		accessRecService.save(rec);

		// 实时重算：打卡当日，以及前一日（夜班跨天，次日清晨的下班卡属于前一日班次）
		LocalDate punchDate = toLocalDate(dto.getAccessTime());
		recomputeUserDate(dto.getUserId(), punchDate);
		recomputeUserDate(dto.getUserId(), punchDate.minusDays(1));
		return rec;
	}

	@Override
	public void recomputeUserDate(Long userId, LocalDate date) {
		TbAtUserSchedule schedule = userScheduleMapper.selectOne(
			new LambdaQueryWrapper<TbAtUserSchedule>()
				.eq(TbAtUserSchedule::getUserId, userId)
				.eq(TbAtUserSchedule::getCalDate, toDate(date)));
		if (schedule == null) {
			return;
		}
		TbAtWorkTimeConf conf = loadWorkTimeConf(schedule.getClassId());
		boolean acrossDay = conf != null && AttendanceConstants.ACROSS_DAY_YES.equals(conf.getAcrossDay());

		// 打卡窗口：白班当日全天；夜班当日+次日（考勤划归当日）
		LocalDateTime windowStart = date.atStartOfDay();
		LocalDateTime windowEnd = (acrossDay ? date.plusDays(1) : date).atTime(LocalTime.of(23, 59, 59));
		List<TbAtUrkAccessRec> punches = accessRecService.lambdaQuery()
			.eq(TbAtUrkAccessRec::getUserId, userId)
			.between(TbAtUrkAccessRec::getAccessTime, toDate(windowStart), toDate(windowEnd))
			.orderByAsc(TbAtUrkAccessRec::getAccessTime)
			.list();

		upsertClockRec(userId, date, punches);
		Integer statusId = evaluateStatus(userId, date, schedule, conf, punches);
		upsertPlanActual(userId, date, statusId);

		// 标记窗口内记录已聚合
		accessRecService.lambdaUpdate()
			.set(TbAtUrkAccessRec::getSyncFlag, "1")
			.eq(TbAtUrkAccessRec::getUserId, userId)
			.between(TbAtUrkAccessRec::getAccessTime, toDate(windowStart), toDate(windowEnd))
			.update();
	}

	@Override
	public int ensureDayRecords(LocalDate date) {
		R<List<EmployeeBasicInfoVO>> result = employeeClient.list();
		if (!result.isSuccess()) {
			throw new ServiceException("获取员工列表失败：" + result.getMsg());
		}
		List<EmployeeBasicInfoVO> employees = result.getData();
		if (employees == null || employees.isEmpty()) {
			return 0;
		}
		int count = 0;
		for (EmployeeBasicInfoVO employee : employees) {
			if (userScheduleMapper.selectCount(new LambdaQueryWrapper<TbAtUserSchedule>()
				.eq(TbAtUserSchedule::getUserId, employee.getUserId())
				.eq(TbAtUserSchedule::getCalDate, toDate(date))) == 0) {
				userScheduleMapper.insert(buildDefaultSchedule(employee, date));
			}
			recomputeUserDate(employee.getUserId(), date);
			count++;
		}
		return count;
	}

	/**
	 * 状态判定优先级：假期（审批中/已通过）> 应休息（有卡加班/无卡休息）> 应出勤（旷工/迟到/早退/出勤）
	 */
	private Integer evaluateStatus(Long userId, LocalDate date, TbAtUserSchedule schedule,
								   TbAtWorkTimeConf conf, List<TbAtUrkAccessRec> punches) {
		// 假期覆盖当天：审批中→假期审批中；已通过→具体假期名称对应状态
		TbAtVacationApply vacation = vacationApplyMapper.selectOne(
			new LambdaQueryWrapper<TbAtVacationApply>()
				.eq(TbAtVacationApply::getUserId, userId)
				.in(TbAtVacationApply::getApproveStatus,
					AttendanceConstants.APPROVE_PENDING, AttendanceConstants.APPROVE_PASSED)
				.le(TbAtVacationApply::getStartDate, toDate(date.atStartOfDay()))
				.ge(TbAtVacationApply::getEndDate, toDate(date.atTime(LocalTime.of(23, 59, 59))))
				.last("LIMIT 1"));
		if (vacation != null) {
			if (AttendanceConstants.APPROVE_PENDING.equals(vacation.getApproveStatus())) {
				return AttendanceConstants.STATUS_VAC_APPROVING;
			}
			return AttendanceConstants.VAC_STATUS_MAP.getOrDefault(vacation.getVacId(),
				AttendanceConstants.STATUS_VAC_FIRST);
		}

		boolean shouldWork = AttendanceConstants.ATTEND_TYPE_WORK.equals(schedule.getAttendType());
		if (!shouldWork) {
			// 休息日：中午前的打卡视为前一夜夜班的下班卡，不计入当日
			boolean hasOvertimePunch = punches.stream()
				.anyMatch(p -> toLocalDateTime(p.getAccessTime()).toLocalTime().isAfter(LocalTime.NOON));
			return hasOvertimePunch ? AttendanceConstants.STATUS_OVERTIME : AttendanceConstants.STATUS_REST;
		}
		if (punches.isEmpty()) {
			return AttendanceConstants.STATUS_ABSENT;
		}
		if (conf == null) {
			return AttendanceConstants.STATUS_ATTEND;
		}
		LocalDateTime inTime = toLocalDateTime(punches.get(0).getAccessTime());
		LocalDateTime outTime = toLocalDateTime(punches.get(punches.size() - 1).getAccessTime());
		LocalDateTime stdStart = date.atTime(LocalTime.parse(conf.getStartTime()));
		LocalDateTime stdEnd = (AttendanceConstants.ACROSS_DAY_YES.equals(conf.getAcrossDay())
			? date.plusDays(1) : date).atTime(LocalTime.parse(conf.getEndTime()));
		boolean late = inTime.isAfter(stdStart);
		boolean earlyLeave = outTime.isBefore(stdEnd);
		if (late && earlyLeave) {
			return AttendanceConstants.STATUS_LATE_EARLY;
		}
		if (late) {
			return AttendanceConstants.STATUS_LATE;
		}
		if (earlyLeave) {
			return AttendanceConstants.STATUS_EARLY;
		}
		return AttendanceConstants.STATUS_ATTEND;
	}

	private TbAtWorkTimeConf loadWorkTimeConf(Integer classId) {
		if (AttendanceConstants.CLASS_REST.equals(classId)) {
			return null;
		}
		return workTimeConfMapper.selectOne(new LambdaQueryWrapper<TbAtWorkTimeConf>()
			.eq(TbAtWorkTimeConf::getClassId, classId)
			.eq(TbAtWorkTimeConf::getIsValid, "1")
			.last("LIMIT 1"));
	}

	private void upsertClockRec(Long userId, LocalDate date, List<TbAtUrkAccessRec> punches) {
		if (punches.isEmpty()) {
			return;
		}
		TbAtUserClockRec rec = clockRecService.lambdaQuery()
			.eq(TbAtUserClockRec::getUserId, userId)
			.eq(TbAtUserClockRec::getWorkDate, toDate(date))
			.last("LIMIT 1")
			.one();
		if (rec == null) {
			rec = new TbAtUserClockRec();
			rec.setUserId(userId);
			rec.setWorkDate(toDate(date));
		}
		TbAtUrkAccessRec first = punches.get(0);
		TbAtUrkAccessRec last = punches.get(punches.size() - 1);
		rec.setInTime(first.getAccessTime());
		rec.setInUrkCode(first.getUrkCode());
		rec.setOutTime(last.getAccessTime());
		rec.setOutUrkCode(last.getUrkCode());
		clockRecService.saveOrUpdate(rec);
	}

	private void upsertPlanActual(Long userId, LocalDate date, Integer statusId) {
		TbAtWorkPlanActual actual = planActualService.lambdaQuery()
			.eq(TbAtWorkPlanActual::getUserId, userId)
			.eq(TbAtWorkPlanActual::getCalDate, toDate(date))
			.last("LIMIT 1")
			.one();
		if (actual == null) {
			actual = new TbAtWorkPlanActual();
			actual.setUserId(userId);
			actual.setCalDate(toDate(date));
		}
		actual.setStatusId(statusId);
		planActualService.saveOrUpdate(actual);
	}

	private TbAtUserSchedule buildDefaultSchedule(EmployeeBasicInfoVO employee, LocalDate date) {
		TbAtUserSchedule schedule = new TbAtUserSchedule();
		schedule.setUserId(employee.getUserId());
		schedule.setOrganId(employee.getOrganId());
		schedule.setCalDate(toDate(date));
		boolean weekend = date.getDayOfWeek().getValue() >= 6;
		schedule.setClassId(weekend ? AttendanceConstants.CLASS_REST : AttendanceConstants.CLASS_REGULAR_DAY);
		schedule.setAttendType(weekend
			? AttendanceConstants.ATTEND_TYPE_REST : AttendanceConstants.ATTEND_TYPE_WORK);
		schedule.setAdjustFlag("0");
		return schedule;
	}

	private static Date toDate(LocalDate date) {
		return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	private static Date toDate(LocalDateTime time) {
		return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
	}

	private static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private static LocalDateTime toLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}

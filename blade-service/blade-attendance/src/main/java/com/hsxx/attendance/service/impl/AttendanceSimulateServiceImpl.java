package com.hsxx.attendance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hsxx.attendance.constant.AttendanceConstants;
import com.hsxx.attendance.mapper.TbAtUserScheduleMapper;
import com.hsxx.attendance.mapper.TbAtVacationApplyMapper;
import com.hsxx.attendance.pojo.dto.AccessEventDTO;
import com.hsxx.attendance.pojo.entity.TbAtUserSchedule;
import com.hsxx.attendance.pojo.entity.TbAtVacationApply;
import com.hsxx.attendance.pojo.entity.TbAtWorkTimeConf;
import com.hsxx.attendance.mapper.TbAtWorkTimeConfMapper;
import com.hsxx.personnel.pojo.vo.EmployeeBasicInfoVO;
import com.hsxx.attendance.service.AttendanceCoreService;
import com.hsxx.attendance.service.AttendanceSimulateService;
import com.hsxx.personnel.feign.IEmployeeClient;
import lombok.AllArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 考勤模拟数据服务实现
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class AttendanceSimulateServiceImpl implements AttendanceSimulateService {

	private final TbAtUserScheduleMapper userScheduleMapper;
	private final TbAtWorkTimeConfMapper workTimeConfMapper;
	private final TbAtVacationApplyMapper vacationApplyMapper;
	private final AttendanceCoreService coreService;
	private final IEmployeeClient employeeClient;

	private final Random random = new Random();

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String seed(int days) {
		R<List<EmployeeBasicInfoVO>> result = employeeClient.list();
		if (!result.isSuccess()) {
			throw new ServiceException("获取员工列表失败：" + result.getMsg());
		}
		List<EmployeeBasicInfoVO> employees = result.getData();
		if (employees == null || employees.isEmpty()) {
			return "员工列表为空，请先在人员模块准备员工数据";
		}
		LocalDate today = LocalDate.now();

		int scheduleCount = 0;
		for (int i = 0; i < days; i++) {
			LocalDate date = today.plusDays(i);
			boolean weekend = date.getDayOfWeek().getValue() >= 6;
			for (EmployeeBasicInfoVO employee : employees) {
				Integer classId = pickClass(employee.getUserId(), weekend);
				upsertSchedule(employee, date, classId);
				scheduleCount++;
			}
		}

		// 假期模拟：约 5% 员工请 1 天假，一半审批中、一半已通过
		int vacationCount = 0;
		int limit = Math.max(1, employees.size() / 20);
		for (EmployeeBasicInfoVO employee : employees) {
			if (vacationCount >= limit) {
				break;
			}
			if (random.nextInt(100) >= 8) {
				continue;
			}
			LocalDate vacDate = today.plusDays(1 + random.nextInt(Math.max(1, days - 1)));
			TbAtVacationApply apply = new TbAtVacationApply();
			apply.setApplyNo("QJ" + vacDate.toString().replace("-", "") + String.format("%03d", vacationCount + 1));
			apply.setUserId(employee.getUserId());
			apply.setOrganId(employee.getOrganId());
			apply.setVacId(1 + random.nextInt(5));
			apply.setStartDate(toDate(vacDate.atStartOfDay()));
			apply.setEndDate(toDate(vacDate.atTime(LocalTime.of(23, 59, 59))));
			apply.setLeaveDays(BigDecimal.ONE);
			apply.setApproveStatus(random.nextBoolean()
				? AttendanceConstants.APPROVE_PENDING : AttendanceConstants.APPROVE_PASSED);
			vacationApplyMapper.insert(apply);
			vacationCount++;
		}

		int recordCount = 0;
		for (int i = 0; i < days; i++) {
			recordCount += coreService.ensureDayRecords(today.plusDays(i));
		}
		return String.format("播种完成：员工 %d 人，排班 %d 条，假期申请 %d 条，实绩 %d 条",
			employees.size(), scheduleCount, vacationCount, recordCount);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int simulateDay(LocalDate date) {
		List<TbAtUserSchedule> schedules = userScheduleMapper.selectList(
			new LambdaQueryWrapper<TbAtUserSchedule>()
				.eq(TbAtUserSchedule::getCalDate, toDate(date))
				.eq(TbAtUserSchedule::getAttendType, AttendanceConstants.ATTEND_TYPE_WORK));
		int eventCount = 0;
		for (TbAtUserSchedule schedule : schedules) {
			TbAtWorkTimeConf conf = workTimeConfMapper.selectOne(new LambdaQueryWrapper<TbAtWorkTimeConf>()
				.eq(TbAtWorkTimeConf::getClassId, schedule.getClassId())
				.eq(TbAtWorkTimeConf::getIsValid, "1")
				.last("LIMIT 1"));
			if (conf == null) {
				continue;
			}
			boolean acrossDay = AttendanceConstants.ACROSS_DAY_YES.equals(conf.getAcrossDay());
			LocalDateTime stdStart = date.atTime(LocalTime.parse(conf.getStartTime()));
			LocalDateTime stdEnd = (acrossDay ? date.plusDays(1) : date).atTime(LocalTime.parse(conf.getEndTime()));

			// 少量漏卡（旷工演示）
			if (random.nextInt(100) < 3) {
				continue;
			}
			LocalDateTime inTime = stdStart.plusMinutes(random.nextInt(56) - 15);
			LocalDateTime outTime = stdEnd.plusMinutes(random.nextInt(56) - 40);
			eventCount += push(schedule.getUserId(), inTime);
			// 中途随机 0~2 次进出（进出厂区）
			int midCount = random.nextInt(3);
			for (int m = 0; m < midCount; m++) {
				long span = java.time.Duration.between(inTime, outTime).toMinutes();
				if (span <= 10) {
					break;
				}
				eventCount += push(schedule.getUserId(), inTime.plusMinutes(10 + random.nextInt((int) span - 10)));
			}
			eventCount += push(schedule.getUserId(), outTime);
		}
		return eventCount;
	}

	private int push(Long userId, LocalDateTime time) {
		AccessEventDTO dto = new AccessEventDTO();
		dto.setUserId(userId);
		dto.setUrkCode(AttendanceConstants.SIM_URK_CODES[random.nextInt(AttendanceConstants.SIM_URK_CODES.length)]);
		dto.setAccessTime(toDate(time));
		dto.setAccessType(random.nextBoolean() ? "1" : "2");
		coreService.acceptAccessEvent(dto);
		return 1;
	}

	/**
	 * 按员工主键稳定分配班次：70% 常白班、10% 白班倒班、10% 12H夜、10% 夜班倒班
	 */
	private Integer pickClass(Long userId, boolean weekend) {
		int bucket = (int) (userId % 10);
		Integer classId;
		if (bucket <= 6) {
			classId = AttendanceConstants.CLASS_REGULAR_DAY;
		} else if (bucket == 7) {
			classId = AttendanceConstants.CLASS_DAY_SHIFT;
		} else if (bucket == 8) {
			classId = AttendanceConstants.CLASS_NIGHT_12H;
		} else {
			classId = AttendanceConstants.CLASS_NIGHT_SHIFT;
		}
		// 倒班（夜班）周末照常上班，常白/白倒周末休息
		if (weekend && (classId.equals(AttendanceConstants.CLASS_REGULAR_DAY)
			|| classId.equals(AttendanceConstants.CLASS_DAY_SHIFT))) {
			return AttendanceConstants.CLASS_REST;
		}
		return classId;
	}

	private void upsertSchedule(EmployeeBasicInfoVO employee, LocalDate date, Integer classId) {
		TbAtUserSchedule schedule = userScheduleMapper.selectOne(new LambdaQueryWrapper<TbAtUserSchedule>()
			.eq(TbAtUserSchedule::getUserId, employee.getUserId())
			.eq(TbAtUserSchedule::getCalDate, toDate(date))
			.last("LIMIT 1"));
		if (schedule == null) {
			schedule = new TbAtUserSchedule();
			schedule.setUserId(employee.getUserId());
			schedule.setOrganId(employee.getOrganId());
			schedule.setCalDate(toDate(date));
			schedule.setAdjustFlag("0");
		}
		schedule.setClassId(classId);
		schedule.setAttendType(classId.equals(AttendanceConstants.CLASS_REST)
			? AttendanceConstants.ATTEND_TYPE_REST : AttendanceConstants.ATTEND_TYPE_WORK);
		if (schedule.getId() == null) {
			userScheduleMapper.insert(schedule);
		} else {
			userScheduleMapper.updateById(schedule);
		}
	}

	private static Date toDate(LocalDateTime time) {
		return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
	}

	private static Date toDate(LocalDate date) {
		return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}

package com.hsxx.attendance.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hsxx.attendance.constant.AttendanceConstants;
import com.hsxx.attendance.mapper.TbAtClassCodeMapper;
import com.hsxx.attendance.mapper.TbAtUserScheduleMapper;
import com.hsxx.attendance.pojo.entity.TbAtClassCode;
import com.hsxx.attendance.pojo.entity.TbAtUserClockRec;
import com.hsxx.attendance.pojo.entity.TbAtUserSchedule;
import com.hsxx.attendance.pojo.entity.TbAtUrkStatusCode;
import com.hsxx.attendance.pojo.entity.TbAtWorkPlanActual;
import com.hsxx.attendance.pojo.vo.AttendanceRecordVO;
import com.hsxx.personnel.pojo.vo.EmployeeBasicInfoVO;
import com.hsxx.attendance.service.AttendanceCoreService;
import com.hsxx.attendance.service.TbAtUrkStatusCodeService;
import com.hsxx.attendance.service.TbAtUserClockRecService;
import com.hsxx.attendance.service.TbAtWorkPlanActualService;
import com.hsxx.personnel.feign.IEmployeeClient;
import lombok.AllArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 考勤实绩 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/attendance")
public class AttendanceRecordController {

	private final TbAtWorkPlanActualService planActualService;
	private final TbAtUserClockRecService clockRecService;
	private final TbAtUserScheduleMapper userScheduleMapper;
	private final TbAtClassCodeMapper classCodeMapper;
	private final TbAtUrkStatusCodeService statusCodeService;
	private final AttendanceCoreService coreService;
	private final IEmployeeClient employeeClient;

	/**
	 * 考勤实绩分页
	 */
	@GetMapping("/record/page")
	@io.swagger.v3.oas.annotations.Operation(summary = "考勤实绩分页",
		description = "按日期查询全员实绩，支持员工与状态分类（1 正常 2 异常 3 假期 4 审批中）筛选，含班次、上下班打卡与状态颜色")
	@io.swagger.v3.oas.annotations.tags.Tag(name = "考勤实绩", description = "考勤实绩查询与日结")
	public R<IPage<AttendanceRecordVO>> page(
		@io.swagger.v3.oas.annotations.Parameter(description = "考勤日期 yyyy-MM-dd（必填）") @RequestParam String date,
		@io.swagger.v3.oas.annotations.Parameter(description = "员工主键") @RequestParam(required = false) Long userId,
		@io.swagger.v3.oas.annotations.Parameter(description = "状态分类：1 正常 2 异常 3 假期 4 审批中") @RequestParam(required = false) String statusType,
		@io.swagger.v3.oas.annotations.Parameter(description = "分页参数") Query query) {
		LocalDate day = LocalDate.parse(date);
		Date dayDate = Date.from(day.atStartOfDay(ZoneId.systemDefault()).toInstant());

		Map<Integer, TbAtUrkStatusCode> statusMap = statusCodeService.list().stream()
			.collect(Collectors.toMap(TbAtUrkStatusCode::getStatusId, Function.identity()));
		if (statusType != null && !statusType.isEmpty()) {
			List<Integer> statusIds = statusMap.values().stream()
				.filter(s -> statusType.equals(s.getStatusType()))
				.map(TbAtUrkStatusCode::getStatusId)
				.collect(Collectors.toList());
			if (statusIds.isEmpty()) {
				return R.data(Condition.getPage(query));
			}
			// 分类筛选命中后再分页
			return R.data(pageWithStatusFilter(dayDate, userId, statusIds, query, statusMap));
		}
		IPage<TbAtWorkPlanActual> actualPage = planActualService.page(Condition.getPage(query),
			new LambdaQueryWrapper<TbAtWorkPlanActual>()
				.eq(TbAtWorkPlanActual::getCalDate, dayDate)
				.eq(userId != null, TbAtWorkPlanActual::getUserId, userId)
				.orderByAsc(TbAtWorkPlanActual::getUserId));
		IPage<AttendanceRecordVO> voPage = actualPage.convert(actual -> buildVO(actual, statusMap));
		fillEmployees(voPage.getRecords());
		return R.data(voPage);
	}

	/**
	 * 手动触发某日全员实绩生成（定时任务 00:00 的等价入口）
	 */
	@PostMapping("/record/generate")
	@io.swagger.v3.oas.annotations.Operation(summary = "生成某日全员实绩", description = "等价于每日 00:00 定时任务，支持补生成历史日期")
	public R<Integer> generate(
		@io.swagger.v3.oas.annotations.Parameter(description = "考勤日期 yyyy-MM-dd，缺省为今天") @RequestParam(required = false) String date) {
		LocalDate day = (date == null || date.isEmpty()) ? LocalDate.now() : LocalDate.parse(date);
		return R.data(coreService.ensureDayRecords(day));
	}

	private IPage<AttendanceRecordVO> pageWithStatusFilter(Date dayDate, Long userId, List<Integer> statusIds,
														   Query query, Map<Integer, TbAtUrkStatusCode> statusMap) {
		IPage<TbAtWorkPlanActual> actualPage = planActualService.page(Condition.getPage(query),
			new LambdaQueryWrapper<TbAtWorkPlanActual>()
				.eq(TbAtWorkPlanActual::getCalDate, dayDate)
				.eq(userId != null, TbAtWorkPlanActual::getUserId, userId)
				.in(TbAtWorkPlanActual::getStatusId, statusIds)
				.orderByAsc(TbAtWorkPlanActual::getUserId));
		IPage<AttendanceRecordVO> voPage = actualPage.convert(actual -> buildVO(actual, statusMap));
		fillEmployees(voPage.getRecords());
		return voPage;
	}

	private AttendanceRecordVO buildVO(TbAtWorkPlanActual actual, Map<Integer, TbAtUrkStatusCode> statusMap) {
		AttendanceRecordVO vo = new AttendanceRecordVO();
		vo.setUserId(actual.getUserId());
		vo.setCalDate(actual.getCalDate());
		vo.setApplyNo(actual.getApplyId());
		LocalDate day = actual.getCalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		TbAtUrkStatusCode status = statusMap.get(actual.getStatusId());
		if (status != null) {
			vo.setStatusId(status.getStatusId());
			vo.setStatusCode(status.getStatusCode());
			vo.setStatusName(status.getStatusName());
			vo.setStatusType(status.getStatusType());
			vo.setShowColor(status.getShowColor());
		}

		TbAtUserSchedule schedule = userScheduleMapper.selectOne(new LambdaQueryWrapper<TbAtUserSchedule>()
			.eq(TbAtUserSchedule::getUserId, actual.getUserId())
			.eq(TbAtUserSchedule::getCalDate, actual.getCalDate())
			.last("LIMIT 1"));
		if (schedule != null) {
			vo.setAttendType(schedule.getAttendType());
			TbAtClassCode classCode = classCodeMapper.selectById(schedule.getClassId());
			if (classCode != null) {
				vo.setClassId(classCode.getClassId());
				vo.setClassName(classCode.getClassName());
				vo.setClassSymbol(classCode.getClassSymbol());
			}
		}

		TbAtUserClockRec clock = clockRecService.lambdaQuery()
			.eq(TbAtUserClockRec::getUserId, actual.getUserId())
			.eq(TbAtUserClockRec::getWorkDate, actual.getCalDate())
			.last("LIMIT 1")
			.one();
		if (clock != null) {
			vo.setInTime(clock.getInTime());
			vo.setInUrkCode(clock.getInUrkCode());
			vo.setOutTime(clock.getOutTime());
			vo.setOutUrkCode(clock.getOutUrkCode());
		}

		return vo;
	}

	/**
	 * 整页批量填充员工工号姓名，避免逐行远程调用
	 */
	private void fillEmployees(List<AttendanceRecordVO> vos) {
		if (vos == null || vos.isEmpty()) {
			return;
		}
		R<List<EmployeeBasicInfoVO>> result = employeeClient.list();
		if (!result.isSuccess()) {
			throw new ServiceException("获取员工列表失败：" + result.getMsg());
		}
		Map<Long, EmployeeBasicInfoVO> employees = result.getData().stream()
			.collect(Collectors.toMap(EmployeeBasicInfoVO::getUserId, Function.identity()));
		for (AttendanceRecordVO vo : vos) {
			EmployeeBasicInfoVO employee = employees.get(vo.getUserId());
			if (employee != null) {
				vo.setUserCode(employee.getUserCode());
				vo.setUserName(employee.getUserName());
			}
		}
	}

}

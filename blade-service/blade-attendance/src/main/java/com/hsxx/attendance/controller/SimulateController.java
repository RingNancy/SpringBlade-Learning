package com.hsxx.attendance.controller;

import com.hsxx.attendance.service.AttendanceSimulateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 考勤模拟数据 控制器（开发阶段造数用，接入真实 ISC 后下线）
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/simulate")
@Tag(name = "考勤模拟数据", description = "排班播种与刷脸事件批量模拟")
public class SimulateController {

	private final AttendanceSimulateService simulateService;

	/**
	 * 播种排班与假期模拟数据
	 */
	@PostMapping("/seed")
	@Operation(summary = "播种排班与假期", description = "为全员按稳定规则生成 N 天排班（含倒班与夜班）、少量假期申请，并生成每日实绩")
	public R<String> seed(@Parameter(description = "播种天数，默认 7") @RequestParam(required = false, defaultValue = "7") Integer days) {
		return R.data(simulateService.seed(days));
	}

	/**
	 * 批量模拟某日刷脸事件
	 */
	@PostMapping("/day")
	@Operation(summary = "模拟某日刷脸事件", description = "按作息时间为当日应出勤员工造上下班卡（含随机迟到/早退/漏卡），夜班下班卡落在次日清晨")
	public R<Integer> day(@Parameter(description = "考勤日期 yyyy-MM-dd，缺省为今天") @RequestParam(required = false) String date) {
		LocalDate day = (date == null || date.isEmpty()) ? LocalDate.now() : LocalDate.parse(date);
		return R.data(simulateService.simulateDay(day));
	}

}

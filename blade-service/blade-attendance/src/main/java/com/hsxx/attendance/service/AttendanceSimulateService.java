package com.hsxx.attendance.service;

import java.time.LocalDate;

/**
 * 考勤模拟数据服务（开发阶段无真实 ISC 对接时的数据造填）
 *
 * @author Chill
 */
public interface AttendanceSimulateService {

	/**
	 * 播种排班与假期模拟数据
	 *
	 * <p>按员工主键稳定分配班次（70% 常白班、10% 白班倒班、10% 12H夜、10% 夜班倒班，常白与白倒周末休息），
	 * 并为少量员工生成假期申请（一半审批中、一半已通过），最后生成范围内每日全员实绩</p>
	 *
	 * @param days 从今天起播种的天数
	 * @return 播种结果摘要
	 */
	String seed(int days);

	/**
	 * 为某考勤日批量模拟门禁刷脸事件
	 *
	 * <p>按作息时间为当日应出勤员工造上下班卡（含随机迟到/早退/漏卡），夜班下班卡落在次日清晨，
	 * 全部经统一事件入口 {@link AttendanceCoreService#acceptAccessEvent} 进入</p>
	 *
	 * @param date 考勤日
	 * @return 生成的刷脸事件条数
	 */
	int simulateDay(LocalDate date);

}

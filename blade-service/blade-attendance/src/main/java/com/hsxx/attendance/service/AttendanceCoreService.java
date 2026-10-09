package com.hsxx.attendance.service;

import com.hsxx.attendance.pojo.dto.AccessEventDTO;
import com.hsxx.attendance.pojo.entity.TbAtUrkAccessRec;

import java.time.LocalDate;

/**
 * 考勤核心服务：门禁事件接入、打卡聚合判定、考勤实绩生成与更新
 *
 * @author Chill
 */
public interface AttendanceCoreService {

	/**
	 * 接入门禁刷脸事件（ISC 订阅推送的唯一入口）
	 *
	 * <p>幂等：同人同机同时间的事件重复推送只生效一次；落库后实时重算受影响考勤日的实绩</p>
	 *
	 * @param dto 刷脸事件
	 * @return 落库后的门禁采集记录
	 */
	TbAtUrkAccessRec acceptAccessEvent(AccessEventDTO dto);

	/**
	 * 重算某员工某考勤日的打卡实绩与考勤状态
	 *
	 * <p>白班取当日（00:00:00-23:59:59）首末卡；夜班取两天窗口（当日与次日）内最早/最晚卡，
	 * 实绩仍划归班次开始当日</p>
	 *
	 * @param userId 员工主键
	 * @param date   考勤日
	 */
	void recomputeUserDate(Long userId, LocalDate date);

	/**
	 * 生成某考勤日全员考勤实绩（每日 00:00 定时任务调用，也可手动触发）
	 *
	 * <p>无排班的员工按"工作日常白班、周末休息"生成默认排班（模拟期占位，待排班模块接入后移除）</p>
	 *
	 * @param date 考勤日
	 * @return 生成/刷新的实绩条数
	 */
	int ensureDayRecords(LocalDate date);

}

package com.hsxx.attendance.task;

import com.hsxx.attendance.service.AttendanceCoreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 考勤定时任务
 *
 * @author Chill
 */
@Slf4j
@Component
@AllArgsConstructor
public class AttendanceDailyTask {

	private final AttendanceCoreService coreService;

	/**
	 * 每天 00:00:00 生成全员当日考勤实绩
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void generateDailyRecords() {
		try {
			int count = coreService.ensureDayRecords(LocalDate.now());
			log.info("考勤实绩日结完成，生成 {} 条", count);
		} catch (Exception e) {
			log.error("考勤实绩日结失败", e);
		}
	}

}

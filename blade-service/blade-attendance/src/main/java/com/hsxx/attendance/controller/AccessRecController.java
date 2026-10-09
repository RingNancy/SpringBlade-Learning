package com.hsxx.attendance.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hsxx.attendance.pojo.dto.AccessEventDTO;
import com.hsxx.attendance.pojo.entity.TbAtUrkAccessRec;
import com.hsxx.attendance.service.AttendanceCoreService;
import com.hsxx.attendance.service.TbAtUrkAccessRecService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * 门禁出入采集 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/access")
@Tag(name = "门禁出入采集", description = "ISC 刷脸事件接入与采集记录查询")
public class AccessRecController {

	private final TbAtUrkAccessRecService accessRecService;
	private final AttendanceCoreService coreService;

	/**
	 * 接收单条刷脸事件（模拟 ISC 订阅推送）
	 */
	@PostMapping("/event")
	@Operation(summary = "接收刷脸事件", description = "ISC 订阅推送入口，幂等；落库后实时重算该员工受影响考勤日的实绩")
	public R<TbAtUrkAccessRec> event(@RequestBody AccessEventDTO dto) {
		return R.data(coreService.acceptAccessEvent(dto));
	}

	/**
	 * 门禁采集记录分页
	 */
	@GetMapping("/page")
	@Operation(summary = "门禁采集记录分页", description = "原始刷脸记录，考勤唯一数据源，可作补打卡凭证")
	public R<IPage<TbAtUrkAccessRec>> page(@Parameter(description = "员工主键") @RequestParam(required = false) Long userId,
										   @Parameter(description = "日期 yyyy-MM-dd") @RequestParam(required = false) String date,
										   @Parameter(description = "分页参数") Query query) {
		LambdaQueryWrapper<TbAtUrkAccessRec> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(userId != null, TbAtUrkAccessRec::getUserId, userId);
		if (date != null && !date.isEmpty()) {
			LocalDate day = LocalDate.parse(date);
			wrapper.between(TbAtUrkAccessRec::getAccessTime,
				Date.from(day.atStartOfDay(ZoneId.systemDefault()).toInstant()),
				Date.from(day.atTime(java.time.LocalTime.of(23, 59, 59)).atZone(ZoneId.systemDefault()).toInstant()));
		}
		wrapper.orderByDesc(TbAtUrkAccessRec::getAccessTime);
		return R.data(accessRecService.page(Condition.getPage(query), wrapper));
	}

}

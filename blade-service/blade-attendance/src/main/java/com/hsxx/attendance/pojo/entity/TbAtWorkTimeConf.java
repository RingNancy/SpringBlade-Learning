package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 上下班时间配置表
 *
 * @author Chill
 */
@Data
@TableName("tb_at_work_time_conf")
public class TbAtWorkTimeConf implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 班次类型
	 */
	private Integer classId;

	/**
	 * 开始时间（如 08:00）
	 */
	private String startTime;

	/**
	 * 结束时间（跨天如 19:00→08:00，取 08:00）
	 */
	private String endTime;

	/**
	 * 是否跨天：夜班=1
	 */
	private String acrossDay;

	/**
	 * 标准班时（H）
	 */
	private BigDecimal stdHours;

	/**
	 * 是否生效
	 */
	private String isValid;

	/**
	 * 启用时间（次日 00:00 生效）
	 */
	private Date effectDate;

}

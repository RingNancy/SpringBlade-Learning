package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 个人排班计划表
 *
 * @author Chill
 */
@Data
@TableName("tb_at_user_schedule")
public class TbAtUserSchedule implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 人员信息
	 */
	private Long userId;

	/**
	 * 冗余组织编码
	 */
	private Long organId;

	/**
	 * 日历日期
	 */
	private Date calDate;

	/**
	 * 排班类型（班次）：1 常白班 2 白班倒班 3 12H夜 4 夜班倒班 5 休息
	 */
	private Integer classId;

	/**
	 * 出勤类型：1 应出勤 2 应休息
	 */
	private String attendType;

	/**
	 * 是否手工调整：0 否 1 是
	 */
	private String adjustFlag;

	/**
	 * 来源班组排班（追溯）
	 */
	private Long organScheduleId;

}

package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 假期申请记录表
 *
 * @author Chill
 */
@Data
@TableName("tb_at_vacation_apply")
public class TbAtVacationApply implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 申请单号（QJyyyyMMddNNN）
	 */
	@TableId("apply_no")
	private String applyNo;

	/**
	 * 申请人
	 */
	private Long userId;

	/**
	 * 申请时部门（流程匹配入参）
	 */
	private Long organId;

	/**
	 * 假期类型编码
	 */
	private Integer vacId;

	/**
	 * 开始日期
	 */
	private Date startDate;

	/**
	 * 结束日期
	 */
	private Date endDate;

	/**
	 * 休假天数
	 */
	private BigDecimal leaveDays;

	/**
	 * 审批状态：0 草稿 1 审批中 2 已通过 3 已退回 4 已撤销 5 已销假
	 */
	private String approveStatus;

}

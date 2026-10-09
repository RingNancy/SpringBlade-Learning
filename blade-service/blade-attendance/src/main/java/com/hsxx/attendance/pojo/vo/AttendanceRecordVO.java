package com.hsxx.attendance.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 考勤实绩视图实体类
 *
 * @author Chill
 */
@Data
@Schema(description = "考勤实绩")
public class AttendanceRecordVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "员工主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;

	@Schema(description = "工号")
	private String userCode;

	@Schema(description = "姓名")
	private String userName;

	@Schema(description = "考勤日期")
	private Date calDate;

	@Schema(description = "班次 ID")
	private Integer classId;

	@Schema(description = "班次名称")
	private String className;

	@Schema(description = "班次符号：白/夜/休")
	private String classSymbol;

	@Schema(description = "出勤类型：1 应出勤 2 应休息")
	private String attendType;

	@Schema(description = "上班打卡时间")
	private Date inTime;

	@Schema(description = "上班打卡考勤机")
	private String inUrkCode;

	@Schema(description = "下班打卡时间")
	private Date outTime;

	@Schema(description = "下班打卡考勤机")
	private String outUrkCode;

	@Schema(description = "考勤状态 ID")
	private Integer statusId;

	@Schema(description = "考勤状态编码")
	private String statusCode;

	@Schema(description = "考勤状态名称：出勤/旷工/迟到/早退/假期名称等")
	private String statusName;

	@Schema(description = "状态分类：1 正常 2 异常 3 假期 4 审批中")
	private String statusType;

	@Schema(description = "显示颜色：绿/橙/红")
	private String showColor;

	@Schema(description = "关联单据号（假期申请等）")
	private String applyNo;

}

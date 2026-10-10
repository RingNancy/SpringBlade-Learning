package com.hsxx.organ.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 人力组织架构信息表（同步人力） 实体类
 *
 * @author Chill
 */
@Data
@TableName("tb_g2_organ")
@Schema(description = "部门组织架构")
public class TbG2Organ implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键：人力同步时沿用人力主键，本地新增为空时由雪花算法生成
	 */
	@Schema(description = "组织主键")
	@TableId(value = "organ_id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long organId;

	/**
	 * 公司代码
	 */
	@Schema(description = "公司代码")
	private String companyCode;

	/**
	 * 公司名称
	 */
	@Schema(description = "公司名称")
	private String companyName;

	/**
	 * 部门代码（树形关联键，唯一）
	 */
	@Schema(description = "部门代码")
	private String deptCode;

	/**
	 * 部门名称
	 */
	@Schema(description = "部门名称")
	private String deptName;

	/**
	 * 层次号：1 公司 2 二级部门
	 */
	@Schema(description = "层次号")
	private Integer levelNo;

	/**
	 * 父部门代码
	 */
	@Schema(description = "父部门代码")
	private String parentCode;

	/**
	 * 二级部门代码
	 */
	@Schema(description = "二级部门代码")
	private String secondDept;

	/**
	 * 部门全称
	 */
	@Schema(description = "部门全称")
	private String deptFullName;

	/**
	 * 属性区分
	 */
	@Schema(description = "属性区分")
	private String attrFlag;

	/**
	 * 有效标志：1 有效 0 无效
	 */
	@Schema(description = "有效标志")
	private String validFlag;

	/**
	 * 使用标志
	 */
	@Schema(description = "使用标志")
	private String useFlag;

}

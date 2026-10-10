package com.hsxx.organ.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 组织架构树节点视图实体类
 *
 * @author Chill
 */
@Data
@Schema(description = "组织架构树节点")
public class OrganTreeVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "组织主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long organId;

	@Schema(description = "公司代码")
	private String companyCode;

	@Schema(description = "公司名称")
	private String companyName;

	@Schema(description = "部门代码")
	private String deptCode;

	@Schema(description = "部门名称")
	private String deptName;

	@Schema(description = "层次号：1 公司 2 二级部门")
	private Integer levelNo;

	@Schema(description = "部门全称")
	private String deptFullName;

	@Schema(description = "有效标志")
	private String validFlag;

	@Schema(description = "下级部门")
	private List<OrganTreeVO> children;

}

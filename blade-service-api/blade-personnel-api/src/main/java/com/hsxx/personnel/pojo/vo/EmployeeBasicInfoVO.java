/**
 * Copyright (c) 2018-2099, Chill Zhuang 庄骞 (bladejava@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hsxx.personnel.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工基本信息视图实体类
 *
 * @author Chill
 */
@Data
@Schema(description = "员工基本信息")
public class EmployeeBasicInfoVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 员工主键
	 */
	@Schema(description = "员工主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;

	/**
	 * 工号
	 */
	@Schema(description = "工号")
	private String userCode;

	/**
	 * 姓名
	 */
	@Schema(description = "姓名")
	private String userName;

	/**
	 * 性别代码
	 */
	@Schema(description = "性别代码")
	private String sexCode;

	/**
	 * 出生日期
	 */
	@Schema(description = "出生日期")
	private Date birthDate;

	/**
	 * 民族代码
	 */
	@Schema(description = "民族代码")
	private String nationCode;

	/**
	 * 婚姻状态
	 */
	@Schema(description = "婚姻状态")
	private String marriageStatus;

	/**
	 * 结婚时间
	 */
	@Schema(description = "结婚时间")
	private Date marriageDate;

	/**
	 * 手机号码（自主维护优先，未维护时取人力同步号码）
	 */
	@Schema(description = "手机号码")
	private String mobile;

	/**
	 * 用工类型：1 正式工 2 劳务工 3 实习生 4 外包工
	 */
	@Schema(description = "用工类型")
	private String employeeType;

	/**
	 * 人员属性：1 普通员工 2 班组长 3 工序负责人 4 部门综合主管 5 部门负责人 6 中层正职 7 中层副职 8 公司领导
	 */
	@Schema(description = "人员属性")
	private String userAttr;

	/**
	 * 进入集团日期
	 */
	@Schema(description = "进入集团日期")
	private Date enterGroupDate;

	/**
	 * 进入公司日期
	 */
	@Schema(description = "进入公司日期")
	private Date enterCompanyDate;

	/**
	 * 参加工作时间
	 */
	@Schema(description = "参加工作时间")
	private Date workStartDate;

	/**
	 * 集团工龄（年）
	 */
	@Schema(description = "集团工龄（年）")
	private BigDecimal groupSeniority;

	/**
	 * 公司工龄（年）
	 */
	@Schema(description = "公司工龄（年）")
	private BigDecimal companySeniority;

	/**
	 * 参工工龄（年）
	 */
	@Schema(description = "参工工龄（年）")
	private BigDecimal workSeniority;

	/**
	 * 住所分类：1 住房 2 租房 3 新区宿舍 4 其他
	 */
	@Schema(description = "住所分类")
	private String residenceType;

	/**
	 * 父母常住地
	 */
	@Schema(description = "父母常住地")
	private String parentsHomePlace;

	/**
	 * 配偶常住地
	 */
	@Schema(description = "配偶常住地")
	private String spouseHomePlace;

	/**
	 * 所属内部管理组织（部门/工段/班组）
	 */
	@Schema(description = "所属内部管理组织")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long organId;

	/**
	 * 岗位
	 */
	@Schema(description = "岗位")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long jobId;

	/**
	 * 班组（甲/乙/丙/丁）
	 */
	@Schema(description = "班组")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long teamId;

	/**
	 * 在职状态：1 在职 2 离职 3 退休
	 */
	@Schema(description = "在职状态")
	private String onJobStatus;

	/**
	 * 最近同步时间
	 */
	@Schema(description = "最近同步时间")
	private Date syncTime;

}

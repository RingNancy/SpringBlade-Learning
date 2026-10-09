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
package com.hsxx.personnel.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * 人员属性枚举（tb_g2_user.user_attr、tb_at_user_office_rec.user_attr）
 *
 * @author Chill
 */
public enum UserAttrEnum implements IEnum<String> {

	/**
	 * 普通员工
	 */
	ORDINARY("1", "普通员工"),

	/**
	 * 班组长
	 */
	TEAM_LEADER("2", "班组长"),

	/**
	 * 工序负责人
	 */
	PROCESS_LEADER("3", "工序负责人"),

	/**
	 * 部门综合主管
	 */
	DEPT_SUPERVISOR("4", "部门综合主管"),

	/**
	 * 部门负责人
	 */
	DEPT_LEADER("5", "部门负责人"),

	/**
	 * 中层正职
	 */
	MIDDLE_CHIEF("6", "中层正职"),

	/**
	 * 中层副职
	 */
	MIDDLE_DEPUTY("7", "中层副职"),

	/**
	 * 公司领导
	 */
	COMPANY_LEADER("8", "公司领导");

	/**
	 * 数据库存储编码
	 */
	@EnumValue
	@JsonValue
	private final String value;

	/**
	 * 中文描述
	 */
	private final String text;

	UserAttrEnum(String value, String text) {
		this.value = value;
		this.text = text;
	}

	@Override
	public String getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	/**
	 * 按编码匹配枚举，未匹配返回 null
	 */
	@JsonCreator
	public static UserAttrEnum of(String value) {
		return Arrays.stream(values())
			.filter(item -> item.value.equals(value))
			.findFirst()
			.orElse(null);
	}

	/**
	 * 判断编码是否在枚举范围内
	 */
	public static boolean isValid(String value) {
		return of(value) != null;
	}

	/**
	 * 编码转中文描述，未匹配时原样返回
	 */
	public static String getText(String value) {
		UserAttrEnum item = of(value);
		return item == null ? value : item.text;
	}

}

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
package com.hsxx.attendance.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * 用工类型枚举（tb_g2_user.employee_type）
 *
 * @author Chill
 */
public enum EmployeeTypeEnum implements IEnum<String> {

	/**
	 * 正式工
	 */
	REGULAR("1", "正式工"),

	/**
	 * 劳务工
	 */
	LABOR("2", "劳务工"),

	/**
	 * 实习生
	 */
	TRAINEE("3", "实习生"),

	/**
	 * 外包工
	 */
	OUTSOURCE("4", "外包工");

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

	EmployeeTypeEnum(String value, String text) {
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
	public static EmployeeTypeEnum of(String value) {
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
		EmployeeTypeEnum item = of(value);
		return item == null ? value : item.text;
	}

}

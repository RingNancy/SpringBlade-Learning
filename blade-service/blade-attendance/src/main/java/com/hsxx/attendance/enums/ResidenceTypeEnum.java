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
 * 住所分类枚举（tb_g2_user.residence_type）
 *
 * @author Chill
 */
public enum ResidenceTypeEnum implements IEnum<String> {

	/**
	 * 住房
	 */
	OWN_HOUSE("1", "住房"),

	/**
	 * 租房
	 */
	RENT_HOUSE("2", "租房"),

	/**
	 * 新区宿舍
	 */
	NEW_AREA_DORM("3", "新区宿舍"),

	/**
	 * 其他
	 */
	OTHER("4", "其他");

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

	ResidenceTypeEnum(String value, String text) {
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
	public static ResidenceTypeEnum of(String value) {
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
		ResidenceTypeEnum item = of(value);
		return item == null ? value : item.text;
	}

}

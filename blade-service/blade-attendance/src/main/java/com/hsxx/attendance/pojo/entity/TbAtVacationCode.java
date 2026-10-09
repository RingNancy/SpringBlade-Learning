package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 假期类型编码表
 *
 * @author Chill
 */
@Data
@TableName("tb_at_vacation_code")
public class TbAtVacationCode implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId("vac_id")
	private Integer vacId;

	/**
	 * 假期类型编码
	 */
	private String vacCode;

	/**
	 * 假期类型名称
	 */
	private String vacName;

	/**
	 * 日历符号
	 */
	private String vacSymbol;

}

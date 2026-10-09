package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 班次类型编码表
 *
 * @author Chill
 */
@Data
@TableName("tb_at_class_code")
public class TbAtClassCode implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键：1 常白班 2 白班倒班 3 12H夜 4 夜班倒班 5 休息
	 */
	@TableId("class_id")
	private Integer classId;

	/**
	 * 班次编码
	 */
	private String classCode;

	/**
	 * 班次名称
	 */
	private String className;

	/**
	 * 排班符号：白/夜/休/班(8H)
	 */
	private String classSymbol;

	/**
	 * 排班网格显示色
	 */
	private String colorFlag;

}

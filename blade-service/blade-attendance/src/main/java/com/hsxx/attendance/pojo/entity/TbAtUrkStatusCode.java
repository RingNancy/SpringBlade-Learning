package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 考勤状态编码表
 * @TableName tb_at_urk_status_code
 */
@TableName(value ="tb_at_urk_status_code")
@Data
public class TbAtUrkStatusCode {
    /**
     * 主键
     */
    @TableId
    private Integer statusId;

    /**
     * 考勤状态编码
     */
    private String statusCode;

    /**
     * 名称
     */
    private String statusName;

    /**
     * 1 正常 2 异常 3 假期
     */
    private String statusType;

    /**
     * 显示色
     */
    private String showColor;
}
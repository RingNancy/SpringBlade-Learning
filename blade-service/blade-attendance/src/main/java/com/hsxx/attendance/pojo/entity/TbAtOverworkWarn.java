package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 连续作业超时预警表
 * @TableName tb_at_overwork_warn
 */
@TableName(value ="tb_at_overwork_warn")
@Data
public class TbAtOverworkWarn {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 人员
     */
    private Long userId;

    /**
     * 预警日期
     */
    private Date warnDate;

    /**
     * 连续作业小时数（>12 触发）
     */
    private BigDecimal continuousHours;

    /**
     * 来源：1 调班 2 补打卡 3 值班
     */
    private String warnSource;

    /**
     * 预警接收人（作业长）
     */
    private String warnTarget;

    /**
     * 是否已处理
     */
    private String isHandled;
}
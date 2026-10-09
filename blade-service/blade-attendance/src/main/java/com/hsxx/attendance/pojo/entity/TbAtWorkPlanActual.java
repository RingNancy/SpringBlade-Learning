package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 人员实际出勤记录表
 * @TableName tb_at_work_plan_actual
 */
@TableName(value ="tb_at_work_plan_actual")
@Data
public class TbAtWorkPlanActual {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 人员信息
     */
    private Long userId;

    /**
     * 日历外键
     */
    private Date calDate;

    /**
     * 考勤状态编码（实绩判定结果）
     */
    private Integer statusId;

    /**
     * 关联单据号（假期/补打卡/出差/值班 单号）
     */
    private String applyId;
}
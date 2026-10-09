package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 人员应出勤计划表
 * @TableName tb_at_work_plan
 */
@TableName(value ="tb_at_work_plan")
@Data
public class TbAtWorkPlan {
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
     * 出勤类型：1 应出勤 2 应休息
     */
    private String attendType;
}
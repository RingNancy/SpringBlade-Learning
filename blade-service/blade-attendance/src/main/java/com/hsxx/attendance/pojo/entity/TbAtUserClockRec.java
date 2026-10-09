package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 人员打卡实绩记录表
 * @TableName tb_at_user_clock_rec
 */
@TableName(value ="tb_at_user_clock_rec")
@Data
public class TbAtUserClockRec {
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
     * 实绩日期
     */
    private Date workDate;

    /**
     * 上班打卡时间
     */
    private Date inTime;

    /**
     * 上班打卡考勤机
     */
    private String inUrkCode;

    /**
     * 下班打卡时间
     */
    private Date outTime;

    /**
     * 下班打卡考勤机
     */
    private String outUrkCode;
}
package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 补打卡申请表
 * @TableName tb_at_recard_apply
 */
@TableName(value ="tb_at_recard_apply")
@Data
public class TbAtRecardApply {
    /**
     * 申请单号（BKyyyyMMddNNN）
     */
    @TableId
    private String applyNo;

    /**
     * 申请人
     */
    private Long userId;

    /**
     * 部门
     */
    private Long organId;

    /**
     * 补卡日期（当月有效，跨月不可补）
     */
    private Date workDate;

    /**
     * 1 上班卡 2 下班卡
     */
    private String cardType;

    /**
     * 补卡原因（现场岗位等）
     */
    private String REASON;

    /**
     * 门岗出入记录（作为凭证条件）
     */
    private Long accessRecId;

    /**
     * 当月第 N 次（快照）
     */
    private Integer monthCount;

    /**
     * 审批路径：A 当月≤2天 B 当月≥3天
     */
    private String approveLevelPath;

    /**
     * 审批状态
     */
    private String approveStatus;

    /**
     * 审批流程实例
     */
    private String flowId;

    /**
     * 审批通过后考勤置为：出勤
     */
    private Integer finalStatusId;
}
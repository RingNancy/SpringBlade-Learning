package com.hsxx.organ.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 岗位基础信息表（同步人力）
 * @TableName tb_g2_job
 */
@TableName(value ="tb_g2_job")
@Data
public class TbG2Job {
    /**
     * 主键
     */
    @TableId
    private Long jobId;

    /**
     * 岗位代码
     */
    private String jobCode;

    /**
     * 岗位名称
     */
    private String jobName;
}
package com.hsxx.organ.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 内部管理岗位基础信息表
 * @TableName tb_g2_job_actual
 */
@TableName(value ="tb_g2_job_actual")
@Data
public class TbG2JobActual {
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

    /**
     * 映射人力岗位代码
     */
    private String jobHrCode;

    /**
     * 有效标志
     */
    private String validFlag;
}
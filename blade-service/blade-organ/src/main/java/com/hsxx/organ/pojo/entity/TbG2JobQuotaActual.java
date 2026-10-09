package com.hsxx.organ.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 内部管理组织与岗位关系表
 * @TableName tb_g2_job_quota_actual
 */
@TableName(value ="tb_g2_job_quota_actual")
@Data
public class TbG2JobQuotaActual {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 内部管理组织
     */
    private Long organId;

    /**
     * 内部管理岗位
     */
    private Long jobId;
}
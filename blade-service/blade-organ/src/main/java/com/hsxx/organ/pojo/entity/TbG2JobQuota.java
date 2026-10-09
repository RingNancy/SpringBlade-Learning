package com.hsxx.organ.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 组织与岗位关系表（同步人力）
 * @TableName tb_g2_job_quota
 */
@TableName(value ="tb_g2_job_quota")
@Data
public class TbG2JobQuota {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 岗位代码
     */
    private String jobCode;
}
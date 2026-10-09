package com.hsxx.organ.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 内部管理组织架构信息表
 * @TableName tb_g2_organ_actual
 */
@TableName(value ="tb_g2_organ_actual")
@Data
public class TbG2OrganActual {
    /**
     * 主键
     */
    @TableId
    private Long organId;

    /**
     * 公司代码
     */
    private String companyCode;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 层级：1 公司 2 部门 3 工段 4 班组
     */
    private String organLevel;

    /**
     * 层次号
     */
    private Integer levelNo;

    /**
     * 父部门
     */
    private Long parentId;

    /**
     * 部门全称
     */
    private String deptFullName;

    /**
     * 工序名称（离心机工段下属工序，排班用）
     */
    private String processName;

    /**
     * 属性区分
     */
    private String attrFlag;

    /**
     * 有效标志
     */
    private String validFlag;

    /**
     * 使用标志
     */
    private String useFlag;

    /**
     * 映射人力部门代码
     */
    private String organHrCode;
}
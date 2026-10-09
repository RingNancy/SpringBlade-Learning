package com.hsxx.organ.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 人力组织架构信息表（同步人力）
 * @TableName tb_g2_organ
 */
@TableName(value ="tb_g2_organ")
@Data
public class TbG2Organ {
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
     * 层次号
     */
    private Integer levelNo;

    /**
     * 父部门
     */
    private String parentCode;

    /**
     * 二级部门
     */
    private String secondDept;

    /**
     * 部门全称
     */
    private String deptFullName;

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
}
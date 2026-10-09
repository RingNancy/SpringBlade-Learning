package com.hsxx.personnel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 任职信息履历表（公司内）
 * @TableName tb_at_user_office_rec
 */
@TableName(value ="tb_at_user_office_rec")
@Data
public class TbAtUserOfficeRec {
    /**
     * 主键
     */
    @TableId
    private Long ID;

    /**
     * 人员外键
     */
    private Long USER_ID;

    /**
     * 内部管理组织架构编码
     */
    private Long ORGAN_ACTUAL_ID;

    /**
     * 内部管理岗位编码
     */
    private Long JOB_ACTUAL_ID;

    /**
     * 工号
     */
    private String USER_CODE;

    /**
     * 员工属性（同 TB_G2_USER.USER_ATTR）
     */
    private String USER_ATTR;

    /**
     * 进入集团日期
     */
    private Date ENTER_GROUP_DATE;

    /**
     * 进入公司日期
     */
    private Date ENTER_COMPANY_DATE;

    /**
     * 工龄
     */
    private BigDecimal SENIORITY;

    /**
     * 任职开始日期
     */
    private Date APPOINT_START;

    /**
     * 任职结束日期
     */
    private Date APPOINT_END;

    /**
     * 高温岗位分级
     */
    private String HEAT_JOB_GRADE;

    /**
     * 组织架构编码（映射 HR）
     */
    private String ORGAN_HR_CODE;

    /**
     * 岗位编码（映射 HR）
     */
    private String JOB_HR_CODE;

    /**
     * 在职状态
     */
    private String ON_JOB_STATUS;
}

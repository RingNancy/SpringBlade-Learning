package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 工作履历表（入职前）
 * @TableName tb_at_user_office_info
 */
@TableName(value ="tb_at_user_office_info")
@Data
public class TbAtUserOfficeInfo {
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
     * 企业名称
     */
    private String COMPANY_NAME;

    /**
     * 任职岗位
     */
    private String JOB_NAME;

    /**
     * 开始日期
     */
    private Date START_DATE;

    /**
     * 结束日期
     */
    private Date END_DATE;
}

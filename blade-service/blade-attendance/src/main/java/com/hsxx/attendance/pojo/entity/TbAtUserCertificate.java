package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 持证信息履历表
 * @TableName tb_at_user_certificate
 */
@TableName(value ="tb_at_user_certificate")
@Data
public class TbAtUserCertificate {
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
     * 证书名称
     */
    private String CERT_NAME;

    /**
     * 取证时间
     */
    private Date GET_DATE;

    /**
     * 有效期截至时间
     */
    private Date EXPIRE_DATE;
}

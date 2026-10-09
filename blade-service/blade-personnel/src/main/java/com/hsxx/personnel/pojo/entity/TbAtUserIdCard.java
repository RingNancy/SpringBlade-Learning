package com.hsxx.personnel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 人员身份证信息表
 * @TableName tb_at_user_id_card
 */
@TableName(value ="tb_at_user_id_card")
@Data
public class TbAtUserIdCard {
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
     * 姓名
     */
    private String USER_NAME;

    /**
     * 性别代码
     */
    private String SEX_CODE;

    /**
     * 出生日期
     */
    private Date BIRTH_DATE;

    /**
     * 民族代码
     */
    private String NATION_CODE;

    /**
     * 身份证号
     */
    private String ID_CARD_NO;

    /**
     * 户籍
     */
    private String HOUSEHOLD;

    /**
     * 身份证签发机关
     */
    private String ISSUE_ORGAN;

    /**
     * 有效开始日期
     */
    private Date VALID_FROM;

    /**
     * 有效结束日期
     */
    private Date VALID_TO;
}

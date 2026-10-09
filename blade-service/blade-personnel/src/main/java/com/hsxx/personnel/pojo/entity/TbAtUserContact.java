package com.hsxx.personnel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 主要家庭成员
 * @TableName tb_at_user_contact
 */
@TableName(value ="tb_at_user_contact")
@Data
public class TbAtUserContact {
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
     * 家庭成员姓名
     */
    private String MEMBER_NAME;

    /**
     * 关系
     */
    private String RELATION;

    /**
     * 手机号码
     */
    private String MOBILE;

    /**
     * 住所地址
     */
    private String ADDRESS;

    /**
     * 是否省外
     */
    private String IS_OUT_PROVINCE;
}

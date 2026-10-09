package com.hsxx.personnel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 人员照片表
 * @TableName tb_g2_user_photo
 */
@TableName(value ="tb_g2_user_photo")
@Data
public class TbG2UserPhoto {
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
     * 照片类型：1 身份证照 2 近照 3 证件照（人脸下发用）
     */
    private String PHOTO_TYPE;

    /**
     * 照片地址
     */
    private String PHOTO_URL;
}

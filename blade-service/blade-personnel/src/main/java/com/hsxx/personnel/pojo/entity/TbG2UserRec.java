package com.hsxx.personnel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 人员基础信息履历表
 * @TableName tb_g2_user_rec
 */
@TableName(value ="tb_g2_user_rec")
@Data
public class TbG2UserRec {
    /**
     * 主键
     */
    @TableId
    private Long REC_ID;

    /**
     * 人员外键
     */
    private Long USER_ID;

    /**
     * 变更前快照（JSON 全字段）
     */
    private String SNAP_JSON;

    /**
     * 修改时间
     */
    private Date MODIFY_TIME;

    /**
     * 修改人信息
     */
    private String MODIFY_USER;
}

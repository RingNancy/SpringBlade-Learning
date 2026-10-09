package com.hsxx.organ.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 内部管理组织架构履历表
 * @TableName tb_g2_organ_actual_rec
 */
@TableName(value ="tb_g2_organ_actual_rec")
@Data
public class TbG2OrganActualRec {
    /**
     * 主键
     */
    @TableId
    private Long recId;

    /**
     * 内部管理组织架构 id
     */
    private Long organId;

    /**
     * 变更前快照
     */
    private String snapJson;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人信息
     */
    private String modifyUser;
}
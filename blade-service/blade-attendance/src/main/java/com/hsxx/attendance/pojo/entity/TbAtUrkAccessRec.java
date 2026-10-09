package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 门禁出入采集记录
 * @TableName tb_at_urk_access_rec
 */
@TableName(value ="tb_at_urk_access_rec")
@Data
public class TbAtUrkAccessRec {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 人员信息
     */
    private Long userId;

    /**
     * 考勤机编码（闸机）
     */
    private String urkCode;

    /**
     * 采集时间（采用订阅 ISC 方式推送）
     */
    private Date accessTime;

    /**
     * 1 进 2 出
     */
    private String accessType;

    /**
     * 是否已聚合至打卡实绩
     */
    private String syncFlag;
}
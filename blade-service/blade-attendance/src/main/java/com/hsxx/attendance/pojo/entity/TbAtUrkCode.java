package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 考勤机编码表
 * @TableName tb_at_urk_code
 */
@TableName(value ="tb_at_urk_code")
@Data
public class TbAtUrkCode {
    /**
     * 主键
     */
    @TableId
    private Integer urkId;

    /**
     * 考勤机编码（门禁闸机）
     */
    private String urkCode;

    /**
     * 考勤机名称
     */
    private String urkName;

    /**
     * 安装位置（东门闸机等）
     */
    private String POSITION;

    /**
     * 1 门禁闸机 2 刷脸考勤机
     */
    private String urkType;
}
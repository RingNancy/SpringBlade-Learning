package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 考勤权限记录表
 * @TableName tb_at_urk_conf
 */
@TableName(value ="tb_at_urk_conf")
@Data
public class TbAtUrkConf {
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
     * 考勤机编码
     */
    private String urkCode;

    /**
     * 人脸照片
     */
    private String faceUrl;

    /**
     * 下发考勤机时间
     */
    private Date issueTime;
}
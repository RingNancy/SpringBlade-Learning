package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 人员考勤记录修改履历表
 * @TableName tb_at_work_rec
 */
@TableName(value ="tb_at_work_rec")
@Data
public class TbAtWorkRec {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 实际出勤记录 id
     */
    private Long planActualId;

    /**
     * 日历外键
     */
    private Date calDate;

    /**
     * 旧考勤状态编码
     */
    private Integer oldStatusId;

    /**
     * 新考勤状态编码
     */
    private Integer newStatusId;

    /**
     * 调整原因
     */
    private String adjustReason;

    /**
     * 调整人（部门管理员）
     */
    private String adjustUser;
}
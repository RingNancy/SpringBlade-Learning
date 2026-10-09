package com.hsxx.attendance.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 考勤机识别明细
 * @TableName tb_at_urk_identify_rec
 */
@TableName(value ="tb_at_urk_identify_rec")
@Data
public class TbAtUrkIdentifyRec {
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
     * 识别时间
     */
    private Date identifyTime;

    /**
     * 识别结果：1 成功 2 失败
     */
    private String identifyResult;
}
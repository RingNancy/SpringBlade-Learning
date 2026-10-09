package com.hsxx.personnel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 学历信息履历表
 * @TableName tb_at_user_degree
 */
@TableName(value ="tb_at_user_degree")
@Data
public class TbAtUserDegree {
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
     * 学历编码
     */
    private String DEGREE_CODE;

    /**
     * 学位编码
     */
    private String ACADEMIC_CODE;

    /**
     * 专业
     */
    private String MAJOR;

    /**
     * 学位获取时间
     */
    private Date DEGREE_DATE;

    /**
     * 毕业学校
     */
    private String SCHOOL;

    /**
     * 毕业日期
     */
    private Date GRADUATE_DATE;
}

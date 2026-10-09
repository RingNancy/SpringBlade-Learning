-- =============================================
-- 黄石新兴人力资源考勤系统 数据库建表脚本
-- Database: MySQL 8.0+
-- Charset: utf8mb4 / utf8mb4_general_ci
-- Generated: 2026-10-08
-- Tables: 59 (10 模块)
-- =============================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ===========================================
-- 模块 1：人员信息（9 张表）
-- ===========================================

-- 1. 人员基础信息表（同步人力）
DROP TABLE IF EXISTS `TB_G2_USER`;
CREATE TABLE `TB_G2_USER` (
  `USER_ID`              BIGINT        NOT NULL                       COMMENT '主键',
  `USER_CODE`            VARCHAR(32)   NOT NULL                       COMMENT '工号（如 GD020118）',
  `USER_NAME`            VARCHAR(64)   NOT NULL                       COMMENT '姓名',
  `SEX_CODE`             CHAR(2)       DEFAULT NULL                   COMMENT '性别代码',
  `BIRTH_DATE`           DATE          DEFAULT NULL                   COMMENT '出生日期',
  `NATION_CODE`          CHAR(2)       DEFAULT NULL                   COMMENT '民族代码',
  `MARRIAGE_STATUS`      CHAR(2)       DEFAULT NULL                   COMMENT '婚姻状态',
  `MARRIAGE_DATE`        DATE          DEFAULT NULL                   COMMENT '结婚时间',
  `MOBILE_SYNC`          VARCHAR(20)   DEFAULT NULL                   COMMENT '手机号码（人力同步）',
  `MOBILE_SELF`          VARCHAR(20)   DEFAULT NULL                   COMMENT '手机号码（自主维护）',
  `EMPLOYEE_TYPE`        CHAR(2)       NOT NULL                       COMMENT '用工类型：1 正式工 2 劳务工 3 实习生 4 外包工',
  `USER_ATTR`            CHAR(2)       NOT NULL                       COMMENT '人员属性：1 普通员工 2 班组长 3 工序负责人 4 部门综合主管 5 部门负责人 6 中层正职 7 中层副职 8 公司领导',
  `ENTER_GROUP_DATE`     DATE          DEFAULT NULL                   COMMENT '进入集团日期',
  `ENTER_COMPANY_DATE`   DATE          DEFAULT NULL                   COMMENT '进入公司日期',
  `WORK_START_DATE`      DATE          DEFAULT NULL                   COMMENT '参加工作时间',
  `GROUP_SENIORITY`      DECIMAL(5,1)  DEFAULT NULL                   COMMENT '集团工龄（年，系统自算）',
  `COMPANY_SENIORITY`    DECIMAL(5,1)  DEFAULT NULL                   COMMENT '公司工龄（年，系统自算）',
  `WORK_SENIORITY`       DECIMAL(5,1)  DEFAULT NULL                   COMMENT '参工工龄（年，系统自算）',
  `RESIDENCE_TYPE`       CHAR(2)       DEFAULT NULL                   COMMENT '住所分类：1 住房 2 租房 3 新区宿舍 4 其他',
  `PARENTS_HOME_PLACE`   VARCHAR(200)  DEFAULT NULL                   COMMENT '父母常住地',
  `SPOUSE_HOME_PLACE`    VARCHAR(200)  DEFAULT NULL                   COMMENT '配偶常住地',
  `ORGAN_ID`             BIGINT        NOT NULL                       COMMENT '所属内部管理组织（部门/工段/班组）',
  `JOB_ID`               BIGINT        DEFAULT NULL                   COMMENT '岗位',
  `TEAM_ID`              BIGINT        DEFAULT NULL                   COMMENT '班组（甲/乙/丙/丁）',
  `ON_JOB_STATUS`        CHAR(2)       NOT NULL                       COMMENT '在职状态：1 在职 2 离职 3 退休',
  `SYNC_TIME`            DATETIME      DEFAULT NULL                   COMMENT '最近同步时间',
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `UK_USER_CODE` (`USER_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人员基础信息表（同步人力）';

-- 2. 人员基础信息履历表
DROP TABLE IF EXISTS `TB_G2_USER_REC`;
CREATE TABLE `TB_G2_USER_REC` (
  `REC_ID`       BIGINT        NOT NULL                COMMENT '主键',
  `USER_ID`      BIGINT        NOT NULL                COMMENT '人员外键',
  `SNAP_JSON`    TEXT          DEFAULT NULL             COMMENT '变更前快照（JSON 全字段）',
  `MODIFY_TIME`  DATETIME      DEFAULT NULL             COMMENT '修改时间',
  `MODIFY_USER`  VARCHAR(64)   DEFAULT NULL             COMMENT '修改人信息',
  PRIMARY KEY (`REC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人员基础信息履历表';

-- 3. 人员身份证信息表
DROP TABLE IF EXISTS `TB_AT_USER_ID_CARD`;
CREATE TABLE `TB_AT_USER_ID_CARD` (
  `ID`           BIGINT        NOT NULL                COMMENT '主键',
  `USER_ID`      BIGINT        NOT NULL                COMMENT '人员外键',
  `USER_NAME`    VARCHAR(64)   NOT NULL                COMMENT '姓名',
  `SEX_CODE`     CHAR(2)       DEFAULT NULL            COMMENT '性别代码',
  `BIRTH_DATE`   DATE          DEFAULT NULL            COMMENT '出生日期',
  `NATION_CODE`  CHAR(2)       DEFAULT NULL            COMMENT '民族代码',
  `ID_CARD_NO`   VARCHAR(18)   NOT NULL                COMMENT '身份证号',
  `HOUSEHOLD`    VARCHAR(200)  DEFAULT NULL            COMMENT '户籍',
  `ISSUE_ORGAN`  VARCHAR(200)  DEFAULT NULL            COMMENT '身份证签发机关',
  `VALID_FROM`   DATE          DEFAULT NULL            COMMENT '有效开始日期',
  `VALID_TO`     DATE          DEFAULT NULL            COMMENT '有效结束日期',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_ID_CARD_NO` (`ID_CARD_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人员身份证信息表';

-- 4. 主要家庭成员
DROP TABLE IF EXISTS `TB_AT_USER_CONTACT`;
CREATE TABLE `TB_AT_USER_CONTACT` (
  `ID`              BIGINT        NOT NULL              COMMENT '主键',
  `USER_ID`         BIGINT        NOT NULL              COMMENT '人员外键',
  `MEMBER_NAME`     VARCHAR(64)   NOT NULL              COMMENT '家庭成员姓名',
  `RELATION`        VARCHAR(20)   NOT NULL              COMMENT '关系',
  `MOBILE`          VARCHAR(20)   DEFAULT NULL          COMMENT '手机号码',
  `ADDRESS`         VARCHAR(200)  DEFAULT NULL          COMMENT '住所地址',
  `IS_OUT_PROVINCE` CHAR(1)       DEFAULT NULL          COMMENT '是否省外',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='主要家庭成员';

-- 5. 人员照片表
DROP TABLE IF EXISTS `TB_G2_USER_PHOTO`;
CREATE TABLE `TB_G2_USER_PHOTO` (
  `ID`          BIGINT        NOT NULL                  COMMENT '主键',
  `USER_ID`     BIGINT        NOT NULL                  COMMENT '人员外键',
  `PHOTO_TYPE`  CHAR(2)       NOT NULL                  COMMENT '照片类型：1 身份证照 2 近照 3 证件照（人脸下发用）',
  `PHOTO_URL`   VARCHAR(500)  NOT NULL                  COMMENT '照片地址',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人员照片表';

-- 6. 学历信息履历表
DROP TABLE IF EXISTS `TB_AT_USER_DEGREE`;
CREATE TABLE `TB_AT_USER_DEGREE` (
  `ID`            BIGINT        NOT NULL                COMMENT '主键',
  `USER_ID`       BIGINT        NOT NULL                COMMENT '人员外键',
  `DEGREE_CODE`   CHAR(2)       DEFAULT NULL            COMMENT '学历编码',
  `ACADEMIC_CODE` CHAR(2)       DEFAULT NULL            COMMENT '学位编码',
  `MAJOR`         VARCHAR(100)  DEFAULT NULL            COMMENT '专业',
  `DEGREE_DATE`   DATE          DEFAULT NULL            COMMENT '学位获取时间',
  `SCHOOL`        VARCHAR(100)  DEFAULT NULL            COMMENT '毕业学校',
  `GRADUATE_DATE` DATE          DEFAULT NULL            COMMENT '毕业日期',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学历信息履历表';

-- 7. 持证信息履历表
DROP TABLE IF EXISTS `TB_AT_USER_CERTIFICATE`;
CREATE TABLE `TB_AT_USER_CERTIFICATE` (
  `ID`          BIGINT        NOT NULL                  COMMENT '主键',
  `USER_ID`     BIGINT        NOT NULL                  COMMENT '人员外键',
  `CERT_NAME`   VARCHAR(200)  NOT NULL                  COMMENT '证书名称',
  `GET_DATE`    DATE          DEFAULT NULL              COMMENT '取证时间',
  `EXPIRE_DATE` DATE          DEFAULT NULL              COMMENT '有效期截至时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='持证信息履历表';

-- 8. 工作履历表（入职前）
DROP TABLE IF EXISTS `TB_AT_USER_OFFICE_INFO`;
CREATE TABLE `TB_AT_USER_OFFICE_INFO` (
  `ID`            BIGINT        NOT NULL                COMMENT '主键',
  `USER_ID`       BIGINT        NOT NULL                COMMENT '人员外键',
  `COMPANY_NAME`  VARCHAR(200)  NOT NULL                COMMENT '企业名称',
  `JOB_NAME`      VARCHAR(100)  DEFAULT NULL            COMMENT '任职岗位',
  `START_DATE`    DATE          DEFAULT NULL            COMMENT '开始日期',
  `END_DATE`      DATE          DEFAULT NULL            COMMENT '结束日期',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='工作履历表（入职前）';

-- 9. 任职信息履历表（公司内）
DROP TABLE IF EXISTS `TB_AT_USER_OFFICE_REC`;
CREATE TABLE `TB_AT_USER_OFFICE_REC` (
  `ID`                 BIGINT        NOT NULL            COMMENT '主键',
  `USER_ID`            BIGINT        NOT NULL            COMMENT '人员外键',
  `ORGAN_ACTUAL_ID`    BIGINT        NOT NULL            COMMENT '内部管理组织架构编码',
  `JOB_ACTUAL_ID`      BIGINT        DEFAULT NULL        COMMENT '内部管理岗位编码',
  `USER_CODE`          VARCHAR(32)   NOT NULL            COMMENT '工号',
  `USER_ATTR`          CHAR(2)       NOT NULL            COMMENT '员工属性（同 TB_G2_USER.USER_ATTR）',
  `ENTER_GROUP_DATE`   DATE          DEFAULT NULL        COMMENT '进入集团日期',
  `ENTER_COMPANY_DATE` DATE          DEFAULT NULL        COMMENT '进入公司日期',
  `SENIORITY`          DECIMAL(5,1)  DEFAULT NULL        COMMENT '工龄',
  `APPOINT_START`      DATE          DEFAULT NULL        COMMENT '任职开始日期',
  `APPOINT_END`        DATE          DEFAULT NULL        COMMENT '任职结束日期',
  `HEAT_JOB_GRADE`     CHAR(2)       DEFAULT NULL        COMMENT '高温岗位分级',
  `ORGAN_HR_CODE`      VARCHAR(32)   DEFAULT NULL        COMMENT '组织架构编码（映射 HR）',
  `JOB_HR_CODE`        VARCHAR(32)   DEFAULT NULL        COMMENT '岗位编码（映射 HR）',
  `ON_JOB_STATUS`      CHAR(2)       NOT NULL            COMMENT '在职状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='任职信息履历表（公司内）';


-- ===========================================
-- 模块 2：组织架构（8 张表）
-- ===========================================

-- 10. 人力组织架构信息表（同步人力）
DROP TABLE IF EXISTS `TB_G2_ORGAN`;
CREATE TABLE `TB_G2_ORGAN` (
  `ORGAN_ID`       BIGINT        NOT NULL              COMMENT '主键',
  `COMPANY_CODE`   VARCHAR(20)   NOT NULL              COMMENT '公司代码',
  `COMPANY_NAME`   VARCHAR(100)  NOT NULL              COMMENT '公司名称',
  `DEPT_CODE`      VARCHAR(32)   NOT NULL              COMMENT '部门代码',
  `DEPT_NAME`      VARCHAR(100)  NOT NULL              COMMENT '部门名称',
  `LEVEL_NO`       INT           DEFAULT NULL          COMMENT '层次号',
  `PARENT_CODE`    VARCHAR(32)   DEFAULT NULL          COMMENT '父部门',
  `SECOND_DEPT`    VARCHAR(32)   DEFAULT NULL          COMMENT '二级部门',
  `DEPT_FULL_NAME` VARCHAR(300)  DEFAULT NULL          COMMENT '部门全称',
  `ATTR_FLAG`      CHAR(2)       DEFAULT NULL          COMMENT '属性区分',
  `VALID_FLAG`     CHAR(1)       NOT NULL DEFAULT '1'  COMMENT '有效标志',
  `USE_FLAG`       CHAR(1)       DEFAULT NULL          COMMENT '使用标志',
  PRIMARY KEY (`ORGAN_ID`),
  UNIQUE KEY `UK_DEPT_CODE` (`DEPT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人力组织架构信息表（同步人力）';

-- 11. 人力组织架构履历表
DROP TABLE IF EXISTS `TB_G2_ORGAN_REC`;
CREATE TABLE `TB_G2_ORGAN_REC` (
  `REC_ID`       BIGINT        NOT NULL                COMMENT '主键',
  `ORGAN_ID`     BIGINT        NOT NULL                COMMENT '组织架构 ID',
  `SNAP_JSON`    TEXT          DEFAULT NULL             COMMENT '变更前快照',
  `MODIFY_TIME`  DATETIME      DEFAULT NULL            COMMENT '修改时间',
  `MODIFY_USER`  VARCHAR(64)   DEFAULT NULL            COMMENT '修改人信息',
  PRIMARY KEY (`REC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人力组织架构履历表';

-- 12. 内部管理组织架构信息表
DROP TABLE IF EXISTS `TB_G2_ORGAN_ACTUAL`;
CREATE TABLE `TB_G2_ORGAN_ACTUAL` (
  `ORGAN_ID`       BIGINT        NOT NULL              COMMENT '主键',
  `COMPANY_CODE`   VARCHAR(20)   NOT NULL              COMMENT '公司代码',
  `COMPANY_NAME`   VARCHAR(100)  NOT NULL              COMMENT '公司名称',
  `DEPT_CODE`      VARCHAR(32)   NOT NULL              COMMENT '部门代码',
  `DEPT_NAME`      VARCHAR(100)  NOT NULL              COMMENT '部门名称',
  `ORGAN_LEVEL`    CHAR(2)       NOT NULL              COMMENT '层级：1 公司 2 部门 3 工段 4 班组',
  `LEVEL_NO`       INT           DEFAULT NULL          COMMENT '层次号',
  `PARENT_ID`      BIGINT        DEFAULT NULL          COMMENT '父部门',
  `DEPT_FULL_NAME` VARCHAR(300)  DEFAULT NULL          COMMENT '部门全称',
  `PROCESS_NAME`   VARCHAR(100)  DEFAULT NULL          COMMENT '工序名称（离心机工段下属工序，排班用）',
  `ATTR_FLAG`      CHAR(2)       DEFAULT NULL          COMMENT '属性区分',
  `VALID_FLAG`     CHAR(1)       NOT NULL DEFAULT '1'  COMMENT '有效标志',
  `USE_FLAG`       CHAR(1)       DEFAULT NULL          COMMENT '使用标志',
  `ORGAN_HR_CODE`  VARCHAR(32)   DEFAULT NULL          COMMENT '映射人力部门代码',
  PRIMARY KEY (`ORGAN_ID`),
  UNIQUE KEY `UK_ACTUAL_DEPT_CODE` (`DEPT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='内部管理组织架构信息表';

-- 13. 内部管理组织架构履历表
DROP TABLE IF EXISTS `TB_G2_ORGAN_ACTUAL_REC`;
CREATE TABLE `TB_G2_ORGAN_ACTUAL_REC` (
  `REC_ID`       BIGINT        NOT NULL                COMMENT '主键',
  `ORGAN_ID`     BIGINT        NOT NULL                COMMENT '内部管理组织架构 ID',
  `SNAP_JSON`    TEXT          DEFAULT NULL             COMMENT '变更前快照',
  `MODIFY_TIME`  DATETIME      DEFAULT NULL            COMMENT '修改时间',
  `MODIFY_USER`  VARCHAR(64)   DEFAULT NULL            COMMENT '修改人信息',
  PRIMARY KEY (`REC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='内部管理组织架构履历表';

-- 14. 岗位基础信息表（同步人力）
DROP TABLE IF EXISTS `TB_G2_JOB`;
CREATE TABLE `TB_G2_JOB` (
  `JOB_ID`    BIGINT        NOT NULL                   COMMENT '主键',
  `JOB_CODE`  VARCHAR(32)   NOT NULL                   COMMENT '岗位代码',
  `JOB_NAME`  VARCHAR(100)  NOT NULL                   COMMENT '岗位名称',
  PRIMARY KEY (`JOB_ID`),
  UNIQUE KEY `UK_JOB_CODE` (`JOB_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='岗位基础信息表（同步人力）';

-- 15. 内部管理岗位基础信息表
DROP TABLE IF EXISTS `TB_G2_JOB_ACTUAL`;
CREATE TABLE `TB_G2_JOB_ACTUAL` (
  `JOB_ID`      BIGINT        NOT NULL                 COMMENT '主键',
  `JOB_CODE`    VARCHAR(32)   NOT NULL                 COMMENT '岗位代码',
  `JOB_NAME`    VARCHAR(100)  NOT NULL                 COMMENT '岗位名称',
  `JOB_HR_CODE` VARCHAR(32)   DEFAULT NULL             COMMENT '映射人力岗位代码',
  `VALID_FLAG`  CHAR(1)       NOT NULL DEFAULT '1'     COMMENT '有效标志',
  PRIMARY KEY (`JOB_ID`),
  UNIQUE KEY `UK_JOB_ACTUAL_CODE` (`JOB_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='内部管理岗位基础信息表';

-- 16. 组织与岗位关系表（同步人力）
DROP TABLE IF EXISTS `TB_G2_JOB_QUOTA`;
CREATE TABLE `TB_G2_JOB_QUOTA` (
  `ID`        BIGINT        NOT NULL                   COMMENT '主键',
  `DEPT_CODE` VARCHAR(32)   NOT NULL                   COMMENT '部门代码',
  `JOB_CODE`  VARCHAR(32)   NOT NULL                   COMMENT '岗位代码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='组织与岗位关系表（同步人力）';

-- 17. 内部管理组织与岗位关系表
DROP TABLE IF EXISTS `TB_G2_JOB_QUOTA_ACTUAL`;
CREATE TABLE `TB_G2_JOB_QUOTA_ACTUAL` (
  `ID`       BIGINT  NOT NULL                          COMMENT '主键',
  `ORGAN_ID` BIGINT  NOT NULL                          COMMENT '内部管理组织',
  `JOB_ID`   BIGINT  NOT NULL                          COMMENT '内部管理岗位',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='内部管理组织与岗位关系表';


-- ===========================================
-- 模块 3：考勤基础配置（7 张表）
-- ===========================================

-- 18. 班组编码表
DROP TABLE IF EXISTS `TB_AT_TEAM_CODE`;
CREATE TABLE `TB_AT_TEAM_CODE` (
  `TEAM_ID`   INT       NOT NULL                       COMMENT '主键',
  `TEAM_NAME` CHAR(10)  NOT NULL                       COMMENT '编码名称：甲班/乙班/丙班/丁班',
  PRIMARY KEY (`TEAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='班组编码表';

-- 19. 班次类型编码表
DROP TABLE IF EXISTS `TB_AT_CLASS_CODE`;
CREATE TABLE `TB_AT_CLASS_CODE` (
  `CLASS_ID`     INT       NOT NULL                    COMMENT '主键',
  `CLASS_CODE`   CHAR(2)   NOT NULL                    COMMENT '班次编码',
  `CLASS_NAME`   CHAR(20)  NOT NULL                    COMMENT '班次名称',
  `CLASS_SYMBOL` CHAR(4)   NOT NULL                    COMMENT '排班符号：白/夜/休/班(8H)',
  `COLOR_FLAG`   CHAR(8)   DEFAULT NULL                COMMENT '排班网格显示色',
  PRIMARY KEY (`CLASS_ID`),
  UNIQUE KEY `UK_CLASS_CODE` (`CLASS_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='班次类型编码表';

-- 20. 倒班方式编码表
DROP TABLE IF EXISTS `TB_AT_WORK_SHIFT_CODE`;
CREATE TABLE `TB_AT_WORK_SHIFT_CODE` (
  `SHIFT_ID`   INT       NOT NULL                      COMMENT '主键',
  `SHIFT_NAME` CHAR(20)  NOT NULL                      COMMENT '编码名称：常白班 / 三班倒 / 四班倒 / 自定义',
  `IS_AUTO`    CHAR(1)   NOT NULL                      COMMENT '1 系统自动排 2 人工排',
  PRIMARY KEY (`SHIFT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='倒班方式编码表';

-- 21. 上下班时间配置表
DROP TABLE IF EXISTS `TB_AT_WORK_TIME_CONF`;
CREATE TABLE `TB_AT_WORK_TIME_CONF` (
  `ID`          BIGINT        NOT NULL                 COMMENT '主键',
  `CLASS_ID`    INT           NOT NULL                 COMMENT '班次类型',
  `START_TIME`  CHAR(5)       NOT NULL                 COMMENT '开始时间（如 08:00）',
  `END_TIME`    CHAR(5)       NOT NULL                 COMMENT '结束时间（跨天如 19:00→08:00）',
  `ACROSS_DAY`  CHAR(1)       NOT NULL DEFAULT '0'     COMMENT '是否跨天：夜班=1',
  `STD_HOURS`   DECIMAL(4,1)  NOT NULL                 COMMENT '标准班时（H）：8H白班=8.0，白班/夜班=11.0（含用餐）',
  `IS_VALID`    CHAR(1)       NOT NULL DEFAULT '1'     COMMENT '是否生效',
  `EFFECT_DATE` DATE          DEFAULT NULL             COMMENT '启用时间（次日 00:00 生效）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='上下班时间配置表';

-- 22. 排班模板表
DROP TABLE IF EXISTS `TB_AT_SHIFT_TEMPLATE`;
CREATE TABLE `TB_AT_SHIFT_TEMPLATE` (
  `TEMPLATE_ID`      BIGINT        NOT NULL            COMMENT '主键',
  `TEMPLATE_NAME`    VARCHAR(50)   NOT NULL            COMMENT '模板名称：三班倒 / 四班倒 / 自定义',
  `CYCLE_DAYS`       INT           NOT NULL            COMMENT '循环周期（天）：三班倒=3，四班倒=4',
  `TEAM_OFFSET_JSON` TEXT          DEFAULT NULL        COMMENT '各班组错位起班偏移（如 甲班序位0、乙班1、丙班2、丁班3）',
  `IS_VALID`         CHAR(1)       NOT NULL DEFAULT '1' COMMENT '是否生效',
  PRIMARY KEY (`TEMPLATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='排班模板表';

-- 23. 排班模板明细表
DROP TABLE IF EXISTS `TB_AT_SHIFT_TEMPLATE_ITEM`;
CREATE TABLE `TB_AT_SHIFT_TEMPLATE_ITEM` (
  `ID`          BIGINT  NOT NULL                       COMMENT '主键',
  `TEMPLATE_ID` BIGINT  NOT NULL                       COMMENT '模板外键',
  `SEQ_NO`      INT     NOT NULL                       COMMENT '循环序号（从 1 开始）',
  `CLASS_ID`    INT     NOT NULL                       COMMENT '班次',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='排班模板明细表';

-- 24. 日历信息表（公休日设置）
DROP TABLE IF EXISTS `TB_AT_HOLIDAY_INFO`;
CREATE TABLE `TB_AT_HOLIDAY_INFO` (
  `ID`        BIGINT       NOT NULL                    COMMENT '主键',
  `CAL_DATE`  DATE         NOT NULL                    COMMENT '日期',
  `DAY_TYPE`  CHAR(1)      NOT NULL                    COMMENT '1 上班（含调休补班） 2 放假',
  `DAY_NAME`  VARCHAR(50)  DEFAULT NULL                COMMENT '名称（元旦/春节/中秋/公司放假等）',
  `IS_LEGAL`  CHAR(1)      DEFAULT NULL                COMMENT '是否国家法定',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_CAL_DATE` (`CAL_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='日历信息表（公休日设置）';


-- ===========================================
-- 模块 4：排班计划（4 张表）
-- ===========================================

-- 25. 班组排班计划表
DROP TABLE IF EXISTS `TB_AT_ORGAN_SCHEDULE`;
CREATE TABLE `TB_AT_ORGAN_SCHEDULE` (
  `ID`          BIGINT    NOT NULL                     COMMENT '主键',
  `ORGAN_ID`    BIGINT    NOT NULL                     COMMENT '内部管理组织（工段/班组层级）',
  `TEAM_ID`     INT       DEFAULT NULL                 COMMENT '班组（甲/乙/丙/丁）',
  `CAL_DATE`    DATE      NOT NULL                     COMMENT '日历外键（TB_AT_HOLIDAY_INFO.CAL_DATE）',
  `CLASS_ID`    INT       NOT NULL                     COMMENT '排班类型（班次）',
  `SHIFT_ID`    INT       DEFAULT NULL                 COMMENT '倒班方式（常白班自动/四班倒/12H人工）',
  `PLAN_MONTH`  CHAR(6)   NOT NULL                     COMMENT '归属月度（yyyyMM，发布状态管理用）',
  `PUB_STATUS`  CHAR(1)   NOT NULL DEFAULT '0'         COMMENT '0 草稿 1 已发布',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='班组排班计划表';

-- 26. 个人排班计划表
DROP TABLE IF EXISTS `TB_AT_USER_SCHEDULE`;
CREATE TABLE `TB_AT_USER_SCHEDULE` (
  `ID`                 BIGINT    NOT NULL              COMMENT '主键',
  `USER_ID`            BIGINT    NOT NULL              COMMENT '人员信息',
  `ORGAN_ID`           BIGINT    DEFAULT NULL          COMMENT '冗余组织编码',
  `CAL_DATE`           DATE      NOT NULL              COMMENT '日历外键',
  `CLASS_ID`           INT       NOT NULL              COMMENT '排班类型（班次）',
  `ATTEND_TYPE`        CHAR(1)   NOT NULL              COMMENT '出勤类型：1 应出勤 2 应休息',
  `ADJUST_FLAG`        CHAR(1)   NOT NULL DEFAULT '0'  COMMENT '是否手工调整：0 否 1 是（个人明细红字显示）',
  `ORGAN_SCHEDULE_ID`  BIGINT    DEFAULT NULL          COMMENT '来源班组排班（追溯）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='个人排班计划表';

-- 27. 个人排班修改履历表
DROP TABLE IF EXISTS `TB_AT_USER_SCHEDULE_REC`;
CREATE TABLE `TB_AT_USER_SCHEDULE_REC` (
  `ID`               BIGINT       NOT NULL             COMMENT '主键',
  `USER_SCHEDULE_ID`  BIGINT      NOT NULL             COMMENT '个人排班主键 ID',
  `CAL_DATE`          DATE        DEFAULT NULL         COMMENT '日历外键',
  `OLD_CLASS_ID`      INT         DEFAULT NULL         COMMENT '旧排班类型',
  `NEW_CLASS_ID`      INT         DEFAULT NULL         COMMENT '新排班类型',
  `OLD_ATTEND_TYPE`   CHAR(1)     DEFAULT NULL         COMMENT '旧出勤类型',
  `NEW_ATTEND_TYPE`   CHAR(1)     DEFAULT NULL         COMMENT '新出勤类型',
  `ADJUST_REASON`     VARCHAR(200) DEFAULT NULL        COMMENT '原因（调班/自动排班重算）',
  `ADJUST_TYPE`       CHAR(1)     DEFAULT NULL         COMMENT '1 提前一天调班 2 临时调班 3 自动排班',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='个人排班修改履历表';

-- 28. 自动排班生成日志
DROP TABLE IF EXISTS `TB_AT_SCHEDULE_GEN_LOG`;
CREATE TABLE `TB_AT_SCHEDULE_GEN_LOG` (
  `ID`          BIGINT       NOT NULL                  COMMENT '主键',
  `PLAN_MONTH`  CHAR(6)      NOT NULL                  COMMENT '生成月份',
  `SHIFT_TYPE`  CHAR(1)      NOT NULL                  COMMENT '1 常白班（每月25日触发） 2 三班倒/四班倒（按模板）',
  `GEN_TIME`    DATETIME     NOT NULL                  COMMENT '生成时间',
  `GEN_COUNT`   INT          DEFAULT NULL              COMMENT '生成记录数',
  `RESULT`      VARCHAR(500) DEFAULT NULL              COMMENT '结果说明',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='自动排班生成日志';


-- ===========================================
-- 模块 5：打卡与考勤实绩（11 张表）
-- ===========================================

-- 29. 考勤机编码表
DROP TABLE IF EXISTS `TB_AT_URK_CODE`;
CREATE TABLE `TB_AT_URK_CODE` (
  `URK_ID`    INT        NOT NULL                      COMMENT '主键',
  `URK_CODE`  CHAR(20)   NOT NULL                      COMMENT '考勤机编码（门禁闸机）',
  `URK_NAME`  CHAR(50)   NOT NULL                      COMMENT '考勤机名称',
  `POSITION`  CHAR(100)  NOT NULL                      COMMENT '安装位置（东门闸机等）',
  `URK_TYPE`  CHAR(1)    DEFAULT NULL                  COMMENT '1 门禁闸机 2 刷脸考勤机',
  PRIMARY KEY (`URK_ID`),
  UNIQUE KEY `UK_URK_CODE` (`URK_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='考勤机编码表';

-- 30. 考勤权限记录表
DROP TABLE IF EXISTS `TB_AT_URK_CONF`;
CREATE TABLE `TB_AT_URK_CONF` (
  `ID`         BIGINT       NOT NULL                   COMMENT '主键',
  `USER_ID`    BIGINT       NOT NULL                   COMMENT '人员信息',
  `URK_CODE`   CHAR(20)     NOT NULL                   COMMENT '考勤机编码',
  `FACE_URL`   VARCHAR(500) DEFAULT NULL               COMMENT '人脸照片',
  `ISSUE_TIME` DATE         DEFAULT NULL               COMMENT '下发考勤机时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='考勤权限记录表';

-- 31. 门禁出入采集记录
DROP TABLE IF EXISTS `TB_AT_URK_ACCESS_REC`;
CREATE TABLE `TB_AT_URK_ACCESS_REC` (
  `ID`          BIGINT    NOT NULL                     COMMENT '主键',
  `USER_ID`     BIGINT    NOT NULL                     COMMENT '人员信息',
  `URK_CODE`    CHAR(20)  NOT NULL                     COMMENT '考勤机编码（闸机）',
  `ACCESS_TIME` DATETIME  NOT NULL                     COMMENT '采集时间（采用订阅 ISC 方式推送）',
  `ACCESS_TYPE` CHAR(1)   DEFAULT NULL                 COMMENT '1 进 2 出',
  `SYNC_FLAG`   CHAR(1)   NOT NULL DEFAULT '0'         COMMENT '是否已聚合至打卡实绩',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='门禁出入采集记录';

-- 32. 考勤机识别明细
DROP TABLE IF EXISTS `TB_AT_URK_IDENTIFY_REC`;
CREATE TABLE `TB_AT_URK_IDENTIFY_REC` (
  `ID`              BIGINT    NOT NULL                 COMMENT '主键',
  `USER_ID`         BIGINT    NOT NULL                 COMMENT '人员信息',
  `URK_CODE`        CHAR(20)  NOT NULL                 COMMENT '考勤机编码',
  `IDENTIFY_TIME`   DATETIME  NOT NULL                 COMMENT '识别时间',
  `IDENTIFY_RESULT` CHAR(1)   DEFAULT NULL             COMMENT '识别结果：1 成功 2 失败',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='考勤机识别明细';

-- 33. 人员打卡实绩记录表
DROP TABLE IF EXISTS `TB_AT_USER_CLOCK_REC`;
CREATE TABLE `TB_AT_USER_CLOCK_REC` (
  `ID`           BIGINT    NOT NULL                    COMMENT '主键',
  `USER_ID`      BIGINT    NOT NULL                    COMMENT '人员信息',
  `WORK_DATE`    DATE      NOT NULL                    COMMENT '实绩日期',
  `IN_TIME`      DATETIME  DEFAULT NULL                COMMENT '上班打卡时间',
  `IN_URK_CODE`  CHAR(20)  DEFAULT NULL                COMMENT '上班打卡考勤机',
  `OUT_TIME`     DATETIME  DEFAULT NULL                COMMENT '下班打卡时间',
  `OUT_URK_CODE` CHAR(20)  DEFAULT NULL                COMMENT '下班打卡考勤机',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人员打卡实绩记录表';

-- 34. 考勤状态编码表
DROP TABLE IF EXISTS `TB_AT_URK_STATUS_CODE`;
CREATE TABLE `TB_AT_URK_STATUS_CODE` (
  `STATUS_ID`   INT       NOT NULL                     COMMENT '主键',
  `STATUS_CODE` CHAR(4)   NOT NULL                     COMMENT '考勤状态编码',
  `STATUS_NAME` CHAR(20)  NOT NULL                     COMMENT '名称',
  `STATUS_TYPE` CHAR(1)   NOT NULL                     COMMENT '1 正常 2 异常 3 假期',
  `SHOW_COLOR`  CHAR(8)   DEFAULT NULL                 COMMENT '显示色',
  PRIMARY KEY (`STATUS_ID`),
  UNIQUE KEY `UK_STATUS_CODE` (`STATUS_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='考勤状态编码表';

-- 35. 人员应出勤计划表
DROP TABLE IF EXISTS `TB_AT_WORK_PLAN`;
CREATE TABLE `TB_AT_WORK_PLAN` (
  `ID`          BIGINT   NOT NULL                      COMMENT '主键',
  `USER_ID`     BIGINT   NOT NULL                      COMMENT '人员信息',
  `CAL_DATE`    DATE     NOT NULL                      COMMENT '日历外键',
  `ATTEND_TYPE` CHAR(1)  NOT NULL                      COMMENT '出勤类型：1 应出勤 2 应休息',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人员应出勤计划表';

-- 36. 人员实际出勤记录表
DROP TABLE IF EXISTS `TB_AT_WORK_PLAN_ACTUAL`;
CREATE TABLE `TB_AT_WORK_PLAN_ACTUAL` (
  `ID`         BIGINT       NOT NULL                   COMMENT '主键',
  `USER_ID`    BIGINT       NOT NULL                   COMMENT '人员信息',
  `CAL_DATE`   DATE         NOT NULL                   COMMENT '日历外键',
  `STATUS_ID`  INT          NOT NULL                   COMMENT '考勤状态编码（实绩判定结果）',
  `APPLY_ID`   VARCHAR(32)  DEFAULT NULL               COMMENT '关联单据号（假期/补打卡/出差/值班 单号）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人员实际出勤记录表';

-- 37. 人员考勤记录修改履历表
DROP TABLE IF EXISTS `TB_AT_WORK_REC`;
CREATE TABLE `TB_AT_WORK_REC` (
  `ID`              BIGINT       NOT NULL              COMMENT '主键',
  `PLAN_ACTUAL_ID`  BIGINT       NOT NULL              COMMENT '实际出勤记录 ID',
  `CAL_DATE`        DATE         DEFAULT NULL          COMMENT '日历外键',
  `OLD_STATUS_ID`   INT          DEFAULT NULL          COMMENT '旧考勤状态编码',
  `NEW_STATUS_ID`   INT          DEFAULT NULL          COMMENT '新考勤状态编码',
  `ADJUST_REASON`   VARCHAR(200) DEFAULT NULL          COMMENT '调整原因',
  `ADJUST_USER`     VARCHAR(64)  DEFAULT NULL          COMMENT '调整人（部门管理员）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人员考勤记录修改履历表';

-- 38. 补打卡申请表
DROP TABLE IF EXISTS `TB_AT_RECARD_APPLY`;
CREATE TABLE `TB_AT_RECARD_APPLY` (
  `APPLY_NO`           VARCHAR(32)  NOT NULL           COMMENT '申请单号（BKyyyyMMddNNN）',
  `USER_ID`            BIGINT       NOT NULL           COMMENT '申请人',
  `ORGAN_ID`           BIGINT       NOT NULL           COMMENT '部门',
  `WORK_DATE`          DATE         NOT NULL           COMMENT '补卡日期（当月有效，跨月不可补）',
  `CARD_TYPE`          CHAR(1)      NOT NULL           COMMENT '1 上班卡 2 下班卡',
  `REASON`             VARCHAR(500) NOT NULL           COMMENT '补卡原因（现场岗位等）',
  `ACCESS_REC_ID`      BIGINT       DEFAULT NULL       COMMENT '门岗出入记录（作为凭证条件）',
  `MONTH_COUNT`        INT          NOT NULL           COMMENT '当月第 N 次（快照）',
  `APPROVE_LEVEL_PATH` CHAR(1)      NOT NULL           COMMENT '审批路径：A 当月≤2天 B 当月≥3天',
  `APPROVE_STATUS`     CHAR(2)      NOT NULL           COMMENT '审批状态',
  `FLOW_ID`            VARCHAR(32)  DEFAULT NULL       COMMENT '审批流程实例',
  `FINAL_STATUS_ID`    INT          DEFAULT NULL       COMMENT '审批通过后考勤置为：出勤',
  PRIMARY KEY (`APPLY_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='补打卡申请表';

-- 39. 连续作业超时预警表
DROP TABLE IF EXISTS `TB_AT_OVERWORK_WARN`;
CREATE TABLE `TB_AT_OVERWORK_WARN` (
  `ID`               BIGINT       NOT NULL             COMMENT '主键',
  `USER_ID`          BIGINT       NOT NULL             COMMENT '人员',
  `WARN_DATE`        DATE         NOT NULL             COMMENT '预警日期',
  `CONTINUOUS_HOURS` DECIMAL(5,1) NOT NULL             COMMENT '连续作业小时数（>12 触发）',
  `WARN_SOURCE`      CHAR(1)      NOT NULL             COMMENT '来源：1 调班 2 补打卡 3 值班',
  `WARN_TARGET`      VARCHAR(64)  DEFAULT NULL         COMMENT '预警接收人（作业长）',
  `IS_HANDLED`       CHAR(1)      NOT NULL DEFAULT '0' COMMENT '是否已处理',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='连续作业超时预警表';


-- ===========================================
-- 模块 6：假期管理（7 张表）
-- ===========================================

-- 40. 假期类型编码表
DROP TABLE IF EXISTS `TB_AT_VACATION_CODE`;
CREATE TABLE `TB_AT_VACATION_CODE` (
  `VAC_ID`         INT           NOT NULL              COMMENT '主键',
  `VAC_CODE`       CHAR(4)       NOT NULL              COMMENT '假期类型编码',
  `VAC_NAME`       VARCHAR(20)   NOT NULL              COMMENT '假期类型名称',
  `VAC_SYMBOL`     CHAR(2)       NOT NULL              COMMENT '日历符号',
  `ENJOY_TYPE`     CHAR(4)       NOT NULL              COMMENT '享受人员：1 正式 2 劳务 3 正式/劳务',
  `HAS_QUOTA`      CHAR(1)       NOT NULL DEFAULT '0'  COMMENT '是否有余额概念：仅年假=1，其余=0',
  `MIN_UNIT`       DECIMAL(3,1)  NOT NULL              COMMENT '最小计算单位（天）：年假/婚/丧/产=1，事/病=0.5',
  `CAN_SPLIT`      CHAR(1)       NOT NULL DEFAULT '0'  COMMENT '可否分开休假：年假=1，其余=0',
  `APPLY_TIME_RULE` VARCHAR(100) DEFAULT NULL          COMMENT '发起流程时间（当天往后/不限/累计小时）',
  `INCLUDE_LEGAL`  CHAR(1)       NOT NULL DEFAULT '0'  COMMENT '天数是否含法定节假日和公休日',
  PRIMARY KEY (`VAC_ID`),
  UNIQUE KEY `UK_VAC_CODE` (`VAC_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='假期类型编码表';

-- 41. 假期计算规则表
DROP TABLE IF EXISTS `TB_AT_VACATION_RULE`;
CREATE TABLE `TB_AT_VACATION_RULE` (
  `ID`              BIGINT        NOT NULL             COMMENT '主键',
  `VAC_ID`          INT           NOT NULL             COMMENT '假期类型 ID',
  `USER_ATTR`       CHAR(2)       NOT NULL             COMMENT '员工属性：普通员工/中层',
  `ENJOY_TYPE`      CHAR(4)       NOT NULL             COMMENT '享受类型：1 正式 2 劳务 3 正式/劳务',
  `SENIORITY_TYPE`  CHAR(2)       DEFAULT NULL         COMMENT '工龄维度：1 集团工龄 2 参工工龄 3 公司工龄',
  `SENIORITY_MIN`   DECIMAL(5,1)  DEFAULT NULL         COMMENT '工龄下限（年）',
  `SENIORITY_MAX`   DECIMAL(5,1)  DEFAULT NULL         COMMENT '工龄上限（年）',
  `DAYS`            DECIMAL(5,1)  DEFAULT NULL         COMMENT '天数',
  `UNIT`            CHAR(2)       DEFAULT NULL         COMMENT '单位：月/年',
  `FREQUENCY`       VARCHAR(50)   DEFAULT NULL         COMMENT '频率（探亲假：四年一次）',
  `SPOUSE_PLACE`    CHAR(1)       DEFAULT NULL         COMMENT '配偶工作地：1 同地 2 异地（婚/丧假）',
  `DELIVERY_TYPE`   CHAR(2)       DEFAULT NULL         COMMENT '分娩方式：1 顺产 2 难产（产假）',
  `BABY_COUNT`      INT           DEFAULT NULL         COMMENT '生育小孩个数（单胞胎/多胞胎 n）',
  `MULTIPLE_COEF`   DECIMAL(4,1)  DEFAULT NULL         COMMENT '多胞胎系数（如 15，总天数=178+15n）',
  `BASE_DAYS`       DECIMAL(5,1)  DEFAULT NULL         COMMENT '基本天数（产假正常分娩=178）',
  `TIME_MIN`        DECIMAL(5,1)  DEFAULT NULL         COMMENT '时间下限（产假终止妊娠/哺乳假）',
  `TIME_MAX`        DECIMAL(5,1)  DEFAULT NULL         COMMENT '时间上限（工伤假最长 24 个月）',
  `ANNEX_RULE`      VARCHAR(200)  DEFAULT NULL         COMMENT '附件要求（婚假=结婚证、产假=医院证明+准生证、病假=诊断证明）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='假期计算规则表';

-- 42. 年假工龄梯度规则表
DROP TABLE IF EXISTS `TB_AT_ANNUAL_RULE`;
CREATE TABLE `TB_AT_ANNUAL_RULE` (
  `ID`                   BIGINT        NOT NULL        COMMENT '主键',
  `GROUP_SENIORITY_MIN`  DECIMAL(5,1)  NOT NULL        COMMENT '集团工龄下限（≥1 年起享）',
  `WORK_SENIORITY_MIN`   DECIMAL(5,1)  NOT NULL        COMMENT '参工工龄下限',
  `WORK_SENIORITY_MAX`   DECIMAL(5,1)  DEFAULT NULL    COMMENT '参工工龄上限',
  `DAYS`                 INT           NOT NULL        COMMENT '年假天数（如 1≤参工<10 → 5 天）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='年假工龄梯度规则表';

-- 43. 个人假期汇总表
DROP TABLE IF EXISTS `TB_AT_VACATION_STATS`;
CREATE TABLE `TB_AT_VACATION_STATS` (
  `ID`           BIGINT        NOT NULL               COMMENT '主键',
  `USER_ID`      VARCHAR(32)   NOT NULL               COMMENT '人员信息',
  `STAT_YEAR`    CHAR(4)       NOT NULL               COMMENT '日期（年度）',
  `VAC_ID`       INT           NOT NULL               COMMENT '假期类型编码（仅 HAS_QUOTA=1 的假种）',
  `SHOULD_DAYS`  DECIMAL(5,1)  NOT NULL               COMMENT '假期应有天数',
  `USED_DAYS`    DECIMAL(5,1)  NOT NULL               COMMENT '已用天数',
  `LEFT_DAYS`    DECIMAL(5,1)  NOT NULL               COMMENT '剩余天数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='个人假期汇总表';

-- 44. 个人假期明细表
DROP TABLE IF EXISTS `TB_AT_VACATION_DETAIL`;
CREATE TABLE `TB_AT_VACATION_DETAIL` (
  `ID`           BIGINT        NOT NULL               COMMENT '主键',
  `USER_ID`      VARCHAR(32)   NOT NULL               COMMENT '人员信息',
  `VAC_ID`       INT           NOT NULL               COMMENT '假期类型编码',
  `VALID_FROM`   DATE          NOT NULL               COMMENT '假期有效开始日期',
  `VALID_TO`     DATE          NOT NULL               COMMENT '假期有效结束日期',
  `SHOULD_DAYS`  DECIMAL(5,1)  DEFAULT NULL           COMMENT '假期应有天数',
  `USED_DAYS`    DECIMAL(5,1)  DEFAULT NULL           COMMENT '已用天数',
  `LEFT_DAYS`    DECIMAL(5,1)  DEFAULT NULL           COMMENT '剩余天数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='个人假期明细表';

-- 45. 假期申请记录表
DROP TABLE IF EXISTS `TB_AT_VACATION_APPLY`;
CREATE TABLE `TB_AT_VACATION_APPLY` (
  `APPLY_NO`       VARCHAR(32)   NOT NULL              COMMENT '申请单号（QJyyyyMMddNNN）',
  `USER_ID`        BIGINT        NOT NULL              COMMENT '申请人',
  `ORGAN_ID`       BIGINT        NOT NULL              COMMENT '申请时部门（流程匹配入参）',
  `VAC_ID`         INT           NOT NULL              COMMENT '假期类型编码',
  `START_DATE`     DATETIME      NOT NULL              COMMENT '开始日期',
  `END_DATE`       DATETIME      NOT NULL              COMMENT '结束日期',
  `LEAVE_DAYS`     DECIMAL(5,1)  NOT NULL              COMMENT '休假天数（系统按起止日期自动计算，最小单位按假种）',
  `REASON`         VARCHAR(500)  DEFAULT NULL          COMMENT '休假原因',
  `APPROVE_STATUS` CHAR(2)       NOT NULL              COMMENT '审批状态：0 草稿 1 审批中 2 已通过 3 已退回 4 已撤销 5 已销假',
  `CURRENT_LEVEL`  INT           DEFAULT NULL          COMMENT '当前审批级（个人端仅显示此值，如"二级审批中"）',
  `TOTAL_LEVEL`    INT           DEFAULT NULL          COMMENT '总级数（个人端仅显示"已通过 N/M 级"）',
  `FLOW_ID`        VARCHAR(32)   DEFAULT NULL          COMMENT '审批流程实例',
  `RETURN_TIME`    DATETIME      DEFAULT NULL          COMMENT '销假时间（提前结束/到期自动生成）',
  `ACTUAL_DAYS`    DECIMAL(5,1)  DEFAULT NULL          COMMENT '实际休假天数（按请假+销假时间计算）',
  `FINISH_NOTE`    VARCHAR(200)  DEFAULT NULL          COMMENT '销假备注',
  `IS_EARLY_END`   CHAR(1)       DEFAULT NULL          COMMENT '是否提前结束（在原申请单上操作）',
  PRIMARY KEY (`APPLY_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='假期申请记录表';

-- 46. 假期申请附件表
DROP TABLE IF EXISTS `TB_AT_VACATION_ANNEX`;
CREATE TABLE `TB_AT_VACATION_ANNEX` (
  `ID`          BIGINT       NOT NULL                  COMMENT '主键',
  `APPLY_NO`    VARCHAR(32)  DEFAULT NULL              COMMENT '假期申请记录 ID',
  `ANNEX_URL`   VARCHAR(500) NOT NULL                  COMMENT '附件地址',
  `ANNEX_NAME`  VARCHAR(200) DEFAULT NULL              COMMENT '附件名称',
  `UPLOAD_TIME` DATETIME     DEFAULT NULL              COMMENT '上传时间（支持审批后补传）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='假期申请附件表';


-- ===========================================
-- 模块 7：加班与调休（5 张表）
-- ===========================================

-- 47. 加班申请记录表
DROP TABLE IF EXISTS `TB_AT_OVERTIME_REC`;
CREATE TABLE `TB_AT_OVERTIME_REC` (
  `APPLY_NO`       VARCHAR(32)   NOT NULL              COMMENT '申请单号（JByyyyMMddNNN）',
  `ORGAN_ID`       BIGINT        NOT NULL              COMMENT '内部管理组织架构编码',
  `APPLY_USER_ID`  BIGINT        NOT NULL              COMMENT '代申请人（直属上级）',
  `START_TIME`     DATETIME      NOT NULL              COMMENT '开始时间',
  `END_TIME`       DATETIME      NOT NULL              COMMENT '结束时间',
  `REASON`         VARCHAR(500)  DEFAULT NULL          COMMENT '加班原因',
  `APPLY_SHIFT_NUM` INT          DEFAULT NULL          COMMENT '申请加班班数（个）',
  `APPLY_HOURS`    DECIMAL(6,1)  DEFAULT NULL          COMMENT '申请加班时长（H）',
  `APPROVE_STATUS` CHAR(2)       NOT NULL              COMMENT '审批状态',
  `CURRENT_LEVEL`  INT           DEFAULT NULL          COMMENT '当前审批级（个人端仅显示级数）',
  `FLOW_ID`        VARCHAR(32)   DEFAULT NULL          COMMENT '审批流程实例',
  PRIMARY KEY (`APPLY_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='加班申请记录表';

-- 48. 加班申请明细表
DROP TABLE IF EXISTS `TB_AT_OVERTIME_DETAIL_REC`;
CREATE TABLE `TB_AT_OVERTIME_DETAIL_REC` (
  `ID`               BIGINT       NOT NULL             COMMENT '主键',
  `APPLY_NO`         VARCHAR(32)  DEFAULT NULL         COMMENT '加班申请单 ID',
  `ORGAN_ID`         BIGINT       DEFAULT NULL         COMMENT '内部管理组织架构编码',
  `JOB_ID`           BIGINT       DEFAULT NULL         COMMENT '内部管理岗位编码',
  `USER_ID`          BIGINT       NOT NULL             COMMENT '加班人员',
  `ACTUAL_SHIFT_NUM` INT          DEFAULT NULL         COMMENT '实际加班班数（个）',
  `ACTUAL_HOURS`     DECIMAL(6,1) DEFAULT NULL         COMMENT '实际加班时长（H）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='加班申请明细表';

-- 49. 加班换休时效天数维护
DROP TABLE IF EXISTS `TB_AT_OVERTIME_DAT_CODE`;
CREATE TABLE `TB_AT_OVERTIME_DAT_CODE` (
  `ID`        BIGINT  NOT NULL                         COMMENT '主键',
  `EXEC_FROM` DATE    NOT NULL                         COMMENT '开始执行日期',
  `EXEC_TO`   DATE    DEFAULT NULL                     COMMENT '结束执行日期',
  `IN_USE`    CHAR(1) NOT NULL DEFAULT '1'             COMMENT '在用状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='加班换休时效天数维护';

-- 50. 调休申请记录表
DROP TABLE IF EXISTS `TB_AT_PAY_OVERTIME_INFO`;
CREATE TABLE `TB_AT_PAY_OVERTIME_INFO` (
  `APPLY_NO`       VARCHAR(32)   NOT NULL              COMMENT '申请单号（TXyyyyMMddNNN）',
  `ORGAN_ID`       BIGINT        DEFAULT NULL          COMMENT '内部管理组织架构编码',
  `JOB_ID`         BIGINT        DEFAULT NULL          COMMENT '内部管理岗位编码',
  `USER_ID`        BIGINT        NOT NULL              COMMENT '人员信息',
  `START_DATE`     DATETIME      NOT NULL              COMMENT '开始日期',
  `END_DATE`       DATETIME      NOT NULL              COMMENT '结束日期',
  `REST_HOURS`     DECIMAL(6,1)  NOT NULL              COMMENT '调休时长（H）',
  `APPROVE_STATUS` CHAR(2)       NOT NULL              COMMENT '审批状态',
  `FLOW_ID`        VARCHAR(32)   DEFAULT NULL          COMMENT '审批流程实例',
  PRIMARY KEY (`APPLY_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='调休申请记录表';

-- 51. 加班调休统计表
DROP TABLE IF EXISTS `TB_AT_PAY_OVERTIME_STATS`;
CREATE TABLE `TB_AT_PAY_OVERTIME_STATS` (
  `ID`               BIGINT        NOT NULL            COMMENT '主键',
  `USER_ID`          BIGINT        NOT NULL            COMMENT '人员外键',
  `STAT_MONTH`       CHAR(6)       NOT NULL            COMMENT '日期（月度）',
  `TOTAL_OT_HOURS`   DECIMAL(7,1)  DEFAULT NULL        COMMENT '总加班时长',
  `TOTAL_REST_HOURS` DECIMAL(7,1)  DEFAULT NULL        COMMENT '总调休时长',
  `ALLOW_REST_HOURS` DECIMAL(7,1)  DEFAULT NULL        COMMENT '允许调休时长（换休时效内）',
  `LEFT_REST_HOURS`  DECIMAL(7,1)  DEFAULT NULL        COMMENT '剩余调休时长',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='加班调休统计表';


-- ===========================================
-- 模块 8：值班管理（3 张表）
-- ===========================================

-- 52. 值班计划表（导入）
DROP TABLE IF EXISTS `TB_AT_DUTY_PLAN`;
CREATE TABLE `TB_AT_DUTY_PLAN` (
  `ID`           BIGINT       NOT NULL                 COMMENT '主键',
  `IMPORT_BATCH` VARCHAR(32)  NOT NULL                 COMMENT '导入批次号（ZB-IMP-yyyyMMdd-NN），手工录入为 MANUAL',
  `USER_ID`      BIGINT       NOT NULL                 COMMENT '值班人员',
  `ORGAN_ID`     BIGINT       NOT NULL                 COMMENT '所属部门',
  `PLAN_MONTH`   CHAR(6)      NOT NULL                 COMMENT '归属月度（yyyyMM）',
  `DUTY_DATE`    DATE         NOT NULL                 COMMENT '值班日期',
  `START_TIME`   CHAR(5)      NOT NULL                 COMMENT '开始时间（如 08:00）',
  `END_TIME`     CHAR(5)      NOT NULL                 COMMENT '结束时间（可跨天，如 19:00→次日08:00）',
  `ACROSS_DAY`   CHAR(1)      NOT NULL DEFAULT '0'     COMMENT '是否跨天',
  `DUTY_HOURS`   DECIMAL(6,1) NOT NULL                 COMMENT '值班时长（H，导入时由起止时段自动计算）',
  `DUTY_TYPE`    CHAR(2)      NOT NULL                 COMMENT '值班类型：1 生产值班 2 设备值班 3 应急值班 4 安全值班',
  `DUTY_CONTENT` VARCHAR(500) DEFAULT NULL             COMMENT '工作内容 / 值守职责',
  `DUTY_STATUS`  CHAR(1)      NOT NULL DEFAULT '1'     COMMENT '上岗状态：1 待上岗 2 已上岗 3 已完成',
  `SOURCE`       CHAR(1)      NOT NULL                 COMMENT '来源：1 Excel 导入 2 手工录入',
  `DATA_STATUS`  CHAR(1)      NOT NULL DEFAULT '1'     COMMENT '数据状态：1 有效 0 已作废',
  `STATS_FLAG`   CHAR(1)      NOT NULL DEFAULT '0'     COMMENT '是否已计入年度统计',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='值班计划表（导入）';

-- 53. 值班表导入批次表
DROP TABLE IF EXISTS `TB_AT_DUTY_IMPORT_LOG`;
CREATE TABLE `TB_AT_DUTY_IMPORT_LOG` (
  `ID`             BIGINT       NOT NULL               COMMENT '主键',
  `IMPORT_BATCH`   VARCHAR(32)  NOT NULL               COMMENT '批次号（ZB-IMP-yyyyMMdd-NN）',
  `FILE_NAME`      VARCHAR(200) NOT NULL               COMMENT '原始文件名',
  `FILE_URL`       VARCHAR(500) DEFAULT NULL           COMMENT '文件存储地址',
  `PLAN_MONTH`     CHAR(6)      NOT NULL               COMMENT '导入月份',
  `ORGAN_ID`       BIGINT       DEFAULT NULL           COMMENT '适用范围（空=全公司）',
  `TOTAL_ROWS`     INT          NOT NULL               COMMENT '文件总行数',
  `SUCCESS_ROWS`   INT          NOT NULL               COMMENT '成功条数',
  `FAIL_ROWS`      INT          NOT NULL               COMMENT '失败条数（错误明细另存日志附件）',
  `IMPORT_MODE`    CHAR(1)      NOT NULL               COMMENT '导入方式：1 整月覆盖（同月旧批次作废） 2 追加',
  `IMPORT_USER_ID` BIGINT       NOT NULL               COMMENT '导入人（HR/调度）',
  `IMPORT_TIME`    DATETIME     NOT NULL               COMMENT '导入时间',
  `ROLLBACK_FLAG`  CHAR(1)      NOT NULL DEFAULT '0'   COMMENT '是否已回滚',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_IMPORT_BATCH` (`IMPORT_BATCH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='值班表导入批次表';

-- 54. 值班年度统计表
DROP TABLE IF EXISTS `TB_AT_DUTY_STATS`;
CREATE TABLE `TB_AT_DUTY_STATS` (
  `ID`          BIGINT        NOT NULL                 COMMENT '主键',
  `USER_ID`     BIGINT        NOT NULL                 COMMENT '人员',
  `STAT_YEAR`   CHAR(4)       NOT NULL                 COMMENT '统计年度',
  `TOTAL_HOURS` DECIMAL(7,1)  NOT NULL                 COMMENT '全年值班小时数（自动累计）',
  `DUTY_COUNT`  INT           DEFAULT NULL             COMMENT '值班次数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='值班年度统计表';


-- ===========================================
-- 模块 9：审批流程（4 张表）
-- ===========================================

-- 55. 审批流程信息表
DROP TABLE IF EXISTS `TB_APPROVAL_FLOW_INFO`;
CREATE TABLE `TB_APPROVAL_FLOW_INFO` (
  `FLOW_ID`          VARCHAR(32)  NOT NULL             COMMENT '流程实例 ID',
  `BIZ_TYPE`         CHAR(2)      NOT NULL             COMMENT '单据类型：1 请假 2 补打卡 3 加班 4 调休 5 出差',
  `BIZ_NO`           VARCHAR(32)  NOT NULL             COMMENT '业务单号',
  `MATCH_ORGAN_ID`   BIGINT       DEFAULT NULL         COMMENT '匹配部门（自动匹配入参）',
  `MATCH_VAC_ID`     INT          DEFAULT NULL         COMMENT '匹配假别（自动匹配入参）',
  `MATCH_USER_ATTR`  CHAR(2)      DEFAULT NULL         COMMENT '匹配人员属性（自动匹配入参）',
  `FLOW_STATUS`      CHAR(2)      NOT NULL             COMMENT '流程状态：1 进行中 2 已通过 3 已退回 4 已撤销',
  PRIMARY KEY (`FLOW_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='审批流程信息表';

-- 56. 审批流程节点信息表
DROP TABLE IF EXISTS `TB_APPROVAL_NODE_INFO`;
CREATE TABLE `TB_APPROVAL_NODE_INFO` (
  `NODE_ID`     BIGINT       NOT NULL                  COMMENT '主键',
  `FLOW_ID`     VARCHAR(32)  DEFAULT NULL              COMMENT '流程实例',
  `LEVEL_NO`    INT          NOT NULL                  COMMENT '审批级别（一级~七级）',
  `APPROVER_ID` BIGINT       NOT NULL                  COMMENT '审批人',
  `NODE_STATUS` CHAR(1)      NOT NULL                  COMMENT '1 完成(done) 2 进行中(doing) 3 等待(wait) 4 跳过(skip)',
  `OP_RESULT`   CHAR(1)      DEFAULT NULL              COMMENT '操作结果：1 同意 2 退回',
  `OP_TIME`     DATETIME     DEFAULT NULL              COMMENT '审批时间',
  `OPINION`     VARCHAR(500) DEFAULT NULL              COMMENT '审批意见',
  PRIMARY KEY (`NODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='审批流程节点信息表';

-- 57. 审批任务表
DROP TABLE IF EXISTS `TB_APPROVAL_TASK`;
CREATE TABLE `TB_APPROVAL_TASK` (
  `TASK_ID`      BIGINT       NOT NULL                 COMMENT '主键',
  `FLOW_ID`      VARCHAR(32)  DEFAULT NULL             COMMENT '流程实例',
  `NODE_ID`      BIGINT       NOT NULL                 COMMENT '当前节点',
  `APPROVER_ID`  BIGINT       NOT NULL                 COMMENT '待办审批人',
  `TASK_STATUS`  CHAR(1)      NOT NULL                 COMMENT '1 待处理 2 已同意 3 已退回 4 已失效',
  `TASK_TYPE`    CHAR(1)      NOT NULL                 COMMENT '1 审批 2 代申请确认',
  `CC_USER_IDS`  VARCHAR(500) DEFAULT NULL             COMMENT '抄送人（冗余显示）',
  `CREATE_TIME`  DATETIME     NOT NULL                 COMMENT '到达时间',
  `FINISH_TIME`  DATETIME     DEFAULT NULL             COMMENT '完成时间',
  PRIMARY KEY (`TASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='审批任务表';

-- 58. 抄送记录表
DROP TABLE IF EXISTS `TB_APPROVAL_CC`;
CREATE TABLE `TB_APPROVAL_CC` (
  `ID`           BIGINT       NOT NULL                 COMMENT '主键',
  `FLOW_ID`      VARCHAR(32)  DEFAULT NULL             COMMENT '流程实例',
  `BIZ_NO`       VARCHAR(32)  NOT NULL                 COMMENT '业务单号',
  `FROM_USER_ID` BIGINT       NOT NULL                 COMMENT '抄送发起人（审批人）',
  `TO_USER_ID`   BIGINT       NOT NULL                 COMMENT '抄送接收人',
  `IS_READ`      CHAR(1)      NOT NULL DEFAULT '0'     COMMENT '已读状态',
  `CC_TIME`      DATETIME     NOT NULL                 COMMENT '抄送时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='抄送记录表';


-- ===========================================
-- 模块 10：考勤统计（1 张表）
-- ===========================================

-- 59. 月度考勤统计表
DROP TABLE IF EXISTS `TB_AT_REPORT_MONTHLY`;
CREATE TABLE `TB_AT_REPORT_MONTHLY` (
  `ID`               BIGINT        NOT NULL            COMMENT '主键',
  `USER_ID`          BIGINT        NOT NULL            COMMENT '人员',
  `ORGAN_ID`         BIGINT        NOT NULL            COMMENT '部门（报表筛选维度）',
  `EMPLOYEE_TYPE`    CHAR(2)       NOT NULL            COMMENT '用工类型（报表筛选维度）',
  `STAT_MONTH`       CHAR(6)       NOT NULL            COMMENT '统计月度',
  `SHOULD_DAYS`      DECIMAL(5,1)  DEFAULT NULL        COMMENT '应出勤天数',
  `ACTUAL_DAYS`      DECIMAL(5,1)  DEFAULT NULL        COMMENT '实际出勤天数',
  `LATE_TIMES`       INT           DEFAULT NULL        COMMENT '迟到次数',
  `EARLY_TIMES`      INT           DEFAULT NULL        COMMENT '早退次数',
  `ABSENT_DAYS`      DECIMAL(5,1)  DEFAULT NULL        COMMENT '旷工天数',
  `OT_HOURS`         DECIMAL(7,1)  DEFAULT NULL        COMMENT '加班小时',
  `DUTY_HOURS`       DECIMAL(7,1)  DEFAULT NULL        COMMENT '值班小时',
  `LEAVE_DETAIL_JSON` TEXT         DEFAULT NULL        COMMENT '各假别明细（JSON：{"N":3,"S":1,...}）',
  `ATTEND_RATE`      DECIMAL(5,2)  DEFAULT NULL        COMMENT '出勤率(%)',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='月度考勤统计表';


SET FOREIGN_KEY_CHECKS = 1;

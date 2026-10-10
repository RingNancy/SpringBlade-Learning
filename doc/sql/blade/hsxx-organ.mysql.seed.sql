-- =============================================
-- 组织架构模拟数据（按真实二级结构）
-- 黄石新兴管业有限公司 + 13 个二级部门（2026-10 组织架构图）
-- ORGAN_ID 2001~2005 与 tb_g2_user 既有测试数据引用保持兼容
-- =============================================

USE blade;

SET NAMES utf8mb4;

-- 建表（与 doc/sql/hsxx-attendance.mysql.create.sql 第 10/11 表一致）
CREATE TABLE IF NOT EXISTS `TB_G2_ORGAN` (
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

CREATE TABLE IF NOT EXISTS `TB_G2_ORGAN_REC` (
  `REC_ID`       BIGINT        NOT NULL                COMMENT '主键',
  `ORGAN_ID`     BIGINT        NOT NULL                COMMENT '组织架构 ID',
  `SNAP_JSON`    TEXT          DEFAULT NULL            COMMENT '变更前快照',
  `MODIFY_TIME`  DATETIME      DEFAULT NULL            COMMENT '修改时间',
  `MODIFY_USER`  VARCHAR(64)   DEFAULT NULL            COMMENT '修改人信息',
  PRIMARY KEY (`REC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人力组织架构履历表';

-- 组织数据：根节点 + 13 个二级部门
DELETE FROM `TB_G2_ORGAN` WHERE `ORGAN_ID` BETWEEN 2000 AND 2099;
INSERT INTO `TB_G2_ORGAN`
(`ORGAN_ID`, `COMPANY_CODE`, `COMPANY_NAME`, `DEPT_CODE`, `DEPT_NAME`, `LEVEL_NO`, `PARENT_CODE`, `SECOND_DEPT`, `DEPT_FULL_NAME`, `VALID_FLAG`, `USE_FLAG`) VALUES
(2000, 'HSXX', '黄石新兴管业有限公司', 'HSXX',   '黄石新兴管业有限公司', 1, NULL,    NULL,     '黄石新兴管业有限公司',             '1', '1'),
(2001, 'HSXX', '黄石新兴管业有限公司', 'HSXX01', '公司领导',             2, 'HSXX',  'HSXX01', '黄石新兴管业有限公司-公司领导',     '1', '1'),
(2002, 'HSXX', '黄石新兴管业有限公司', 'HSXX02', '党群工作部',           2, 'HSXX',  'HSXX02', '黄石新兴管业有限公司-党群工作部',   '1', '1'),
(2003, 'HSXX', '黄石新兴管业有限公司', 'HSXX03', '监督执纪室',           2, 'HSXX',  'HSXX03', '黄石新兴管业有限公司-监督执纪室',   '1', '1'),
(2004, 'HSXX', '黄石新兴管业有限公司', 'HSXX04', '安全监督部',           2, 'HSXX',  'HSXX04', '黄石新兴管业有限公司-安全监督部',   '1', '1'),
(2005, 'HSXX', '黄石新兴管业有限公司', 'HSXX05', '环保能源部',           2, 'HSXX',  'HSXX05', '黄石新兴管业有限公司-环保能源部',   '1', '1'),
(2006, 'HSXX', '黄石新兴管业有限公司', 'HSXX06', '生产管理部',           2, 'HSXX',  'HSXX06', '黄石新兴管业有限公司-生产管理部',   '1', '1'),
(2007, 'HSXX', '黄石新兴管业有限公司', 'HSXX07', '联动部',               2, 'HSXX',  'HSXX07', '黄石新兴管业有限公司-联动部',       '1', '1'),
(2008, 'HSXX', '黄石新兴管业有限公司', 'HSXX08', '经营财务部',           2, 'HSXX',  'HSXX08', '黄石新兴管业有限公司-经营财务部',   '1', '1'),
(2009, 'HSXX', '黄石新兴管业有限公司', 'HSXX09', '数字研发部',           2, 'HSXX',  'HSXX09', '黄石新兴管业有限公司-数字研发部',   '1', '1'),
(2010, 'HSXX', '黄石新兴管业有限公司', 'HSXX10', '炼铁部',               2, 'HSXX',  'HSXX10', '黄石新兴管业有限公司-炼铁部',       '1', '1'),
(2011, 'HSXX', '黄石新兴管业有限公司', 'HSXX11', '动力部',               2, 'HSXX',  'HSXX11', '黄石新兴管业有限公司-动力部',       '1', '1'),
(2012, 'HSXX', '黄石新兴管业有限公司', 'HSXX12', '铸管部',               2, 'HSXX',  'HSXX12', '黄石新兴管业有限公司-铸管部',       '1', '1'),
(2013, 'HSXX', '黄石新兴管业有限公司', 'HSXX13', '铸造部',               2, 'HSXX',  'HSXX13', '黄石新兴管业有限公司-铸造部',       '1', '1');

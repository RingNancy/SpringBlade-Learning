-- =============================================
-- 考勤模块字典种子数据（模拟期）
-- 与 doc/sql/hsxx-attendance.mysql.create.sql 配套使用
-- 状态 ID 与 AttendanceConstants.java 中的常量一一对应，勿随意改动
-- =============================================

USE blade;

SET NAMES utf8mb4;

-- 班次类型编码表
INSERT INTO `TB_AT_CLASS_CODE` (`CLASS_ID`, `CLASS_CODE`, `CLASS_NAME`, `CLASS_SYMBOL`, `COLOR_FLAG`) VALUES
(1, 'CB', '常白班',   '白', '#409EFF'),
(2, 'BD', '白班倒班', '白', '#67C23A'),
(3, 'Y12', '12H夜',  '夜', '#909399'),
(4, 'YD', '夜班倒班', '夜', '#8B6FDE'),
(5, 'XU', '休息',     '休', '#C0C4CC');

-- 上下班时间配置表（夜班 ACROSS_DAY=1：窗口为当日+次日，考勤划归当日）
INSERT INTO `TB_AT_WORK_TIME_CONF` (`ID`, `CLASS_ID`, `START_TIME`, `END_TIME`, `ACROSS_DAY`, `STD_HOURS`, `IS_VALID`) VALUES
(1, 1, '08:00', '17:00', '0', 8.0,  '1'),
(2, 2, '08:00', '19:00', '0', 11.0, '1'),
(3, 3, '19:00', '08:00', '1', 12.0, '1'),
(4, 4, '20:00', '08:00', '1', 11.0, '1');

-- 考勤状态编码表（STATUS_ID 与代码常量对应；STATUS_TYPE：1 正常 2 异常 3 假期 4 审批中）
INSERT INTO `TB_AT_URK_STATUS_CODE` (`STATUS_ID`, `STATUS_CODE`, `STATUS_NAME`, `STATUS_TYPE`, `SHOW_COLOR`) VALUES
(1,  'CQ',  '出勤',     '1', '绿'),
(2,  'XX',  '休息',     '1', '绿'),
(3,  'TX',  '调休',     '1', '绿'),
(4,  'JB',  '加班',     '1', '绿'),
(5,  'KG',  '旷工',     '2', '红'),
(6,  'CD',  '迟到',     '2', '红'),
(7,  'ZT',  '早退',     '2', '红'),
(8,  'CDZT', '迟到早退', '2', '红'),
(9,  'SJ',  '事假',     '3', '绿'),
(10, 'BJ',  '病假',     '3', '绿'),
(11, 'NJ',  '年假',     '3', '绿'),
(12, 'HJ',  '婚假',     '3', '绿'),
(13, 'CHJ', '产假',     '3', '绿'),
(15, 'SPZ', '假期审批中', '4', '橙');

-- 假期类型编码表（VAC_ID 与代码 VAC_STATUS_MAP 对应）
INSERT INTO `TB_AT_VACATION_CODE` (`VAC_ID`, `VAC_CODE`, `VAC_NAME`, `VAC_SYMBOL`) VALUES
(1, 'SJ',  '事假', '事'),
(2, 'BJ',  '病假', '病'),
(3, 'NJ',  '年假', '年'),
(4, 'HJ',  '婚假', '婚'),
(5, 'CHJ', '产假', '产');

-- 模拟考勤机编码表
INSERT INTO `TB_AT_URK_CODE` (`URK_ID`, `URK_CODE`, `URK_NAME`, `POSITION`, `URK_TYPE`) VALUES
(1, 'URK-EAST-01', '东门闸机', '厂区东门', '1'),
(2, 'URK-WEST-02', '西门闸机', '厂区西门', '1');

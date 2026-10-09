package com.hsxx.attendance.constant;

import java.util.Map;

/**
 * 考勤业务常量：班次、考勤状态、审批状态等编码约定
 *
 * <p>状态 ID 对应 doc/sql/hsxx-attendance.mysql.seed.sql 中 TB_AT_URK_STATUS_CODE 的字典数据</p>
 *
 * @author Chill
 */
public interface AttendanceConstants {

	/* ---- 班次类型 TB_AT_CLASS_CODE.CLASS_ID ---- */
	Integer CLASS_REGULAR_DAY = 1;
	Integer CLASS_DAY_SHIFT = 2;
	Integer CLASS_NIGHT_12H = 3;
	Integer CLASS_NIGHT_SHIFT = 4;
	Integer CLASS_REST = 5;

	/* ---- 出勤类型 TB_AT_USER_SCHEDULE.ATTEND_TYPE ---- */
	String ATTEND_TYPE_WORK = "1";
	String ATTEND_TYPE_REST = "2";

	/* ---- 是否跨天 TB_AT_WORK_TIME_CONF.ACROSS_DAY ---- */
	String ACROSS_DAY_YES = "1";

	/* ---- 假期审批状态 TB_AT_VACATION_APPLY.APPROVE_STATUS ---- */
	String APPROVE_PENDING = "1";
	String APPROVE_PASSED = "2";

	/* ---- 考勤状态 TB_AT_URK_STATUS_CODE.STATUS_ID ---- */
	int STATUS_ATTEND = 1;
	int STATUS_REST = 2;
	int STATUS_COMP_OFF = 3;
	int STATUS_OVERTIME = 4;
	int STATUS_ABSENT = 5;
	int STATUS_LATE = 6;
	int STATUS_EARLY = 7;
	int STATUS_LATE_EARLY = 8;
	/** 假期状态 9~13：事假/病假/年假/婚假/产假 */
	int STATUS_VAC_FIRST = 9;
	int STATUS_VAC_LAST = 13;
	/** 假期审批中 */
	int STATUS_VAC_APPROVING = 15;

	/* ---- 状态分类 TB_AT_URK_STATUS_CODE.STATUS_TYPE ---- */
	String STATUS_TYPE_NORMAL = "1";
	String STATUS_TYPE_ABNORMAL = "2";
	String STATUS_TYPE_VACATION = "3";
	String STATUS_TYPE_APPROVING = "4";

	/** 假期类型 ID → 已批假期考勤状态 ID 的映射（事假/病假/年假/婚假/产假） */
	Map<Integer, Integer> VAC_STATUS_MAP = Map.of(
		1, STATUS_VAC_FIRST,
		2, STATUS_VAC_FIRST + 1,
		3, STATUS_VAC_FIRST + 2,
		4, STATUS_VAC_FIRST + 3,
		5, STATUS_VAC_FIRST + 4
	);

	/** 假期类型 ID → 状态名称（用于实绩展示） */
	Map<Integer, String> VAC_NAME_MAP = Map.of(
		1, "事假", 2, "病假", 3, "年假", 4, "婚假", 5, "产假"
	);

	/** 模拟用考勤机编码 */
	String[] SIM_URK_CODES = {"URK-EAST-01", "URK-WEST-02"};

}

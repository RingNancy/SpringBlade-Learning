package com.hsxx.attendance.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 门禁刷脸事件数据传输对象
 *
 * <p>模拟 ISC 订阅推送的事件报文，也作为补打卡凭证的原始来源</p>
 *
 * @author Chill
 */
@Data
@Schema(description = "门禁刷脸事件")
public class AccessEventDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "员工主键")
	private Long userId;

	@Schema(description = "考勤机编码（门禁闸机）")
	private String urkCode;

	@Schema(description = "刷脸时间")
	private Date accessTime;

	@Schema(description = "出入类型：1 进 2 出")
	private String accessType;

}

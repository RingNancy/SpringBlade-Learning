package com.hsxx.attendance;

import org.springblade.core.cloud.client.BladeCloudApplication;
import org.springblade.core.launch.BladeApplication;

/**
 * 考勤启动器
 *
 * @author Chill
 */
@BladeCloudApplication
public class AttendanceApplication {

	public static void main(String[] args) {
		BladeApplication.run("blade-attendance", AttendanceApplication.class, args);
	}

}

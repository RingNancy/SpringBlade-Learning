package com.hsxx.organ;

import org.springblade.core.cloud.client.BladeCloudApplication;
import org.springblade.core.launch.BladeApplication;

/**
 * 组织架构启动器
 *
 * @author Chill
 */
@BladeCloudApplication
public class OrganApplication {

	public static void main(String[] args) {
		BladeApplication.run("blade-organ", OrganApplication.class, args);
	}

}

package com.hsxx.personnel;


import org.springblade.core.cloud.client.BladeCloudApplication;
import org.springblade.core.launch.BladeApplication;


@BladeCloudApplication
public class PersonnelApplication {
	public static void main(String[] args) {
		BladeApplication.run("blade-personnel", PersonnelApplication.class, args);
	}
}

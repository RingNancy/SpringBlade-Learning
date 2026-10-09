package com.hsxx.attendance.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 配置 feign、mybatis 包名与定时任务
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@EnableScheduling
@EnableFeignClients({"org.springblade", "com.hsxx.attendance", "com.hsxx.personnel"})
@MapperScan({"org.springblade.**.mapper.**", "com.hsxx.attendance.**.mapper.**"})
public class AttendanceConfiguration {

}

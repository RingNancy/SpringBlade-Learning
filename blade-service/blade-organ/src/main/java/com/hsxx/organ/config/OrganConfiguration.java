package com.hsxx.organ.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 配置 feign、mybatis 包名
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@EnableFeignClients({"org.springblade", "com.hsxx.organ"})
@MapperScan({"org.springblade.**.mapper.**", "com.hsxx.organ.**.mapper.**"})
public class OrganConfiguration {

}

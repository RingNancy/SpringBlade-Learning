/**
 * Copyright (c) 2018-2099, Chill Zhuang 庄骞 (bladejava@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hsxx.attendance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.IService;
import com.hsxx.attendance.pojo.entity.TbG2User;

/**
 * 人员基础信息表（同步人力） 服务类
 *
 * @author Chill
 */
public interface TbG2UserService extends IService<TbG2User> {

	/**
	 * 获取员工基本信息
	 * <p>
	 * 传入工号时按工号查询，否则按员工主键查询，两者至少传入一个
	 *
	 * @param userId   员工主键
	 * @param userCode 工号
	 * @return 员工基础信息，未查询到返回 null
	 */
	TbG2User getEmployeeBasicInfo(Long userId, String userCode);

	/**
	 * 员工信息分页
	 * <p>
	 * 关键字对姓名与工号做模糊匹配；用工类型、人员属性、所属内部管理组织按等值过滤
	 *
	 * @param pages    分页参数
	 * @param employee 等值过滤条件
	 * @param keyword  关键字：姓名或工号
	 * @return 员工分页数据
	 */
	IPage<TbG2User> selectEmployeePage(IPage<TbG2User> pages, TbG2User employee, String keyword);

	/**
	 * 新增员工基础信息
	 * <p>
	 * 工号唯一，主键为空时由系统生成；人力同步字段与工龄等系统自算字段不接受调用方传入
	 *
	 * @param employee 员工基础信息
	 * @return 是否新增成功
	 */
	boolean saveEmployee(TbG2User employee);

}

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
package com.hsxx.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.hsxx.personnel.enums.EmployeeTypeEnum;
import com.hsxx.personnel.enums.OnJobStatusEnum;
import com.hsxx.personnel.enums.ResidenceTypeEnum;
import com.hsxx.personnel.enums.UserAttrEnum;
import com.hsxx.personnel.mapper.TbG2UserMapper;
import com.hsxx.personnel.pojo.entity.TbG2User;
import com.hsxx.personnel.service.TbG2UserService;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 人员基础信息表（同步人力） 服务实现类
 *
 * @author Chill
 */
@Service
public class TbG2UserServiceImpl extends ServiceImpl<TbG2UserMapper, TbG2User> implements TbG2UserService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveEmployee(TbG2User employee) {
		validEmployee(employee);
		// 工号在库中唯一（对应 uk_user_code），先做业务校验，避免依赖数据库唯一键报错
		long existCount = count(Wrappers.<TbG2User>lambdaQuery().eq(TbG2User::getUserCode, employee.getUserCode()));
		if (existCount > 0) {
			throw new ServiceException("工号已存在：" + employee.getUserCode());
		}
		// 人力同步字段与系统自算字段由服务端接管，一律忽略调用方传入值
		employee.setMobileSync(null);
		employee.setSyncTime(null);
		employee.setGroupSeniority(null);
		employee.setCompanySeniority(null);
		employee.setWorkSeniority(null);
		// 在职状态缺省为在职
		if (StringUtil.isBlank(employee.getOnJobStatus())) {
			employee.setOnJobStatus(OnJobStatusEnum.ON.getValue());
		}
		// 单表新增直接复用 ServiceImpl 继承的 save，内部即 baseMapper.insert
		return save(employee);
	}

	/**
	 * 新增入参校验，覆盖建表脚本中 NOT NULL 的字段与固定码值字段
	 */
	private void validEmployee(TbG2User employee) {
		if (employee == null) {
			throw new ServiceException("员工信息不能为空");
		}
		if (StringUtil.isBlank(employee.getUserCode())) {
			throw new ServiceException("工号不能为空");
		}
		if (StringUtil.isBlank(employee.getUserName())) {
			throw new ServiceException("姓名不能为空");
		}
		if (StringUtil.isBlank(employee.getEmployeeType())) {
			throw new ServiceException("用工类型不能为空");
		}
		if (!EmployeeTypeEnum.isValid(employee.getEmployeeType())) {
			throw new ServiceException("用工类型取值不合法：" + employee.getEmployeeType());
		}
		if (StringUtil.isBlank(employee.getUserAttr())) {
			throw new ServiceException("人员属性不能为空");
		}
		if (!UserAttrEnum.isValid(employee.getUserAttr())) {
			throw new ServiceException("人员属性取值不合法：" + employee.getUserAttr());
		}
		if (Func.isEmpty(employee.getOrganId())) {
			throw new ServiceException("所属内部管理组织不能为空");
		}
		// 非必填码值：填了就必须合法
		if (StringUtil.isNotBlank(employee.getResidenceType()) && !ResidenceTypeEnum.isValid(employee.getResidenceType())) {
			throw new ServiceException("住所分类取值不合法：" + employee.getResidenceType());
		}
		if (StringUtil.isNotBlank(employee.getOnJobStatus()) && !OnJobStatusEnum.isValid(employee.getOnJobStatus())) {
			throw new ServiceException("在职状态取值不合法：" + employee.getOnJobStatus());
		}
	}

	@Override
	public IPage<TbG2User> selectEmployeePage(IPage<TbG2User> pages, TbG2User employee, String keyword) {
		LambdaQueryWrapper<TbG2User> queryWrapper = Wrappers.<TbG2User>lambdaQuery()
			.eq(StringUtil.isNotBlank(employee.getEmployeeType()), TbG2User::getEmployeeType, employee.getEmployeeType())
			.eq(StringUtil.isNotBlank(employee.getUserAttr()), TbG2User::getUserAttr, employee.getUserAttr())
			.eq(Func.isNotEmpty(employee.getOrganId()), TbG2User::getOrganId, employee.getOrganId())
			.and(StringUtil.isNotBlank(keyword), wrapper -> wrapper
				.like(TbG2User::getUserName, keyword)
				.or()
				.like(TbG2User::getUserCode, keyword))
			.orderByDesc(TbG2User::getUserId);
		return page(pages, queryWrapper);
	}

	@Override
	public TbG2User getEmployeeBasicInfo(Long userId, String userCode) {
		if (Func.isEmpty(userId) && StringUtil.isBlank(userCode)) {
			throw new ServiceException("输入工号");
		}
		LambdaQueryWrapper<TbG2User> queryWrapper = Wrappers.<TbG2User>lambdaQuery();
		if (StringUtil.isNotBlank(userCode)) {
			queryWrapper.eq(TbG2User::getUserCode, userCode);
		} else {
			queryWrapper.eq(TbG2User::getUserId, userId);
		}
		return getOne(queryWrapper, false);
	}

}

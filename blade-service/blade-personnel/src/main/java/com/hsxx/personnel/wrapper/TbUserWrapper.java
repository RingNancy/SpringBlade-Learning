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
package com.hsxx.personnel.wrapper;

import com.hsxx.personnel.pojo.entity.TbG2User;
import com.hsxx.personnel.pojo.vo.EmployeeBasicInfoVO;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.StringUtil;

/**
 * 员工基本信息包装类，返回视图层所需的字段
 *
 * @author Chill
 */
public class TbUserWrapper extends BaseEntityWrapper<TbG2User, EmployeeBasicInfoVO> {

	public static TbUserWrapper build() {
		return new TbUserWrapper();
	}

	@Override
	public EmployeeBasicInfoVO entityVO(TbG2User user) {
		if (user == null) {
			return null;
		}
		EmployeeBasicInfoVO employee = BeanUtil.copyProperties(user, EmployeeBasicInfoVO.class);
		// 手机号取值规则：自主维护优先，未维护时使用人力同步号码
		employee.setMobile(StringUtil.isNotBlank(user.getMobileSelf()) ? user.getMobileSelf() : user.getMobileSync());
		return employee;
	}

}

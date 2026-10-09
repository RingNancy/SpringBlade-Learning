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
package com.hsxx.personnel.feign;

import com.hsxx.personnel.pojo.entity.TbG2User;
import com.hsxx.personnel.pojo.vo.EmployeeBasicInfoVO;
import com.hsxx.personnel.service.TbG2UserService;
import com.hsxx.personnel.wrapper.TbUserWrapper;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工信息 Feign实现类
 *
 * <p>以 REST 端点形式对外提供 IEmployeeClient 契约，供其他微服务经 Feign 调用</p>
 *
 * @author Chill
 */
@Hidden
@RestController
@AllArgsConstructor
public class EmployeeClient implements IEmployeeClient {

	private TbG2UserService tbG2UserService;

	@Override
	@GetMapping(LIST)
	public R<List<EmployeeBasicInfoVO>> list() {
		List<TbG2User> users = tbG2UserService.list();
		return R.data(users.stream().map(TbUserWrapper.build()::entityVO).collect(Collectors.toList()));
	}

	@Override
	@GetMapping(DETAIL)
	public R<EmployeeBasicInfoVO> detail(Long userId, String userCode) {
		TbG2User user = tbG2UserService.getEmployeeBasicInfo(userId, userCode);
		if (user == null) {
			return R.fail("未查询到该员工信息");
		}
		return R.data(TbUserWrapper.build().entityVO(user));
	}

}

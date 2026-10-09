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
package com.hsxx.personnel.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hsxx.personnel.pojo.dto.EmployeeDTO;
import com.hsxx.personnel.pojo.entity.TbG2User;
import com.hsxx.personnel.pojo.vo.EmployeeBasicInfoVO;
import com.hsxx.personnel.service.TbG2UserService;
import com.hsxx.personnel.wrapper.TbUserWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.swagger.annotation.ApiOrder;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 员工信息 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tbUser")
@ApiOrder
@Tag(name = "员工信息", description = "员工基本信息接口")
public class TbUserController {

	private TbG2UserService tbG2UserService;

	/**
	 * 员工信息分页
	 */
	@GetMapping("/page")
	@Operation(summary = "员工信息分页", description = "支持姓名/工号关键字与用工类型、人员属性、所属组织筛选")
	public R<IPage<EmployeeBasicInfoVO>> page(@Parameter(description = "关键字：姓名或工号") @RequestParam(required = false) String keyword,
											  @Parameter(description = "用工类型") @RequestParam(required = false) String employeeType,
											  @Parameter(description = "人员属性") @RequestParam(required = false) String userAttr,
											  @Parameter(description = "所属内部管理组织") @RequestParam(required = false) Long organId,
											  @Parameter(description = "分页参数") Query query) {
		TbG2User employee = new TbG2User();
		employee.setEmployeeType(employeeType);
		employee.setUserAttr(userAttr);
		employee.setOrganId(organId);
		IPage<TbG2User> pages = tbG2UserService.selectEmployeePage(Condition.getPage(query), employee, keyword);
		return R.data(TbUserWrapper.build().pageVO(pages));
	}

	/**
	 * 员工基本信息详情
	 */
	@GetMapping("/detail")
	@Operation(summary = "员工基本信息详情", description = "传入员工主键或工号，两者至少传入一个")
	public R<EmployeeBasicInfoVO> detail(@Parameter(description = "员工主键") @RequestParam(required = false) Long userId,
										 @Parameter(description = "工号") @RequestParam(required = false) String userCode) {
		TbG2User user = tbG2UserService.getEmployeeBasicInfo(userId, userCode);
		if (user == null) {
			return R.fail("未查询到该员工信息");
		}
		return R.data(TbUserWrapper.build().entityVO(user));
	}

	/**
	 * 新增员工
	 */
	@PostMapping("/save")
	@Operation(summary = "新增员工", description = "传入员工基础信息，工号唯一")
	public R save(@RequestBody EmployeeDTO employee) {
		return R.status(tbG2UserService.saveEmployee(employee));
	}

}

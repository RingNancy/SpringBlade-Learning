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
package com.hsxx.attendance.feign;

import com.hsxx.attendance.pojo.vo.EmployeeBasicInfoVO;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 员工信息 Feign接口类
 *
 * <p>供其他微服务引用本模块实现服务间调用，value 须与 AttendanceApplication 注册的服务名保持一致</p>
 *
 * @author Chill
 */
@FeignClient(
	value = "blade-attendance"
)
public interface IEmployeeClient {

	String API_PREFIX = "/feign/client/employee";
	String DETAIL = API_PREFIX + "/detail";

	/**
	 * 获取员工基本信息
	 *
	 * @param userId   员工主键
	 * @param userCode 工号
	 * @return 员工基本信息
	 */
	@GetMapping(DETAIL)
	R<EmployeeBasicInfoVO> detail(@RequestParam(value = "userId", required = false) Long userId,
								  @RequestParam(value = "userCode", required = false) String userCode);

}

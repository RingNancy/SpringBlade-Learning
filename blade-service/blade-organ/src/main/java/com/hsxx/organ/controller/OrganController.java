package com.hsxx.organ.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hsxx.organ.pojo.entity.TbG2Organ;
import com.hsxx.organ.pojo.vo.OrganTreeVO;
import com.hsxx.organ.service.TbG2OrganService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.core.swagger.annotation.ApiOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门组织架构 控制器
 *
 * <p>删除功能暂缓：二级结构稳定后随详细组织结构一并设计</p>
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/organ")
@ApiOrder
@Tag(name = "部门组织架构", description = "二级部门的新增、修改与树形查询")
public class OrganController {

	private final TbG2OrganService organService;

	/**
	 * 组织架构树
	 */
	@GetMapping("/tree")
	@Operation(summary = "组织架构树", description = "公司根节点 + 二级部门的树形结构")
	public R<List<OrganTreeVO>> tree() {
		return R.data(organService.tree());
	}

	/**
	 * 部门平铺列表
	 */
	@GetMapping("/list")
	@Operation(summary = "部门列表", description = "全部部门平铺列表，按层次与部门代码排序")
	public R<List<TbG2Organ>> list() {
		return R.data(organService.list(new LambdaQueryWrapper<TbG2Organ>()
			.orderByAsc(TbG2Organ::getLevelNo)
			.orderByAsc(TbG2Organ::getDeptCode)));
	}

	/**
	 * 部门详情
	 */
	@GetMapping("/detail")
	@Operation(summary = "部门详情", description = "传入组织主键")
	public R<TbG2Organ> detail(@Parameter(description = "组织主键") @RequestParam Long organId) {
		return R.data(organService.getById(organId));
	}

	/**
	 * 新增或修改部门
	 */
	@PostMapping("/submit")
	@Operation(summary = "新增或修改部门", description = "主键为空即新增（默认挂公司根节点下）；修改自动记录履历快照")
	public R submit(@RequestBody TbG2Organ organ) {
		return R.status(organService.submitOrgan(organ));
	}

}

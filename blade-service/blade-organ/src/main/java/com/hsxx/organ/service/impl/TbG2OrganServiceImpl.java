package com.hsxx.organ.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsxx.organ.mapper.TbG2OrganMapper;
import com.hsxx.organ.mapper.TbG2OrganRecMapper;
import com.hsxx.organ.pojo.entity.TbG2Organ;
import com.hsxx.organ.pojo.entity.TbG2OrganRec;
import com.hsxx.organ.pojo.vo.OrganTreeVO;
import com.hsxx.organ.service.TbG2OrganService;
import lombok.AllArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 人力组织架构信息表 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class TbG2OrganServiceImpl extends ServiceImpl<TbG2OrganMapper, TbG2Organ>
	implements TbG2OrganService {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private final TbG2OrganRecMapper organRecMapper;

	@Override
	public List<OrganTreeVO> tree() {
		List<TbG2Organ> all = list(new LambdaQueryWrapper<TbG2Organ>()
			.orderByAsc(TbG2Organ::getLevelNo)
			.orderByAsc(TbG2Organ::getDeptCode));
		Map<String, List<TbG2Organ>> byParent = all.stream()
			.filter(o -> o.getParentCode() != null)
			.collect(Collectors.groupingBy(TbG2Organ::getParentCode));
		// 根节点：无父代码，或父代码在现有部门中不存在（挂孤节点防丢数据）
		List<TbG2Organ> roots = all.stream()
			.filter(o -> o.getParentCode() == null || !byParent.containsKey(o.getParentCode())
				|| all.stream().noneMatch(p -> p.getDeptCode().equals(o.getParentCode())))
			.collect(Collectors.toList());
		return roots.stream().map(o -> buildNode(o, byParent)).collect(Collectors.toList());
	}

	private OrganTreeVO buildNode(TbG2Organ organ, Map<String, List<TbG2Organ>> byParent) {
		OrganTreeVO node = new OrganTreeVO();
		node.setOrganId(organ.getOrganId());
		node.setCompanyCode(organ.getCompanyCode());
		node.setCompanyName(organ.getCompanyName());
		node.setDeptCode(organ.getDeptCode());
		node.setDeptName(organ.getDeptName());
		node.setLevelNo(organ.getLevelNo());
		node.setDeptFullName(organ.getDeptFullName());
		node.setValidFlag(organ.getValidFlag());
		List<TbG2Organ> children = byParent.getOrDefault(organ.getDeptCode(), new ArrayList<>());
		node.setChildren(children.stream().map(c -> buildNode(c, byParent)).collect(Collectors.toList()));
		return node;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean submitOrgan(TbG2Organ organ) {
		if (StringUtil.isBlank(organ.getDeptCode()) || StringUtil.isBlank(organ.getDeptName())) {
			throw new ServiceException("部门代码与部门名称不能为空");
		}
		if (organ.getOrganId() == null) {
			return insertOrgan(organ);
		}
		return updateOrgan(organ);
	}

	private boolean insertOrgan(TbG2Organ organ) {
		if (count(new LambdaQueryWrapper<TbG2Organ>().eq(TbG2Organ::getDeptCode, organ.getDeptCode())) > 0) {
			throw new ServiceException("部门代码已存在：" + organ.getDeptCode());
		}
		TbG2Organ root = getOne(new LambdaQueryWrapper<TbG2Organ>()
			.eq(TbG2Organ::getLevelNo, 1).last("LIMIT 1"));
		if (root != null) {
			if (StringUtil.isBlank(organ.getParentCode())) {
				organ.setParentCode(root.getDeptCode());
			}
			if (StringUtil.isBlank(organ.getCompanyCode())) {
				organ.setCompanyCode(root.getCompanyCode());
			}
			if (StringUtil.isBlank(organ.getCompanyName())) {
				organ.setCompanyName(root.getCompanyName());
			}
		}
		if (organ.getLevelNo() == null) {
			organ.setLevelNo(2);
		}
		if (StringUtil.isBlank(organ.getSecondDept())) {
			organ.setSecondDept(organ.getDeptCode());
		}
		if (StringUtil.isBlank(organ.getDeptFullName())) {
			organ.setDeptFullName(organ.getCompanyName() + "-" + organ.getDeptName());
		}
		if (StringUtil.isBlank(organ.getValidFlag())) {
			organ.setValidFlag("1");
		}
		if (StringUtil.isBlank(organ.getUseFlag())) {
			organ.setUseFlag("1");
		}
		return save(organ);
	}

	private boolean updateOrgan(TbG2Organ organ) {
		TbG2Organ old = getById(organ.getOrganId());
		if (old == null) {
			throw new ServiceException("部门不存在：" + organ.getOrganId());
		}
		// 变更前快照写入履历表
		TbG2OrganRec rec = new TbG2OrganRec();
		rec.setOrganId(old.getOrganId());
		rec.setSnapJson(toJson(old));
		rec.setModifyTime(new Date());
		rec.setModifyUser("blade-organ");
		organRecMapper.insert(rec);

		if (!old.getDeptCode().equals(organ.getDeptCode())) {
			long childCount = count(new LambdaQueryWrapper<TbG2Organ>()
				.eq(TbG2Organ::getParentCode, old.getDeptCode()));
			if (childCount > 0) {
				throw new ServiceException("该部门存在下级部门，不允许修改部门代码");
			}
			if (count(new LambdaQueryWrapper<TbG2Organ>()
				.eq(TbG2Organ::getDeptCode, organ.getDeptCode())) > 0) {
				throw new ServiceException("部门代码已存在：" + organ.getDeptCode());
			}
		}
		return updateById(organ);
	}

	private String toJson(TbG2Organ organ) {
		try {
			return MAPPER.writeValueAsString(organ);
		} catch (Exception e) {
			throw new ServiceException("生成组织快照失败：" + e.getMessage());
		}
	}

}

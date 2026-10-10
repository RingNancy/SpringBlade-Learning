package com.hsxx.organ.service;

import com.hsxx.organ.pojo.entity.TbG2Organ;
import com.hsxx.organ.pojo.vo.OrganTreeVO;
import com.baomidou.mybatisplus.spring.service.IService;

import java.util.List;

/**
 * 人力组织架构信息表 服务类
 *
 * @author Chill
 */
public interface TbG2OrganService extends IService<TbG2Organ> {

	/**
	 * 查询组织架构树（当前为二级结构：公司 + 二级部门，树形组装逻辑通用）
	 *
	 * @return 组织树，根节点为公司
	 */
	List<OrganTreeVO> tree();

	/**
	 * 新增或修改部门（主键为空即新增）
	 *
	 * <p>新增时默认挂到公司根节点下（二级部门），部门代码唯一；
	 * 修改时先将变更前快照写入履历表 TB_G2_ORGAN_REC，存在下级部门时不允许修改部门代码</p>
	 *
	 * @param organ 部门信息
	 * @return 是否成功
	 */
	boolean submitOrgan(TbG2Organ organ);

}

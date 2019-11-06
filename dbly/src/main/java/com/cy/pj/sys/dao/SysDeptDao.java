package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cy.pj.sys.common.vo.Node;
import com.cy.pj.sys.entity.SysDept;

@Mapper
public interface SysDeptDao {

	List<Map<String, Object>> selectObjects();
	
	
	/**
	 * 根据id删除表信息
	 */
	int deleteObjectById(Integer id);
	
	/**
	 * 根据id获取其所拥有子dept个数
	 */
	int getChildDeptCountById(Integer id);
	
	/**
	 * 根据id获取id name parentId信息
	 */
	List<Node> findZtreeDeptNodes();
	
	/**
	 * INSERT
	 */
	int insertObject(SysDept entity);
	
	/**
	 * UPDATE
	 */
	int updateObject(SysDept entiy);
	
}

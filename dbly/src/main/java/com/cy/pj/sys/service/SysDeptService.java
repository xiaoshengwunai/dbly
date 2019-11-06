package com.cy.pj.sys.service;

import java.util.List;
import java.util.Map;

import com.cy.pj.sys.common.vo.Node;
import com.cy.pj.sys.entity.SysDept;

public interface SysDeptService {

	List<Map<String, Object>> getDepts();

	int deleteDeptById(Integer id); 
	
	List<Node> findZtreeDeptNodes();
	
	int saveObject(SysDept entity);
	
	int updateObject(SysDept entity);
}

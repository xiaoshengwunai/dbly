package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserDao {

	/**
	 * 获取某deptId名下的user的count
	 */
	int getCountByDeptId(Integer deptId);
	
}

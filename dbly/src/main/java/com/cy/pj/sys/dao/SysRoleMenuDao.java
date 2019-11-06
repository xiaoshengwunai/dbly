package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * role_menu
 */
@Mapper
public interface SysRoleMenuDao {
	
	/**
	 * 根据menuId删除role_menu表中数据
	 * @return
	 */
	int deleteRoleMenuByMenuId(@Param("id")Integer menuId);
}

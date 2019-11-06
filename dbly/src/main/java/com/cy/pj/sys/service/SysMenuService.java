package com.cy.pj.sys.service;

import java.util.List;
import java.util.Map;

import com.cy.pj.sys.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;

public interface SysMenuService {
	
	/**
	 * 从数据库获取menu表所有信息以及拼接 parentId对应name的业务层方法
	 */
	List<Map<String,Object>> getMenus();
	
	/**
	 *  调用dao,删除menue表中单条数据的service方法
	 */
	int deleteMenuById(Integer id);
	
	/**
	 * 通过DAO,从menu表中查询所有记录的部分字段,保存入Node对象
	 */
	List<Node> getZtreeMenuNodes();
	
	/**
	 * 通过Dao向数据库中insert menu的方法放爱抚
	 */
	int saveMenu(SysMenu sysMenu);

	/**
	 * 通过DAO,在menu的table中update某条信息
	 */
	void updateMenu(SysMenu sysMenu);
}

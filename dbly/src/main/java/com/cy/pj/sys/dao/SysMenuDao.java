package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.cy.pj.sys.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;

/**
 * menu模块
 */
@Mapper
public interface SysMenuDao {
	
	/**
	 * 从menu表中获取所有数据以及父menuId对应的name的方法1
	 * 从menu表中获取所有数据以及父menuId对应的name的方法2
	 */
	List<Map<String, Object>>findMenus1();
	List<Map<String, Object>>findMenus2();

	/**
	 * 删除menu信息
	 *  1,要先确认有没有对应子菜单
	 *  2,先删除关系数据库的信息
	 *  3,再删除menu信息
	 * 实现1 从menu表所有信息中,查询要删除的id是否为别人的parentId,所以不需要在本层级专门定义方法了,直接可以用上边的方法
	 * 实现2 要先创建关系表对应的dao对象啊...
	 */
	int deleteMenuById(Integer id);
	
	
	/**
	 * 从menu表中获取所有信息的部分字段,保存为一个Node节点对象
	 */
	List<Node> findZtreeMenuNodes();
	
	/**
	 * 向表中insert菜单menu信息
	 */
	int insertObject(SysMenu sysMenu);
	
	/**
	 * 向表中update某menu数据
	 * @param sysMenu
	 * @return
	 */
	int updateObject(SysMenu sysMenu);
	
}

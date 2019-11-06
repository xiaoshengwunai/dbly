package com.cy.pj.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.sys.common.exception.ServiceException;
import com.cy.pj.sys.common.vo.Node;
import com.cy.pj.sys.dao.SysDeptDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.entity.SysDept;
import com.cy.pj.sys.service.SysDeptService;
@Service
public class SysDeptServiceImpl implements SysDeptService {

	@Autowired
	private SysDeptDao sysDeptDao;
	@Autowired
	private SysUserDao sysUserDao;
	
	@Override
	public List<Map<String, Object>> getDepts() {
		List<Map<String,Object>> list = sysDeptDao.selectObjects();
 		return list;
	}

	@Override
	public int deleteDeptById(Integer id) {
		//验证参数合法性
		if(id == null || id == 0) throw new IllegalArgumentException();
		//删除前先验证是否有子部门或名下员工
		int childDeptCount = sysDeptDao.getChildDeptCountById(id);
		if (childDeptCount > 0) throw new ServiceException("请先删除子部门!"  +childDeptCount + "个");
		int userCount = sysUserDao.getCountByDeptId(id);
		if(userCount > 0) throw new ServiceException("请先删除名下员工");
		//执行删除
		int rows = sysDeptDao.deleteObjectById(id);
		if(rows == 0) throw new ServiceException("删除0条记录!");
		return rows;
	}

	/**
	 * 通过Dao获得dept的node节点信息
	 */
	@Override
	public List<Node> findZtreeDeptNodes(){
		List<Node> list = sysDeptDao.findZtreeDeptNodes();
		return list;
	}

	/**
	 * 通过Dao向表中insert一条dept信息的方法
	 * 
	 */
	@Override
	public int saveObject(SysDept entity) {
		// 参数基本合法性验证
		if (entity == null) throw new ServiceException("存入的对象不能为null");
		if (StringUtils.isEmpty(entity.getName())) throw new ServiceException("至少名字不能为null啊");
		int rows;
		try {
			rows = sysDeptDao.insertObject(entity);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException("震惊!居然保存失败了!");
		}
		return rows;
	}

	/**
	 * UPDATE
	 */
	@Override
	public int updateObject(SysDept entity) {
		// 参数基本合法性验证
		if (entity == null) throw new ServiceException("存入的对象不能为null");
		if (StringUtils.isEmpty(entity.getName())) throw new ServiceException("至少名字不能为null啊");
		int rows = sysDeptDao.updateObject(entity);
		if(rows == 0) throw new ServiceException("更新失败!");
		return rows;
	}
	
	
	
	
	
	

}

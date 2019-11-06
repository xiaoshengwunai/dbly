package com.cy.pj.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.sys.common.exception.ServiceException;
import com.cy.pj.sys.common.vo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
@Service
public class SysMenuServiceImpl implements SysMenuService{

	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	private SysMenu sysMenu;

	/**
	 * DAO已经封装得比较完善,这里的工作少了好多
	 *
	 */
	@Override
	public List<Map<String, Object>> getMenus() {
		List<Map<String, Object>> list = sysMenuDao.findMenus1();
		if(list == null || list.size() == 0)throw new ServiceException("震惊!业务层竟然没有从持久层拿到任何数据!");
	return list;
	}

	
	/**
	 * 	根据id从menus表中删除单条信息
	 */
	@Override
	public int deleteMenuById(Integer id) throws ServiceException{
		// 先验证传来参数的基本合法性
		if (id == null || id <=0) throw new IllegalArgumentException("请先选择要删除的信息啊!"); 
		/**
		* 判断要删除的订单有没有子菜单
		 * 其实这里要是否有子菜单也可以通过"先拿到表中所有parentId,再看所要删除的id 是否有作为parentId存在其中"
		 * 这是与课件原有的思路少有不同的地方,课件原有思路是"从dao操作"
		 * 
		 * 遇到问题.明明已经找到,但就是阻止拥有子菜单的误删除!
		 * 
		 * 解决问题:原因是用错了== ,if条件是个== 不同类型的比较,恒为false不能达到应有的效果!学习了!
		 */
		Object pId ;
		List<Map<String, Object>> list = sysMenuDao.findMenus1();
		for (Map map : list) {
			pId = map.get("parentId");
			System.out.println("id和parentId分别为" + id + pId);
			if(id.equals(pId)) {	// 重要!这里总是失败的原因是之前用了== 类型不同== 永远是错的,所以,语法使用错误!!!!
				System.out.println("终于找到这个id作为别人parentId的证据了!");
				throw new ServiceException("请先删除该菜单下的子菜单");
			}	//从表中所有parentId中找,传入id只有作为parentId存在其中了,就说明有子类
		}
		System.out.println("发现所有父id中存在有该id的身影,已经抛了业务异常了,居然又执行到这一步!不对劲");
		sysRoleMenuDao.deleteRoleMenuByMenuId(id);
		int rows = sysMenuDao.deleteMenuById(id);
		return rows;
	}


	@Override
	public List<Node> getZtreeMenuNodes() {
		List<Node> list = sysMenuDao.findZtreeMenuNodes();
		if (list == null || list.size() == 0) throw new ServiceException("没有获取任何node信息啊!");
		return list;
	}


	@Override
	public int saveMenu(SysMenu sysMenu) {
		/**
		 * 合法验证
		 * 	参数不能为null
		 * 	参数中getName属性不能为null
		 */
		if(sysMenu == null) throw new ServiceException("出现了保存信息为null的异常!") ;
		if(StringUtils.isEmpty(sysMenu.getName())) throw new ServiceException("所保存的信息中name不能为null啊,你不知道嘛!");
		/**
		 * 执行
		 * 	需要对执行过程捕获异常
		 */
		int rows;
		try {
			rows = sysMenuDao.insertObject(sysMenu);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException("糟糕!居然保存失败了!你说气不气");
		}
		return rows;
	}


	@Override
	public void updateMenu(SysMenu sysMenu) {
		/**
		 * 参数合法验证
		 */
		if (sysMenu == null) throw new ServiceException("业务层收到的所保存信息整体为null啊!!");
		if (StringUtils.isEmpty(sysMenu.getId())) throw new ServiceException("无论如何,id不能为null,毕竟只是update!");
		/**
		 * core
		 */
		int rows = sysMenuDao.updateObject(sysMenu);
		if (rows == 0) throw new ServiceException("糟糕,业务层发现居然只影响0条数据!");
		
	}
	
	
	
}

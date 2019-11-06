package com.cy.pj.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.sys.common.exception.ServiceException;
import com.cy.pj.sys.common.vo.JsonResult;
import com.cy.pj.sys.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
/**
 * 2019年9月29日 20:09:25 实验,写下这句话,看gitee能不能看到!
 *	2019年9月29日 20:18:32
 */
@RequestMapping("/menu")
@Controller
public class SysMenuController {
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping("/doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		List<Map<String,Object>> list = sysMenuService.getMenus();
		if (list == null || list.size() == 0)throw new ServiceException("震惊!控制层返回的数据不对劲!");
		return new JsonResult(list);
	}
	
	/**
	 * 根据id删除menu表中单条数据以及关系表对应数据的controller方法
	 */
	@RequestMapping("/doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		int rows = sysMenuService.deleteMenuById(id);
		if(rows == 0)throw new ServiceException("不知何故,删除失败!");
		return new JsonResult("Deleted OK!");
	}
	
	/**
	 * "菜单管理",增加"菜单"
	 * 
	 * (测试通过)
	 */
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysMenu sysMenu) {
		System.out.println("看我拿到的数据是否乱码" + sysMenu);
		int rows = sysMenuService.saveMenu(sysMenu);
		if (rows == 0) throw new ServiceException("控制层发现保存失败了!");
		return new JsonResult("Congratulations!Saved OK!");
	}
	
	/**
	 *	"菜单管理",update菜单
	 *(测试通过)
	 */
	@RequestMapping("/doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysMenu sysMenu) {
		sysMenuService.updateMenu(sysMenu);
		return new JsonResult("Updated OK!");
	}
	
	/**
	 * 获取后台所有menu表中封装的Node信息的list,给前端页面
	 * (测试通过)
	 */
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes() {
		List<Node> list = sysMenuService.getZtreeMenuNodes();
		System.out.println("ztree的控制层结果数据" + list);
		return new JsonResult(list); 
	}
	
	
}

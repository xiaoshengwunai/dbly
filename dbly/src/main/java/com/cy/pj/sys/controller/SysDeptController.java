package com.cy.pj.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.sys.common.vo.JsonResult;
import com.cy.pj.sys.common.vo.Node;
import com.cy.pj.sys.entity.SysDept;
import com.cy.pj.sys.service.SysDeptService;

/**
 * (测试通过)
 */
@RequestMapping("/dept")
@Controller
public class SysDeptController {

	@Autowired
	private SysDeptService sysDeptService;
	
	@RequestMapping("/doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		List<Map<String, Object>> list = sysDeptService.getDepts();
		return new JsonResult(list);
	}
	
	/**
	 * 删除dept信息
	 */
	@RequestMapping("/doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		sysDeptService.deleteDeptById(id);
		return new JsonResult("Deleted OK!");
	}
	
	/**
	 * 获取ztree呈现信息
	 */
	@RequestMapping("/doFindZTreeNodes")
	@ResponseBody
	public JsonResult doFindZTreeNodes() {
		List<Node> list = sysDeptService.findZtreeDeptNodes();
		return new JsonResult(list);
	}
	
	/**
	 * Insert
	 */
	@RequestMapping("/doSaveObject")
	@ResponseBody
	public JsonResult doSaveOBject(SysDept entity) {
		sysDeptService.saveObject(entity);
		return new JsonResult("Saved OK!");
	}
	
	/**
	 * UPDATE
	 */
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysDept entity) {
		sysDeptService.updateObject(entity);
		return new JsonResult("Updated OK!");
	}
	
	
	
	
}

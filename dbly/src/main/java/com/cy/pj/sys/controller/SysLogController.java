package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.sys.common.exception.ServiceException;
import com.cy.pj.sys.common.vo.JsonResult;
import com.cy.pj.sys.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
/**
 *  遗留问题: 信息总条数显示null
 *
 */
@RequestMapping("/log")
@Controller
public class SysLogController {

	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageLogs(String username, Integer pageCurrent) {
		PageObject<SysLog> pageObject = sysLogService.findPageLogs(username, pageCurrent);
//		System.out.println("控制层" + username + pageCurrent + pageObject);
		return new JsonResult(pageObject);
	}
	
	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteLogs(Integer... ids) {
		System.out.println("控制层拿到数据:" + ids);
		int rows = sysLogService.deleteLogs(ids);
		if (rows == 0) throw new ServiceException("删除失败!");
		return new JsonResult("Deleted OK!");
	}
	

}

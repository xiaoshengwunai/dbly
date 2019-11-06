package com.cy.pj.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class PageController {

	@RequestMapping("doIndexUI")
	public String doIndexUI() {

		return "starter";
	}

	/**
	 * 返回log列表
	 */
	@RequestMapping("log/log_list")
	public String doLogUI() {
		return "sys/log_list";
	}
	/**
	 * 返回分页
	 */
	@RequestMapping("doPageUI")
	public String doPageUI() {
		return "common/page";
	}

	/**返回某个模块的UI页面(rest架构编码风格)*/
	@RequestMapping("{module}/{ui}")
	public String doModuleUI(@PathVariable String ui) {
		return "sys/"+ui;
	}
}

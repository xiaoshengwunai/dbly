package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.User;
import com.jt.service.HttpUserService;
import com.jt.util.ObjectMapperUtil;

/**
 * http实现user注册
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/web/user")
public class HttpUserController {

	
	@Autowired
	private HttpUserService httpUserService;
	@RequestMapping("/doRegister")
	public String doRegister(String json) {
		System.out.println("1");
		System.out.println("http 注册 sso 控制" + json);
		User user = ObjectMapperUtil.toObject(json, User.class);
		System.out.println("2");
		httpUserService.httpSaveUser(user);
		System.out.println("3");
		System.out.println("4");
		return "ok!";
	}
}

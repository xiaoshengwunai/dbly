package com.jt.controller;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.service.DubboUserService;
import com.jt.pojo.User;
import com.jt.service.HttpUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

	/**
	 * 注册
	 * 登录
	 * 
	 * 通用 
	 */
//	@RequestMapping("/{moduleName}")
//	public String module(@PathVariable String moduleName) {
//		return moduleName;
//	}
	
	
	/**
	 * 改dubbo
	 */
	@Reference(check = false)	// 不需要校验
	private DubboUserService dubboUserService;
	@Autowired
	private HttpUserService httpUserService;
	@Autowired
	private JedisCluster jedisCluster;
	
	private static final String TICKET = "JT_TICKET"; 
	
	@RequestMapping("/{moduleName}")
	public String module(@PathVariable String moduleName) {
		return moduleName;
	}
	
	/**
	 * 可以用user对象接受参数
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) {
		// dubbo
		dubboUserService.saveUser(user);
		// http实现!!这两个需要根据技术切换!!
//		httpUserService.httpSaveUser(user);
		System.out.println("http web 注册 控制" + user);
		return SysResult.success();
	}
	
	/**
	 * 登录
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user, HttpServletResponse response) {	// 是从底层Servlet来的
		
		String ticket = dubboUserService.doLogin(user);
		
		if (StringUtils.isEmpty(ticket)) {	// 什么情况下会空呢? 登录错误时!
			return SysResult.fail("用户名或密码不正确!");
		}

		/**
		 *将ticket保存到客户端的cookie中
		 * 	cookie
		 * 		setMaxAge(>0) 存活的生命周期,秒
		 * 		setMaxAge(0)	要求cookie立即删除
		 * 		setMaxAge(-1)	会话级cookie,会话关闭立即删除(关闭网页)
		 */
		Cookie ticketCookie = new Cookie(TICKET,ticket);	//	cookie导包暂时是Servlet 注意!
		ticketCookie.setMaxAge(60 * 60 * 24 * 7);			//	设置有效时间.7天有效
		ticketCookie.setPath("/");									//	设置有效路径,何时有效
		ticketCookie.setDomain("jt.com");						//	设置谁可共享此cookie! 这样写顺便包含了二级域名
		
		response.addCookie(ticketCookie);
		
		return SysResult.success();
	}
	
	/**
	 * 登出
	 * 1. 删除cookie 名称为JT_TICKET  前提需要获取cookies HttpServletRequest request
	 * 2. 删除redis 根据ticket值删除redis
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String ticket = null;
		if (cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(TICKET)) {
					ticket = cookie.getValue();
					break;	// 肯定只有一个,所以没必要继续遍历
				}
			}
		}
		if (!StringUtils.isEmpty(ticket)) {
			jedisCluster.del(ticket);
			Cookie cookie = new Cookie(TICKET,"");	// cookie没有直接删除的方法,需要找一个空cookie来覆盖,曲线实现删除!
			cookie.setMaxAge(0);
			cookie.setPath("/");
			cookie.setDomain("jt.com");
			response.addCookie(cookie);
		} 
		return "redirect:/";	//小技巧,首页可以就写个/
	}
	
}

package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	/**
	 * 根据用户信息实现数据的校验
	 * 
	 * 返回值结果:
	 * true 表示已经存在 false:不存在 可以使用!
	 */
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(
			@PathVariable String param,
			@PathVariable Integer type,
			String callback
			) {
		boolean data = userService.checkUser(param, type);
		System.out.println("sso 校验 控制" + param + type +callback+data);
		return new JSONPObject(callback, SysResult.success(data));
	}
	
	/**
	 * http://sso.jt.com/user/query/753893da69df8675952cdea6da86433f?callback=jsonp1571282904544&_=1571282904621
	 * 登录后数据回显
	 * 根据ticket信息查询用户信息(json数据),然后将数据回传客户端
	 * restFul风格接收
	 */
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByuTicket(@PathVariable String ticket,String callback) {
		String userJSON = jedisCluster.get(ticket);
		// 校验,ticket可能是假的?即使对了也可能被淘汰了,不存在了
		if (StringUtils.isEmpty(userJSON)) {		
			return new JSONPObject(callback, SysResult.fail());
		}
		return new JSONPObject(callback, SysResult.success(userJSON));
	}
	

}

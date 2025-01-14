package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortController {
	@Value("${server.port}")
	private String port;
	
	/**
	 * 如果动态获取当前服务器的端口号
	 */
	@RequestMapping("/port")
	public String getPort() {
		return "本次访问的端口号为:" + port;
	}
}

package com.jt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.User;
import com.jt.service.HttpUserService;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;

/**
 * http实现user注册的业务实现
 * @author Administrator
 *
 */
@Service
public class HttpUserServiceImpl implements HttpUserService {

	@Autowired
	private HttpClientService httpClientService;
	@Override
	public void httpSaveUser(User user) {
		String url = "http://sso.jt.com/web/user/doRegister";
		String json = ObjectMapperUtil.toJSON(user);
		Map<String, String> params = new HashMap<>();
		params.put("json", json);
		System.out.println("http web 注册 业务 " + user + params);
		httpClientService.doPost(url, params, "UTF-8");
	}

}

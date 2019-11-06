package com.jt.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.service.HttpUserService;
/**
 * http实现user注册 sso子系统 业务层实现
 * @author Administrator
 *
 */
@Service
public class HttpUserServiceImpl implements HttpUserService {

	@Autowired
	private UserMapper userMapper;
	@Override
	public void httpSaveUser(User user) {
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass)
		.setEmail(user.getPhone())
		.setCreated(new Date())
		.setUpdated(user.getCreated());
		userMapper.insert(user);
	}

}

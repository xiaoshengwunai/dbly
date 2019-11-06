package com.jt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 可以用map来匹配几种
	 *
	 */
	@Override
	public boolean checkUser(String param, Integer type) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "username");
		map.put(2, "phone");
		map.put(3, "email");
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		String column = map.get(type);
		queryWrapper.eq(column, param);
		User user = userMapper.selectOne(queryWrapper);
		System.out.println("校验 业务实现数据 param" + param +"type"+ type + "user" + user);
		return user == null ? false : true;	// 注意!如果不存在,表示可以用,反而要返回false
	}

	
}

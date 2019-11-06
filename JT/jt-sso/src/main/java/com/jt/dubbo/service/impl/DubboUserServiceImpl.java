package com.jt.dubbo.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.dubbo.service.DubboUserService;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service
public class DubboUserServiceImpl implements DubboUserService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public void saveUser(User user) {
		// 防止email值为null报错,使用电话号码暂时代替
		// 加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass)
			  .setEmail(user.getPhone())
			  .setCreated(new Date())
			  .setUpdated(user.getCreated());
		userMapper.insert(user);
	}

	/**
	 * 登录
	 * 1. 根据用户名和密码查询数据库
	 * 		如果没有记录(用户名密码错误) return null
	 * 2. 生成ticket(加密后的密钥),userJSON串,保存到redis
	 * 3. 返回ticket
	 * 
	 *
	 */
	@Override
	public String doLogin(User user) {
		User userDB = findUserByUP(user);
		if (userDB != null) {
			// 生成密钥
			String uuid = UUID.randomUUID().toString();
			String ticket = DigestUtils.md5DigestAsHex(uuid.getBytes());
			// 将某些敏感数据进行脱敏处理
			userDB.setPassword("***********");
			// 将user对象转换为json串
			String userJson = ObjectMapperUtil.toJSON(userDB);
			// ticket数据存入redis
			jedisCluster.setex(ticket, 7 * 24 * 3600, userJson);
			return ticket;
		}
		return null;
	}
	
	public User findUserByUP(User user) {
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>(user);
		User userDB = userMapper.selectOne(queryWrapper);
		return userDB;
	}

	
	
}

package com.jt.service;

import com.jt.pojo.User;

/**
 * http实现user注册 sso子系统 业务层
 * @author Administrator
 *
 */
public interface HttpUserService {

	void httpSaveUser(User user);
}

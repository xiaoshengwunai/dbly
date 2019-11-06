package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.jt.pojo.User;
import com.jt.util.CookieUtil;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.ThreadLocalUtil;

import redis.clients.jedis.JedisCluster;

/**
 * 把这个接口了解一下会更加理解拦截器!
 * 
 * 修改:
 * 		写了ThreadLocal工具,改用之,弃用httpServeltrequest
 *
 */
@Component 	//将此对象交给spring容器管理
public class UserInterceptor implements HandlerInterceptor{ // (可以记一记)

	@Autowired
	private JedisCluster jedisCluster;
	
	private static final String JTUSER = "JT_USER";
	/**
	 * ""
	 * 目标: 用户不登录就不能访问购物车页面
	 * 实现思路:
	 * 		获取cookie
	 * 		获取redis
	 * 		
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		System.out.println("方法执行之前");
		String ticket = CookieUtil.getCookieValue(request, "JT_TICKET");
		/**
		 * 不建议用StringUtils
		 */
		if (!StringUtils.isEmpty(ticket)) {
			System.out.println("进入拦截器pre");
			String userJSON = jedisCluster.get(ticket);
			System.out.println("拦截器pre"+userJSON);
			if (!StringUtils.isEmpty(userJSON)) {
				User user = ObjectMapperUtil.toObject(userJSON, User.class);
				// user对象着呢么传递到控制层?
				// 等会再抄!
//				request.setAttribute(JTUSER, user);
				ThreadLocalUtil.setUser(user);
				System.out.println("拦截器 之前 "+ThreadLocalUtil.get());
				return true;
			}
		}
		
		/**
		 * 一般拦截器中的false和重定向联用,重定向到登陆页面(www→sso)
		 * (因为总不能因为点个购物车就把网页挂掉吧????)
		 */
		response.sendRedirect("/user/login.html");
		return false;	// 放行! false不放行!
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("方法执行之后!");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		request.removeAttribute(JTUSER);
		ThreadLocalUtil.remove();
		System.out.println("拦截器最后的管理范围");
	}
	
	
	
	
}

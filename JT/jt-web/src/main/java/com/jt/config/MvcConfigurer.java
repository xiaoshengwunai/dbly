package com.jt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jt.interceptor.UserInterceptor;
import com.jt.util.CookieUtil;

/**
 * 直接复制课件的!
 *	WebMvcConfigurer 就是这个功能提供的
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer{
	
	//开启匹配后缀型配置 html
	// 伪静态
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(true);
	}
	
	
	/**
	 * 配置拦截器们!
	 */
	@Autowired
	private UserInterceptor userInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/**
		 *</星">只能拦截子目录一级 两个星星可以所有目录
		 */
		
		registry.addInterceptor(userInterceptor)
				  .addPathPatterns("/cart/**", "/order/**");		//定义拦截规则 
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	
	
}

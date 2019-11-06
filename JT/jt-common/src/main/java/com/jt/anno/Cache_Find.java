package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 *	@Target 表示声明的注解的修饰范围,在哪用
 *	@Retention 作用的时间范围,是运行时有效!
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache_Find {
	/**
	 * 需要一个key 给用户一个选择,如果用户不写 为null ..... 写了不为null...如下
	 * 	如果key为null  	自动生成一个动态的key
	 * 	如果!null			使用用户传过来的!
	 * 
	 * 设置seconds 表示数据声明周期,为0表示不会过期!
	 */
	String key() default "";	
	int seconds() default 0;	// 表示声明周期0,不会过期
}

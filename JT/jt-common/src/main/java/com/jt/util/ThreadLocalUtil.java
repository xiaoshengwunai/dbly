package com.jt.util;

import com.jt.pojo.User;

public class ThreadLocalUtil {

	private static ThreadLocal<User> thread = new ThreadLocal<>();
	
	public static void setUser(User user) {
		thread.set(user);
	}
	
	public static User get() {
		return thread.get();
	}
	
	/**
	 * 内存泄漏问题:
	 * Thread有特殊之处,如果一个线程调用多个子线程的话,原线程不能轻易关闭,可能会带来泄露问题
	 */
	public static void remove() {
		thread.remove();
	}
}

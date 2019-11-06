package com.jt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *	对象和Json串互相转化的工具API
 */
public class ObjectMapperUtil {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	/**
	 * 对象转化为json串
	 * static 工具api用static贼方便的说!
	 */
	public static String toJSON(Object target) {
		String result = null;
		try {
			result = MAPPER.writeValueAsString(target);
		} catch (Exception e) {
			e.printStackTrace();
			/**
			 * 这里把checked异常转化为RuntimeException
			 * "告诉程序该停停 该处理处理"
			 */
			throw new RuntimeException(e);	 
		}
		return result;
	}
	
	/**
	 * json串转换为对象
	 * 
	 * 用泛型对象
	 * 不知道传啥的时候用这个...
	 * 如果写成Object到时候还得强转!
	 */
	public static <T> T toObject(String json, Class<T> targetClass) {
		T object = null;
		try {
			object = MAPPER.readValue(json, targetClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return object;
	}
}

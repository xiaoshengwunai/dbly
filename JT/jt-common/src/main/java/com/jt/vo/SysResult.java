package com.jt.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)	// 链式调用
@NoArgsConstructor
@AllArgsConstructor
public class SysResult implements Serializable{
	
	private static final long serialVersionUID = 5635009095875451036L;
	
	private Integer status;	// 服务器返回客户端的状态码,200正常,201失败
	private String message;	// 服务器返回客户端的消息
	private Object data;	// 服务器返回客户端的数据
	
	public static SysResult success() {
		return new SysResult(200, null, null);
	}
	public static SysResult success(Object data) {
		return new SysResult(200, null, data);
	}
	public static SysResult success(String msg, Object data) {
		return new SysResult(200, msg, data);
	}
	
	public static SysResult fail() {
		return new SysResult(201, null, null);
	}
	public static SysResult fail(String msg) {
		return new SysResult(201, msg, null);
	}
	
	
	
	
}

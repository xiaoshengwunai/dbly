package com.cy.pj.sys.common.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult implements Serializable {
	private static final long serialVersionUID = -3698132841665867809L;
	private int state = 1;
	private String message = "ok";
	private Object data;
	
	public JsonResult (String message) {
		this.message = message;
	}
	
	public JsonResult (Object data) {
		this.data = data;
	}
	
	/**
	 * 异常出现会调用此构造
	 * @param t
	 */
	public JsonResult (Throwable t) {
		this.state = 0;
		this.message = t.getMessage();
	}
}

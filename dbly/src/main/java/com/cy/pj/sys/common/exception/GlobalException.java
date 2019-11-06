package com.cy.pj.sys.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.sys.common.vo.JsonResult;

@ControllerAdvice
public class GlobalException {
	//这是jdk中自带的日志api
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	JsonResult doHandleRuntimeException(RuntimeException r){
		r.printStackTrace();
		return new JsonResult(r);	//封装异常信息
	}
}

package com.jt.thro;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.vo.SysResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 * @author Changlu Zhang
 *
 */
@ControllerAdvice	// 对Controller层的异常有效,如果service出问题,因为调用也会有效
@Slf4j	//引入日志
public class SystemException {
	
	@ExceptionHandler(RuntimeException.class) // 表示只对"运行时异常"有效
	@ResponseBody
	public SysResult exception(Throwable throwable) {
		System.out.println("##############动用了全局异常处理类的方法!!!!!!");
		throwable.printStackTrace();	// 输出异常信息
		log.info(throwable.getMessage());	// 打印日志
		return SysResult.fail(throwable.getMessage());
	}
}

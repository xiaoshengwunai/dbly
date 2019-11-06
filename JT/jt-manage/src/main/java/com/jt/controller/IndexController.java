package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;

@Controller
public class IndexController {
	
	@Autowired
	private ItemService itemService;
	/**
	 * restFul风格
	 * 	语法:
	 * 		1. 参数必须使用/分隔
	 * 		2. 参数使用{}包裹
	 * 		3. @PathVariable注解修饰参数
	 * 	规则:
	 * 		如果参数名称与对象的属性一致,可以直接使用对象接收
	 *
	 *	restFul一种高级应用: 同url实现不同功能?
	 * 		例子1 userById/100 查询id100
	 * 		例子2 userById/100 删除id100
	 * 		
	 * 		请求方式有4种
	 * 			get 查询
	 * 			post入库
	 * 			put 更新
	 * 			delete 删除		
	 * 	要写不同方法来接收
	 * 		RequestMethod.POST
	 * 		RequestMethod.GET
	 * 		RequestMethod.PUT
	 * 		RequestMethod.DELETE
	 * 	类似于@GetMapping()这种标签
	 * 
	 * @param moduleName
	 * @return
	 */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		return moduleName;
	}

	
//	// 如果想这么写url:localhost:8091/addUserw/100/tomcat/18/男
//	@RequestMapping("addUser/{id}/{name}/{age}/{sex}")
//	public String addUser(Integer id, String name, Integer age, String sex) {
//		
//		return "";
//	}
//	
//	/**
//	 * 查询操作
//	 * 
//	 */
//	@GetMapping("/userById")
//	public String findUser() {
//		
//		return "";
//	}
	/**
	 * 添加操作
	 */
//	@RequestMapping(value = "/",RequestMethod.POST)
//	public String addUser() {
//		
//	}


}

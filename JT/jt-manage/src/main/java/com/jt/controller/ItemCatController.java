package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.anno.Cache_Find;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * url:"/item/cat/queryItemName",
	 */
	
	@ResponseBody
	@RequestMapping("/queryItemName")
	public String findItemCatById(Long itemCatId) {
		String name = itemCatService.findItemCatById(itemCatId);
//		System.out.println("###############"+name);
		return name;
				
	}
	
	/**
	 * 一
	 * 1.url:/item/cat/list
	 * 2. 返回结果 List<EasyUITree>
	 * 	业务思想:
	 * 		只查询一节商品分类信息
	 * 二
	 * parent_id = 0 
	 * 三 参数介绍
	 * @RequestParam 参数介绍
	 * 作用 页面传参与接收参数名称不一致时
	 * 	name/value 接收用户提交参数 
	 * 	defaultValue设置默认值 
	 * 	requeired 是否必须传 默认false
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITree> doFindList(@RequestParam(value = "id",defaultValue = "0")Long parentId){	// 也可以写name 和value效果相同
		// 调用数据操作
		List<EasyUITree> list = itemCatService.findEasyUITree(parentId);
//		List<EasyUITree> list = itemCatService.findEasyUITreeCache(parentId);	// 写aop了 就不用这个方法了
		return list;
	}	
}
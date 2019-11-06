package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@RestController
@RequestMapping("/web/item")
public class WebItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 编辑jt-mage 完成数据获取
	 *findItemById?id=10086
	 * @param id
	 * @return
	 */
	@RequestMapping("/findItemById")
	public Item findItemById(Long id) {
		System.out.println("商品显示manage控制 进入方法" + id);
		Item item =itemService.findItemById(id) ;
		System.out.println("商品展示 后台 控制 返回item" + item);
		return item; 
	}
	@RequestMapping("/findItemDescById")
	public ItemDesc findItemDescById(Long id) {
		ItemDesc itemDesc = itemService.findItemDescById(id); 
		System.out.println("后台控制desc" + id + "###" + itemDesc);
		return itemDesc; 
	}
	
}

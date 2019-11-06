package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@RequestMapping("/{itemId}")
	public String findItemById(@PathVariable Long itemId  , Model model) {
		Item item = itemService.findItemById(itemId);
		System.out.println("商品显示web控制2");
		ItemDesc itemDesc = itemService.findItemDescById(itemId);
		System.out.println("商品显示web控制3");
		model.addAttribute("item",item);
		System.out.println("商品显示web控制4");
		model.addAttribute("itemDesc", itemDesc);
		System.out.println("商品显示web控制5" + item);
		return "item";
	}
	@RequestMapping("/123")
	public String findItemById2() {
		return "item";
	}
	
//	@RequestMapping("/")
//	public String findItemById56445() {
//		return "item";
//	}
	
}

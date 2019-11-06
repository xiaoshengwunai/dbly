package com.jt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private HttpClientService httpClient;
	@Override
	public Item findItemById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemById";
		Map<String,String> params = new HashMap<String, String>();
		params.put("id", itemId+"");
		String result = httpClient.doGet(url,params);
		Item item = ObjectMapperUtil.toObject(result,Item.class); 
		return item;
	}
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		System.out.println("商品详情 业务1" + itemId);
		String url = "http://manage.jt.com/web/item/findItemDescById";
		System.out.println("商品详情 业务2");
		Map<String,String> params = new HashMap<String, String>();
		System.out.println("商品详情 业务3");
		params.put("id", itemId+"");
		System.out.println("商品详情 业务4");
		String result = httpClient.doGet(url,params);
		System.out.println("商品详情 业务5" + result);
		return ObjectMapperUtil.toObject(result,ItemDesc.class);
	}

	
	
	
}

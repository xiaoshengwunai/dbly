package com.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;

public class TestJSON {

	//jackson包里的玩意,先记住吧...
	private final static ObjectMapper MAPPER = new ObjectMapper();
	/**
	 * 对象转JSON串
	 * @throws IOException 
	 */
	@Test
	public void toJSON() throws IOException {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(100L)
		.setItemDesc("这是商品详情")
		.setCreated(new Date())
		.setUpdated(itemDesc.getCreated());
		String json = MAPPER.writeValueAsString(itemDesc);
		System.out.println(json);

		/**
		 * 将json转化为对象
		 */
		ItemDesc itemDesc2;
		itemDesc2 = MAPPER.readValue(json, ItemDesc.class);
		System.out.println(itemDesc2);
	}

	@Test
	public void testList() throws IOException {
		ItemDesc itemDesc1 = new ItemDesc();
		itemDesc1.setItemId(100L)
		.setItemDesc("这是商品详情")
		.setCreated(new Date())
		.setUpdated(itemDesc1.getCreated());
		ItemDesc itemDesc2 = new ItemDesc();
		itemDesc2.setItemId(100L)
		.setItemDesc("这是商品详情")
		.setCreated(new Date())
		.setUpdated(itemDesc2.getCreated());

		List list = new ArrayList();
		list.add(itemDesc1);
		list.add(itemDesc2);
		String json = MAPPER.writeValueAsString(list);
		System.out.println(json);


		// 转回来
		List list2 = MAPPER.readValue(json, list.getClass());
		System.out.println(list2);
	}
}
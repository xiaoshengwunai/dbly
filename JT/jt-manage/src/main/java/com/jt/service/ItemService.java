package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {


	/**
	 * 修改:增加"商品详情"save功能
	 */
	void saveItem(Item item, ItemDesc itemDesc);

	void updateItem(Item item,ItemDesc itemDesc);

	void deleteItem(Long[] ids);

	void updateItemState(int status, Long[] ids);
	
	EasyUITable findItemByPage(Integer page, Integer rows);

	/**
	 * "回显"
	 * @param itemId
	 * @return
	 */
	ItemDesc findItemDescById(Long id);

	Item findItemById(Long id);
}

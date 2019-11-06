package com.jt.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		// 获取商品的记录总数
		int total = itemMapper.selectCount(null);
		// 获取分页后的数据
		// sql: select # from tb_item limit 起始页下标,查询条数
		// 第一页: select * from tb_item limit 0,20;
		// 第二页: select * from tb_item limit 20,20
		// 第三页:												40,			20
		// n														(n-1)*rows, 20
		int start = (page - 1) * rows;
		List<Item> itemList = itemMapper.findItemByPage(start,rows);
		
		return new EasyUITable(total,itemList);
	}

	@Override
	@Transactional
	public void saveItem(Item item, ItemDesc itemDesc) {
		//"商品"赋值+入库
		item.setStatus(1)	// 上下架状态
				.setCreated(new Date())
				.setUpdated(item.getCreated());
		itemMapper.insert(item);	
		//"商品详情"赋值+入库
		itemDesc.setItemId(item.getId())
					.setCreated(item.getCreated())
					.setUpdated(item.getCreated());
		System.out.println("业务层的itemDesc是" + itemDesc);
		itemDescMapper.insert(itemDesc);
	}

	@Override
	@Transactional
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		itemDesc.setItemId(item.getId());
		System.out.println("更新详情,业务层拿到数据itemDesc" + itemDesc);
		itemDescMapper.updateById(itemDesc);
//		int i = 0/0;
	}

	/**
	 * 能专为list集合,但不能map 要知道...
	 */
	@Override
	@Transactional
	public void deleteItem(Long[] ids) {
		// 参数的数组转换为List集合
		List<Long> idList = Arrays.asList(ids);
		itemDescMapper.deleteBatchIds(idList);
		itemMapper.deleteBatchIds(idList);
	}

	
	/**
	 * "修改"操作一般要单独做,不建议批量!
	 * plus压根就不提供批量修改,就说明了一些问题
	 */
	@Override
	public void updateItemState(int status, Long[] ids) {
		for (Long id : ids) {
			// 修改其中不为null的数据,id作条件
			Item item = new Item();	// 需要一个实体对象
			item.setId(id)
				.setStatus(status)
				.setUpdated(new Date());
			itemMapper.updateById(item);
		}
	
	}
	

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		ItemDesc itemDesc = itemDescMapper.selectById(itemId);
		return itemDesc;
	}

	@Override
	public Item findItemById(Long id) {
		return itemMapper.selectById(id);
	}
	
	
	
	
	
	
}

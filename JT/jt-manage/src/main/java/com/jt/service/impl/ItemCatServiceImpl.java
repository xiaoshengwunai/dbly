package com.jt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.anno.Cache_Find;
import com.jt.config.RedisConfig;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMapper;
//	@Autowired
//	private RedisConfig redisConfig;


	/**
	 * 另一种实现方法是自己写个Mapper方法
	 * 这里用了MabatisPlus中的方法
	 */
	@Override
	public String findItemCatById(Long itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		String name = itemCat.getName();
		return name;
	}


	/**
	 * 思路:
	 * 	根据parentId查询以及商品分类信息List<ItemCat>
	 *	 
	 *原则 一个方法只完成一个任务
	 */
	@Override
	@Cache_Find	// 这个注解不用专门写括号写参数,不用写出来!
	public List<EasyUITree> findEasyUITree(Long parentId) {
		/**
		 * 以上复制课件的
		 * 自己写的不能正确遍历,课下再研究
		 * 找到原因了,newEasyUITree时候不能写循环之外
		 */
		// 获取以及目录的*信息的list集合
		List<ItemCat> itemCatList = findItemCatByParentId(parentId);
//		System.out.println("第一步获取的数据:" + itemCatList);	// 结果正确 看接下来的问题
		List<EasyUITree> treeList = new ArrayList<>();
		Long id;
		String name;
		String state;
		for (ItemCat itemCat : itemCatList) {
			EasyUITree easyUITree = new EasyUITree();// 这里需要多个对象,随着循环一样多,所以new语句需要写在循环内
			id = itemCat.getId();
			name = itemCat.getName();
			state = itemCat.getIsParent() ? "closed":"open";
			easyUITree.setId(id);
			easyUITree.setText(name);
			easyUITree.setState(state);
			System.out.println(easyUITree);
			treeList.add(easyUITree);
		}
//		System.out.println(treeList);
		return treeList;
	}
	
	
	/**
	 * 加个缓存!
	 * 自己手动实现!!!
	 * (测试通过)
	 * 
	 * 翌日老师讲了他的实现,更合理
	 * 		开头就声明了key和value的对象
	 * 		key值拼接了固定的标志字符串(ITEM_CAT_ + parentId)
	 * 		if判断条件是用value是否为null或"" 来判断,调用了StringUtil的API
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<EasyUITree> findEasyUITreeCache(Long parentId) {
//		List<EasyUITree> listResult = new ArrayList<EasyUITree>();
////		Jedis jedis = redisConfig.getJedis();
//		Jedis jedis = redisConfig.getJedis();
////		String key = String.valueOf(parentId);	// 老师这里拼接了别的固定字符串
//		String key = "ITEM_CAT_" + parentId;	//更聪明,简化了long转string的操作并且加上业务识别串
//		String result = jedis.get(key);
//		//先查缓存,如果缓存不存在所需数据就从数据库查,然后存入缓存
////		if (! jedis.exists (key)) {	// 老师这里用isempty
//		if (StringUtils.isEmpty(result)) {	// 老师这里用isempty
//			System.out.println("缓存没有数据,所以从数据库查!");
//			List<EasyUITree> list = findEasyUITree(parentId);
//			String json = ObjectMapperUtil.toJSON(list);
//			jedis.set(key, json);
//		}
//		//如果缓存存在有或刚刚存入就取出并return
//		result = jedis.get(key);
//		listResult = ObjectMapperUtil.toObject(result, listResult.getClass());
//		return listResult;
//	}
	
	public List<ItemCat> findItemCatByParentId(Long parentId){
		
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<ItemCat>();
		queryWrapper.eq("parent_id",parentId);
		List<ItemCat> itemCatList = itemCatMapper.selectList(queryWrapper);
		return itemCatList;
	}
	
	
}

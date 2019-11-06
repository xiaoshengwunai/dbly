package com.jt.service;

import java.util.List;

import com.jt.vo.EasyUITable;
import com.jt.vo.EasyUITree;

public interface ItemCatService{

	
	/**
	 * 在商品分类表中,根据id查找name
	 */
	String findItemCatById(Long itemCatId);

	List<EasyUITree> findEasyUITree(Long parentId);

//	List<EasyUITree> findEasyUITreeCache(Long parentId);
	
}

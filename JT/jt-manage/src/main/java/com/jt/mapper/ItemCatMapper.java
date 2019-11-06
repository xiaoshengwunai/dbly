package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

@Mapper
public interface ItemCatMapper extends BaseMapper<ItemCat>{

	/**
	* 获取商品分类表中name
	* (暂时并未使用此方法,而是使用了Mybatis Plus中的方法了)
	 */
	@Select("select name from tb_item_cat where id =#{itemCatId}")
	String findItemCatById(Long itemCatId) ;

	
	List<EasyUITree> findList();
	
}

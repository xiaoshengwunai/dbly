package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;

public interface ItemMapper extends BaseMapper<Item>{
	
	/**
	 * 以字段为参数时用${}
	 * 纯值用#{}
	 * @param start
	 * @param rows
	 * @return
	 */
	@Select("SELECT * FROM tb_item ORDER BY updated DESC LIMIT #{start},#{rows}")
	List<Item> findItemByPage(@Param("start")int start, @Param("rows")Integer rows);
	
	/**
	 * 获取商品分类表中name
	 */
	String findNameById(Integer id) ;
	
}

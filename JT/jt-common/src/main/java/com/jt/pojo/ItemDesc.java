package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用于封装 item的description(详情信息)
 * @author Administrator
 *
 */
@Data
@Accessors(chain = true)
@TableName("tb_item_desc")
public class ItemDesc extends BasePojo{
	
	// 主键自增一定不能写,因为id必须与item的id一致
	@TableId
	private  Long itemId;
	private String itemDesc;
}

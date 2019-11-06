package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品分类数据表的POJO
 */
@Data
@Accessors(chain = true)
@TableName(value = "tb_item_cat")
public class ItemCat extends BasePojo{
	
	@TableId(type = IdType.AUTO)	// 主键自增
	private Long id;					// 商品分类id// null 和 0 不同, 对数据库的查询有影响,所以用包装类
	private Long parentId;				// 父分类id
	private String name;				//分类名称
	private Integer status;				//商品分类状态
	private Integer sortOrder;			//排序号
	private Boolean isParent;			//tinyint用这个接收 是否为父级别

	// 这里还有两个"时间" 通过继承BasePojo实现,不需要专门声明了
	
}

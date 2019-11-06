package com.jt.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *	这个vo类 呈现数据用的table的vo对象
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EasyUITable implements Serializable{
	/**
	 *	数据转化JSON串时,要调用属性的getter
	 *	getTotal() 去掉get 首字母小写 生成key
	 *	value 利用get方法获取的值作为value
	 *
	 *	Json转回为对象是调用的对象的setter
	 */
	private Integer total;
	private List<?> rows;
	
	
	
}

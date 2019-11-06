package com.cy.pj.sys.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PageObject<T> implements Serializable {

	private static final long serialVersionUID = 8726325331175819586L;
	private Integer pageCurrent;// 当前页数
	private Integer pageSise;	// 当前页显示信息数
	private Integer rowCount;	// 信息条数
	private Integer pageCount;	// 信息页数
	
	private List<T> records;	// 数据对象
}

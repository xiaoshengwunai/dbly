package com.cy.pj.sys.common.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class Node implements Serializable {

	private static final long serialVersionUID = 1714693030263932206L;
	
	private Integer id;
	private String name;
	private Integer parentId;
}

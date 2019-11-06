package com.jt.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITree implements Serializable{

	private static final long serialVersionUID = -6430564299899005614L;
	// 节点id
	private Long id;// 为毛用Long,因为库中已经是Long了,省转换
	// 节点名称
	private String text;
	// 节点状态 open/closed
	private String state;
}

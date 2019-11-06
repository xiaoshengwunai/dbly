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
public class EasyUIImage implements Serializable {
	
	private Integer error = 0;	// 0默认,正常.1,失败.
	private String url;				// 网络访问的虚拟地址
	private Integer width;
	private Integer height;
}

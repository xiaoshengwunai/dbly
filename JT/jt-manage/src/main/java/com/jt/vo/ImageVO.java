package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ImageVO {			// 课件上明明为EasyUIImage.java
	
	private Integer error = 0; 	//0没错,1有错
	private String url;				//虚拟空间路径地址
	private Integer width;		// 宽度
	private Integer height;		// 高度
	
	/**
	 * 指定失败的方法
	 * @param error 状态码
	 * @return
	 */
	public static ImageVO fail() {
		return new ImageVO(1, null, null, null);
	}	
}

package com.jt.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @JsonIgnoreProperties(ignoreUnknown=true) 
 * 忽略未知属性!
 * 告诉Json转换,当你发现转换不了的时候,别逼逼,忽略就行(缺少一个set)
 * 
 * 这个注解经常用于爬虫!
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性!!!
@TableName("tb_item")
@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BasePojo{
	@TableId(type=IdType.AUTO)
	private Long id;				//商品id
	private String title;			//商品标题
	private String sellPoint;		//商品卖点信息
	private Long   price;			//商品价格 Long > dubbo 1, 运算速度考量 2.精度
	private Integer num;			//商品数量
	private String barcode;			//条形码
	private String image;			//商品图片信息   1.jpg,2.jpg,3.jpg
	private Long   cid;				//表示商品的分类id
	private Integer status;			//1表示正常，2表示下架
	
	//为了满足页面调用需求,添加get方法
	public String[] getImages(){
		return image.split(",");
	}
}

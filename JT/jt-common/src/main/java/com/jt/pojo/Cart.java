package com.jt.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("tb_cart")
@Accessors(chain = true)
public class Cart extends BasePojo implements Serializable {

	private static final long serialVersionUID = -4430911263650119595L;
	@TableId(type = IdType.AUTO)
	private Long id;
	private Long userId;
	private Long itemId;
	private String itemTitle;
	private String itemImage;
	private Long itemPrice;
	private Integer num;
	private Date created;
	private Date updated;

}

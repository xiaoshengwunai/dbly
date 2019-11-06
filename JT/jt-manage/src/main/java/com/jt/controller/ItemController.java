package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@ResponseBody
	@RequestMapping("/query")
	public EasyUITable findItemByPage(Integer page,Integer rows) {
		EasyUITable easyUITable = itemService.findItemByPage(page, rows);
//		System.out.println("控制层拿到数据:"+page+rows+"返回数据:"+ easyUITable);
		return easyUITable;
	}

	/**
	 * 业务需求:
	 * url item/save
	 * id=1&title
	 * 
	 * 修改:
	 * 	参数列表新增了@ItemDesc
	 * @ItemDesc "商品详情"的pojo
	 */
	@ResponseBody
	@RequestMapping("/save")  
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
//		System.out.println("更新-控制层拿到前端'详情':" + itemDesc);
		itemService.saveItem(item,itemDesc); 
		return SysResult.success();
	}

	/**
	 * "修改"商品信息
	 *  规则:一般都需要通过主键进行修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		System.out.println("更新商品-控制层获取两个数据" + item + itemDesc);
		try {
			itemService.updateItem(item,itemDesc);
		} catch (Exception e) {
			return SysResult.fail("错了啊");
		}
		return SysResult.success();
	}

	/**
	 * 删除(批量)item信息
	 * url:/item/delete
	 * ids: id id2 id3
	 * SysResult
	 * 
	 * 关于参数:
	 * SpringMVC的功能,可以String[] 接收,也可以用Long[] 接收
	 * ,所以MVC很牛批
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItems(Long[] ids) {	
		itemService.deleteItem(ids);
		return SysResult.success();
	}

	/**
	 * "下架"
	 */
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instock(Long[] ids) {	
		int status = 2;
		itemService.updateItemState(status,ids);
		return SysResult.success();
	}
	/**
	 * "上架"
	 */
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelf(Long[] ids) {	
		int status = 1;
		itemService.updateItemState(status,ids);
		return SysResult.success();
	}
	
	/**
	 * 商品详情查询(回显)
	 */
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDescById(@PathVariable Long itemId) {
//		System.out.println("回显+控制层+获取参数" + itemId);
		ItemDesc itemDesc = itemService.findItemDescById(itemId);
//		System.out.println("回显-控制层" + itemId + itemDesc);
		return SysResult.success(itemDesc);
	}

	
}

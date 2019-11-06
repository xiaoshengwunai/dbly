package com.jt.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.service.DubboCartService;
import com.jt.dubbo.service.DubboOrderService;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.util.ThreadLocalUtil;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Reference
	private DubboCartService cartService;
	@Reference
	private DubboOrderService orderService;

	/**
	 * 实现订单页面的跳转 www.jt.com/order/create.html
	 * f回显数据说明:${carts}
	 */
	@RequestMapping("/create")
	public String create(Model model) {
		Long userId = ThreadLocalUtil.get().getId();
		List<Cart> carts = cartService.findCartList(userId);
		/**
		 * 为毛用model?
		 * 因为要把数据带到jsp中
		 */
		model.addAttribute("carts", carts);
		return "order-cart";
	}
	
	/**
	 * 业务说明:
	 * 		完成订单入库操作,并且返回orderId
	 * 		自己动态生成一个OrderId uuid方式
	 * 		同时实现三张表入库(事务控制)
	 * 		
	 * 斜杠submit
	 */
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order) {
		Long userId = ThreadLocalUtil.get().getId();
		String orderId = orderService.saveOrder(order, userId);
		System.out.println("订单 提交 控制" + order + orderId);
		return SysResult.success(orderId);
	}
	
	/**
	 * 如果成功要完成这个herf
	 * "/order/success.html?id="+result.data;
	 * 
	 */
	@RequestMapping("/success")
	public String displaySuccess(Model model, String id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 根据id获取order对象
		Order order = orderService.findOrderById(id);
		String dateString = sdf.format(new Date(order.getCreated().getTime() + (long)4 * 24 * 60 * 60 * 1000));
		model.addAttribute("order",order);
		model.addAttribute("date",dateString);
		return "success";
	}
	
	
	
}

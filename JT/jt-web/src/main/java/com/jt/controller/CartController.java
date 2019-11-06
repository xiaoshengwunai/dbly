package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.service.DubboCartService;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.util.ThreadLocalUtil;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {


	@Reference(check = false)	// 不需要校验
	private DubboCartService dubboCartService;

	/**
	 * items = "${cartList}"
	 * 查询用户的全部购物记录信息
	 */
	@RequestMapping("/show")
	public String show(Model model) {
//		User user = (User) request.getAttribute("JT_USER");
		User user = ThreadLocalUtil.get();
		Long userId = user.getId();
		List<Cart> list = dubboCartService.findCartList(userId);
		model.addAttribute("cartList", list);
		return "cart";
	}
	
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(Cart cart, HttpServletRequest request) {	//这里讲了可以用cart
		
//		User user = (User) request.getAttribute("JT_USER");
		User user = ThreadLocalUtil.get();
		Long userId = user.getId();
		cart.setUserId(userId);
		dubboCartService.updateCartNum(cart);
		return SysResult.success();
	}
	
	
	/**
	 * 删除购物车信息
	 * userId+itemId共同指向一次删除
	 * @param cart
	 * @return
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(Cart cart, HttpServletRequest request) {	//这里讲了可以用cart
//		User user = (User) request.getAttribute("JT_USER");
		User user = ThreadLocalUtil.get();
		Long userId = user.getId();
		cart.setUserId(userId);
		dubboCartService.deleteCartItem(cart);
//		return "cart";	// 不能写cart,实际是一种业务的转发
		return "redirect:/cart/show.html";
	}
	
	/**
	 * 加购物车,完成后跳转到购物车展现页面
	 */
	@RequestMapping("/add/{itemId}")
	public String saveCartItem(Cart cart, HttpServletRequest request) {
//		User user = (User) request.getAttribute("JT_USER");
		User user = ThreadLocalUtil.get();
		Long userId = user.getId();
		cart.setUserId(userId);
		dubboCartService.saveCartItem(cart);
		return "redirect:/cart/show.html";
	}
	
	
	
}

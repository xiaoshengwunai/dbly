package com.jt.dubbo.service;

import java.util.List;

import com.jt.pojo.Cart;

public interface DubboCartService {

	List<Cart> findCartList(Long userId);

	void updateCartNum(Cart cart);

	void deleteCartItem(Cart cart);

	void saveCartItem(Cart cart);


}

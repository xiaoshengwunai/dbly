package com.jt.dubbo.service;

import com.jt.pojo.Order;

public interface DubboOrderService {

	String saveOrder(Order order, Long userId);

	Order findOrderById(String orderId);

}

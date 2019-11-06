package com.jt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.dubbo.service.DubboOrderService;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;


@Service
public class OrderServiceImpl implements DubboOrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;

	/**
	 * 需要返回一个OrderId (uuid方式生成)
	 *
	 */
	@Override
	@Transactional	// 要求三张表同时入库,原子性操作
	public String saveOrder(Order order, Long userId) {
		// 生成订单号
		int uuid = UUID.randomUUID().toString().hashCode();
		if (uuid < 0) uuid = -uuid;
		String orderId = "715180" + String.valueOf(uuid);
		// 设置创建时间
		order.setOrderId(orderId);
		order.setCreated(new Date())
				.setUpdated(order.getCreated());
		Date CreatedDate = order.getCreated();
		order.getOrderShipping().setOrderId(orderId)
											.setCreated(CreatedDate)
											.setCreated(CreatedDate);
		order.setStatus(1);		// 订单状态,表示已付款!
		order.setUserId(userId);
		// 写数据库
		orderMapper.insert(order);
		List<OrderItem> orderItems= order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId)
							.setCreated(CreatedDate)
							.setUpdated(CreatedDate);
			orderItemMapper.insert(orderItem);
		}
		orderShippingMapper.insert(order.getOrderShipping());
		return orderId;
	}

	@Override
	public Order findOrderById(String orderId) {
		QueryWrapper<Order> queryWrapper = new QueryWrapper<Order>();
		queryWrapper.eq("order_id", orderId);
		Order order = orderMapper.selectOne(queryWrapper);
		return order;
	}
	
}

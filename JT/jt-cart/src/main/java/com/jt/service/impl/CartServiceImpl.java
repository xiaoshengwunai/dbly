package com.jt.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.dubbo.service.DubboCartService;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import com.jt.vo.SysResult;
@Service
public class CartServiceImpl implements DubboCartService {

	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCartList(Long userId) {
		
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
		queryWrapper.eq("user_id", userId);

		List<Cart> list = cartMapper.selectList(queryWrapper);
		
		return list ;
	}

	/**
	 * update tb_cart set num = #{num}
	 * updated = #{updated}
	 * where user_id=#{userId} and item_id=#{itemId}
	 *
	 */
	@Override
	public void updateCartNum(Cart cart) {
		Cart cartTemp = new Cart();
		cartTemp.setNum(cart.getNum())
					.setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<Cart>();
		updateWrapper.eq("item_id", cart.getItemId())
							.eq("user_id", cart.getUserId());
		/**
		 * 第一个参数entity是要修改的数据
		 * 第二个参数updateWrapper
		 */
		cartMapper.update(cartTemp, updateWrapper);
	}

	/**
	 * "删除购物车中的商品"
	 *	由于mybatis plus的特殊固资额: 需要根据对象中不为空的属性操作!
	 */
	@Override
	public void deleteCartItem(Cart cart) {
//		Cart cartTemp = new Cart();
//		cartTemp.setUserId(cart.getUserId())
//					 .setItemId(cart.getItemId());
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>(cart);	//老师这里竟然直接传进去了! 下边方法有解释!
//		queryWrapper.eq("user_id", cart.getUserId())
//						   .eq("item_id", cart.getItemId());
		int rows = cartMapper.delete(queryWrapper);
		System.out.println("删除购物车 后台 业务实现" + rows + cart.getItemId());
	}

	/**
	 * 判断购物车中是否已有记录
	 * 如果为空,则新增
	 * 如果有相同的item,则需要update num updated等
	 *
	 */
	@Override
	public void saveCartItem(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		/**
		 * 这里为毛不能把cart直接传参进去呢?这里不能!因为mybatis会把所有传入数据当作where条件!
		 * 我们只需要UserId和itemId当作where条件,不能多别的条件!!
		 */
		queryWrapper.eq("user_id",cart.getUserId())
							.eq("item_id", cart.getItemId());
		Cart cartDB = cartMapper.selectOne(queryWrapper);
		if (cartDB == null) {	//说明该用户第一次新增该itemid的商品!
			cart.setCreated(new Date())
				.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		} else {	// 说明该用户购物车已经有了某些数量的该itemid的商品了,要更新操作
			int num = cartDB.getNum() + cart.getNum();
			cartDB.setNum(num);
//					.setCreated(new Date());
			/**
			 * 其实这个更新操作效率不高,并非因为使用mybatis plus的原因,有别的原因!
			 * sql: update tb)cart set ....除了id外都要set一遍
			 * 但实际上我们要更新的数据只有num和updated
			 * 做法1: 设置以下临时对象,并且取消上边代码中对created时间的设置
			 * 只更新我们以下set的数据,不需要用之前从DB拿到的完整对象,做多余更新!
			 */
			Cart cartTemp = new Cart();
			cartTemp.setId(cartDB.getId())
						.setNum(num)
						.setUpdated(new Date());
			cartMapper.updateById(cartTemp);
		}
		
	}
	
	
	
	
	
	
	
}

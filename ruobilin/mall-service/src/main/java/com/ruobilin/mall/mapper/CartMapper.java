package com.ruobilin.mall.mapper;

import java.util.List;

import com.ruobilin.mall.entity.Cart;

public interface CartMapper {

	public void insertCart(Cart cart);
	
	public void deleteCart(String id);
	
	public List<Cart> queryCart(String userId);
	
	/**
	 * 根据用户ID删除购物车数据
	 */
	public void delectCartUserId(String userId);
}

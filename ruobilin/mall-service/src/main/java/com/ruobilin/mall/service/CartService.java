package com.ruobilin.mall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.entity.Cart;
import com.ruobilin.mall.mapper.CartMapper;

@Service
public class CartService {
	
	@Autowired
	private CartMapper cartMapper;
	
	public List<Cart> queryCart(String userId){
		return cartMapper.queryCart(userId);
	}
	
	/**
	 * 新增
	 * @param cart
	 */
	public void insertCart(Cart cart){
		cartMapper.insertCart(cart);
	}
	
	/**
	 * 根据用户ID删除购物车数据
	 * @param userId
	 */
	public void delectCartUserId(String userId){
		cartMapper.delectCartUserId(userId);
	}
	
}

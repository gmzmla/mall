package com.ruobilin.mall.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.entity.CartProduct;
import com.ruobilin.mall.mapper.CartProductMapper;

@Service
public class CartProductService {
	
	@Autowired
	private CartProductMapper cartProductMapper;
	
	public List<CartProduct> queryCartProduct(String cartId){
		return cartProductMapper.queryCartProduct(cartId);
	}
	 
	/**
	 * 删除购物车一种商品
	 * @param productId
	 * @param userId
	 */
	public void deleteCartProduct(String id){
		Map<String,String> map=new HashMap<String, String>();
		map.put("id", id);
		cartProductMapper.deleteCartProduct(map);
	}
	
	/**
	 * 更新购物车中的商品数量
	 * @param map
	 */
	public void updateCartProduct(Map<String,Object> map){
		cartProductMapper.updateCartProduct(map);
	}
	
}

package com.ruobilin.mall.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.entity.CartProduct;

public interface CartProductMapper {

	public void insertCartProduct(CartProduct cartProduct);
	
	public void deleteCartProduct(Map<String,String> map);
	
	public void updateCartProduct(Map<String,Object> map);
	public void updateCartProductCount(Map<String,String> map);
	
	/**
	 * 根据购物车ID查询购物车商品表
	 * @param cartId
	 * @return
	 */
	public List<CartProduct> queryCartProduct(String cartId);
	
	
}

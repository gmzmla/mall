package com.ruobilin.mall.mapper;

import java.util.List;

import com.ruobilin.mall.entity.CartProductProperty;

public interface CartProductPropertyMapper {

	public void insertCartProductProperty(List<CartProductProperty> list);
	
	public void deleteCartProductProperty();
	
	public List<CartProductProperty> queryCartProductProperty(String cartProductId);
	
	public List<String> queryCartProductPropertyId(String cartProductId);
}

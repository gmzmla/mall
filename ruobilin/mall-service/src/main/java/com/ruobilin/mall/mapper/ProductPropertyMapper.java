package com.ruobilin.mall.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.entity.ProductProperty;

public interface ProductPropertyMapper {

	List<ProductProperty> findByProductId(Long id);

	void deleteByProductId(Long id);

	void insert(ProductProperty pp);
	
	/**
	 * 查询购物车商品属性
	 * @param map 包含ids	商品属性ID ，pid 商品ID
	 * @return
	 */
	List<ProductProperty> queryShoppingCarProductProperty(Map<String,Object> map);

}

package com.ruobilin.mall.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.entity.ProductPrice;

public interface ProductPriceMapper {

	List<ProductPrice> findByProductId(Long id);

	void deleteByProductId(Long id);

	void insert(ProductPrice pp);
	
	List<ProductPrice> queryShoppingCarProductPrice(Map<String,Object> map);

}

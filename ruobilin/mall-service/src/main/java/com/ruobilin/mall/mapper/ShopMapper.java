package com.ruobilin.mall.mapper;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ruobilin.mall.entity.Page;
import com.ruobilin.mall.entity.Product;
import com.ruobilin.mall.entity.Shop;

public interface ShopMapper {

	List<Shop> findAll(String name, PageBounds pageBounds);

	void insert(Shop shop);

	void update(Shop shop);

	void deleteById(Long id);

	Shop getById(Long id);
	
	List<Shop> getAll(Page page);
	
	List<Map> findlist();
	
	List<Product> getProductByShopId(Long id);

}

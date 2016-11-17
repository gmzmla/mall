package com.ruobilin.mall.mapper;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ruobilin.mall.entity.Order;

public interface OrderMapper {

	void insert(Order o);

	List<Order> findAll(Long userId, PageBounds pageBounds);

	/**
	 * 查询购物车中的数据
	 * @param userId	用户ID
	 * @return
	 */
	List<Map<String,Object>> queryCartList(String userId);
	
	/**
	 * 根据商品ID查询商品属性
	 * @param commodityId
	 * @return
	 */
	List<Map<String,String>> queryCommodityProperty(String commodityId);
}

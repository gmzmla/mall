package com.ruobilin.mall.mapper;

import java.util.List;

import com.ruobilin.mall.entity.OrderProduct;

public interface OrderProductMapper {

	void insert(OrderProduct p);

	List<OrderProduct> findByOrderId(String oid);

}

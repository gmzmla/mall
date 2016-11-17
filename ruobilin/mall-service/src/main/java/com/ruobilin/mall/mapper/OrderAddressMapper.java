package com.ruobilin.mall.mapper;

import com.ruobilin.mall.entity.OrderAddress;

public interface OrderAddressMapper {

	void insert(OrderAddress address);

	OrderAddress getByOrderId(String oid);

}

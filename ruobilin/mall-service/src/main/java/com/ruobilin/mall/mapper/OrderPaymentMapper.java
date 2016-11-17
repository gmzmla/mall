package com.ruobilin.mall.mapper;

import java.util.List;

import com.ruobilin.mall.entity.OrderPayment;

public interface OrderPaymentMapper {

	void insert(OrderPayment p);

	List<OrderPayment> findByOrderId(String oid);

}

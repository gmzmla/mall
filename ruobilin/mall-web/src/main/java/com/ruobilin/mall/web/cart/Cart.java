package com.ruobilin.mall.web.cart;

import java.util.List;

import com.ruobilin.mall.entity.OrderProduct;

public interface Cart {
	void addToCart(Object session, Long userId, OrderProduct pc);
	
	List<OrderProduct> getProducts(Object session, Long userId);
	
	void removeByProductId(Object session, Long userId, Long productId);
	
	void removeAt(Object session, Long userId, int index);
	
	void removeAll(Object session, Long userId);
}

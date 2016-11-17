package com.ruobilin.mall.controller.model;

import java.util.List;

import com.ruobilin.mall.entity.OrderProduct;
import com.ruobilin.mall.entity.Shop;

public class ShopProduct {
	private Shop shop;
	private Integer expressFee;
	private List<OrderProduct> products;
	public Integer getExpressFee() {
		return expressFee;
	}
	public void setExpressFee(Integer expressFee) {
		this.expressFee = expressFee;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<OrderProduct> getProducts() {
		return products;
	}
	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}
	
}

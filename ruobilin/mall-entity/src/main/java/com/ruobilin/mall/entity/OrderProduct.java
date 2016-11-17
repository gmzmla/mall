package com.ruobilin.mall.entity;

public class OrderProduct {
	private Long productId;
	private String orderId;
	private Long shopId;
	private String priceGroup;
	private int price;
	private int count;
	private String name;
	private String smallUrl;
	private int expressFee;
	public OrderProduct() {
		
	}
	
	public OrderProduct(Long id, String pg, Integer count, int price, int expressFee, String name, String smallUrl, Long shopId) {
		this.productId = id;
		this.priceGroup = pg;
		this.count = count;
		this.price = price;
		this.name = name;
		this.smallUrl = smallUrl;
		this.expressFee = expressFee;
		this.shopId = shopId;
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPriceGroup() {
		return priceGroup;
	}
	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSmallUrl() {
		return smallUrl;
	}
	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}

	public int getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(int expressFee) {
		this.expressFee = expressFee;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
	
}

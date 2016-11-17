package com.ruobilin.mall.entity;

import java.util.List;

public class CartProduct {
	private String id;
	private String cartId;	//购物车ID
	private String productId;	//商品ID
	private String productCount;	//商品数量
	private String tableName;
	
	private List<CartProductProperty> cppList;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductCount() {
		return productCount;
	}
	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}
	public List<CartProductProperty> getCppList() {
		return cppList;
	}
	public void setCppList(List<CartProductProperty> cppList) {
		this.cppList = cppList;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}

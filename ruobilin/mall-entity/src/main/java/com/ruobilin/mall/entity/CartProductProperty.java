package com.ruobilin.mall.entity;

public class CartProductProperty {
	private String id;
	private String propertyId;	//属性ID
	private String cartProductId;	//购物车商品表ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public String getCartProductId() {
		return cartProductId;
	}
	public void setCartProductId(String cartProductId) {
		this.cartProductId = cartProductId;
	}
	
}

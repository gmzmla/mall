package com.ruobilin.mall.entity;

import java.util.Date;
import java.util.List;

public class Order {
	private String id;
	private Long productId;
	private Integer productCount;
	private Long userId;
	private Integer price;
	private Integer expressFee;
	private Integer coupon;
	private Date created;
	private Integer status;
	private String remark;
	
	private OrderAddress address;
	private List<OrderPayment> payments;
	private List<OrderProduct> products;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getExpressFee() {
		return expressFee;
	}
	public void setExpressFee(Integer expressFee) {
		this.expressFee = expressFee;
	}
	public Integer getCoupon() {
		return coupon;
	}
	public void setCoupon(Integer coupon) {
		this.coupon = coupon;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public OrderAddress getAddress() {
		return address;
	}
	public void setAddress(OrderAddress address) {
		this.address = address;
	}
	public List<OrderPayment> getPayments() {
		return payments;
	}
	public void setPayments(List<OrderPayment> payments) {
		this.payments = payments;
	}
	public List<OrderProduct> getProducts() {
		return products;
	}
	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}
	
	
}

package com.ruobilin.mall.entity;

import java.util.Date;

public class UserCoupon {
	private Long id;
	private Long userId;
	private String name;
	private Integer price;
	private Integer type;
	private String remark;
	private Date created;
	private Integer expiryDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Integer getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Integer expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
}

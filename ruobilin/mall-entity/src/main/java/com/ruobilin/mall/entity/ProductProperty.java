package com.ruobilin.mall.entity;

public class ProductProperty {
	private Long id;
	private Long productId;
	private Long categoryPropertyId;
	private String property;
	private String value;
	private String type;
	private Integer extend;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getCategoryPropertyId() {
		return categoryPropertyId;
	}
	public void setCategoryPropertyId(Long categoryPropertyId) {
		this.categoryPropertyId = categoryPropertyId;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getExtend() {
		return extend;
	}
	public void setExtend(Integer extend) {
		this.extend = extend;
	}
}

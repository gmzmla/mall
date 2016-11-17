package com.ruobilin.mall.entity;

import java.util.Date;
import java.util.List;

public class Category {
	private Long id;
	private String name;
	private Long parentId;
	private int productCount;
	private Date created;
	private Long userId;
	private String code;
	
	private List<Category> children;
	private List<CategoryProperty> categoryProperties;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<CategoryProperty> getCategoryProperties() {
		return categoryProperties;
	}
	public void setCategoryProperties(List<CategoryProperty> categoryProperties) {
		this.categoryProperties = categoryProperties;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<Category> getChildren() {
		return children;
	}
	public void setChildren(List<Category> children) {
		this.children = children;
	}
	
	
}

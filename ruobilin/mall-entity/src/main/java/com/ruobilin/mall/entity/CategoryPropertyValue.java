package com.ruobilin.mall.entity;

public class CategoryPropertyValue {
	private String id;	//属性值ID
	private String categoryPropertyId;	//分类属性ID
	private String name;	//值
	private String code;	//值首字母
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryPropertyId() {
		return categoryPropertyId;
	}
	public void setCategoryPropertyId(String categoryPropertyId) {
		this.categoryPropertyId = categoryPropertyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}

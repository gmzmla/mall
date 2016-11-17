package com.ruobilin.mall.admin.entity;

/**
 * 商品属性表
 */
public class CommodityProperty {
	private String id;
	private String commodityId;	//商品ID
	private String propertyId;	//属性ID
	private String propertyValue;	//属性值ID
	private String commodityBasicId;	//商品基本信息表ID
	
	private String name;	//商品属性名称
	private String value;	//商品属性值
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getCommodityBasicId() {
		return commodityBasicId;
	}
	public void setCommodityBasicId(String commodityBasicId) {
		this.commodityBasicId = commodityBasicId;
	}
	
}

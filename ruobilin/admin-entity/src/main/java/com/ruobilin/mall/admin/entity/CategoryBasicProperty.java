package com.ruobilin.mall.admin.entity;

/**
 *	所选类目属性与值表
 */
public class CategoryBasicProperty {
	private String id;
	private String commodityBasicId;	//商品基本信息表ID
	private String propertyId;	//类目属性ID
	private String valueId;	//类目属性值ID
	
	private String property;	//类目属性
	private String value;	//类目属性值
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCommodityBasicId() {
		return commodityBasicId;
	}
	public void setCommodityBasicId(String commodityBasicId) {
		this.commodityBasicId = commodityBasicId;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public String getValueId() {
		return valueId;
	}
	public void setValueId(String valueId) {
		this.valueId = valueId;
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
	
	
}

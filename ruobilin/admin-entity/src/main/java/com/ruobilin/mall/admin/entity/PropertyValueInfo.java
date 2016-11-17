package com.ruobilin.mall.admin.entity;

/**
 * 商品销售属性值表
 */
public class PropertyValueInfo {
	private String id;
	private String propertyId;	//商品销售属性表ID
	private String mark;	//是否选中标识：0未选中、1选中
	private String value;	//销售属性值
	private String commodityBasicId;	//商品销售属性值
	
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
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCommodityBasicId() {
		return commodityBasicId;
	}
	public void setCommodityBasicId(String commodityBasicId) {
		this.commodityBasicId = commodityBasicId;
	}
	
	
}

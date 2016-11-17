package com.ruobilin.mall.admin.entity;

import java.util.List;

/**
 * 商品销售属性表
 */
public class PropertyInfo {
	private String id;
	private String commodityId;	//商品基本信息信息表ID
	private String name;	//销售属性名
	private String mark;	//是否使用标识：0未使用、1使用
	
	List<PropertyValueInfo> listPropertyValueInfo;	//商品销售属性值
	
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
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public List<PropertyValueInfo> getListPropertyValueInfo() {
		return listPropertyValueInfo;
	}
	public void setListPropertyValueInfo(
			List<PropertyValueInfo> listPropertyValueInfo) {
		this.listPropertyValueInfo = listPropertyValueInfo;
	}
	
}

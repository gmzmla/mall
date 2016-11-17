package com.ruobilin.mall.entity;

import java.util.List;
import java.util.Map;

/**
 * 商品信息VO
 */
public class CommodityInfo {
	private String id;
	private String name;	//商品名称
	private String promotionWord;	//商品推广词
//	private String shopId;	//商铺ID
//	private String created;	//创建时间
	private String content;	//商品描述
	private String price;	//商品价格
	private String stock;	//商品库存量
	
	//非表中字段
	private String shopName;	//商铺名称
	private String commodityBasicId;	//商品基本信息ID
	private List<Map<String,String>> listCommodityImage;	//商品图片
	private List<Map<String,Object>> listCommodityProperty;	//商品属性
	
	private String tableName;	//表名字
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPromotionWord() {
		return promotionWord;
	}
	public void setPromotionWord(String promotionWord) {
		this.promotionWord = promotionWord;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public List<Map<String, String>> getListCommodityImage() {
		return listCommodityImage;
	}
	public void setListCommodityImage(List<Map<String, String>> listCommodityImage) {
		this.listCommodityImage = listCommodityImage;
	}
	public String getCommodityBasicId() {
		return commodityBasicId;
	}
	public void setCommodityBasicId(String commodityBasicId) {
		this.commodityBasicId = commodityBasicId;
	}
	public List<Map<String, Object>> getListCommodityProperty() {
		return listCommodityProperty;
	}
	public void setListCommodityProperty(
			List<Map<String, Object>> listCommodityProperty) {
		this.listCommodityProperty = listCommodityProperty;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}

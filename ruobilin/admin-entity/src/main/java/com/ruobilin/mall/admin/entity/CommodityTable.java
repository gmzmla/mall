package com.ruobilin.mall.admin.entity;

import java.util.List;

/**
 * 商品表
 */
public class CommodityTable {
	private String id;
	private String commodityBasicId;	//商品基本信息表ID
	private String name;	//商品名称
	private String promotionWord;	//商品广告词
	private String price;	//商品价格
	private String stock;	//商品库存
	
	
	private List<CommodityProperty> listCommodityProperty;	//单个商品的商品属性
	private List<CommodityImage> listCommodityImage;	//单个商品的商品图片
	
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
	public List<CommodityProperty> getListCommodityProperty() {
		return listCommodityProperty;
	}
	public void setListCommodityProperty(
			List<CommodityProperty> listCommodityProperty) {
		this.listCommodityProperty = listCommodityProperty;
	}
	public List<CommodityImage> getListCommodityImage() {
		return listCommodityImage;
	}
	public void setListCommodityImage(List<CommodityImage> listCommodityImage) {
		this.listCommodityImage = listCommodityImage;
	}
	
}

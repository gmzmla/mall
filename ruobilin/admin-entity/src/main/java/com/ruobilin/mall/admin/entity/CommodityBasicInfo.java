package com.ruobilin.mall.admin.entity;

import java.util.Date;
import java.util.List;

import com.ruobilin.search.utils.DateUtils;

/**
 * 商品基本信息表
 */
public class CommodityBasicInfo {
	private String id;
	private String name;	//商品名称
	private String promotionWord;	//商品推广词
//	private String brand;	//品牌
	private String shopId;	//商铺ID
	private String userId;	//创建商品用户 ID
	private String created;	//创建时间
	private String status;	//商品状态：0已发布、1未发布、2停用
	private String content;	//商品描述
	private String categoryId;	//所属类目id
	private String price;	//商品价格
	private String stock;	//商品库存量
	
	//非表中字段
	private String userName;	//用户名称
	private String shopName;	//商铺名称
	private String categoryName;	//所属类目名称
	private List<CategoryBasicProperty> listCategoryBasicProperty;	//所属类目属性与值
	private List<PropertyInfo> listPropertyInfo;	//销售属性
	private List<PropertyValueInfo> listPropertyValueInfo;	//销售属性值
	private List<CommodityBasicImage> listCommodityBasicImage;	//商品基本信息图片
	
	private List<CommodityTable> listCommodityTable;	//单个商品信息
	private List<CommodityProperty> listCommodityProperty;	//单个商品的商品属性
	private List<CommodityImage> listCommodityImage;	//单个商品的商品图片
	
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
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCreated() {
		if(created==null||"".equals(created)){
			return DateUtils.format(new Date());
		}
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<CategoryBasicProperty> getListCategoryBasicProperty() {
		return listCategoryBasicProperty;
	}
	public void setListCategoryBasicProperty(
			List<CategoryBasicProperty> listCategoryBasicProperty) {
		this.listCategoryBasicProperty = listCategoryBasicProperty;
	}
	public List<PropertyInfo> getListPropertyInfo() {
		return listPropertyInfo;
	}
	public void setListPropertyInfo(List<PropertyInfo> listPropertyInfo) {
		this.listPropertyInfo = listPropertyInfo;
	}
	public List<PropertyValueInfo> getListPropertyValueInfo() {
		return listPropertyValueInfo;
	}
	public void setListPropertyValueInfo(
			List<PropertyValueInfo> listPropertyValueInfo) {
		this.listPropertyValueInfo = listPropertyValueInfo;
	}
	public List<CommodityTable> getListCommodityTable() {
		return listCommodityTable;
	}
	public void setListCommodityTable(List<CommodityTable> listCommodityTable) {
		this.listCommodityTable = listCommodityTable;
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
	public List<CommodityBasicImage> getListCommodityBasicImage() {
		return listCommodityBasicImage;
	}
	public void setListCommodityBasicImage(
			List<CommodityBasicImage> listCommodityBasicImage) {
		this.listCommodityBasicImage = listCommodityBasicImage;
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
	
}

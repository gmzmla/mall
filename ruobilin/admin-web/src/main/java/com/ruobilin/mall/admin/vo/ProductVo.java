package com.ruobilin.mall.admin.vo;

import java.util.List;

import com.ruobilin.mall.entity.ProductImage;
import com.ruobilin.mall.entity.ProductPrice;
import com.ruobilin.mall.entity.ProductProperty;
import com.ruobilin.mall.entity.Shop;

public class ProductVo {
	private String id;
	private String name;
	private String content;
	private String productNo;
	private String shopId;
	private String userId;
	private String userName;
	private String created;
	private String saled;
	private String grade;
	private String originalPrice;
	private String expiryDate;
	private String status;
	private String categoryId;
	private String recommend;

	private List<ProductImage> images;
	private List<ProductPrice> prices;
	private List<ProductProperty> propertis;
	private Shop shop;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
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
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getSaled() {
		return saled;
	}
	public void setSaled(String saled) {
		this.saled = saled;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public List<ProductImage> getImages() {
		return images;
	}
	public void setImages(List<ProductImage> images) {
		this.images = images;
	}
	public List<ProductPrice> getPrices() {
		return prices;
	}
	public void setPrices(List<ProductPrice> prices) {
		this.prices = prices;
	}
	public List<ProductProperty> getPropertis() {
		return propertis;
	}
	public void setPropertis(List<ProductProperty> propertis) {
		this.propertis = propertis;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}

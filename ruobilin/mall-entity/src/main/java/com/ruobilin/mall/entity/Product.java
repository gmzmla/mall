package com.ruobilin.mall.entity;

import java.util.Date;
import java.util.List;

public class Product {
	private Long id;
	private String name;
	private String content;
	private String productNo;
	private Long shopId;
	private String shopName;	//商铺名称
	private String userId;	//创建人ID
	private String userName;	//创建人name
	private Date created;
	private Integer saled;
	private Integer grade;
	private Integer originalPrice;
	private Date expiryDate;
	private Integer status;
	private Long categoryId;	//产品分类ID
	private String categoryText;	//分类 name
	private Integer recommend;
	
	private String numberProduct;	//用于购买数量存储
	private String productCartTotal;	//商品总价

	private List<ProductImage> images;
	private List<ProductPrice> prices;
	private List<ProductProperty> propertis;
	private Shop shop;
	
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
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Integer getSaled() {
		return saled;
	}
	public void setSaled(Integer saled) {
		this.saled = saled;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getRecommend() {
		return recommend;
	}
	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
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
	public String getCategoryText() {
		return categoryText;
	}
	public void setCategoryText(String categoryText) {
		this.categoryText = categoryText;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getNumberProduct() {
		return numberProduct;
	}
	public void setNumberProduct(String numberProduct) {
		this.numberProduct = numberProduct;
	}
	public String getProductCartTotal() {
		return productCartTotal;
	}
	public void setProductCartTotal(String productCartTotal) {
		this.productCartTotal = productCartTotal;
	}
	
}

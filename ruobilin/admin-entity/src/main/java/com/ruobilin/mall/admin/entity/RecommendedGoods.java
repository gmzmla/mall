package com.ruobilin.mall.admin.entity;

public class RecommendedGoods {
	private Integer id;
	private String productId;//与商品表关联id
	private Integer recommendedId;//与推荐层级表关联id
	private String goodsName;//商品名称
	private String tableName;//商品所属分类(推荐层级表节点名称)
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getRecommendedId() {
		return recommendedId;
	}
	public void setRecommendedId(Integer recommendedId) {
		this.recommendedId = recommendedId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}

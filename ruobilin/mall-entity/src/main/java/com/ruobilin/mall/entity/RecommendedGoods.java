package com.ruobilin.mall.entity;

public class RecommendedGoods {
	private Integer id;
	private Integer productId;//与商品表关联id
	private Integer recommendedId;//与推荐层级表关联id
	private String goodsName;//商品名称
	private String type;//商品所属分类(推荐层级表节点名称)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}

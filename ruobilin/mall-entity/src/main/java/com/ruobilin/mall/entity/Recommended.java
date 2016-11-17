package com.ruobilin.mall.entity;

import java.util.List;
import java.util.Map;

public class Recommended {
	private Integer id;
	private String parentId;//父级ID
	private String name;//节点名称
	private String status;//节点状态
	private String level;//节点等级
	private String createTime;//创建时间
	private String endTime;//结束时间
	
	private List<Recommended> children; //存放下一级节点
	private List<RecommendedGoods> recommendedGoods;//存放节点下的商品
	private List<Map<String,String>> homeProducts;
	
	
	
	public List<RecommendedGoods> getRecommendedGoods() {
		return recommendedGoods;
	}
	public void setRecommendedGoods(List<RecommendedGoods> recommendedGoods) {
		this.recommendedGoods = recommendedGoods;
	}
	public List<Recommended> getChildren() {
		return children;
	}
	public void setChildren(List<Recommended> children) {
		this.children = children;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public List<Map<String, String>> getHomeProducts() {
		return homeProducts;
	}
	public void setHomeProducts(List<Map<String, String>> homeProducts) {
		this.homeProducts = homeProducts;
	}
	

}

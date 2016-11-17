package com.ruobilin.mall.entity;

public class Commodity {
	private Long id;
	private String dityid;
	private String name;
	private String tableName;
	private String ordet;
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
	public String getOrdet() {
		return ordet;
	}
	public void setOrdet(String ordet) {
		this.ordet = ordet;
	}
	public String getDityid() {
		return dityid;
	}
	public void setDityid(String dityid) {
		this.dityid = dityid;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
}

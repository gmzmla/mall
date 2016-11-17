package com.ruobilin.mall.entity;

public class City {
	private Long id;
	private String enName;
	private String cnName;
	private Integer sortId;
	private Integer type;
	private Long provinceId;
	private Float beginLatitude;
	private Float beginLongitude;
	private Float endLongitude;
	private Float endLatitude;
	private String code;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public Integer getSortId() {
		return sortId;
	}
	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public Float getBeginLatitude() {
		return beginLatitude;
	}
	public void setBeginLatitude(Float beginLatitude) {
		this.beginLatitude = beginLatitude;
	}
	public Float getBeginLongitude() {
		return beginLongitude;
	}
	public void setBeginLongitude(Float beginLongitude) {
		this.beginLongitude = beginLongitude;
	}
	public Float getEndLongitude() {
		return endLongitude;
	}
	public void setEndLongitude(Float endLongitude) {
		this.endLongitude = endLongitude;
	}
	public Float getEndLatitude() {
		return endLatitude;
	}
	public void setEndLatitude(Float endLatitude) {
		this.endLatitude = endLatitude;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}

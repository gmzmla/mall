package com.ruobilin.mall.entity;

/**
 *【配送表】
 */
public class Dispatching {
	private String id;
	private String dispatchingMode;	//配送方式
	private String dispatchingDate;	//配送时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDispatchingMode() {
		return dispatchingMode;
	}
	public void setDispatchingMode(String dispatchingMode) {
		this.dispatchingMode = dispatchingMode;
	}
	public String getDispatchingDate() {
		return dispatchingDate;
	}
	public void setDispatchingDate(String dispatchingDate) {
		this.dispatchingDate = dispatchingDate;
	}
	
	
}

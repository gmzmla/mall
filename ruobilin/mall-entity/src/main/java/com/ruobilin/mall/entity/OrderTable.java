package com.ruobilin.mall.entity;

import java.util.List;

/**
 * 订单表：订单编号、总计、订单状态、用户ID、收货人、收货人地址、手机号码、支付方式、运费、送货日期、配送时间、发票类型、发票抬头、发票内容、取消订单时间、快递单号、快递公司
 */
public class OrderTable {
	private String id;
	private String orderNumber;
	private String total;
	private String orderStatus;	//"01提交订单","02付款成功","03商品出库","04等待收货","05完成"
	private String submitTime;	//订单提交时间
	private String outTime;	//商品出库时间
	private String waitTime;	//等待收货时间
	private String completeTime;	//完成订单时间
	private String userId;
	private String consignee;
	private String consigneeAddress;
	private String phoneNumber;
	private String modePayment;
	private String expressCharge;
	private String deliveryDate;
	private String deliveryTime;
	private String invoiceType;
	private String invoiceTitle;
	private String invoiceContent;
	private String cancelTime;
	private String courierNumber;	
	private String express;	//快递公司
	
	
	private List<ListOfGoods> listOfGoodsList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getModePayment() {
		return modePayment;
	}
	public void setModePayment(String modePayment) {
		this.modePayment = modePayment;
	}
	public String getExpressCharge() {
		return expressCharge;
	}
	public void setExpressCharge(String expressCharge) {
		this.expressCharge = expressCharge;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getInvoiceContent() {
		return invoiceContent;
	}
	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	public List<ListOfGoods> getListOfGoodsList() {
		return listOfGoodsList;
	}
	public void setListOfGoodsList(List<ListOfGoods> listOfGoodsList) {
		this.listOfGoodsList = listOfGoodsList;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}
	public String getCourierNumber() {
		return courierNumber;
	}
	public void setCourierNumber(String courierNumber) {
		this.courierNumber = courierNumber;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	
}

package com.ruobilin.search.utils.constants;

public class MallConstant {
	/**
	 ************************************产品管理********************************
	 */
	//产品状态
	public static final String PRODUCT_STATUS_RELEASE="0";	//商品已发布
	public static final String PRODUCT_STATUS_UNPUBLISHED="1";	//商品未发布
	public static final String PRODUCT_STATUS_DISABLING="2";	//商品到期停用
	public static final String[] PRODUCT_STATUS={"发布","未发布","停用"};
	
	//产品属性是否必填
	public static final String PRODUCT_EXTEND_YES="1";	//必填，extend == 1 ? '是' : '否'
	public static final String PRODUCT_EXTEND_NO="0";	//非必填
	public static final String[] PRODUCT_EXTEND={"否","是"};
	
	//产品图片
	public static final String PRODUCT_IMAGE_LB="0";	//列表图
	public static final String PRODUCT_IMAGE_ZS="1";	//展示图
	public static final String[] PRODUCT_IMAGE={"列表图","展示图"};
	
	/*
	 * *******************************前台常量**************************
	 */
	//订单状态
	/** 提交订单 */
	public static final String ORDER_STATUS_KS="01";
	/** 付款成功,或者货到付款 */	
	public static final String ORDER_STATUS_FK="02";
	/** 商品出库 */
	public static final String ORDER_STATUS_CK="03";
	/** 等待收货 */	
	public static final String ORDER_STATUS_DS="04";
	/** 完成 */	
	public static final String ORDER_STATUS_END="05";
	/** 取消订单 */	
	public static final String ORDER_STATUS_QX="06";
	/** 退换货物 */
	public static final String ORDER_STATUS_TH="07";
	
	public static final String[] ORDER_STATUS={"提交订单","付款成功","商品出库","等待收货","完成","取消订单","退换货物"};
	
	//付款方式
	/** 货到付款 */
	public static final String MODE_PAYMENT_DF="1";
	/** 在线支付 */
	public static final String MODE_PAYMENT_ZX="2";
	
}

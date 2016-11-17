package com.ruobilin.mall.admin.mapper;

import java.util.List;

import com.ruobilin.mall.admin.entity.CommodityBasicInfo;


public interface CommodityBasicMapper {
	
	/**
	 * 新增商品基本信息
	 * @param commodityBasicInfo
	 */
	public void insertCommodityBasicInfo(CommodityBasicInfo commodityBasicInfo);
	
	/**
	 * 更新商品基本信息
	 * @param commodityBasicInfo
	 */
	public void updateCommodityBasicInfo(CommodityBasicInfo commodityBasicInfo);
	
	/**
	 * 删除商品基本信息
	 * @param id
	 */
	public void delCommodityBasicInfo(String id);
	
	/**
	 * 查询商品列表信息
	 * @return
	 */
	public List<CommodityBasicInfo> queryListCommodity(); 
	
	/**
	 * 查询商品基本信息详情
	 * @param id
	 * @return
	 */
	public CommodityBasicInfo queryCommodityBasicInfo(String id);
}

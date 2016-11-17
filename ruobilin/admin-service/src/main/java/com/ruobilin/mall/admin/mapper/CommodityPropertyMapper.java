package com.ruobilin.mall.admin.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.admin.entity.CommodityProperty;


public interface CommodityPropertyMapper {
	
	/**
	 * 新增商品属性
	 * @param commodityProperty
	 */
	public void insertCommodityProperty(CommodityProperty commodityProperty);
	
	/**
	 * 批量新增商品属性
	 * @param list
	 */
	public void insertCommodityPropertyList(Map<String,Object> mapParam);
	
	/**
	 * 更新商品属性
	 * @param commodityProperty
	 */
	public void updateCommodityProperty(CommodityProperty commodityProperty);
	
	/**
	 * 删除商品属性
	 * @param commodityProperty
	 */
	public void delCommodityProperty(String id);
	
	/**
	 * 根据商品ID删除商品属性
	 * @param commodityId
	 */
	public void delCommodityPropertyAndcommodityId(String commodityId);
	
	/**
	 * 根据商品基本信息ID删除商品属性
	 * @param commodityBasicId
	 */
	public void delCommodityPropertyAndcommodityBasicId(String commodityBasicId);
	
	/**
	 * 根据商品基本信息ID查询商品属性
	 * @param commodityId
	 * @return
	 */
	public List<CommodityProperty> queryListCommodityProperty(String commodityBasicId);
}

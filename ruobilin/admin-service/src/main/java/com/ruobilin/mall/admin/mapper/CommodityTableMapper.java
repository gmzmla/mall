package com.ruobilin.mall.admin.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.admin.entity.CommodityTable;


public interface CommodityTableMapper {
	
	/**
	 * 新增商品表
	 * @param commodityTable
	 */
	public void insertCommodityTable(CommodityTable commodityTable);
	
	/**
	 * 批量新增商品
	 * @param list
	 */
	public void insertCommodityTableList(Map<String,Object> mapParam);
	
	/**
	 * 更新商品表
	 * @param commodityTable
	 */
	public void updateCommodityTable(CommodityTable commodityTable);
	
	/**
	 * 删除商品表
	 * @param id
	 */
	public void delCommodityTable(String id);
	
	/**
	 * 根据商品基本信息表ID删除商品
	 * @param commodityBasicId
	 */
	public void delCommodityTableAndCommodityBasicId(String commodityBasicId);
	
	/**
	 * 根据商品基本信息表ID查询商品信息
	 * @param commodityBasicId
	 * @return
	 */
	public List<CommodityTable> queryListCommodityTable(String commodityBasicId);
}

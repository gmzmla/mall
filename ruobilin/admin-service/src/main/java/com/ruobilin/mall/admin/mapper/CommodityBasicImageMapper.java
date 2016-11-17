package com.ruobilin.mall.admin.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.admin.entity.CommodityBasicImage;


public interface CommodityBasicImageMapper {
	
	/**
	 * 批量新增商品基本信息图片
	 * @param map
	 */
	public void insertCommodityBasicImageList(Map<String,Object> map);
	
	/**
	 * 根据商品基本信息ID查询基本信息图片
	 * @param commodityBasicId
	 * @return
	 */
	public List<CommodityBasicImage> queryListCommodityBasicImage(String commodityBasicId);
	
	/**
	 * 根据商品基本信息ID删除基本信息图片
	 * @param commodityBasicId
	 */
	public void delListCommodityBasicImage(String commodityBasicId);
}

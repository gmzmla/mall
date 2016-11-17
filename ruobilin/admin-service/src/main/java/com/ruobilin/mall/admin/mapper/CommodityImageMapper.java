package com.ruobilin.mall.admin.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.admin.entity.CommodityImage;


public interface CommodityImageMapper {
	
	/**
	 * 新增商品图片
	 * @param commodityImage
	 */
	public void insertCommodityImage(CommodityImage commodityImage);
	
	/**
	 * 批量添加商品图片
	 * @param list
	 */
	public void insertCommodityImageList(Map<String,Object> mapParam);
	
	/**
	 * 更新商品图片
	 * @param commodityImage
	 */
	public void updateCommodityImage(CommodityImage commodityImage);
	
	/**
	 * 删除商品图片
	 * @param id
	 */
	public void delCommodityImage(String id);
	
	/**
	 * 根据商品ID 删除商品下的所有图片
	 * @param commodityId
	 */
	public void delCommodityImageAndcommodityId(String commodityId);
	
	/**
	 * 根据商品基本信息ID 删除商品图片
	 * @param commodityBasicId
	 */
	public void delCommodityImageAndcommodityBasicId(String commodityBasicId);
	
	/**
	 * 根据商品基本信息ID 查询商品下的所有图片
	 * @param commodityId
	 * @return
	 */
	public List<CommodityImage> queryListCommodityImage(String commodityBasicId);
}

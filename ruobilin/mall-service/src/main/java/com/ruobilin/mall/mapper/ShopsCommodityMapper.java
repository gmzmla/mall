package com.ruobilin.mall.mapper;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ruobilin.mall.entity.CommodityInfo;

public interface ShopsCommodityMapper {
	
	List<Map<String,String>> findByCategoryId(Map<String,Object> map, PageBounds pageBounds);
	
	/**
	 * 根据参数查询商品
	 * @param param
	 * @return 
	 */
	List<Map<String,String>> homeProducts(Map<String,String> param);
	
	/**
	 * 查询商品基本信息
	 */
	CommodityInfo queryCommodityBasicInfo(String id);
	
	/**
	 * 查询商品基本信息图片
	 * @param commodityBasicId 商品基本信息表ID
	 */
	List<Map<String,String>> queryCommodityBasicImage(String commodityBasicId);
	
	/**
	 * 查询商品信息
	 * @param id
	 * @return
	 */
	CommodityInfo queryCommodityInfo(String id);
	
	/**
	 * 查询商品图片信息
	 * @param commodityBasicId
	 * @return
	 */
	List<Map<String,String>> queryCommodityImage(String commodityId);
	
	/**
	 * 查询关联其他的商品信息
	 * @param commodityBasicId	基本商品信息ID
	 * @return
	 */
	List<Map<String,String>> queryCommodityProperty(Map<String,String> map);
	
	/**
	 * 根据属性ID属性值ID商品基本信息ID 查询商品ID
	 * @param list
	 * @return
	 */
	List<String> queryCommodityId(List<Map<String,String>> list);
	
	/**
	 * 查询单个 商品信息
	 * @param id
	 * @return
	 */
	Map<String,Object> findCommodityInfo(String id);
	Map<String,Object> findCommodityBasicInfo(String id);
	
	/**
	 * 更新购物车中商品数量
	 * @param id
	 * @param cartProductId
	 * @param munCart
	 */
	void updateCookieCart(Map<String,String> map);
}

package com.ruobilin.mall.admin.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.admin.entity.PropertyInfo;


public interface PropertyInfoMapper {
	
	/**
	 * 新增商品销售属性
	 * @param propertyInfo
	 */
	public void insertPropertyInfo(PropertyInfo propertyInfo);
	
	/**
	 * 批量新增商品销售属性
	 * @param list
	 */
	public void insertPropertyInfoList(Map<String,Object> map);
	
	/**
	 * 更新商品销售属性
	 * @param propertyInfo
	 */
	public void updatePropertyInfo(PropertyInfo propertyInfo);
	
	/**
	 * 删除商品销售属性
	 * @param id
	 */
	public void delPropertyInfo(String id);
	
	/**
	 * 根据商品基本信息ID删除商品销售属性
	 * @param commodityId
	 */
	public void delPropertyInfoAndCommodityId(String commodityBasicId);
	
	/**
	 * 根据商品基本信息ID查询商品销售属性
	 * @param commodityId
	 * @return
	 */
	public List<PropertyInfo> querListPropertyInfo(String commodityBasicId);
}

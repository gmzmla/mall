package com.ruobilin.mall.admin.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.admin.entity.PropertyValueInfo;

public interface PropertyValueInfoMapper {
	
	/**
	 * 新增商品销售属性值
	 * @param propertyValueInfo
	 */
	public void insertPropertyValueInfo(PropertyValueInfo propertyValueInfo);
	
	/**
	 * 批量新增商品销售属性
	 * @param list
	 */
	public void insertPropertyValueInfoList(Map<String,Object> mapParam);
	
	/**
	 * 更新商品销售属性值
	 * @param propertyValueInfo
	 */
	public void updatePropertyValueInfo(PropertyValueInfo propertyValueInfo);
	
	/**
	 * 删除商品销售属性值
	 * @param id
	 */
	public void delPropertyValueInfo(String id);
	
	/**
	 * 根据销售属性ID批量删除销售属性值
	 * @param propertyId
	 */
	public void delPropertyValueInfoAndPropertyId(String propertyId);
	
	/**
	 * 根据基本信息表ID删除销售属性值
	 * @param commodityBasicId
	 */
	public void delListPropertyValueInfo(String commodityBasicId);
	
	/**
	 * 根据基本商品信息ID查询销售属性值
	 * @param propertyId
	 * @return
	 */
	public List<PropertyValueInfo> queryListPropertyValueInfo(String commodityBasicId);
}

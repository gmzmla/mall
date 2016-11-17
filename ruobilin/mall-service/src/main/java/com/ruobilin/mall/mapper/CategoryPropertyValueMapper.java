package com.ruobilin.mall.mapper;

import java.util.List;

import com.ruobilin.mall.entity.CategoryPropertyValue;


public interface CategoryPropertyValueMapper {
	
	/**
	 * 根据categoryPropertyId删除数据
	 * @param id
	 */
	void deleteByCategoryValueId(String id);
	/**
	 * 根据主键ID 删除数据
	 * @param id
	 */
	void delete(String id);
	
	/**
	 * 保存
	 * @param cpValue
	 */
	void insertCategoryPropertyValue(CategoryPropertyValue cpValue);
	
	/**
	 * 更新
	 * @param cpValue
	 */
	void updateCategoryPropertyValue(CategoryPropertyValue cpValue);
	
	/**
	 * 更具属性ID 查询出所有值
	 * @param propertyId
	 * @return
	 */
	List<CategoryPropertyValue> queryCategoryPropertyValueList(String propertyId);
	
	/**
	 * 根据主键ID 查询值信息
	 * @param id
	 * @return
	 */
	CategoryPropertyValue findCategoryPropertyValue(String id);
}

package com.ruobilin.mall.admin.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.admin.entity.CategoryBasicProperty;


public interface CategoryBasicPropertyMapper {
	
	/**
	 * 新增商品基本信息属性与值
	 * @param CategoryBasicProperty
	 */
	public void insertCategoryBasicProperty(CategoryBasicProperty categoryBasicProperty);
	/**
	 * 批量新增商品基本信息属性与值
	 * @param listCategoryBasicProperty
	 */
	public void insertListCategoryBasicProperty(Map<String,Object> map);
	
	/**
	 * 更新商品基本信息属性与值
	 * @param CategoryBasicProperty
	 */
	public void updateCategoryBasicProperty(CategoryBasicProperty CategoryBasicProperty);
	
	/**
	 * 删除商品基本信息属性与值
	 * @param id
	 */
	public void delCategoryBasicProperty(String id);
	
	/**
	 * 根据商品基本信息ID删除商品基本信息属性与值
	 * @param commodityBasicId
	 */
	public void delListCategoryBasicProperty(String commodityBasicId);
	
	/**
	 * 根据商品基本信息ID查询属性
	 * @param commodityBasicId
	 * @return
	 */
	public List<CategoryBasicProperty> queryListCategoryBasicProperty(String commodityBasicId);
}

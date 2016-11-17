package com.ruobilin.mall.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.entity.CategoryProperty;

public interface CategoryPropertyMapper {

	List<CategoryProperty> findByCategoryId(Long id);

	void deleteByCategoryId(Long id);

	void insert(CategoryProperty cp);
	
	void update(CategoryProperty cp);
	
	/**
	 * 批量更新属性的categoryId
	 * @param map
	 */
	void updateCategoryPropertyList(Map<String,Object> map);
	
	List<Map<String,Object>> findCategoryCodeQueryProperty(String code);
	List<Map<String,Object>> findCategoryCodeQueryPropertyValue(String code);
	
	/**
	 * 根据ID删除数据
	 * @param id
	 */
	void deleteByCategoryPropertyId(String id);
	
	/**
	 * 根据主键ID 查询属性信息
	 * @param id
	 * @return
	 */
	CategoryProperty findCategoryProperty(String id);
	
}

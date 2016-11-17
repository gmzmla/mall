package com.ruobilin.mall.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.admin.entity.CategoryBasicProperty;
import com.ruobilin.mall.admin.mapper.CategoryBasicPropertyMapper;

@Service
public class CategoryBasicPropertyService {
	
	@Autowired
	private CategoryBasicPropertyMapper CategoryBasicPropertyMapper;
	
	/**
	 * 新增商品图片
	 * @param CategoryBasicProperty
	 */
	public void insertCategoryBasicProperty(CategoryBasicProperty CategoryBasicProperty){
		CategoryBasicPropertyMapper.insertCategoryBasicProperty(CategoryBasicProperty);
	}
	
	/**
	 * 更新商品图片
	 * @param CategoryBasicProperty
	 */
	public void updateCategoryBasicProperty(CategoryBasicProperty CategoryBasicProperty){
		CategoryBasicPropertyMapper.updateCategoryBasicProperty(CategoryBasicProperty);
	}
	
	/**
	 * 删除商品图片
	 * @param id
	 */
	public void delCategoryBasicProperty(String id){
		CategoryBasicPropertyMapper.delCategoryBasicProperty(id);
	}
	
}

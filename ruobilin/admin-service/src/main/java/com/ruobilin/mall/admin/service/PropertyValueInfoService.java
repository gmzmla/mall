package com.ruobilin.mall.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.admin.entity.PropertyValueInfo;
import com.ruobilin.mall.admin.mapper.PropertyValueInfoMapper;

@Service
public class PropertyValueInfoService {
	
	@Autowired
	private PropertyValueInfoMapper propertyValueInfoMapper;
	
	/**
	 * 新增商品销售属性值
	 * @param propertyValueInfo
	 */
	public void insertPropertyValueInfo(PropertyValueInfo propertyValueInfo){
		propertyValueInfoMapper.insertPropertyValueInfo(propertyValueInfo);
	}
	
	/**
	 * 更新商品销售属性值
	 * @param propertyValueInfo
	 */
	public void updatePropertyValueInfo(PropertyValueInfo propertyValueInfo){
		propertyValueInfoMapper.updatePropertyValueInfo(propertyValueInfo);
	}
	
	/**
	 * 删除商品销售属性值
	 * @param id
	 */
	public void delPropertyValueInfo(String id){
		propertyValueInfoMapper.delPropertyValueInfo(id);
	}
	
	/**
	 * 根据销售属性ID批量删除销售属性值
	 * @param propertyId
	 */
	public void delPropertyValueInfoAndPropertyId(String propertyId){
		propertyValueInfoMapper.delPropertyValueInfoAndPropertyId(propertyId);
	}
}

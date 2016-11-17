package com.ruobilin.mall.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.admin.entity.PropertyInfo;
import com.ruobilin.mall.admin.mapper.PropertyInfoMapper;

@Service
public class PropertyInfoService {
	
	@Autowired
	private PropertyInfoMapper propertyInfoMapper;
	
	/**
	 * 新增商品销售属性
	 * @param propertyInfo
	 */
	public void insertPropertyInfo(PropertyInfo propertyInfo){
		propertyInfoMapper.insertPropertyInfo(propertyInfo);
	}
	
	/**
	 * 更新商品销售属性
	 * @param propertyInfo
	 */
	public void updatePropertyInfo(PropertyInfo propertyInfo){
		propertyInfoMapper.updatePropertyInfo(propertyInfo);
	}
	
	/**
	 * 删除商品销售属性
	 * @param id
	 */
	public void delPropertyInfo(String id){
		propertyInfoMapper.delPropertyInfo(id);
	}
	
	/**
	 * 根据商品基本信息ID删除商品销售属性
	 * @param commodityId
	 */
	public void delPropertyInfoAndCommodityId(String commodityId){
		propertyInfoMapper.delPropertyInfoAndCommodityId(commodityId);
	}
}

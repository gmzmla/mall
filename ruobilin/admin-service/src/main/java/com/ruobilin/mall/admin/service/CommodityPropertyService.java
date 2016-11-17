package com.ruobilin.mall.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.admin.entity.CommodityProperty;
import com.ruobilin.mall.admin.mapper.CommodityPropertyMapper;

@Service
public class CommodityPropertyService {
	
	@Autowired
	private CommodityPropertyMapper commodityPropertyMapper;
	
	/**
	 * 新增商品属性
	 * @param commodityProperty
	 */
	public void insertCommodityProperty(CommodityProperty commodityProperty){
		commodityPropertyMapper.insertCommodityProperty(commodityProperty);
	}
	
	/**
	 * 更新商品属性
	 * @param commodityProperty
	 */
	public void updateCommodityProperty(CommodityProperty commodityProperty){
		commodityPropertyMapper.updateCommodityProperty(commodityProperty);
	}
	
	/**
	 * 删除商品属性
	 * @param commodityProperty
	 */
	public void delCommodityProperty(String id){
		commodityPropertyMapper.delCommodityProperty(id);
	}
	
	/**
	 * 根据商品ID删除商品属性
	 * @param commodityId
	 */
	public void delCommodityPropertyAndcommodityId(String commodityId){
		commodityPropertyMapper.delCommodityPropertyAndcommodityId(commodityId);
	}
}

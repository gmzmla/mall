package com.ruobilin.mall.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.admin.entity.CommodityTable;
import com.ruobilin.mall.admin.mapper.CommodityTableMapper;

@Service
public class CommodityTableService {
	
	@Autowired
	private CommodityTableMapper commodityTableMapper;
	
	/**
	 * 新增商品表
	 * @param commodityTable
	 */
	public void insertCommodityTable(CommodityTable commodityTable){
		commodityTableMapper.insertCommodityTable(commodityTable);
	}
	
	/**
	 * 更新商品表
	 * @param commodityTable
	 */
	public void updateCommodityTable(CommodityTable commodityTable){
		commodityTableMapper.updateCommodityTable(commodityTable);
	}
	
	/**
	 * 删除商品表
	 * @param id
	 */
	public void delCommodityTable(String id){
		commodityTableMapper.delCommodityTable(id);
	}
	
	/**
	 * 根据商品基本信息表ID删除商品
	 * @param commodityBasicId
	 */
	public void delCommodityTableAndCommodityBasicId(String commodityBasicId){
		commodityTableMapper.delCommodityTableAndCommodityBasicId(commodityBasicId);
	}
}

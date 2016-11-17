package com.ruobilin.mall.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.admin.entity.CommodityImage;
import com.ruobilin.mall.admin.mapper.CommodityImageMapper;

@Service
public class CommodityImageService {
	
	@Autowired
	private CommodityImageMapper commodityImageMapper;
	
	/**
	 * 新增商品图片
	 * @param commodityImage
	 */
	public void insertCommodityImage(CommodityImage commodityImage){
		commodityImageMapper.insertCommodityImage(commodityImage);
	}
	
	/**
	 * 更新商品图片
	 * @param commodityImage
	 */
	public void updateCommodityImage(CommodityImage commodityImage){
		commodityImageMapper.updateCommodityImage(commodityImage);
	}
	
	/**
	 * 删除商品图片
	 * @param id
	 */
	public void delCommodityImage(String id){
		commodityImageMapper.delCommodityImage(id);
	}
	
	/**
	 * 根据商品ID 删除商品ID下的所有图片
	 * @param commodityId
	 */
	public void delCommodityImageAndcommodityId(String commodityId){
		commodityImageMapper.delCommodityImageAndcommodityId(commodityId);
	}
}

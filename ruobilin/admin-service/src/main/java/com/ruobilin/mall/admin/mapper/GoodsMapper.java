package com.ruobilin.mall.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ruobilin.mall.admin.entity.Goods;



public interface GoodsMapper {
	/**
	 * 根据商品名查询商品列表
	 * @author weizhaohui
	 *
	 * 2015年8月12日下午5:01:18
	 *
	 */
	public List<Map> findAllGoods(@Param(value="name") String name);

}

package com.ruobilin.mall.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ruobilin.mall.admin.entity.RecommendedGoods;

public interface RecommendedGoodsMapper {
	
	/**
	 * 根据节点名称查询
	 * @author weizhaohui
	 *  @param recommendedName  节点名称
	 * 2015年8月11日下午2:50:47
	 *
	 */
	public List<RecommendedGoods> findAllByRecommendedId(String  recommendedId);
	
	/**
	 * 根据id删除
	 * @author weizhaohui
	 *
	 * 2015年8月12日下午2:41:22
	 *
	 */
	public Integer deleteById(@Param(value="id") Integer id);
	
	/**
	 *  新增推荐商品
	 * @author weizhaohui
	 * @param RecommendedGoods recommendedGoods
	 * 2015年8月17日下午2:02:36
	 *
	 */
	public Integer addRecommendedGoods(RecommendedGoods recommendedGoods);
	
}

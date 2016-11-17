package com.ruobilin.mall.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.entity.Recommended;
import com.ruobilin.mall.entity.RecommendedGoods;


/**
 * 
 * @author weizhaohui
 *
 * 2015年8月20日上午9:28:45
 */
public interface FrontRecommendedMapper {
	
	//查询所有
	public List<Recommended> findAllLevel();
	
	//根据父级id查询其所属
	public List<Recommended> findRecommendedByParentId(String parentId);
	
	//根据节点id查询其所属商品信息
	public List<RecommendedGoods> findRecommendedGoodsById(Integer recommendedId);

	//查询首页展示用的商品信息
	public List<Map<String,String>> getHomeProducts(Integer thirdRecommendedId);
}

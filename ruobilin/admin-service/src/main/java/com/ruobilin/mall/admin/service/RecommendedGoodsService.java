package com.ruobilin.mall.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruobilin.mall.admin.entity.RecommendedGoods;
import com.ruobilin.mall.admin.mapper.RecommendedGoodsMapper;

@Service
@Transactional
public class RecommendedGoodsService {
	@Autowired
	private RecommendedGoodsMapper recommendedGoodsMapper;
	
	/**
	 * 
	 * @author weizhaohui
	 * @param recommendedName 节点名称
	 * 2015年8月11日下午2:52:35
	 *
	 */
	@Transactional(readOnly=true)
	public List<RecommendedGoods> findAllByRecommendedId(String recommendedId){
		 return recommendedGoodsMapper.findAllByRecommendedId(recommendedId);
	}
	
	/**
	 * 根据id删除
	 * @author weizhaohui
	 *
	 * 2015年8月12日下午2:43:01
	 *
	 */
	public Integer deleteById(Integer id){
		if(null == id) {
			return 0; // 删除失败
		}
		
		return recommendedGoodsMapper.deleteById(id);
		
	}
	
	
	
	/**
	 *  @param 
	 * @author weizhaohui
	 *
	 * 2015年8月17日下午4:40:16
	 *
	 */
	public Integer addRecommendedGoods(RecommendedGoods recommendedGoods){
		if(recommendedGoods == null){
			return 0;
		}
		return recommendedGoodsMapper.addRecommendedGoods(recommendedGoods);
	}
}

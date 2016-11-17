package com.ruobilin.mall.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruobilin.mall.admin.mapper.GoodsMapper;

@Service
@Transactional
public class GoodsService {
	@Autowired
	private GoodsMapper productMapper;
	
	
	/**
	 * 根据商品名称查询商品列表
	 * @author weizhaohui
	 *
	 * 2015年8月12日下午5:05:25
	 *
	 */
	@Transactional(readOnly=true)
	public List<Map> findAllGoods(String name){
		return productMapper.findAllGoods(name);
	}

}

package com.ruobilin.mall.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.entity.Commodity;
import com.ruobilin.mall.entity.Shop;
import com.ruobilin.mall.entity.User;
import com.ruobilin.mall.mapper.CommodityMapper;;

@Service
public class CommodityService {
	@Autowired
	private CommodityMapper commodityMapper;

	public List<Commodity> findAll(int page, int limit,
			String sort, String dir) {
		return (List<Commodity>)commodityMapper.findAll( new PageBounds(page, limit , Order.formString(sort + "." + dir)));
	}

	
	
	public boolean save(Commodity commodity) {
		try {
			if("".equals(commodity.getId())||commodity.getId()==null){
				commodityMapper.delete(commodity);
				commodityMapper.insert(commodity);
			}else{
				commodityMapper.deleteById(commodity.getId());
				commodityMapper.insert(commodity);
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public void delete(Long id) {
		commodityMapper.deleteById(id);
	}

	public void deletes(Commodity commodity){
		commodityMapper.delete(commodity);
	}
	public Commodity getById(Long id) {
		return commodityMapper.getById(id);
	}
}

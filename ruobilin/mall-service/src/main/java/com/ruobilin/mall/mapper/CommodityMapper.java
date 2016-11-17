package com.ruobilin.mall.mapper;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ruobilin.mall.entity.Page;
import com.ruobilin.mall.entity.Commodity;

public interface CommodityMapper {

	List<Commodity> findAll( PageBounds pageBounds);

	void insert(Commodity commodity);

	void update(Commodity commodity);

	void deleteById(Long id);
	
	void delete(Commodity commodity);

	Commodity getById(Long id);
	
	List<Commodity> getAll(Page page);
	

}

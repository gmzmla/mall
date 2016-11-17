package com.ruobilin.mall.mapper;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ruobilin.mall.entity.CategoryPropertyValue;
import com.ruobilin.mall.entity.Product;

public interface ProductMapper { 

	Product getById(String id);

	List<Product> findByCategoryId(Map<String,Object> map, PageBounds pageBounds);

	List<Product> findHotByCategoryId(Long id, int limit);

	List<Product> findRecommendByCategoryId(Long id, int limit);

	List<Product> findAll(Map<String, Object> param, PageBounds pageBounds);

    List<Map> findAlis(String name);
    
	void insert(Product product);

	void update(Product product);
	
	void deleteById(Long id);
	
	List<Product> getProductByKeyword(String keyWord);
	
	public String getCategoryNameById(Long id);
	
	public Long getCategoryPropertyId(Map<Object,Object> params);
	
	public List<CategoryPropertyValue> getCategoryPropertyValues(Long id);
	
	public Long getCategoryPropertyValueId(Map<Object,Object> params);
	
	public String getCategoryPropertyName(Map<Object,Object> params);
	
	/**
	 * 根据参数查询商品
	 * @param param
	 * @return 
	 */
	List<Map<String,String>> homeProducts(Map<String,String> param);

	/**
	 * 定时更新到期商品 
	 */
	void updateAllProduct(String status);
}

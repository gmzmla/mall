package com.ruobilin.mall.mapper;

import java.util.List;

import com.ruobilin.mall.entity.ProductImage;

public interface ProductImageMapper {

	List<ProductImage> findByProductId(Long id);

	void deleteByProductId(Long id);

	void insert(ProductImage pi);

}

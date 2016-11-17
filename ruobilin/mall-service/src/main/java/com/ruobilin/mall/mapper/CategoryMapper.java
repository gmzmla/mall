package com.ruobilin.mall.mapper;

import java.util.List;

import com.ruobilin.mall.entity.Category;

public interface CategoryMapper {

	List<Category> findAll();

	Category getByCode(String code);

	Category getById(Integer id);

	void insert(Category category);

	void update(Category category);

	void deleteById(Long id);

}

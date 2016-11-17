package com.ruobilin.mall.admin.mapper;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ruobilin.mall.admin.entity.Menu;

public interface MenuMapper {

	List<Menu> findAll();

	void insert(Menu menu);

	void update(Menu menu);

	Menu getById(Integer id);

	void deleteById(Integer id);

}

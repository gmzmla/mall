package com.ruobilin.mall.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.admin.entity.Menu;
import com.ruobilin.mall.admin.mapper.MenuMapper;

@Service
public class MenuService {
	@Autowired
	private MenuMapper menuMapper;

	public List<Menu> findAll() {
		List<Menu> list = menuMapper.findAll();
		if (list == null)
			return null;

		return list;
	}

	private Menu getRootMenu() {
		Menu root = new Menu();
		root.setId(0);
		root.setLevel(0);
		root.setName("管理后台");
		root.setParentId(-1);
		root.setStatus(0);
		root.setType(0);
		return root;
	}

	public boolean save(Menu menu) {
		try {
			if (menu.getId() == null) {
				menuMapper.insert(menu);
			}
			else {
				menuMapper.update(menu);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Menu getById(Integer id) {
		return menuMapper.getById(id);
	}

	public void delete(Integer id) {
		menuMapper.deleteById(id);
	}

	public List<Menu> findAllWithRoot() {
		List<Menu> list = menuMapper.findAll();
		if (list == null)
			return null;
		Menu root = getRootMenu();
		list.add(0, root);
		return list;
	}

}

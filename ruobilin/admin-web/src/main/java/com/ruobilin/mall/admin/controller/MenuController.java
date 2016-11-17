package com.ruobilin.mall.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruobilin.mall.admin.entity.Menu;
import com.ruobilin.mall.admin.service.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value = "/list")
	public String list(Model model) {
		List<Menu> menus = menuService.findAllWithRoot();
		model.addAttribute("menus", menus);
		return "menu/list";
	}
	
	@RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Menu get(@PathVariable("id") Integer id) {
		return menuService.getById(id);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public String edit(Menu menu) {
		if (menuService.save(menu))
			return "0";
		
		return "1";
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(Integer id, Model model) {
		menuService.delete(id);
		return "redirect:/menu/list";
	}
}

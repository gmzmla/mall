package com.ruobilin.mall.admin.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ruobilin.mall.admin.entity.Menu;
import com.ruobilin.mall.admin.service.MenuService;
import com.ruobilin.mall.entity.Category;
import com.ruobilin.mall.entity.Product;
import com.ruobilin.mall.service.CategoryService;
import com.ruobilin.mall.service.ProductService;

@Controller
@RequestMapping("")
public class HomeController {
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value = "/")
	public String index(Model model) {
		return "home/index";
	}
	
	@RequestMapping(value = "/left")
	public String left(Model model) {
		List<Menu> menus = menuService.findAllWithRoot();
		model.addAttribute("menus", menus);
		return "home/left";
	}
	
	@RequestMapping(value = "/top")
	public String top(Model model) {
		return "home/top";
	}
	
	@RequestMapping(value = "/main")
	public String main(Model model) {
		return "home/main";
	}
}

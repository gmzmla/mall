package com.ruobilin.mall.admin.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.entity.Shop;
import com.ruobilin.mall.service.ShopService;

@Controller
@RequestMapping("/shop")
public class ShopController {
	@Autowired
	private ShopService shopService;
	
	@RequestMapping(value = "/list")
	public String list(String name, @RequestParam(required = false,defaultValue = "1")int page, 
			@RequestParam(required = false,defaultValue = "20")int limit, 
			@RequestParam(required = false,defaultValue = "id")String sort, 
			@RequestParam(required = false,defaultValue = "desc")String dir,
			Model model) {
		
		PageList<Shop> shops = shopService.findAll(name, page, limit, sort, dir);
		model.addAttribute("shops", shops);
		model.addAttribute("page", page);
		
		return "shop/list";
	}
	
	@RequestMapping(value = "/edit")
	public String edit(Long id, Model model) {
		if (id != null) {
			Shop shop = shopService.getById(id);
			model.addAttribute("shop", shop);
		}
		return "shop/edit";
	}
	
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(Shop shop, Model model) {
		if (shop.getId() == null) {
			shop.setGrade(0);
		}
		if (shopService.save(shop))
			return "redirect:/shop/list";
		
		model.addAttribute("shop", shop);
		model.addAttribute("message", "失败。");
		return "shop/edit";
	}
	
	
	//删除的方法 method = RequestMethod.GET  method 应该是get

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(Long id, Model model) {
		shopService.delete(id);
		return "redirect:/shop/list";
	}
}

package com.ruobilin.mall.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruobilin.mall.admin.entity.CommodityBasicInfo;
import com.ruobilin.mall.admin.service.CommodityBasicService;

/**
 * 商品controller
 *
 */
@Controller
@RequestMapping("/shopsCommodity")
public class ShopsCommodityController {
	private final Logger log = LoggerFactory.getLogger(ShopsCommodityController.class);
	
	@Autowired
	private CommodityBasicService commodityBasicService;
	
	/**
	 * 进入商品列表页面
	 * @param shopsId	商铺ID
	 * @return
	 */
	@RequestMapping("/queryListCommodity")
	public String queryListCommodity(String shopsId,Model model){
		model.addAttribute("commodityList",commodityBasicService.queryListCommodity(""));
		return "/shopsCommodity/list"; 
	}
	
	/**
	 * 添加商品页面
	 * @return
	 */
	@RequestMapping("/addCommodity")
	public String addCommodity(){
		
		return "/shopsCommodity/add";
	}
	
	/**
	 * 进入商铺编辑页面
	 * @return
	 */
	@RequestMapping("/editCommodity")
	public String editCommodity(String id,Model model){
		CommodityBasicInfo commodityBasicInfo=commodityBasicService.queryCommodityBasicInfo(id);
		model.addAttribute("commodityBasicInfo",commodityBasicInfo);
		return "/shopsCommodity/edit";
	}
	
	@RequestMapping("/saveCommodity")
	public String saveCommodity(CommodityBasicInfo commodityBasicInfo){
		//保存商品信息
		commodityBasicService.insertCommodityBasicInfo(commodityBasicInfo);
		
		
		return "redirect:/shopsCommodity/queryListCommodity";
	}
}

package com.ruobilin.mall.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruobilin.mall.admin.entity.Recommended;
import com.ruobilin.mall.admin.service.GoodsService;
import com.ruobilin.mall.admin.service.RecommendedService;
import com.ruobilin.mall.service.ProductService;
/**
 * @author weizhaohui
 * 2015年8月6日上午11:16:17
 */
@Controller
@RequestMapping(value="/recommended")
public class RecommendedController {
	
	@Autowired
	private RecommendedService recommendedService;
	@Autowired
	private ProductService productService;
	@Autowired
	private GoodsService goodsService;
	
	@RequestMapping(value ="/loadNode")
	@ResponseBody
	public String loadNode(Model model){
		Map map = new HashMap();
		map.put("a","aaa");
		JSONObject jp= new JSONObject();
		String s=jp.toJSONString(map);
		model.addAttribute("a", s);
		return "recommended/list";
	}
	
	//获取列表
	@RequestMapping(value="/list")
	public String list(Model model){
		String name="";
		List<Recommended> reList=recommendedService.findAllWithRoot();
		//List<Map> productList=goodsService.findAllGoods(name);
		model.addAttribute("reList",reList);
		//model.addAttribute("productList", productList);
		return "recommended/list";
	}
	
	//根据主键ID进行查询
	@RequestMapping(value="/item/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Recommended get(@PathVariable("id")Integer id){
		
		return recommendedService.getById(id);
	}
	
	//添加/修改
	@RequestMapping(value="/edit" ,method=RequestMethod.POST)
	@ResponseBody
	public String edit(Recommended recommended){
		if(recommendedService.save(recommended)){
			return "0";
		}
		return "1";
	}
	
	
	//删除
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String delete(Integer id,ModelMap model){
		recommendedService.deleteRecommendedById(id);
		return "redirect:/recommended/list";
	}
	
	//批量删除
	@RequestMapping(value="/deletes")
	@ResponseBody
	public String deletes(Integer[] ids,ModelMap model){
		recommendedService.deleteRecommendedByIds(ids);
		return "redirect:/recommended/list";
	}
	
	
	
}

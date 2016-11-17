package com.ruobilin.mall.admin.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ruobilin.mall.admin.utils.ImageFactory;
import com.ruobilin.mall.entity.Commodity;
import com.ruobilin.mall.service.CommodityService;
import com.ruobilin.mall.service.ProductService;

@Controller
@RequestMapping("/commodity")
public class CommodityController {
	private final Logger log = LoggerFactory.getLogger(CommodityController.class);
	
	@Autowired
	private CommodityService commodityService;
	
	@Autowired
	private ImageFactory imageFactory;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/list")
	public String list(@RequestParam(required=false)Long id,@RequestParam(required = false,defaultValue = "1")int page, 
			@RequestParam(required = false,defaultValue = "20")int limit, 
			@RequestParam(required = false,defaultValue = "id")String sort, 
			@RequestParam(required = false,defaultValue = "desc")String dir,
			Model model) {
		Map<String, Object> param = new TreeMap();
		param.put("id", id);
		
		List<Commodity> commoditys = commodityService.findAll(page, limit, sort, dir);
		model.addAttribute("commoditys", commoditys);
		model.addAttribute("page", page);
		model.addAttribute("id", id);
		
		return "commodity/list";
	}
	@RequestMapping(value="/edit")
	public String edit(Long id,Model model,String name){
		if(id!=null){
			Commodity commodity=commodityService.getById(id);
			model.addAttribute("commodity",commodity);
		}
		List<Map> list=productService.findAlls("null".equals(name)?"":(name!=null?name:""));
		model.addAttribute("jsonssList", list);
		return "commodity/edit";
	}
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String edit(Commodity commodity,
			@RequestParam(required = false)String[] imgUrls, @RequestParam(required = false)String[] imgTypes, @RequestParam(required = false)String[] smallUrls, 
			@RequestParam(required = false)String[] groups, @RequestParam(required = false)String[] pprices, @RequestParam(required = false)String[] inventories,
			@RequestParam(required = false)String[] cpids, @RequestParam(required = false)String[] ptypes, 
			@RequestParam(required = false)String[] pproperties, @RequestParam(required = false)String[] pvalues, @RequestParam(required = false)String[] pextends
			){
	if (commodityService.save(commodity))
		return "redirect:/commodity/list";
	
	return "commodity/edit";
}
	
	

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(Long id) {
		
		commodityService.delete(id);

		return "redirect:/commodity/list";
	}


	
}
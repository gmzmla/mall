package com.ruobilin.mall.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruobilin.mall.admin.entity.Goods;
import com.ruobilin.mall.admin.entity.Recommended;
import com.ruobilin.mall.admin.entity.RecommendedGoods;
import com.ruobilin.mall.admin.service.GoodsService;
import com.ruobilin.mall.admin.service.RecommendedGoodsService;
import com.ruobilin.mall.admin.service.RecommendedService;
import com.ruobilin.mall.entity.Product;
import com.ruobilin.mall.service.ProductService;

@Controller
@RequestMapping(value="/recommendedGoods")
public class RecommendedGoodsController {
	@Autowired
	private RecommendedGoodsService recommendedGoodsService;
	@Autowired
	private RecommendedService recommendedService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ProductService productService;
	
	
	
	/**
	 * 显示某个节点下的商品
	 * @author weizhaohui
	 *
	 * 2015年8月13日下午2:35:57
	 *
	 */
	@RequestMapping(value="/list")
	public String  list(@RequestParam(required=false)String currentId,Model model,String goodsName){
		//获取前面传过来的当前节点ID
		String  recommendedId=currentId;
		//根据id获取当前节点所有信息,提取名字
		if("0".equals(recommendedId)){
			return "recommended/rgList";
		}
		
		//根据当前节点id查询该节点下所有商品的信息
		List<RecommendedGoods> rgList=recommendedGoodsService.findAllByRecommendedId(recommendedId);
		model.addAttribute("rgList",rgList);
		return "recommended/rgList";
	}
	
	/**
	 * 删除某节点下的商品
	 * @author weizhaohui
	 *
	 * 2015年8月13日下午2:36:44
	 *
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
  public String delete(Integer id){
		if(null == id) {
			return "faild";
		}
		
		Integer isSuccess = recommendedGoodsService.deleteById(id);
		if(0 == isSuccess) {
			return "faild";
		}else {
			return "success";
		}
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@ResponseBody
	public String addRecommendedGoods(String productId,Integer recommendedId){
		
		//只要productId,recommendedId有一个为null或"",失败
	  if(productId == null && "".equals(productId) && recommendedId == null && "".equals(recommendedId) ){
		   return "faild";
	   }
	  
	   //通过productId获取商品名
	  Product product=productService.getById(productId);
	  String goodsName=product.getName();
	  
	  //通过recommended获取节点名称(type)
	  Recommended recommended=recommendedService.getById(recommendedId);
//	  String type=recommended.getName();
	  
	  //封装对象执行添加操作
	  RecommendedGoods recommendedGoods=new RecommendedGoods();
	  recommendedGoods.setProductId(productId);
	  recommendedGoods.setRecommendedId(recommendedId);
	  recommendedGoods.setGoodsName(goodsName);
//	  recommendedGoods.setType(type);
	  Integer flag= recommendedGoodsService.addRecommendedGoods(recommendedGoods);
	  
	  //如果方法返回值不为0,成功 否则失败
	  if(flag == 0 ){
		  return "faild";
	  }
	  else{
		  return "success";
	  }
		
	}
	
	/**
	 * 显示商品表中所有的商品
	 * @author weizhaohui
	 *
	 * 2015年8月13日下午2:37:05
	 *
	 */
	@RequestMapping(value="/product")
	@ResponseBody
	public List<Map>  findAllGoods(String name){
		List<Map> productList= goodsService.findAllGoods(name);
		return productList ;
	}
}

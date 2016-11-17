package com.ruobilin.mall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruobilin.mall.entity.Category;
import com.ruobilin.mall.entity.CommodityInfo;
import com.ruobilin.mall.entity.Recommended;
import com.ruobilin.mall.service.CategoryService;
import com.ruobilin.mall.service.FrontRecommendedService;
import com.ruobilin.mall.service.OrderService;
import com.ruobilin.mall.service.ProductService;
import com.ruobilin.mall.service.ShopsCommodityService;
import com.ruobilin.mall.service.UserService;
import com.ruobilin.search.utils.CookieUtil;
 
@Controller
@RequestMapping("")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
    @Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FrontRecommendedService frontRecommendedService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ShopsCommodityService shopsCommodityService;
	
	/**
	 * 首页
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/")
	public String index(Model model,HttpSession session,HttpServletRequest request) {
		List<Category> categories = categoryService.findAllForList();
		List<Category> tree = categoryService.findAllForTree();
		session.setAttribute("categories", tree);
		model.addAttribute("categorieAlls", categories);
		
		
		Map<String,String> param=new HashMap<String, String>(); 
		//查询产品推荐1，用于幻灯片显示
		List<Map<String,String>> homeList=shopsCommodityService.homeProducts(param);
		model.addAttribute("homeList", homeList);
		
		//查询所有推荐的商品
		List<Recommended> recoList=frontRecommendedService.findAllForTree();
		model.addAttribute("recoList", recoList);
		
		return "home/index";
	}
	
	/**
	 * 查询购物车商品
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/listCart")
	@ResponseBody
	public Map<String,Object> listCart(HttpServletRequest request,Model model){
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		Map<String,Object> returnList=new HashMap<String, Object>();
		if(principalCollection!=null){
			String userId=principalCollection.getRealmNames().iterator().next();
			List<Map<String,Object>> list=orderService.queryCartList(userId);
			returnList.put("cartP", list);
			return returnList;
		}
		String str=CookieUtil.getCookie("carProduct", request);
		if(str!=null&&!"".equals(str)){
			String[] obj=str.split("&");
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			for(int i=0;i<obj.length;i++){
				String[] obj1=obj[i].split(":");
				if(obj1.length<2){
					continue;
				}
				Map<String,Object> map=new HashMap<String, Object>();
				if("commodityTable".equals(obj1[2])){
					map=shopsCommodityService.findCommodityInfo(obj1[0]);
					map.put("tableName", "commodityTable");
				}else if("commodityBasicInfo".equals(obj1[2])){
					map=shopsCommodityService.findCommodityBasicInfo(obj1[0]);
					map.put("tableName", "commodityBasicInfo");
				}
				map.put("productCount", obj1[1]);
				list.add(map);
			}
			returnList.put("cartP", list);
		}
		return returnList;
	}
	
	/**
	 * 商品详情
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{tableName}/{id}")
	public String item(@PathVariable("tableName")String tableName,@PathVariable("id")String id, Model model, HttpServletRequest request) {
		CommodityInfo sco =new CommodityInfo();
		if("commodityTable".equals(tableName)){
			sco=shopsCommodityService.queryCommodityInfo(id);
		}else if("commodityBasicInfo".equals(tableName)){
			sco=shopsCommodityService.queryCommodityBasicInfo(id);
		}else{
			return "redirect:/";
		}
		model.addAttribute("product", sco);
		return "shopsCommodity/item";
	}
	
	/**
	 * 跳转到成功加入购物车页面
	 * @return
	 */
	@RequestMapping("/successAddCart")
	public String successAddCart(){
		return "shopsCommodity/addToCart";
	}
	
	/**
	 * 查询购物车中的商品信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/account")
	public String account(Model model,HttpServletRequest request){
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		if(principalCollection!=null){
			String userId=principalCollection.getRealmNames().iterator().next();
			List<Map<String,Object>> list=orderService.queryCartList(userId);
			if(list==null||list.size()<1){
				return "shopsCommodity/nullAccount";
			}
			for(int i=0;i<list.size();i++){
				Map<String,Object> map=list.get(i);
				if("commodityTable".equals(map.get("tableName"))){
					list.get(i).put("propertis", orderService.queryCommodityProperty(String.valueOf(map.get("id"))));
				}
			}
			model.addAttribute("list",list);
		}else{
			String str=CookieUtil.getCookie("carProduct", request);
			if(str!=null&&!"".equals(str)){
				String[] obj=str.split("&");
				List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
				for(int i=0;i<obj.length;i++){
					String[] obj1=obj[i].split(":");
					if(obj1.length<2){
						continue;
					}
					Map<String,Object> map=new HashMap<String, Object>();
					if("commodityTable".equals(obj1[2])){
						map=shopsCommodityService.findCommodityInfo(obj1[0]);
						map.put("propertis", orderService.queryCommodityProperty(String.valueOf(map.get("id"))));
						map.put("tableName", "commodityTable");
					}else if("commodityBasicInfo".equals(obj1[2])){
						map=shopsCommodityService.findCommodityBasicInfo(obj1[0]);
						map.put("tableName", "commodityBasicInfo");
					}
					map.put("productCount", obj1[1]);
					list.add(map);
				}
				if(list==null||list.size()<1){
					return "shopsCommodity/nullAccount";
				}
				model.addAttribute("list",list);
			}else{
				return "shopsCommodity/nullAccount";
			}
		}
		return "shopsCommodity/account";
	}
}

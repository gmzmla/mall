package com.ruobilin.mall.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.entity.Cart;
import com.ruobilin.mall.entity.CartProduct;
import com.ruobilin.mall.entity.Category;
import com.ruobilin.mall.service.CartProductService;
import com.ruobilin.mall.service.CartService;
import com.ruobilin.mall.service.CategoryService;
import com.ruobilin.mall.service.ShopsCommodityService;
import com.ruobilin.search.utils.CookieUtil;

@Controller
@RequestMapping("list")
public class ShopsCommodityController {
	
	@Autowired
	private ShopsCommodityService shopsCommodityService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartProductService cartProductService;
	
	/**
	 * 解密
	 * @param cat 加密串
	 * @param i
	 * @return
	 */
	private String decodeBase64Cat(String cat,int i){
		try {
			String newParam=new String(Base64.decodeBase64(new String(Base64.decodeBase64(cat), "UTF-8")), "UTF-8");
			newParam=URLDecoder.decode(newParam,"UTF-8");
			cat=newParam.split("&")[i].split("=").length<1?null:newParam.split("&")[i].split("=")[1];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		};
		return cat;
	}
	
	/**
	 * 商品列表查询
	 * @param code
	 * @param page
	 * @param limit
	 * @param sort
	 * @param dir
	 * @param cat
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/{code}")
	public String list(@PathVariable("code") String code, 
			@RequestParam(required = false,defaultValue = "1")int page, 
			@RequestParam(required = false,defaultValue = "20")int limit, 
			@RequestParam(required = false,defaultValue = "created")String sort, 
			@RequestParam(required = false,defaultValue = "desc")String dir,
			String cat,
			Model model,HttpSession session) {
		Category category = categoryService.getByCode(code);
		if(category==null||"".equals(category.getId())){
			return "redirect:/";
		}
		String orderSelect=null,propertisParam=null,resultSearchTxt=null;
		model.addAttribute("cat", cat);
		if(cat!=null&&!"".equals(cat)){
			//解密
			 orderSelect=decodeBase64Cat(cat,0);propertisParam =decodeBase64Cat(cat,1);resultSearchTxt=decodeBase64Cat(cat,2);
		}
		if("undefined".equals(orderSelect)){
			orderSelect=null;
		}
		if("undefined".equals(resultSearchTxt)){
			resultSearchTxt=null;
		}
		
		propertisParam=propertisParam!=null?propertisParam.replaceAll("'", ""):propertisParam;
		
		List<Map<String,Object>> idsList=new ArrayList<Map<String,Object>>();
		Map<String,List<String>> listMap=new HashMap<String, List<String>>();
		aif:if(propertisParam!=null){
			String[] param=propertisParam.split(",");
			if(param.length<1){
				break aif;
			}
			List<Map<String,String>> listParam=new ArrayList<Map<String,String>>();
			for(int j=0;j<param.length;j++){
				List<String> valuesList=new ArrayList<String>();
				if("".equals(param[j])){
					continue;
				}
				String[] obj=param[j].split(":");
				Map<String,String> map=new HashMap<String, String>();
				map.put("propertisValues", obj[0]);
				map.put("propertisIds", obj[1]);
				map.put("propertisNames", obj[2]);
				map.put("propertisValueIds", obj[3]);
				if("".equals(listMap.get(obj[1]))||"null".equals(listMap.get(obj[1]))||listMap.get(obj[1])==null){
					List<String> valueIdsList=new ArrayList<String>();
					for(int c=0;c<param.length;c++){
						String[] objc=param[c].split(":");
						if(objc.length<2){
							continue;
						}
						if(obj[1].equals(objc[1])){
							valuesList.add(objc[3]);
							valueIdsList.add(objc[3]);
						}
					}
					listMap.put(obj[1], valuesList);
					Map<String,Object> idsMap=new HashMap<String, Object>();
					idsMap.put("propertisIds", obj[1]);
					idsMap.put("propertisValueIds", valueIdsList);
					idsList.add(idsMap);
				}
				listParam.add(map);
			}
			model.addAttribute("listParam",listParam);
		}
		
		//查询 查询属性
		List<Map<String,Object>> propertyList = categoryService.findCategoryCodeQueryProperty(code);
		List<Map<String,Object>> propertyValueList=categoryService.findCategoryCodeQueryPropertyValue(code);
		model.addAttribute("propertyList", propertyList);
		model.addAttribute("propertyValueList", propertyValueList);
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		map.put("idsList", idsList.size()<1?null:idsList);	//菜单属性IDList内存valueList
		if("created".equals(orderSelect)){
			map.put("orderSelect", orderSelect);
		}
		if(resultSearchTxt!=null&&!"".equals(resultSearchTxt)&&!"null".equals(resultSearchTxt)){
			map.put("productName", "%"+resultSearchTxt+"%");
		}
		map.put("categoryId", category.getId());	//菜单ID
		PageList<Map<String,String>> products = shopsCommodityService.findByCategory(page, limit, sort, dir,map);
		model.addAttribute("products", products);
		model.addAttribute("category", category);
		model.addAttribute("orderSelect",orderSelect);
		return "shopsCommodity/list";
	}
	
	/**
	 * 	
	 */
	@RequestMapping(value = "/selectCommodityId")
	public String queryCommodityId(String str, Model model, HttpServletRequest request) {
		String commodityId=shopsCommodityService.queryCommodityId(str);
		return "redirect:/commodityTable/"+commodityId;
	}
	
	/**
	 * 将商品加入到购物车
	 * @param str 商品ID:商品数量:tableName
	 * @return
	 */
	@RequestMapping("/addCart")
	public String addCart(String str,HttpServletResponse response,HttpServletRequest request,Model model){
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		String[] productStr=str.split(":");	//商品信息,商品ID:商品数量:tableName
		if(principalCollection!=null){	//已登录用户直接把购物车数据加入数据库表
			String userId=principalCollection.getRealmNames().iterator().next();	//用户ID
			List<Cart> cartList=cartService.queryCart(userId);	//查询用户购物车表中商品数据,正常都有一条数据
			if(cartList!=null&&cartList.size()>0){
				for(int i=0;i<cartList.size();i++){
					Cart cart=cartList.get(i);
					List<CartProduct> cartProductList=cartProductService.queryCartProduct(cart.getId());
					aIf:for(int j=0;j<cartProductList.size();j++){
						CartProduct cartProduct=cartProductList.get(j);
						if(cartProduct.getProductId().equals(productStr[0])){
							shopsCommodityService.updateCartProduct(cartProduct.getId(), productStr[1]);
							str=null;
							break aIf;
						}
					}
					if(str!=null){	//购物车中有数据，但没相同的商品，只保存购物车商品表
						shopsCommodityService.saveCartProduct(str, cart.getId());
					}
				}
			}else{	//用户购物车没数据可直接把商品信息保存到购物车表中
				shopsCommodityService.saveCart(str, userId);
			}
			
		}else{	//未登录用户把商品数据加入Cookie
			//先获取cookie中的数据
			String carProduct=CookieUtil.getCookie("carProduct", request);
			//定义变量用于存放商品数据
			StringBuffer cookieStr=new StringBuffer();
			//如果cookie中的商品数据不是空,需要根据新加入的商品更新cookie中的数据
			if(carProduct!=null&&!"".equals(carProduct)){
				String[] cookieStrs=carProduct.split("&");	//cookie中的每个商品用"&"间隔
				for(int i=0;i<cookieStrs.length;i++){	//挨个比较cookie中的商品信息
					String[] product=cookieStrs[i].split(":");
					if(product.length<2){	//筛除不规范的数据
						continue;
					}
					if(productStr[0].equals(product[0])&&productStr[2].equals(product[2])){	//如果cookie中已存在相同的商品，增加商品数量
						cookieStrs[i].split(":")[1]=String.valueOf(Integer.valueOf(productStr[1]) + Integer.valueOf(product[1]));
						str="";
					}
					cookieStr.append(cookieStrs[i]);
					if(cookieStrs.length-1!=i){
						cookieStr.append("&");
					}
				}
			}
			if(str!=null&&!"".equals(str)){
				cookieStr.append("&");
				cookieStr.append(str);
			}
			CookieUtil.addCookie("carProduct", cookieStr.toString(), -1, response);	//把商品数据加入到cookie
		}
		return "redirect:/successAddCart";
	}
	
	/**
	 * 更新购物车中商品数量
	 * @param id	//商品ID
	 * @param cartProductId
	 * @param productCount	//商品数量
	 */
	@RequestMapping("/updateCartProduct")
	@ResponseBody
	public void updateCartProduct(String id,String cartProductId,String productCount,HttpServletRequest request,HttpServletResponse response){
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		if(principalCollection!=null){
			shopsCommodityService.updateCartProduct(id, productCount);
		}else{
			//先获取cookie中的数据
			String carProduct=CookieUtil.getCookie("carProduct", request);
			if(carProduct!=null&&id!=null&&productCount!=null){
				//定义变量用于存放商品数据
				StringBuffer cookieStr=new StringBuffer();
				String[] cookieStrs=carProduct.split("&");	//cookie中的每个商品用"&"间隔
				for(int i=0;i<cookieStrs.length;i++){
					String [] product=cookieStrs[i].split(":");
					if("".equals(product[0])){
						continue;
					}
					if(id.equals(product[0])){
						product[1]=productCount;
					}
					cookieStr.append(product[0]);
					cookieStr.append(":");
					cookieStr.append(product[1]);
					cookieStr.append(":");
					cookieStr.append(product[2]);
					if(cookieStrs.length-1!=i){
						cookieStr.append("&");
					}
				}
				CookieUtil.addCookie("carProduct", cookieStr.toString(), -1, response);	//把商品数据加入到cookie
			}
			
		}
	}
}

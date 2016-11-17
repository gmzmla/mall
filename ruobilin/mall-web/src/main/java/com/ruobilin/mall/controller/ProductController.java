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
import com.ruobilin.mall.entity.AreaIp;
import com.ruobilin.mall.entity.Cart;
import com.ruobilin.mall.entity.CartProduct;
import com.ruobilin.mall.entity.Category;
import com.ruobilin.mall.entity.Product;
import com.ruobilin.mall.entity.ProductProperty;
import com.ruobilin.mall.entity.Province;
import com.ruobilin.mall.entity.User;
import com.ruobilin.mall.service.AreaService;
import com.ruobilin.mall.service.CartProductPropertyService;
import com.ruobilin.mall.service.CartProductService;
import com.ruobilin.mall.service.CartService;
import com.ruobilin.mall.service.CategoryService;
import com.ruobilin.mall.service.OrderService;
import com.ruobilin.mall.service.ProductService;
import com.ruobilin.search.utils.CookieUtil;
import com.ruobilin.search.utils.math.MyMath;

@Controller
@RequestMapping("p")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartProductService cartProductService;
	
	@Autowired
	private CartProductPropertyService cartProductPropertyService;
	
	@Autowired
	private OrderService orderService;
	
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
	
	@RequestMapping(value = "/list/{code}")
	public String list(@PathVariable("code") String code, 
			@RequestParam(required = false,defaultValue = "1")int page, 
			@RequestParam(required = false,defaultValue = "20")int limit, 
			@RequestParam(required = false,defaultValue = "saled")String sort, 
			@RequestParam(required = false,defaultValue = "desc")String dir,
			String cat,
			Model model,HttpSession session) {
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
		
		List<List<String>> idsList=new ArrayList<List<String>>();
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
				if("".equals(listMap.get(obj[1]))||"null".equals(listMap.get(obj[1]))||listMap.get(obj[1])==null){
					for(int c=0;c<param.length;c++){
						String[] objc=param[c].split(":");
						if(objc.length<2){
							continue;
						}
						if(obj[1].equals(objc[1])){
							valuesList.add(objc[0]);
						}
					}
					listMap.put(obj[1], valuesList);
					idsList.add(valuesList);
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
		
		map.put("propertisIds", idsList.size()<1?null:idsList);	//菜单属性IDList内存valueList
		if("created".equals(orderSelect)){
			map.put("orderSelect", orderSelect);
		}
		if(resultSearchTxt!=null&&!"".equals(resultSearchTxt)){
			map.put("productName", "%"+resultSearchTxt+"%");
		}
		Category c = categoryService.getByCode(code);
		PageList<Product> products = productService.findByCategory(c, page, limit, sort, dir,map);
		model.addAttribute("products", products);
		model.addAttribute("category", c);
		model.addAttribute("orderSelect",orderSelect);
		return "product/list";
	}
	
	@RequestMapping(value = "/{id}")
	public String item(@PathVariable("id")String id, Model model, HttpServletRequest request) {
		Product product = productService.getById(id);
		if (product == null) {
			return "404";
		}
		
		model.addAttribute("product", product);
		model.addAttribute("city", areaService.getCityById(product.getShop().getCityId()));

		String ip = getIpaddress(request);
		if (ip == null || ip.equals("") || ip.equals("localhost") || ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "27.131.220.1";
		}
		AreaIp ai = areaService.getAreaIpForHit(ip);
		Province province = null;
		if (ai != null && ai.getProvinceId() != null) {
			province = areaService.getProvinceById(ai.getProvinceId());
		} 
		ProductProperty pp = productService.getExpressPropertyByIp(product, ai);
		int value;
		if(pp!=null&&pp.getValue()!=null&&!"".equals(pp.getValue())&&!"null".equals(pp.getValue())){
			value=(int)MyMath.down(Double.valueOf(pp.getValue()), 0);
		}else{
			value=0;
		}
		model.addAttribute("expressFee",value);
		model.addAttribute("destProvince", province != null ? province.getCnName() : "未知");
		return "product/item";
	}
	private String getIpaddress(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
        if (request.getHeader("x-forwarded-for") != null
                && !"unknown".equalsIgnoreCase(request.getHeader("x-forwarded-for"))) {
            ip = ip + "," + request.getHeader("x-forwarded-for");
        }
        return ip;
	}
	
	/**
	 * 购物车存入cookie中的名字为carProduct，数据式为  [物品ID:物品数量:[物品属性值ID,~]]
	 * @param pid
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "shopitm")
	public String shopitm(String pid,HttpServletResponse response,HttpServletRequest request,Model model) {
		//判断用户是否登录，登录后直接添加商品到购物车数据表
		User user=(User) request.getSession().getAttribute("userInfo");
		if(user!=null&&user.getId()!=null&&!"".equals(user.getId())){
			//判断用户购物车表是否存数据
			List<Cart> cartList=cartService.queryCart(user.getId().toString());
			if(cartList.size()<1){
				productService.saveCart(null,pid, user.getId());
			}else if(pid!=null){
				String[] pids=pid.split(":");
				if(pids.length>1){
					List<CartProduct> listProduct=cartProductService.queryCartProduct(cartList.get(0).getId());
					boolean bool=true;
					for(int i=0;i<listProduct.size();i++){
						CartProduct cp=listProduct.get(i);
						if(pids[0].equals(cp.getProductId())){
							if(pids.length>2&&!"".equals(pids[2].split(",")[0])){
								String[] ppid=pids[2].split(",");
								List<String> ppVid=new ArrayList<String>();
								for(int j=0;j<ppid.length;j++){
									if(!"".equals(ppid[j])){
										ppVid.add(ppid[j]);
									}
								}
								List<String> listr=cartProductPropertyService.queryCartProductPropertyCartId(cp.getId());
								if(listr.size()==ppVid.size()&&listr.containsAll(ppVid)){
									productService.updateCart(pid, cartList.get(0).getId());
									bool=false;
									break;
								}
							}
						}
					}
					if(bool){
						productService.saveCart(cartList.get(0).getId(),pid, user.getId());
					}
				}
			}
			return "redirect:addToCart?bool=1";
		}
		
		String str=CookieUtil.getCookie("carProduct", request);
		StringBuffer sb=new StringBuffer();
		if(str!=null&&!"".equals(str)){
			String[] cookieStrs=str.split("&");
			for(int i=0;i<cookieStrs.length;i++){
				int count=0;
				if(cookieStrs[i].split(":").length<2){
					continue;
				}
				String pids=cookieStrs[i].split(":")[0];
				String ppids=cookieStrs[i].split(":").length<3?"":cookieStrs[i].split(":")[2];
				if(pid!=null&&pids!=null&&pids.equals(pid.split(":")[0])&&ppids.equals(pid.split(":").length<3?"":pid.split(":")[2])){
					count=(Integer.valueOf(cookieStrs[i].split(":")[1])+Integer.valueOf(pid.split(":")[1]));
					pid=null;
				}
				sb.append(cookieStrs[i].split(":")[0]);
				sb.append(":");
				sb.append(count==0?cookieStrs[i].split(":")[1]:count);
				sb.append(":");
				sb.append(cookieStrs[i].split(":").length<3?"":cookieStrs[i].split(":")[2]);
				if(i!=cookieStrs.length-1){
					sb.append("&");
				}
			}
			if(pid!=null){
				sb.append("&"+pid);
			}
			str=sb.toString();
		}else{
			str=pid;
		}
		CookieUtil.addCookie("carProduct", str, -1, response);
		
		return "redirect:addToCart";
	}
	
	/**
	 * 成功加入购物车页面
	 * @return
	 */
	@RequestMapping(value = "addToCart")
	public String addToCart(String bool,HttpServletRequest request,Model model){
//		if(bool!=null&&!"".equals(bool)){
//			User user=(User) request.getSession().getAttribute("userInfo");
//			model.addAttribute("listCart",orderService.queryCartList(user.getId().toString()));
//		}else{
//			model.addAttribute("listCart",this.listCart(request, model));
//		}
		return "product/addToCart";
	}

	/**
	 * 查询购物车数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "account")
	public String account(HttpServletRequest request,Model model) {
		User user=(User) request.getSession().getAttribute("userInfo");
		if(user!=null&&user.getId()!=null){
			List<Map<String,Object>> list=orderService.queryCartList(user.getId().toString());
			if(list!=null&&list.size()>0){
				Map<String,Object> mmap=list.get(list.size()-1);
				model.addAttribute("munCart",mmap.get("munCart"));
				list.remove(list.size()-1);
			}else{
				model.addAttribute("munCart",0);
			}
			model.addAttribute("list",list);
		}else{
			model.addAttribute("list",this.listCart(request, model));
		}
		return "product/account";
	}
	
	private List<Map<String,Object>> listCart(HttpServletRequest request,Model model){
		String str=CookieUtil.getCookie("carProduct", request);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		int munCart=0;
		if(str!=null&&!"".equals(str)){
			String[] obj=str.split("&");
			for(int i=0;i<obj.length;i++){
				Map<String,Object> map=new HashMap<String, Object>();
				String[] obj1=obj[i].split(":");
				if(obj1.length<2){
					continue;
				}
				map.put("product", productService.getProductShoppingCar(obj1));
				map.put("pCount", obj1.length<2?null:obj1[1]);
				map.put("delP",obj[i]);	//删除时需要delCart方法参数
				list.add(map);
				munCart +=obj1.length<2?0:Integer.valueOf(obj1[1]);
			}
		}
		model.addAttribute("munCart",munCart);
		return list;
	}
	
	/**
	 * 删除购物车中的商品
	 * @return
	 */
	@RequestMapping(value = "/delCart")
	@ResponseBody
	public String delCart(String pid,String cartProductId,HttpServletResponse response,HttpServletRequest request){
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		//判断用户是否登录
		if(principalCollection!=null){
			String userId=principalCollection.getRealmNames().iterator().next();
			List<Map<String,Object>> list=orderService.queryCartList(userId);
			if(list.size()>1){	//商品种类大于1
				if(cartProductId!=null&&!"".equals(cartProductId)){
					cartProductService.deleteCartProduct(cartProductId);
				}
			}else{
				//商品种类等于或者小于1，删除购物车所有数据
				cartService.delectCartUserId(userId);
				cartProductService.deleteCartProduct(cartProductId);
			}
			
		}else{
			if(pid==null){
				return "-1";
			}
			String str=CookieUtil.getCookie("carProduct", request);
			StringBuffer sb=new StringBuffer();
			if(!"".equals(str)&&str!=null){
				String[] obj=str.split("&");
				for(int i=0;i<obj.length;i++){
					String[] pids=obj[i].split(":");
					if(pid.equals(pids[0])||"".equals(pids[0])){
						continue;
					}
					sb.append(obj[i]);
					if(i!=obj.length-1){
						sb.append("&");
					}
				}
			}
			CookieUtil.addCookie("carProduct", sb.toString(), -1, response);
		}
		return "";
	}
	
	/**
	 * 更新cookie中的购物车商品数量
	 * @param carProduct 数据式为  [物品ID:物品数量:[物品属性ID,~]&]
	 * @param response
	 * @param request
	 */
	@RequestMapping("/updateCookieCart")
	@ResponseBody
	public String updateCookieCart(String carProduct,String munCart,String cartProductId,HttpServletResponse response,HttpServletRequest request){
		String str=CookieUtil.getCookie("carProduct", request);
		if(str!=null&&!"".equals(str)&&carProduct!=null&&!"".equals(carProduct)){
			StringBuffer sb=new StringBuffer();
			String[] carProducts=carProduct.split("&")[0].split(":");
			String[] strs=str.split("&");
			for(int i=0;i<strs.length;i++){
				int  j=Integer.valueOf(carProducts[1]);
				if("".equals(strs[i])||strs[i]==null){
					continue;
				}
				if(strs[i].split(":")[0].equals(carProducts[0])
						&&strs[i].split(":")[1].equals(carProducts[1])
						&&(strs[i].split(":").length<3||strs[i].split(":")[2].equals(carProducts[2]))){
					j =Integer.valueOf(munCart);
				}
				sb.append(strs[i].split(":")[0]);
				sb.append(":");
				sb.append(j);
				sb.append(":");
				sb.append(strs[i].split(":").length<3?"":strs[i].split(":")[2]);
				if(i!=strs.length-1){
					sb.append("&");
				}
			}
			CookieUtil.addCookie("carProduct", sb.toString(), -1, response);
		}else{
			//更新表数据
			if(cartProductId!=null&&!"".equals(cartProductId)){
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("id", cartProductId);
				map.put("munCart", munCart);
				cartProductService.updateCartProduct(map);
			}
		}
		return "1";
	}
}

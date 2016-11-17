package com.ruobilin.mall.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.controller.model.ShopProduct;
import com.ruobilin.mall.entity.Contact;
import com.ruobilin.mall.entity.ListOfGoods;
import com.ruobilin.mall.entity.Order;
import com.ruobilin.mall.entity.OrderAddress;
import com.ruobilin.mall.entity.OrderProduct;
import com.ruobilin.mall.entity.OrderTable;
import com.ruobilin.mall.entity.Product;
import com.ruobilin.mall.entity.ProductImage;
import com.ruobilin.mall.entity.ProductPrice;
import com.ruobilin.mall.entity.User;
import com.ruobilin.mall.service.CartService;
import com.ruobilin.mall.service.OrderService;
import com.ruobilin.mall.service.OrderTableService;
import com.ruobilin.mall.service.ProductService;
import com.ruobilin.mall.service.ShopService;
import com.ruobilin.mall.service.UserService;
import com.ruobilin.mall.web.cart.Cart;
import com.ruobilin.mall.web.utils.JsonResult;
import com.ruobilin.search.utils.DateUtils;
import com.ruobilin.search.utils.MallWebUtil;
import com.ruobilin.search.utils.constants.MallConstant;

@Controller
@RequestMapping("o")
public class OrderController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private Cart cart;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private OrderTableService orderTableService;
	
	
	/**
	 * 添加到购物车
	 * @param id
	 * @param pp
	 * @return
	 */
	@RequestMapping(value = "/addToCart")
	@ResponseBody
	public String addToCart(@RequestParam(required = false)String id, @RequestParam(required = false)String count, @RequestParam(required = false)String pg, 
			@RequestParam(required = false)String efee, HttpServletRequest request) {
		
		Product p = productService.getById(id);
		if (p == null)
			return "1";
		
		ProductPrice price = findPrice(p, pg);
		if (price == null)
			return "2";
		
		String uid = SecurityUtils.getSubject().getPrincipals().getRealmNames().iterator().next();
		OrderProduct pc =new OrderProduct(Long.valueOf(id), pg, Integer.valueOf(count), price.getPrice(), Integer.valueOf(efee), p.getName(), p.getImages() != null ? p.getImages().get(0).getSmallUrl() : null, p.getShopId());
		cart.addToCart(request.getSession(), Long.valueOf(uid), pc);
		
		return "0";
	}
	
	@RequestMapping(value = "/removeToCart")
	@ResponseBody
	public String removeToCart(Integer index, HttpServletRequest request) {
		try {
			String uid = SecurityUtils.getSubject().getPrincipals().getRealmNames().iterator().next();
			cart.removeAt(request.getSession(), Long.valueOf(uid), index);
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
		
		return "0";
	}
	
	private ProductPrice findPrice(Product p, String pg) {
		if (p.getPrices() == null || p.getPrices().size() < 1)
			return null;
		int size = p.getPrices().size();
		String[] gs = pg.split(",");
		for (int i=0; i<size; i++) {
			ProductPrice pp = p.getPrices().get(i);
			if (pp.getPropertyGroup().equals(pg)) {
				return pp;
			}
				
			String[] pgs = pp.getPropertyGroup().split(",");
			if (pgs == null || pgs.length < 1)
				continue;
			
			boolean foundPrice = true;
			for (int j=0; j<pgs.length; j++) {
				String g = pgs[j];
				boolean found = false;
				for (int k=0; k<gs.length; k++) {
					String tmp = gs[k];
					if (tmp.equals(g)) {
						found = true;
						break;
					}
				}
				if (!found) {
					foundPrice = false;
					break;
				}
			}
			if (foundPrice) {
				return pp;
			}
		}
		
		return null;
	}

	/**
	 * 购物车列表页
	 * @return
	 */
	@RequestMapping(value = "/cart")
	public String cart(Model model, HttpServletRequest request) {
		String uid = SecurityUtils.getSubject().getPrincipals().getRealmNames().iterator().next();
		List<OrderProduct> list = cart.getProducts(request.getSession(), Long.valueOf(uid));
		model.addAttribute("products", list);
		return "order/cart";
	}
	
	/**
	 * 购买第一步，生成订单，配送信息，支付信息，代金券，积分
	 * @return
	 */
	@RequestMapping(value = "/order")
	public String order(Model model, HttpServletRequest request) {
		Long uid = Long.valueOf(SecurityUtils.getSubject().getPrincipals().getRealmNames().iterator().next());
		List<Contact> contacts = userService.findContact(uid);
		List<OrderProduct> products = cart.getProducts(request.getSession(), uid);
		
		model.addAttribute("products", getShopProducts(products));
		model.addAttribute("contacts", contacts);
		return "order/order";
	}
	
	private List<ShopProduct> getShopProducts(List<OrderProduct> products) {
		if (products == null || products.size() < 1)
			return null;
		
		List<ShopProduct> sps = new ArrayList();
		for (OrderProduct p : products) {
			ShopProduct sp = findShop(sps, p);
			if (sp == null) {
				sp = new ShopProduct();
				sp.setProducts(new ArrayList());
				sp.setExpressFee(0);
				sp.setShop(shopService.getById(p.getShopId()));

				sps.add(sp);
			}
			
			sp.getProducts().add(p);
			if (sp.getExpressFee().intValue() < p.getExpressFee()) {
				sp.setExpressFee(p.getExpressFee());
			}
		}
		return sps;
	}

	private ShopProduct findShop(List<ShopProduct> sps, OrderProduct p) {
		if (sps.size() < 1)
			return null;
		for (ShopProduct s : sps) {
			if (s.getShop().getId().equals(p.getShopId()))
				return s;
		}
		return null;
	}

	@RequestMapping(value = "/create")
	@ResponseBody
	public JsonResult create(Long cid, HttpServletRequest request) {
		Long uid = Long.valueOf(SecurityUtils.getSubject().getPrincipals().getRealmNames().iterator().next());
		Contact contact = userService.getContactById(cid);
		List<OrderProduct> products = cart.getProducts(request.getSession(), uid);
		
		if (contact == null || products == null || products.size() < 1)
			return new JsonResult(false, "订单错误。", null);
		
		List<ShopProduct> sps = getShopProducts(products);
		if (sps == null || sps.size() < 1)
			return new JsonResult(false, "订单错误。", null);
		
		String rid = "";
		for (ShopProduct sp : sps) {
			Order o = new Order();
			o.setPrice(sumPrice(products));
			o.setProductCount(products.size());
			o.setProducts(sp.getProducts());
			o.setExpressFee(sp.getExpressFee());
			o.setUserId(uid);
			o.setAddress(getAddress(contact));
			
			String id = orderService.saveOrder(o);
			if (id != null) {
				rid += id + ",";
			}
		}
		
		if (rid.length() > 0) {
			rid = rid.substring(0, rid.length() - 1);
			return new JsonResult(true, "成功。", rid);
		}
		else {
			return new JsonResult(false, "订单生成错误。", null);
		}
		
	}

	private OrderAddress getAddress(Contact contact) {
		OrderAddress oa = new OrderAddress();
		oa.setType(0);
		oa.setContactId(contact.getId());
		oa.setAddress(contact.getAddress());
		oa.setAreaId(contact.getAreaId());
		oa.setCityId(contact.getCityId());
		oa.setMail(contact.getMail());
		oa.setName(contact.getName());
		oa.setPhone(contact.getPhone());
		oa.setCountryId(contact.getCountryId());
		oa.setMobile(contact.getMobile());
		oa.setProvinceId(contact.getProvinceId());
		return oa;
	}

	private Integer sumPrice(List<OrderProduct> products) {
		int sum = 0;
		for (OrderProduct p : products) {
			sum += p.getPrice() * p.getCount();
		}
		return sum;
	}

	/**
	 * 购买第三步，完成
	 * @return
	 */
	@RequestMapping(value = "/finish")
	public String finish(String id, Model model) {
		model.addAttribute("id", id);
		return "order/finish";
	}
	
	/**
	 * 订单列表
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(
			@RequestParam(required = false,defaultValue = "1")int page, 
			@RequestParam(required = false,defaultValue = "20")int limit, 
			@RequestParam(required = false,defaultValue = "id")String sort, 
			@RequestParam(required = false,defaultValue = "desc")String dir,
			Model model) {
		Long uid = Long.valueOf(SecurityUtils.getSubject().getPrincipals().getRealmNames().iterator().next());
		PageList<Order> orders = orderService.findAll(uid, page, limit, sort, dir);
		model.addAttribute("orders", orders);
		return "order/list";
	}
	
	/**
	 * 订单详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public String detail(String id) {
		return "order/detail";
	}
	
	/**
	 * 进入购物车结算页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "settle")
	public String settle(HttpServletRequest request,Model model,HttpServletResponse response) {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		Map<String,Object> map=new HashMap<String, Object>();
		if(principalCollection!=null){
			String userId=principalCollection.getRealmNames().iterator().next();
			//加载结算页数据
			map=orderService.settleQueryInfoSaveCart(userId);
		}
		model.addAttribute("settleMap",map);
		return "shopsCommodity/settle";
	}
	
	
	
	/**
	 * 生成订单编号，跳转到支付方式页面
	 * @return
	 */
	@RequestMapping("/cartPay")
	public String cartPay(Model model,String cartTotal,OrderTable ot){
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		String userId=principalCollection.getRealmNames().iterator().next();
		//生成订单
		if(ot==null){
			ot=new OrderTable();
		}
		ot.setUserId(userId);
		ot.setOrderNumber(MallWebUtil.getTaskSerialNo(userId)); //订单编号
		if(MallConstant.MODE_PAYMENT_DF.equals(ot.getModePayment())){
			ot.setOrderStatus(MallConstant.ORDER_STATUS_FK);
		}else{
			ot.setOrderStatus(MallConstant.ORDER_STATUS_KS);
		}
		ot.setTotal(cartTotal);
		ot.setSubmitTime(DateUtils.format(new Date()));
		orderTableService.insertOrderTable(ot);
		//保存商品
		Map<String,Object> map=orderService.settleQueryInfoSaveCart(userId);
		List<ListOfGoods> list=new ArrayList<ListOfGoods>();
		List<Map<String,Object>> listp=(List<Map<String, Object>>) map.get("productList");
		if(listp!=null&&listp.size()>0){
			for(int i=0;i<listp.size();i++){
				Map<String,Object> p=listp.get(i);
				ListOfGoods listOfGoods=new ListOfGoods();
				listOfGoods.setId(MallWebUtil.getID());
				listOfGoods.setOrderId(ot.getId());
				listOfGoods.setCommodityId(String.valueOf(p.get("id")));
				listOfGoods.setImgUrl(String.valueOf(p.get("imageUrl")));
				listOfGoods.setPrice(String.valueOf(p.get("price")));
				listOfGoods.setNumber(String.valueOf(p.get("productCount")));
				listOfGoods.setName(String.valueOf(p.get("name")));
				listOfGoods.setTableName(String.valueOf(p.get("tableName")));
				list.add(listOfGoods);
			}
			ot.setListOfGoodsList(list);
			orderTableService.insertListOfGoodsList(list);
		}
		//生产订单清空购物车
		cartService.delectCartUserId(userId);
		return "redirect:../o/pay?cartTotal="+cartTotal+"&id="+ot.getOrderNumber()+"&modePayment="+ot.getModePayment();
	}

	/**
	 * 购买第二步，第三方支付
	 * @return
	 */
	@RequestMapping("/pay")
	public String pay(Model model,String cartTotal,String id,String modePayment){
		model.addAttribute("total",cartTotal);
		model.addAttribute("id",id);
		
		if(MallConstant.MODE_PAYMENT_DF.equals(modePayment)){	//货到付款
			return "order/success";
		}
		return "product/cartPay";
	}
	
	/**
	 * 付款成功，改变订单状态
	 * @param id 支付成功的订单编号
	 */
	@RequestMapping("/updateOrderStatus")
	public String updateOrderStatus(String id){
		Map<String,String> map=new HashMap<String, String>();
		map.put("orderStatus", MallConstant.ORDER_STATUS_FK);	//付款成功
		map.put("orderNumber", id);	//订单编号
		orderTableService.updateOrderStatus(map);
		return "redirect:../center/list";
	}
}

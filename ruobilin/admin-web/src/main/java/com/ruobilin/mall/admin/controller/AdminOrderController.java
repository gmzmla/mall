package com.ruobilin.mall.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.admin.entity.AdminOrderTable;
import com.ruobilin.mall.admin.service.AdminOrderService;
import com.ruobilin.search.utils.DateUtils;
import com.ruobilin.search.utils.constants.MallConstant;

@Controller
@RequestMapping("/adminOrder")
public class AdminOrderController {
	private final Logger log = LoggerFactory.getLogger(AdminOrderController.class);
	
	@Autowired
	private AdminOrderService adminOrderService;
	
	/**
	 * 查询待出库订单列表
	 * @return
	 */
	@RequestMapping("/list")
	public String list(
			String orderNumber,
			@RequestParam(required = false,defaultValue = "1")int page, 
			@RequestParam(required = false,defaultValue = "20")int limit, 
			@RequestParam(required = false,defaultValue = "id")String sort, 
			@RequestParam(required = false,defaultValue = "desc")String dir,
			Model model){
		Map<String,String> map=new HashMap<String, String>();
//		map.put("orderStatus", MallConstant.ORDER_STATUS_FK);	//商品付款成功就是待出库商品
		map.put("notOrderStatus", MallConstant.ORDER_STATUS_QX);	//不包含已取消的订单
		if(orderNumber!=null){
			map.put("orderNumber", "%"+orderNumber+"%");
		}
		PageList<AdminOrderTable> list=adminOrderService.findAll(map,page, limit, sort, dir);
		model.addAttribute("list", list);
		return "order/list";
	}
	
	/**
	 * 商品出库
	 * @param orderId	订单ID
	 * @param courierNumber 快递单号
	 * @param express	快递公司
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateOrderStatus")
	@ResponseBody
	public String updateOrderStatus(String orderId,String courierNumber,String express,Model model){
		Map<String,String> map=new HashMap<String, String>();
		map.put("id", orderId); //订单ID
		map.put("orderStatus", MallConstant.ORDER_STATUS_CK);	//订单状态
		map.put("outTime", DateUtils.format(new Date()));	//出库时间
		map.put("courierNumber", courierNumber);	//物流编号
		map.put("express", express);	//快递公司
		//更新订单状态
		adminOrderService.updateOrderStatus(map);
		return "1";
	}
}

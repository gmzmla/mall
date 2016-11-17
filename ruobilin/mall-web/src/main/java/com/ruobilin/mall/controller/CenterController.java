package com.ruobilin.mall.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ruobilin.mall.entity.ListOfGoods;
import com.ruobilin.mall.entity.OrderTable;
import com.ruobilin.mall.entity.User;
import com.ruobilin.mall.service.OrderTableService;
import com.ruobilin.search.utils.DateUtils;
import com.ruobilin.search.utils.constants.MallConstant;

/**
 *	我的比邻-我的订单
 */
@Controller
@RequestMapping("center")
public class CenterController {

	@Autowired
	private OrderTableService orderTableService;
	
	/**
	 * 订单列表
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(String searchParam,
			@RequestParam(required = false,defaultValue = "1")int page, 
			@RequestParam(required = false,defaultValue = "20")int limit, 
			@RequestParam(required = false,defaultValue = "id")String sort, 
			@RequestParam(required = false,defaultValue = "desc")String dir,
			Model model) {
		String userId=String.valueOf(((User)SecurityUtils.getSubject().getSession().getAttribute("userInfo")).getId());
		Map<String,String> map=new HashMap<String, String>();
		if(searchParam !=null){
			map.put("searchParam", "%"+searchParam+"%");
		}
		map.put("userId", userId);
		List<OrderTable> listOrderTable=orderTableService.queryOrderTableList(map);
		if(listOrderTable!=null&&listOrderTable.size()>0){
			List<ListOfGoods> listOfGoodsList=orderTableService.queryListOfGoods(userId);
			for(int i=0;i<listOrderTable.size();i++){
				List<ListOfGoods> listOfGoods=new ArrayList<ListOfGoods>();
				for(int j=0;j<listOfGoodsList.size();j++){
					if(listOfGoodsList.get(j).getOrderId().equals(listOrderTable.get(i).getId())){
						listOfGoods.add(listOfGoodsList.get(j));
					}
				}
				listOrderTable.get(i).setListOfGoodsList(listOfGoods);
			}
		}
		model.addAttribute("listOrderTable", listOrderTable);
		return "center/list";
	}
	
	/**
	 * 订单详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public String detail(String id,Model model) {
		OrderTable orderTable=orderTableService.queryOrderTable(id);
		if(orderTable!=null){
			List<ListOfGoods> listOfGoodsList=orderTableService.queryListOfGoodsId(id);
			orderTable.setListOfGoodsList(listOfGoodsList);
		}
		model.addAttribute("orderTable", orderTable);
		return "center/detail";
	}
	
	/**
	 * 取消订单
	 * @param id
	 * @return
	 */
	@RequestMapping("/cancelOrderTable")
	public String cancelOrderTable(String id){
		Map<String,String> map=new HashMap<String, String>();
		map.put("cancelTime", DateUtils.format(new Date()));	//订单取消时间
		map.put("orderStatus", MallConstant.ORDER_STATUS_QX);	//取消订单
		map.put("id", id);	//订单id
		orderTableService.updateOrderStatus(map);
		return "redirect:/center/list";
	}
	
	/**
	 * 修改订单状态
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateOrderStatus")
	public String updateOrderStatus(String id,String orderStatus){
		Map<String,String> map=new HashMap<String, String>();
		map.put("orderStatus", orderStatus);
		map.put("id", id);	//订单id
		orderTableService.updateOrderStatus(map);
		return "redirect:/center/list";
	}
}

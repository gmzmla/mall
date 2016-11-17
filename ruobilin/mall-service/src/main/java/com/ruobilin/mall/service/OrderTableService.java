package com.ruobilin.mall.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.entity.ListOfGoods;
import com.ruobilin.mall.entity.OrderTable;
import com.ruobilin.mall.mapper.OrderTableMapper;

@Service
public class OrderTableService {
	
	@Autowired
	private OrderTableMapper orderTableMapper;
	
	/**
	 * 保存订单信息
	 * @param orderTable
	 */
	public void insertOrderTable(OrderTable orderTable){
		orderTableMapper.insertOrderTable(orderTable);
	}
	
	/**
	 * 保存订单 信息-商品信息
	 * @param listOfGoodsList
	 */
	public void insertListOfGoodsList(List<ListOfGoods> listOfGoodsList){
		orderTableMapper.insertListOfGoods(listOfGoodsList);
	}
	
	/**
	 * 根据ID查询订单详情
	 * @param id
	 * @return
	 */
	public OrderTable queryOrderTable(String id){
		return orderTableMapper.queryOrderTable(id);
	}
	
	/**
	 * 根据用户ID查询 订单
	 * @param userId
	 * @return
	 */
	public List<OrderTable> queryOrderTableList(Map<String,String> map){
		
		return orderTableMapper.queryOrderTableList(map);
	}
	
	/**
	 * 根据用户ID 查询用户购买的所有商品信息
	 * @param orderId
	 * @return
	 */
	public List<ListOfGoods> queryListOfGoods(String userId){
		return orderTableMapper.queryListOfGoods(userId);
	}
	
	public List<ListOfGoods> queryListOfGoodsId(String orderId){
		return orderTableMapper.queryListOfGoodsId(orderId);
	}
	
	/**
	 * 更新订单状态
	 * @param map 必写订单状态key:orderStatus ,必写修改条件ID或 orderNumber
	 */
	public void updateOrderStatus(Map<String,String> map){
		orderTableMapper.updateOrderStatus(map);
	}
	
}

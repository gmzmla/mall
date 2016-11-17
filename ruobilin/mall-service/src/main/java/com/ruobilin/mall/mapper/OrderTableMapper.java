package com.ruobilin.mall.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.entity.ListOfGoods;
import com.ruobilin.mall.entity.OrderTable;

public interface OrderTableMapper {
	
	/**
	 * 保存订单信息
	 * @param orderTable
	 */
	void insertOrderTable(OrderTable orderTable);
	
	/**
	 * 保存订单--商品清单信息
	 * @param list
	 */
	public void insertListOfGoods(List<ListOfGoods> list);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public OrderTable queryOrderTable(String orderId);
	
	/**
	 * 根据用户ID查询 订单
	 * @param userId
	 * @return
	 */
	public List<OrderTable> queryOrderTableList(Map<String,String> map);
	
	/**
	 * 根据用户ID 查询用户购买的所有商品信息
	 * @param userId
	 * @return
	 */
	public List<ListOfGoods> queryListOfGoods(String userId);
	
	/**
	 * 根据订单ID查询订单下商品清单信息
	 * @param orderId
	 * @return
	 */
	public List<ListOfGoods> queryListOfGoodsId(String orderId);
	
	/**
	 * 更改订单状态
	 */
	public void updateOrderStatus(Map<String,String> map);
	
}

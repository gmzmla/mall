package com.ruobilin.mall.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.admin.entity.AdminListOfGoods;
import com.ruobilin.mall.admin.entity.AdminOrderTable;
import com.ruobilin.mall.admin.mapper.AdminOrderMapper;

@Service
public class AdminOrderService {
	
	@Autowired
	private AdminOrderMapper adminOrderMapper;
	
	/**
	 * 查询待出库订单列表
	 * @return
	 */
	public PageList<AdminOrderTable> findAll(Map<String,String> map,int page,
			int limit, String sort, String dir){
		List<AdminOrderTable> list=adminOrderMapper.queryOrderTableList(map,new PageBounds(page, limit , Order.formString(sort + "." + dir)));
		if(list!=null){
			for(int i=0;i<list.size();i++){
				List<AdminListOfGoods> listOf=this.queryListOfGoodsList();
				List<AdminListOfGoods> listOfGoodsList=new ArrayList<AdminListOfGoods>();
				for(int j=0;j<listOf.size();j++){
					if(list.get(i).getId().equals(listOf.get(j).getOrderId())){
						listOfGoodsList.add(listOf.get(j));
					}
				}
				list.get(i).setListOfGoodsList(listOfGoodsList);
			}
		}
		return (PageList<AdminOrderTable>) list;
	}
	
	/**
	 * 查询订单商品清单
	 * @return
	 */
	public List<AdminListOfGoods> queryListOfGoodsList(){
		return adminOrderMapper.queryListOfGoodsList();
	}
	
	/**
	 * 更新订单状态
	 * @param orderId
	 * @return
	 */
	public void updateOrderStatus(Map<String,String> map){
		adminOrderMapper.updateOrderStatus(map);
	}
}

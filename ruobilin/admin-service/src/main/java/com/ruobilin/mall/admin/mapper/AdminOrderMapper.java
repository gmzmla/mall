package com.ruobilin.mall.admin.mapper;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ruobilin.mall.admin.entity.AdminListOfGoods;
import com.ruobilin.mall.admin.entity.AdminOrderTable;

public interface AdminOrderMapper {

	public List<AdminOrderTable> queryOrderTableList(Map<String,String> map,PageBounds pageBounds);
	
	public List<AdminListOfGoods> queryListOfGoodsList();
	
	public void updateOrderStatus(Map<String,String> map);
}

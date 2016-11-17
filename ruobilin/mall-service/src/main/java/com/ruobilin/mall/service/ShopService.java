package com.ruobilin.mall.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.entity.Product;
import com.ruobilin.mall.entity.Shop;
import com.ruobilin.mall.mapper.ShopMapper;

@Service
public class ShopService {
	@Autowired
	private ShopMapper shopMapper;

	public PageList<Shop> findAll(String name, int page, int limit,
			String sort, String dir) {
		return (PageList<Shop>)shopMapper.findAll(name, new PageBounds(page, limit , Order.formString(sort + "." + dir)));
	}

	public boolean save(Shop shop) {
		try {
			if (shop.getId() == null) {
				shopMapper.insert(shop);
			}
			else {
				shopMapper.update(shop);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
  
	public List<Map> findlist(){
		List<Map> shops=shopMapper.findlist();
		return shops;
	}
	
	
	public void delete(Long id) {
		shopMapper.deleteById(id);
	}

	public Shop getById(Long id) {
		return shopMapper.getById(id);
	}
	
	public List<Product> getProductByShopId(Long id){
		return shopMapper.getProductByShopId(id);
	}
}

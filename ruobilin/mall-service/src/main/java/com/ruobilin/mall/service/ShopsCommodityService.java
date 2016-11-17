package com.ruobilin.mall.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.entity.Cart;
import com.ruobilin.mall.entity.CartProduct;
import com.ruobilin.mall.entity.CommodityInfo;
import com.ruobilin.mall.mapper.CartMapper;
import com.ruobilin.mall.mapper.CartProductMapper;
import com.ruobilin.mall.mapper.ShopsCommodityMapper;

@Service
public class ShopsCommodityService {
	
	@Autowired
	private ShopsCommodityMapper shopsCommodityMapper;
	
	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private CartProductMapper cartProductMapper;
	
	/**
	 * 跟据分类code查询分页产品
	 * @param code
	 * @param page
	 * @param limit
	 * @param sort
	 * @param dir
	 * @param propertisId\propertisValueId 查询条件
	 * @return
	 */
	public PageList<Map<String,String>> findByCategory(int page, int limit,
			String sort, String dir,Map<String,Object> map) {
		
		List<Map<String, String>> ps = shopsCommodityMapper.findByCategoryId(map, new PageBounds(page, limit , Order.formString(sort + "." + dir)));
		
		return (PageList<Map<String,String>>)ps;
	}
	
	/**
	 * 查询幻灯片推荐数据 
	 * @return
	 */
	public List<Map<String,String>> homeProducts(Map<String,String> param){
		return shopsCommodityMapper.homeProducts(param);
	}
	
	/**
	 * 查询商品基本信息
	 * @param id 商品基本信息ID
	 * @return
	 */
	public CommodityInfo queryCommodityBasicInfo(String id){
		CommodityInfo cbi=shopsCommodityMapper.queryCommodityBasicInfo(id);
		cbi.setTableName("commodityBasicInfo");
		if(cbi!=null){
			cbi.setListCommodityImage(shopsCommodityMapper.queryCommodityBasicImage(cbi.getId()));
		}
		return cbi;
	}
	
	/**
	 * 查询商品信息
	 * @param id
	 * @return
	 */
	public CommodityInfo queryCommodityInfo(String id){
		CommodityInfo cbi=shopsCommodityMapper.queryCommodityInfo(id);
		cbi.setTableName("commodityTable");
		if(cbi!=null){
			cbi.setListCommodityImage(shopsCommodityMapper.queryCommodityImage(cbi.getId()));
			Map<String,String> map=new HashMap<String, String>();
			map.put("commodityBasicId", cbi.getCommodityBasicId());
			map.put("commodityId", cbi.getId());
			List<Map<String,Object>> list=analytical(shopsCommodityMapper.queryCommodityProperty(map));
			cbi.setListCommodityProperty(list);
		}
		return cbi;
	}
	
	private List<Map<String,Object>> analytical(List<Map<String,String>> list){
		if(list!=null){
			Map<String,String> mapa=new HashMap<String,String>();
			List<Map<String,Object>> list1=new ArrayList<Map<String,Object>>();
			for(int i=0;i<list.size();i++){
				Map<String,Object> maps=new HashMap<String, Object>();
				List<Map<String,String>> valueList=new ArrayList<Map<String,String>>();
				Map<String,String> map=list.get(i);
				String name=map.get("name");
				String propertyId=map.get("propertyId");
				if(mapa.get(name)==null||"".equals(mapa.get(name))){
					mapa.put(name, name);
					for(int j=0;j<list.size();j++){
						map=list.get(j);
						if(name.equals(map.get("name"))){
							Map<String,String> map1=new HashMap<String, String>();
							map1.put("value", map.get("value"));
							map1.put("propertyValue", map.get("propertyValue"));
							map1.put("commodityBasicId", map.get("commodityBasicId"));
							map1.put("selected", map.get("selected"));
							valueList.add(map1);
						}
					}
					maps.put("name", name);
					maps.put("propertyId", propertyId);
					maps.put("values", valueList);
					list1.add(maps);
				}
			}
			return list1;
		}
		return null;
	}
	
	/**
	 * 根据 {属性ID,属性值ID,商品基本信息ID} 查询商品ID
	 * @param str {属性ID|属性值ID|商品基本信息ID!#!....}
	 * @return
	 */
	public String queryCommodityId(String str){
		String [] strs=str.split(",");
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		for(int i=0;i<strs.length;i++){
			if("".equals(strs[i])){
				continue;
			}
			String[] sb=strs[i].split(":");
			Map<String,String> map=new HashMap<String, String>();
			map.put("propertyId", sb[0]);
			map.put("propertyValue", sb[1]);
			map.put("commodityBasicId", sb[2]);
			list.add(map);
		}
		List<String> commodityIds=shopsCommodityMapper.queryCommodityId(list);
		if(commodityIds!=null){
			return commodityIds.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @param str	商品ID:商品数量:tableName
	 * @param userId	用户ID
	 */
	public void saveCart(String str,String userId){
		Cart cart=new Cart();
		cart.setUserId(userId);
		cartMapper.insertCart(cart);
		
		String[] productStr=str.split(":");
		CartProduct cp=new CartProduct();
		cp.setCartId(cart.getId());
		cp.setProductId(productStr[0]);
		cp.setProductCount(productStr[1]);
		cp.setTableName(productStr[2]);
		cartProductMapper.insertCartProduct(cp);
	}
	
	public void saveCartProduct(String str,String cartId){
		String[] productStr=str.split(":");
		CartProduct cp=new CartProduct();
		cp.setCartId(cartId);
		cp.setProductId(productStr[0]);
		cp.setProductCount(productStr[1]);
		cp.setTableName(productStr[2]);
		cartProductMapper.insertCartProduct(cp);
	}
	
	/**
	 * 更新商品数量
	 * @param id CartProduct表ID
	 * @param productCount	商品数量
	 */
	public void updateCartProduct(String id,String productCount){
		Map<String,String> map=new HashMap<String, String>();
		map.put("id", id);
		map.put("productCount", productCount);
		cartProductMapper.updateCartProductCount(map);
	}
	
	/**
	 * 查询单个 商品信息
	 * @param id
	 * @return
	 */
	public Map<String,Object> findCommodityInfo(String id){
		return shopsCommodityMapper.findCommodityInfo(id);
	}
	public Map<String,Object> findCommodityBasicInfo(String id){
		return shopsCommodityMapper.findCommodityBasicInfo(id);
	}
	
}

package com.ruobilin.mall.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.entity.AreaIp;
import com.ruobilin.mall.entity.Cart;
import com.ruobilin.mall.entity.CartProduct;
import com.ruobilin.mall.entity.CartProductProperty;
import com.ruobilin.mall.entity.Category;
import com.ruobilin.mall.entity.CategoryPropertyValue;
import com.ruobilin.mall.entity.Product;
import com.ruobilin.mall.entity.ProductImage;
import com.ruobilin.mall.entity.ProductPrice;
import com.ruobilin.mall.entity.ProductProperty;
import com.ruobilin.mall.mapper.CartMapper;
import com.ruobilin.mall.mapper.CartProductMapper;
import com.ruobilin.mall.mapper.CartProductPropertyMapper;
import com.ruobilin.mall.mapper.CategoryMapper;
import com.ruobilin.mall.mapper.ProductCommentMapper;
import com.ruobilin.mall.mapper.ProductImageMapper;
import com.ruobilin.mall.mapper.ProductMapper;
import com.ruobilin.mall.mapper.ProductPriceMapper;
import com.ruobilin.mall.mapper.ProductPropertyMapper;
import com.ruobilin.mall.mapper.ShopMapper;

@Service
public class ProductService {
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ProductCommentMapper productCommentMapper;
	
	@Autowired
	private ProductImageMapper productImageMapper;
	
	@Autowired
	private ProductPropertyMapper productPropertyMapper;
	
	@Autowired
	private ProductPriceMapper productPriceMapper;
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private CartProductMapper cartProductMapper;
	
	@Autowired
	private CartProductPropertyMapper cartProductPropertyMapper;
	
	

	@Resource(name = "mysqlTransactionTemplate")
	private TransactionTemplate transactionTemplate;
	


	public List<Map> findAlls(String name){
		List<Map> products=productMapper.findAlis("%"+name+"%");
		return products;
	}
	
	public String getCategoryNameById(Long id){
		return productMapper.getCategoryNameById(id);
	}
	
	
	
	
	/**
	 * 得到最热的n条
	 * @param categories
	 * @param limit
	 * @return
	 */
	public Map<String, List<Product>> findHotByCategories(List<Category> categories, int limit) {
		if (categories == null || categories.size() < 1)
			return null;
		Map<String, List<Product>> map = new HashMap();
		for (Category c : categories) {
			List<Product> ps = productMapper.findHotByCategoryId(c.getId(), limit);
			if (ps == null || ps.size() < 1)
				continue;
			ps = fillProducts(ps);
			
			map.put(c.getCode(), ps);
		}
		return map;
	}

	private List<Product> fillProducts(List<Product> ps) {
		for (Product p : ps) {
			p.setImages(productImageMapper.findByProductId(p.getId()));
			p.setPrices(productPriceMapper.findByProductId(p.getId()));
			p.setPropertis(productPropertyMapper.findByProductId(p.getId()));
		}
		return ps;
	}

	/**
	 * 得到推荐的n条
	 * @param categories
	 * @param limit
	 * @return
	 */
	public Map<String, List<Product>> findRecommendByCategories(
			List<Category> categories, int limit) {
		if (categories == null || categories.size() < 1)
			return null;
		Map<String, List<Product>> map = new HashMap();
		for (Category c : categories) {
			List<Product> ps = productMapper.findRecommendByCategoryId(c.getId(), limit);
			if (ps == null || ps.size() < 1)
				continue;
			
			ps = fillProducts(ps);
			map.put(c.getCode(), ps);
		}
		return map;
	}

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
	public PageList<Product> findByCategory(Category c, int page, int limit,
			String sort, String dir,Map<String,Object> map) {
		if (c == null)
			return null;
		map.put("categoryId", c.getId());	//菜单ID
		List<Product> ps = productMapper.findByCategoryId(map, new PageBounds(page, limit , Order.formString(sort + "." + dir)));
		ps = fillProducts(ps);
		
		return (PageList<Product>)ps;
	}
	
	/**
	 * 通过id得到产品
	 * @param id
	 * @return
	 */
	public Product getById(String id) {
		Product p = productMapper.getById(id);
		if (p == null)
			return null;
		p.setImages(productImageMapper.findByProductId(p.getId()));
		p.setPrices(productPriceMapper.findByProductId(p.getId()));
		p.setPropertis(productPropertyMapper.findByProductId(p.getId()));
		p.setShop(shopMapper.getById(p.getShopId()));
		return p;
	}
	
	public PageList<Product> findAll(Map<String, Object> param, int page,
			int limit, String sort, String dir) {
		return (PageList<Product>)productMapper.findAll(param, new PageBounds(page, limit , Order.formString(sort + "." + dir)));
	}

	public void delete(Long id) {
		productMapper.deleteById(id);
	}

	public boolean save(final Product product) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(
					TransactionStatus transactionStatus) {
				if (product.getId() == null) {
					productMapper.insert(product);
				}
				else {
					productMapper.update(product);
					productImageMapper.deleteByProductId(product.getId());
					productPropertyMapper.deleteByProductId(product.getId());
					productPriceMapper.deleteByProductId(product.getId());
				}
				
				if (product.getImages() != null && product.getImages().size() > 0) {
					for (ProductImage pi : product.getImages()) {
						pi.setProductId(product.getId());
						productImageMapper.insert(pi);
					}
				}
				
				if (product.getPrices() != null && product.getPrices().size() > 0) {
					for (ProductPrice pp : product.getPrices()) {
						pp.setProductId(product.getId());
						productPriceMapper.insert(pp);
					}
				}
				
				if (product.getPropertis() != null && product.getPropertis().size() > 0) {
					for (ProductProperty pp : product.getPropertis()) {
						pp.setProductId(product.getId());
						productPropertyMapper.insert(pp);
					}
				}
			}
		});
		return true;
	}

	public ProductProperty getExpressPropertyByIp(Product product, AreaIp ip) {
		ProductProperty dp = null;
		for (ProductProperty p : product.getPropertis()) {
			if (!p.getType().equals("express"))
				continue;
			if (p.getProperty().equals("t:0")) {
				dp = p;
				continue;
			}
			if (ip == null)
				continue;
			if (p.getProperty().equals("t:1") && ip.getCityId() != null && ip.getCityId().equals(product.getShop().getCityId()))
				return p;
			if (p.getProperty().equals("t:1"))
				continue;

			String[] ss = p.getProperty().split(",");
			if (ss == null || ss.length < 1 || ip.getProvinceId() == null)
				continue;
			for (String s : ss) {
				if (s == null || s.equals(""))
					continue;
				if (Long.valueOf(s).equals(ip.getProvinceId()))
					return p;
			}
		}
		return dp;
	}
	
	/**
	 * 查询幻灯片推荐数据 
	 * @return
	 */
	public List<Map<String,String>> homeProducts(Map<String,String> param){
		return productMapper.homeProducts(param);
	}
	
	/**
	 * 查询购物车商品信息
	 * @return
	 */
	public Product getProductShoppingCar(String[] pid){
		Product p=new Product(); 
		if(pid!=null&&pid.length>1){
			p=productMapper.getById(pid[0]);
			if(p==null){
				return new Product();
			}
			p.setImages(productImageMapper.findByProductId(p.getId()));
		}
		
		//查询价钱与库存
		StringBuilder propertyGroup=new StringBuilder();
		if(pid!=null&&pid.length>2){
			List<ProductProperty> list=null;
			String[] ids=pid[2]!=null?pid[2].split(","):null;
			StringBuffer sb=new StringBuffer();
			if(ids!=null){
				for(int i=0;i<ids.length;i++){
					String id=ids[i];
					if(!"".equals(id)){
						sb.append("'"+ids[i]+"'");
						if(i!=ids.length-1){
							sb.append(",");
						}
					}
				}
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("ids", "".equals(sb.toString())?null:sb.toString());
				map.put("pid",p.getId());
				list=productPropertyMapper.queryShoppingCarProductProperty(map);
			}
			p.setPropertis(list);
			
			for(int i=0;i<list.size();i++){
				ProductProperty pp=list.get(i);
				propertyGroup.append(pp.getProperty());
				propertyGroup.append(":");
				propertyGroup.append(pp.getValue());
				propertyGroup.append("%");
			}
		}
		if("".equals(propertyGroup.toString())){
			propertyGroup.append("0");	//默认价钱与库存
		}
		Map<String,Object> pMap=new HashMap<String, Object>();
		pMap.put("pid", p.getId());
		pMap.put("propertyGroup", propertyGroup.toString());
		List<ProductPrice> plist=productPriceMapper.queryShoppingCarProductPrice(pMap);
		if(plist!=null&&plist.size()<1){
			plist=null;
		}
		p.setPrices(plist);
		return p;
	}
	
	/**
	 * 保存购物车商品信息
	 * @param pid
	 * @param userId
	 */
	public void saveCart(final String cartId ,final String pid,final long userId){
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus){
				String carId=cartId;
				if(cartId==null){
					Cart cart=new Cart();
					cart.setUserId(String.valueOf(userId));
					cartMapper.insertCart(cart);
					carId=cart.getId();
				}
				String[] obj=pid.split(":");
				if(obj.length>1&&!"".equals(obj[0])){
					CartProduct cp=new CartProduct();
					cp.setCartId(carId);
					cp.setProductId(obj[0]);
					cp.setProductCount(obj[1]);
					cartProductMapper.insertCartProduct(cp);
					if(obj.length>2&&!"".equals(obj[2])&&cp.getId()!=null){
						String[] cpps=obj[2].split(",");
						if(cpps.length>0&&!"".equals(cpps[0])){
							List<CartProductProperty> listCartProductProperty=new ArrayList<CartProductProperty>();
							for(int i=0;i<cpps.length;i++){
								CartProductProperty cpp=new CartProductProperty();
								cpp.setPropertyId(cpps[i]);
								cpp.setCartProductId(cp.getId());
								listCartProductProperty.add(cpp);
							}
							cartProductPropertyMapper.insertCartProductProperty(listCartProductProperty);
						}
					}
				}
			}
		});
	}
	
	/**
	 * 更新商品数量
	 * @param count
	 * @param userId
	 */
	public void updateCart(String pid,String cartId){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("cartId", cartId);
		map.put("productCount", pid.split(":")[1]);
		map.put("productId", pid.split(":")[0]);
		cartProductMapper.updateCartProduct(map);
	}
	
	
	
	
	
	/**
	 * 根据关键字查询商品
	 * @author weizhaohui
	 *
	 * 2015年9月3日下午5:27:48
	 *
	 */
	public List<Product> getProductByKeyword(String keyWord){
		List<Product> productList=productMapper.getProductByKeyword(keyWord);
		return productList;
	}
	
	/**
	 * 查询商品类别根据商品id
	 * @author weizhaohui
	 *
	 * 2015年9月3日下午5:28:17
	 *
	 */
	public List<ProductProperty> findByProductId(Long productId){
		return productPropertyMapper.findByProductId(productId);
	}
	/**
	 * 获取类别id
	 * @author weizhaohui
	 *
	 * 2015年9月3日下午5:28:03
	 *
	 */
	public Long getCategoryPropertyId(Map<Object,Object> params){
		return productMapper.getCategoryPropertyId(params);
	}
	
	public List<CategoryPropertyValue> getCategoryPropertyValues(Long id){
		return productMapper.getCategoryPropertyValues(id);
	}
	
	public Long getCategoryPropertyValueId(Map<Object,Object> params){
		return productMapper.getCategoryPropertyValueId(params);
	}
	
	public String getCategoryPropertyName(Map<Object,Object> params){
		return productMapper.getCategoryPropertyName(params);
	}
}

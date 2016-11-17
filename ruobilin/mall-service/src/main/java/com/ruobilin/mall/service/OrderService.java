package com.ruobilin.mall.service;

import java.math.BigDecimal;
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

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.entity.CartProduct;
import com.ruobilin.mall.entity.CartProductProperty;
import com.ruobilin.mall.entity.Cart;
import com.ruobilin.mall.entity.Dispatching;
import com.ruobilin.mall.entity.MailingAddress;
import com.ruobilin.mall.entity.Order;
import com.ruobilin.mall.entity.OrderPayment;
import com.ruobilin.mall.entity.OrderProduct;
import com.ruobilin.mall.entity.Product;
import com.ruobilin.mall.entity.ProductImage;
import com.ruobilin.mall.entity.ProductPrice;
import com.ruobilin.mall.entity.ProductProperty;
import com.ruobilin.mall.entity.User;
import com.ruobilin.mall.mapper.CartMapper;
import com.ruobilin.mall.mapper.CartProductMapper;
import com.ruobilin.mall.mapper.CartProductPropertyMapper;
import com.ruobilin.mall.mapper.ContactMapper;
import com.ruobilin.mall.mapper.MailingAddressMapper;
import com.ruobilin.mall.mapper.OrderAddressMapper;
import com.ruobilin.mall.mapper.OrderMapper;
import com.ruobilin.mall.mapper.OrderPaymentMapper;
import com.ruobilin.mall.mapper.OrderProductMapper;
import com.ruobilin.mall.mapper.ProductImageMapper;
import com.ruobilin.mall.mapper.ProductMapper;
import com.ruobilin.mall.mapper.ProductPriceMapper;
import com.ruobilin.mall.mapper.ProductPropertyMapper;
import com.ruobilin.mall.sequence.SequenceGenerator;
import com.ruobilin.search.utils.CookieUtil;
import com.ruobilin.search.utils.math.MyMath;

@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderAddressMapper orderAddressMapper;

	@Autowired
	private OrderPaymentMapper orderPaymentMapper;

	@Autowired
	private OrderProductMapper orderProductMapper;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private CartProductMapper cartProductMapper;
	
	@Autowired
	private CartProductPropertyMapper cartProductPropertyMapper;
	
	@Autowired
	private MailingAddressMapper mailingAddressMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ProductImageMapper productImageMapper;
	
	@Autowired
	private ProductPropertyMapper productPropertyMapper;
	
	@Autowired
	private ProductPriceMapper productPriceMapper;
	
	@Autowired
	private ShopsCommodityService shopsCommodityService;
	
	@Resource(name = "mysqlTransactionTemplate")
	private TransactionTemplate transactionTemplate;

	public String saveOrder(final Order o) {
		o.setId(sequenceGenerator.generate());
		// 0正常处理中，1未支付，2取消，3删除
		o.setStatus(1);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(
					TransactionStatus transactionStatus) {
				orderMapper.insert(o);

				if (o.getAddress() != null) {
					o.getAddress().setOrderId(o.getId());
					orderAddressMapper.insert(o.getAddress());
				}
				if (o.getPayments() != null && o.getPayments().size() > 0) {
					for (OrderPayment p : o.getPayments()) {
						p.setOrderId(o.getId());
						orderPaymentMapper.insert(p);
					}
				}

				if (o.getProducts() != null && o.getProducts().size() > 0) {
					for (OrderProduct p : o.getProducts()) {
						p.setOrderId(o.getId());
						orderProductMapper.insert(p);
					}
				}
			}
		});
		return o.getId();
	}

	public PageList<Order> findAll(Long uid, int page, int limit, String sort,
			String dir) {
		List<Order> list = orderMapper.findAll(
				uid,
				new PageBounds(page, limit,
						com.github.miemiedev.mybatis.paginator.domain.Order
								.formString(sort + "." + dir)));
		if (list == null || list.size() < 1)
			return null;
		for (Order o : list) {
			o.setAddress(orderAddressMapper.getByOrderId(o.getId()));
			o.setProducts(orderProductMapper.findByOrderId(o.getId()));
			if (o.getStatus().intValue() != 1) {
				// 不是为支付
				o.setPayments(orderPaymentMapper.findByOrderId(o.getId()));
			}
		}
		return (PageList<Order>) list;
	}

	/**
	 * 加载结算界面的数据
	 * @param str
	 * @param user
	 * @return
	 */
	public Map<String,Object> settleQueryInfoSaveCart(String userId){
		Map<String,Object> map=new HashMap<String, Object>();
		//加载商品信息
		List<Map<String,Object>> list=orderMapper.queryCartList(userId);
		if(list!=null&&list.size()>0){
			map.put("productList", list);
			//查询收货人地址
			List<MailingAddress> listMailingAddress=mailingAddressMapper.queryMailingAddress(userId);
			map.put("listMailingAddress", listMailingAddress);
		}
		return map;
	}
	
	/**
	 * 先去掉Cookie中已存在购物车表中的商品再保存cookie中新加入的购物车商品，获取数据库中的商品存入cookie中
	 * @param str	cookie中的购物车数据
	 * @param user session中的用户信息
	 */
	public void saveHandleCart(String str,String userId){
		List<Cart> cartList=cartMapper.queryCart(userId);	//查询用户购物车表中商品数据,正常都有一条数据
		String[] productStr=str.split(":");	//商品信息,商品ID:商品数量:tableName
		aIf:if(cartList!=null&&cartList.size()>0){
			for(int i=0;i<cartList.size();i++){
				Cart cart=cartList.get(i);
				List<CartProduct> cartProductList=cartProductMapper.queryCartProduct(cart.getId());
				for(int j=0;j<cartProductList.size();j++){
					CartProduct cartProduct=cartProductList.get(j);
					if(cartProduct.getProductId().equals(productStr[0])){
						shopsCommodityService.updateCartProduct(cartProduct.getId(), productStr[1]);
						str=null;
						break aIf;
					}
				}
			}
		}
		if(str!=null){	//用户商品表没数据可直接把商品信息保存到购物车表中
			shopsCommodityService.saveCart(str, userId);
		}
	}
	
	/**
	 * 查询当前用户购物车中的信息
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> queryCartList(String userId){
		List<Map<String,Object>> listMap=orderMapper.queryCartList(userId);
		return listMap;
	}
	
	/**
	 * 根据商品ID查询商品属性
	 * @param commodityId
	 * @return
	 */
	public List<Map<String,String>> queryCommodityProperty(String commodityId){
		List<Map<String,String>> listMap=orderMapper.queryCommodityProperty(commodityId);
		return listMap;
	}
	
}

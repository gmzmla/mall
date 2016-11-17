package com.ruobilin.mall.web.cart;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.ruobilin.mall.entity.OrderProduct;

public class SessionCart implements Cart {
	private final String cartName = "cart";
	
	@Override
	public void addToCart(Object session, Long userId, OrderProduct pc) {
		HttpSession s = (HttpSession)session;
		List<OrderProduct> ps = (List<OrderProduct>)s.getAttribute(cartName);
		if (ps == null) {
			ps = new ArrayList();
		}
		ps.add(pc);
		s.setAttribute(cartName, ps);
	}

	@Override
	public List<OrderProduct> getProducts(Object session, Long userId) {
		HttpSession s = (HttpSession)session;
		return (List<OrderProduct>)s.getAttribute(cartName);
	}

	@Override
	public void removeByProductId(Object session, Long userId, Long productId) {
		HttpSession s = (HttpSession)session;
		List<OrderProduct> ps = (List<OrderProduct>)s.getAttribute(cartName);
		if (ps == null)
			return;
		for (OrderProduct pc : ps) {
			if (pc.getProductId().equals(productId))
				ps.remove(pc);
		}
		s.setAttribute(cartName, ps);
	}

	@Override
	public void removeAll(Object session, Long userId) {
		HttpSession s = (HttpSession)session;
		s.setAttribute(cartName, null);
	}

	@Override
	public void removeAt(Object session, Long userId, int index) {
		HttpSession s = (HttpSession)session;
		List<OrderProduct> ps = (List<OrderProduct>)s.getAttribute(cartName);
		if (ps == null)
			return;
		for (int i=0; i<ps.size(); i++) {
			if (i == index)
				ps.remove(i);
		}
		s.setAttribute(cartName, ps);
	}

}

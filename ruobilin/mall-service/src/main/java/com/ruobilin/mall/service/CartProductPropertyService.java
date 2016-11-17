package com.ruobilin.mall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.mapper.CartProductPropertyMapper;

@Service
public class CartProductPropertyService {
	
	@Autowired
	private CartProductPropertyMapper cartProductPropertyMapper;
	
	/**
	 * 返回购物车属性表的属性ID集合
	 * @param cartProductId
	 * @return
	 */
	public List<String> queryCartProductPropertyCartId(String cartProductId){
		return cartProductPropertyMapper.queryCartProductPropertyId(cartProductId);
	}
}

package com.ruobilin.mall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ruobilin.mall.mapper.ProductMapper;
import com.ruobilin.search.utils.constants.MallConstant;

/**
 * @Description 定时器
 * @author 苏高堋
 * @Date 2015-06-09
 */
@Component("taskService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TaskService {

	@Autowired
	private ProductMapper productMapper;
	
	/**
	 * @Description 每天00：05调用，更新商品状态
	 * @Author 苏高堋
	 * @Date 2015-06-09
	 */
	public void updateProjectStatusSuccess(){
		productMapper.updateAllProduct(MallConstant.PRODUCT_STATUS_DISABLING);
	}
}

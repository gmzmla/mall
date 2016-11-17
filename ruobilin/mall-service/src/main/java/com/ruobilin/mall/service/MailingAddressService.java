package com.ruobilin.mall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.entity.MailingAddress;
import com.ruobilin.mall.mapper.MailingAddressMapper;

@Service
public class MailingAddressService {
	
	@Autowired
	private MailingAddressMapper mailingAddressMapper;
	
	/**
	 * 根据用户ID查询收货地址
	 * @param userId
	 * @return
	 */
	public List<MailingAddress> queryMailingAddress(String userId){
		return mailingAddressMapper.queryMailingAddress(userId);
	}
	
	/**
	 * 保存地址
	 * @param mailingAddress
	 */
	public void saveMailingAddress(MailingAddress mailingAddress){
		mailingAddressMapper.insertMailingAddress(mailingAddress);
	}

	/**
	 * 删除收货地址
	 * @param id
	 */
	public void deleteMailingAddress(String id){
		mailingAddressMapper.deleteMailingAddress(id);
	}
	
	public void updateMailingAddress(MailingAddress mailingAddress){
		mailingAddressMapper.updateMailingAddress(mailingAddress);
	}
	
	public void updateMark(MailingAddress mailingAddress){
		mailingAddressMapper.updateMark(mailingAddress);
	}
}

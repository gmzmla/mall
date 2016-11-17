package com.ruobilin.mall.mapper;

import java.util.List;

import com.ruobilin.mall.entity.MailingAddress;

public interface MailingAddressMapper {

	public void insertMailingAddress(MailingAddress mailingAddress);
	
	public void deleteMailingAddress(String id);
	
	public List<MailingAddress> queryMailingAddress(String userId);
	
	public void updateMailingAddress(MailingAddress mailingAddress);
	
	public void updateMark(MailingAddress mailingAddress);
}

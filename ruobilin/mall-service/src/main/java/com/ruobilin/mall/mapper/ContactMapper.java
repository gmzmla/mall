package com.ruobilin.mall.mapper;

import java.util.List;

import com.ruobilin.mall.entity.Contact;
import com.ruobilin.mall.entity.User;

public interface ContactMapper {

	List<Contact> findByUserId(Long uid);

	Contact getById(Long id);

	void insert(Contact c);

	void update(Contact c);

	void deleteById(Long id);

	void updateStatus(Long uid, int status);

}

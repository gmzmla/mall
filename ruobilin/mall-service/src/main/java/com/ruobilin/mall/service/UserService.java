package com.ruobilin.mall.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.mapper.ContactMapper;
import com.ruobilin.mall.mapper.UserMapper;
import com.ruobilin.mall.entity.Contact;
import com.ruobilin.mall.entity.User;


@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ContactMapper contactMapper;
	
	public UserService() {
	}
	
	public List<User> findAll(){
		return userMapper.list();
	}
	
	public User getUserInfo(Map<String,Object> params){
		return userMapper.getUser(params);
	}

	@Transactional("mysql")
	public void registUser(User user){
		userMapper.insert(user);
	}

	public User login(String email, String pwd) {
		User u = userMapper.getByEmail(email);
		if (u == null)
			return null;
		if (!u.getPassword().equals(pwd))
			return null;
		return u;
	}

	public boolean save(User u) {
		if (u.getId() == null) {
			userMapper.insert(u);
			return u.getId() != null;
		}
		else {
			userMapper.update(u);
			return true;
		}
	}

	@Transactional("mysql")
	public void register(User u, boolean needActivate) {
		if (needActivate) {
			u.setUserStatus(1);
		}
		else {
			u.setUserStatus(0);
		}
		u.setUserType(0);
		save(u);
	}

	public User getById(Long id) {
		return userMapper.getById(id);
	}

	public PageList<User> findAll(String name, int page, int limit, String sort, String dir) {
		return (PageList<User>)userMapper.findAll(name, new PageBounds(page, limit , Order.formString(sort + "." + dir)));
	}
	
	public void delete(Long id) {
		userMapper.deleteById(id);
	}

	public List<Contact> findContact(Long uid) {
		return contactMapper.findByUserId(uid);
	}

	public boolean saveContact(Contact c) {
		if (c.getStatus().intValue() == 0) {
			contactMapper.updateStatus(c.getUserId(), 1);
		}
		if (c.getId() != null) {
			contactMapper.update(c);
		}
		else {
			contactMapper.insert(c);
		}
		
		return false;
	}

	public Contact getContactById(Long id) {
		return contactMapper.getById(id);
	}
}

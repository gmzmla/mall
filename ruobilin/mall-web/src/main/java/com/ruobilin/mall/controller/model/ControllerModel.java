package com.ruobilin.mall.controller.model;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.ruobilin.mall.entity.User;


public class ControllerModel {
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public User getUserInfo(){
		Subject currentUser = SecurityUtils.getSubject(); 
		Session session = currentUser.getSession();
		User user=(User) session.getAttribute("userInfo");
		return user;
	}
}

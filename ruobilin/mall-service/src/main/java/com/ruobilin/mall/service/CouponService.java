package com.ruobilin.mall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruobilin.mall.mapper.UserCouponMapper;

@Service
public class CouponService {
	@Autowired
	private UserCouponMapper userCouponMapper;
}

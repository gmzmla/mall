package com.ruobilin.mall.admin.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruobilin.mall.admin.entity.Recommended;
import com.ruobilin.mall.admin.mapper.RecommendedMapper;
/**
 * 
 * @author weizhaohui
 *
 * 2015年8月6日上午11:09:54
 */
@Service
@Transactional
public class RecommendedService {
	
	@Autowired
	private RecommendedMapper recommendedMapper;
	
	//节点列表展示
	public List<Recommended> findAll() {
		 List<Recommended> recommendedList=recommendedMapper.findAll();
		 if(recommendedList == null){
			 return null;
		 }
		 return recommendedList;
	}
	
	//获取根节点
	private Recommended getRootMenu() {
		Recommended root = new Recommended();
		root.setId(0);
		root.setLevel("0");
		root.setName("推荐商品管理");
		root.setParentId("-1");
		root.setStatus("0");
		return root;
	}
	
	//添加/删除
	public boolean save(Recommended recommended){
		try{
			Date date=new Date();
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(recommended.getCreateTime()==null ||recommended.getCreateTime()==""){
				recommended.setCreateTime(sdf.format(date));
			}
			if(recommended.getEndTime()==null ||recommended.getEndTime()==""){
				recommended.setEndTime(sdf.format(date));
			}
			if(recommended.getId() == null){
				recommendedMapper.addRecommended(recommended);
			}
			else{
				recommendedMapper.updateRecommended(recommended);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public Recommended getById(Integer id) {
		// TODO Auto-generated method stub
		return recommendedMapper.getById(id);
	}

	public void addRecommended(Recommended recommended) {
		recommendedMapper.addRecommended(recommended);

	}


	public void deleteRecommendedById(Integer id) {
		recommendedMapper.deleteRecommendedById(id);

	}

	public void deleteRecommendedByIds(Integer[] ids) {
		recommendedMapper.deleteRecommendedByIds(ids);

	}
	
	//显示所有节点
	public List<Recommended> findAllWithRoot(){
		List<Recommended> reList=recommendedMapper.findAll();
		if(reList == null){
			return null;
		}
		Recommended root=getRootMenu();
		reList.add(0, root);
		return reList;
	}

}

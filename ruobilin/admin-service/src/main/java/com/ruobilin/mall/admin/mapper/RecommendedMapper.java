package com.ruobilin.mall.admin.mapper;

import java.util.List;

import com.ruobilin.mall.admin.entity.Recommended;

/**
 * @author weizhaohui
 *
 * 2015年8月6日
 */
public interface RecommendedMapper {
	
	//查询所有
    List<Recommended> findAll(); 
    
    //根据主键ID查询
    Recommended getById(Integer id);
    
    //新增
    void addRecommended(Recommended recommended);
    
    //根据逐渐ID更改
    void updateRecommended(Recommended recommended);
    
    //根据逐渐ID删除
    void deleteRecommendedById(Integer id);
    
    //批量删除
    void deleteRecommendedByIds(Integer[] ids);

}

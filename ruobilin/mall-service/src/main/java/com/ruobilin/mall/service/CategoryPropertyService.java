package com.ruobilin.mall.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.ruobilin.mall.entity.CategoryProperty;
import com.ruobilin.mall.entity.CategoryPropertyValue;
import com.ruobilin.mall.mapper.CategoryPropertyMapper;
import com.ruobilin.mall.mapper.CategoryPropertyValueMapper;
import com.ruobilin.search.utils.ChinaInitial;

@Service
public class CategoryPropertyService {
	
	@Autowired
	private CategoryPropertyMapper categoryPropertyMapper;
	
	@Autowired
	private CategoryPropertyValueMapper categoryPropertyValueMapper;
	
	@Resource(name = "mysqlTransactionTemplate")
	private TransactionTemplate transactionTemplate;
	
	public List<CategoryProperty> findByCategoryId(long categoryId){
		List<CategoryProperty> list=categoryPropertyMapper.findByCategoryId(categoryId);
		if (list == null||list.size()<1)
			return null;
		
		return list;
	}
	/**
	 * 添加属性
	 * @param cp
	 */
	public void insert(CategoryProperty cp){
		categoryPropertyMapper.insert(cp);
	}
	
	/**
	 * 更新属性
	 * @param cp
	 */
	public void update(CategoryProperty cp){
		categoryPropertyMapper.update(cp);
	}
	
	/**
	 * 删除属性
	 */
	public void delete(final String id){
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(
					TransactionStatus transactionStatus) {
				//删除属性下的值
				categoryPropertyValueMapper.deleteByCategoryValueId(id);
				//删除属性
				categoryPropertyMapper.deleteByCategoryPropertyId(id);
			}
		});
	}
	/**
	 * 删除属性下的值
	 */
	public void deleteCategoryPropertyValue(String id){
		String propertyValueId=id==null?"":id.split("_")[1];
		//删除值
		categoryPropertyValueMapper.delete(propertyValueId);
	}
	/**
	 * 给属性添加值
	 * @param cpValue
	 */
	public void insertCategoryPropertyValue(CategoryPropertyValue cpValue){
		categoryPropertyValueMapper.insertCategoryPropertyValue(cpValue);
	}
	
	public void updateCategoryPropertyValue(CategoryPropertyValue cpValue){
		categoryPropertyValueMapper.updateCategoryPropertyValue(cpValue);
	}
	
	/**
	 * 根据属性ID 查询所有值
	 * @param propertyId
	 * @return
	 */
	public List<CategoryPropertyValue> queryCategoryPropertyValueList(String propertyId){
		return categoryPropertyValueMapper.queryCategoryPropertyValueList(propertyId);
	}
	
	public CategoryProperty findCategoryProperty(String id){
		return categoryPropertyMapper.findCategoryProperty(id);
	}
	
	/**
	 * 根据主键查询值信息
	 * @param id
	 * @return
	 */
	public CategoryPropertyValue findCategoryPropertyValue(String propertyValueId){
		String id=propertyValueId==null?"":propertyValueId.split("_")[1];
		return categoryPropertyValueMapper.findCategoryPropertyValue(id);
	}
	
}

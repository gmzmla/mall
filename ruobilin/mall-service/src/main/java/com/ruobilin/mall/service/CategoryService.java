package com.ruobilin.mall.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.ruobilin.mall.entity.Category;
import com.ruobilin.mall.entity.CategoryProperty;
import com.ruobilin.mall.mapper.CategoryMapper;
import com.ruobilin.mall.mapper.CategoryPropertyMapper;

@Service
public class CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private CategoryPropertyMapper categoryPropertyMapper;

	@Resource(name = "mysqlTransactionTemplate")
	private TransactionTemplate transactionTemplate;
	
	/**
	 * 返回原始的分类列表
	 * @return
	 */
	public List<Category> findAllForList() {
		return categoryMapper.findAll();
	}
	
	/**
	 * 返回分类带层次结构
	 * @return
	 */
	public List<Category> findAllForTree() {
		List<Category> cs = findAllForList();
		if (cs == null || cs.size() < 1)
			return null;
		
		return sortByTree(cs, 0, false);
	}
	
	/**
	 * 返回原始的分类，带属性
	 * @return
	 */
	public List<Category> findTreeWithProperties() {
		List<Category> cs = findAllForList();
		if (cs == null || cs.size() < 1)
			return null;
		
		return sortByTree(cs, 0, true);
	}

	private List<Category> sortByTree(List<Category> cs, long parentId, boolean hasPropery) {
		List<Category> tree = new ArrayList<Category>();
		for (Category c : cs) {
			if (c.getParentId().longValue() != parentId)
				continue;
			
			c.setChildren(sortByTree(cs, c.getId().longValue(), hasPropery));
			if (hasPropery) {
				c.setCategoryProperties(categoryPropertyMapper.findByCategoryId(c.getId()));
			}
			tree.add(c);
		}
		return tree;
	}

	/**
	 * 返回原始的层次结构，添加root节点
	 * @return
	 */
	public List<Category> findAllWithRoot() {
		List<Category> categories = categoryMapper.findAll();
		if (categories == null)
			return null;
		
		categories.add(0, getRootCategory());
		return categories;
	}
	
	private Category getRootCategory() {
		Category root = new Category();
		root.setId(0l);
		root.setCode("root");
		root.setName("分类管理");
		root.setParentId(-1l);
		return root;
	}

	public Category getById(Integer id) {
		return categoryMapper.getById(id);
	}

	public Category getWithProperty(Integer id) {
		Category c = categoryMapper.getById(id);
		if (c == null)
			return c;
		
		List<CategoryProperty> list=categoryPropertyMapper.findByCategoryId(c.getId());
		c.setCategoryProperties(list);
		return c;
	}
	
	public boolean save(final Category category) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(
					TransactionStatus transactionStatus) {
				if (category.getId() == null) {
					categoryMapper.insert(category);
					//更新属性信息
					if (category.getId()!=null&&!"".equals(category.getId())&&category.getParentId()!=null) {
						String pId="PID_"+category.getParentId();
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("pId", pId);	//在页面属性保存时 暂时赋值为PID_+category.getParentId()
						map.put("categoryId", category.getId());
						categoryPropertyMapper.updateCategoryPropertyList(map);
					}
				}
				else {
					categoryMapper.update(category);
				}
				
			}
		});
		
		return true;
	}

	public void delete(final Long id) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(
					TransactionStatus transactionStatus) {
				categoryMapper.deleteById(id);
				categoryPropertyMapper.deleteByCategoryId(id);
			}
		});
	}
	
	public Category getByCode(String code){
		return categoryMapper.getByCode(code);
	}
	
	/**
	 * 根据菜单code 查出菜单下的属性
	 * @return
	 */
	public List<Map<String,Object>> findCategoryCodeQueryProperty(String code){
		
		return categoryPropertyMapper.findCategoryCodeQueryProperty(code);
	}
	
	public List<Map<String,Object>> findCategoryCodeQueryPropertyValue(String code){
		
		return categoryPropertyMapper.findCategoryCodeQueryPropertyValue(code);
	}
}

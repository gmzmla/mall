package com.ruobilin.mall.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruobilin.mall.entity.Category;
import com.ruobilin.mall.entity.CategoryProperty;
import com.ruobilin.mall.entity.CategoryPropertyValue;
import com.ruobilin.mall.service.CategoryPropertyService;
import com.ruobilin.mall.service.CategoryService;
import com.ruobilin.search.utils.ChinaInitial;

@Controller
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryPropertyService categoryPropertyService;
	
	@RequestMapping(value = "/list")
	public String list(Model model) {
		List<Category> categories = categoryService.findAllWithRoot();
		model.addAttribute("categories", categories);
		return "category/list";
	}
	
	@RequestMapping(value = "/list/json")
	@ResponseBody
	public List<Category> listJson() {
		return categoryService.findAllWithRoot();
	}
	
	@RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Category get(@PathVariable("id") Integer id) {
		return categoryService.getWithProperty(id);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public String edit(Category category) {
		if (category.getId() == null) {
			category.setUserId(0l);
			category.setProductCount(0);
		}
		if (categoryService.save(category))
			return "0";
		
		return "1";
	}
	
	private List<CategoryProperty> getProperties(String ptypes,
			String properties, String dataTypes, String pextends) {
		if (ptypes == null || ptypes.length() < 1)
			return null;
		String[] ts = ptypes.split(",");
		String[] ps = properties.split(",");
		String[] ds = dataTypes.split(",");
		String[] es = pextends.split(",");
		List<CategoryProperty> cs = new ArrayList();
		int size = ts.length;
		for (int i=0; i<size; i++) {
			String t = ts[i];
			String p = ps[i];
			String d = ds[i];
			String e = es[i];
			if (t.equals("") || p.equals("") || d.equals("") || e.equals(""))
				continue;
			
			CategoryProperty c = new CategoryProperty();
			c.setDataType(d);
			c.setExtend(Integer.valueOf(e));
			c.setProperty(p);
			c.setType(t);
			
			cs.add(c);
		}
		return cs;
	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(Long id,Model model) {
		categoryService.delete(id);
		return "redirect:../category/list";
	}
	
	/**
	 * 给分类添加属性
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveCategoryProperty")
	@ResponseBody
	public CategoryProperty saveCategoryProperty(String id,String ptype, String propertie, String dataType, String pextend,String pId,String mode,Model model){
		
		List<CategoryProperty> list=getProperties(ptype, propertie, dataType, pextend);
		CategoryProperty categoryProperty=list.get(0);
		if("1".equals(mode)){
			pId=pId.split("_").length>1?pId.split("_")[1]:pId;
		}
		categoryProperty.setCategoryId(pId);
		if(id!=null&&!"".equals(id)){
			categoryProperty.setId(Long.valueOf(id));
			categoryPropertyService.update(categoryProperty);
		}else{
			categoryPropertyService.insert(categoryProperty);
		}
		return categoryProperty;
	}
	
	/**
	 * 给属性添加值
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveCategoryPropertyValue")
	@ResponseBody
	public CategoryPropertyValue saveCategoryPropertyValue(String id,String propertyPId,String name,Model model){
		CategoryPropertyValue cpValue=new CategoryPropertyValue();
		cpValue.setCategoryPropertyId(propertyPId);
		cpValue.setName(name);
		cpValue.setCode(ChinaInitial.getPYIndexStr(cpValue.getName(), true));
		if(id!=null&&!"".equals(id)){
			String propertyValueId=id.split("_").length>1?id.split("_")[1]:id;
			cpValue.setId(propertyValueId);
			cpValue.setCategoryPropertyId(null);
			categoryPropertyService.updateCategoryPropertyValue(cpValue);
		}else{
			categoryPropertyService.insertCategoryPropertyValue(cpValue);
		}
		return cpValue;
	}
	
	/**
	 * 删除分类下的属性
	 * @param model
	 * @return
	 */
	@RequestMapping("/delCategoryProperty")
	@ResponseBody
	public String delCategoryProperty(String id,Model model){
		categoryPropertyService.delete(id);
		return id;
	}
	
	/**
	 * 删除属性下的值
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/delCategoryPropertyValue")
	@ResponseBody
	public String delCategoryPropertyValue(String id,Model model){
		categoryPropertyService.deleteCategoryPropertyValue(id);
		return id;
	}
	
	/**
	 * 根据属性ID查询属性下的所有值
	 * @param propertyId
	 * @return
	 */
	@RequestMapping("/queryCategoryPropertyValueList")
	@ResponseBody
	public List<CategoryPropertyValue> queryCategoryPropertyValueList(String propertyPId){
		
		return categoryPropertyService.queryCategoryPropertyValueList(propertyPId);
	}
	
	/**
	 * 根据ID 查询表信息 ，属性表
	 * @param id
	 * @return
	 */
	@RequestMapping("/findCategoryProperty")
	@ResponseBody
	public CategoryProperty findCategoryProperty(String id){
		
		return categoryPropertyService.findCategoryProperty(id);
	}
	
	/**
	 * 根据ID 查询表信息 . 值表
	 * @param id
	 * @return
	 */
	@RequestMapping("/findCategoryPropertyValue")
	@ResponseBody
	public CategoryPropertyValue findCategoryPropertyValue(String id){
		
		return categoryPropertyService.findCategoryPropertyValue(id);
	}
}

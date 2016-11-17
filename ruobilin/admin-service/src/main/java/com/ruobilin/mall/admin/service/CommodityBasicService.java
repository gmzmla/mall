package com.ruobilin.mall.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruobilin.mall.admin.entity.CategoryBasicProperty;
import com.ruobilin.mall.admin.entity.CommodityBasicImage;
import com.ruobilin.mall.admin.entity.CommodityBasicInfo;
import com.ruobilin.mall.admin.entity.CommodityImage;
import com.ruobilin.mall.admin.entity.CommodityProperty;
import com.ruobilin.mall.admin.entity.CommodityTable;
import com.ruobilin.mall.admin.entity.PropertyInfo;
import com.ruobilin.mall.admin.entity.PropertyValueInfo;
import com.ruobilin.mall.admin.mapper.CategoryBasicPropertyMapper;
import com.ruobilin.mall.admin.mapper.CommodityBasicImageMapper;
import com.ruobilin.mall.admin.mapper.CommodityBasicMapper;
import com.ruobilin.mall.admin.mapper.CommodityImageMapper;
import com.ruobilin.mall.admin.mapper.CommodityPropertyMapper;
import com.ruobilin.mall.admin.mapper.CommodityTableMapper;
import com.ruobilin.mall.admin.mapper.PropertyInfoMapper;
import com.ruobilin.mall.admin.mapper.PropertyValueInfoMapper;
import com.ruobilin.search.utils.MallWebUtil;

@Service
public class CommodityBasicService {
	
	@Autowired
	private CommodityBasicMapper commodityBasicMapper;
	
	@Autowired
	private CategoryBasicPropertyMapper categoryBasicPropertyMapper;
	
	@Autowired
	private PropertyInfoMapper propertyInfoMapper;
	
	@Autowired
	private PropertyValueInfoMapper propertyValueInfoMapper;
	
	@Autowired
	private CommodityTableMapper commodityTableMapper;
	
	@Autowired
	private CommodityPropertyMapper commodityPropertyMapper;
	
	@Autowired
	private CommodityImageMapper commodityImageMapper;
	
	@Autowired
	private CommodityBasicImageMapper commodityBasicImageMapper;
	
	/**
	 * 新增商品基本信息
	 * @param commodityBasicInfo
	 */
	@Transactional
	public void insertCommodityBasicInfo(CommodityBasicInfo commodityBasicInfo){
		//保存商品基本信息
		if(commodityBasicInfo.getId()==null||"".equals(commodityBasicInfo.getId())){
			commodityBasicInfo.setId(MallWebUtil.getID());
			commodityBasicMapper.insertCommodityBasicInfo(commodityBasicInfo);
		}else{
			commodityBasicMapper.updateCommodityBasicInfo(commodityBasicInfo);
			//删除商品属性和值
			categoryBasicPropertyMapper.delListCategoryBasicProperty(commodityBasicInfo.getId());
			//删除销售属性
			propertyInfoMapper.delPropertyInfoAndCommodityId(commodityBasicInfo.getId());
			//删除商品销售属性值
			propertyValueInfoMapper.delListPropertyValueInfo(commodityBasicInfo.getId());
			//删除商品基本信息图片
			commodityBasicImageMapper.delListCommodityBasicImage(commodityBasicInfo.getId());
			//删除商品
			commodityTableMapper.delCommodityTableAndCommodityBasicId(commodityBasicInfo.getId());
			//删除商品属性
			commodityPropertyMapper.delCommodityPropertyAndcommodityBasicId(commodityBasicInfo.getId());
			//删除商品图片
			commodityImageMapper.delCommodityImageAndcommodityId(commodityBasicInfo.getId());
		}
		
		//获取商品基本信息的属性和值
		List<CategoryBasicProperty> listCategoryBasicProperty=commodityBasicInfo.getListCategoryBasicProperty();
		if(listCategoryBasicProperty!=null&&listCategoryBasicProperty.size()>0){
			Map<String,Object> mapParam =new HashMap<String, Object>();
			mapParam.put("list", listCategoryBasicProperty);
			mapParam.put("commodityBasicId", commodityBasicInfo.getId());
			//保存商品基本信息属性
			categoryBasicPropertyMapper.insertListCategoryBasicProperty(mapParam);
		}
		//获取商品销售属性
		List<PropertyInfo> listPropertyInfo=commodityBasicInfo.getListPropertyInfo();
		if(listPropertyInfo!=null&&listPropertyInfo.size()>0){
			Map<String,Object> mapParam =new HashMap<String, Object>();
			mapParam.put("list", listPropertyInfo);
			mapParam.put("commodityBasicId", commodityBasicInfo.getId());
			//保存商品销售属性
			propertyInfoMapper.insertPropertyInfoList(mapParam);
		}
		//获取商品销售属性值
		List<PropertyValueInfo> listPropertyValueInfo=commodityBasicInfo.getListPropertyValueInfo();
		if(listPropertyValueInfo!=null&&listPropertyValueInfo.size()>0){
			Map<String,Object> mapParam =new HashMap<String, Object>();
			mapParam.put("list", listPropertyValueInfo);
			mapParam.put("commodityBasicId", commodityBasicInfo.getId());
			//保存商品销售属性值
			propertyValueInfoMapper.insertPropertyValueInfoList(mapParam);
		}
		//获取商品基本信息图片
		List<CommodityBasicImage> listCommodityBasicImage=commodityBasicInfo.getListCommodityBasicImage();
		if(listCommodityBasicImage!=null&&listCommodityBasicImage.size()>0){
			Map<String,Object> mapParam=new HashMap<String, Object>();
			mapParam.put("list", listCommodityBasicImage);
			mapParam.put("commodityBasicId", commodityBasicInfo.getId());
			commodityBasicImageMapper.insertCommodityBasicImageList(mapParam);
		}
		
		//获取商品信息
		List<CommodityTable> listCommodityTable=commodityBasicInfo.getListCommodityTable();
		if(listCommodityTable!=null&&listCommodityTable.size()>0){
			Map<String,Object> mapParam =new HashMap<String, Object>();
			mapParam.put("list", listCommodityTable);
			mapParam.put("commodityBasicId", commodityBasicInfo.getId());
			//保存商品
			commodityTableMapper.insertCommodityTableList(mapParam);
			
			//获取商品属性
			List<CommodityProperty> listCommodityProperty=commodityBasicInfo.getListCommodityProperty();
			if(listCommodityProperty!=null&&listCommodityProperty.size()>0){
				mapParam.put("list", listCommodityProperty);
				commodityPropertyMapper.insertCommodityPropertyList(mapParam);
			}
			//获取商品图片
			List<CommodityImage> listCommodityImage=commodityBasicInfo.getListCommodityImage();
			if(listCommodityImage!=null&&listCommodityImage.size()>0){
				mapParam.put("list", listCommodityImage);
				commodityImageMapper.insertCommodityImageList(mapParam);
			}
		}
		
	}
	
	/**
	 * 更新商品基本信息
	 * @param commodityBasicInfo
	 */
	public void updateCommodityBasicInfo(CommodityBasicInfo commodityBasicInfo){
		commodityBasicMapper.updateCommodityBasicInfo(commodityBasicInfo);
	}
	
	/**
	 * 查询商品列表信息
	 * @param userId
	 * @return
	 */
	public List<CommodityBasicInfo> queryListCommodity(String userId){
		
		return commodityBasicMapper.queryListCommodity();
	}
	
	/**
	 * 查询商品详情
	 * @return
	 */
	public CommodityBasicInfo queryCommodityBasicInfo(String commodityBasicInfoId){
		//查询基本信息
		CommodityBasicInfo commodityBasicInfo=commodityBasicMapper.queryCommodityBasicInfo(commodityBasicInfoId);
		
		if(commodityBasicInfo==null){
			return null;
		}
		//查询基本信息属性
		List<CategoryBasicProperty> listCategoryBasicProperty=categoryBasicPropertyMapper.queryListCategoryBasicProperty(commodityBasicInfo.getId());
		commodityBasicInfo.setListCategoryBasicProperty(listCategoryBasicProperty);
		
		//查询销售属性
		List<PropertyInfo> listPropertyInfo=propertyInfoMapper.querListPropertyInfo(commodityBasicInfo.getId());	//销售属性
		commodityBasicInfo.setListPropertyInfo(listPropertyInfo);
		//查询销售属性值
		List<PropertyValueInfo> listPropertyValueInfo=propertyValueInfoMapper.queryListPropertyValueInfo(commodityBasicInfo.getId());	//销售属性值
		commodityBasicInfo.setListPropertyValueInfo(listPropertyValueInfo);
		//查询商品基本信息图片
		List<CommodityBasicImage> listCommodityBasicImage=commodityBasicImageMapper.queryListCommodityBasicImage(commodityBasicInfo.getId());	//商品基本信息图片
		commodityBasicInfo.setListCommodityBasicImage(listCommodityBasicImage);
		
		//查询商品信息
		List<CommodityTable> listCommodityTable=commodityTableMapper.queryListCommodityTable(commodityBasicInfo.getId());	//单个商品信息
		commodityBasicInfo.setListCommodityTable(listCommodityTable);
		if(listCommodityTable!=null&&listCommodityTable.size()>0){
			//查询商品属性
			List<CommodityProperty> listCommodityProperty=commodityPropertyMapper.queryListCommodityProperty(commodityBasicInfo.getId());	//商品属性
			//查询商品图片信息
			List<CommodityImage> listCommodityImage=commodityImageMapper.queryListCommodityImage(commodityBasicInfo.getId());	//商品图片
			
			for(int i=0;i<listCommodityTable.size();i++){
				List<CommodityProperty> list1=new ArrayList<CommodityProperty>();
				for(int j=0;j<listCommodityProperty.size();j++){
					if(listCommodityTable.get(i).getId().equals(listCommodityProperty.get(j).getCommodityId())){
						list1.add(listCommodityProperty.get(j));
					}
				}
				listCommodityTable.get(i).setListCommodityProperty(list1);
				
				List<CommodityImage> list2=new ArrayList<CommodityImage>();
				for(int j=0;j<listCommodityImage.size();j++){
					if(listCommodityTable.get(i).getId().equals(listCommodityImage.get(j).getCommodityId())){
						list2.add(listCommodityImage.get(j));
					}
				}
				listCommodityTable.get(i).setListCommodityImage(list2);
			}
//			commodityBasicInfo.setListCommodityProperty(listCommodityProperty);
//			commodityBasicInfo.setListCommodityImage(listCommodityImage);
		}
		
		
		return commodityBasicInfo;
	}
	
	/**
	 * 删除商品基本信息
	 * @param id
	 */
	public void delCommodityBasicInfo(String id){
		commodityBasicMapper.delCommodityBasicInfo(id);
	}
}

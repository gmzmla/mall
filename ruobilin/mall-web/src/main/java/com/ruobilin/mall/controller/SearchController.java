package com.ruobilin.mall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.Doc;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.velocity.runtime.directive.Break;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruobilin.mall.entity.CategoryProperty;
import com.ruobilin.mall.entity.CategoryPropertyValue;
import com.ruobilin.mall.entity.Product;
import com.ruobilin.mall.entity.ProductImage;
import com.ruobilin.mall.entity.ProductProperty;
import com.ruobilin.mall.service.CategoryPropertyService;
import com.ruobilin.mall.service.ProductService;
import com.ruobilin.search.utils.Pagination;

@Controller
@RequestMapping("solrSearch")
public class SearchController {
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryPropertyService categoryPropertyService;
	
	@RequestMapping(value = "/display")
	public String index(String keyWord,Model model,Integer orderbyPrice,String categoryParam ,String categoryPropertyId,Integer pageNo) {
		Integer pageSize = 8;
		//商品信息展示
		ModifiableSolrParams params = new ModifiableSolrParams();
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.set("q", "productname:"+keyWord);
		model.addAttribute("keyWord", keyWord);
		//为了分页创建 StringBuder
		StringBuilder pageParams = new StringBuilder();
		pageParams.append("keyWord=").append(keyWord);
		

		List<String> categoryPropertyIdList=new ArrayList<String>();
		//信息过滤
		chao:if(categoryParam!=null){
			if(categoryParam.split(",").length<1){
				break  chao;
			}
			String[]  param=categoryParam.split(",");
			List<ProductProperty> listProperties = new ArrayList<ProductProperty>();
			for(int c=0;c<param.length;c++){
				if(param[c].split(":").length<1||param[c].split(":")[0].equals("")){
					continue;
				}
				String categoryPropertyValueId=param[c].split(":")[0];
				String categoryPropertyName=param[c].split(":")[1];
				String categoryPropertyValueName=param[c].split(":")[2];
				
				categoryPropertyIdList.add(param[c].split(":")[3]);
				//已选条件回显
				ProductProperty productProperty =new ProductProperty();
				productProperty.setProperty(categoryPropertyName);
				productProperty.setValue(categoryPropertyValueName);
				productProperty.setId(Long.valueOf(categoryPropertyValueId));
				productProperty.setCategoryPropertyId(Long.valueOf(param[c].split(":")[3]));
				listProperties.add(productProperty);
				
				//传进来的 乐视的id  就要把品牌隐藏掉
				StringBuilder str=new StringBuilder();
				for(int i=0;i<10;i++){
					if(i==0){
						str.append("categoryPropertyValue"+i+":"+ categoryPropertyValueId);
					}else{
						str.append(" || categoryPropertyValue"+i+":"+ categoryPropertyValueId);
					}
				}
				solrQuery.addFilterQuery(str.toString());
				pageParams.append("&categoryPropertyValueId=").append(categoryPropertyValueId);
			}
			params.add(solrQuery);
			model.addAttribute("listProperties", listProperties);
		}
		
		//高亮显示  <span style='color:red'></span>
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("productname");
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		solrQuery.setHighlightSimplePost("</span>");
		
		//分页
		Integer start = (Pagination.cpn(pageNo) - 1) * pageSize;
		solrQuery.setStart(start);
		solrQuery.setRows(pageSize);
		
		//查询
		QueryResponse response=null;
		try {
			response=solrServer.query(solrQuery);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//回显结果集
		SolrDocumentList docs = response.getResults();
			//发现多少条
		Long numFound = docs.getNumFound();
			//创建分页对象
			//页号
			//每页数
			//总条数
		Pagination pagination = new Pagination(Pagination.cpn(pageNo),pageSize,numFound.intValue());

		List<Product> products=new ArrayList<Product>();
		boolean bool=true;
		for(SolrDocument doc:docs){
			Product product = new Product();
			Map<String, Map<String, List<String>>> highlighting=response.getHighlighting();
			Map<String, List<String>> map=highlighting.get((String)doc.get("id"));
			String id=(String) doc.get("id");
			product.setId(Long.parseLong(id));
			Long categoryId=(Long) doc.get("categoryId");
			product.setCategoryId(categoryId);
			//显示商品名
			List<String> list=map.get("productname");
			product.setName(list.get(0));
			//显示价格
			Double price=(Double) doc.get("price");
			int i=price.intValue();
			product.setOriginalPrice(Integer.valueOf(i));
			//显示图片
			List<ProductImage> imges=new ArrayList<ProductImage>();
			ProductImage productImage=new ProductImage();
			String url=(String) doc.get("url");
			productImage.setImageUrl(url);
			imges.add(productImage);
			product.setImages(imges);
			products.add(product);
			
			String category=(String) doc.get("category");
			model.addAttribute("category", category);
			
			//获取筛选条件  价格,品牌
			List<CategoryProperty> properties = new ArrayList<CategoryProperty>();
			
			for(int j=0;j<10;j++){
				if((doc.get("categoryPropertyName"+j))!=null && (doc.get("categoryPropertyName"+j))!="" && (doc.get("categoryProperty"+j))!=null && (doc.get("categoryProperty"+j))!=""){
					CategoryProperty categoryProperty = new CategoryProperty();
					categoryProperty.setId((Long)(doc.get("categoryProperty"+j)));
					categoryProperty.setProperty((doc.get("categoryPropertyName"+j)).toString());
					if(!categoryPropertyIdList.contains(String.valueOf(doc.get("categoryProperty"+j)))){
						properties.add(categoryProperty);
					}
				}
			}
			if (bool) {
				model.addAttribute("categoryProperties", properties);
			}
			
			//添加类别属性值
			List<CategoryPropertyValue> categoryPropertyValues=productService.getCategoryPropertyValues(categoryId);
			
			if (bool) {
				model.addAttribute("CategoryPropertyValues", categoryPropertyValues);
			}
			bool=false;
		}
			
		
		
		//结果集放入分页对象
		pagination.setList(products);
		model.addAttribute("products", products);
		//页面展示
		String url = "/solrSearch/productList";
		pagination.pageView(url, pageParams.toString());
		model.addAttribute("pagination", pagination);

		return "solrSearch/solrQueryList";
	}
	
	@RequestMapping(value="/productList")
	@ResponseBody
	public Pagination productList(String keyWord,String categoryParam ,Integer pageNo,Integer orderParam){
		Integer pageSize = 8;
		//商品信息展示
		ModifiableSolrParams params = new ModifiableSolrParams();
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.set("q", "productname:"+keyWord);
		//为了分页创建 StringBuder
		StringBuilder pageParams = new StringBuilder();
		pageParams.append("keyWord=").append(keyWord);
		
				
		//排序开始
		if(null != orderParam && orderParam == 0){
			solrQuery.setSort("price", ORDER.desc);
			pageParams.append("&orderParam=").append(orderParam);
		} else if(null != orderParam && orderParam == 1){
			solrQuery.setSort("last_modified", ORDER.desc);
			pageParams.append("&orderParam=").append(orderParam);
		}

		List<String> categoryPropertyIdList=new ArrayList<String>();
		//信息过滤
		chao:if(categoryParam!=null){
			if(categoryParam.split(",").length<1){
				break  chao;
			}
			String[]  param=categoryParam.split(",");
			List<ProductProperty> listProperties = new ArrayList<ProductProperty>();
			for(int c=0;c<param.length;c++){
				if(param[c].split(":").length<1||param[c].split(":")[0].equals("")){
					continue;
				}
				String categoryPropertyValueId=param[c].split(":")[0];
				String categoryPropertyName=param[c].split(":")[1];
				String categoryPropertyValueName=param[c].split(":")[2];
				
				categoryPropertyIdList.add(param[c].split(":")[3]);
				//已选条件回显
				ProductProperty productProperty =new ProductProperty();
				productProperty.setProperty(categoryPropertyName);
				productProperty.setValue(categoryPropertyValueName);
				productProperty.setId(Long.valueOf(categoryPropertyValueId));
				productProperty.setCategoryPropertyId(Long.valueOf(param[c].split(":")[3]));
				listProperties.add(productProperty);
				
				//传进来的 乐视的id  就要把品牌隐藏掉
				StringBuilder str=new StringBuilder();
				for(int i=0;i<10;i++){
					if(i==0){
						str.append("categoryPropertyValue"+i+":"+ categoryPropertyValueId);
					}else{
						str.append(" || categoryPropertyValue"+i+":"+ categoryPropertyValueId);
					}
				}
				solrQuery.addFilterQuery(str.toString());
				pageParams.append("&categoryPropertyValueId=").append(categoryPropertyValueId);
			}
			params.add(solrQuery);
		}
		
		//高亮显示  <span style='color:red'></span>
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("productname");
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		solrQuery.setHighlightSimplePost("</span>");
		
		//分页
		Integer start = (Pagination.cpn(pageNo) - 1) * pageSize;
		solrQuery.setStart(start);
		solrQuery.setRows(pageSize);
		
		//查询
		QueryResponse response=null;
		try {
			response=solrServer.query(solrQuery);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//回显结果集
		SolrDocumentList docs = response.getResults();
			//发现多少条
		Long numFound = docs.getNumFound();
			//创建分页对象
			//页号
			//每页数
			//总条数
		Pagination pagination = new Pagination(Pagination.cpn(pageNo),pageSize,numFound.intValue());

		List<Product> products=new ArrayList<Product>();
		for(SolrDocument doc:docs){
			Product product = new Product();
			Map<String, Map<String, List<String>>> highlighting=response.getHighlighting();
			Map<String, List<String>> map=highlighting.get((String)doc.get("id"));
			String id=(String) doc.get("id");
			product.setId(Long.parseLong(id));
			Long categoryId=(Long) doc.get("categoryId");
			product.setCategoryId(categoryId);
			//显示商品名
			List<String> list=map.get("productname");
			product.setName(list.get(0));
			//显示价格
			Double price=(Double) doc.get("price");
			int i=price.intValue();
			product.setOriginalPrice(Integer.valueOf(i));
			//显示图片
			List<ProductImage> imges=new ArrayList<ProductImage>();
			ProductImage productImage=new ProductImage();
			String url=(String) doc.get("url");
			productImage.setImageUrl(url);
			imges.add(productImage);
			product.setImages(imges);
			products.add(product);
			
		}
		
		//结果集放入分页对象
		pagination.setList(products);
		//页面展示
		String url = "/solrSearch/productList";
		pagination.pageView(url, pageParams.toString());
		return pagination;
	}
}


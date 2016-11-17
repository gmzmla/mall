package com.ruobilin.mall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruobilin.mall.entity.Area;
import com.ruobilin.mall.entity.City;
import com.ruobilin.mall.entity.Province;
import com.ruobilin.mall.service.AreaService;
 
@Controller
@RequestMapping("area")
public class AreaController {
	
	@Autowired
	private AreaService areaService;
	
	/**
	 * 查出所有省份
	 * @param 国家ID
	 * @return
	 */
	@RequestMapping(value="queryProvinces",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Province> queryProvinces(String countryId){
		if("".equals(countryId)){
			countryId=null;
		}
		List<Province> list=areaService.findProvinces(countryId==null?null:Long.valueOf(countryId));
		return list;
	}
	/**
	 * 根据省份ID查出市
	 * @param provinceId 省ID
	 * @return
	 */
	@RequestMapping(value="queryCities",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<City> queryCities(String provinceId){
		List<City> list=areaService.findCities(Long.valueOf(provinceId));
		return list;
	}
	/**
	 * 根据市ID查出县区
	 * @param cityId	市ID
	 * @return
	 */
	@RequestMapping(value="queryAreas",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Area> queryAreas(String cityId){
		List<Area> list=areaService.findAreas(Long.valueOf(cityId));
		return list;
	}	
	
	/**
	 * 查出省份
	 * @param countryId 国家ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/provinces",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String provinces(Long countryId,String callback, Model model) {
		List<Province> list=areaService.findProvinces(countryId);
		List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Province p=list.get(i);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("name", p.getCnName());
			map.put("id", p.getId());
			mapList.add(map);
		}
		String json=JSONArray.fromObject(mapList).toString();
		if(callback!=null&&!"".equals(callback)){
			return callback+"("+json+")";
		}
		return json;
	}
	/**
	 * 查出市级
	 * @param provinceId 省ID
	 * @return
	 */
	@RequestMapping(value = "/findCities", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findCities(Long provinceId,String callback, Model model) {
		List<City> list= areaService.findCities(provinceId);
		List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			City c=list.get(i);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("name", c.getCnName());
			map.put("id", c.getId());
			mapList.add(map);
		}
		String json=JSONArray.fromObject(mapList).toString();
		if(callback!=null&&!"".equals(callback)){
			return callback+"("+json+")";
		}
		return json;
	}
	/**
	 * 查出县区
	 * @param cityId 县区ID
	 * @return
	 */
	@RequestMapping(value = "/findAreas", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findAreas(Long cityId,String callback, Model model) {
		List<Area> list=areaService.findAreas(cityId);
		List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Area a=list.get(i);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("name", a.getCnName());
			map.put("id", a.getId());
			mapList.add(map);
		}
		String json=JSONArray.fromObject(mapList).toString();
		if(callback!=null&&!"".equals(callback)){
			return callback+"("+json+")";
		}
		return json;
	}
	
}

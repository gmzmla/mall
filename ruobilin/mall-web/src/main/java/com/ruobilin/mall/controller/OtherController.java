package com.ruobilin.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruobilin.mall.entity.Area;
import com.ruobilin.mall.entity.City;
import com.ruobilin.mall.entity.Province;
import com.ruobilin.mall.service.AreaService;

@Controller
@RequestMapping("/other")
public class OtherController {
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/provinces", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Province> provinces(Long countryId, Model model) {
		return areaService.findProvinces(countryId);
	}
	
	@RequestMapping(value = "/cities")
	@ResponseBody
	public List<City> cities(Long provinceId, Model model) {
		return areaService.findCities(provinceId);
	}
	
	@RequestMapping(value = "/areas")
	@ResponseBody
	public List<Area> areas(Long cityId, Model model) {
		return areaService.findAreas(cityId);
	}
}

package com.ruobilin.mall.mapper;

import java.util.List;
import java.util.Map;

import com.ruobilin.mall.entity.Area;
import com.ruobilin.mall.entity.AreaIp;
import com.ruobilin.mall.entity.City;
import com.ruobilin.mall.entity.Country;
import com.ruobilin.mall.entity.Province;

public interface AreaMapper {

	List<Country> findCountries();

	List<Province> findProvinces(Long countryId);

	List<City> findCities(Long provinceId);

	List<Area> findAreas(Long cityId);

	List<AreaIp> findIp(Map<String, Object> params);

	Country getCountryById(Long id);

	Province getProvinceById(Long id);

	City getCityById(Long id);

	Area getAreaById(Long id);

	AreaIp getAreaIpById(Long id);

	void insertCountry(Country country);

	void updateCountry(Country country);

	void deleteCountryById(Long id);

	void insertProvince(Province province);

	void updateProvince(Province province);

	void deleteProvinceById(Long id);

	void insertCity(City city);

	void updateCity(City city);

	void deleteCityById(Long id);

	void insertArea(Area area);

	void updateArea(Area area);

	void deleteAreaById(Long id);

	void insertAreaIp(AreaIp ip);

	void updateAreaIp(AreaIp ip);

	void deleteAreaIpById(Long id);

	Country getCountryByECode(String code);

	AreaIp getAreaIpForHit(long ipn);

}

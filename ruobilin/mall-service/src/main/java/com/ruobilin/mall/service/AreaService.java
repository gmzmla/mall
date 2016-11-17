package com.ruobilin.mall.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.ruobilin.mall.entity.Area;
import com.ruobilin.mall.entity.AreaIp;
import com.ruobilin.mall.entity.City;
import com.ruobilin.mall.entity.Country;
import com.ruobilin.mall.entity.ProductImage;
import com.ruobilin.mall.entity.ProductPrice;
import com.ruobilin.mall.entity.ProductProperty;
import com.ruobilin.mall.entity.Province;
import com.ruobilin.mall.mapper.AreaMapper;

@Service
public class AreaService {
	@Autowired
	private AreaMapper areaMapper;
	
	@Resource(name = "mysqlTransactionTemplate")
	private TransactionTemplate transactionTemplate;

	public List<Country> findCountries() {
		return areaMapper.findCountries();
	}

	public List<Province> findProvinces(Long countryId) {
		return areaMapper.findProvinces(countryId);
	}

	public List<City> findCities(Long provinceId) {
		return areaMapper.findCities(provinceId);
	}

	public List<Area> findAreas(Long cityId) {
		return areaMapper.findAreas(cityId);
	}

	public List<AreaIp> findIp(Long countryId, Long provinceId, Long cityId,
			Long areaId) {
		Map<String, Object> params = new TreeMap();
		params.put("countryId", countryId);
		params.put("provinceId", provinceId);
		params.put("cityId", cityId);
		params.put("areaId", areaId);
		
		return areaMapper.findIp(params);
	}

	public Country getCountryById(Long id) {
		return areaMapper.getCountryById(id);
	}

	public Province getProvinceById(Long id) {
		return areaMapper.getProvinceById(id);
	}

	public City getCityById(Long id) {
		return areaMapper.getCityById(id);
	}

	public Area getAreaById(Long id) {
		return areaMapper.getAreaById(id);
	}

	public AreaIp getAreaIpById(Long id) {
		return areaMapper.getAreaIpById(id);
	}

	public boolean saveCountry(Country country) {
		if (country.getId() == null) {
			areaMapper.insertCountry(country);
		}
		else {
			areaMapper.updateCountry(country);
		}
		return true;
	}

	public void deleteCountryById(Long id) {
		areaMapper.deleteCountryById(id);
	}

	public boolean saveProvince(Province province) {
		if (province.getId() == null) {
			areaMapper.insertProvince(province);
		}
		else {
			areaMapper.updateProvince(province);
		}
		return true;
	}

	public void deleteProvinceById(Long id) {
		areaMapper.deleteProvinceById(id);
	}

	public boolean saveCity(City city) {
		if (city.getId() == null) {
			areaMapper.insertCity(city);
		}
		else {
			areaMapper.updateCity(city);
		}
		return true;
	}

	public void deleteCityById(Long id) {
		areaMapper.deleteCityById(id);
	}

	public boolean saveArea(Area area) {
		if (area.getId() == null) {
			areaMapper.insertArea(area);
		}
		else {
			areaMapper.updateArea(area);
		}
		return true;
	}

	public void deleteAreaById(Long id) {
		areaMapper.deleteAreaById(id);
	}

	public boolean saveAreaIp(AreaIp ip) {
		if (ip.getId() == null) {
			areaMapper.insertAreaIp(ip);
		}
		else {
			areaMapper.updateAreaIp(ip);
		}
		return true;
	}

	public void deleteAreaIpById(Long id) {
		areaMapper.deleteAreaIpById(id);
	}

	public boolean saveAreaIps(final List<AreaIp> ips) {
		if (ips == null || ips.size() < 1)
			return false;
		
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(
					TransactionStatus transactionStatus) {
				for (AreaIp ip : ips) {
					areaMapper.insertAreaIp(ip);
				}
			}
		});
		return true;
	}

	public Country getCountryByCode(String code) {
		return areaMapper.getCountryByECode(code);
	}

	public List<Country> findTree(int type) {
		List<Country> countries = findCountries();
		if (type > 0) {
			for (Country c : countries) {
				c.setProvinces(findProvinces(c.getId()));
				if (c.getProvinces() != null && c.getProvinces().size() > 0 && type > 1) {
					for (Province p : c.getProvinces()) {
						p.setCities(findCities(p.getId()));
					}
				}
			}
		}
		return countries;
	}

	public AreaIp getAreaIpForHit(String ip) {
		long ipn = getIpNumber(ip);
		if (ipn < 1)
			return null;
		return areaMapper.getAreaIpForHit(ipn);
	}
	
	public long getIpNumber(String ip) {
		String[] strs = ip.split("\\.");
        long sum = 0;
        int time = 3;
        for (String s : strs) {
            sum += Math.pow(256L, time) * Long.valueOf(s);
            time --;
        }
        return sum;
	}
}

package com.ruobilin.mall.admin.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruobilin.mall.admin.utils.ImageFactory;
import com.ruobilin.mall.admin.utils.MultipartUploadParser;
import com.ruobilin.mall.admin.utils.RequestParser;
import com.ruobilin.mall.entity.Area;
import com.ruobilin.mall.entity.AreaIp;
import com.ruobilin.mall.entity.City;
import com.ruobilin.mall.entity.Country;
import com.ruobilin.mall.entity.Province;
import com.ruobilin.mall.service.AreaService;

@Controller
@RequestMapping("/area")
public class AreaController {
	private final Logger log = LoggerFactory.getLogger(AreaController.class);
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/tree")
	@ResponseBody
	public List<Country> tree(int type) {
		return areaService.findTree(type);
	}
	
	@RequestMapping(value = "/country")
	public String country(Model model) {
		List<Country> countries = areaService.findCountries();
		model.addAttribute("countries", countries);
		return "area/country";
	}
	
	@RequestMapping(value = "/country/{id}")
	@ResponseBody
	public Country countryItem(@PathVariable("id") Long id, Model model) {
		return areaService.getCountryById(id);
	}
	
	@RequestMapping(value = "/country/save")
	@ResponseBody
	public String saveCountry(Country country, Model model) {
		return areaService.saveCountry(country) ? "0" : "1";
	}
	
	@RequestMapping(value = "/country/del")
	@ResponseBody
	public String delCountry(Long id, Model model) {
		areaService.deleteCountryById(id);
		return "0";
	}
	
	@RequestMapping(value = "/provinces", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Province> provinces(Long countryId, Model model) {
		return areaService.findProvinces(countryId);
	}
	
	@RequestMapping(value = "/province")
	public String province(Long countryId, Model model) {
		List<Province> provinces = areaService.findProvinces(countryId);
		model.addAttribute("provinces", provinces);
		
		return "area/province";
	}
	
	@RequestMapping(value = "/province/{id}")
	@ResponseBody
	public Province provinceItem(@PathVariable("id") Long id, Model model) {
		return areaService.getProvinceById(id);
	}
	
	@RequestMapping(value = "/province/save")
	@ResponseBody
	public String saveProvince(Province province, Model model) {
		return areaService.saveProvince(province) ? "0" : "1";
	}
	
	@RequestMapping(value = "/province/del")
	@ResponseBody
	public String delProvince(Long id, Model model) {
		areaService.deleteProvinceById(id);
		return "0";
	}
	
	@RequestMapping(value = "/cities")
	@ResponseBody
	public List<City> cities(Long provinceId, Model model) {
		return areaService.findCities(provinceId);
	}
	
	@RequestMapping(value = "/city")
	public String city(Long provinceId, Model model) {
		List<City> cities = areaService.findCities(provinceId);
		model.addAttribute("cities", cities);
		
		return "area/city";
	}
	
	@RequestMapping(value = "/city/{id}")
	@ResponseBody
	public City cityItem(@PathVariable("id") Long id, Model model) {
		return areaService.getCityById(id);
	}
	
	@RequestMapping(value = "/city/save")
	@ResponseBody
	public String saveCity(City city, Model model) {
		return areaService.saveCity(city) ? "0" : "1";
	}
	
	@RequestMapping(value = "/city/del")
	@ResponseBody
	public String delCity(Long id, Model model) {
		areaService.deleteCityById(id);
		return "0";
	}
	
	@RequestMapping(value = "/areas")
	@ResponseBody
	public List<Area> areas(Long cityId, Model model) {
		return areaService.findAreas(cityId);
	}
	
	@RequestMapping(value = "/area")
	public String area(Long cityId, Model model) {
		List<Area> areas = areaService.findAreas(cityId);
		model.addAttribute("areas", areas);
		
		return "area/area";
	}
	
	@RequestMapping(value = "/area/{id}")
	@ResponseBody
	public Area areaItem(@PathVariable("id") Long id, Model model) {
		return areaService.getAreaById(id);
	}
	
	@RequestMapping(value = "/area/save")
	@ResponseBody
	public String saveArea(Area area, Model model) {
		return areaService.saveArea(area) ? "0" : "1";
	}
	
	@RequestMapping(value = "/area/del")
	@ResponseBody
	public String delArea(Long id, Model model) {
		areaService.deleteAreaById(id);
		return "0";
	}
	
	@RequestMapping(value = "/ip")
	public String ip(Long countryId, Long provinceId, Long cityId, Long areaId, Model model) {
		List<AreaIp> ip = areaService.findIp(countryId, provinceId, cityId, areaId);
		model.addAttribute("ip", ip);
		
		return "area/ip";
	}
	
	@RequestMapping(value = "/ip/{id}")
	@ResponseBody
	public AreaIp ipItem(@PathVariable("id") Long id, Model model) {
		return areaService.getAreaIpById(id);
	}
	
	@RequestMapping(value = "/ip/save")
	@ResponseBody
	public String saveIp(AreaIp ip, Model model) {
		return areaService.saveAreaIp(ip) ? "0" : "1";
	}
	
	@RequestMapping(value = "/ip/del")
	@ResponseBody
	public String delIp(Long id, Model model) {
		areaService.deleteAreaIpById(id);
		return "0";
	}
	
	private static File TEMP_DIR = new File("uploadsTemp");

	private static String CONTENT_TYPE = "text/plain";
	private static String CONTENT_LENGTH = "Content-Length";
	private static int RESPONSE_CODE = 200;
	
	@RequestMapping(value = "/ip/upload")
	@ResponseBody
	public String upload(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String contentLengthHeader = request.getHeader(CONTENT_LENGTH);
		Long expectedFileSize = StringUtils.isBlank(contentLengthHeader) ? null
				: Long.parseLong(contentLengthHeader);
		RequestParser requestParser;
		
		long tk = (new Date()).getTime();
		String fileName = null;
		String ext = null;
		String tmpPath = null;

		try {
			response.setContentType(CONTENT_TYPE);
			response.setStatus(RESPONSE_CODE);

			if (ServletFileUpload.isMultipartContent(request)) {
				requestParser = RequestParser.getInstance(request,
						new MultipartUploadParser(request, TEMP_DIR, request
								.getSession().getServletContext()));
				
				ext = ImageFactory.getExtName(requestParser.getFilename());
				fileName = tk + ext;
				tmpPath = request.getRealPath("upload/tmp/" + fileName);
				
				requestParser.writeToFile(requestParser.getUploadItem().getInputStream(), new File(tmpPath), null);
			} else {
				requestParser = RequestParser.getInstance(request, null);
				
				ext = ImageFactory.getExtName(requestParser.getFilename());
				fileName = tk + ext;
				tmpPath = request.getRealPath("upload/tmp/" + fileName);
				
				requestParser.writeToFile(request.getInputStream(), new File(tmpPath), expectedFileSize);
			}
			
			if (!importIpFile(tmpPath))
				return uploadResult("上传失败");
			
			return uploadResult(null);
		} catch (Exception e) {
			log.error("Problem handling upload request", e);
			return uploadResult("上传失败" + e.getMessage());
		}
	}
	
	private boolean importIpFile(String tmpPath) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(tmpPath));
			String line = null;
			List<AreaIp> ips = new ArrayList();
			while ((line = br.readLine()) != null){
				AreaIp ai = getIpLine(line);
				if (ai != null) {
					ips.add(ai);
				}
			}
			
			return areaService.saveAreaIps(ips);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private AreaIp getIpLine(String line) {
		String[] ss = line.split(",");
		if (ss == null || ss.length != 5)
			return null;
		AreaIp ip = new AreaIp();
		String tmp = ss[0];
		if (tmp.length() < 2)
			return null;
		tmp = tmp.substring(1, tmp.length() - 1);
		ip.setStart(Long.valueOf(tmp));
		
		tmp = ss[1];
		if (tmp.length() < 2)
			return null;
		tmp = tmp.substring(1, tmp.length() - 1);
		ip.setEnd(Long.valueOf(tmp));
		
		tmp = ss[2];
		if (tmp.length() < 2)
			return null;
		tmp = tmp.substring(1, tmp.length() - 1);
		ip.setCountryId(getCountryIdByCode(tmp));
		if (ip.getCountryId() == null)
			return null;
		
		//描述
		tmp = ss[4];
		if (tmp.length() < 2)
			return null;
		tmp = tmp.substring(1, tmp.length() - 1);
		ip.setRemark(tmp);
		
		if (!setIPArea(ip, ip.getRemark()))
			return null;
		
		return ip;
	}

	private boolean setIPArea(AreaIp ip, String remark) {
		int index = remark.indexOf("[");
		if (index < 0) {
			//只能定位到国家
			ip.setType(0);
			return true;
		}
		
		String tmp = remark.substring(index + 1, remark.length() - 1);
		List<Province> ps = areaService.findProvinces(ip.getCountryId());
		Province fp = null;
		for (Province p : ps) {
			if (tmp.startsWith(p.getCnName())) {
				fp = p;
				break;
			}
		}
		if (fp == null) {
			ip.setType(0);
			return true;
		}
		ip.setProvinceId(fp.getId());
		tmp = tmp.substring(fp.getCnName().length());
		if (tmp.startsWith("省") || tmp.startsWith("市")) {
			tmp = tmp.substring(1);
		}
		
		List<City> cs = areaService.findCities(ip.getProvinceId());
		City fc = null;
		for (City c : cs) {
			if (tmp.startsWith(c.getCnName())) {
				fc = c;
				break;
			}
		}
		if (fc == null) {
			ip.setType(1);
			return true;
		}
		ip.setCityId(fc.getId());
		tmp = tmp.substring(fc.getCnName().length());
		if (tmp.startsWith("市")) {
			tmp = tmp.substring(1);
		}
		
		List<Area> as = areaService.findAreas(ip.getCityId());
		Area fa = null;
		for (Area a : as) {
			if (tmp.indexOf(a.getCnName()) > -1) {
				fa = a;
				break;
			}
		}
		if (fa == null) {
			ip.setType(2);
			return true;
		}
		
		ip.setAreaId(fa.getId());
		ip.setType(3);
		return true;
	}

	private Long getCountryIdByCode(String code) {
		Country c = areaService.getCountryByCode(code);
		if (c == null)
			return null;
		return c.getId();
	}

	private String uploadResult(String failureReason) {
		if (failureReason == null) {
			return "{\"success\":true}";
		} else {
			return "{\"error\": \"" + failureReason + "\"}";
		}
	}
}

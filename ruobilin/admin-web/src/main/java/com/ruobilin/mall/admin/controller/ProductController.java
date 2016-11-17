package com.ruobilin.mall.admin.controller;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ruobilin.mall.admin.utils.DateUtils;
import com.ruobilin.mall.admin.utils.ImageFactory;
import com.ruobilin.mall.admin.utils.MultipartUploadParser;
import com.ruobilin.mall.admin.utils.RequestParser;
import com.ruobilin.mall.admin.vo.ProductVo;
import com.ruobilin.mall.entity.Product;
import com.ruobilin.mall.entity.ProductImage;
import com.ruobilin.mall.entity.ProductPrice;
import com.ruobilin.mall.entity.ProductProperty;
import com.ruobilin.mall.entity.User;
import com.ruobilin.mall.mapper.ShopMapper;
import com.ruobilin.mall.service.CategoryPropertyService;
import com.ruobilin.mall.service.ProductService;
import com.ruobilin.mall.service.ShopService;
import com.ruobilin.mall.service.UserService;
import com.ruobilin.search.utils.constants.MallConstant;
import com.ruobilin.search.utils.math.MyMath;

@Controller
@RequestMapping("/product")
public class ProductController {
	private final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private ImageFactory imageFactory;
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private CategoryPropertyService categoryPropertyService;
	
	@Autowired
	private SolrServer solrServer;
	
	@RequestMapping(value = "/list")
	public String list(@RequestParam(required = false)Long shopId, @RequestParam(required = false,defaultValue = "1")int page, 
			@RequestParam(required = false,defaultValue = "20")int limit, 
			@RequestParam(required = false,defaultValue = "id")String sort, 
			@RequestParam(required = false,defaultValue = "desc")String dir,
			Model model) {
		Map<String, Object> param = new TreeMap();
		param.put("shopId", shopId);
		
		PageList<Product> products = productService.findAll(param, page, limit, sort, dir);
		model.addAttribute("products", products);
		model.addAttribute("page", page);
		model.addAttribute("shopId", shopId);
		model.addAttribute("PRODUCT_STATUS", MallConstant.PRODUCT_STATUS);
		return "product/list";
	}
	
	@RequestMapping(value = "/list/json")
	@ResponseBody
	public List<Map> listJson(String name) {
		return productService.findAlls(name);
	}
	
	@RequestMapping(value = "/add")
	public String edit(String id, Long shopId, Model model,HttpServletRequest request) {
		Product product=new Product();
		if (id != null) {
			product = productService.getById(id);
		}else{
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			String email=String.valueOf(session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY"));//获取当前用户登录名
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("email", email);
			User user=userservice.getUserInfo(params);
			product.setUserId(String.valueOf(user.getId()));
			product.setUserName(user.getName());
			product.setShopName(shopMapper.getById(shopId).getName());
		}
		model.addAttribute("product", product);
		model.addAttribute("shopId", shopId);
		model.addAttribute("PRODUCT_STATUS", MallConstant.PRODUCT_STATUS);
		return "product/edit";
	}
	
	@RequestMapping("/editSave")
	public String edit(ProductVo productVo, 
			@RequestParam(required = false)String[] imgUrls, @RequestParam(required = false)String[] imgTypes, @RequestParam(required = false)String[] smallUrls, 
			@RequestParam(required = false)String[] groups, @RequestParam(required = false)String[] pprices, @RequestParam(required = false)String[] inventories,
			@RequestParam(required = false)String[] cpids, @RequestParam(required = false)String[] ptypes, 
			@RequestParam(required = false)String[] pproperties, @RequestParam(required = false)String[] pvalues, @RequestParam(required = false)String[] pextends,
			Model model) {
		if (productVo.getId() == null||"".equals(productVo.getId())) {
			productVo.setId(null);
			productVo.setGrade("0");	//评论数
			productVo.setSaled("0");	//出售数量
			productVo.setCreated(DateUtils.getTodayByFormat(new Date(), null));
		}
		Product product=new Product();
		product.setId(productVo.getId()==null?null:Long.valueOf(productVo.getId()));
		product.setName(productVo.getName());
		try {
			String newParam=new String(Base64.decodeBase64(productVo.getContent()), "UTF-8");
			newParam=URLDecoder.decode(newParam,"UTF-8");
			product.setContent(newParam);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		product.setProductNo(productVo.getProductNo());
		product.setShopId(Long.valueOf(productVo.getShopId()));
		product.setUserId(productVo.getUserId()==""?null:productVo.getUserId());
		product.setCreated(productVo.getCreated()==null?null:DateUtils.getDateByString(productVo.getCreated(), "yyyy-MM-dd HH:mm:ss") );
		product.setSaled(Integer.valueOf(productVo.getSaled()==null?"0":productVo.getSaled()));	//出售数量
		product.setGrade(Integer.valueOf(productVo.getGrade()==null?"0":productVo.getGrade()));	//评论数量
		product.setOriginalPrice((int)((MyMath.round(Double.valueOf((productVo.getOriginalPrice()==null?0:Double.valueOf(productVo.getOriginalPrice())*100)),0))));
		product.setExpiryDate(productVo.getExpiryDate()==null?null:DateUtils.getDateByString(productVo.getExpiryDate(), "yyyy-MM-dd"));
		product.setStatus(Integer.valueOf(productVo.getStatus()==null?"0":productVo.getStatus()));
		product.setCategoryId(Long.valueOf(productVo.getCategoryId()==null?"0":productVo.getCategoryId()));
		product.setRecommend(Integer.valueOf(productVo.getRecommend()==null?"0":productVo.getRecommend()));
		product.setImages(getImages(imgUrls, smallUrls, imgTypes));
		product.setPropertis(getPropertis(cpids, ptypes, pproperties, pvalues, pextends));
		product.setPrices(getPrices(groups, pprices, inventories));
		if (productService.save(product)){
			
			//引用solr
			SolrInputDocument doc = new SolrInputDocument();
			//商品id
			doc.setField("id", product.getId());
			//名称
			doc.setField("productname", product.getName());
			
			
			//图片
			List<ProductImage> imges=product.getImages();
			for(ProductImage img : imges){
				//展示列表页图片
				if(img.getType() == 0){
				doc.setField("url", img.getImageUrl());
				}
			}
			//上架时间
			doc.setField("last_modified", new Date());
			//价格
			Double originalPrice=Double.valueOf(product.getOriginalPrice().toString());
			originalPrice=originalPrice/100;
			doc.setField("price",originalPrice);
			
			//类型id(平板电视)
			doc.setField("categoryId",product.getCategoryId());
			//类别名称
			String category=productService.getCategoryNameById(product.getCategoryId());
			doc.setField("category", category);
			
			List<ProductProperty> productProperties=productService.findByProductId(product.getId());

			for(int i=0;i<productProperties.size();i++){
				ProductProperty productProperty=productProperties.get(i);
				Map<Object, Object> categoryPropertyTypeParams =new HashMap<Object, Object>();
				categoryPropertyTypeParams.put("categoryId", product.getCategoryId());
				categoryPropertyTypeParams.put("categoryPropertyType", productProperty.getProperty());
				doc.setField("categoryPropertyName"+i, productProperty.getProperty());
				Long categoryPropertyId=productService.getCategoryPropertyId(categoryPropertyTypeParams);
				doc.setField("categoryProperty"+i, categoryPropertyId);
				//获取到价钱,品牌所属的值的id存放到solr中 1000-2000 长虹
				Map<Object, Object> productPropertyValueParams = new HashMap<Object, Object>();
				productPropertyValueParams.put("categoryId", product.getCategoryId());
				productPropertyValueParams.put("categoryPropertyType", productProperty.getProperty());
				productPropertyValueParams.put("categoryPropertyValueName", productProperty.getValue());
				doc.setField("categoryPropertyValueName"+i, productProperty.getValue());
				Long categoryPropertyValueId=productService.getCategoryPropertyValueId(productPropertyValueParams);
				doc.setField("categoryPropertyValue"+i, categoryPropertyValueId);
			}
			try {
				solrServer.add(doc);
				solrServer.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "redirect:/product/list?shopId=" + product.getShopId();
		}
		model.addAttribute("product", product);
		model.addAttribute("message", "失败。");
		return "product/edit";
	}
	
	private List<ProductPrice> getPrices(String[] gs, String[] ps,
			String[] is) {
		if (gs == null || gs.length < 1)
			return null;
		List<ProductPrice> pis = new ArrayList();
		int size = ps.length;
		for (int i=0; i<size; i++) {
			String g = gs[i];
			String p = ps[i];
			String ii = is[i];
			if (g.equals("") || p.equals("") || ii.equals(""))
				continue;
			
			ProductPrice pp = new ProductPrice();
			pp.setInventory(Integer.valueOf(ii));
			pp.setPrice((int)(Double.valueOf(p) * 100));
			pp.setPropertyGroup(g);
			
			pis.add(pp);
		}
		return pis;
	}

	private List<ProductProperty> getPropertis(String[] ids, String[] ts,
			String[] ps, String[] vs, String[] es) {
		if (ids == null || ids.length < 1)
			return null;
		List<ProductProperty> pis = new ArrayList();
		int size = ids.length;
		for (int i=0; i<size; i++) {
			String d = ids[i];
			String t = ts[i];
			String p = ps[i];
			String v = vs[i];
			String e = es[i];
			if (d.equals("") || t.equals("") || p.equals("") || v.equals("") || e.equals(""))
				continue;
			
			ProductProperty pp = new ProductProperty();
			pp.setCategoryPropertyId(Long.valueOf(d));
			pp.setExtend(Integer.valueOf(e));
			pp.setProperty(p);
			pp.setType(t);
			if (t.equals("express")) {
				pp.setValue(String.valueOf(Float.valueOf(v) * 100));
			} else {
				pp.setValue(v);
			}
			
			pis.add(pp);
		}
		return pis;
	}

	private List<ProductImage> getImages(String[] us, String[] ss, String[] ts) {
		if (us == null || us.length < 1)
			return null;
		List<ProductImage> pis = new ArrayList();
		int size = ts.length;
		for (int i=0; i<size; i++) {
			String u = us[i];
			String t = ts[i];
			String s = ss[i];
			if (u.equals("") || t.equals("") || s.equals(""))
				continue;
			
			ProductImage p = new ProductImage();
			p.setImageUrl(u);
			p.setSmallUrl(s);
			p.setType(Integer.valueOf(t));
			
			pis.add(p);
		}
		return pis;
	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(Long id,String shopId) {
		productService.delete(id);
		return "redirect:/product/list?shopId="+shopId;
	}

	private static File TEMP_DIR = new File("uploadsTemp");

	private static String CONTENT_TYPE = "text/plain";
	private static String CONTENT_LENGTH = "Content-Length";
	private static int RESPONSE_CODE = 200;

	@RequestMapping(value = "/upload")
	@ResponseBody
	public String upload(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String contentLengthHeader = request.getHeader(CONTENT_LENGTH);
		Long expectedFileSize = StringUtils.isBlank(contentLengthHeader) ? null
				: Long.parseLong(contentLengthHeader);
		RequestParser requestParser;
		
		long tk = (new Date()).getTime();
		String fileName = null;
		String smallFile = null;
		String ext = null;
		String tmpPath = null;
//		String datePath = imageFactory.getDatePath();

		try {
			response.setContentType(CONTENT_TYPE);
			response.setStatus(RESPONSE_CODE);

			if (ServletFileUpload.isMultipartContent(request)) {
				requestParser = RequestParser.getInstance(request,
						new MultipartUploadParser(request, TEMP_DIR, request
								.getSession().getServletContext()));
				
				ext = ImageFactory.getExtName(requestParser.getFilename());
				fileName = tk + ext;
				smallFile = tk + "_s" + ext;
				tmpPath = 
						imageFactory.getImageSite() + imageFactory.getUploadUrl()+ fileName;
//						request.getRealPath(imageFactory.getTmpUploadPath() + fileName);
				
				requestParser.writeToFile(requestParser.getUploadItem().getInputStream(), new File(tmpPath), null);
			} else {
				requestParser = RequestParser.getInstance(request, null);
				
				ext = ImageFactory.getExtName(requestParser.getFilename());
				fileName = tk + ext;
				smallFile = tk + "_s" + ext;
				tmpPath = 
						imageFactory.getImageService() + imageFactory.getUploadPath()+ fileName;
//						request.getRealPath(imageFactory.getTmpUploadPath() + fileName);
				
				requestParser.writeToFile(request.getInputStream(), new File(tmpPath), expectedFileSize);
			}
			
//			if (!imageFactory.zoomImages(tmpPath, request.getRealPath(imageFactory.getUploadPath()), ext, String.valueOf(tk)))
//				return uploadResult("上传失败", null, null);
			return uploadResult(
					null, 
//					datePath + 
					fileName, 
//					datePath + 
					smallFile);
		} catch (Exception e) {
			log.error("Problem handling upload request", e);
			return uploadResult("上传失败" + e.getMessage(), null, null);
		}
	}
	
	@RequestMapping("/upload_json")
	@ResponseBody
	public void uploadJson(HttpServletRequest request,HttpServletResponse response, Model model){

		String error="";
		//文件保存目录路径
		String savePath = imageFactory.getUploadPath();
		
		//文件保存目录URL
		String saveUrl = imageFactory.getImageSite()+imageFactory.getUploadUrl();

		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		//最大文件大小
		long maxSize = 1000000;

		response.setContentType("text/html; charset=UTF-8");

		if(!ServletFileUpload.isMultipartContent(request)){
			error="请选择文件。";
			return ;
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			error="目录名不正确。";
			return ;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		//创建文件夹
		savePath += dirName+"/";
		saveUrl += dirName+"/";
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		savePath=request.getRealPath(savePath);
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		//检查目录写权限
		if(!dirFile.canWrite()){
			error="上传目录没有写权限。";
			return ;
		}
		

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items;
		try {
			items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			String newFileName="";
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				String fileName = item.getName();
				long fileSize = item.getSize();
				if (!item.isFormField()) {
					//检查文件大小
					if(item.getSize() > maxSize){
						error="上传文件大小超过限制。";
						return ;
					}
					//检查扩展名
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
						error="上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。";
						return ;
					}
					
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
					try{
						File uploadedFile = new File(savePath, newFileName);
						item.write(uploadedFile);
					}catch(Exception e){
						return ;
					}
					
					
					response.setContentType("text/html");
					response.setHeader("Pragma", "No-cache");
					response.setHeader("Cache-Control", "no-cache");
					response.setCharacterEncoding("UTF-8");
					JSONObject obj = new JSONObject();
					obj.put("error", error);
					obj.put("url", saveUrl + newFileName);
					PrintWriter out = null;
					out = response.getWriter();
					out.print(obj);
					out.flush();
					out.close();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private String uploadResult(String failureReason, String name, String smallName) {
		if (failureReason == null) {
			return "{\"success\":true,\"url\":\"" + imageFactory.getImageSite() + imageFactory.getUploadUrl() + name + "\",\"surl\":\"" + imageFactory.getImageSite() + imageFactory.getUploadUrl() + smallName + "\"}";
		} else {
			return "{\"error\": \"" + failureReason + "\"}";
		}
	}
	
	@RequestMapping(value="/downloadexcel")
	public ModelAndView downloadExcel(HttpServletRequest request, HttpServletResponse response, Long shopId){
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet=wb.createSheet("例子");
		HSSFRow row=sheet.createRow((int)0);
		 HSSFCellStyle style=wb.createCellStyle();
		 style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		 HSSFCell cell = row.createCell((short) 0);  
		 cell.setCellValue("id");  
	     cell.setCellStyle(style);  
	     cell = row.createCell((short) 1);  
	     cell.setCellValue("categoryId");  
	     cell.setCellStyle(style);  
	     cell = row.createCell((short) 2);  
	     cell.setCellValue("name");  
	     cell.setCellStyle(style);  
	     cell = row.createCell((short) 3);  
	     cell.setCellValue("content");  
	     cell.setCellStyle(style);
		  
		  cell=row.createCell((short)4);
		  cell.setCellValue("productNo");
		  cell.setCellStyle(style);
		  cell=row.createCell((short)5);
		  cell.setCellValue("shopId");
		  cell.setCellStyle(style);
		  cell=row.createCell((short)6);
		  cell.setCellValue("userId");
		  cell.setCellStyle(style);
		  cell=row.createCell((short)7);
		  cell.setCellValue("created");
		  cell.setCellStyle(style);
		  cell=row.createCell((short)8);
		  cell.setCellValue("saled");
		  cell.setCellStyle(style);
		  cell=row.createCell((short)9);
		  cell.setCellValue("grade");
		  cell.setCellStyle(style);
		  cell=row.createCell((short)10);
		  cell.setCellValue("originalPrice");
		  cell.setCellStyle(style);
		  cell=row.createCell((short)11);
		  cell.setCellValue("expiryDate");
		  cell.setCellStyle(style);
		  cell=row.createCell((short)12);
		  cell.setCellValue("status");
		  cell.setCellStyle(style);
		  cell=row.createCell((short)13);
		  cell.setCellValue("recommend");
		  cell.setCellStyle(style);
		  
		  List<Product> productList=shopService.getProductByShopId(shopId);
		  for(int i=0;i<productList.size();i++){
			 row= sheet.createRow((int) i + 1);
			 Product product=productList.get(i);
			 row.createCell((short)0).setCellValue((Long)product.getId());
			 row.createCell((short)1).setCellValue((Long)product.getCategoryId());
			 row.createCell((short)2).setCellValue((String)product.getName());
			 row.createCell((short)3).setCellValue((String)product.getContent());
			 row.createCell((short)4).setCellValue((String)product.getProductNo());
			 row.createCell((short)5).setCellValue((Long)product.getShopId());
			 row.createCell((short)6).setCellValue((String)product.getUserId());
			 row.createCell((short)7).setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(product.getCreated()));
			 row.createCell((short)8).setCellValue((Integer)product.getSaled());
			 row.createCell((short)9).setCellValue((Integer)product.getGrade());
			 row.createCell((short)10).setCellValue((Integer)product.getOriginalPrice());
			 row.createCell((short)11).setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(product.getExpiryDate()));
			 row.createCell((short)12).setCellValue((Integer)product.getStatus());
			 row.createCell((short)13).setCellValue((Integer)product.getRecommend());
		  }
		  try  
	        {  
	            FileOutputStream fout = new FileOutputStream("E:/product.xls");  
	            wb.write(fout);  
	            fout.close();  
	        }  
	        catch (Exception e)  
	        {  
	            e.printStackTrace();  
	        }  
		return null;
	}
	
	
	
}

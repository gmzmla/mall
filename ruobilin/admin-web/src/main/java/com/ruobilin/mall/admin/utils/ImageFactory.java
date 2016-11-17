package com.ruobilin.mall.admin.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ruobilin.search.utils.image.ImageDisposable;
import com.ruobilin.search.utils.image.Size;


public class ImageFactory {
	private ImageDisposable imageDisposer;
	
	private Size maxSize;
	
	private Size maxSmallSize;
	
	private String uploadPath;
	
	private String uploadUrl;
	
	private String tmpUploadPath;
	
	private String imageSite;
	
	private String imageService;

	public ImageDisposable getImageDisposer() {
		return imageDisposer;
	}

	public void setImageDisposer(ImageDisposable imageDisposer) {
		this.imageDisposer = imageDisposer;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	
	public String getImageService() {
		return imageService;
	}

	public void setImageService(String imageService) {
		this.imageService = imageService;
	}

	public void setMaxSize(String s) {
		String[] ss = s.split("x");
		maxSize = new Size();
		maxSize.setWidth(Integer.valueOf(ss[0]));
		maxSize.setHeight(Integer.valueOf(ss[1]));
	}
	
	public void setMaxSmallSize(String s) {
		String[] ss = s.split("x");
		maxSmallSize = new Size();
		maxSmallSize.setWidth(Integer.valueOf(ss[0]));
		maxSmallSize.setHeight(Integer.valueOf(ss[1]));
	}
	
	public String getTmpUploadPath() {
		return tmpUploadPath;
	}

	public void setTmpUploadPath(String tmpUploadPath) {
		this.tmpUploadPath = tmpUploadPath;
	}

	public String getImageSite() {
		return imageSite;
	}

	public void setImageSite(String imageSite) {
		this.imageSite = imageSite;
	}

	public String getDatePath() {
		Date dt = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd/");
		return sf.format(dt);
	}
	
	public boolean zoomImages(String srcFile, String path, String ext, String name) {
		return zoomImages(srcFile, path, new String[]{name + ext, name + "_s" + ext}, new Size[]{maxSize, maxSmallSize});
	}
	
	public boolean zoomImages(String srcFile, String path, String[] names, Size[] sizes) {
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		int len = names.length;
		for (int i=0; i<len; i++) {
			String dstFile = path + names[i];
			Size s = sizes[i];
			
			createPath(path);
			
			if (!zoomImage(srcFile, dstFile, s))
				return false;
		}
		return true;
	}

	private void createPath(String path) {
		File f = new File(path);
		if (f.exists())
			return;
		f.mkdirs();
	}

	public boolean zoomImage(String srcFile, String dstFile, Size size) {
		Size srcSize = imageDisposer.getImageSize(srcFile);
		Size dstSize = new Size();
		if (srcSize.getWidth() > srcSize.getHeight()) {
			if (srcSize.getWidth() <= size.getWidth()) {
				dstSize.setWidth(srcSize.getWidth());
				dstSize.setHeight(srcSize.getHeight());
			} else {
				dstSize.setWidth(size.getWidth());
				dstSize.setHeight(srcSize.getHeight() * size.getWidth() / srcSize.getWidth());
			}
		} else {
			if (srcSize.getHeight() <= size.getHeight()) {
				dstSize.setWidth(srcSize.getWidth());
				dstSize.setHeight(srcSize.getHeight());
			} else {
				dstSize.setWidth(srcSize.getWidth() * size.getHeight() / srcSize.getHeight());
				dstSize.setHeight(size.getHeight());
			}
		}
		
		return imageDisposer.zoom(srcFile, dstFile, dstSize.getWidth(), dstSize.getHeight());
	}

	public static String getExtName(String file) {
		int index = file.lastIndexOf(".");
		if (index > -1)
			return file.substring(index, file.length());
		return "";
	}
}

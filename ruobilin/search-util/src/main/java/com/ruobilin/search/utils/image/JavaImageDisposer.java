package com.ruobilin.search.utils.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

public class JavaImageDisposer implements ImageDisposable {

	@Override
	public boolean cropAndZoom(String srcImageFile, int cropX, int cropY,
			int cropWidth, int cropHeight, String destImageFile, int destWidth,
			int destHeight) {
		try {
			ImageFilter cropFilter;
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image img = tk.getImage(srcImageFile);
			
			cropFilter = new CropImageFilter(cropX, cropY, cropWidth,
					cropHeight);
			img = tk.createImage(
					new FilteredImageSource(img.getSource(), cropFilter));

			img = img.getScaledInstance(destWidth, destHeight,
					Image.SCALE_DEFAULT);// 获取缩放后的图片大小
			
			BufferedImage tag = new BufferedImage(destWidth, destHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			
			ImageIO.write(tag, "JPEG", new File(destImageFile));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean crop(String srcImageFile, int cropX, int cropY,
			int cropWidth, int cropHeight, String destImageFile) {
		try {
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image img = tk.getImage(srcImageFile);
			
			ImageFilter cropFilter = new CropImageFilter(cropX, cropY, cropWidth,
					cropHeight);
			img = tk.createImage(new FilteredImageSource(img.getSource(), cropFilter));

			BufferedImage tag = new BufferedImage(cropWidth, cropHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			
			ImageIO.write(tag, "JPEG", new File(destImageFile));
		    
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean zoom(String srcImageFile, String destImageFile,
			int destWidth, int destHeight) {
		try {
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			BufferedImage newImage = new BufferedImage(destWidth, destHeight, bi.getType());
	        Graphics g = newImage.getGraphics();
	        g.drawImage(bi, 0, 0, destWidth, destHeight, null);
	        g.dispose();
	        ImageIO.write(newImage, "JPG", new File(destImageFile));
	        
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Size getImageSize(String file) {
		try {
			Size s = new Size();
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(file));
			s.setHeight(bi.getHeight());
			s.setWidth(bi.getWidth());

			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}

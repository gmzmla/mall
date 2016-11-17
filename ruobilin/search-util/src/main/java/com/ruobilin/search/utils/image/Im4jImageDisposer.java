package com.ruobilin.search.utils.image;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

public class Im4jImageDisposer implements ImageDisposable {
	private String imPath = null;
	private boolean useGm = true;
	
	public Im4jImageDisposer(String path, boolean useGm) {
		imPath = path;
		this.useGm = useGm;
	}

	@Override
	public boolean cropAndZoom(String srcImageFile, int cropX, int cropY,
			int cropWidth, int cropHeight, String destImageFile, int destWidth,
			int destHeight) {
		try {
			ConvertCmd cmd = new ConvertCmd(useGm);
			cmd.setSearchPath(imPath);

			// create the operation, add images and operators/options
			IMOperation op = new IMOperation();
			op.addImage(srcImageFile);
			op.crop(cropWidth, cropHeight, cropX, cropY).resize(destWidth, destHeight);
			op.addImage(destImageFile);

			// execute the operation
			cmd.run(op);
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
			ConvertCmd cmd = new ConvertCmd(useGm);
			cmd.setSearchPath(imPath);

			// create the operation, add images and operators/options
			IMOperation op = new IMOperation();
			op.addImage(srcImageFile);
			op.crop(cropWidth, cropHeight, cropX, cropY);
			op.addImage(destImageFile);

			// execute the operation
			cmd.run(op);
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
			ConvertCmd cmd = new ConvertCmd(useGm);
			cmd.setSearchPath(imPath);

			// create the operation, add images and operators/options
			IMOperation op = new IMOperation();
			op.addImage(srcImageFile);
			//op.resize(destWidth,destHeight);
			op.addRawArgs("-scale", destWidth + "x" + destHeight + "^");  
			op.addRawArgs("-gravity", "center");
			op.addRawArgs("-extent", destWidth + "x" + destHeight);
			op.addRawArgs("-define", "jpeg:preserve-settings=quality");
			op.addImage(destImageFile);

			// execute the operation
			cmd.run(op);
			
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

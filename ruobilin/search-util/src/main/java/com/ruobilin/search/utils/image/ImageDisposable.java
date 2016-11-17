package com.ruobilin.search.utils.image;

public interface ImageDisposable {
	/**
	 * 裁剪后缩放
	 * @param srcImageFile
	 * @param cropX
	 * @param cropY
	 * @param cropWidth
	 * @param cropHeight
	 * @param destImageFile
	 * @param destWidth
	 * @param destHeight
	 * @return
	 */
	public boolean cropAndZoom(String srcImageFile, int cropX, int cropY, int cropWidth,
            int cropHeight, String destImageFile, int destWidth,int destHeight);
	
	/**
	 * 裁剪
	 * @param srcImageFile
	 * @param cropX
	 * @param cropY
	 * @param cropWidth
	 * @param cropHeight
	 * @param destImageFile
	 * @return
	 */
	public boolean crop(String srcImageFile, int cropX, int cropY, int cropWidth,
            int cropHeight, String destImageFile);
	
	/**
	 * 缩放
	 * @param srcImageFile
	 * @param destImageFile
	 * @param destWidth
	 * @param destHeight
	 * @return
	 */
	public boolean zoom(String srcImageFile, String destImageFile, int destWidth,int destHeight);
	
	/**
	 * 得到图片尺寸
	 * @param file
	 * @return
	 */
	public Size getImageSize(String file);
}

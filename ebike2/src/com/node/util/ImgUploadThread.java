/**
  * 文件名：ImgUploadThread.java
  * 版本信息：Version 1.0
  * 日期：2016年6月9日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.node.model.PicPath;


/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月9日 下午3:54:24 
 */
public class ImgUploadThread implements Runnable{
	private MultipartFile file;
	private int limitWidth;
	private int limitHeight;
	private PicPath imgPath;
	private String newFilePath;
	
		
	/**
	  * 类的构造方法
	  * 创建一个新的实例 ImgUploadThread.
	  * @param 
	  * @param file
	  * @param limitWidth
	  * @param limitHeight
	  * @param imgPath
	  */
	public ImgUploadThread(MultipartFile file, int limitWidth, int limitHeight,
			PicPath imgPath,String newFilePath) {
		super();
		this.file = file;
		this.limitWidth = limitWidth;
		this.limitHeight = limitHeight;
		this.imgPath = imgPath;
		this.newFilePath = newFilePath;
	}


		/**
	 * @return imgPath : return the property imgPath.
	 */
	public PicPath getImgPath() {
		return imgPath;
	}


	/**
	 * @param imgPath : set the property imgPath.
	 */
	public void setImgPath(PicPath imgPath) {
		this.imgPath = imgPath;
	}


		/**
	 * @return file : return the property file.
	 */
	public MultipartFile getFile() {
		return file;
	}


	/**
	 * @param file : set the property file.
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}


	/**
	 * @return limitWidth : return the property limitWidth.
	 */
	public int getLimitWidth() {
		return limitWidth;
	}


	/**
	 * @param limitWidth : set the property limitWidth.
	 */
	public void setLimitWidth(int limitWidth) {
		this.limitWidth = limitWidth;
	}


	/**
	 * @return limitHeight : return the property limitHeight.
	 */
	public int getLimitHeight() {
		return limitHeight;
	}


	/**
	 * @param limitHeight : set the property limitHeight.
	 */
	public void setLimitHeight(int limitHeight) {
		this.limitHeight = limitHeight;
	}


	/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(10000);
			uploadImg();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	  * 方法描述： 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月9日 下午4:06:30
	  */
	private   void  uploadImg() {
		if (file != null && !file.isEmpty()) {
			String source = imgPath.getVcAddpath();// 图片保存路径
			SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
			source = source + "/" + format.format(new Date());
			if (!source.endsWith("/")) {
				source += "/";
			}
			String getImagePath = source;

			// 得到上传文件的后缀名
			String uploadName = file.getContentType();
			System.out.println("图片类型 ------------" + uploadName);

			String lastuploadName = uploadName.substring(
					uploadName.indexOf("/") + 1, uploadName.length());
			System.out.println("得到上传文件的后缀名 ------------" + lastuploadName);

			// 得到文件的新名字
			String fileNewName =newFilePath;
			System.out.println("// 得到文件的新名字 ------------" + fileNewName);

			// 最后返回图片路径
			String imagePath = source + "/" + fileNewName;
			System.out.println("        //最后返回图片路径   " + imagePath);
			File image = new File(getImagePath);
			if (!image.exists()) {
				image.mkdir();
			}
			BufferedImage srcBufferImage;
			try {
				srcBufferImage = ImageIO.read(file.getInputStream());
				BufferedImage scaledImage;
				ScaleImage scaleImage = new ScaleImage();
				int yw = srcBufferImage.getWidth();
				int yh = srcBufferImage.getHeight();
				int w = limitWidth, h = limitHeight;
				if (w > yw && h > yh) {
					File image2 = new File(getImagePath, fileNewName);
					file.transferTo(image2);
				} else {
					scaledImage = scaleImage.imageZoomOut(srcBufferImage, w, h);
					FileOutputStream out = new FileOutputStream(getImagePath + "/"
							+ fileNewName);
					ImageIO.write(scaledImage, "jpeg", out);
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
	}


	/**
	 * @return newFilePath : return the property newFilePath.
	 */
	public String getNewFilePath() {
		return newFilePath;
	}


	/**
	 * @param newFilePath : set the property newFilePath.
	 */
	public void setNewFilePath(String newFilePath) {
		this.newFilePath = newFilePath;
	}


	
}

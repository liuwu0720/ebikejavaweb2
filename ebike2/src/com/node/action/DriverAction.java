/**
 * 文件名：DriverAction.java
 * 版本信息：Version 1.0
 * 日期：2016年6月15日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.node.model.DdcDriver;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.PicPath;
import com.node.obj.ScoreResult;
import com.node.service.ICompanyService;
import com.node.service.IDriverSerivce;
import com.node.service.IEbikeService;
import com.node.util.AjaxUtil;
import com.node.util.HqlHelper;
import com.node.util.ImgUploadThread;
import com.node.util.Page;
import com.node.util.ScaleImage;
import com.node.util.ScoreQueryUtil;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;

/**
 * 类描述：司机信息维护
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月15日 下午5:59:17
 */
@Controller
@RequestMapping("/driverAction")
@ApiIgnore
public class DriverAction {

	@Autowired
	IDriverSerivce iDriverSerivce;
	@Autowired
	ICompanyService iCompanyService;
	@Autowired
	IEbikeService iEbikeService;

	@RequestMapping("/getAll")
	public String getAll() {
		return "driver/driverlistf";
	}

	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request,
			HttpServletResponse response, String sfzhm, String jsrxm,
			Integer userStatus) {

		Object object = request.getSession()
				.getAttribute(SystemConstants.SESSION_USER).getClass()
				.getSimpleName();
		Page p = ServiceUtil.getcurrPage(request);
		HqlHelper hql = new HqlHelper(DdcDriver.class);
		if (object.equals(SystemConstants.CLASS_NAME_DDC_HYXHSSDW)) {
			DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
					.getAttribute(SystemConstants.SESSION_USER);
			hql.addEqual("ssdwId", ddcHyxhSsdw.getId());
		}
		if (StringUtils.isNotBlank(sfzhm)) {
			hql.addEqual("sfzhm", sfzhm);
		}
		if (StringUtils.isNotBlank(jsrxm)) {
			hql.addLike("jsrxm", jsrxm);
		}
		if (userStatus != null) {
			if (userStatus == SystemConstants.INT_USER_STATUS_2) {
				hql.addGreatThan("userStatus",
						SystemConstants.INT_USER_STATUS_1);
			} else {
				hql.addEqual("userStatus", userStatus);
			}
		}

		hql.addOrderBy("id", "desc");
		hql.setQueryPage(p);
		Map<String, Object> resultMap = iDriverSerivce.queryByHql(hql);
		return resultMap;
	}

	/**
	 * 
	 * 方法描述：保存
	 * 
	 * @param request
	 * @param response
	 * @param ddcDriver
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年6月16日 下午6:58:40
	 */
	@RequestMapping("/saveOrUpdate")
	public void saveOrUpdate(
			HttpServletRequest request,
			HttpServletResponse response,
			DdcDriver ddcDriver,
			@RequestParam(value = "headimg_jsr1", required = false) MultipartFile headimg_jsr1,
			@RequestParam(value = "card1img_jsr1", required = false) MultipartFile card1img_jsr1,
			@RequestParam(value = "card2img_jsr1", required = false) MultipartFile card2img_jsr1,
			@RequestParam(value = "vcUser1WorkImgfile", required = false) MultipartFile vcUser1WorkImgfile) {
		/**
		 * 检查身份证
		 */
		String message = "success";

		DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		ddcDriver.setSsdwId(ddcHyxhSsdw.getId());
		ddcDriver.setHyxhzh(ddcHyxhSsdw.getHyxhzh());
		ddcDriver.setTranDate(new Date());
		/*
		 * int userStatus = iDriverSerivce.updateDriverStatus(ddcDriver);
		 * if(userStatus == 1){
		 * ddcDriver.setSynFlag(SystemConstants.SYSNFLAG_ADD); }
		 * ddcDriver.setUserStatus(userStatus);
		 */
		if (ddcDriver.getId() == null) {
			message = iDriverSerivce.findSameSfzhm(ddcDriver);
		} else {
			DdcDriver beforedriver = iDriverSerivce.getDriverById(ddcDriver
					.getId());
			if (StringUtils.isNotBlank(beforedriver.getSfzhm())
					&& !beforedriver.getSfzhm().equals(ddcDriver.getSfzhm())) {
				message = iDriverSerivce.findSameSfzhm(ddcDriver);
			}
		}

		if (!message.equals("success")) {
			AjaxUtil.rendJson(response, false, message);
			return;
		}

		PicPath imgPath = iCompanyService.getPathById(SystemConstants.PIC_IMG);

		try {
			String vcUser1_img = uploadImg(request, headimg_jsr1,
					SystemConstants.IMG_HEAD_WITH,
					SystemConstants.IMG_HEAD_HEIGHT, imgPath);// 上传驾驶人1照片
			if (StringUtils.isNotBlank(vcUser1_img)) {
				ddcDriver.setVcUserImg(vcUser1_img);
			} else {
				ddcDriver.setVcUserImg(ddcDriver.getVcUserImg());
			}
			String vcUser1CardImg1 = uploadImg(request, card1img_jsr1,
					SystemConstants.IMG_IDCARD_WIDTH,
					SystemConstants.IMG_IDCARD_HEIGHT, imgPath);// 上传驾驶人1身份证正面
			if (StringUtils.isNotBlank(vcUser1CardImg1)) {
				ddcDriver.setVcUserCardImg1(vcUser1CardImg1);
			} else {
				ddcDriver.setVcUserCardImg1(ddcDriver.getVcUserCardImg1());
			}
			String vcUser1CardImg2 = uploadImg(request, card2img_jsr1,
					SystemConstants.IMG_IDCARD_WIDTH,
					SystemConstants.IMG_IDCARD_HEIGHT, imgPath);// 上传驾驶人1身份证反面
			if (StringUtils.isNotBlank(vcUser1CardImg2)) {
				ddcDriver.setVcUserCardImg2(vcUser1CardImg2);
			} else {
				ddcDriver.setVcUserCardImg2(ddcDriver.getVcUserCardImg2());
			}
			String vcUser1WorkImg_path = uploadImg(request, vcUser1WorkImgfile,
					SystemConstants.IMG_INVOICE_WIDTH,
					SystemConstants.IMG_INVOICE_HEIGHT, imgPath);// 驾驶人1
																	// 在职证明或居住证
			if (StringUtils.isNotBlank(vcUser1WorkImg_path)) {
				ddcDriver.setVcUserWorkImg(vcUser1WorkImg_path);
			} else {
				ddcDriver.setVcUserWorkImg(ddcDriver.getVcUserWorkImg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "图片上传失败，请重试!");
		}
		try {

			if (ddcDriver.getId() == null) {
				ddcDriver.setUserStatus(0);
				ddcDriver.setJsrxm(ddcDriver.getJsrxm().trim());
				ddcDriver.setSfzhm(ddcDriver.getSfzhm().trim());
				iDriverSerivce.saveDdcDriver(ddcDriver);
				iEbikeService.saveDdcDriver(ddcDriver);
			} else {
				if (ddcDriver.getUserStatus() != null
						&& ddcDriver.getUserStatus() > 0) {
					DdcDriver oldDdcDriver = iDriverSerivce
							.getDriverById(ddcDriver.getId());
					if (ddcDriver.getJsrxm().equals(oldDdcDriver.getJsrxm())
							&& ddcDriver.getSfzhm().equals(
									oldDdcDriver.getSfzhm())) {
						ddcDriver.setUserStatus(ddcDriver.getUserStatus());
						ddcDriver.setSynFlag(SystemConstants.SYSNFLAG_ADD);
					} else {
						ddcDriver.setUserStatus(0);
					}

				} else {
					ddcDriver.setUserStatus(0);
				}
				ddcDriver.setJsrxm(ddcDriver.getJsrxm().trim());
				ddcDriver.setSfzhm(ddcDriver.getSfzhm().trim());
				iDriverSerivce.updateDdcDriver(ddcDriver);
				if (ddcDriver.getUserStatus() == 0) {
					iEbikeService.saveDdcDriver(ddcDriver);
				}

				iDriverSerivce.updateClsb(ddcDriver);
			}
			AjaxUtil.rendJson(response, true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "系统错误，操作失败");
		}

	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年6月16日 下午5:31:39
	 */
	@RequestMapping("/queryInfoById")
	public String queryInfoById(HttpServletRequest request, String id) {
		long driverId = Long.parseLong(id);
		DdcDriver ddcDriver = iDriverSerivce.getDriverById(driverId);
		String hyxhName = iDriverSerivce.getHyxhNameByHyxhzh(ddcDriver
				.getHyxhzh());
		String ssdwName = iDriverSerivce.getDwmcById(ddcDriver.getSsdwId());
		request.setAttribute("hyxhName", hyxhName);
		request.setAttribute("ssdwName", ssdwName);
		ddcDriver.setVcShowUserImg(parseUrl(ddcDriver.getVcUserImg()));
		ddcDriver
				.setVcUserCardImg1Show(parseUrl(ddcDriver.getVcUserCardImg1()));
		ddcDriver
				.setVcUserCardImg2Show(parseUrl(ddcDriver.getVcUserCardImg2()));
		ddcDriver.setVcUserWorkImgShow(parseUrl(ddcDriver.getVcUserWorkImg()));
		List<ScoreResult> scoreResultes = ScoreQueryUtil.scoreResults(
				ddcDriver.getSfzhm(), ddcDriver.getLxdh());
		if (CollectionUtils.isNotEmpty(scoreResultes)) {
			for (ScoreResult scoreResult : scoreResultes) {
				if (StringUtils.isNotBlank(scoreResult.getDtjg())
						&& scoreResult.getDtjg().equals("合格")) {
					if(ddcDriver.getUserStatus()!=0){
						ddcDriver.setUserStatus(SystemConstants.INT_USER_STATUS_3);
						ddcDriver.setUserNote("已通过星级考试");
						iEbikeService.updateDdcDriver(ddcDriver);
						break;
					}
					
				}
			}
		}

		request.setAttribute("scoreResultes", scoreResultes);
		request.setAttribute("ddcDriver", ddcDriver);
		return "driver/driverinfo";
	}

	/**
	 * 
	 * 方法描述：修改详情
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年6月16日 下午7:32:06
	 */
	@RequestMapping("/updateInfoById")
	public String updateInfoById(HttpServletRequest request, String id) {
		long driverId = Long.parseLong(id);
		DdcDriver ddcDriver = iDriverSerivce.getDriverById(driverId);
		String hyxhName = iDriverSerivce.getHyxhNameByHyxhzh(ddcDriver
				.getHyxhzh());
		String ssdwName = iDriverSerivce.getDwmcById(ddcDriver.getSsdwId());
		request.setAttribute("hyxhName", hyxhName);
		request.setAttribute("ssdwName", ssdwName);
		ddcDriver.setVcShowUserImg(parseUrl(ddcDriver.getVcUserImg()));
		ddcDriver
				.setVcUserCardImg1Show(parseUrl(ddcDriver.getVcUserCardImg1()));
		ddcDriver
				.setVcUserCardImg2Show(parseUrl(ddcDriver.getVcUserCardImg2()));
		ddcDriver.setVcUserWorkImgShow(parseUrl(ddcDriver.getVcUserWorkImg()));
		request.setAttribute("ddcDriver", ddcDriver);
		return "driver/driverupdate";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年6月18日 下午2:15:33
	 */
	@RequestMapping("/deleteRow")
	public void deleteRow(String id, HttpServletRequest request,
			HttpServletResponse response) {
		long driverId = Long.parseLong(id);
		DdcDriver ddcDriver = iDriverSerivce.getDriverById(driverId);
		Object object = request.getSession()
				.getAttribute(SystemConstants.SESSION_USER).getClass()
				.getSimpleName();
		if (object.equals(SystemConstants.CLASS_NAME_DDC_HYXHBASE)) {
			DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
					.getAttribute(SystemConstants.SESSION_USER);
			if (ddcHyxhBase.getHyxhzh().equals("cs")) {
				ddcDriver.setUserStatus(1);
				ddcDriver.setUserNote(null);
				ddcDriver.setSynFlag(SystemConstants.SYSNFLAG_ADD);
				iDriverSerivce.updateDdcDriver(ddcDriver);
				AjaxUtil.rendJson(response, true, "删除成功");
			}

		} else {
			ddcDriver.setUserStatus(-1);

			String message = iDriverSerivce.findIfdelete(ddcDriver);// 查询该司机下面是否有档案表
			message = iDriverSerivce.updateClsbDeleteByDriver(ddcDriver);// 该司机下面的车辆申报也将删除
			if (!message.equals("success")) {
				AjaxUtil.rendJson(response, false, message);
				return;
			} else {
				try {
					iDriverSerivce.saveDriverLog(ddcDriver);
					iDriverSerivce.deleteById(driverId);

					AjaxUtil.rendJson(response, true, "删除成功");
				} catch (Exception e) {
					e.printStackTrace();
					AjaxUtil.rendJson(response, false, "删除失败");
				}
			}
		}

	}

	private String parseUrl(String vcPicPath) {
		if (StringUtils.isNotBlank(vcPicPath)) {
			PicPath picPath = iCompanyService
					.getPathById(SystemConstants.PIC_IMG);
			String subPath = picPath.getVcParsePath();
			if (!subPath.endsWith("/")) {
				subPath += "/";
			}
			return subPath + vcPicPath;
		} else {
			return null;
		}

	}

	private String uploadImg(HttpServletRequest request,
			MultipartFile headimg_jsr1, int imgHeadWith, int imgHeadHeight,
			PicPath imgPath) {
		if (!headimg_jsr1.isEmpty()) {
			SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
			String ebike_jpgPath = generateFileName(headimg_jsr1
					.getOriginalFilename());
			ImgUploadThread iThread = new ImgUploadThread(headimg_jsr1,
					imgHeadWith, imgHeadHeight, imgPath, ebike_jpgPath);
			iThread.run();
			return format.format(new Date()) + "/" + ebike_jpgPath;
		} else {
			return null;
		}

	}

	private String uploadImg2(HttpServletRequest request, MultipartFile file,
			int imgHeadWith, int imgHeadHeight, PicPath imgPath)
			throws FileNotFoundException, IOException {
		if (!file.isEmpty()) {
			String source = imgPath.getVcAddpath();// 图片保存路径
			SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
			source = source + "/" + format.format(new Date());
			if (!source.endsWith("/")) {
				source += "/";
			}
			if (StringUtils.isBlank(source)) {
				System.out.println("source路径查不到！");
				return null;
			}
			String getImagePath = source;

			// 得到上传文件的后缀名
			String uploadName = file.getContentType();
			System.out.println("图片类型 ------------" + uploadName);

			String lastuploadName = uploadName.substring(
					uploadName.indexOf("/") + 1, uploadName.length());
			System.out.println("得到上传文件的后缀名 ------------" + lastuploadName);

			// 得到文件的新名字
			String fileNewName = generateFileName(file.getOriginalFilename());
			System.out.println("// 得到文件的新名字 ------------" + fileNewName);

			// 最后返回图片路径
			String imagePath = source + "/" + fileNewName;
			System.out.println("        //最后返回图片路径   " + imagePath);
			File image = new File(getImagePath);
			if (!image.exists()) {
				image.mkdir();
			}
			// file.transferTo(pathFile);
			BufferedImage srcBufferImage = ImageIO.read(file.getInputStream());
			BufferedImage scaledImage;
			ScaleImage scaleImage = new ScaleImage();
			int yw = srcBufferImage.getWidth();
			int yh = srcBufferImage.getHeight();
			int w = imgHeadWith, h = imgHeadHeight;
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
			return format.format(new Date()) + "/" + fileNewName;// 将文件夹名和文件名返回
		} else {
			return null;
		}
	}

	/**
	 * 方法描述：
	 * 
	 * @param originalFilename
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年6月16日 下午7:06:11
	 */
	private String generateFileName(String fileName) {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String formatDate = format.format(new Date());
		int random = new Random().nextInt(10000);
		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);
		return formatDate + random + extension;
	}
}

/**
 * 文件名：DriverAction.java
 * 版本信息：Version 1.0
 * 日期：2016年6月15日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.node.model.DdcDriver;
import com.node.model.DdcHyxhSsdw;
import com.node.model.PicPath;
import com.node.service.ICompanyService;
import com.node.service.IDriverSerivce;
import com.node.service.IEbikeService;
import com.node.util.AjaxUtil;
import com.node.util.HqlHelper;
import com.node.util.ImgUploadThread;
import com.node.util.Page;
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
		DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		Page p = ServiceUtil.getcurrPage(request);

		HqlHelper hql = new HqlHelper(DdcDriver.class);
		hql.addEqual("ssdwId", ddcHyxhSsdw.getId());
		if (StringUtils.isNotBlank(sfzhm)) {
			hql.addEqual("sfzhm", sfzhm);
		}
		if (StringUtils.isNotBlank(jsrxm)) {
			hql.addEqual("jsrxm", jsrxm);
		}
		if (userStatus != null) {
			hql.addEqual("userStatus", userStatus);
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
		if (ddcDriver.getId() == null) {
			message = iDriverSerivce.findSameSfzhm(ddcDriver.getSfzhm());
		} else {
			DdcDriver beforedriver = iDriverSerivce.getDriverById(ddcDriver
					.getId());
			if (!beforedriver.getSfzhm().equals(ddcDriver.getSfzhm())) {
				message = iDriverSerivce.findSameSfzhm(ddcDriver.getSfzhm());
			}
		}

		if (!message.equals("success")) {
			AjaxUtil.rendJson(response, false, message);
			return;
		}
		ddcDriver.setUserStatus(0);
		DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		ddcDriver.setSsdwId(ddcHyxhSsdw.getId());
		ddcDriver.setHyxhzh(ddcHyxhSsdw.getHyxhzh());
		PicPath imgPath = iCompanyService.getPathById(SystemConstants.PIC_IMG);

		String vcUser1_img = uploadImg(request, headimg_jsr1,
				SystemConstants.IMG_HEAD_WITH, SystemConstants.IMG_HEAD_HEIGHT,
				imgPath);// 上传驾驶人1照片
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
				SystemConstants.IMG_INVOICE_HEIGHT, imgPath);// 驾驶人1 在职证明或居住证
		if (StringUtils.isNotBlank(vcUser1WorkImg_path)) {
			ddcDriver.setVcUserWorkImg(vcUser1WorkImg_path);
		} else {
			ddcDriver.setVcUserWorkImg(ddcDriver.getVcUserWorkImg());
		}
		try {
			if (ddcDriver.getId() == null) {
				iDriverSerivce.saveDdcDriver(ddcDriver);
				iEbikeService.saveDdcDriver(ddcDriver);
			} else {
				iDriverSerivce.updateDdcDriver(ddcDriver);
				iEbikeService.saveDdcDriver(ddcDriver);
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

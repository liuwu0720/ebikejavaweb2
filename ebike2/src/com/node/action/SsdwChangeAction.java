/**
 * 文件名：SsdwChangeAction.java
 * 版本信息：Version 1.0
 * 日期：2016年4月12日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.node.model.DdcDaxxb;
import com.node.model.DdcFlow;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcSjzd;
import com.node.model.PicPath;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
import com.node.service.IEbikeService;
import com.node.util.AjaxUtil;
import com.node.util.ImgUploadThread;
import com.node.util.Page;
import com.node.util.ScaleImage;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;

/**
 * 类描述：所属单位变更申报
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年4月12日 下午1:19:05
 */
@Controller
@RequestMapping("/ssdwChangeAction")
@ApiIgnore
public class SsdwChangeAction {

	@Autowired
	IEbikeService iEbikeService;

	@Autowired
	IApplyService iApplyService;

	@Autowired
	ICompanyService iCompanyService;

	@RequestMapping("/getAllBg")
	public String getAll(HttpServletRequest request) {
		List<DdcSjzd> ywyys = iApplyService.getSjzdByDmlb("YWYY_D");// 业务原因
		List<DdcSjzd> slzls = iApplyService.getSjzdByDmlb("ZXSQZL");// 注销资料
		request.setAttribute("slzls", slzls);
		request.setAttribute("ywyys", ywyys);
		return "company/bgApply";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @param djh
	 * @param cphm
	 * @param jsrxm1
	 * @param dabh
	 * @param ssdw
	 * @param response
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月12日 下午1:29:04
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request, String djh,
			String cphm, String jsrxm1, String dabh, String ywlx, String slyj,
			String xsqy, HttpServletResponse response) {
		DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		Page p = ServiceUtil.getcurrPage(request);

		String sql = "select A.ID,A.DABH,A.CPHM,A.DJH,A.JSRXM1,A.GDYJ,A.SFZMHM1,A.YWLX, (SELECT S.DWMC FROM DDC_HYXH_SSDW S WHERE S.ID=A.SSDWID and rownum = 1) AS DWMC,"
				+ " (select d.DMMS1 from ddc_sjzd d where d.dmz = a.cysy and d.dmlb='CSYS' and rownum=1)as csysname,"
				+ "(select d.DMMS1 from ddc_sjzd d where d.dmz = a.YWLX and d.dmlb='YWLX' and rownum=1)as YWLXNAME,"
				+ "(select d.DMMS1 from ddc_sjzd d where d.dmz=a.xsqy and d.dmlb='SSQY' and rownum = 1) as xsqy ,a.SYRQ,a.slyj "
				+ " from DDC_DAXXB A WHERE A.SSDWID='"
				+ ddcHyxhSsdw.getId()
				+ "'  ";
		// 电机号
		if (StringUtils.isNotBlank(djh)) {
			sql += " and a.djh like '%" + djh + "%'";
		}
		// 档案编号
		if (StringUtils.isNotBlank(dabh)) {
			sql += " and a.dabh like '%" + dabh + "%'";
		}
		// 车牌号
		if (StringUtils.isNotBlank(cphm)) {
			sql += " and a.cphm like '%" + cphm + "%'";
		}
		// 驾驶人
		if (StringUtils.isNotBlank(jsrxm1)) {
			sql += " and a.JSRXM1 like '%" + jsrxm1 + "%'";
		}
		if (StringUtils.isNotBlank(ywlx)) {
			sql += " and a.YWLX = '" + ywlx + "'";
		}
		if (StringUtils.isNotBlank(slyj)) {
			sql += " and a.SLYJ = '" + slyj + "'";
		}
		if (StringUtils.isNotBlank(xsqy)) {
			sql += " and a.XSQY = '" + xsqy + "'";
		}

		sql += "  order by A.ID DESC";

		Map<String, Object> resultMap = iEbikeService.queryBySpringSql(sql, p);
		return resultMap;

	}

	/**
	 * 
	 * 方法描述：变更
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月12日 下午4:06:32
	 */
	@RequestMapping("/changeInfo")
	public String changeInfo(HttpServletRequest request, String id) {
		long sbId = Long.parseLong(id);
		DdcDaxxb ddcDaxxb = iEbikeService.getById(sbId);
		String cysyName = iApplyService.findByProPerties("CSYS",
				ddcDaxxb.getCysy());

		ddcDaxxb.setCysyName(cysyName);// 车身颜色
		String xsqyName = iApplyService.findByProPerties("SSQY",
				ddcDaxxb.getXsqy());
		ddcDaxxb.setXsqyName(xsqyName);// 所属区域

		String ztName = iApplyService
				.findByProPerties("CLZT", ddcDaxxb.getZt());
		ddcDaxxb.setZtName(ztName);
		// 申报单位
		if (StringUtils.isNotBlank(ddcDaxxb.getSsdwId())) {
			DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(Long
					.parseLong(ddcDaxxb.getSsdwId()));
			if (ddcHyxhSsdw != null) {
				ddcDaxxb.setSsdwName(ddcHyxhSsdw.getDwmc());
			} else {
				ddcDaxxb.setSsdwName(null);
			}
		}
		// 业务类型
		String ywlxName = iApplyService.findByProPerties("YWLX",
				ddcDaxxb.getYwlx());
		ddcDaxxb.setYwlxName(ywlxName);
		// 受理资料
		List<DdcSjzd> selectSlzls = iApplyService.getDbyyList(
				ddcDaxxb.getSlzl(), "BASQZL");

		String showEbikeImg = parseUrl(ddcDaxxb.getVcEbikeImg());
		String showUser1Img = parseUrl(ddcDaxxb.getVcUser1Img());
		String showUser2Img = parseUrl(ddcDaxxb.getVcUser2Img());
		String vcUser1CardImg1Show = parseUrl(ddcDaxxb.getVcUser1CardImg1());
		String vcUser1CardImg2Show = parseUrl(ddcDaxxb.getVcUser1CardImg2());
		String vcUser2CardImg1Show = parseUrl(ddcDaxxb.getVcUser2CardImg1());
		String vcUser2CardImg2Show = parseUrl(ddcDaxxb.getVcUser2CardImg2());
		String vcEbikeInvoiceImgShow = parseUrl(ddcDaxxb.getVcEbikeInvoiceImg());
		ddcDaxxb.setVcShowEbikeImg(showEbikeImg);
		ddcDaxxb.setVcShowUser1Img(showUser1Img);
		ddcDaxxb.setVcShowUser2Img(showUser2Img);
		ddcDaxxb.setVcUser1CardImg1Show(vcUser1CardImg1Show);
		ddcDaxxb.setVcUser1CardImg2Show(vcUser1CardImg2Show);
		ddcDaxxb.setVcUser2CardImg1Show(vcUser2CardImg1Show);
		ddcDaxxb.setVcUser2CardImg2Show(vcUser2CardImg2Show);
		ddcDaxxb.setVcEbikeInvoiceImgShow(vcEbikeInvoiceImgShow);

		String vcUser1WorkImgShow = parseUrl(ddcDaxxb.getVcUser1WorkImg());
		String vcUser2WorkImgShow = parseUrl(ddcDaxxb.getVcUser2WorkImg());
		String vcQualifiedImgShow = parseUrl(ddcDaxxb.getVcQualifiedImg());
		String vcEbikeInsuranceImgShow = parseUrl(ddcDaxxb
				.getVcEbikeInsuranceImg());

		ddcDaxxb.setVcUser1WorkImgShow(vcUser1WorkImgShow);
		ddcDaxxb.setVcUser2WorkImgShow(vcUser2WorkImgShow);
		ddcDaxxb.setVcQualifiedImgShow(vcQualifiedImgShow);
		ddcDaxxb.setVcEbikeInsuranceImgShow(vcEbikeInsuranceImgShow);

		List<DdcSjzd> colorsSjzds = iApplyService.getSjzdByDmlb("CSYS");// 车身颜色
		List<DdcSjzd> bgDataSjzds = iApplyService.getSjzdByDmlb("BGSQZL");// 变更申请资料
		List<DdcSjzd> ssqySjzds = iApplyService.getSjzdByDmlb("SSQY");// 所属区域
		request.setAttribute("colorsSjzds", colorsSjzds);
		request.setAttribute("bgDataSjzds", bgDataSjzds);
		request.setAttribute("ssqySjzds", ssqySjzds);
		request.setAttribute("ddcDaxxb", ddcDaxxb);
		request.setAttribute("selectSlzls", selectSlzls);
		return "company/bgDetail";
	}

	/**
	 * 
	 * 方法描述：变更
	 * 
	 * @param daxxb
	 * @param request
	 * @param fileupload
	 * @param file_upload1
	 * @param file_upload2
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月12日 下午4:45:49
	 */
	@RequestMapping("/changeData")
	public void changeData(
			DdcDaxxb daxxb,
			String note,
			HttpServletRequest request,
			@RequestParam(value = "headimg_jsr1", required = false) MultipartFile headimg_jsr1,
			@RequestParam(value = "headimg_jsr2", required = false) MultipartFile headimg_jsr2,
			@RequestParam(value = "ebike_img", required = false) MultipartFile ebike_img,
			@RequestParam(value = "card1img_jsr1", required = false) MultipartFile card1img_jsr1,
			@RequestParam(value = "card2img_jsr1", required = false) MultipartFile card2img_jsr1,
			@RequestParam(value = "card1img_jsr2", required = false) MultipartFile card1img_jsr2,
			@RequestParam(value = "card2img_jsr2", required = false) MultipartFile card2img_jsr2,
			@RequestParam(value = "ebike_invoice_img", required = false) MultipartFile ebike_invoice_img,
			@RequestParam(value = "vcUser1WorkImg_file", required = false) MultipartFile vcUser1WorkImg_file,
			@RequestParam(value = "vcUser2WorkImg_file", required = false) MultipartFile vcUser2WorkImg_file,
			HttpServletResponse response) throws FileNotFoundException,
			IOException {

		/**
		 * 检查黑名单
		 */
		String message = iApplyService.findHmd(daxxb.getJsrxm1(),
				daxxb.getJsrxm2());
		if (!message.equals("success")) {
			AjaxUtil.rendJson(response, false, message);
			return;
		}

		String slzls = request.getParameter("slzllist");// 重组变更资料字符串
		long daId = daxxb.getId();
		DdcDaxxb newDaxxb = iEbikeService.getById(daId);
		// newDaxxb.setSlzl(slzls);
		newDaxxb.setJsrxm1(daxxb.getJsrxm1());
		newDaxxb.setJsrxm2(daxxb.getJsrxm2());
		newDaxxb.setXb1(daxxb.getXb1());
		newDaxxb.setXb2(daxxb.getXb2());
		newDaxxb.setLxdh1(daxxb.getLxdh1());
		newDaxxb.setLxdh2(daxxb.getLxdh2());
		newDaxxb.setSfzmhm1(daxxb.getSfzmhm1());
		newDaxxb.setSfzmhm2(daxxb.getSfzmhm2());
		newDaxxb.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
		newDaxxb.setTranDate(new Date());
		PicPath imgPath = iCompanyService.getPathById(SystemConstants.PIC_IMG);
		String ebike_jpgPath = uploadImgThread(request, ebike_img,
				SystemConstants.IMG_EBIKE_WITH,
				SystemConstants.IMG_EBIKE_HEIGHT,imgPath);// 上传车身照片
		if (StringUtils.isNotBlank(ebike_jpgPath)) {
			newDaxxb.setVcEbikeImg(ebike_jpgPath);
		} else {
			newDaxxb.setVcEbikeImg(daxxb.getVcEbikeImg());
		}

		String vcUser1_img = uploadImgThread(request, headimg_jsr1,
				SystemConstants.IMG_HEAD_WITH, SystemConstants.IMG_HEAD_HEIGHT,imgPath);// 上传驾驶人1照片
		if (StringUtils.isNotBlank(vcUser1_img)) {
			newDaxxb.setVcUser1Img(vcUser1_img);
		} else {
			newDaxxb.setVcUser1Img(daxxb.getVcUser1Img());
		}

		String vcUser2_img = uploadImgThread(request, headimg_jsr2,
				SystemConstants.IMG_HEAD_WITH, SystemConstants.IMG_HEAD_HEIGHT,imgPath);// 上传驾驶人2照片
		if (StringUtils.isNotBlank(vcUser2_img)) {
			newDaxxb.setVcUser2Img(vcUser2_img);
		} else {
			newDaxxb.setVcUser2Img(daxxb.getVcUser2Img());
		}
		String vcUser1CardImg1 = uploadImgThread(request, card1img_jsr1,
				SystemConstants.IMG_IDCARD_WIDTH,
				SystemConstants.IMG_IDCARD_HEIGHT,imgPath);// 上传驾驶人1身份证正面
		if (StringUtils.isNotBlank(vcUser1CardImg1)) {
			newDaxxb.setVcUser1CardImg1(vcUser1CardImg1);
		} else {
			newDaxxb.setVcUser1CardImg1(newDaxxb.getVcUser1CardImg1());
		}
		String vcUser1CardImg2 = uploadImgThread(request, card2img_jsr1,
				SystemConstants.IMG_IDCARD_WIDTH,
				SystemConstants.IMG_IDCARD_HEIGHT,imgPath);// 上传驾驶人1身份证反面
		if (StringUtils.isNotBlank(vcUser1CardImg2)) {
			newDaxxb.setVcUser1CardImg2(vcUser1CardImg2);
		} else {
			newDaxxb.setVcUser1CardImg2(newDaxxb.getVcUser1CardImg2());
		}
		String vcUser2CardImg1 = uploadImgThread(request, card1img_jsr2,
				SystemConstants.IMG_IDCARD_WIDTH,
				SystemConstants.IMG_IDCARD_HEIGHT,imgPath);// 驾驶人2身份证正面
		if (StringUtils.isNotBlank(vcUser2CardImg1)) {
			newDaxxb.setVcUser2CardImg1(vcUser2CardImg1);
		} else {
			newDaxxb.setVcUser2CardImg1(newDaxxb.getVcUser2CardImg1());
		}
		String vcUser2CardImg2 = uploadImgThread(request, card2img_jsr2,
				SystemConstants.IMG_IDCARD_WIDTH,
				SystemConstants.IMG_IDCARD_HEIGHT,imgPath);// 驾驶人2身份证反面
		if (StringUtils.isNotBlank(vcUser2CardImg2)) {
			newDaxxb.setVcUser2CardImg2(vcUser2CardImg2);
		} else {
			newDaxxb.setVcUser2CardImg2(newDaxxb.getVcUser2CardImg2());
		}
		String vcUser1WorkImg = uploadImgThread(request, vcUser1WorkImg_file,
				SystemConstants.IMG_INVOICE_WIDTH,
				SystemConstants.IMG_INVOICE_HEIGHT,imgPath);
		if(StringUtils.isNotBlank(vcUser1WorkImg)){
			newDaxxb.setVcUser1WorkImg(vcUser1WorkImg);
		}else {
			newDaxxb.setVcUser1WorkImg(newDaxxb.getVcUser1WorkImg());
		}

		String vcUser2WorkImg = uploadImgThread(request, vcUser2WorkImg_file,
				SystemConstants.IMG_INVOICE_WIDTH,
				SystemConstants.IMG_INVOICE_HEIGHT,imgPath);
		if(StringUtils.isNotBlank(vcUser2WorkImg)){
			newDaxxb.setVcUser2WorkImg(vcUser2WorkImg);
		}else {
			newDaxxb.setVcUser2WorkImg(newDaxxb.getVcUser2WorkImg());
		}
		try {
			String type = "B";// 变更
			newDaxxb.setYwlx(type);
			newDaxxb.setSlyj(null);// 审批中
			// newDaxxb.setGdyj(null);
			// newDaxxb.setSlrq(null);
			// newDaxxb.setGdrq(null);
			saveDdcFlow(type, newDaxxb, slzls, null, note);

			// 保存日志
			// saveDaxxblog(newDaxxb, request);

			iEbikeService.update(newDaxxb);
			AjaxUtil.rendJson(response, true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "操作失败！系统错误");
		}

	}

	
	/**
	  * 方法描述：
	  * @param request
	  * @param vcUser2WorkImg_file
	  * @param imgInvoiceWidth
	  * @param imgInvoiceHeight
	  * @param imgPath
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年6月9日 下午5:11:15
	  */
	private String uploadImgThread(HttpServletRequest request,
			MultipartFile file, int with,
			int height, PicPath imgPath) {
		if(!file.isEmpty()){
			SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
			String ebike_jpgPath = generateFileName(file
					.getOriginalFilename());
			ImgUploadThread iThread = new ImgUploadThread(file,
					with, height, imgPath,
					ebike_jpgPath);
			iThread.run();
			return format.format(new Date()) + "/" + ebike_jpgPath;
		}else {
			return null;
		}
		
	}

	/**
	 * 
	 * 方法描述：转移
	 * 
	 * @param daxxb
	 * @param request
	 * @param fileupload
	 * @param file_upload1
	 * @param file_upload2
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月18日 上午11:17:59
	 */
	@RequestMapping("/zhuanyi")
	public void zhuanyi(DdcDaxxb daxxb, HttpServletRequest request,
			String note, String newXsqy, HttpServletResponse response)
			throws FileNotFoundException, IOException {

		String slzls = request.getParameter("slzllist");// 重组变更资料字符串

		long daId = daxxb.getId();
		DdcDaxxb newDaxxb = iEbikeService.getById(daId);
		newDaxxb.setXsqy(newXsqy);
		newDaxxb.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
		newDaxxb.setTranDate(new Date());
		newDaxxb.setTranFlag(null);
		newDaxxb.setTranDate(null);

		try {
			String type = "C";// 转移
			newDaxxb.setYwlx(type);
			newDaxxb.setSlyj(null);// 审批中
			saveDdcFlow(type, newDaxxb, slzls, null, note);

			// 保存日志
			// saveDaxxblog(newDaxxb, request);

			iEbikeService.update(newDaxxb);
			AjaxUtil.rendJson(response, true, "操作成功");
		} catch (Exception e) {
			AjaxUtil.rendJson(response, false, "操作失败！系统错误");
		}

	}

	/**
	 * 
	 * 方法描述：注销
	 * 
	 * @param request
	 * @param response
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月18日 下午2:33:19
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping("/zhuxiao")
	public void zhuxiao(HttpServletRequest request,
			HttpServletResponse response, String id, String slbz)
			throws IllegalAccessException, InvocationTargetException {
		long daId = Long.parseLong(id);
		String[] ywyys = request.getParameterValues("ywyys");
		String[] slzllist = request.getParameterValues("slzls");

		String newYwyy = "";// 重组业务原因字符串
		if (ywyys.length != 0) {
			for (String tbyy : ywyys) {
				newYwyy = newYwyy + tbyy + ",";
			}
			newYwyy = newYwyy.substring(0, newYwyy.length() - 1);
		}

		String newSlzl = "";// 重组受理资料字符串
		if (slzllist.length != 0) {
			for (String slzl : slzllist) {
				newSlzl = newSlzl + slzl + ",";
			}
			newSlzl = newSlzl.substring(0, newSlzl.length() - 1);
		}
		DdcDaxxb daxxb = iEbikeService.getById(daId);

		daxxb.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
		daxxb.setTranDate(new Date());

		try {

			String type = "D";// 注销
			daxxb.setYwlx(type);
			daxxb.setSlyj(null);// 审批中
			// daxxb.setGdyj(null);
			saveDdcFlow(type, daxxb, newSlzl, newYwyy, slbz);
			// saveDaxxblog(daxxb, request);
			iEbikeService.update(daxxb);
			AjaxUtil.rendJson(response, true, "操作成功！");
		} catch (Exception e) {
			AjaxUtil.rendJson(response, false, "操作失败！系统错误");
		}

	}

	/**
	 * 方法描述：
	 * 
	 * @param newDaxxb
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 下午9:53:50
	 * @param slzls
	 * @param newYwyy
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private void saveDdcFlow(String ywlxType, DdcDaxxb newDaxxb, String slzls,
			String newYwyy, String note) throws IllegalAccessException,
			InvocationTargetException {
		// TODO Auto-generated method stub
		DdcFlow ddcFlow = new DdcFlow();
		BeanUtils.copyProperties(ddcFlow, newDaxxb);
		// 生成流水号
		String sql = "select SEQ_DDL_FLOW.nextval from dual";
		Object object = iApplyService.getDateBySQL(sql);
		String seq = object.toString();
		String md = new SimpleDateFormat("yyMMdd").format(new Date());
		String lsh = ywlxType + md + seq + "";// 生成流水表流水号
		ddcFlow.setLsh(lsh);
		ddcFlow.setYwlx(ywlxType);// A-备案 B-变更 C-转移 D-注销 E-检查
		ddcFlow.setSlyj(null);
		ddcFlow.setSlrq(new Date());// 申请日期
		ddcFlow.setId(null);
		ddcFlow.setYwyy(newYwyy);
		ddcFlow.setSlzl(slzls);
		ddcFlow.setBz(note);
		ddcFlow.setVcTableName(SystemConstants.DAXXB_TABLE);
		ddcFlow.setiTableId(newDaxxb.getId());
		ddcFlow.setGdrq(null);
		ddcFlow.setSlIndex(0);
		iEbikeService.saveDdcFlow(ddcFlow);
	}

	private String uploadImg(HttpServletRequest request, MultipartFile file,
			int limitWidth, int limitHeight) throws FileNotFoundException,
			IOException {
		if (file != null && !file.isEmpty()) {
			PicPath imgPath = iCompanyService
					.getPathById(SystemConstants.PIC_IMG);
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

			// 得到文件的新名字
			String fileNewName = generateFileName(file.getOriginalFilename());

			File image = new File(getImagePath);
			if (!image.exists()) {
				image.mkdir();
			}
			// file.transferTo(pathFile);
			BufferedImage srcBufferImage = ImageIO.read(file.getInputStream());
			BufferedImage scaledImage;
			ScaleImage scaleImage = ScaleImage.getInstance();
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
			return format.format(new Date()) + "/" + fileNewName;// 将文件夹名和文件名返回
		} else {
			return null;
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

	/**
	 * 方法描述：
	 * 
	 * @param originalFilename
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月1日 下午3:31:37
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

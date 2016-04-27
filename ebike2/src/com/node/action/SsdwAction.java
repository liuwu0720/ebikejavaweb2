/**
 * 文件名：SsdwAction.java
 * 版本信息：Version 1.0
 * 日期：2016年4月9日
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
import com.node.model.DdcApproveUser;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.model.DdcHyxhSsdwclsbLog;
import com.node.model.DdcSjzd;
import com.node.model.PicPath;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
import com.node.service.IEbikeService;
import com.node.util.AjaxUtil;
import com.node.util.Page;
import com.node.util.ScaleImage;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;

/**
 * 类描述：所属单位
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年4月9日 下午3:48:51
 */
@Controller
@RequestMapping("/ssdwAction")
@ApiIgnore
public class SsdwAction {

	@Autowired
	ICompanyService iCompanyService;

	@Autowired
	IApplyService iApplyService;

	@Autowired
	IEbikeService iEbikeService;

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月9日 下午3:59:31
	 */
	@RequestMapping("/getAllBa")
	public String getAllBa(HttpServletRequest request) {
		DdcHyxhSsdw dw = (DdcHyxhSsdw) request.getSession().getAttribute(
				SystemConstants.SESSION_USER);
		DdcHyxhSsdw ddcHyxhSsdw = iApplyService.getDdcHyxhSsdwById(String
				.valueOf(dw.getId()));
		request.setAttribute("ddcHyxhSsdw", ddcHyxhSsdw);
		return "company/baApply";
	}

	/**
	 * 
	 * 方法描述：查询车辆备案申请的列表
	 * 
	 * @param request
	 * @param zt
	 * @param response
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月9日 下午7:16:31
	 */
	@RequestMapping("/queryBaList")
	@ResponseBody
	public Map<String, Object> queryBaList(HttpServletRequest request,
			String djh, String dtStart, String dtend, String xsqy, String zt,
			String jsrxm1, HttpServletResponse response) {
		Page page = ServiceUtil.getcurrPage(request);
		DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		String sql = "select s.id,s.lsh,s.ppxh,(select d.DMMS1 from ddc_sjzd d where d.dmz = s.cysy and d.dmlb='CSYS' and rownum=1)as csysname,"
				+ "s.JSRXM1,s.djh,(select  d.DMMS1 from ddc_sjzd d where d.dmz = s.xsqy and d.dmlb='SSQY' and rownum=1 )as xsqyname ,s.sqrq,s.sl_index,s.slyj "
				+ "from DDC_HYXH_SSDWCLSB s where 1=1 and s.ENABLE = 1";
		sql += " and s.SSDWID=" + ddcHyxhSsdw.getId();
		if (StringUtils.isNotBlank(zt)) {
			if (zt.equals("0") || zt.equals("1")) {
				sql += " and s.slyj =" + Integer.parseInt(zt);
			} else {
				sql += " and s.SLYJ is null ";
			}

		}
		if (StringUtils.isNotBlank(dtStart)) {
			sql += " and s.SQRQ > to_date('" + dtStart + "','yyyy-MM-dd')";
		}
		if (StringUtils.isNotBlank(dtend)) {
			sql += " and s.SQRQ < to_date('" + dtend + "','yyyy-MM-dd')";
		}
		if (StringUtils.isNotBlank(djh)) {
			sql += " and s.djh like '" + djh + "'";
		}
		if (StringUtils.isNotBlank(xsqy)) {
			sql += " and s.XSQY = '" + xsqy + "'";
		}
		if (StringUtils.isNotBlank(jsrxm1)) {
			sql += " and s.jsrxm1 like '%" + jsrxm1 + "%'";
		}

		sql += " order by s.id desc";

		Map<String, Object> map = iCompanyService.getBySpringSql(sql, page);
		return map;
	}

	/**
	 * 
	 * 方法描述：保存车辆备案申报
	 * 
	 * @param ddcHyxhSsdwclsb
	 * @param fileupload
	 * @param file_upload1
	 * @param file_upload2
	 * @param request
	 * @param response
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月9日 下午7:34:31
	 */
	@RequestMapping("/saveOrUpdateBaClSb")
	public void saveOrUpdateBaClSb(
			DdcHyxhSsdwclsb ddcHyxhSsdwclsb,
			@RequestParam(value = "headimg_jsr1", required = false) MultipartFile headimg_jsr1,
			@RequestParam(value = "headimg_jsr2", required = false) MultipartFile headimg_jsr2,
			@RequestParam(value = "ebike_img", required = false) MultipartFile ebike_img,
			@RequestParam(value = "card1img_jsr1", required = false) MultipartFile card1img_jsr1,
			@RequestParam(value = "card2img_jsr1", required = false) MultipartFile card2img_jsr1,
			@RequestParam(value = "card1img_jsr2", required = false) MultipartFile card1img_jsr2,
			@RequestParam(value = "card2img_jsr2", required = false) MultipartFile card2img_jsr2,
			@RequestParam(value = "ebike_invoice_img", required = false) MultipartFile ebike_invoice_img,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("headimg_jsr1 = " + headimg_jsr1);
		System.out.println("headimg_jsr2 = " + headimg_jsr2);
		System.out.println("ebike_img = " + ebike_img);
		System.out.println("card1img_jsr1 = " + card1img_jsr1);
		System.out.println("card2img_jsr1 = " + card2img_jsr1);
		System.out.println("card1img_jsr2 = " + card1img_jsr2);
		System.out.println("card2img_jsr2 = " + card2img_jsr2);
		System.out.println("ebike_invoice_img = " + ebike_invoice_img);
		/**
		 * 检查黑名单
		 */
		String message = iApplyService.findHmd(ddcHyxhSsdwclsb.getJsrxm1(),
				ddcHyxhSsdwclsb.getJsrxm2());
		if (!message.equals("success")) {
			AjaxUtil.rendJson(response, false, message);
			return;
		}
		/**
		 * 新增时检查单位配额
		 */
		DdcHyxhSsdw dw = (DdcHyxhSsdw) request.getSession().getAttribute(
				SystemConstants.SESSION_USER);
		DdcHyxhSsdw ddcHyxhSsdw = iApplyService.getDdcHyxhSsdwById(String
				.valueOf(dw.getId()));
		if (ddcHyxhSsdwclsb.getId() == null) {
			if (ddcHyxhSsdw.getDwpe() <= 0) {
				AjaxUtil.rendJson(response, false, "该单位配额已用完，不能再申报");
				return;
			} else {
				ddcHyxhSsdw.setDwpe(ddcHyxhSsdw.getDwpe() - 1);
			}
		}
		ddcHyxhSsdwclsb.setSsdwId(ddcHyxhSsdw.getId() + "");
		ddcHyxhSsdwclsb.setHyxhzh(ddcHyxhSsdw.getHyxhzh());
		ddcHyxhSsdwclsb.setSqrq(new Date());
		ddcHyxhSsdwclsb.setSqr(ddcHyxhSsdw.getUserCode());
		ddcHyxhSsdwclsb.setnEnable(Integer.parseInt(SystemConstants.ENABLE_ZT));
		ddcHyxhSsdwclsb.setSlIndex(0);
		try {

			String ebike_jpgPath = uploadImg(request, ebike_img,
					SystemConstants.IMG_EBIKE_WITH,
					SystemConstants.IMG_EBIKE_HEIGHT);// 上传车身照片
			if (StringUtils.isNotBlank(ebike_jpgPath)) {
				ddcHyxhSsdwclsb.setVcEbikeImg(ebike_jpgPath);
			} else {
				ddcHyxhSsdwclsb.setVcEbikeImg(ddcHyxhSsdwclsb.getVcEbikeImg());
			}

			String vcUser1_img = uploadImg(request, headimg_jsr1,
					SystemConstants.IMG_HEAD_WITH,
					SystemConstants.IMG_HEAD_HEIGHT);// 上传驾驶人1照片
			if (StringUtils.isNotBlank(vcUser1_img)) {
				ddcHyxhSsdwclsb.setVcUser1Img(vcUser1_img);
			} else {
				ddcHyxhSsdwclsb.setVcUser1Img(ddcHyxhSsdwclsb.getVcUser1Img());
			}

			String vcUser2_img = uploadImg(request, headimg_jsr2,
					SystemConstants.IMG_HEAD_WITH,
					SystemConstants.IMG_HEAD_HEIGHT);// 上传驾驶人2照片
			if (StringUtils.isNotBlank(vcUser2_img)) {
				ddcHyxhSsdwclsb.setVcUser2Img(vcUser2_img);
			} else {
				ddcHyxhSsdwclsb.setVcUser2Img(ddcHyxhSsdwclsb.getVcUser2Img());
			}
			String vcUser1CardImg1 = uploadImg(request, card1img_jsr1,
					SystemConstants.IMG_IDCARD_WIDTH,
					SystemConstants.IMG_IDCARD_HEIGHT);// 上传驾驶人1身份证正面
			if (StringUtils.isNotBlank(vcUser1CardImg1)) {
				ddcHyxhSsdwclsb.setVcUser1CardImg1(vcUser1CardImg1);
			} else {
				ddcHyxhSsdwclsb.setVcUser1CardImg1(ddcHyxhSsdwclsb
						.getVcUser1CardImg1());
			}
			String vcUser1CardImg2 = uploadImg(request, card2img_jsr1,
					SystemConstants.IMG_IDCARD_WIDTH,
					SystemConstants.IMG_IDCARD_HEIGHT);// 上传驾驶人1身份证反面
			if (StringUtils.isNotBlank(vcUser1CardImg2)) {
				ddcHyxhSsdwclsb.setVcUser1CardImg2(vcUser1CardImg2);
			} else {
				ddcHyxhSsdwclsb.setVcUser1CardImg2(ddcHyxhSsdwclsb
						.getVcUser1CardImg2());
			}
			String vcUser2CardImg1 = uploadImg(request, card1img_jsr2,
					SystemConstants.IMG_IDCARD_WIDTH,
					SystemConstants.IMG_IDCARD_HEIGHT);// 驾驶人2身份证正面
			if (StringUtils.isNotBlank(vcUser2CardImg1)) {
				ddcHyxhSsdwclsb.setVcUser2CardImg1(vcUser2CardImg1);
			} else {
				ddcHyxhSsdwclsb.setVcUser2CardImg1(ddcHyxhSsdwclsb
						.getVcUser2CardImg1());
			}
			String vcUser2CardImg2 = uploadImg(request, card2img_jsr2,
					SystemConstants.IMG_IDCARD_WIDTH,
					SystemConstants.IMG_IDCARD_HEIGHT);// 驾驶人2身份证反面
			if (StringUtils.isNotBlank(vcUser2CardImg2)) {
				ddcHyxhSsdwclsb.setVcUser2CardImg2(vcUser2CardImg2);
			} else {
				ddcHyxhSsdwclsb.setVcUser2CardImg2(ddcHyxhSsdwclsb
						.getVcUser2CardImg2());
			}
			String vcEbikeInvoiceImg = uploadImg(request, ebike_invoice_img,
					SystemConstants.IMG_INVOICE_WIDTH,
					SystemConstants.IMG_INVOICE_HEIGHT);// 购车发票
			if (StringUtils.isNotBlank(vcEbikeInvoiceImg)) {
				ddcHyxhSsdwclsb.setVcEbikeInvoiceImg(vcEbikeInvoiceImg);
			} else {
				ddcHyxhSsdwclsb.setVcEbikeInvoiceImg(ddcHyxhSsdwclsb
						.getVcEbikeInvoiceImg());
			}

			if (ddcHyxhSsdwclsb.getId() == null) {
				// 生成流水号
				String sql = "select SEQ_HYXH_SSDWCLSB_XH.nextval from dual";
				Object object = iApplyService.getDateBySQL(sql);
				String seq = object.toString();
				String md = new SimpleDateFormat("yyMMdd").format(new Date());
				ddcHyxhSsdwclsb.setLsh("A" + md + seq);

				saveLog(ddcHyxhSsdwclsb, "新增", request);
				iApplyService.saveDdcHyxhSsdwclsb(ddcHyxhSsdwclsb);
			} else {

				saveLog(ddcHyxhSsdwclsb, "修改", request);
				iApplyService.updateDdcHyxhSsdwclsb(ddcHyxhSsdwclsb);
			}
			ddcHyxhSsdw.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
			ddcHyxhSsdw.setTranDate(new Date());
			iCompanyService.update(ddcHyxhSsdw);
			AjaxUtil.rendJson(response, true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "系统错误，操作失败");
		}
	}

	/**
	 * 方法描述：保存操作日志
	 * 
	 * @param ddcHyxhSsdwclsb
	 * @param string
	 * @param request
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午4:12:56
	 */
	private void saveLog(DdcHyxhSsdwclsb ddcHyxhSsdwclsb, String string,
			HttpServletRequest request) throws IllegalAccessException,
			InvocationTargetException {
		// TODO Auto-generated method stub
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		DdcHyxhSsdwclsbLog ddcHyxhSsdwclsbLog = new DdcHyxhSsdwclsbLog();
		BeanUtils.copyProperties(ddcHyxhSsdwclsbLog, ddcHyxhSsdwclsb);
		ddcHyxhSsdwclsbLog.setSqip(ip);
		ddcHyxhSsdwclsbLog.setCznr(string);
		ddcHyxhSsdwclsbLog.setCzrq(new Date());
		iApplyService.saveDdcHyxhSsdwclsbLog(ddcHyxhSsdwclsbLog);
	}

	/**
	 * 方法描述：
	 * 
	 * @param request
	 * @param fileupload
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午3:15:24
	 */
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

	/**
	 * 
	 * 方法描述：退回
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月9日 下午9:50:42
	 */
	@RequestMapping("/cancelSb")
	public void cancelSb(HttpServletRequest request,
			HttpServletResponse response, String id) {
		long dId = Long.parseLong(id);
		DdcHyxhSsdwclsb ddcHyxhSsdwclsb = iApplyService
				.getDdcHyxhSsdwclsbById(dId);
		try {
			if (ddcHyxhSsdwclsb.getSlIndex() == 0) {
				ddcHyxhSsdwclsb.setnEnable(Integer
						.parseInt(SystemConstants.DISENABLE_ZT));
				DdcHyxhSsdw ddcHyxhSsdw = iApplyService
						.getDdcHyxhSsdwById(ddcHyxhSsdwclsb.getSsdwId());
				ddcHyxhSsdw.setDwpe(ddcHyxhSsdw.getDwpe() + 1);
				iApplyService.updateDdcHyxhSsdwclsb(ddcHyxhSsdwclsb);
				AjaxUtil.rendJson(response, true, "已取消申报");
			} else {
				AjaxUtil.rendJson(response, false, "该申报申请正在审批中，无法修改或取消取消");
			}
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "系统错误");
		}
	}

	/**
	 * 
	 * 方法描述：车辆备案申报审批
	 * 
	 * @param state
	 *            1--拒绝 0-同意
	 * @param request
	 * @param response
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月12日 上午9:56:36
	 */
	@RequestMapping("/sureApproveRecord")
	public void sureApproveRecord(HttpServletRequest request, String state,
			String id, String slzl, String tbyy, String note,
			HttpServletResponse response) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		long dId = Long.parseLong(id);
		DdcHyxhSsdwclsb ddcHyxhSsdwclsb = iApplyService
				.getDdcHyxhSsdwclsbById(dId);
		if (state.equals("1")) {
			// 如果行业协会拒绝，则审批流程结束，不提交至内网审批,且申请单位的配额回收
			ddcHyxhSsdwclsb.setSlr(ddcHyxhBase.getHyxhmc());
			ddcHyxhSsdwclsb.setSlyj(SystemConstants.NOTAGREE);
			ddcHyxhSsdwclsb.setSlbz(note);
			ddcHyxhSsdwclsb.setSlrq(new Date());
			ddcHyxhSsdwclsb.setSlIndex(1);
			if (StringUtils.isNotBlank(tbyy)) {
				ddcHyxhSsdwclsb.setTbyy(tbyy);
			}
			DdcHyxhSsdw ddcHyxhSsdw = iApplyService
					.getDdcHyxhSsdwById(ddcHyxhSsdwclsb.getSsdwId());
			ddcHyxhSsdw.setDwpe(ddcHyxhSsdw.getDwpe() + 1);

			// 审批人
			DdcApproveUser ddcApproveUser = new DdcApproveUser();
			String sql = "select SEQ_DDC_APPROVE_USER.nextval from dual";
			Object object = iApplyService.getDateBySQL(sql);
			String seq = object.toString();
			String md = new SimpleDateFormat("yyMMdd").format(new Date());
			String approveNo = "W" + md + seq;// 生成审批号
			ddcApproveUser.setApproveNo(approveNo);
			ddcApproveUser.setUserName(ddcHyxhBase.getHyxhmc());
			ddcApproveUser.setUserRoleName("行业协会");
			ddcApproveUser.setApproveIndex(1);
			ddcApproveUser.setApproveNote(note);
			ddcApproveUser.setApproveState(Integer.parseInt(state));
			ddcApproveUser.setApproveTable(SystemConstants.RECORDSBTABLE);
			ddcApproveUser.setApproveTableid(ddcHyxhSsdwclsb.getId());
			ddcApproveUser.setApproveTime(new Date());
			ddcApproveUser.setLsh(ddcHyxhSsdwclsb.getLsh());
			try {
				iCompanyService.update(ddcHyxhSsdw);
				iApplyService.updateDdcHyxhSsdwclsb(ddcHyxhSsdwclsb);
				iApplyService.saveDdcApproveUser(ddcApproveUser);
				AjaxUtil.rendJson(response, true, "审批成功!");
			} catch (Exception e) {
				e.printStackTrace();
				AjaxUtil.rendJson(response, false, "审批失败!系统错误");
			}
		} else if (state.equals("0")) {
			// 同意，审批顺序改变,同步至内网
			ddcHyxhSsdwclsb.setSlIndex(1);
			ddcHyxhSsdwclsb.setSynFlag(SystemConstants.SYSNFLAG_ADD);
			ddcHyxhSsdwclsb.setTranDate(new Date());
			// 审批人
			DdcApproveUser ddcApproveUser = new DdcApproveUser();
			String sql = "select SEQ_DDC_APPROVE_USER.nextval from dual";
			Object object = iApplyService.getDateBySQL(sql);
			String seq = object.toString();
			String md = new SimpleDateFormat("yyMMdd").format(new Date());
			String approveNo = "W" + md + seq;// 生成审批号
			ddcApproveUser.setApproveNo(approveNo);
			ddcApproveUser.setUserName(ddcHyxhBase.getHyxhmc());
			ddcApproveUser.setUserRoleName("行业协会");
			ddcApproveUser.setApproveIndex(1);
			ddcApproveUser.setApproveNote(note);
			ddcApproveUser.setApproveState(Integer.parseInt(state));
			ddcApproveUser.setApproveTable(SystemConstants.RECORDSBTABLE);
			ddcApproveUser.setApproveTableid(ddcHyxhSsdwclsb.getId());
			ddcApproveUser.setApproveTime(new Date());
			ddcApproveUser.setSysFlag(SystemConstants.SYSNFLAG_ADD);
			ddcApproveUser.setTranDate(new Date());
			ddcApproveUser.setLsh(ddcHyxhSsdwclsb.getLsh());
			try {
				iApplyService.updateDdcHyxhSsdwclsb(ddcHyxhSsdwclsb);
				iApplyService.saveDdcApproveUser(ddcApproveUser);
				AjaxUtil.rendJson(response, true, "审批成功!");
			} catch (Exception e) {
				e.printStackTrace();
				AjaxUtil.rendJson(response, false, "审批失败!系统错误");
			}
		}
	}

	/**
	 * 
	 * 方法描述：档案的所有流水记录
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月12日 下午7:46:33
	 */
	@RequestMapping("/getFlowList")
	public String getFlowList(HttpServletRequest request, String dabh) {
		request.setAttribute("dabh", dabh);
		List<DdcSjzd> ywylxs = iApplyService.getSjzdByDmlb("YWLX");// 业务原因
		request.setAttribute("ywylxs", ywylxs);
		return "ebike/flowList";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param dabh
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月12日 下午8:05:44
	 */
	@RequestMapping("/queryFlowList")
	@ResponseBody
	public Map<String, Object> queryFlowList(HttpServletRequest request,
			String dabh, String ssdw, String ywlx, String djh, String cphm,
			String dtstart, String dtend) {
		Page p = ServiceUtil.getcurrPage(request);
		Object object = request.getSession().getAttribute(
				SystemConstants.SESSION_USER);
		String hyxhzh = null;
		DdcHyxhSsdw ddcHyxhSsdw = null;
		if (object.getClass().getSimpleName()
				.equals(SystemConstants.CLASS_NAME_DDC_HYXHSSDW)) {
			ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession().getAttribute(
					SystemConstants.SESSION_USER);
			hyxhzh = ddcHyxhSsdw.getHyxhzh();
		} else {
			DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
					.getAttribute(SystemConstants.SESSION_USER);
			hyxhzh = ddcHyxhBase.getHyxhzh();
		}

		String sql = "select A.DABH, A.ID,A.LSH,A.CPHM,A.DJH,A.SLRQ,a.SLYJ,a.SL_INDEX,(SELECT distinct S.DWMC FROM DDC_HYXH_SSDW S WHERE S.ID=A.SSDWID and rownum=1) AS DWMC,"
				+ " (select distinct D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.YWLX AND D.DMLB='YWLX' and rownum=1) as YWLX ,"
				+ "  (select distinct D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.XSQY AND D.DMLB='SSQY' and rownum=1 ) as SSQY ,"
				+ "(SELECT D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.YWLX AND D.DMLB='CLZT')AS ZT from DDC_FLOW A WHERE A.HYXHZH='"
				+ hyxhzh + "'  ";
		if (ddcHyxhSsdw != null) {
			sql += " and a.SSDWID = " + ddcHyxhSsdw.getId();
		}

		if (StringUtils.isNotBlank(ywlx)) {
			sql += " AND A.YWLX = '" + ywlx + "'";
		}
		if (StringUtils.isNotBlank(djh)) {
			sql += " AND A.djh = '" + djh + "'";
		}
		if (StringUtils.isNotBlank(cphm)) {
			sql += " AND A.cphm = '" + cphm + "'";
		}
		if (StringUtils.isNotBlank(dtstart)) {
			sql += " and a.slrq >=to_date('" + dtstart + "','yyyy-MM-dd')";
		}
		if (StringUtils.isNotBlank(dtend)) {
			sql += " and a.slrq <=to_date('" + dtend + "','yyyy-MM-dd')";
		}
		if (StringUtils.isNotBlank(ssdw)) {
			sql += " and a.SSDWID = " + ssdw;
		}
		if (StringUtils.isNotBlank(dabh)) {
			sql += " and a.dabh = '" + dabh + "'";
		}

		sql += " order by a.id desc";
		Map<String, Object> resultMap = iEbikeService.queryBySpringSql(sql, p);

		return resultMap;
	}

	/**
	 * 
	 * 方法描述：查询单位信息
	 * 
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月13日 下午5:40:06
	 */
	@RequestMapping("/querySsdwInfoById")
	@ResponseBody
	public DdcHyxhSsdw querySsdwInfoById(String id) {
		long dId = Long.parseLong(id);
		DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(dId);
		String vcShowLicenceImg = parseUrl(ddcHyxhSsdw.getVcPicPath());
		ddcHyxhSsdw.setVcShowPath(vcShowLicenceImg);
		return ddcHyxhSsdw;
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
}

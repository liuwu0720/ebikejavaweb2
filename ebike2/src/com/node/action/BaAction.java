/**
 * 文件名：BaAction.java
 * 版本信息：Version 1.0
 * 日期：2016年6月17日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

import com.node.model.DdcApproveUser;
import com.node.model.DdcDriver;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.model.DdcSjzd;
import com.node.model.PicPath;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
import com.node.service.IDriverSerivce;
import com.node.util.AjaxUtil;
import com.node.util.HqlHelper;
import com.node.util.ImgUploadThread;
import com.node.util.Page;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;

/**
 * 类描述：新修改：人车分离备案申报
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月17日 上午11:09:30
 */
@Controller
@RequestMapping("/baAction")
public class BaAction {
	@Autowired
	IApplyService iApplyService;

	@Autowired
	ICompanyService iCompanyService;

	@Autowired
	IDriverSerivce iDriverSerivce;

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年6月17日 上午11:12:21
	 */
	@RequestMapping("/getAll")
	public String getAll(HttpServletRequest request) {
		DdcHyxhSsdw dw = (DdcHyxhSsdw) request.getSession().getAttribute(
				SystemConstants.SESSION_USER);
		DdcHyxhSsdw ddcHyxhSsdw = iApplyService.getDdcHyxhSsdwById(String
				.valueOf(dw.getId()));
		request.setAttribute("ddcHyxhSsdw", ddcHyxhSsdw);
		return "baApply/clsblist";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @param zt
	 * @param xsqy
	 * @param jsrxm1
	 * @param ssdw
	 * @param djh
	 * @param dtstart
	 * @param dtend
	 * @return
	 * @throws ParseException
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年6月17日 上午11:27:26
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryBaList(HttpServletRequest request,
			String djh, String dtStart, String dtend, String xsqy, String zt,
			String jsrxm1, HttpServletResponse response) {
		Page page = ServiceUtil.getcurrPage(request);
		DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		String sql = "select s.id,s.lsh,s.ppxh,(select d.DMMS1 from ddc_sjzd d where d.dmz = s.cysy and d.dmlb='CSYS' and rownum=1)as csysname,"
				+ "s.LXDH1,s.SFZMHM1, s.JSRXM1,s.djh,(select  d.DMMS1 from ddc_sjzd d where d.dmz = s.xsqy and d.dmlb='SSQY' and rownum=1 )as xsqyname ,s.sqrq,s.sl_index,s.slyj "
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
	 * 方法描述：修改详情
	 * 
	 * @param request
	 * @param type
	 * @param response
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年6月18日 下午2:50:36
	 */
	@RequestMapping("/updateRecordApprovalInfoById")
	public String updateRecordApprovalInfoById(HttpServletRequest request,
			String type, HttpServletResponse response, String id) {
		long dId = Long.parseLong(id);
		DdcHyxhSsdwclsb ddcHyxhSsdwclsb = iApplyService
				.getDdcHyxhSsdwclsbById(dId);

		String cysyName = iApplyService.findByProPerties("CSYS",
				ddcHyxhSsdwclsb.getCysy());

		ddcHyxhSsdwclsb.setCysyName(cysyName);// 车身颜色
		String xsqyName = iApplyService.findByProPerties("SSQY",
				ddcHyxhSsdwclsb.getXsqy());
		ddcHyxhSsdwclsb.setXsqyName(xsqyName);// 行驶区域

		// 申报单位
		if (StringUtils.isNotBlank(ddcHyxhSsdwclsb.getSsdwId())) {
			DdcHyxhSsdw ddcHyxhSsdw = iApplyService
					.getDdcHyxhSsdwById(ddcHyxhSsdwclsb.getSsdwId());
			if (ddcHyxhSsdw != null) {
				ddcHyxhSsdwclsb.setSsdwName(ddcHyxhSsdw.getDwmc());
			} else {
				ddcHyxhSsdwclsb.setSsdwName(null);
			}
		}
		String showEbikeImg = parseUrl(ddcHyxhSsdwclsb.getVcEbikeImg());
		String showUser1Img = parseUrl(ddcHyxhSsdwclsb.getVcUser1Img());
		String showUser2Img = parseUrl(ddcHyxhSsdwclsb.getVcUser2Img());
		String vcUser1CardImg1Show = parseUrl(ddcHyxhSsdwclsb
				.getVcUser1CardImg1());
		String vcUser1CardImg2Show = parseUrl(ddcHyxhSsdwclsb
				.getVcUser1CardImg2());
		String vcUser2CardImg1Show = parseUrl(ddcHyxhSsdwclsb
				.getVcUser2CardImg1());
		String vcUser2CardImg2Show = parseUrl(ddcHyxhSsdwclsb
				.getVcUser2CardImg2());
		String vcEbikeInvoiceImgShow = parseUrl(ddcHyxhSsdwclsb
				.getVcEbikeInvoiceImg());
		String vcUser1WorkImgShow = parseUrl(ddcHyxhSsdwclsb
				.getVcUser1WorkImg());
		String vcUser2WorkImgShow = parseUrl(ddcHyxhSsdwclsb
				.getVcUser2WorkImg());
		String vcQualifiedImgShow = parseUrl(ddcHyxhSsdwclsb
				.getVcQualifiedImg());
		String vcEbikeInsuranceImgShow = parseUrl(ddcHyxhSsdwclsb
				.getVcEbikeInsuranceImg());
		ddcHyxhSsdwclsb.setVcShowEbikeImg(showEbikeImg);
		ddcHyxhSsdwclsb.setVcShowUser1Img(showUser1Img);
		ddcHyxhSsdwclsb.setVcShowUser2Img(showUser2Img);
		ddcHyxhSsdwclsb.setVcUser1CardImg1Show(vcUser1CardImg1Show);
		ddcHyxhSsdwclsb.setVcUser1CardImg2Show(vcUser1CardImg2Show);
		ddcHyxhSsdwclsb.setVcUser2CardImg1Show(vcUser2CardImg1Show);
		ddcHyxhSsdwclsb.setVcUser2CardImg2Show(vcUser2CardImg2Show);
		ddcHyxhSsdwclsb.setVcEbikeInvoiceImgShow(vcEbikeInvoiceImgShow);
		ddcHyxhSsdwclsb.setVcUser1WorkImgShow(vcUser1WorkImgShow);
		ddcHyxhSsdwclsb.setVcUser2WorkImgShow(vcUser2WorkImgShow);
		ddcHyxhSsdwclsb.setVcQualifiedImgShow(vcQualifiedImgShow);
		ddcHyxhSsdwclsb.setVcEbikeInsuranceImgShow(vcEbikeInsuranceImgShow);
		String approveTableName = SystemConstants.RECORDSBTABLE;
		List<DdcApproveUser> approveUsers = iApplyService
				.findApproveUsersByProperties(approveTableName,
						ddcHyxhSsdwclsb.getId());
		List<DdcSjzd> selectlTbyy = iApplyService.getDbyyList(
				ddcHyxhSsdwclsb.getTbyy(), "TBYY");// 选中的退办原因
		List<DdcSjzd> selectSlzls = iApplyService.getDbyyList(
				ddcHyxhSsdwclsb.getSlzl(), "BASQZL");
		List<DdcSjzd> dbyyDdcSjzds = iApplyService.getSjzdByDmlb("TBYY");// 数据字典中所有的退办原因
		List<DdcSjzd> slzList = iApplyService.getSjzdByDmlb("BASQZL");// 数据字典中所有的受理资料
		request.setAttribute("selectSlzls", selectSlzls);
		request.setAttribute("selectlTbyy", selectlTbyy);
		request.setAttribute("approveUsers", approveUsers);
		request.setAttribute("ddcHyxhSsdwclsb", ddcHyxhSsdwclsb);
		request.setAttribute("dbyyDdcSjzds", dbyyDdcSjzds);
		request.setAttribute("slzList", slzList);
		if (StringUtils.isNotBlank(type)) {
			request.setAttribute("type", type);
		}
		return "baApply/recordUpdate";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年6月18日 上午11:16:14
	 */
	@RequestMapping("/getDriverList")
	@ResponseBody
	public Map<String, Object> getDriverList(HttpServletRequest request,
			HttpServletResponse response) {
		Page page = ServiceUtil.getcurrPage(request);
		DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		String jsrxm = null;
		if (request.getParameter("q") != null) {
			jsrxm = request.getParameter("q").toString();
		}
		HqlHelper hql = new HqlHelper(DdcDriver.class);
		hql.addEqual("ssdwId", ddcHyxhSsdw.getId());
		if (StringUtils.isNotBlank(jsrxm)) {
			hql.addLike("jsrxm", jsrxm);
		}

		hql.addOrderBy("id", "desc");
		hql.setQueryPage(page);
		Map<String, Object> resultMap = iDriverSerivce.queryByHql(hql);
		return resultMap;
	}

	/**
	 * 
	 * 方法描述：保存 车辆申报
	 * 
	 * @param ddcHyxhSsdwclsb
	 * @param ebike_img
	 * @param ebike_invoice_img
	 * @param vcQualifiedImgfile
	 * @param vcEbikeInsuranceImgfile
	 * @param request
	 * @param response
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年6月22日 下午5:57:29
	 */
	@RequestMapping("/saveOrUpdateBaClSb")
	public void saveOrUpdateBaClSb(
			DdcHyxhSsdwclsb ddcHyxhSsdwclsb,
			@RequestParam(value = "ebike_img", required = false) MultipartFile ebike_img,
			@RequestParam(value = "ebike_invoice_img", required = false) MultipartFile ebike_invoice_img,
			@RequestParam(value = "vcQualifiedImgfile", required = false) MultipartFile vcQualifiedImgfile,
			@RequestParam(value = "vcEbikeInsuranceImgfile", required = false) MultipartFile vcEbikeInsuranceImgfile,
			HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 检查黑名单
		 */
		String message = iApplyService.findHmd(ddcHyxhSsdwclsb.getJsrxm1(),
				ddcHyxhSsdwclsb.getJsrxm2());
		if (ddcHyxhSsdwclsb.getId() == null) {
			message = iApplyService.findSameDjh(ddcHyxhSsdwclsb.getDjh());
		}
		if (ddcHyxhSsdwclsb.getSfzmhm1().equals(ddcHyxhSsdwclsb.getSfzmhm2())) {
			message = "两个驾驶人身份证号码不能相同!";
		}
		if (!message.equals("success")) {
			AjaxUtil.rendJson(response, false, message);
			return;
		}
		/*
		 * message = iApplyService.findIsValid(ddcHyxhSsdwclsb);//是否通过支付宝验证 if
		 * (!message.equals("success")) { AjaxUtil.rendJson(response, false,
		 * message); return; }
		 */

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
		iDriverSerivce.updateClsbByDriver(ddcHyxhSsdwclsb);
		PicPath imgPath = iCompanyService.getPathById(SystemConstants.PIC_IMG);
		try {

			String ebike_jpgPath = uploadImg(request, ebike_img,
					SystemConstants.IMG_EBIKE_WITH,
					SystemConstants.IMG_EBIKE_HEIGHT, imgPath);
			if (StringUtils.isNotBlank(ebike_jpgPath)) {
				ddcHyxhSsdwclsb.setVcEbikeImg(ebike_jpgPath);
			} else {
				ddcHyxhSsdwclsb.setVcEbikeImg(ddcHyxhSsdwclsb.getVcEbikeImg());
			}

			String vcEbikeInvoiceImg = uploadImg(request, ebike_invoice_img,
					SystemConstants.IMG_INVOICE_WIDTH,
					SystemConstants.IMG_INVOICE_HEIGHT, imgPath);// 购车发票
			if (StringUtils.isNotBlank(vcEbikeInvoiceImg)) {
				ddcHyxhSsdwclsb.setVcEbikeInvoiceImg(vcEbikeInvoiceImg);
			} else {
				ddcHyxhSsdwclsb.setVcEbikeInvoiceImg(ddcHyxhSsdwclsb
						.getVcEbikeInvoiceImg());
			}

			String vcQualifiedImg_path = uploadImg(request, vcQualifiedImgfile,
					SystemConstants.IMG_INVOICE_WIDTH,
					SystemConstants.IMG_INVOICE_HEIGHT, imgPath);// 车辆合格证
			if (StringUtils.isNotBlank(vcQualifiedImg_path)) {
				ddcHyxhSsdwclsb.setVcQualifiedImg(vcQualifiedImg_path);
			} else {
				ddcHyxhSsdwclsb.setVcQualifiedImg(ddcHyxhSsdwclsb
						.getVcQualifiedImg());
			}
			String vcEbikeInsuranceImg_path = uploadImg(request,
					vcEbikeInsuranceImgfile, SystemConstants.IMG_INVOICE_WIDTH,
					SystemConstants.IMG_INVOICE_HEIGHT, imgPath);// 投保凭证
			if (StringUtils.isNotBlank(vcEbikeInsuranceImg_path)) {
				ddcHyxhSsdwclsb
						.setVcEbikeInsuranceImg(vcEbikeInsuranceImg_path);
			} else {
				ddcHyxhSsdwclsb.setVcEbikeInsuranceImg(ddcHyxhSsdwclsb
						.getVcEbikeInsuranceImg());
			}

			if (ddcHyxhSsdwclsb.getId() == null) {
				// 生成流水号
				String sql = "select SEQ_HYXH_SSDWCLSB_XH.nextval from dual";
				Object object = iApplyService.getDateBySQL(sql);
				String seq = object.toString();
				String md = new SimpleDateFormat("yyMMdd").format(new Date());
				ddcHyxhSsdwclsb.setLsh("A" + md + seq);
				iApplyService.saveDdcHyxhSsdwclsb(ddcHyxhSsdwclsb);
			} else {
				ddcHyxhSsdwclsb.setSlbz(null);
				ddcHyxhSsdwclsb.setTbyy(null);
				ddcHyxhSsdwclsb.setSlyj(null);
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
	 * 方法描述：
	 * 
	 * @param request
	 * @param headimg_jsr1
	 * @param imgHeadWith
	 * @param imgHeadHeight
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年6月9日 下午4:41:41
	 */
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
	 * @version: 2016年6月18日 下午1:56:38
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
	 * 方法描述：图片显示路径进行解析
	 * 
	 * @param vcPicPath
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 下午3:23:40
	 */
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

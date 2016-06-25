/**
 * 文件名：SsdwAction.java
 * 版本信息：Version 1.0
 * 日期：2016年4月9日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.text.DateFormat;
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

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.node.model.DdcApproveUser;
import com.node.model.DdcDriver;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.model.DdcSjzd;
import com.node.model.PicPath;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
import com.node.service.IEbikeService;
import com.node.util.AjaxUtil;
import com.node.util.ImgUploadThread;
import com.node.util.Page;
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
			@RequestParam(value = "vcUser1WorkImgfile", required = false) MultipartFile vcUser1WorkImgfile,
			@RequestParam(value = "vcUser2WorkImgfile", required = false) MultipartFile vcUser2WorkImgfile,
			@RequestParam(value = "vcQualifiedImgfile", required = false) MultipartFile vcQualifiedImgfile,
			@RequestParam(value = "vcEbikeInsuranceImgfile", required = false) MultipartFile vcEbikeInsuranceImgfile,
			HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 检查黑名单
		 */
		String message = iApplyService.findHmd(ddcHyxhSsdwclsb.getJsrxm1(),
				ddcHyxhSsdwclsb.getJsrxm2());
		if(ddcHyxhSsdwclsb.getId()==null){
			message = iApplyService.findSameDjh(ddcHyxhSsdwclsb.getDjh());
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

			String vcUser1_img = uploadImg(request, headimg_jsr1,
					SystemConstants.IMG_HEAD_WITH,
					SystemConstants.IMG_HEAD_HEIGHT, imgPath);// 上传驾驶人1照片
			if (StringUtils.isNotBlank(vcUser1_img)) {
				ddcHyxhSsdwclsb.setVcUser1Img(vcUser1_img);
			} else {
				ddcHyxhSsdwclsb.setVcUser1Img(ddcHyxhSsdwclsb.getVcUser1Img());
			}

			String vcUser2_img = uploadImg(request, headimg_jsr2,
					SystemConstants.IMG_HEAD_WITH,
					SystemConstants.IMG_HEAD_HEIGHT, imgPath);// 上传驾驶人2照片
			if (StringUtils.isNotBlank(vcUser2_img)) {
				ddcHyxhSsdwclsb.setVcUser2Img(vcUser2_img);
			} else {
				ddcHyxhSsdwclsb.setVcUser2Img(ddcHyxhSsdwclsb.getVcUser2Img());
			}
			String vcUser1CardImg1 = uploadImg(request, card1img_jsr1,
					SystemConstants.IMG_IDCARD_WIDTH,
					SystemConstants.IMG_IDCARD_HEIGHT, imgPath);// 上传驾驶人1身份证正面
			if (StringUtils.isNotBlank(vcUser1CardImg1)) {
				ddcHyxhSsdwclsb.setVcUser1CardImg1(vcUser1CardImg1);
			} else {
				ddcHyxhSsdwclsb.setVcUser1CardImg1(ddcHyxhSsdwclsb
						.getVcUser1CardImg1());
			}
			String vcUser1CardImg2 = uploadImg(request, card2img_jsr1,
					SystemConstants.IMG_IDCARD_WIDTH,
					SystemConstants.IMG_IDCARD_HEIGHT, imgPath);// 上传驾驶人1身份证反面
			if (StringUtils.isNotBlank(vcUser1CardImg2)) {
				ddcHyxhSsdwclsb.setVcUser1CardImg2(vcUser1CardImg2);
			} else {
				ddcHyxhSsdwclsb.setVcUser1CardImg2(ddcHyxhSsdwclsb
						.getVcUser1CardImg2());
			}
			String vcUser2CardImg1 = uploadImg(request, card1img_jsr2,
					SystemConstants.IMG_IDCARD_WIDTH,
					SystemConstants.IMG_IDCARD_HEIGHT, imgPath);// 驾驶人2身份证正面
			if (StringUtils.isNotBlank(vcUser2CardImg1)) {
				ddcHyxhSsdwclsb.setVcUser2CardImg1(vcUser2CardImg1);
			} else {
				ddcHyxhSsdwclsb.setVcUser2CardImg1(ddcHyxhSsdwclsb
						.getVcUser2CardImg1());
			}
			String vcUser2CardImg2 = uploadImg(request, card2img_jsr2,
					SystemConstants.IMG_IDCARD_WIDTH,
					SystemConstants.IMG_IDCARD_HEIGHT, imgPath);// 驾驶人2身份证反面
			if (StringUtils.isNotBlank(vcUser2CardImg2)) {
				ddcHyxhSsdwclsb.setVcUser2CardImg2(vcUser2CardImg2);
			} else {
				ddcHyxhSsdwclsb.setVcUser2CardImg2(ddcHyxhSsdwclsb
						.getVcUser2CardImg2());
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
			String vcUser1WorkImg_path = uploadImg(request, vcUser1WorkImgfile,
					SystemConstants.IMG_INVOICE_WIDTH,
					SystemConstants.IMG_INVOICE_HEIGHT, imgPath);// 驾驶人1 在职证明或居住证
			if (StringUtils.isNotBlank(vcUser1WorkImg_path)) {
				ddcHyxhSsdwclsb.setVcUser1WorkImg(vcUser1WorkImg_path);
			} else {
				ddcHyxhSsdwclsb.setVcUser1WorkImg(ddcHyxhSsdwclsb
						.getVcUser1WorkImg());
			}
			String vcUser2WorkImg_path = uploadImg(request, vcUser2WorkImgfile,
					SystemConstants.IMG_INVOICE_WIDTH,
					SystemConstants.IMG_INVOICE_HEIGHT, imgPath);// 驾驶人2 在职证明或居住证
			if (StringUtils.isNotBlank(vcUser2WorkImg_path)) {
				ddcHyxhSsdwclsb.setVcUser2WorkImg(vcUser2WorkImg_path);
			} else {
				ddcHyxhSsdwclsb.setVcUser2WorkImg(ddcHyxhSsdwclsb
						.getVcUser2WorkImg());
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
				saveHasValidDriver(ddcHyxhSsdwclsb);
				iApplyService.saveDdcHyxhSsdwclsb(ddcHyxhSsdwclsb);
			} else {
				saveHasValidDriver(ddcHyxhSsdwclsb);
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
		if(!headimg_jsr1.isEmpty()){
			SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
			String ebike_jpgPath = generateFileName(headimg_jsr1
					.getOriginalFilename());
			ImgUploadThread iThread = new ImgUploadThread(headimg_jsr1,
					imgHeadWith, imgHeadHeight, imgPath,
					ebike_jpgPath);
			iThread.run();
			return format.format(new Date()) + "/" + ebike_jpgPath;
		}else {
			return null;
		}
		
	}

	/**
	 * 方法描述：
	 * 
	 * @param ddcHyxhSsdwclsb
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年6月5日 下午3:18:02
	 */
	private void saveHasValidDriver(DdcHyxhSsdwclsb ddcHyxhSsdwclsb) {
		// TODO Auto-generated method stub
		DdcDriver ddcDriver1 = new DdcDriver();
		ddcDriver1.setJsrxm(ddcHyxhSsdwclsb.getJsrxm1());
		ddcDriver1.setLxdh(ddcHyxhSsdwclsb.getLxdh1());
		ddcDriver1.setSfzhm(ddcHyxhSsdwclsb.getSfzmhm1());
		ddcDriver1.setUserCode(ddcHyxhSsdwclsb.getLxdh1());
		ddcDriver1.setUserPassword("123456");
		ddcDriver1.setVcUserImg(ddcHyxhSsdwclsb.getVcUser1Img());
		ddcDriver1.setVcUserWorkImg(ddcHyxhSsdwclsb.getVcUser1WorkImg());
		ddcDriver1.setHyxhzh(ddcHyxhSsdwclsb.getHyxhzh());
		ddcDriver1.setSsdwId(Long.parseLong(ddcHyxhSsdwclsb.getSsdwId()));
		ddcDriver1.setVcUserCardImg1(ddcHyxhSsdwclsb.getVcUser1CardImg1());
		ddcDriver1.setVcUserCardImg2(ddcHyxhSsdwclsb.getVcUser1CardImg2());
		if (StringUtils.isNotBlank(ddcHyxhSsdwclsb.getJsrxm2())) {
			DdcDriver ddcDriver2 = new DdcDriver();
			ddcDriver2.setJsrxm(ddcHyxhSsdwclsb.getJsrxm2());
			ddcDriver2.setLxdh(ddcHyxhSsdwclsb.getLxdh2());
			ddcDriver2.setSfzhm(ddcHyxhSsdwclsb.getSfzmhm2());
			ddcDriver2.setUserCode(ddcHyxhSsdwclsb.getLxdh2());
			ddcDriver2.setUserPassword("123456");
			ddcDriver2.setHyxhzh(ddcHyxhSsdwclsb.getHyxhzh());
			ddcDriver2.setSsdwId(Long.parseLong(ddcHyxhSsdwclsb.getSsdwId()));
			if (StringUtils.isNotBlank(ddcHyxhSsdwclsb.getVcUser2Img())) {
				ddcDriver2.setVcUserImg(ddcHyxhSsdwclsb.getVcUser2Img());
			}
			if (StringUtils.isNotBlank(ddcHyxhSsdwclsb.getVcUser2WorkImg())) {
				ddcDriver2
						.setVcUserWorkImg(ddcHyxhSsdwclsb.getVcUser2WorkImg());
			}
			ddcDriver2.setVcUserCardImg1(ddcHyxhSsdwclsb.getVcUser2CardImg1());
			ddcDriver2.setVcUserCardImg2(ddcHyxhSsdwclsb.getVcUser2CardImg2());

			iEbikeService.saveDdcDriver(ddcDriver2);
		}
		iEbikeService.saveDdcDriver(ddcDriver1);
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
			/*DdcHyxhSsdw ddcHyxhSsdw = iApplyService
					.getDdcHyxhSsdwById(ddcHyxhSsdwclsb.getSsdwId());
			ddcHyxhSsdw.setDwpe(ddcHyxhSsdw.getDwpe() + 1);*/

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
				//iCompanyService.update(ddcHyxhSsdw);
				iApplyService.updateDdcHyxhSsdwclsb(ddcHyxhSsdwclsb);
				iApplyService.saveDdcApproveUser(ddcApproveUser);
				AjaxUtil.rendJson(response, true, "审批成功!");
			} catch (Exception e) {
				e.printStackTrace();
				AjaxUtil.rendJson(response, false, "审批失败!系统错误");
			}
		} else if (state.equals("0")) {
			String message = iApplyService.findIsValid(ddcHyxhSsdwclsb);// 是否通过支付宝验证
			if (!message.equals("success")) {
				AjaxUtil.rendJson(response, false, message);
				return;
			}
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

/**
 * 文件名：ApplyAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月14日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.model.DdcHyxhSsdwclsbLog;
import com.node.model.DdcSjzd;
import com.node.model.PicPath;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
import com.node.util.AjaxUtil;
import com.node.util.HqlHelper;
import com.node.util.Page;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;

/**
 * 类描述：备案申报
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月14日 下午4:25:46
 */
@Controller
@RequestMapping("/applyAction")
public class ApplyAction {
	@Autowired
	IApplyService iApplyService;
	@Autowired
	ICompanyService iCompanyService;

	/**
	 * 
	 * 方法描述：页面跳转
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月14日 下午4:45:47
	 */
	@RequestMapping("/getAll")
	public String getAll() {
		return "apply/ebikeInfos";

	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月14日 下午5:40:32
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request, String zt,
			String djh, Date dtstart, Date dtend) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute("ddcHyxhBase");
		Page p = ServiceUtil.getcurrPage(request);

		HqlHelper hql = new HqlHelper(DdcHyxhSsdwclsb.class);
		hql.addEqual("hyxhzh", ddcHyxhBase.getHyxhzh());
		if (StringUtils.isNotBlank(zt)) {
			hql.addEqual("synFlag", zt);
		}
		if (StringUtils.isNotBlank(djh)) {
			hql.addEqual("djh", djh);
		}
		if (dtstart != null) {
			hql.addGreatThan("sqrq", dtstart);
		}
		if (dtend != null) {
			hql.addLessThan("sqrq", dtend);
		}

		hql.addOrderBy("sqrq", "desc");
		hql.setQueryPage(p);
		Map<String, Object> resultMap = iApplyService.queryByHql(hql);
		List<DdcHyxhSsdwclsb> list = (List<DdcHyxhSsdwclsb>) resultMap
				.get("rows");
		for (DdcHyxhSsdwclsb ddcHyxhSsdwclsb : list) {
			String cysyName = iApplyService.findByProPerties("CSYS",
					ddcHyxhSsdwclsb.getCysy());

			ddcHyxhSsdwclsb.setCysyName(cysyName);// 车身颜色
			String xsqyName = iApplyService.findByProPerties("SSQY",
					ddcHyxhSsdwclsb.getXsqy());
			ddcHyxhSsdwclsb.setXsqyName(xsqyName);// 行驶区域
			// 申报单位
			if (StringUtils.isNotBlank(ddcHyxhSsdwclsb.getSsdwId())) {
				DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(Long
						.parseLong(ddcHyxhSsdwclsb.getSsdwId()));
				if (ddcHyxhSsdw != null) {
					ddcHyxhSsdwclsb.setSsdwName(ddcHyxhSsdw.getDwmc());
				} else {
					ddcHyxhSsdwclsb.setSsdwName(null);
				}
			}

		}

		return resultMap;
	}

	/**
	 * 
	 * 方法描述：根据ID查询详情
	 * 
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午6:47:58
	 */
	@RequestMapping("/queryInfoById")
	@ResponseBody
	public DdcHyxhSsdwclsb queryInfoById(String id) {
		long sbId = Long.parseLong(id);
		DdcHyxhSsdwclsb ddcHyxhSsdwclsb = iApplyService
				.getDdcHyxhSsdwclsbById(sbId);
		String cysyName = iApplyService.findByProPerties("CSYS",
				ddcHyxhSsdwclsb.getCysy());

		ddcHyxhSsdwclsb.setCysyName(cysyName);// 车身颜色
		String xsqyName = iApplyService.findByProPerties("SSQY",
				ddcHyxhSsdwclsb.getXsqy());
		ddcHyxhSsdwclsb.setXsqyName(xsqyName);// 行驶区域
		// 申报单位
		if (StringUtils.isNotBlank(ddcHyxhSsdwclsb.getSsdwId())) {
			DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(Long
					.parseLong(ddcHyxhSsdwclsb.getSsdwId()));
			if (ddcHyxhSsdw != null) {
				ddcHyxhSsdwclsb.setSsdwName(ddcHyxhSsdw.getDwmc());
			} else {
				ddcHyxhSsdwclsb.setSsdwName(null);
			}
		}
		String showEbikeImg = parseUrl(ddcHyxhSsdwclsb.getVcEbikeImg());
		String showUser1Img = parseUrl(ddcHyxhSsdwclsb.getVcUser1Img());
		String showUser2Img = parseUrl(ddcHyxhSsdwclsb.getVcUser2Img());
		ddcHyxhSsdwclsb.setVcShowEbikeImg(showEbikeImg);
		ddcHyxhSsdwclsb.setVcShowUser1Img(showUser1Img);
		ddcHyxhSsdwclsb.setVcShowUser2Img(showUser2Img);
		return ddcHyxhSsdwclsb;
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

	/**
	 * 
	 * 方法描述：ajax查询所有车身颜色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午2:21:13
	 */
	@RequestMapping("/getAllColorsAjax")
	@ResponseBody
	public List<DdcSjzd> getAllColorsAjax(HttpServletRequest request,
			HttpServletResponse response) {
		String dmlb = "dmlb";
		String value = "CSYS";
		List<DdcSjzd> ddcSjzds = iApplyService.getAllByProperties(dmlb, value);
		return ddcSjzds;
	}

	/**
	 * 
	 * 方法描述：查询所有区域
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午3:33:57
	 */
	@RequestMapping("/getAllAreaAjax")
	@ResponseBody
	public List<DdcSjzd> getAllAreaAjax(HttpServletRequest request,
			HttpServletResponse response) {
		String dmlb = "dmlb";
		String value = "SSQY";
		List<DdcSjzd> ddcSjzds = iApplyService.getAllByProperties(dmlb, value);
		return ddcSjzds;
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param ddcHyxhSsdwclsb行业协会所属单位车辆申报
	 * @param fileupload
	 *            车身照片
	 * @param file_upload1
	 *            驾驶人1照片
	 * @param file_upload2
	 *            驾驶人2照片
	 * @param request
	 * @param response
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月15日 下午2:51:47
	 */
	@RequestMapping("/saveOrUpdate")
	public void saveOrUpdate(
			DdcHyxhSsdwclsb ddcHyxhSsdwclsb,
			@RequestParam(value = "file_upload", required = false) MultipartFile fileupload,
			@RequestParam(value = "file_upload1", required = false) MultipartFile file_upload1,
			@RequestParam(value = "file_upload2", required = false) MultipartFile file_upload2,
			HttpServletRequest request, HttpServletResponse response) {
		if (!fileupload.isEmpty()
				&& fileupload.getSize() / 1024 / 1024 > SystemConstants.MAXFILESIZE) {
			AjaxUtil.rendJson(response, false, "车身照片超出最大尺寸，允许上传大小为"
					+ SystemConstants.MAXFILESIZE + "MB");
			return;
		}
		if (!file_upload1.isEmpty()
				&& file_upload1.getSize() / 1024 / 1024 > SystemConstants.MAXFILESIZE) {
			AjaxUtil.rendJson(response, false, "驾驶人1照片超出最大尺寸，允许上传大小为"
					+ SystemConstants.MAXFILESIZE + "MB");
			return;
		}
		if (!file_upload2.isEmpty()
				&& file_upload2.getSize() / 1024 / 1024 > SystemConstants.MAXFILESIZE) {
			AjaxUtil.rendJson(response, false, "驾驶人2照片超出最大尺寸，允许上传大小为"
					+ SystemConstants.MAXFILESIZE + "MB");
			return;
		}
		/**
		 * 检查黑名单
		 */
		String message = iApplyService.findHmd(ddcHyxhSsdwclsb);
		if (!message.equals("success")) {
			AjaxUtil.rendJson(response, false, message);
			return;
		}
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute("ddcHyxhBase");
		ddcHyxhSsdwclsb.setHyxhzh(ddcHyxhBase.getHyxhzh());
		ddcHyxhSsdwclsb.setSqrq(new Date());
		ddcHyxhSsdwclsb.setSqr(ddcHyxhBase.getHyxhzh());
		try {

			String ebike_jpgPath = uploadImg(request, fileupload);// 上传车身照片
			if (StringUtils.isNotBlank(ebike_jpgPath)) {
				ddcHyxhSsdwclsb.setVcEbikeImg(ebike_jpgPath);
			} else {
				ddcHyxhSsdwclsb.setVcEbikeImg(ddcHyxhSsdwclsb.getVcEbikeImg());
			}

			String vcUser1_img = uploadImg(request, file_upload1);// 上传驾驶人1照片
			if (StringUtils.isNotBlank(vcUser1_img)) {
				ddcHyxhSsdwclsb.setVcUser1Img(vcUser1_img);
			} else {
				ddcHyxhSsdwclsb.setVcUser1Img(ddcHyxhSsdwclsb.getVcUser1Img());
			}

			String vcUser2_img = uploadImg(request, file_upload2);// 上传驾驶人2照片
			if (StringUtils.isNotBlank(vcUser2_img)) {
				ddcHyxhSsdwclsb.setVcUser2Img(vcUser2_img);
			} else {
				ddcHyxhSsdwclsb.setVcUser2Img(ddcHyxhSsdwclsb.getVcUser2Img());
			}

			if (ddcHyxhSsdwclsb.getId() == null) {
				// 生成流水号
				String sql = "select SEQ_HYXH_SSDWCLSB_XH.nextval from dual";
				Object object = iApplyService.getDateBySQL(sql);
				String seq = object.toString();
				String md = new SimpleDateFormat("yyMMdd").format(new Date());
				ddcHyxhSsdwclsb.setLsh("B" + md + seq);
				saveLog(ddcHyxhSsdwclsb, "新增", request);
				iApplyService.saveDdcHyxhSsdwclsb(ddcHyxhSsdwclsb);
			} else {
				saveLog(ddcHyxhSsdwclsb, "修改", request);
				iApplyService.updateDdcHyxhSsdwclsb(ddcHyxhSsdwclsb);
			}
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
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute("ddcHyxhBase");
		DdcHyxhSsdwclsbLog ddcHyxhSsdwclsbLog = new DdcHyxhSsdwclsbLog();
		BeanUtils.copyProperties(ddcHyxhSsdwclsbLog, ddcHyxhSsdwclsb);
		ddcHyxhSsdwclsbLog.setSqip(ip);
		ddcHyxhSsdwclsbLog.setCzbm(ddcHyxhBase.getCjbm());
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
	private String uploadImg(HttpServletRequest request, MultipartFile file)
			throws FileNotFoundException, IOException {
		if (!file.isEmpty()) {
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
			String path = source;
			String jpgPath = file.getOriginalFilename();

			File pathFile = new File(path, jpgPath);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}

			path += jpgPath;

			file.transferTo(pathFile);

			return format.format(new Date()) + "/" + jpgPath;
		} else {
			return null;
		}
	}
}

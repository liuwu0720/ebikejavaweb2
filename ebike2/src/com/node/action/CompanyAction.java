/**
 * 文件名：CompanyAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月11日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwLog;
import com.node.model.PicPath;
import com.node.service.ICompanyService;
import com.node.service.IUserService;
import com.node.util.AjaxUtil;
import com.node.util.HqlHelper;
import com.node.util.Page;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;

/**
 * 类描述：单位信息管理
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月11日 下午6:47:17
 */
@Controller
@RequestMapping("/companyAction")
@ApiIgnore
public class CompanyAction {

	@Autowired
	ICompanyService iCompanyService;

	@Autowired
	IUserService iUserService;

	/**
	 * 
	 * 方法描述：页面跳转
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月11日 下午10:07:14
	 */
	@RequestMapping("/getAll")
	public String getAll(HttpServletRequest request) {
		DdcHyxhBase xh = (DdcHyxhBase) request.getSession().getAttribute(
				SystemConstants.SESSION_USER);
		DdcHyxhBase ddcHyxhBase = iUserService.getById(xh.getId());
		request.setAttribute("ddcHyxhBase", ddcHyxhBase);
		return "company/companyInfos";

	}

	/**
	 * 
	 * 方法描述：查询行业所属单位的信息
	 * 
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月11日 下午10:16:16
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request, String zt,
			String dwmc, String lxr) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		Page p = ServiceUtil.getcurrPage(request);

		HqlHelper hql = new HqlHelper(DdcHyxhSsdw.class);
		hql.addEqual("hyxhzh", ddcHyxhBase.getHyxhzh());
		if (StringUtils.isNotBlank(zt)) {
			hql.addEqual("zt", zt);
		}
		if (StringUtils.isNotBlank(dwmc)) {
			hql.addEqual("dwmc", dwmc);
		}
		if (StringUtils.isNotBlank(lxr)) {
			hql.addEqual("lxr", lxr);
		}
		hql.addOrderBy("id", "desc");

		hql.setQueryPage(p);
		Map<String, Object> resultMap = iCompanyService.queryByHql(hql);
		DdcHyxhBase ddcHyxhBase2 = iUserService.getById(ddcHyxhBase.getId());// 重新查一次，因为数量有变化在session中体现不了
		request.setAttribute("ddcHyxhBase", ddcHyxhBase2);
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
	 * @version: 2016年3月12日 上午9:46:35
	 */
	@RequestMapping("/queryInfoById")
	@ResponseBody
	public DdcHyxhSsdw queryInfoById(HttpServletRequest request, String id) {
		long dwId = Long.parseLong(id);
		DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(dwId);
		String picShowPath = parseUrl(ddcHyxhSsdw.getVcPicPath());
		ddcHyxhSsdw.setVcShowPath(picShowPath);
		return ddcHyxhSsdw;
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
	 * 方法描述：新增、编辑保存、单位信息
	 * 
	 * @param DdcHyxhSsdw
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 上午10:30:36
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public void saveOrUpdate(DdcHyxhSsdw ddcHyxhSsdw,
			HttpServletRequest request, HttpServletResponse response) {
		DdcHyxhBase ba = (DdcHyxhBase) request.getSession().getAttribute(
				SystemConstants.SESSION_USER);
		DdcHyxhBase ddcHyxhBase = iUserService.getById(ba.getId());
		ddcHyxhSsdw.setHyxhzh(ddcHyxhBase.getHyxhzh());
		ddcHyxhSsdw.setSqrq(new Date());
		ddcHyxhSsdw.setSqr(ddcHyxhBase.getHyxhmc());
		ddcHyxhSsdw.setShFlag(1);
		if (ddcHyxhSsdw.getId() == null) {
			ddcHyxhSsdw.setPassWord("123456");
		}

		ddcHyxhSsdw.setZt(SystemConstants.ENABLE_ZT);
		/**
		 * vbg 验证剩余配额数量
		 */
		if (ddcHyxhSsdw.getId() == null) {
			// 剩余配额减少
			ddcHyxhBase.setHyxhsjzpe(ddcHyxhBase.getHyxhsjzpe()
					- ddcHyxhSsdw.getTotalPe());
			if (ddcHyxhBase.getHyxhsjzpe() <= 0) {
				AjaxUtil.rendJson(response, false, "配额不足不能再分配");
				return;
			}
		}

		/*
		 * try { String jpgPath = uploadImg(request, file); String imgPath =
		 * ddcHyxhSsdw.getVcPicPath(); if (StringUtils.isNotBlank(jpgPath)) {
		 * ddcHyxhSsdw.setVcPicPath(jpgPath); } else {
		 * ddcHyxhSsdw.setVcPicPath(imgPath); }
		 * 
		 * } catch (IOException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */

		if (ddcHyxhSsdw.getId() == null) {
			// 帐号重复验证
			String message = iCompanyService.queryIsSame(ddcHyxhSsdw);
			if (message.equals("success")) {
				try {
					ddcHyxhSsdw.setTotalPe(ddcHyxhSsdw.getTotalPe());
					ddcHyxhSsdw.setSynFlag(SystemConstants.SYSNFLAG_ADD);
					ddcHyxhSsdw.setTranDate(new Date());
					ddcHyxhSsdw.setDwpe(ddcHyxhSsdw.getTotalPe());
					iCompanyService.save(ddcHyxhSsdw);
					// 保存操作日志
					saveLog(ddcHyxhSsdw, "新增", request);
					ddcHyxhBase.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
					ddcHyxhBase.setTranDate(new Date());
					iUserService.update(ddcHyxhBase);
					AjaxUtil.rendJson(response, true, "操作成功");
				} catch (Exception e) {
					e.printStackTrace();
					AjaxUtil.rendJson(response, false, "系统错误：" + e.getMessage());
				}
			} else {
				AjaxUtil.rendJson(response, false, message);
			}
		} else {
			try {
				DdcHyxhSsdw beforeDdcHyxhSsdw = iCompanyService
						.queryInfoById(ddcHyxhSsdw.getId());
				String message = "success";
				if (!beforeDdcHyxhSsdw.getDwmc().equals(ddcHyxhSsdw.getDwmc())) {
					message = iCompanyService.queryIsSame(ddcHyxhSsdw);
				}
				if (message.equals("success")) {
					// 行业协会总配额
					int minus = ddcHyxhSsdw.getTotalPe()
							- beforeDdcHyxhSsdw.getTotalPe();
					ddcHyxhBase
							.setHyxhsjzpe(ddcHyxhBase.getHyxhsjzpe() - minus);
					ddcHyxhSsdw.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
					ddcHyxhSsdw.setTranDate(new Date());
					/*
					 * int useNum = ddcHyxhSsdw.getTotalPe() -
					 * ddcHyxhSsdw.getDwpe();
					 * ddcHyxhSsdw.setDwpe(ddcHyxhSsdw.getTotalPe() - useNum);
					 */
					iCompanyService.update(ddcHyxhSsdw);
					ddcHyxhBase.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
					ddcHyxhBase.setTranDate(new Date());
					iUserService.update(ddcHyxhBase);
					// 保存操作日志
					saveLog(ddcHyxhSsdw, "修改", request);

					AjaxUtil.rendJson(response, true, "操作成功");
				} else {
					AjaxUtil.rendJson(response, false, message);
				}

			} catch (Exception e) {
				e.printStackTrace();
				AjaxUtil.rendJson(response, false, "系统错误：" + e.getMessage());
			}
		}
	}

	/**
	 * 方法描述：保存操作日志
	 * 
	 * @param ddcHyxhSsdw
	 * @param type
	 *            操作类型：新增、修改
	 * @param request
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 下午5:50:37
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private void saveLog(DdcHyxhSsdw ddcHyxhSsdw, String type,
			HttpServletRequest request) throws IllegalAccessException,
			InvocationTargetException {
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
				.getAttribute(SystemConstants.SESSION_USER);
		DdcHyxhSsdwLog ddcHyxhSsdwLog = new DdcHyxhSsdwLog();
		BeanUtils.copyProperties(ddcHyxhSsdwLog, ddcHyxhSsdw);
		ddcHyxhSsdwLog.setCznr(type + "  ip=" + ip);// 操作内容
		ddcHyxhSsdwLog.setCzbm(ddcHyxhBase.getCjbm());
		ddcHyxhSsdwLog.setCzr(ddcHyxhSsdwLog.getCzr());
		ddcHyxhSsdwLog.setCzrq(new Date());
		iCompanyService.saveLog(ddcHyxhSsdwLog);
	}

	/**
	 * 方法描述：图片上传
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 下午2:21:13
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
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
			String jpgPath = UUID.randomUUID() + ".png";
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

	/**
	 * 
	 * 方法描述：批量审核
	 * 
	 * @param list
	 * @param request
	 * @param response
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 下午5:09:20
	 */
	@RequestMapping(value = "/changeRowData", method = RequestMethod.POST)
	public void changeRowData(HttpServletRequest request,
			HttpServletResponse response) {
		String[] ids = request.getParameterValues("array[]");
		System.out.println("ids = " + ids);
		try {
			for (int i = 0; i < ids.length; i++) {
				long id = Long.parseLong(ids[i]);
				DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(id);
				if (ddcHyxhSsdw.getZt().equals(SystemConstants.ENABLE_ZT)) {
					ddcHyxhSsdw.setShFlag(Integer
							.parseInt(SystemConstants.ENABLE_ZT));
					ddcHyxhSsdw.setShrq(new Date());
					ddcHyxhSsdw.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);// 同步标志
					ddcHyxhSsdw.setTranDate(new Date());
				}
				iCompanyService.update(ddcHyxhSsdw);
			}
			AjaxUtil.rendJson(response, true, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "系统错误");
		}

	}

	/**
	 * 
	 * 方法描述：ajax查询当前协会下的所有单位
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月14日 下午8:30:08
	 */
	@RequestMapping("/getAllCompanyAjax")
	@ResponseBody
	public List<DdcHyxhSsdw> getAllCompanyAjax(HttpServletRequest request,
			HttpServletResponse response) {
		Object object = request.getSession().getAttribute(
				SystemConstants.SESSION_USER);
		List<DdcHyxhSsdw> ddcHyxhSsdws = new ArrayList<>();
		if (object.getClass().getSimpleName()
				.equals(SystemConstants.CLASS_NAME_DDC_HYXHSSDW)) {
			DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
					.getAttribute(SystemConstants.SESSION_USER);
			ddcHyxhSsdws.add(ddcHyxhSsdw);

		} else {
			DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
					.getAttribute(SystemConstants.SESSION_USER);
			ddcHyxhSsdws = iCompanyService.getAllCompany(ddcHyxhBase
					.getHyxhzh());
		}

		return ddcHyxhSsdws;
	}

	/**
	 * 
	 * 方法描述：禁用
	 * 
	 * @param response
	 * @param request
	 * @param id
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月1日 下午5:26:17
	 */
	@RequestMapping("/deleteRowData")
	public void deleteRowData(HttpServletResponse response, String status,
			HttpServletRequest request, String id) {
		long dId = Long.parseLong(id);
		try {
			DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(dId);
			if (status.equals("0")) {
				ddcHyxhSsdw.setZt(SystemConstants.DISENABLE_ZT);
			} else if (status.equals("1")) {
				ddcHyxhSsdw.setZt(SystemConstants.ENABLE_ZT);
			}

			ddcHyxhSsdw.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
			ddcHyxhSsdw.setTranDate(new Date());
			iCompanyService.update(ddcHyxhSsdw);
			/*
			 * DdcHyxhBase ddcHyxhBase =
			 * iUserService.getByUserAccount(ddcHyxhSsdw .getHyxhzh()); if
			 * (ddcHyxhSsdw.getTotalPe() != null) {
			 * ddcHyxhBase.setHyxhsjzpe(ddcHyxhBase.getHyxhsjzpe() +
			 * ddcHyxhSsdw.getTotalPe()); iUserService.update(ddcHyxhBase); }
			 */

			AjaxUtil.rendJson(response, true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "操作失败，系统错误!");
		}
	}

	/**
	 * 
	 * 方法描述：重置密码
	 * 
	 * @param request
	 * @param id
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月9日 下午2:08:56
	 */
	@RequestMapping("/resetPassword")
	public void resetPassword(HttpServletRequest request,
			HttpServletResponse response, String id) {
		long dId = Long.parseLong(id);
		try {
			DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(dId);
			ddcHyxhSsdw.setPassWord("123456");
			ddcHyxhSsdw.setZt(SystemConstants.ENABLE_ZT);
			// ddcHyxhSsdw.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
			iCompanyService.update(ddcHyxhSsdw);
			AjaxUtil.rendJson(response, true, "密码重置成功！");
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "操作失败，系统错误!");
		}
	}
}

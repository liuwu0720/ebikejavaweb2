/**
 * 文件名：CompanyAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月11日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcHyxhSsdwLog;
import com.node.model.PicPath;
import com.node.service.ICompanyService;
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
public class CompanyAction {

	@Autowired
	ICompanyService iCompanyService;

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
	public String getAll() {
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
	public Map<String, Object> queryAll(HttpServletRequest request) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute("ddcHyxhBase");
		Page p = ServiceUtil.getcurrPage(request);

		HqlHelper hql = new HqlHelper(DdcHyxhSsdw.class);
		hql.addEqual("hyxhzh", ddcHyxhBase.getHyxhzh());
		hql.setQueryPage(p);
		Map<String, Object> resultMap = iCompanyService.queryByHql(hql);

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
					.getPathById(SystemConstants.PIC_LICENSE);
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
	 * 方法描述：新增、编辑保存
	 * 
	 * @param DdcHyxhSsdw
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月12日 上午10:30:36
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public void saveOrUpdate(
			DdcHyxhSsdw ddcHyxhSsdw,
			@RequestParam(value = "file_upload", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute("ddcHyxhBase");
		ddcHyxhSsdw.setHyxhzh(ddcHyxhBase.getHyxhzh());
		ddcHyxhSsdw.setSqrq(new Date());
		ddcHyxhSsdw.setSqr(ddcHyxhBase.getHyxhmc());

		try {
			String jpgPath = uploadImg(request, file);
			String imgPath = ddcHyxhSsdw.getVcPicPath();
			if (StringUtils.isBlank(imgPath)) {
				ddcHyxhSsdw.setVcPicPath(jpgPath);
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (ddcHyxhSsdw.getId() == null) {
			String message = iCompanyService.queryIsSame(ddcHyxhSsdw);
			if (message.equals("success")) {
				try {
					iCompanyService.save(ddcHyxhSsdw);
					// 保存操作日志
					saveLog(ddcHyxhSsdw, "新增", request);
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
					iCompanyService.update(ddcHyxhSsdw);
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
				.getAttribute("ddcHyxhBase");
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
	private String uploadImg(HttpServletRequest request, MultipartFile file)
			throws FileNotFoundException, IOException {
		if (!file.isEmpty()) {
			PicPath imgPath = iCompanyService
					.getPathById(SystemConstants.PIC_LICENSE);
			String source = imgPath.getVcAddpath();// 图片保存路径
			if (!source.endsWith("/")) {
				source += "/";
			}
			if (StringUtils.isBlank(source)) {
				System.out.println("source路径查不到！");
				return null;
			}
			String path = source;
			File pathFile = new File(path);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			String jpgPath = new Date().getTime() + file.getOriginalFilename();
			// path += new Date().getTime() +
			// files[i].getOriginalFilename();
			path += jpgPath;
			// 拿到输出流，同时重命名上传的文件
			FileOutputStream os = new FileOutputStream(path);
			// 拿到上传文件的输入流
			FileInputStream in = (FileInputStream) file.getInputStream();

			// 以写字节的方式写文件
			int b = 0;
			while ((b = in.read()) != -1) {
				os.write(b);
			}
			os.flush();
			os.close();
			in.close();
			return jpgPath;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 方法描述：同步
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
				ddcHyxhSsdw.setSynFlag("Y1");// 同步标志 刚开始为空 Y1-外网同步，不可修改等待晚上同步到内网
												// ;Y2-晚上已同步至内网

				iCompanyService.update(ddcHyxhSsdw);
			}
			AjaxUtil.rendJson(response, true, "成功");
		} catch (Exception e) {
			AjaxUtil.rendJson(response, false, "系统错误");
		}

	}
}

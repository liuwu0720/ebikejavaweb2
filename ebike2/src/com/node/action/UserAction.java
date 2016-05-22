/**
 * 文件名：UserAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月1日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.PicPath;
import com.node.service.ICompanyService;
import com.node.service.IUserService;
import com.node.util.AjaxUtil;
import com.node.util.ScaleImage;
import com.node.util.SingleOnline;
import com.node.util.SystemConstants;

/**
 * 类描述：用户首页登录、用户增删改查的一些操作
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月1日 上午12:09:31
 */
@Controller
@RequestMapping("/userAction")
@ApiIgnore
public class UserAction {

	@Autowired
	IUserService iUserService;

	@Autowired
	ICompanyService iCompanyService;

	/**
	 * 
	 * 方法描述：登录首页
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月1日 下午4:49:30
	 * @throws IOException 
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Object object = request.getSession().getAttribute(SystemConstants.SESSION_USER);
		String sessionUserName = (String) request.getSession().getAttribute(SystemConstants.SESSION_USER_NAME);
		boolean flag = SingleOnline.isValidUser(sessionUserName, request.getSession().getId());
		if(object==null&&StringUtils.isEmpty(sessionUserName)||!flag){
			return "index";
		}else {
			PrintWriter out = response.getWriter();
			out.println("<script>window.parent.location.replace('"
					+ request.getContextPath() + "/userAction/loginToMain')</script>");
			out.flush();
			out.close();
			return null;
		}
		
	
	}
	
	@RequestMapping("/makecode")
	public String makecode(){
		return "makeCodePic";
	}

	@RequestMapping("/checkUser")
	public void checkUser(HttpServletRequest request,
			HttpServletResponse response, String cuser, String cpassword,
			String role, String ccode) {

		String code = (String) request.getSession().getAttribute("certCode");

		if (StringUtils.isEmpty(code) || StringUtils.isEmpty(ccode)) {
			AjaxUtil.rendJson(response, false, "验证码获取失败，请刷新页面重试");
			return;
		}

		if (!code.equalsIgnoreCase(ccode)) {
			AjaxUtil.rendJson(response, false, "验证码不正确");
			return;
		}

		if (role.equals(SystemConstants.ROLE_HYXH)
				|| role.equals(SystemConstants.ROLE_ADMIN)) {
			DdcHyxhBase ddcHyxhBase = iUserService.getByUserAccount(cuser);
			if (ddcHyxhBase == null
					|| !ddcHyxhBase.getHyxhmm().equals(cpassword)) {

				AjaxUtil.rendJson(response, false, "用户名或密码错误！");
				return;
			} else {
				SingleOnline.addUser(cuser, request.getSession().getId());
				if (!SingleOnline.isValidUser(cuser, request.getSession()
						.getId())) {
					request.getSession().invalidate();
					SingleOnline.removeValidUser(cuser);
					AjaxUtil.rendJson(response, false,
							"您的账户在其它地方登录，对方已被踢掉请重新登录");
				} else {
					request.getSession().removeAttribute(
							SystemConstants.SESSION_USER);
					request.getSession().setAttribute(
							SystemConstants.SESSION_USER_NAME,
							ddcHyxhBase.getHyxhzh());
					request.getSession().setAttribute(
							SystemConstants.SESSION_USER, ddcHyxhBase);
					AjaxUtil.rendJson(response, true, "验证通过");
				}

			}
		}
		if (role.equals(SystemConstants.ROLE_SSDW)) {
			DdcHyxhSsdw ddcHyxhSsdw = iUserService.getSsdwByUserCode(cuser);
			if (ddcHyxhSsdw == null
					|| !ddcHyxhSsdw.getPassWord().equals(cpassword)) {

				AjaxUtil.rendJson(response, false, "用户名或密码错误！");
				return;
			} else {
				SingleOnline.addUser(cuser, request.getSession().getId());
				if (!SingleOnline.isValidUser(cuser, request.getSession()
						.getId())) {
					request.getSession().invalidate();
					SingleOnline.removeValidUser(cuser);
					AjaxUtil.rendJson(response, false,
							"您的账户在其它地方登录，对方已被踢掉请重新登录");
				} else {
					request.getSession().removeAttribute(
							SystemConstants.SESSION_USER);
					request.getSession().setAttribute(
							SystemConstants.SESSION_USER, ddcHyxhSsdw);
					request.getSession().setAttribute(
							SystemConstants.SESSION_USER_NAME,
							ddcHyxhSsdw.getUserCode());
					AjaxUtil.rendJson(response, true, "验证通过");
				}

			}
		}

	}

	/**
	 * 
	 * 方法描述：登录到首页
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月2日 上午9:00:35
	 */
	@RequestMapping("/loginToMain")
	public String loginToMain() {
		return "main";

	}

	/**
	 * 
	 * 方法描述：退出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月31日 上午11:29:26
	 */
	@RequestMapping("/loginout")
	public String loginout(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute(SystemConstants.SESSION_USER);
		request.getSession().invalidate();
		PrintWriter out = response.getWriter();
		out.println("<script>window.parent.location.replace('"
				+ request.getContextPath() + "/userAction/index')</script>");
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 
	 * 方法描述：修改密码界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月31日 上午11:33:43
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(HttpServletRequest request,
			HttpServletResponse response) {
		Object object = request.getSession().getAttribute(
				SystemConstants.SESSION_USER);
		if (object.getClass().getSimpleName()
				.equals(SystemConstants.CLASS_NAME_DDC_HYXHBASE)) {
			DdcHyxhBase ba = (DdcHyxhBase) request.getSession().getAttribute(
					SystemConstants.SESSION_USER);
			DdcHyxhBase ddcHyxhBase = iUserService.getById(ba.getId());
			if (ddcHyxhBase != null) {
				request.setAttribute("ddcHyxhBase", ddcHyxhBase);
			}
		} else {
			DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) request.getSession()
					.getAttribute(SystemConstants.SESSION_USER);

			if (ddcHyxhSsdw != null) {
				DdcHyxhSsdw newDdcHyxhSsdw = iCompanyService
						.queryInfoById(ddcHyxhSsdw.getId());
				String showImg = parseUrl(newDdcHyxhSsdw.getVcPicPath());
				newDdcHyxhSsdw.setVcShowPath(showImg);
				if (ddcHyxhSsdw.getShFlag() == 0) {
					request.setAttribute("message", "您的帐号还没通过审核，请先完善资料!");
				}
				request.setAttribute("ddcHyxhSsdw", newDdcHyxhSsdw);
			}
		}

		return "main/modifyUser";
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
	 * 方法描述：修改行业协会
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月31日 下午12:13:09
	 * @throws IOException
	 */
	@RequestMapping("/saveModifyUser")
	public void saveModifyUser(HttpServletRequest request, String id,
			String userPassword, HttpServletResponse response)
			throws IOException {
		try {
			if (StringUtils.isNotBlank(userPassword)) {
				DdcHyxhBase ddcHyxhBase = iUserService.getById(Long
						.parseLong(id));
				ddcHyxhBase.setHyxhmm(userPassword.trim());
				iUserService.update(ddcHyxhBase);
				AjaxUtil.rendJson(response, true, "修改成功");

			} else {
				AjaxUtil.rendJson(response, false, "密码不能为空格或无效字符串");
			}

		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "失败！");
		}
	}

	/**
	 * 
	 * 方法描述：修改单位信息
	 * 
	 * @param request
	 * @param ddcHyxhSsdw
	 * @param response
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月9日 下午2:40:10
	 */
	@RequestMapping("/saveModifySSdw")
	public void saveModifySSdw(
			HttpServletRequest request,
			@RequestParam(value = "file_upload", required = false) MultipartFile fileupload,
			DdcHyxhSsdw ddcHyxhSsdw, HttpServletResponse response) {
		try {
			if (StringUtils.isNotBlank(ddcHyxhSsdw.getPassWord())) {
				ddcHyxhSsdw.setSqrq(new Date());
				ddcHyxhSsdw.setZt(SystemConstants.ENABLE_ZT);
				String licenseImg = uploadImg(request, fileupload);// 上传车身照片
				if (StringUtils.isNotBlank(licenseImg)) {
					ddcHyxhSsdw.setVcPicPath(licenseImg);
				} else {
					ddcHyxhSsdw.setVcPicPath(ddcHyxhSsdw.getVcPicPath());
				}
				ddcHyxhSsdw.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
				ddcHyxhSsdw.setTranDate(new Date());
				iCompanyService.update(ddcHyxhSsdw);
				AjaxUtil.rendJson(response, true, "成功");
			} else {
				AjaxUtil.rendJson(response, false, "密码不能为空格或才无效字符串");
			}

		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "失败！");
		}
	}

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
			int w = SystemConstants.IMG_LICENCE_WITH, h = SystemConstants.IMG_LICENCE_HEIGHT;
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
	 * 
	 * 方法描述：导出excel
	 * 
	 * @param request
	 * @param response
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月2日 下午5:53:39
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response) {
		response.reset();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String hf = request.getParameter("hfs");
		String type = request.getParameter("type");
		String exportname = "grid";
		try {
			if (type.equals("excel")) {
				exportname += ".xls";
				response.setHeader(
						"Content-disposition",
						"attachment; filename="
								+ java.net.URLEncoder.encode(exportname,
										"UTF-8") + "");
				response.setContentType("application/msexcel;charset=utf-8");
			} else if (type.equals("word")) {
				exportname += ".doc";
				response.setHeader(
						"Content-disposition",
						"attachment; filename="
								+ java.net.URLEncoder.encode(exportname,
										"UTF-8") + "");
				response.setContentType("application/ms-word;charset=UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(hf);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String generateFileName(String fileName) {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String formatDate = format.format(new Date());
		int random = new Random().nextInt(10000);
		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);
		return formatDate + random + extension;
	}

	@RequestMapping("/export")
	public String export() {
		return "apply/export";
	}

}

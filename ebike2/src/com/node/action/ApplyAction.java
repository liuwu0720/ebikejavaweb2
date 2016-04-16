/**
 * 文件名：ApplyAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月14日
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.node.model.DdcApproveUser;
import com.node.model.DdcHyxhBasb;
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
import com.node.util.ScaleImage;
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
	 * 方法描述：配额申报
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月19日 上午9:48:46
	 */
	@RequestMapping("/applyQtys")
	public String applyQtys(HttpServletRequest request) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		request.setAttribute("ddcHyxhBase", ddcHyxhBase);
		return "apply/ebikeQtys";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月19日 上午10:29:57
	 * @throws ParseException
	 */
	@RequestMapping("/queryAllQtys")
	@ResponseBody
	public Map<String, Object> queryAllQtys(HttpServletRequest request,
			String dtend, String dtstart) throws ParseException {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		Page p = ServiceUtil.getcurrPage(request);
		HqlHelper hql = new HqlHelper(DdcHyxhBasb.class);
		hql.addEqual("hyxhzh", ddcHyxhBase.getHyxhzh());
		if (StringUtils.isNotBlank(dtstart)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			hql.addGreatThan("sqrq", sdf.parse(dtstart));
		}
		if (StringUtils.isNotBlank(dtend)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			hql.addLessThan("sqrq", sdf.parse(dtend));
		}

		hql.setQueryPage(p);
		Map<String, Object> resultMap = iCompanyService.queryByHql(hql);

		return resultMap;
	}

	/**
	 * 
	 * 方法描述：配额申请保存
	 * 
	 * @param request
	 * @param bz
	 * @param id
	 * @param hyxhsqpe
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月19日 上午11:35:43
	 */
	@RequestMapping("/saveOrUpdateQty")
	public void saveOrUpdateQty(HttpServletRequest request,
			HttpServletResponse response, String bz, String hyxhsqpe) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		DdcHyxhBasb ddcHyxhBasb = new DdcHyxhBasb();
		// 生成流水号
		String sql = "select SEQ_HYXH_SSDWCLSB_XH.nextval from dual";
		Object object = iApplyService.getDateBySQL(sql);
		String seq = object.toString();
		String md = new SimpleDateFormat("yyMMdd").format(new Date());
		ddcHyxhBasb.setLsh("F" + md + seq);
		ddcHyxhBasb.setBz(bz);
		ddcHyxhBasb.setSqr(ddcHyxhBase.getHyxhzh());
		ddcHyxhBasb.setSqrq(new Date());
		ddcHyxhBasb.setHyxhsqpe(Integer.parseInt(hyxhsqpe));
		ddcHyxhBasb.setHyxhzh(ddcHyxhBase.getHyxhzh());
		ddcHyxhBasb.setHyxhmc(ddcHyxhBase.getHyxhmc());
		try {

			iApplyService.saveDdcHyxhBasb(ddcHyxhBasb);
			AjaxUtil.rendJson(response, true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "操作失败！系统错误");
		}
	}

	/**
	 * 
	 * 方法描述：查询配额申报详情
	 * 
	 * @param response
	 * @param request
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月19日 下午2:29:22
	 */
	@RequestMapping("/queryDdcHyxhBasbById")
	@ResponseBody
	public DdcHyxhBasb queryDdcHyxhBasbById(HttpServletResponse response,
			HttpServletRequest request, String id) {
		long sbId = Long.parseLong(id);
		DdcHyxhBasb ddcHyxhBasb = iApplyService.getDdcHyxhBasbById(sbId);
		return ddcHyxhBasb;
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
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request, String zt,
			String xsqy, String jsrxm1, String ssdw, String djh,
			String dtstart, String dtend) throws ParseException {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		Page p = ServiceUtil.getcurrPage(request);
		String sql = "select s.id,s.lsh,s.ppxh,(select d.DMMS1 from ddc_sjzd d where d.dmz = s.cysy and d.dmlb='CSYS' and rownum=1)as csysname,s.ssdwid,"
				+ " (select dw.DWMC from  DDC_HYXH_SSDW dw where dw.id = s.SSDWID and rownum = 1) as SSDWNAME,"
				+ "s.JSRXM1,s.djh,(select  d.DMMS1 from ddc_sjzd d where d.dmz = s.xsqy and d.dmlb='SSQY' and rownum=1 )as xsqyname ,s.sqrq,s.sl_index,s.slyj "
				+ "from DDC_HYXH_SSDWCLSB s where 1=1 and s.ENABLE = 1";
		sql += " and s.HYXHZH = '" + ddcHyxhBase.getHyxhzh() + "'";
		if (StringUtils.isNotBlank(zt)) {
			if (zt.equals("0") || zt.equals("1")) {
				sql += " and s.slyj =" + Integer.parseInt(zt);
			} else {
				sql += " and s.SLYJ is null ";
			}

		}
		if (StringUtils.isNotBlank(ssdw)) {
			sql += " and s.SSDWID = " + ssdw;
		}
		if (StringUtils.isNotBlank(dtstart)) {
			sql += " and s.SQRQ > to_date('" + dtstart + "','yyyy-MM-dd')";
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

		Map<String, Object> map = iCompanyService.getBySpringSql(sql, p);
		return map;

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
		String message = iApplyService.findHmd(ddcHyxhSsdwclsb.getJsrxm1(),
				ddcHyxhSsdwclsb.getJsrxm2());
		if (!message.equals("success")) {
			AjaxUtil.rendJson(response, false, message);
			return;
		}
		/**
		 * 新增时检查单位配额
		 */
		DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(Long
				.parseLong(ddcHyxhSsdwclsb.getSsdwId()));
		if (ddcHyxhSsdwclsb.getId() == null) {
			if (ddcHyxhSsdw.getDwpe() <= 0) {
				AjaxUtil.rendJson(response, false, "该单位配额已用完，不能再申报");
				return;
			} else {
				ddcHyxhSsdw.setDwpe(ddcHyxhSsdw.getDwpe() - 1);
			}
		}

		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
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
				.getAttribute(SystemConstants.SESSION_USER);
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
			int w = 800, h = 600;
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
	 * 方法描述：批量审核
	 * 
	 * @param request
	 * @param response
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 上午10:37:57
	 */
	@RequestMapping(value = "/changeRowData", method = RequestMethod.POST)
	public void changeRowData(HttpServletRequest request,
			HttpServletResponse response) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		String[] ids = request.getParameterValues("array[]");
		System.out.println("ids = " + ids);
		try {
			for (int i = 0; i < ids.length; i++) {
				long id = Long.parseLong(ids[i]);
				DdcHyxhSsdwclsb ddcHyxhSsdwclsb = iApplyService
						.getDdcHyxhSsdwclsbById(id);
				if (StringUtils.isBlank(ddcHyxhSsdwclsb.getSynFlag())) {
					ddcHyxhSsdwclsb.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
				}
				ddcHyxhSsdwclsb.setSlIndex(1);
				iApplyService.updateDdcHyxhSsdwclsb(ddcHyxhSsdwclsb);
				DdcApproveUser ddcApproveUser = new DdcApproveUser();
				ddcApproveUser.setApproveIndex(1);
				ddcApproveUser.setApproveTable(SystemConstants.RECORDSBTABLE);
				ddcApproveUser.setApproveTableid(ddcHyxhSsdwclsb.getId());
				ddcApproveUser.setApproveTime(new Date());
				ddcApproveUser.setUserName(ddcHyxhBase.getHyxhmc());
				ddcApproveUser.setUserRoleName("行业协会");
				iApplyService.saveDdcApproveUser(ddcApproveUser);
			}
			AjaxUtil.rendJson(response, true, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "系统错误");
		}

	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @param response
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午1:31:33
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	@ResponseBody
	public String exportExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String[] ids = request.getParameterValues("array[]");

		List<DdcHyxhSsdwclsb> ddcHyxhSsdwclsbs = new ArrayList<DdcHyxhSsdwclsb>();
		for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i]);
			DdcHyxhSsdwclsb ddcHyxhSsdwclsb = iApplyService
					.getDdcHyxhSsdwclsbById(id);
			ddcHyxhSsdwclsbs.add(ddcHyxhSsdwclsb);
		}

		String filename = "ebike" + UUID.randomUUID();
		String basePath = request.getSession().getServletContext()
				.getRealPath("/static/downexcel");
		String basePath2 = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/";
		String filepath = filename + ".xls";
		// String filepath2 = filename + ".xls";
		String cpath = basePath2 + "static/downexcel/" + filepath;
		try {
			// 写入excel
			String path = basePath + "/" + filepath;
			System.out.println("path = " + path);
			File fileWrite = new File(path);
			WritableWorkbook wwb = null;
			fileWrite.createNewFile();
			wwb = Workbook.createWorkbook(fileWrite);
			WritableSheet ws = wwb.createSheet("Sheet1", 0);

			// //////////
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 15,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);

			// WritableFont wfc1 = new WritableFont(WritableFont.ARIAL, 10,
			// WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
			// Colour.RED);
			WritableFont wfc2 = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			WritableCellFormat wcfFC = new WritableCellFormat(wfc);
			// wcfFC.setBackground(Colour.DARK_YELLOW); 背景
			// WritableCellFormat wcfFC1 = new WritableCellFormat(wfc1);
			WritableCellFormat wcfFC2 = new WritableCellFormat(wfc2);

			// wcfFC.setWrap(true);//自动换行
			wcfFC.setAlignment(Alignment.CENTRE);// 把水平对齐方式指定为居中
			wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);// 把垂直对齐方式指定为居中
			// //////////

			Label label = new Label(0, 0, "车辆申报流水报表", wcfFC);
			ws.mergeCells(0, 0, 6, 0);
			ws.addCell(label);
			Label label1 = new Label(0, 2, "序号", wcfFC2);
			ws.addCell(label1);
			Label label2 = new Label(1, 2, "流水号", wcfFC2);
			ws.addCell(label2);
			Label label3 = new Label(2, 2, "品牌型号", wcfFC2);
			ws.addCell(label3);
			Label label4 = new Label(3, 2, "车身颜色", wcfFC2);
			ws.addCell(label4);
			Label label5 = new Label(4, 2, "电机号", wcfFC2);
			ws.addCell(label5);
			Label label6 = new Label(5, 2, "行驶区域", wcfFC2);
			ws.addCell(label6);
			Label label7 = new Label(6, 2, "驾驶人", wcfFC2);
			ws.addCell(label7);
			Label label8 = new Label(7, 2, "申报单位", wcfFC2);
			ws.addCell(label8);
			Label label9 = new Label(8, 2, "申报时间", wcfFC2);
			ws.addCell(label9);
			Label label10 = new Label(9, 2, "状态", wcfFC2);
			ws.addCell(label10);
			int i = 3;
			int j = 1;
			for (DdcHyxhSsdwclsb dhsc : ddcHyxhSsdwclsbs) {
				Label labelC8 = new Label(0, i, j + "");
				ws.addCell(labelC8);
				Label labelC0 = new Label(1, i, dhsc.getLsh());
				ws.addCell(labelC0);
				Label labelC1 = new Label(2, i, dhsc.getPpxh());
				ws.addCell(labelC1);
				Label labelC2 = new Label(3, i, dhsc.getCysyName());
				ws.addCell(labelC2);
				Label labelC3 = new Label(4, i, dhsc.getDjh());
				ws.addCell(labelC3);
				Label labelC4 = new Label(5, i, dhsc.getXsqyName());
				ws.addCell(labelC4);
				Label labelC5 = new Label(6, i, dhsc.getJsrxm1());
				ws.addCell(labelC5);
				Label labelC6 = new Label(7, i, dhsc.getSsdwName());
				ws.addCell(labelC6);
				Label labelC7 = new Label(8, i, dhsc.getSqrq().toString());
				ws.addCell(labelC7);
				Label labelC9 = new Label(9, i, dhsc.getNote());
				ws.addCell(labelC9);
				i++;
				j++;
			}

			wwb.write();
			wwb.close();
			response.getWriter().print(cpath);
		} catch (Exception e) {
			e.printStackTrace();
			// String jsonString = JsonUtil.string2json("2");
			// response.setContentType("text/xml; charset=UTF-8");
			// response.setHeader("Cache-Control", "no-cache");
			// response.setDateHeader("Expires", 0);
			response.getWriter().print("2");
		}
		return null;

	}

	/**
	 * 
	 * 方法描述：查看审批的详情
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月2日 上午10:22:06
	 */
	@RequestMapping("/queryRecordApprovalInfoById")
	public String queryRecordApprovalInfoById(HttpServletRequest request,
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
		ddcHyxhSsdwclsb.setVcShowEbikeImg(showEbikeImg);
		ddcHyxhSsdwclsb.setVcShowUser1Img(showUser1Img);
		ddcHyxhSsdwclsb.setVcShowUser2Img(showUser2Img);
		ddcHyxhSsdwclsb.setVcUser1CardImg1Show(vcUser1CardImg1Show);
		ddcHyxhSsdwclsb.setVcUser1CardImg2Show(vcUser1CardImg2Show);
		ddcHyxhSsdwclsb.setVcUser2CardImg1Show(vcUser2CardImg1Show);
		ddcHyxhSsdwclsb.setVcUser2CardImg2Show(vcUser2CardImg2Show);
		ddcHyxhSsdwclsb.setVcEbikeInvoiceImgShow(vcEbikeInvoiceImgShow);
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
		return "apply/recordDetail";
	}

	/**
	 * 
	 * 方法描述：跳转查看配额申报的详情页面
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月2日 下午2:14:37
	 */
	@RequestMapping("/queryDdcHyxhBasbDetailById")
	public String queryDdcHyxhBasbDetailById(HttpServletRequest request,
			String id) {
		long dId = Long.parseLong(id);
		DdcHyxhBasb ddcHyxhBasb = iApplyService.getDdcHyxhBasbById(dId);
		String approveTableName = SystemConstants.PESBTABLE;
		List<DdcApproveUser> approveUsers = iApplyService
				.findApproveUsersByProperties(approveTableName,
						ddcHyxhBasb.getId());
		request.setAttribute("approveUsers", approveUsers);
		request.setAttribute("ddcHyxhBasb", ddcHyxhBasb);
		return "apply/qtyApplyDetail";
	}

}

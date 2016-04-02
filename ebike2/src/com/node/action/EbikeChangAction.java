/**
 * 文件名：EbikeChangAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月16日
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

import com.node.model.DdcDaxxb;
import com.node.model.DdcDaxxbLog;
import com.node.model.DdcFlow;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.DdcSjzd;
import com.node.model.PicPath;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
import com.node.service.IEbikeService;
import com.node.util.AjaxUtil;
import com.node.util.Page;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;

/**
 * 类描述：电动车变更管理
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月16日 下午4:00:17
 */
@Controller
@RequestMapping("/ebikeChangeAction")
public class EbikeChangAction {

	@Autowired
	IEbikeService iEbikeService;
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
	 * @version: 2016年3月16日 下午4:01:17
	 */
	@RequestMapping("/getAll")
	public String getAll(HttpServletRequest request) {
		List<DdcSjzd> ywyys = iApplyService.getSjzdByDmlb("YWYY_D");// 业务原因
		List<DdcSjzd> slzls = iApplyService.getSjzdByDmlb("ZXSQZL");// 注销资料
		request.setAttribute("slzls", slzls);
		request.setAttribute("ywyys", ywyys);
		return "ebike/ebikeInfos";
	}

	/**
	 * 
	 * 方法描述：查看详情页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月18日 上午9:53:42
	 */
	@RequestMapping("/queryInfoById")
	@ResponseBody
	public DdcDaxxb queryInfoById(String id, HttpServletRequest request) {
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
		if (StringUtils.isNotBlank(ddcDaxxb.getZzjgdmzh())) {
			DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(Long
					.parseLong(ddcDaxxb.getZzjgdmzh()));
			if (ddcHyxhSsdw != null) {
				ddcDaxxb.setZzjgdmzhName(ddcHyxhSsdw.getDwmc());
			} else {
				ddcDaxxb.setZzjgdmzhName(null);
			}
		}
		// 业务类型
		String ywlxName = iApplyService.findByProPerties("YWLX",
				ddcDaxxb.getYwlx());
		ddcDaxxb.setYwlxName(ywlxName);
		// 业务原因
		String ywyyName = iApplyService.findByProPerties("YWYY_A",
				ddcDaxxb.getYwyy());
		ddcDaxxb.setYwyyName(ywyyName);

		String showEbikeImg = parseUrl(ddcDaxxb.getVcEbikeImg());
		String showUser1Img = parseUrl(ddcDaxxb.getVcUser1Img());
		String showUser2Img = parseUrl(ddcDaxxb.getVcUser2Img());
		ddcDaxxb.setVcShowEbikeImg(showEbikeImg);
		ddcDaxxb.setVcShowUser1Img(showUser1Img);
		ddcDaxxb.setVcShowUser2Img(showUser2Img);
		return ddcDaxxb;
	}

	/**
	 * 
	 * 方法描述：跳转至变更详情页面
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 上午11:32:11
	 */
	@RequestMapping("/changeInfo")
	public String changeInfo(String id, HttpServletRequest request) {
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
		if (StringUtils.isNotBlank(ddcDaxxb.getZzjgdmzh())) {
			DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.queryInfoById(Long
					.parseLong(ddcDaxxb.getZzjgdmzh()));
			if (ddcHyxhSsdw != null) {
				ddcDaxxb.setZzjgdmzhName(ddcHyxhSsdw.getDwmc());
			} else {
				ddcDaxxb.setZzjgdmzhName(null);
			}
		}
		// 业务类型
		String ywlxName = iApplyService.findByProPerties("YWLX",
				ddcDaxxb.getYwlx());
		ddcDaxxb.setYwlxName(ywlxName);
		// 业务原因
		String ywyyName = iApplyService.findByProPerties("YWYY_A",
				ddcDaxxb.getYwyy());
		ddcDaxxb.setYwyyName(ywyyName);

		String showEbikeImg = parseUrl(ddcDaxxb.getVcEbikeImg());
		String showUser1Img = parseUrl(ddcDaxxb.getVcUser1Img());
		String showUser2Img = parseUrl(ddcDaxxb.getVcUser2Img());
		ddcDaxxb.setVcShowEbikeImg(showEbikeImg);
		ddcDaxxb.setVcShowUser1Img(showUser1Img);
		ddcDaxxb.setVcShowUser2Img(showUser2Img);
		// 获取数据字典
		List<DdcSjzd> colorsSjzds = iApplyService.getSjzdByDmlb("CSYS");// 车身颜色
		List<DdcSjzd> bgDataSjzds = iApplyService.getSjzdByDmlb("BGSQZL");// 变更申请资料
		List<DdcSjzd> ssqySjzds = iApplyService.getSjzdByDmlb("SSQY");// 所属区域
		List<DdcSjzd> gdDataSjzds = iApplyService.getSjzdByDmlb("BASQZL");// 备案申请资料\归档资料退
		request.setAttribute("colorsSjzds", colorsSjzds);
		request.setAttribute("bgDataSjzds", bgDataSjzds);
		request.setAttribute("ssqySjzds", ssqySjzds);
		request.setAttribute("gdDataSjzds", gdDataSjzds);
		request.setAttribute("ddcDaxxb", ddcDaxxb);
		return "ebike/changeInfos";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param response
	 * @param dabh
	 * @param cphm
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月16日 下午5:02:52
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletResponse response,
			String djh, String dabh, String cphm, HttpServletRequest request) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute("ddcHyxhBase");
		Page p = ServiceUtil.getcurrPage(request);

		String sql = "select A.ID,A.DABH,A.CPHM,A.DJH,A.SLRQ,(SELECT S.DWMC FROM DDC_HYXH_SSDW S WHERE S.ID=A.ZZJGDMZH ) AS DWMC, "
				+ "(SELECT D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.ZT AND D.DMLB='CLZT')AS ZT from DDC_DAXXB A WHERE A.HYXHZH='"
				+ ddcHyxhBase.getHyxhzh() + "'  ";
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
		sql += "  order by A.ID DESC";

		Map<String, Object> resultMap = iEbikeService.queryBySpringSql(sql, p);

		return resultMap;
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
	 * 方法描述：资料变更
	 * 
	 * @param daxxb
	 * @param request
	 * @param response
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 下午5:59:43
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/changeData")
	public void changeData(
			DdcDaxxb daxxb,
			HttpServletRequest request,
			@RequestParam(value = "file_upload", required = false) MultipartFile fileupload,
			@RequestParam(value = "file_upload1", required = false) MultipartFile file_upload1,
			@RequestParam(value = "file_upload2", required = false) MultipartFile file_upload2,
			HttpServletResponse response) throws FileNotFoundException,
			IOException {
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
		String message = iApplyService.findHmd(daxxb.getJsrxm1(),
				daxxb.getJsrxm2());
		if (!message.equals("success")) {
			AjaxUtil.rendJson(response, false, message);
			return;
		}

		String slzls = request.getParameter("slzllist");// 重组变更资料字符串
		long daId = daxxb.getId();
		DdcDaxxb newDaxxb = iEbikeService.getById(daId);
		newDaxxb.setSlzl(slzls);
		newDaxxb.setJsrxm1(daxxb.getJsrxm1());
		newDaxxb.setJsrxm2(daxxb.getJsrxm2());
		newDaxxb.setXb1(daxxb.getXb1());
		newDaxxb.setXb2(daxxb.getXb2());
		newDaxxb.setLxdh1(daxxb.getLxdh1());
		newDaxxb.setLxdh2(daxxb.getLxdh2());
		newDaxxb.setSfzmhm1(daxxb.getSfzmhm1());
		newDaxxb.setSfzmhm2(daxxb.getSfzmhm2());
		newDaxxb.setSynFlag("UC");
		String ebike_jpgPath = uploadImg(request, fileupload);// 上传车身照片
		if (StringUtils.isNotBlank(ebike_jpgPath)) {
			newDaxxb.setVcEbikeImg(ebike_jpgPath);
		} else {
			newDaxxb.setVcEbikeImg(daxxb.getVcEbikeImg());
		}

		String vcUser1_img = uploadImg(request, file_upload1);// 上传驾驶人1照片
		if (StringUtils.isNotBlank(vcUser1_img)) {
			newDaxxb.setVcUser1Img(vcUser1_img);
		} else {
			newDaxxb.setVcUser1Img(daxxb.getVcUser1Img());
		}

		String vcUser2_img = uploadImg(request, file_upload2);// 上传驾驶人2照片
		if (StringUtils.isNotBlank(vcUser2_img)) {
			newDaxxb.setVcUser2Img(vcUser2_img);
		} else {
			newDaxxb.setVcUser2Img(daxxb.getVcUser2Img());
		}
		try {
			String type = "B";
			saveDdcFlow(type, newDaxxb, slzls, null);

			// 保存日志
			saveDaxxblog(newDaxxb, request);

			iEbikeService.update(newDaxxb);
			AjaxUtil.rendJson(response, true, "操作成功");
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
			String newYwyy) throws IllegalAccessException,
			InvocationTargetException {
		// TODO Auto-generated method stub
		DdcFlow ddcFlow = new DdcFlow();
		BeanUtils.copyProperties(ddcFlow, newDaxxb);
		// 生成流水号
		String sql = "select seq_ddl_flow.nextval from dual";
		Object object = iApplyService.getDateBySQL(sql);
		String seq = object.toString();
		String md = new SimpleDateFormat("yyMMdd").format(new Date());
		String lsh = ywlxType + md + seq;// 生成流水表流水号
		ddcFlow.setLsh(lsh);
		ddcFlow.setYwlx(ywlxType);// A-管好 B-变更 C-转移 D-注销 E-检查
		ddcFlow.setSlrq(new Date());
		ddcFlow.setId(null);
		ddcFlow.setYwyy(newYwyy);
		iEbikeService.saveDdcFlow(ddcFlow);
	}

	/**
	 * 方法描述：
	 * 
	 * @param newDaxxb
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 下午7:58:00
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private void saveDaxxblog(DdcDaxxb newDaxxb, HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
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
		DdcDaxxbLog daxxbLog = new DdcDaxxbLog();
		BeanUtils.copyProperties(daxxbLog, newDaxxb);
		daxxbLog.setId(null);
		daxxbLog.setCznr("变更 ; ip=" + ip);
		daxxbLog.setCzbm(ddcHyxhBase.getHyxhmc());
		iEbikeService.saveDdcDaxxbLog(daxxbLog);
	}

	/**
	 * 方法描述：
	 * 
	 * @param request
	 * @param fileupload
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月17日 下午7:48:09
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
			String newXsqy, HttpServletResponse response)
			throws FileNotFoundException, IOException {

		String slzls = request.getParameter("slzllist");// 重组变更资料字符串

		long daId = daxxb.getId();
		DdcDaxxb newDaxxb = iEbikeService.getById(daId);
		newDaxxb.setXsqy(newXsqy);
		newDaxxb.setSynFlag("UC");
		newDaxxb.setTranFlag(null);
		newDaxxb.setTranDate(null);

		try {
			String type = "C";
			saveDdcFlow(type, newDaxxb, slzls, null);

			// 保存日志
			saveDaxxblog(newDaxxb, request);

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
		daxxb.setZt("E");
		daxxb.setSynFlag("UC");
		daxxb.setTranFlag(null);
		daxxb.setTranDate(null);
		daxxb.setSlbz(slbz);
		try {
			saveDaxxblog(daxxb, request);
			String type = "D";
			saveDdcFlow(type, daxxb, newSlzl, newYwyy);
			iEbikeService.update(daxxb);
			AjaxUtil.rendJson(response, true, "操作成功！");
		} catch (Exception e) {
			AjaxUtil.rendJson(response, false, "操作失败！系统错误");
		}

	}

}

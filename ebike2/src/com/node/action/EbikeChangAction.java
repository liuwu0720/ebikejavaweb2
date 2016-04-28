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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.node.model.DdcApproveUser;
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
 * 类描述：电动车变更审批
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月16日 下午4:00:17
 */
@Controller
@RequestMapping("/ebikeChangeAction")
@ApiIgnore
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
	 * 方法描述：档案详情
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月7日 上午9:47:39
	 */
	@RequestMapping("/queryDetailById")
	public String queryDetailById(String id, HttpServletRequest request) {
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
		request.setAttribute("ddcDaxxb", ddcDaxxb);
		request.setAttribute("selectSlzls", selectSlzls);
		return "ebike/ebikeDaInfo";
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
		// 受理资料
		List<DdcSjzd> selectSlzls = iApplyService.getDbyyList(
				ddcDaxxb.getSlzl(), "BASQZL");
		request.setAttribute("colorsSjzds", colorsSjzds);
		request.setAttribute("bgDataSjzds", bgDataSjzds);
		request.setAttribute("ssqySjzds", ssqySjzds);
		request.setAttribute("selectSlzls", selectSlzls);
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
	public Map<String, Object> queryAll(HttpServletRequest request, String djh,
			String lsh, String cphm, String jsrxm1, String dabh, String ywlx,
			String slyj, String xsqy, HttpServletResponse response) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		Page p = ServiceUtil.getcurrPage(request);

		String sql = "select t.id, t.ywlx, t.lsh,t.dabh,t.cphm,t.ppxh,t.SL_INDEX ,"
				+ "(select d.DMMS1 from ddc_sjzd d where d.dmz = t.YWLX and d.dmlb='YWLX' and rownum=1)as YWLXNAME,"
				+ "t.djh,t.jsrxm1,t.xsqy,(SELECT D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=t.xsqy AND D.DMLB='SSQY') as XSQYNAME,t.slyj,"
				+ "t.SSDWID, (SELECT S.DWMC FROM DDC_HYXH_SSDW S WHERE S.ID=t.SSDWID)as ssdwname ,t.slrq from DDC_FLOW t where t.ywlx !='A' and t.hyxhzh ='"
				+ ddcHyxhBase.getHyxhzh() + "'  ";
		// 流水号
		if (StringUtils.isNotBlank(lsh)) {
			sql += " and t.lsh like '%" + lsh + "%'";
		}

		// 电机号
		if (StringUtils.isNotBlank(djh)) {
			sql += " and t.djh like '%" + djh + "%'";
		}
		// 档案编号
		if (StringUtils.isNotBlank(dabh)) {
			sql += " and t.dabh like '%" + dabh + "%'";
		}
		// 车牌号
		if (StringUtils.isNotBlank(cphm)) {
			sql += " and t.sfzhm1 like '%" + cphm + "%'";
		}
		// 驾驶人
		if (StringUtils.isNotBlank(jsrxm1)) {
			sql += " and t.JSRXM1 like '%" + jsrxm1 + "%'";
		}
		if (StringUtils.isNotBlank(ywlx)) {
			sql += " and t.YWLX = '" + ywlx + "'";
		}
		if (StringUtils.isNotBlank(slyj)) {
			sql += " and t.SLYJ = '" + slyj + "'";
		}
		if (StringUtils.isNotBlank(xsqy)) {
			sql += " and t.XSQY = '" + xsqy + "'";
		}

		sql += "  order by t.slrq DESC";

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
		ddcFlow.setSlzl(slzls);
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
				.getAttribute(SystemConstants.SESSION_USER);
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
	 * 方法描述：协会审批 注销、转移、变更
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @param note
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月13日 下午7:37:45
	 */
	@RequestMapping("/sureState")
	public void sureState(HttpServletRequest request,
			HttpServletResponse response, String id, String note, String state) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute(SystemConstants.SESSION_USER);
		DdcFlow ddcFlow = iApplyService.getDdcFlowById(Long.parseLong(id));

		if (!ddcFlow.getYwlx().equals("D")) {
			ddcFlow.setSlyj(state);
			ddcFlow.setGdrq(new Date());
			ddcFlow.setGdbz(note);
			ddcFlow.setGdyj(state);
		}
		DdcApproveUser ddcApproveUser = new DdcApproveUser();
		ddcApproveUser.setApproveIndex(1);
		String sql = "select SEQ_DDC_APPROVE_USER.nextval from dual";
		Object object = iApplyService.getDateBySQL(sql);
		String seq = object.toString();
		String md = new SimpleDateFormat("yyMMdd").format(new Date());
		String approveNo = "W" + md + seq;// 生成审批号
		ddcApproveUser.setApproveNo(approveNo);
		ddcApproveUser.setApproveNote(note);
		ddcApproveUser.setApproveState(Integer.parseInt(state));
		ddcApproveUser.setApproveTable(SystemConstants.DDCFLOW_TABLE);
		ddcApproveUser.setApproveTableid(ddcFlow.getId());
		ddcApproveUser.setApproveTime(new Date());

		ddcApproveUser.setSysFlag(SystemConstants.SYSNFLAG_ADD);
		ddcApproveUser.setTranDate(new Date());
		ddcApproveUser.setUserRoleName("行业协会");
		ddcApproveUser.setUserName(ddcHyxhBase.getHyxhmc());
		ddcApproveUser.setLsh(ddcFlow.getLsh());
		if (state.equals("0")) {

			// 同意---注销 ，档案表受理意见改为受理中
			DdcDaxxb daxxb = iApplyService.getDdcDaxxbByDabh(ddcFlow.getDabh());
			if (daxxb != null) {
				daxxb.setSlyj(state);
				ddcFlow.setSlIndex(1);
				ddcFlow.setSynFlag(SystemConstants.SYSNFLAG_ADD);// 协会审批后才同步至内网
				ddcFlow.setTranDate(new Date());
				if (ddcFlow.getYwlx().equalsIgnoreCase("D")) {
					// 注销需要内网审批
					daxxb.setSlyj(null);
				}

				try {
					iApplyService.saveDdcApproveUser(ddcApproveUser);
					iEbikeService.update(daxxb);
					iEbikeService.updateDdcFlow(ddcFlow);

					AjaxUtil.rendJson(response, true, "操作成功");
				} catch (Exception e) {
					e.printStackTrace();
					AjaxUtil.rendJson(response, false, "系统错误！");
				}

			} else {
				AjaxUtil.rendJson(response, false, "档案编号【" + ddcFlow.getDabh()
						+ "】的档案信息被删除");
			}

		} else {
			// 拒绝--不需要提交至内网
			DdcDaxxb daxxb = iApplyService.getDdcDaxxbByDabh(ddcFlow.getDabh());
			if (daxxb != null) {
				daxxb.setSlyj(state);
				try {
					iEbikeService.update(daxxb);
					ddcFlow.setSlIndex(1);
					ddcFlow.setSlyj(state);
					iEbikeService.updateDdcFlow(ddcFlow);
					iApplyService.saveDdcApproveUser(ddcApproveUser);
					AjaxUtil.rendJson(response, true, "操作成功");
				} catch (Exception e) {
					e.printStackTrace();
					AjaxUtil.rendJson(response, false, "系统错误！");
				}

			} else {
				AjaxUtil.rendJson(response, false, "档案编号【" + ddcFlow.getDabh()
						+ "】的档案信息被删除");
			}

		}

	}
}

/**
 * 文件名：AppAction.java
 * 版本信息：Version 1.0
 * 日期：2016年4月22日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.node.model.DdcDaxxb;
import com.node.model.DdcDriver;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.PicPath;
import com.node.service.IApplyService;
import com.node.service.ICompanyService;
import com.node.service.IEbikeService;
import com.node.util.AjaxUtil;
import com.node.util.SystemConstants;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年4月22日 下午3:33:24
 */
@Controller
@RequestMapping("/appAction")
@Api(value = "appAction-api", description = "APP微信端相关接口", position = 5)
public class AppAction {
	@Autowired
	IApplyService iApplyService;

	@Autowired
	ICompanyService iCompanyService;

	@Autowired
	IEbikeService iEbikeService;

	@ApiIgnore
	@RequestMapping("/interface")
	public String getinterface() {
		return "data/apiIndex";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param dabh
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月22日 下午7:17:17
	 */
	@ApiOperation(value = "根据档案编号查询出车辆信息", notes = "二维码扫描档案编号返回的数据<br/>"
			+ "	private String dabh;// 档案编号4403 00000001<br/>"
			+ " private String cysyName;//车身颜色<br/>	"
			+ "private String djh;// 电机号<br/>	private String jtzz;// 脚踏装置（有、无）<br/>"
			+ "private String jsrxm1;// 驾驶人姓名1<br/>	 private String xb1;// 性别1<br/>"
			+ "private String sfzmhm1;// 身份证号码1<br/>private String lxdh1;// 联系电话1<br/>	"
			+ "private String jsrxm2;// 驾驶人姓名2	<br/>private String xb2;// 性别2<br/>"
			+ "	private String sfzmhm2;// 身份证号码2<br/>	private String lxdh2;// 联系电话2<br/>"
			+ "private String xsqyName;//行驶区域<br/>private String ztName;//车辆状态<br/>"
			+ "private String hyxhzhName;//协会名称<br/>private String ssdwName;//单位名称<br/>	"
			+ "private String vcShowEbikeImg;//车辆图片地址<br/>private String vcShowUser1Img;//驾驶人1图片地址<br/>"
			+ "private String vcShowUser2Img;//驾驶人2图片地址", position = 5)
	@RequestMapping(value = "/getEbikeInfoByDabh", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getEbikeInfoByDabh(
			@ApiParam(value = "档案编号", required = true) @RequestParam("dabh") String dabh) {
		DdcDaxxb ddcDaxxb = iApplyService.getDdcDaxxbByDabh(dabh);
		if (ddcDaxxb == null) {
			return AjaxUtil.getMapByNotException(false, null);
		} else {
			String cysyName = iApplyService.findByProPerties("CSYS",
					ddcDaxxb.getCysy());

			ddcDaxxb.setCysyName(cysyName);// 车身颜色
			String xsqyName = iApplyService.findByProPerties("SSQY",
					ddcDaxxb.getXsqy());
			ddcDaxxb.setXsqyName(xsqyName);// 所属区域

			String ztName = iApplyService.findByProPerties("CLZT",
					ddcDaxxb.getZt());
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
			DdcHyxhBase ddcHyxhBase = iCompanyService.getHyxhZhByCode(ddcDaxxb
					.getHyxhzh());
			ddcDaxxb.setHyxhzhName(ddcHyxhBase.getHyxhmc());
			String showUser1Img = parseUrl(ddcDaxxb.getVcUser1Img());
			String showUser2Img = parseUrl(ddcDaxxb.getVcUser2Img());

			ddcDaxxb.setVcShowUser1Img(showUser1Img);
			ddcDaxxb.setVcShowUser2Img(showUser2Img);
			return AjaxUtil.getMapByNotException(true, ddcDaxxb);
		}

	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param userCode
	 * @param passWord
	 * @param request
	 * @param response
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月23日 上午9:15:22
	 */
	@ApiOperation(value = "司机登录接口", notes = "司机关注公众号后，进行电动自行车证件的领用<br/>", position = 5)
	@RequestMapping(value = "/loginByDriver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginByDriver(
			@ApiParam(value = "帐号", required = true) @RequestParam("userCode") String userCode,
			@ApiParam(value = "密码", required = true) @RequestParam("passWord") String passWord,
			HttpServletRequest request, HttpServletResponse response) {
		List<DdcDriver> ddcDrivers = iEbikeService.findDriverByUserInfo(
				userCode, passWord);
		if (CollectionUtils.isNotEmpty(ddcDrivers)) {
			return AjaxUtil.getMapByNotException(true, ddcDrivers.get(0));
		} else {
			return AjaxUtil.getMapByNotException(false, null);
		}

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

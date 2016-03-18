/**
 * 文件名：EbikeWaterAction.java
 * 版本信息：Version 1.0
 * 日期：2016年3月18日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.node.model.DdcHyxhBase;
import com.node.service.IEbikeService;
import com.node.util.Page;
import com.node.util.ServiceUtil;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月18日 下午4:20:17
 */
@Controller
@RequestMapping("/ebikeWaterAction")
public class EbikeWaterAction {
	@Autowired
	IEbikeService iEbikeService;

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月18日 下午4:21:52
	 */
	@RequestMapping("/getAll")
	public String getAll() {
		return "ebike/ebikeflowList";
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月18日 下午4:23:25
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request) {
		DdcHyxhBase ddcHyxhBase = (DdcHyxhBase) request.getSession()
				.getAttribute("ddcHyxhBase");
		Page p = ServiceUtil.getcurrPage(request);

		String sql = "select A.ID,A.LSH,A.CPHM,A.DJH,A.SLRQ,a.SLYJ,(SELECT S.DWMC FROM DDC_HYXH_SSDW S WHERE S.ID=A.ZZJGDMZH ) AS DWMC,"
				+ " (select  D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.YWLX AND D.DMLB='YWLX') as YWLX ,"
				+ "  (select  D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.XSQY AND D.DMLB='SSQY') as SSQY ,"
				+ "(SELECT D.DMMS1 FROM DDC_SJZD D WHERE D.DMZ=A.YWLX AND D.DMLB='CLZT')AS ZT from DDC_FLOW A WHERE A.HYXHZH='"
				+ ddcHyxhBase.getHyxhzh() + "'  ";
		Map<String, Object> resultMap = iEbikeService.queryBySpringSql(sql, p);

		return resultMap;
	}
}

/**
  * 文件名：AdminAction.java
  * 版本信息：Version 1.0
  * 日期：2016年5月22日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.node.model.DdcHyxhBase;
import com.node.model.DdcHyxhSsdw;
import com.node.model.PicPath;
import com.node.service.ICompanyService;
import com.node.service.IEbikeService;
import com.node.util.AjaxUtil;
import com.node.util.HqlHelper;
import com.node.util.Page;
import com.node.util.ServiceUtil;
import com.node.util.SystemConstants;


/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年5月22日 下午1:10:52 
 */
@Controller
@RequestMapping("/adminAction")
@ApiIgnore
public class AdminAction {
	
	@Autowired
	ICompanyService iCompanyService;
	
	@Autowired
	IEbikeService iEbikeService;
	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月23日 下午5:04:21
	 */
	@RequestMapping("/getAllxhs")
	public String getAll() {
		return "admin/industrys";
	}

	@RequestMapping("/getAldws")
	public String getAllCompany() {
		return "admin/companys";
	}
	

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param code
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月29日 上午11:54:05
	 */
	@RequestMapping("/queryHxyHyxhBaseByCode")
	@ResponseBody
	public DdcHyxhBase queryHxyHyxhBaseByCode(String code) {
		DdcHyxhBase ddcHyxhBase = iCompanyService.getHyxhZhByCode(code);
		return ddcHyxhBase;
	}

	/**
	 * 
	 * 方法描述：查询所有行业协会
	 * 
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月23日 下午6:18:34
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request,
			String hyxhmc) {
		Page p = ServiceUtil.getcurrPage(request);
		HqlHelper hql = new HqlHelper(DdcHyxhBase.class);
		if (StringUtils.isNotBlank(hyxhmc)) {
			hql.addLike("hyxhmc", hyxhmc);
		}

		hql.addOrderBy("id", "desc");
		hql.setQueryPage(p);
		Map<String, Object> resultMap = iEbikeService.queryByHql(hql);
		return resultMap;
	}

	/**
	 * 
	 * 方法描述：查询所有行业协会所属单位
	 * 
	 * @param request
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月24日 上午10:39:22
	 */
	@RequestMapping("/queryAllCompany")
	@ResponseBody
	public Map<String, Object> queryAllCompany(HttpServletRequest request,
			String hyxhzh, String dwmc) {
		Page p = ServiceUtil.getcurrPage(request);
		HqlHelper hql = new HqlHelper(DdcHyxhSsdw.class);
		if(StringUtils.isNotBlank(hyxhzh)){
			hql.addEqual("hyxhzh", hyxhzh);
		}
		if(StringUtils.isNotBlank(dwmc)){
			hql.addLike("dwmc", dwmc);
		}
		
		
		hql.addOrderBy("id", "desc");
		hql.setQueryPage(p);
		
		
		Map<String, Object> resultMap = iCompanyService.queryByHql(hql);

		List<Map<String, Object>> mapList = (List<Map<String, Object>>) resultMap
				.get("rows");
		for (int i = 0; i < mapList.size(); i++) {
			DdcHyxhSsdw ddcHyxhSsdw = (DdcHyxhSsdw) mapList.get(i);
			DdcHyxhBase ddcHyxhBase = iCompanyService.getHyxhZhByCode(ddcHyxhSsdw.getHyxhzh());
			ddcHyxhSsdw.setHyxhzhName(ddcHyxhBase.getHyxhmc());
		}
		return resultMap;
	}



	/**
	 * 
	 * 方法描述：根据ID查询出单位详情
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月24日 下午12:11:26
	 */
	@RequestMapping("/queryCompanyById")
	@ResponseBody
	public DdcHyxhSsdw queryCompanyById(HttpServletRequest request, String id) {
		long dId = Long.parseLong(id);
		DdcHyxhSsdw ddcHyxhSsdw = iCompanyService.getDdcHyxhSsdwById(dId);
		DdcHyxhBase ddcHyxhBase = iCompanyService.getHyxhZhByCode(ddcHyxhSsdw.getHyxhzh());
		ddcHyxhSsdw.setHyxhzhName(ddcHyxhBase.getHyxhmc());
		String showImg = parseUrl(ddcHyxhSsdw.getVcPicPath());
		ddcHyxhSsdw.setVcShowPath(showImg);
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
			PicPath picPath = iCompanyService.getPathById(SystemConstants.PIC_IMG);

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
	 * 方法描述：
	 * 
	 * @param request
	 * @param response
	 * @param ddcHyxhBase
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月23日 下午6:43:44
	 */
	@RequestMapping("/saveOrUpdate")
	public void saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response, DdcHyxhBase ddcHyxhBase) {
	
		ddcHyxhBase.setCjrq(new Date());
		ddcHyxhBase.setnEnable(0);
		if (ddcHyxhBase.getId() == null) {
			try {
				ddcHyxhBase.setHyxhmm("123456");
				ddcHyxhBase.setHyxhsjzpe(ddcHyxhBase.getTotalPe());// 新增时剩余配额=总配额
				ddcHyxhBase.setSynFlag(SystemConstants.SYSNFLAG_ADD);
				ddcHyxhBase.setTranDate(new Date());
				iEbikeService.saveDdcHyxhBase(ddcHyxhBase);
				AjaxUtil.rendJson(response, true, "新增成功，默认密码为123456");
			} catch (Exception e) {
				e.printStackTrace();
				AjaxUtil.rendJson(response, false, "操作失败，系统错误!");
			}
		} else {
			try {
				if (ddcHyxhBase.getTotalPe() < ddcHyxhBase.getHyxhsjzpe()) {
					AjaxUtil.rendJson(response, false, "剩余配额应该小于总配额！");
					return;
				}
				ddcHyxhBase.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
				ddcHyxhBase.setTranDate(new Date());
				iEbikeService.updateDdchyxhBase(ddcHyxhBase);
				AjaxUtil.rendJson(response, true, "操作成功");
			} catch (Exception e) {
				e.printStackTrace();
				AjaxUtil.rendJson(response, false, "操作失败，系统错误!");
			}
		}
	}

	/**
	 * 
	 * 方法描述：修改行业协会所属单位
	 * 
	 * @param request
	 * @param response
	 * @param ddcHyxhSsdw
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月24日 上午11:20:16
	 */
	@RequestMapping("/saveOrUpdateCompany")
	public void saveOrUpdateCompany(HttpServletRequest request,
			HttpServletResponse response, DdcHyxhSsdw ddcHyxhSsdw) {

		try {
			if (ddcHyxhSsdw.getId() == null) {
				iCompanyService.save(ddcHyxhSsdw);
			} else {
				ddcHyxhSsdw.setSynFlag(SystemConstants.SYSNFLAG_UPDATE);
				ddcHyxhSsdw.setTranDate(new Date());
				iCompanyService.update(ddcHyxhSsdw);
			}
			AjaxUtil.rendJson(response, true, "操作成功！");

		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.rendJson(response, false, "操作失败，系统错误!");
		}
	}



	/**
	 * 
	 * 方法描述：ajax获取所有行业协会
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月24日 上午11:10:52
	 */
	@RequestMapping("/getAllIndustry")
	@ResponseBody
	public List<DdcHyxhBase> getAllIndustry() {
		List<DdcHyxhBase> ddcHyxhBases = iEbikeService.getAllDDcHyxhBase();
		return ddcHyxhBases;
	}

	/**
	 * 
	 * 方法描述：根据行业协会账号查出所有公司
	 * 
	 * @param hyxhzh
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年3月29日 下午4:37:05
	 */
	@RequestMapping("/getDwmcByHyxh")
	@ResponseBody
	public List<DdcHyxhSsdw> getDwmcByHyxh(String hyxhzh) {
		List<DdcHyxhSsdw> ddcHyxhSsdws = iCompanyService.getAllCompany(hyxhzh);
		return ddcHyxhSsdws;
	}
}

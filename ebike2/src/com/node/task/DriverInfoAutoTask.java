/**
 * 文件名：DriverInfoAutoTask.java
 * 版本信息：Version 1.0
 * 日期：2016年6月7日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.task;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.node.model.DdcDriver;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.service.IEbikeService;
import com.node.service.ITaskService;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月7日 下午8:40:16
 */
@Component("scheduledTaskManager")
public class DriverInfoAutoTask {
	@Autowired
	ITaskService iTaskService;
	
	@Autowired
	IEbikeService iEbikeService;

	
	/**
	 * 
	 * @Description: "0 0 12 * * ?" 每天中午十二点触发 "0 15 10 ? * *" 每天早上10：15触发
	 *               "0 15 10 * * ?" 每天早上10：15触发 "0 15 10 * * ? *" 每天早上10：15触发
	 *               "0 15 10 * * ? 2005" 2005年的每天早上10：15触发 "0 * 14 * * ?"
	 *               每天从下午2点开始到2点59分每分钟一次触发 "0 0/5 14 * * ?"
	 *               每天从下午2点开始到2：55分结束每5分钟一次触发 "0 0/5 14,18 * * ?"
	 *               每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 "0 0-5 14 * * ?"
	 *               每天14:00至14:05每分钟一次触发 "0 10,44 14 ? 3 WED"
	 *               三月的每周三的14：10和14：44触发 "0 15 10 ? * MON-FRI"
	 *               每个周一、周二、周三、周四、周五的10：15触发
	 * @author liuwu
	 * @create_date 2015-8-17 下午4:16:19
	 */
	/** 
     * 定时卡点计算。每天凌晨 02:00 执行一次  cron = "0 0 2 * * *"
     */  
	@Scheduled(cron = "0 35 22 * * *?")
	public void autoCardCalculate() {
		/*List<DdcHyxhSsdwclsb> ddcHyxhSsdwclsbs = iTaskService.findAllClsbs();
		for(DdcHyxhSsdwclsb ddcHyxhSsdwclsb:ddcHyxhSsdwclsbs){
			saveHasValidDriver(ddcHyxhSsdwclsb);
		}*/
		List<DdcDriver> ddcDrivers = iTaskService.findAllDriversNotValid();//所有未绑定协会单位的司机
		if(CollectionUtils.isNotEmpty(ddcDrivers)){
			for(DdcDriver ddcDriver:ddcDrivers){
				DdcHyxhSsdwclsb ddcHyxhSsdwclsb = iTaskService.findClsbByDriver(ddcDriver);
				if(ddcHyxhSsdwclsb!=null){
					ddcDriver.setHyxhzh(ddcHyxhSsdwclsb.getHyxhzh());
					ddcDriver.setSsdwId(Long.parseLong(ddcHyxhSsdwclsb.getSsdwId()));
					iEbikeService.saveDdcDriver(ddcDriver);
				}
			
			}
		}
		
	}
	

	
	private void saveHasValidDriver(DdcHyxhSsdwclsb ddcHyxhSsdwclsb) {
		// TODO Auto-generated method stub
		DdcDriver ddcDriver1 = new DdcDriver();
		ddcDriver1.setJsrxm(ddcHyxhSsdwclsb.getJsrxm1());
		ddcDriver1.setLxdh(ddcHyxhSsdwclsb.getLxdh1());
		ddcDriver1.setXb(ddcHyxhSsdwclsb.getXb1());
		ddcDriver1.setSfzhm(ddcHyxhSsdwclsb.getSfzmhm1());
		ddcDriver1.setUserCode(ddcHyxhSsdwclsb.getLxdh1());
		ddcDriver1.setUserPassword("123456");
		ddcDriver1.setVcUserImg(ddcHyxhSsdwclsb.getVcUser1Img());
		ddcDriver1.setVcUserWorkImg(ddcHyxhSsdwclsb.getVcUser1WorkImg());
		ddcDriver1.setVcUserCardImg1(ddcHyxhSsdwclsb.getVcUser1CardImg1());
		ddcDriver1.setVcUserCardImg2(ddcHyxhSsdwclsb.getVcUser1CardImg2());
		if(StringUtils.isNotBlank(ddcHyxhSsdwclsb.getJsrxm2())){
			DdcDriver ddcDriver2 = new DdcDriver();
			ddcDriver2.setJsrxm(ddcHyxhSsdwclsb.getJsrxm2());
			ddcDriver2.setLxdh(ddcHyxhSsdwclsb.getLxdh2());
			ddcDriver2.setXb(ddcHyxhSsdwclsb.getXb1());
			ddcDriver2.setSfzhm(ddcHyxhSsdwclsb.getSfzmhm2());
			ddcDriver2.setUserCode(ddcHyxhSsdwclsb.getLxdh2());
			ddcDriver2.setUserPassword("123456");
			if(StringUtils.isNotBlank(ddcHyxhSsdwclsb.getVcUser2Img())){
				ddcDriver2.setVcUserImg(ddcHyxhSsdwclsb.getVcUser2Img());
			}
			if(StringUtils.isNotBlank(ddcHyxhSsdwclsb.getVcUser2WorkImg())){
				ddcDriver2.setVcUserWorkImg(ddcHyxhSsdwclsb.getVcUser2WorkImg());
			}
			if(StringUtils.isNotBlank(ddcHyxhSsdwclsb.getVcUser2CardImg1())){
				ddcDriver2.setVcUserCardImg1(ddcHyxhSsdwclsb.getVcUser2CardImg1());
			}
			if(StringUtils.isNotBlank(ddcHyxhSsdwclsb.getVcUser2CardImg2())){
				ddcDriver2.setVcUserCardImg2(ddcHyxhSsdwclsb.getVcUser2CardImg2());
			}
			
			
			iEbikeService.saveDdcDriver(ddcDriver2);
		}
		iEbikeService.saveDdcDriver(ddcDriver1);
	}
}

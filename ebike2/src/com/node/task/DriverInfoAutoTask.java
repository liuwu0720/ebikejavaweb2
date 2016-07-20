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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.node.model.DdcDriver;
import com.node.model.DdcHyxhSsdwclsb;
import com.node.service.IApplyService;
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
	
	@Autowired
	IApplyService iApplyService;

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
	 * 定时卡点计算。每天凌晨 02:00 执行一次 cron = "0 0 2 * * *"
	 */
	@Scheduled(cron = "0 35 22 * * *?")
	public void autoCardCalculate() {
		/*
		 * List<DdcHyxhSsdwclsb> ddcHyxhSsdwclsbs = iTaskService.findAllClsbs();
		 * for(DdcHyxhSsdwclsb ddcHyxhSsdwclsb:ddcHyxhSsdwclsbs){
		 * saveHasValidDriver(ddcHyxhSsdwclsb); }
		 */
		List<DdcDriver> ddcDrivers = iTaskService.findAllDriversNotValid();// 所有未验证的司机
		if (CollectionUtils.isNotEmpty(ddcDrivers)) {
			for (DdcDriver ddcDriver : ddcDrivers) {
				iEbikeService.saveDdcDriver(ddcDriver);
			}
		}

	}
	
	
	@Scheduled(cron = "0 15 16 * * *?")
	public void updateDriverState4() {
		String sql2 = " update DDC_DRIVER t set t.syn_flag='ADD' where t.USER_STATUS=1 ";
		iTaskService.updateBySql(sql2);
	}
	
	@Scheduled(cron = "0 15 20 * * *?")
	public void updateDriverState5() {
		String sql2 = " update  DDC_DRIVER t set t.user_status=1,t.xj_flag=null,t.xj_rq=null,t.xj_msg=null  where  t.xj_flag = -99";
		iTaskService.updateBySql(sql2);
	}
	
	@Scheduled(cron = "0 15 22 * * *?")
	public void updateClsb(){
		List<DdcHyxhSsdwclsb> ddcHyxhSsdwclsbs = iEbikeService.getAllClsb();
		for(DdcHyxhSsdwclsb ddcHyxhSsdwclsb:ddcHyxhSsdwclsbs){
			int slIndexStatus = iApplyService.getSlStatus(ddcHyxhSsdwclsb);
			ddcHyxhSsdwclsb.setSlIndex(slIndexStatus);
			iApplyService.updateDdcHyxhSsdwclsb(ddcHyxhSsdwclsb);
		}
	}
}

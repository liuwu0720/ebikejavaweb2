/**
  * 文件名：DriverInfoAutoTask.java
  * 版本信息：Version 1.0
  * 日期：2016年6月7日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月7日 下午8:40:16 
 */
@Component("driverInfoAutoTask")
public class DriverInfoAutoTask {
	/**
	 * 
	 * @Description: void CRON表达式 含义 "0 0 12 * * ?" 每天中午十二点触发 "0 15 10 ? * *"
	 *               每天早上10：15触发 "0 15 10 * * ?" 每天早上10：15触发 "0 15 10 * * ? *"
	 *               每天早上10：15触发 "0 15 10 * * ? 2005" 2005年的每天早上10：15触发
	 *               "0 * 14 * * ?" 每天从下午2点开始到2点59分每分钟一次触发 "0 0/5 14 * * ?"
	 *               每天从下午2点开始到2：55分结束每5分钟一次触发 "0 0/5 14,18 * * ?"
	 *               每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 "0 0-5 14 * * ?"
	 *               每天14:00至14:05每分钟一次触发 "0 10,44 14 ? 3 WED"
	 *               三月的每周三的14：10和14：44触发 "0 15 10 ? * MON-FRI"
	 *               每个周一、周二、周三、周四、周五的10：15触发
	 * @author liuwu
	 * @create_date 2015-8-17 下午4:16:19
	 */
	@Scheduled(fixedRate = 1000 * 3)
	public void checkDriverInfo(){
		System.out.println("********************");
	}
}

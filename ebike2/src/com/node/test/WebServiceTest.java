/**
 * 文件名：WebServiceTest.java
 * 版本信息：Version 1.0
 * 日期：2016年8月4日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.test;

import java.util.List;
import java.util.Map;

import com.node.obj.ScoreResult;
import com.node.util.ScoreQueryUtil;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年8月4日 下午6:07:38
 */
public class WebServiceTest {

	public static void main(String[] args) {
		String sfzhm="440527196809071038";
		String sjh="18312463699";
		Map<String, Object> resultMap = ScoreQueryUtil.queryTestResult(sfzhm, sjh);
		System.out.println("resultMap = "+resultMap);
		List<ScoreResult> scoreResults = ScoreQueryUtil.scoreResults(sfzhm, sjh);
		System.out.println("scoreResults = "+scoreResults);
		/*TrafficIllegalQuery query = new TrafficIllegalQuery();
		List<TrafficMsg> trafficMsgs = query.postHttp("00002dddfd");
		System.out.println("trafficMsgs = "+trafficMsgs);
		
		TrafficMsgQueryUtil trafficMsgQueryUtil = new TrafficMsgQueryUtil();
		List<TrafficMsg> trafficMsgs2 =trafficMsgQueryUtil.postHttp("深A1111");
		System.out.println("trafficMsgs2 = "+trafficMsgs2);*/
	}

}

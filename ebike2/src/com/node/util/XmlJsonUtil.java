/**
  * 文件名：XmlJsonUtil.java
  * 版本信息：Version 1.0
  * 日期：2016年8月5日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.alibaba.fastjson.JSON;
import com.node.obj.ScoreResult;


/**
 * 类描述：将XML转成JSON 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年8月5日 下午7:02:08 
 */
public class XmlJsonUtil {
	
    public static  Map<String, Object> xml2JSON(String xml) throws JSONException {  
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	JSONObject xmlJSONObj = XML.toJSONObject(xml);
		String json2 = JSON.parseObject(xmlJSONObj.toString()).getString("return");
		System.out.println("json2 = "+json2);
		String bodyString = JSON.parseObject(json2).getString("body");
		String headString =  JSON.parseObject(json2).getString("head");
		String fhzString = JSON.parseObject(headString).getString("fhz");
		System.out.println("headString = "+JSON.parseObject(headString).getString("fhz"));
		if(StringUtils.isBlank(bodyString)){
			//String errorMsg=JSON.parseObject(headString).getString("fhz-msg");
			//查询失败！不是星级用户或用户状态未激活，不能查询答题信息。
			return null;
		}else if(JSON.parseObject(bodyString).containsKey("examresultlist")){
			String examresultlist = JSON.parseObject(bodyString).getString("examresultlist");
			// {"pxhgsj":"2016-07-18","pxjg":"合格"}
			System.out.println("examresultlist = "+examresultlist);
			String result= JSON.parseObject(examresultlist).getString("pxjg");
			resultMap.put(fhzString, result);
		}else{
			return null;
		}
		
    	return resultMap;
    }

	
	/**
	  * 方法描述：
	  * @param result
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年8月8日 上午11:06:39
	 * @throws JSONException 
	  */
	public static List<ScoreResult> getScoreResultList(String result) throws JSONException {
		JSONObject xmlJSONObj = XML.toJSONObject(result);
		String json2 = JSON.parseObject(xmlJSONObj.toString()).getString("return");
	
		String bodyString = JSON.parseObject(json2).getString("body");
		if(StringUtils.isNotBlank(bodyString)){
			System.out.println("bodyString = "+bodyString);
			if(JSON.parseObject(bodyString).containsKey("examlist")){
				String examlistStr=JSON.parseObject(bodyString).getString("examlist");
				
				try {
					List<ScoreResult> scoreResults = JSON.parseArray(
							examlistStr, ScoreResult.class);
					return scoreResults;
				} catch (Exception e) {
					ScoreResult scoreResult= JSON.parseObject(examlistStr, ScoreResult.class);
					List<ScoreResult> scoreResults = new ArrayList<ScoreResult>();
					scoreResults.add(scoreResult);
					return scoreResults;
				}
			}else {
				return null;
			}
		}
		
		return null;
	}  
    
   
}

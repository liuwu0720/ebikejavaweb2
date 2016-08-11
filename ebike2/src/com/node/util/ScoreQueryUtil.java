/**
  * 文件名：ScoreQueryUtil.java
  * 版本信息：Version 1.0
  * 日期：2016年8月5日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.node.obj.ScoreResult;
import com.node.test.DESCorder;


/**
 * 类描述：星级考试分数查询
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年8月5日 下午2:54:19 
 */
public class ScoreQueryUtil {
	private static final Logger logger = Logger.getLogger("星级考试分数查询.");
	
	/**
	 * 
	  * 方法描述：考试结果查询 返回最终结果
	  * @param sfzhm
	  * @param sjh
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年8月8日 上午11:02:50
	 */
	public static Map<String, Object> queryTestResult(String sfzhm,String sjh) {
		try {

			String endpoint = SystemConstants.SERVICE_URL;
			//String endpoint="http://green.stc.gov.cn:8080/xxfbpt/services/xxfbptservice";
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpoint);
			// WSDL里面描述的接口名称(要调用的方法)
			call.setOperationName("xxptSchuding");
			// 接口方法的参数名, 参数类型,参数模式 IN(输入), OUT(输出) or INOUT(输入输出)
			call.addParameter("userid", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("userpwd", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("jkid", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("srcs", XMLType.XSD_STRING, ParameterMode.IN);
			// 设置被调用方法的返回值类型
			call.setReturnType(XMLType.XSD_STRING);
			String srcsString = getResultByXml(sfzhm,sjh);
			String key = SystemConstants.SERVICE_KEY;
			String base64 = DESCorder.encryptModeToString(srcsString, key);
			// String base64 = srcsString;
			// 设置方法中参数的值
			Object[] paramValues = new Object[] { SystemConstants.SERVICE_USER_ID, SystemConstants.SERVICE_USER_PWD, SystemConstants.SERVICE_JK_ID, base64 };
			
		
			// 给方法传递参数，并且调用方法
			String result = (String) call.invoke(paramValues);
			System.out.println("查询成绩返回结果result = "+result);
			result = parseXML(result);
			String deCodeStr2 = DESCorder.decryptMode(result, key, "utf8");
			Map<String, Object> json = XmlJsonUtil.xml2JSON(deCodeStr2);
			return json;
			
		} catch (Exception e) {
			logger.warn("接口调用失败："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	  * 方法描述：返回考试结果列表信息
	  * @param sfzhm
	  * @param sjh
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年8月8日 上午11:06:13
	 */
	public static List<ScoreResult> scoreResults(String sfzhm,String sjh){
		try {

			String endpoint = SystemConstants.SERVICE_URL;
			//String endpoint="http://green.stc.gov.cn:8080/xxfbpt/services/xxfbptservice";
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpoint);
			// WSDL里面描述的接口名称(要调用的方法)
			call.setOperationName("xxptSchuding");
			// 接口方法的参数名, 参数类型,参数模式 IN(输入), OUT(输出) or INOUT(输入输出)
			call.addParameter("userid", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("userpwd", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("jkid", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("srcs", XMLType.XSD_STRING, ParameterMode.IN);
			// 设置被调用方法的返回值类型
			call.setReturnType(XMLType.XSD_STRING);
			String srcsString = getResultByXml(sfzhm,sjh);
			String key = SystemConstants.SERVICE_KEY;
			String base64 = DESCorder.encryptModeToString(srcsString, key);
			// String base64 = srcsString;
			// 设置方法中参数的值
			Object[] paramValues = new Object[] { SystemConstants.SERVICE_USER_ID, SystemConstants.SERVICE_USER_PWD, SystemConstants.SERVICE_JK_ID, base64 };
			
		
			// 给方法传递参数，并且调用方法
			String result = (String) call.invoke(paramValues);
			System.out.println("查询成绩返回结果result = "+result);
			result = parseXML(result);
			String deCodeStr2 = DESCorder.decryptMode(result, key, "utf8");
			System.out.println("解密后的结果 = "+deCodeStr2);
			List<ScoreResult> scoreResults = XmlJsonUtil.getScoreResultList(deCodeStr2);
			return  scoreResults;
		} catch (Exception e) {
			logger.warn("接口调用失败："+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	  * 方法描述：
	  * @param sfzhm
	  * @param sjh
	  * @return 
	  * @version: 1.0
	  * @author: liuwu
	  * @version: 2016年8月6日 上午10:30:40
	  */
	private static String getResultByXml(String sfzhm, String sjh) {
		String ip = SystemConstants.SERVICE_REQUEST_IP;//
		String yhly = SystemConstants.SERVICE_YWLY;
		String ywlx = SystemConstants.SERVICE_YWLX;
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> ");
		sb.append("<request>");
		sb.append("<head>");
		sb.append("<sfzmhm>" + sfzhm + "</sfzmhm>");
		sb.append("<sjhm>" + sjh + "</sjhm>");
		sb.append("<ip>" + ip + "</ip>");
		sb.append("<ywlx>" + ywlx + "</ywlx>");
		sb.append("<yhly>" + yhly + "</yhly>");
		sb.append("</head>");
		sb.append("</request>");
		return sb.toString();
	}
	private static String parseXML(String xmlDoc) {
		 //创建一个新的字符串
       StringReader read = new StringReader(xmlDoc);
       //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
       InputSource source = new InputSource(read);
       //创建一个新的SAXBuilder
       SAXBuilder sb = new SAXBuilder();
       try {
           //通过输入源构造一个Document
           Document doc = sb.build(source);
           //取的根元素
           Element root = doc.getRootElement();
           //得到根元素所有子元素的集合
           String msg=  root.getChildText("msg");
           return msg;

       } catch (JDOMException e) {
           // TODO 自动生成 catch 块
           e.printStackTrace();
       } catch (IOException e) {
           // TODO 自动生成 catch 块
           e.printStackTrace();
       }
       return null;
	}
}

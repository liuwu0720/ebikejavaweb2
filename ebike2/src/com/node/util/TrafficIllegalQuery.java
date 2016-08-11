/**
 * 文件名：TrafficIllegalQuery.java
 * 版本信息：Version 1.0
 * 日期：2016年8月2日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.node.obj.TrafficMsg;
import com.winshines.mac.util.MacUtil;

/**
 * 类描述：违法查询测试
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年8月2日 上午9:00:21
 */
public class TrafficIllegalQuery {

	public  List<TrafficMsg> postHttp(String cphm) {
		String partnerCode = SystemConstants.ILLEGAL_CODE;// 合作方代码
		String partnerUserId = SystemConstants.ILLEGAL_USER_ID;// 唯一标识
		String serialNo = "";// 通讯流水号，由合作方生成能唯一标识每次请求即可
		String timeStamp = "";
		String macAlg = "33";// MD5算法
		String macKey = SystemConstants.ILLEGAL_KEY;// 对该字段后请求报文的签名值（计算方式见“MAC算法”章节）

		String ReqURL = SystemConstants.ILLEGAL_URL;

		timeStamp = DateStrUtil.toStringTimeStamp(new Date());
		MacUtil macUtil = new MacUtil();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("licensePlateNo", cphm);
		jsonObject.put("licensePlateType", "07");
		String msg = jsonObject.toString();
		String mac = macUtil.genMsgMac(timeStamp, macKey, macAlg, msg);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("partnerCode", partnerCode);
		params.put("partnerUserId", partnerUserId);
		params.put("serialNo", timeStamp);
		params.put("timeStamp", timeStamp);
		params.put("macAlg", macAlg);
		params.put("mac", mac);
		params.put("data", jsonObject);
		String result = sendPost(ReqURL, params);
		System.out.println("result = " + result);
		if (JSON.parseObject(result).containsKey("data")) {
			String data = JSON.parseObject(result).getString("data");
			List<TrafficMsg> trafficMsgs = JSONArray.parseArray(data,
					TrafficMsg.class);
			return trafficMsgs;
		} else {
			return null;
		}

	}

	private  String sendPost(String url, Map<String, Object> params) {
		DataOutputStream out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// POST方法
			conn.setRequestMethod("POST");
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.connect();

			// 获取URLConnection对象对应的输出流
			out = new DataOutputStream(conn.getOutputStream());
			// 发送请求参数
			if (params != null) {
				String param = JSONObject.fromObject(params).toString();
				System.out.println("报文:"+param);
				out.write(param.getBytes());
			}
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}


}

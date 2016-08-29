/**
 * 文件名：SubscribeMessage.java
 * 版本信息：Version 1.0
 * 日期：2016年8月27日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年8月27日 下午5:11:24
 */
public class SubscribeMessage {
	static String reqURL = "http://127.0.0.1:3000/backend/ebiz/manage/subscribeMessage";

	private static String sendPost(String url, Map<String, String> params) {
		OutputStreamWriter out = null;
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
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			// 发送请求参数
			if (params != null) {
				StringBuilder param = new StringBuilder();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if (param.length() > 0) {
						param.append("&");
					}
					param.append(entry.getKey());
					param.append("=");
					param.append(entry.getValue());
				}
				System.out.println("param:" + param.toString());
				out.write(param.toString());
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

	public static void main(String[] args) throws IOException {
		String requestData = Test2.getJSon();
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
		params.put("RequestType", "1005");

		String result = sendPost(reqURL, params);
		System.out.println("result="+result);
	}

	private static String urlEncoder(String str, String charset)
			throws UnsupportedEncodingException {
		String result = URLEncoder.encode(str, charset);
		return result;
	}

}

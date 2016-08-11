/**
 * 文件名：GetHttp.java
 * 版本信息：Version 1.0
 * 日期：2016年6月21日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.util;

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

import com.node.test.DESCorder;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月21日 上午10:00:51
 */
public class GetHttp {
	String url = "http://app.stc.gov.cn:9082/xxfbpt/services/xxfbptservice?wsdl";
	String jkid = "xxptSchuding";
	String userid = "dzcgs";
	String userpwd = "343667632@qq";
	String key="mMnwKKRPrAPLrG5sY01DDGmt";

	String sendPost(String urlStr, Map<String, String> params) throws Exception {
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
			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			conn.connect();
			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			// 发送请求参数
			if (params != null) {
				StringBuffer  sb = new StringBuffer();
				for (Map.Entry<String, String> e : params.entrySet()) {
					sb.append("&").append(e.getKey()).append("=")
					.append(e.getValue());
					// System.out.println(entry.getKey()+":"+entry.getValue());
				}
				sb.substring(0, sb.length() - 1);
				/*String param = JSON.toJSONString(params);
				byte[] bkey = EncryptUtils.GetKeyBytes(key);  
				String jsonString= EncryptUtils.byte2Base64(EncryptUtils.encryptMode(bkey, param.getBytes()));*/
				System.out.println("param = "+sb);
				out.write(sb.toString());
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
		System.out.println("解密前："+result.toString());
		//System.out.println("解密后:"+ EncryptUtils.Decrypt3DES(result.toString(),key));
		return result.toString();
	}

	public static void main(String[] args) {
		GetHttp getHttp = new GetHttp();
		try {
			String result = getHttp.getResultByXml();
			System.out.println("result = " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 方法描述：
	 * 
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年8月1日 上午10:02:42
	 * @throws UnsupportedEncodingException
	 */
	private String getResultByXml() throws Exception {
		 byte[] bkey = EncryptUtils.GetKeyBytes(key);  
		 
		String sfzhm = "430621198807206810";
		String phone = "15818684074";
		String ip = "163.125.147.240";//
		String yhly = "D";
		String ywlx = "BA";
		StringBuilder sb = new StringBuilder();
		sb.append("<request>");
		sb.append("<head>");
		sb.append("<sfzmhm>" + sfzhm + "</sfzmhm>");
		sb.append("<sjhm>" + phone + "</sjhm>");
		sb.append("<ip>" + ip + "</ip>");
		sb.append("<ywlx>" + ywlx + "</ywlx>");
		sb.append("<yhly>" + yhly + "</yhly>");
		sb.append("</head>");
		sb.append("</request>");
		Map<String, String> params = new HashMap<String, String>();
		
		String base64 = DESCorder.encryptModeToString(sb.toString().trim(), key);
		System.out.println("base64 = "+base64);
		params.put("srcs",base64);
		params.put("jkid", jkid);
		params.put("userid", userid);
		params.put("userpwd", userpwd);
	
		String result = sendPost(url, params);
		
		return result;
	}

	private String urlEncoder(String str, String charset)
			throws UnsupportedEncodingException {
		String result = URLEncoder.encode(str, charset);
		return result;
	}

}

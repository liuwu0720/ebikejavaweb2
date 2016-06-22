/**
  * 文件名：GetHttp.java
  * 版本信息：Version 1.0
  * 日期：2016年6月21日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年6月21日 上午10:00:51 
 */
public class GetHttp {
	void testPost(String urlStr) {  
        try {  
            URL url = new URL(urlStr);  
            HttpURLConnection  con = (HttpURLConnection) url.openConnection();  
            con.setDoOutput(true);  
            con.setRequestProperty("Pragma:", "no-cache");  
            con.setRequestProperty("Cache-Control", "no-cache");  
            con.setRequestProperty("Content-Type", "text/xml");  
            String xmlInfo = getXmlInfo();  
            con.setRequestProperty("Content-Length",xmlInfo.length()+""); 
            con.setRequestProperty("srcs", xmlInfo);
            con.setRequestProperty("userId", "dzcgs");
            con.setRequestProperty("userPwd", "343667632@qq");
            con.setRequestProperty("jkid", "xxptSchuding");
            //设置输入和输出流  
            con.setDoOutput(true);  
            con.setDoInput(true); 
            
            DataOutputStream dos=new DataOutputStream(con.getOutputStream());  
            dos.writeBytes(xmlInfo);  
            dos.flush();  
            dos.close();  
            //得到HTTP响应码  
            int code=con.getResponseCode();  
            InputStream is=con.getInputStream();  
              
            //获取返回报文  
            BufferedReader bis=new BufferedReader(new InputStreamReader(is));  
            StringBuffer sb=new StringBuffer();  
            String temp="";  
            while((temp=bis.readLine())!=null){  
                sb.append(temp+"\n");  
            }  
            System.out.println("sb="+sb.toString()+",code="+code);
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    private String getXmlInfo() {  
    	String sfzhm="430621198807206810";
    	String phone = "15818684074";
    	String ip = "127.0.0.1";
    	String type="D";
        StringBuilder sb = new StringBuilder();  
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");
        sb.append("<request>");  
        sb.append("    <head>");  
        sb.append("        <sfzmhm>"+sfzhm+"</sfzmhm>");  
        sb.append("        <sjhm>"+phone+"</sjhm>");  
        sb.append("        <ip>"+ip+"</ip>");  
        sb.append("        <yhly>"+type+"</yhly>");  
        sb.append("    </head>");  
        sb.append("</request>");  
        return sb.toString();  
    }  
  
    public static void main(String[] args) {  
        String url = "http://app.stc.gov.cn:9082/xxfbpt/services/xxfbptservice?wsdl";  
        new GetHttp().testPost(url);  
    }  
}

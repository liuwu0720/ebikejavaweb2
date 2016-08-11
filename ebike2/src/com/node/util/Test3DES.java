/**
  * 文件名：Test3DES.java
  * 版本信息：Version 1.0
  * 日期：2016年8月1日
  * Copyright 结点科技 Corporation 2016 
  * 版权所有
  */
package com.node.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
/**
 * 类描述：
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年8月1日 下午1:48:33 
 */
public class Test3DES {

    public static void main(String[] args) {  
  
        String key = "mMnwKKRPrAPLrG5sY01DDGmt";  
  
        String password = "<REQUEST><LOGIN_USER>230102198012070418</LOGIN_USER><YHLY>C</YHLY></REQUEST>";  
  
        System.out.println("key=" + key + ",password=" + password);  
  
        System.out.println();  
  
        System.out.println("----------示例开始：使用java写的算法加密解密-----------");  
  
       try {  
  
            String encrypt = "";  
  
            String decrypt = "";  
  
           byte[] bkey = EncryptUtils.GetKeyBytes(key);  
  
            encrypt = EncryptUtils.byte2Base64(EncryptUtils.encryptMode(bkey, password.getBytes()));  
  
            System.out.println("用预转换密钥算加密结果=" + EncryptUtils.encryptMode(bkey, password.getBytes()));  
  
            System.out.println("加密后base64表示=" + EncryptUtils.byte2hex(Base64.decode(encrypt)));  
  
            System.out.println("调用原始密钥算加密结果=" + EncryptUtils.Encrypt3DES(password, key));  
  
   
  
           try {  
  
              //  decrypt = new String(EncryptUtils.decryptMode(bkey, Base64.decode(encrypt)));  
  
                System.out.println("用预转换密钥算解密结果=" + decrypt);  
  
                System.out.println("调用原始密钥算解密结果=" + EncryptUtils.Decrypt3DES(encrypt, key));  
  
            } catch (Exception ex) {  
  
                System.out.println("Exception:" + ex.getMessage());  
  
            }  
  
        } catch (Exception ex) {  
  
            System.out.println("Exception:" + ex.getMessage());  
  
        }  
  
        System.out.println("----------示例结束：使用java写的算法加密解密-----------");  
  
    }  
}

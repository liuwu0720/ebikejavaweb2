package com.node.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
public class test {
	//����API��ַ
	protected static String testUrl = "http://qimen.6x86.net:10537/restxin/index.aspx";
	//�����appkey
	public static final String appkey = "c184567b";
	//�����secret
	public static final String secret="90353b57f17a4bf6a11263f0545ddbdc";
	//�����token
	public static final String token   ="e6513e432b724720ae6b6ab4155e6ccb";
	//���ʺ�
	public static final String dbhost = "edb_a99999";
	//���ظ�ʽ
	public static final String format = "json";
	
	//获取产品信息
	public static void edbProductGet()
	{
		
		TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();

		
		
        apiparamsMap.put("format", format);//添加请求参数——返回格式

        apiparamsMap.put("method", "edbProductGet");//添加请求参数——接口名称

        apiparamsMap.put("slencry","0");//添加请求参数——返回结果是否加密（0，为不加密 ，1.加密）

		apiparamsMap.put("ip","192.168.60.80");//添加请求参数——IP地址

        apiparamsMap.put("appkey",appkey);//添加请求参数——appkey

		apiparamsMap.put("appscret",secret);//添加请求参数——appscret

		apiparamsMap.put("token",token);//添加请求参数——token

        apiparamsMap.put("v", "2.0");//添加请求参数——版本号（目前只提供2.0版本）

		//apiparamsMap.put("fields", "msn,alipay_account,shop,mobile,sales,post,total_score,plan_balance,customerlevel_no,customer_name,email,customer_type,costomer_catalog,begin_time,end_time,consignee,consignee_address,telephone,province,actualremind_date,birthday,fp_date,lp_date,transaction_num,transaction_hz,total,shop_name,sex,pay_score,lsm_date,distributor_type,city,county,character,zl_degree,wangwang,qq,belong_distributor,from_shop,is_blanklist,is_potentialcustomer,totolplan_money,is_specialcustomer");

		String timestamp = new SimpleDateFormat("yyyyMMddHHmm")
				.format(new Date());

		apiparamsMap.put("timestamp", timestamp);//添加请求参数——时间戳
		apiparamsMap.put("dbhost", dbhost);//添加请求参数——主帐号
	
		apiparamsMap.put("page_no", "1");//分页
		apiparamsMap.put("page_size", "10");//页数量
		
		//获取数字签名
		String sign = Util.md5Signature(apiparamsMap, appkey);
		System.out.println("sign = "+sign);
		apiparamsMap.put("sign", sign);

		StringBuilder param = new StringBuilder();

		for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<String, String> e = it.next();
			if(e.getKey()!="appscret" && e.getKey()!="token")
			{
				if(e.getKey()=="shop_id" || e.getKey()=="wangwang_id" || e.getKey()=="date_type")
				{
					try {
						param.append("&").append(e.getKey()).append("=").append(Util.encodeUri(e.getValue()));
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					param.append("&").append(e.getKey()).append("=").append(e.getValue());
				}
			}
		}

		
		String PostData="";
		System.out.println("param="+param.toString());
		PostData=param.toString().substring(1);
		System.out.println(testUrl+"?"+PostData);
		String result="";
		result=Util.getResult(testUrl,PostData);
		System.out.println(result);
	}
	
	//修改客户信息
	public static void edbTradeAdd() {

		TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();

		apiparamsMap.put("dbhost", dbhost);//添加请求参数——主帐号
		
        apiparamsMap.put("format", format);//添加请求参数——返回格式

        apiparamsMap.put("method", "edbTradeAdd");//添加请求参数——接口名称

        apiparamsMap.put("slencry","0");//添加请求参数——返回结果是否加密（0，为不加密 ，1.加密）

		apiparamsMap.put("ip","192.168.60.80");//添加请求参数——IP地址

        apiparamsMap.put("appkey",appkey);//添加请求参数——appkey

		apiparamsMap.put("appscret",secret);//添加请求参数——appscret

		apiparamsMap.put("token",token);//添加请求参数——token

        apiparamsMap.put("v", "2.0");//添加请求参数——版本号（目前只提供2.0版本）

		apiparamsMap.put("fields", "result");

		String timestamp = new SimpleDateFormat("yyyyMMddHHmm")
				.format(new Date());

		apiparamsMap.put("timestamp", timestamp);//添加请求参数——时间戳
		/*shop_name,店铺名称
		 * wangwang 旺旺号
		 * level 用户级别，传入名称
		 * customer_type 客户分组  可以传多个，，以,号分隔
		 * buy_num 交易 次数
		 * order_money 订货总额
		 * custom_1  星座   ----value_1 星座名称
		 * custom_2 生日类型   ----value_2生日名称
		 * custom_3  情感状态   ----value_3 情感名称
		 * custom_4  血型   ----value_4 血型名称
		 * custom_5 生肖   ----value_5 生肖名称
		 */		
		String postdate = "<order>" ;	
		for(int i=0;i<1;i++)
		{
			postdate		+= "<orderInfo><out_tid>66666666666666</out_tid>";
			postdate		+= "<out_order_status>已付款</out_order_status>";
			postdate		+= "<storage_id>1</storage_id>";
			postdate		+= "<processState>未确认</processState>";
			postdate		+= "<express>EMS</express>";
			
			/*postdate		+= "<order_date>"+new Date()+"</order_date>";*/
		
			postdate		+= "<product_info>";
			for(int j=0;j<1;j++)
			{
				postdate		+= "<product_item>";
				postdate		+= "<barCode>2434343</barCode>";
				postdate		+= "<product_title>测试数据</product_title>";
				postdate		+= "<standard>测试规格</standard>";
				postdate		+= "<out_price>1153</out_price>";
				postdate		+= "<favorite_money>2</favorite_money>";
				postdate		+= "<orderGoods_Num>1</orderGoods_Num>";
				postdate		+= "<product_stockout>0</product_stockout>";
				postdate		+= "<shop_id>111</shop_id>";
				postdate		+= "<out_tid>66666666666666</out_tid>";
				postdate		+= "</product_item>";
			}
			postdate		+= "</product_info>";
			postdate		+= "</orderInfo>";								
		}				
		postdate		+= "</order>";
		java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		.format(new Date());	
		System.out.println("postdate = "+postdate);
		apiparamsMap.put("xmlValues",postdate);
		//获取数字签名
		String sign = Util.md5Signature(apiparamsMap, appkey);
			
		System.out.println("sign="+sign);
		apiparamsMap.put("sign", sign);

		StringBuilder param = new StringBuilder();

		for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<String, String> e = it.next();
			if(e.getKey()!="appscret" && e.getKey()!="token")
			{
				if(e.getKey()=="xmlValues")
				{
					try {
						param.append("&").append(e.getKey()).append("=").append(Util.encodeUri(e.getValue()));
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					param.append("&").append(e.getKey()).append("=").append(e.getValue());
				}
			}
		}
		String PostData="";
		System.out.println("param = "+param.toString());
		PostData=param.toString().substring(1);
		System.out.println(testUrl+"?"+PostData);
		String result="";
		result=Util.getResult(testUrl,PostData);
		System.out.println(result);
		
		String et = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		.format(new Date());
		try
		{
		java.util.Date begin=df.parse(st);
		java.util.Date end = df.parse(et);
		System.out.println(st);
		System.out.println(et);
		long between=(end.getTime()-begin.getTime())/1000;
		System.out.println(between);
		}catch(java.text.ParseException e){
		System.err.println("格式不正确");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//test.edbProductGet();
		test.edbTradeAdd();
		

	}

}

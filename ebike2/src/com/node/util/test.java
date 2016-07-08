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
			postdate		+= "<orderInfo><out_tid>66666666666666</out_tid><!-- 外部平台编号-->";
			postdate		+= "<out_order_status>已付款</out_order_status><!-- 外部订单状态（字符串，随意填写）-->";
			postdate		+= "<storage_id>1</storage_id><!-- 库房ID-->";
			postdate		+= "<processState></processState><!-- 处理状态（全部   未确认   已财务审核 已归档   已确认   已作废   ）-->";
			postdate		+= "<mergeStatus></mergeStatus><!-- 合并状态（合并 手拆 自动拆分 正常 ）-->";
			postdate		+= "<payStatus></payStatus><!-- 付款状态（字符串，随意填写）-->";
			postdate		+= "<shipStatus>未发货</shipStatus><!-- 发货状态（待退货部分退货  待退货全部退货    退货到货部分退货 退货到货全部退货  未发货   已发货）-->";
			postdate		+= "<express>申通</express><!-- 快递公司名称-->";
			postdate		+= "<WuLiu_no>2342325324</WuLiu_no><!--快递单号 -->";
			postdate		+= "<buyer_id>单反机</buyer_id><!-- 买家ID-->";
			postdate		+= "<buyer_msg>10</buyer_msg><!-- 买家留言-->";
			postdate		+= "<seller_remark>2</seller_remark><!-- 客服备注-->";
			postdate		+= "<pay_date></pay_date><!-- 付款日期-->";
			postdate		+= "<order_type>正常订单</order_type><!-- 订单类型-->";
			postdate		+= "<shop_id>112</shop_id><!-- 店铺代码-->";
			postdate		+= "<orderSource></orderSource><!-- 订单渠道-->";
			postdate		+= "<express></express><!-- 快递公司名称-->";
			postdate		+= "<payWay>alipay</payWay><!-- 支付方式-->";
			postdate		+= "<WuLiu>圆通</WuLiu><!-- 物流公司（线上物流公司）-->";
			postdate		+= "<in_memo>圆通</in_memo><!-- 内部便签-->";
			postdate		+= "<other_remark>121212</other_remark><!-- 其他备注-->";
			postdate		+= "<consignee></consignee><!-- 收货人-->";
			postdate		+= "<privince></privince><!-- 省-->";
			postdate		+= "<city></city><!-- 市-->";
			postdate		+= "<area></area><!-- 区-->";
			postdate		+= "<product_totalMoney></product_totalMoney><!-- 产品总金额-->";
			postdate		+= "<order_totalMoney></order_totalMoney><!-- 订单总金额-->";
			postdate		+= "<actual_RP></actual_RP><!-- 实收金额-->";
			postdate		+= "<actual_freight_get></actual_freight_get><!--实收运费-->";
			postdate		+= "<telephone></telephone><!-- 电话-->";
			postdate		+= "<mobilPhone></mobilPhone><!-- 手机-->";
			postdate		+= "<buyer_email></buyer_email><!-- 邮件-->";
			postdate		+= "<order_date>"+new Date()+"</order_date><!-- 订货日期-->";
			postdate		+= "<invoice_title></invoice_title><!--发票抬头 -->";
			postdate		+= "<invoice_type></invoice_type><!--发票类型 -->";
			postdate		+= "<invoice_money></invoice_money><!--发票金额 -->";
			postdate		+= "<invoice_msg></invoice_msg><!--开票内容 -->";
			postdate		+= "<is_needInvoice></is_needInvoice><!--是否需要开发票（0：否 1：是）默认：0 -->";
			postdate		+= "<is_invoiceOpened></is_invoiceOpened><!-- 是否已打印发票（0：否 1：是）-->";
			postdate		+= "<is_COD></is_COD><!--是否货到付款（0：否 1：是）默认：0 -->";
			postdate		+= "<serverCost_COD></serverCost_COD><!--货到付款服务费 -->";
			postdate		+= "<is_scorePay></is_scorePay><!--是否积分换购（0：否 1：是）默认：0 -->";
			postdate		+= "<pay_score></pay_score><!-- 支付积分-->";
			postdate		+= "<totalPackage></totalPackage><!-- 总件数-->";
			postdate		+= "<totalCount></totalCount><!-- 总条数-->";
			postdate		+= "<product_info>";
			for(int j=0;j<1;j++)
			{
				postdate		+= "<product_item>";
				postdate		+= "<barCode>123</barCode><!--条形码 -->";
				postdate		+= "<product_title>平底鞋</product_title><!-- 网店品名-->";
				postdate		+= "<standard>单反数码</standard><!-- 网店规格-->";
				postdate		+= "<out_price>1153</out_price><!-- 销售价-->";
				postdate		+= "<favorite_money>2</favorite_money><!-- 优惠金额-->";
				postdate		+= "<orderGoods_Num>1</orderGoods_Num><!-- 订货数量-->";
				postdate		+= "<gift_Num></gift_Num><!--赠品数量-->";
				postdate		+= "<cost_Price></cost_Price><!--成交单价-->";
				postdate		+= "<product_stockout>0</product_stockout><!--缺货状态（0：否 1：是）默认：0-->";
				postdate		+= "<is_Book></is_Book><!--是否预订（0：否 1：是）默认：0-->";
				postdate		+= "<is_presell></is_presell><!--是否预售-->";
				postdate		+= "<is_Gift></is_Gift><!--是否赠品（0：否 1：是）默认：0-->";
				postdate		+= "<avg_price></avg_price><!--加权平均单价-->";
				postdate		+= "<product_freight></product_freight><!--产品运费-->";
				postdate		+= "<shop_id>1</shop_id><!--店铺编号-->";
				postdate		+= "<out_tid>66666666666666</out_tid><!--外部平台单号-->";
				postdate		+= "<out_productId></out_productId><!--外部平台产品Id-->";
				postdate		+= "<out_barCode></out_barCode><!--外部平台条形码-->";
				postdate		+= "<product_intro></product_intro><!--产品简介-->";
				postdate		+= "</product_item>";
			}
			postdate		+= "</product_info>";
			postdate		+= "</orderInfo>";								
		}				
		postdate		+= "</order>";
		java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		.format(new Date());	
		apiparamsMap.put("xmlValues",postdate);
		//获取数字签名
		String sign = Util.md5Signature(apiparamsMap, appkey);

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

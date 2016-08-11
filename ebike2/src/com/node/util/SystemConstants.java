/**
 * 文件名：SystemConstants.java
 * 版本信息：Version 1.0
 * 日期：2016年3月3日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.util;


/**
 * 类描述：系統常量
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年3月3日 上午10:43:57
 */
public class SystemConstants {
	/**
	 * 
	 */

	public static final Integer SYS_RESOURCE_TYPE_OPERATION = 2;
	/**
	 * 交警支队的档案类型
	 */
	public static final Integer IARCHIVETYPE_TRAFFICE = 1;

	/**
	 * 根菜单
	 */
	public static final Integer ROOT_PARENTID = 0;

	/**
	 * 营业执照图片上传路径ID
	 */
	public static final Integer PIC_IMG = 1;
	/**
	 * 需要更新至内网
	 */
	public static final String SYSNFLAG_UPDATE = "UP";
	/**
	 * 需要新增至内网
	 */
	public static final String SYSNFLAG_ADD = "ADD";
	/**
	 * 最大文件
	 */
	public static final long MAXFILESIZE = 50;

	/**
	 * 配额申报表名
	 */
	public static final String PESBTABLE = "DDC_HYXH_BASB";
	/**
	 * 不同意
	 */
	public static final String NOTAGREE = "1";// 不同意
	/**
	 * 车辆备案申报表
	 */
	public static final String RECORDSBTABLE = "DDC_HYXH_SSDWCLSB";
	/**
	 * 同意
	 */
	public static final String AGREE = "0";
	/**
	 * 行业协会角色
	 */
	public static final String ROLE_HYXH = "1";
	/**
	 * 行业协会角色session
	 */
	public static final String SESSION_USER = "sessionuser";
	public static final String SESSION_USER_NAME = "sessionusername";
	/**
	 * 所属单位
	 */
	public static final String ROLE_SSDW = "2";

	public static final String ROLE_ADMIN = "3";

	/**
	 * 0 禁用 1 启用
	 */
	public static final String ENABLE_ZT = "1";
	/**
	 * 0 禁用 1 启用
	 */
	public static final String DISENABLE_ZT = "0";
	/**
	 * 营业执照最大宽度
	 */
	public static final int IMG_LICENCE_WITH = 800;
	/**
	 * 营业执照最大高度
	 */
	public static final int IMG_LICENCE_HEIGHT = 600;

	public static final String CLASS_NAME_DDC_HYXHBASE = "DdcHyxhBase";

	public static final String CLASS_NAME_DDC_HYXHSSDW = "DdcHyxhSsdw";
	/**
	 * 车身照片最大宽度
	 */
	public static final int IMG_EBIKE_WITH = 600;
	/**
	 * 车身照片最大高度
	 */
	public static final int IMG_EBIKE_HEIGHT = 400;
	/**
	 * 人员头像最大宽度
	 */
	public static final int IMG_HEAD_WITH = 300;
	/**
	 * 人员头像最大高度
	 */
	public static final int IMG_HEAD_HEIGHT = 400;
	/**
	 * 身份证最大宽度
	 */
	public static final int IMG_IDCARD_WIDTH = 500;
	/**
	 * 身份证最大高度
	 */
	public static final int IMG_IDCARD_HEIGHT = 350;
	/**
	 * 发票最大宽度
	 */
	public static final int IMG_INVOICE_WIDTH = 900;
	/**
	 * 发票最大高度
	 */
	public static final int IMG_INVOICE_HEIGHT = 600;
	/**
	 * 测试帐号
	 */
	public static final String TEST_USER = "cs";
	public static final Integer EXCEL_PATH = 2;
	public static final String DAXXB_TABLE = "DDC_DAXXB";
	public static final String DDCFLOW_TABLE = "DDC_FLOW";
	public static final String TOKEN_ALIPAY = "DDCGLXT2016";
	
	/**
	 * 司机状态
	 */
	
	public static final String USER_STATUS_0 = "未认证";
	public static final String USER_STATUS_1 = "已实名认证";
	public static final String USER_STATUS_2 = "星级用户";
	public static final String USER_STATUS_3 = "星级考试合格";
	
	public static final Integer INT_USER_STATUS_0=0;
	public static final Integer INT_USER_STATUS_1=1;
	public static final Integer INT_USER_STATUS_2=2;
	public static final Integer INT_USER_STATUS_3=3;
	
	/**
	 * 考试结果查询接口密钥等
	 */
	public static final String SERVICE_KEY="mMnwKKRPrAPLrG5sY01DDGmt";
	public static final String SERVICE_URL = "http://app.stc.gov.cn:9080/xxfbpt/services/xxfbptservice";//正式端口9080  测试端口9082
	public static final String SERVICE_REQUEST_IP ="192.168.2.197";//正式192.168.2.197
	public static final String SERVICE_YWLY="D";
	public static final String SERVICE_YWLX="BA";
	public static final String SERVICE_JK_ID="DDC1003";
	public static final String SERVICE_USER_ID="dzcgs";
	public static final String SERVICE_USER_PWD="343667632@qq";
	public static final String SERVICE_JK_NAME="xxptSchuding";
	public static final String SERVICE_FHZ1="1";
	public static final String SERVICE_FHZ0="0";
	/**
	 * 违法查询接口参数
	 */
	public static final String ILLEGAL_URL="http://120.197.101.170:9181/ysth-traffic-front/partnerService/trafficIllegalQuerySync.do";
	public static final String ILLEGAL_KEY="1234567890123456";
	public static final String ILLEGAL_CODE="P008";
	public static final String ILLEGAL_USER_ID="感识";
	
}

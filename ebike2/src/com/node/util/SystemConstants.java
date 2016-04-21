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
	public static final int IMG_EBIKE_WITH = 400;
	/**
	 * 车身照片最大高度
	 */
	public static final int IMG_EBIKE_HEIGHT = 300;
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
	public static final int IMG_INVOICE_WIDTH = 600;
	/**
	 * 发票最大高度
	 */
	public static final int IMG_INVOICE_HEIGHT = 400;
	/**
	 * 测试帐号
	 */
	public static final String TEST_USER = "cs";
	public static final Integer EXCEL_PATH = 2;

	public static void main(String[] args) {
		String codeString = "13366665555";
		System.out.println(codeString.substring(codeString.length() - 6));
	}
}

/**
 * 文件名：DataAction.java
 * 版本信息：Version 1.0
 * 日期：2016年4月14日
 * Copyright 结点科技 Corporation 2016 
 * 版权所有
 */
package com.node.action;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.node.model.FileRecord;
import com.node.model.PicPath;
import com.node.service.ICompanyService;
import com.node.service.IEbikeService;
import com.node.service.IUserService;
import com.node.util.HqlHelper;
import com.node.util.Page;
import com.node.util.ServiceUtil;

/**
 * 类描述：数据导出
 * 
 * @version: 1.0
 * @author: liuwu
 * @version: 2016年4月14日 下午4:29:50
 */
@Controller
@RequestMapping("/dataAction")
public class DataAction {

	@Autowired
	IUserService iUserService;

	@Autowired
	ICompanyService iCompanyService;

	@Autowired
	IEbikeService iEbikeService;

	@RequestMapping("/export")
	public String export() {
		return "data/exportData";
	}

	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(HttpServletRequest request) {
		Page p = ServiceUtil.getcurrPage(request);
		HqlHelper hql = new HqlHelper(FileRecord.class);
		hql.addOrderBy("id", "desc");

		hql.setQueryPage(p);
		Map<String, Object> resultMap = iUserService.queryFileRecordByHql(hql);
		return resultMap;
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月14日 下午5:23:41
	 * @throws IOException
	 * @throws WriteException
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, WriteException {
		try {
			PicPath imgPath = iCompanyService.getPathById(2);
			String path = imgPath.getVcAddpath();
			String fileName = createFileName() + ".xls";
			String filePath = path + "/" + fileName;// 写入
			String outPath = imgPath.getVcParsePath() + fileName;// 解析
			File pathFile = new File(filePath);
			WritableWorkbook wwb = null;
			pathFile.createNewFile();
			wwb = Workbook.createWorkbook(pathFile);
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 15,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// WritableFont wfc1 = new WritableFont(WritableFont.ARIAL, 10,
			// WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
			// Colour.RED);
			WritableFont wfc2 = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			WritableCellFormat wcfFC = new WritableCellFormat(wfc);
			// wcfFC.setBackground(Colour.DARK_YELLOW); 背景
			// WritableCellFormat wcfFC1 = new WritableCellFormat(wfc1);
			WritableCellFormat wcfFC2 = new WritableCellFormat(wfc2);
			// wcfFC.setWrap(true);//自动换行
			wcfFC.setAlignment(Alignment.CENTRE);// 把水平对齐方式指定为居中
			wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);// 把垂直对齐方式指定为居中
			/**
			 * 档案信息表
			 */
			WritableSheet ws = wwb.createSheet("档案信息表", 0);
			iEbikeService.createDaxxbExcel(wcfFC, wcfFC2, ws);

			/**
			 * 审批信息表
			 */
			WritableSheet ws2 = wwb.createSheet("审批信息表", 1);
			iEbikeService.createApproveUsers(wcfFC, wcfFC2, ws2);

			wwb.write();
			wwb.close();

			response.getWriter().print(outPath);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("2");
		}
		return null;

	}

	/**
	 * 方法描述：
	 * 
	 * @return
	 * @version: 1.0
	 * @author: liuwu
	 * @version: 2016年4月14日 下午6:10:46
	 */
	private String createFileName() {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String formatDate = format.format(new Date());

		return formatDate + "_N";
	}
}

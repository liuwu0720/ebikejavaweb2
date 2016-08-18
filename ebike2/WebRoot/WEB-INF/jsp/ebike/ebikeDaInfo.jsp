<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>档案详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@include file="../common/common.jsp"%>
<script type="text/javascript">

$(document).ready(function(){
	<%-- if('${ddcDaxxb.vcShowEbikeImg}'==''){
		 $("#img_0").attr("src","<%=basePath%>static/images/iconfont-wu.png");
	}else{
		$("#img_0").attr("src",'${ddcDaxxb.vcShowEbikeImg}');
	}
	if('${ddcDaxxb.vcShowUser2Img}'==''){
		 $("#img2_2").attr("src","<%=basePath%>static/images/iconfont-wu.png");
	}else{
		$("#img2_2").attr("src",'${ddcDaxxb.vcShowUser2Img}');
	}
	if('${ddcDaxxb.vcShowUser1Img}'==''){
		 $("#img1_1").attr("src","<%=basePath%>static/images/iconfont-wu.png");
	}else{
		$("#img1_1").attr("src",'${ddcDaxxb.vcShowUser1Img}');
	}  --%>
})

function exportPage() {
	$("#main").css('width', '650px');
	var bdhtml=window.document.body.innerHTML;
	var startStr="<!--startprint-->";//设置打印开始区域 
	var endStr="<!--endprint-->";//设置打印结束区域 
	var printHtml=bdhtml.substring(bdhtml.indexOf(startStr)+startStr.length,bdhtml.indexOf(endStr));//从标记里获取需要打印的页面 
	window.document.body.innerHTML=printHtml;//需要打印的页面 
	window.print(); 
	window.document.body.innerHTML=bdhtml;//还原界面 
}
</script>
  </head>
  
  <body>
   	 <div  class="maindiv">
    <!--startprint-->
    	<h2>备案车辆详情</h2>
    	<table id="main" class="table table-condensed"  border="1" cellpadding="0" cellspacing="0" width="98%">
				<tr>
					<th>所属单位</th>
					<td>${ddcDaxxb.ssdwName }</td>					
					<th>档案编号</th>
					<td>${ddcDaxxb.dabh }</td>
					<th>车牌号码</th>
					<td>${ddcDaxxb.cphm }</td>					
					<th>车辆状态</th>
					<td>${ddcDaxxb.ztName }</td>
				</tr>
				<tr>
					<th>归档日期</th>
					<td><fmt:formatDate value="${ddcDaxxb.gdrq }" pattern="yyyy-MM-dd"/></td>
    				<th>审检日期</th>
					<td><fmt:formatDate value="${ddcDaxxb.syrq }" pattern="yyyy/MM/dd HH:mm:ss"/></td>
					<th>审批状态</th>
					<td>
						<c:if test="${ddcDaxxb.slyj==null }">
							审批中
						</c:if>
						<c:if test="${ddcDaxxb.slyj==0 }">
							办结
						</c:if>
						<c:if test="${ddcDaxxb.slyj==1 }">
							退办
						</c:if>
					</td>
					<th>品牌型号</th>
					<td>${ddcDaxxb.ppxh }</td>
				</tr>
				<tr>
					<th>电机号</th>
					<td>${ddcDaxxb.djh }</td>	
					<th>脚踏装置:</th>
					<c:if test="${ddcDaxxb.jtzz == 0 }">
					<td>有</td>
					</c:if>
					<c:if test="${ddcDaxxb.jtzz == 1 }">
					<td>无</td>
					</c:if>
					<th>行驶区域</th>
					<td>全市范围(${ddcDaxxb.xsqyName }交警大队备案)</td>
					<th>车身颜色</th>
    				<td>${ddcDaxxb.cysyName }</td>
				</tr>
				<tr>
					<th>驾驶人姓名1</th>
					<td>${ddcDaxxb.jsrxm1 }(${ddcDaxxb.vcJsr1State })</td>	
					<th>驾驶人性别1</th>
					<c:if test="${ddcDaxxb.xb1 == 0 }">
					<td>男</td>
					</c:if>
					<c:if test="${ddcDaxxb.xb1 == 1 }">
					<td>女</td>
					</c:if>
					<th>身份证号码1</th>
					<td>${ddcDaxxb.sfzmhm1 }</td>
					<th>联系电话1</th>
					<td>${ddcDaxxb.lxdh1 }</td>
				</tr>
				<tr>
					<th>驾驶人姓名2</th>
					<td>${ddcDaxxb.jsrxm2 }(${ddcDaxxb.vcJsr2State })</td>
					<th>驾驶人性别2</th>
					<td>
					<c:if test="${ddcDaxxb.xb2 == 0 }">男</c:if>
					<c:if test="${ddcDaxxb.xb2 == 1 }">女</c:if>
					</td>
					<th>身份证号码2</th>
					<td>${ddcDaxxb.sfzmhm2 }</td>
					<th>联系电话2</th>
					<td>${ddcDaxxb.lxdh2 }</td>
				</tr>
				
				<tr>
    				<th>受理资料</th>
    				<td colspan="7">
    				<c:forEach items="${selectSlzls }" var="tb">
    					<p>${tb.dmms1 }</p>
    				</c:forEach>
    				</td>
    			</tr>
    			<tr>
    				<th>申请备注</th>
					<td colspan="7">${ddcDaxxb.bz }</td>
					
    			</tr>
    			<tr>
    				<th>归档备注</th>
					<td  colspan="7">${ddcDaxxb.gdbz }</td
    			</tr>
    			
    			<tr>
					<td colspan="2">
					<div class="imgdiv"> 
					<p>投保凭证照片</p>
					<a href="${ddcDaxxb.vcEbikeInsuranceImgShow }" target="_blank">
					<img src="${ddcDaxxb.vcEbikeInsuranceImgShow }"/>
					</a></div>
					</td>
					<td colspan="2">
					<div  class="imgdiv">
					<p>车辆合格证照片</p>
					<a href="${ddcDaxxb.vcQualifiedImgShow }" target="_blank">
					<img src="${ddcDaxxb.vcQualifiedImgShow }"/>
					</a>
					</div</td>
					<td colspan="2">
					<a href="${ddcDaxxb.vcShowEbikeImg }" target="_blank">
					<div  class="imgdiv">
					<p>车身照片</p>
					<img   src="${ddcDaxxb.vcShowEbikeImg }"/>
					</div></a></td>
					<td colspan="2">
					<a href="${ddcDaxxb.vcEbikeInvoiceImgShow }" target="_blank">
					<div  class="imgdiv">
					<p>购车发票</p>
					<img   src="${ddcDaxxb.vcEbikeInvoiceImgShow }"/>
					</div></a></td>
				</tr>
    			
				<tr>
					<td colspan="2">
					<div class="imgdiv"> 
					<p>驾驶人1照片</p>
					<a href="${ddcDaxxb.vcShowUser1Img }" target="_blank">
					<img src="${ddcDaxxb.vcShowUser1Img }"/>
					</a></div>
					</td>
					<td colspan="2">
					<div class="imgdiv"> 
					<p>驾驶人1身份证正面</p>
					<a href="${ddcDaxxb.vcUser1CardImg1Show }" target="_blank">
					<img   src="${ddcDaxxb.vcUser1CardImg1Show }"/>
					</a></div>
				</td>
				<td colspan="2">
					<div class="imgdiv"> 
					<p>驾驶人1身份证反面</p>
					<a href="${ddcDaxxb.vcUser1CardImg2Show }" target="_blank">
					<img  src="${ddcDaxxb.vcUser1CardImg2Show }"/>
					</a></div>
				</td>
				<td colspan="2">
					<div class="imgdiv"> 
					<p>驾驶人1在职证明或居住证</p>
					<a href="${ddcDaxxb.vcUser1WorkImgShow }" target="_blank">
					<img  src="${ddcDaxxb.vcUser1WorkImgShow }"/>
					</a></div>
				</td>
				</tr>
				
				<tr>
					<td colspan="2">
					<div class="imgdiv"> 
					<p>驾驶人2照片</p>
					<a href="${ddcDaxxb.vcShowUser2Img }" target="_blank">
					<img src="${ddcDaxxb.vcShowUser2Img }"/>
					</a></div>
					</td>
					<td colspan="2">
					<div class="imgdiv"> 
					<p>驾驶人2身份证正面</p>
					<a href="${ddcDaxxb.vcUser2CardImg1Show }" target="_blank">
					<img   src="${ddcDaxxb.vcUser2CardImg1Show }"/>
					</a></div>
				</td>
				<td colspan="2">
					<div class="imgdiv"> 
					<p>驾驶人2身份证反面</p>
					<a href="${ddcDaxxb.vcUser2CardImg2Show }" target="_blank">
					<img  src="${ddcDaxxb.vcUser2CardImg2Show }"/>
					</a></div>
				</td>
				<td colspan="2">
					<div class="imgdiv"> 
					<p>驾驶人2在职证明或居住证</p>
					<a href="${ddcDaxxb.vcUser2WorkImgShow }" target="_blank">
					<img  src="${ddcDaxxb.vcUser2WorkImgShow }"/>
					</a></div>
				</td>
				</tr>	
			</table>
		<!--endprint-->		
			<div class="btndiv">
		<button type="button" onclick="exportPage()" class="btn">打印</button>
		<button type="button" class="btn" onclick="history.back()">返回</button>
		</div>
		<!-- 违法信息 -->
		<c:if test="${trafficMsgs!=null }">
		<table class="table table-condensed">
				<caption style="text-align: center">违法详情</caption>
				<tr>
					<td>缴款编号</td>
					<td>车辆所有人</td>
					<td>车牌号</td>
					<td>违法日期</td>
					<td>违法地点</td>
					<td>违法行为</td>
					<td>罚款金额</td>
					<td>罚分</td>
				</tr>
				<c:forEach items="${trafficMsgs }" var="traffic">
					<tr>
						<td>${traffic.billNo }</td>
						<td>${traffic.carOwner }</td>
						<td>${traffic.licensePlateNo }</td>
						<td>${traffic.illegalTime }</td>
						<td>${traffic.illegalAddr }</td>
						<td>${traffic.illegalDesc }</td>
						<td>${traffic.punishAmt/100 }</td>
						<td>${traffic.punishScore }</td>
					</tr>
				</c:forEach>
			</table>
			</c:if>
    </div>		
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>档案详情</title>

<%@include file="../common/common.jsp"%>

<script type="text/javascript"
	src="<%=basePath%>static/js/qrcode.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	/*生成二维码*/
	 var qrcode = new QRCode(document.getElementById("qrcode"), {
        width : 90,//设置宽高
        height : 90
    });
    qrcode.makeCode('${ddcDaxxb.dabh}');
    
    if('${ddcDaxxb.jsrxm2}'){
	    var qrcode2 = new QRCode(document.getElementById("qrcode2"), {
	        width : 90,//设置宽高
	        height : 90
	    });
	    
	    qrcode2.makeCode('${ddcDaxxb.dabh}');
    }
    
    <%--
	if('${ddcDaxxb.vcShowEbikeImg}'==''){
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
	$("#table1").css('width', '650px');
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
	<div>
	<!--startprint-->
		<div class="idCard">
			<div class="idCard-title">
				<p>电动自行车二维码工作证</p>
			</div>

			<div class="idCard-middle">
				<div class="idCard-headimg-div">
					<img alt="" src="${ddcDaxxb.vcShowUser1Img}" class="idCard-headimg">
				</div>
				<div class="idCard-middle-msg">
					<ul>
						<li>人员姓名:<span>${ddcDaxxb.jsrxm1}</span></li>
						<li>联系电话:<span>${ddcDaxxb.lxdh1}</span></li>
						<c:if test="${ddcDaxxb.xb1 == 0}">
							<li>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:<span>男</span></li>
						</c:if>
						<c:if test="${ddcDaxxb.xb1 == 1}">
							<li>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:<span>女</span></li>
						</c:if>
						<li>所属单位:<span>${ddcDaxxb.ssdwName }</span></li>
						<li>所属协会:<span>${ddcDaxxb.hyxhzhName }</span></li>
					</ul>
				</div>
			</div>
			<div class="idCard-foot">
				<ul>
					<li>品牌型号:<span>${ddcDaxxb.ppxh}</span></li>
					<li>电&nbsp;机&nbsp;号:<span>${ddcDaxxb.djh}</span></li>
					<li>车身颜色:<span>${ddcDaxxb.cysyName}</span></li>
					<li>行驶区域:<span>${ddcDaxxb.xsqyName}</span></li>
					<li>车辆状态:<span>${ddcDaxxb.ztName}</span></li>
					<li>档案编号:<span>${ddcDaxxb.dabh}</span></li>
				</ul>

			</div>

			<div class="idCard-qrcode">
				<p>二维码仅供交警扫描</p>
				<div id="qrcode" class="idCard-qrcode-img"></div>
			</div>

		</div>


		<!-- 第二张 -->
		<c:if test="${ddcDaxxb.jsrxm2!=null}">
			<div class="idCard">
				<div class="idCard-title">
					<p>电动自行车二维码工作证</p>
				</div>

				<div class="idCard-middle">
					<div class="idCard-headimg-div">
						<img alt="" src="${ddcDaxxb.vcShowUser2Img}"
							class="idCard-headimg">
					</div>
					<div class="idCard-middle-msg">
						<ul>
							<li>人员姓名:<span>${ddcDaxxb.jsrxm2}</span></li>
							<li>联系电话:<span>${ddcDaxxb.lxdh2}</span></li>
							<c:if test="${ddcDaxxb.xb2 == 0}">
								<li>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:<span>男</span></li>
							</c:if>
							<c:if test="${ddcDaxxb.xb2 == 1}">
								<li>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:<span>女</span></li>
							</c:if>
							<li>所属单位:<span>${ddcDaxxb.ssdwName }</span></li>
							<li>所属协会:<span>${ddcDaxxb.hyxhzhName }</span></li>
						</ul>
					</div>
				</div>
				<div class="idCard-foot">
					<ul>
						<li>品牌型号:<span>${ddcDaxxb.ppxh}</span></li>
						<li>电&nbsp;机&nbsp;号:<span>${ddcDaxxb.djh}</span></li>
						<li>车身颜色:<span>${ddcDaxxb.cysyName}</span></li>
						<li>行驶区域:<span>${ddcDaxxb.xsqyName}</span></li>
						<li>车辆状态:<span>${ddcDaxxb.ztName}</span></li>
						<li>档案编号:<span>${ddcDaxxb.dabh}</span></li>
					</ul>

				</div>

				<div class="idCard-qrcode">
					<p>二维码仅供交警扫描</p>
					<div id="qrcode2" class="idCard-qrcode-img"></div>
				</div>

			</div>
		</c:if>
		<!--endprint-->
		
			<div class="btndiv">
		<button type="button" onclick="exportPage()" class="btn">打印</button>
		<button type="button" class="btn" onclick="history.back()">返回</button>
		</div>
	</div>
</body>
</html>

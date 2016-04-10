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

<title>流水详情</title>

<%@include file="../common/common.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
	
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
				} else {
					$("#img1_1").attr("src", '${ddcDaxxb.vcShowUser1Img}');
				}

			});
</script>

</head>

<body>
	<div class="maindiv">
		<form id="dgform" class="easyui-form" enctype="multipart/form-data"
			method="post">
			<table id="main" class="table table-condensed" border="1"
				cellpadding="0" cellspacing="0" width="98%">
				<tr>
					<th>流水号：</th>
					<td colspan="3" style="font-weight:bold;color:blue;">
						${ddcFlow.lsh }</td>
					<th>审批状态</th>
					<td  colspan="3">
						<c:if test="${ddcFlow.slyj==null }">审批中</c:if>
						<c:if test="${ddcFlow.slyj!=null }">已审批</c:if>
					</td>		
				</tr>
				<tr>
					<th>品牌型号</th>
					<td colspan="3">${ddcFlow.ppxh }</td>
					<th>电机号：</th>
					<td colspan="3">${ddcFlow.djh }</td>

				</tr>
				<tr>

					<th>车身颜色</th>
					<td colspan="3">${ddcFlow.cysyName }</td>
					<th>脚踏装置:</th>
					<c:if test="${ddcFlow.jtzz ==0 }">
						<td colspan="3">有</td>
					</c:if>
					<c:if test="${ddcFlow.jtzz ==1 }">
						<td colspan="3">无</td>
					</c:if>
				</tr>
				<tr>
					<th>驾驶人1姓名</th>
					<td colspan="3">${ddcFlow.jsrxm1 }</td>

					<th>驾驶人2姓名</th>
					<td colspan="3">${ddcFlow.jsrxm2 }</td>
				</tr>
				<tr>
					<th>驾驶人1性别</th>

					<td colspan="3"><c:if test="${ddcFlow.xb1==0 }">男</c:if> <c:if
							test="${ddcFlow.xb1==1 }">女</c:if></td>

					<th>驾驶人2性别</th>
					<td colspan="3"><c:if test="${ddcFlow.xb2==0 }">男</c:if> <c:if
							test="${ddcFlow.xb2==1 }">女</c:if></td>
				</tr>
				<tr>
					<th>身份证号码1</th>
					<td colspan="3">${ddcFlow.sfzmhm1 }</td>
					<th>身份证号码2</th>
					<td colspan="3">${ddcFlow.sfzmhm2 }</td>
				</tr>
				<tr>
					<th>联系电话1</th>
					<td colspan="3">${ddcFlow.lxdh1 }</td>
					<th>联系电话2</th>
					<td colspan="3">${ddcFlow.lxdh2 }</td>
				</tr>
				<tr>
					<th>行驶区域</th>
					<td colspan="3">${ddcFlow.xsqyName }</td>
					<th>备注</th>
					<td colspan="3">${ddcFlow.bz }</td>
				</tr>
				<tr>
					<th>受理资料：</th>
					<td colspan="7"><c:forEach items="${slzlDdcSjzds }" var="bg">
	   				${bg.dmms1 }<br />
						</c:forEach></td>
				</tr>
				<tr>
					<th>退办原因：</th>
					<td colspan="7"><c:forEach items="${tbyyDdcSjzds }" var="bg">
	   				${bg.dmms1 }<br />
						</c:forEach></td>
				</tr>
				
				<tr>

					<th>受理意见：</th>
					<td colspan="3">${ddcFlow.slyj=='0'?'同意':'不同意' }</td>
					<th>受理日期：</th>
					<td colspan="3"><fmt:formatDate value="${ddcFlow.slrq }" pattern="yyyy-MM-dd"/> </td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人1照片</p>
							<img id="img1_1" src="<%=basePath%>static/images/iconfont-wu.png" />
						</div>
					</td>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人2照片</p>
							<img id="img2_2" src="<%=basePath%>static/images/iconfont-wu.png" />
						</div>
						<br />
					</td>
					<td colspan="4">
						<div class="imgdiv">
							<p>车身照片</p>
							<img id="img_0" src="<%=basePath%>static/images/iconfont-wu.png" />
						</div>
						<br />
					</td>
				</tr>
				
			</table>
			<div class="btndiv">
			<button type="button" class="btn" onclick="history.back()">返回</button>
			</div>
		</form>

	</div>
</body>
</html>

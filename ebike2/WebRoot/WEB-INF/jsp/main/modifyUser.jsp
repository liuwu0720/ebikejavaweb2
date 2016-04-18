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

<title>My JSP 'modifyUser.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>修改密码</title>

<%@include file="../common/common.jsp"%>

<script type="text/javascript">
	function updateModify(){
		$.messager.progress({
			text:"正在处理，请稍候..."
		});
		$('#dgform').form('submit', {
			url : "<%=basePath%>userAction/saveModifyUser",
			onSubmit : function() {
				
				var isValid = $("#dgform").form('enableValidation').form(
						'validate');
				if (!isValid) {
					$.messager.progress('close'); // 如果表单是无效的则隐藏进度条
				}
				return isValid; // 返回false终止表单提交
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON
				if (data.isSuccess) {
					window.location.href="<%=basePath%>mainAction/getWelcome"
				}else{
					alert(data.message);
				}
				$.messager.progress('close'); // 如果提交成功则隐藏进度条

			}

		});
	}
	
	function backHome(){
		window.location.href="<%=basePath%>mainAction/getWelcome"
	}
	
	function updateModify2(){
		$.messager.progress({
			text:"正在处理，请稍候..."
		});
		$('#dgform2').form('submit', {
			url : "<%=basePath%>userAction/saveModifySSdw",
			onSubmit : function() {
				
				var isValid = $("#dgform2").form('enableValidation').form(
						'validate');
				if (!isValid) {
					$.messager.progress('close'); // 如果表单是无效的则隐藏进度条
				}
				return isValid; // 返回false终止表单提交
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON
				if (data.isSuccess) {
					window.location.href="<%=basePath%>mainAction/getWelcome"
				}else{
					alert(data.message);
				}
				$.messager.progress('close'); // 如果提交成功则隐藏进度条

			}

		});
	}
</script>

</head>

<body>
	<div id="main" class="maindiv">
	<p style="color: red">${message }</p>
	<c:if test="${ddcHyxhBase!=null }">
		<form id="dgform" class="easyui-form" enctype="multipart/form-data"
			action="" method="post">
			<table class="table">
				<tr>
					<th>账号</th>
					<td>${ddcHyxhBase.hyxhzh }</td>
				</tr>
				<tr>
					<th>协会名称</th>
					<td>${ddcHyxhBase.hyxhmc }</td>
				</tr>
				<tr>
					<th>协会地址</th>
					<td>${ddcHyxhBase.hyxhdz }</td>
				</tr>
				<tr>
					<th>负责人</th>
					<td>${ddcHyxhBase.hyxhfzr }</td>
				</tr>
				<tr>
					<th>负责人电话</th>
					<td>${ddcHyxhBase.hyxhfzrdh }</td>
				</tr>
				<tr>
					<th>总配额</th>
					<td>${ddcHyxhBase.hyxhsjzpe }</td>
				</tr>
				<tr>
					<th>用户密码</th>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true" name="userPassword"
						value="${ddcHyxhBase.hyxhmm }"></input></td>
				</tr>
			</table>
			<input class="easyui-validatebox" type="hidden" name="id"
				value="${ddcHyxhBase.id }"></input>
			<div class="table-btndiv">
				<button type="button" onclick="updateModify()">修改</button>
				<button type="button" onclick="backHome()">返回</button>
			</div>
		</form>
</c:if>

	<c:if test="${ddcHyxhSsdw!=null }">
		<form id="dgform2" class="easyui-form" enctype="multipart/form-data"
			action="" method="post">
			
			<table class="table">
					
				<tr>
					<th>账号</th>
					<td><input class="easyui-validatebox" type="text" name="userCode" readonly="readonly"
						value="${ddcHyxhSsdw.userCode }"></input></td>
				</tr>
				<tr>
					<th>单位名称</th>
					<td><input class="easyui-validatebox" type="text" name="dwmc" 	data-options="required:true" 
						value="${ddcHyxhSsdw.dwmc }"></input></td>
				</tr>
				<tr>
					<th>用户密码</th>
					<td><input class="easyui-validatebox" type="password"
						data-options="required:true" name="passWord"
						value="${ddcHyxhSsdw.passWord }"></input></td>
				</tr>
				<tr>
					<th> 组织机构代码证号</th>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true" name="zzjgdmzh"
						value="${ddcHyxhSsdw.zzjgdmzh }"></input></td>
				</tr>
				<tr>
					<th>地址</th>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true" name="zsdz"
						value="${ddcHyxhSsdw.zsdz }"></input></td>
				</tr>
				<tr>
					<th>联系人</th>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true" name="lxr"
						value="${ddcHyxhSsdw.lxr }"></input></td>
				</tr>
				<tr>
					<th>联系电话</th>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true" name="lxdh"
						value="${ddcHyxhSsdw.lxdh }"></input></td>
				</tr>
				<tr>
					<th>总配额</th>
					<td><input class="easyui-validatebox" type="text" name="dwpe" readonly="readonly"
						value="${ddcHyxhSsdw.dwpe }"></input></td>
				</tr>
				<tr>
					<th>上传营业执照(800*600)</th>
					<td>
						<input  type="file" id="file_upload"
						name="file_upload" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="imgdiv"> 
							<p>营业执照照片</p>
						<a href="${ddcHyxhSsdw.vcShowPath }" target="_blank">
						<img id="img1_1"  src="${ddcHyxhSsdw.vcShowPath }"/></a></div>
					</td>
				</tr>
			</table>
			<input  type="hidden" name="id" value="${ddcHyxhSsdw.id }"></input>
			<input  type="hidden" name="shFlag" value="${ddcHyxhSsdw.shFlag }"></input>
			<input  type="hidden" name="shr" value="${ddcHyxhSsdw.shr }"></input>
			<input  type="hidden" name="shbm" value="${ddcHyxhSsdw.shbm }"></input>
			<input  type="hidden" name="shrq" value="${ddcHyxhSsdw.shrq }"></input>
			<input  type="hidden" name="hyxhzh" value="${ddcHyxhSsdw.hyxhzh }"></input>
			<input  type="hidden" name="vcPicPath" value="${ddcHyxhSsdw.vcPicPath }"></input>
			<input  type="hidden" name="totalPe" value="${ddcHyxhSsdw.totalPe }"></input>
			<div class="table-btndiv">
				<button type="button" onclick="updateModify2()">修改</button>
				<button type="button" onclick="backHome()">返回</button>
			</div>
		</form>
</c:if>
	</div>
</body>
</html>

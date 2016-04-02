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
	
</script>

</head>

<body>
	<div class="tablediv">
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

	</div>
</body>
</html>

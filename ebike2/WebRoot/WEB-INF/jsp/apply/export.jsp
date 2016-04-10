<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'export.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@include file="../common/common.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>static/js/export.js"></script>
<script type="text/javascript">
//var currTabId=parent.currTabId;
//var parentObj=parent.$("#"+currTabId)[0].contentWindow;
window.onload=function(){
	document.getElementById("hlf").value=parent.exportString;
	
};


function btnExcel(){
	document.getElementById("type").value="excel";
	document.getElementById("formAction").submit();
}
function btnWord(){
	document.getElementById("type").value="word";
	document.getElementById("formAction").submit();
}
</script>
</head>
<body style="padding:20px; align:center" >
     <form id='formAction' action="<%=basePath%>userAction/exportExcel"  method="post">
     	<font size="3px">Please select the type of export for export ......</font><br /><br />
     	<input id="dgg" class="easyui-linkbutton" type="button" value="Excel" onclick="btnExcel()"/>
     	<input id="dgg" class="easyui-linkbutton" type="button" style="margin-left:50px;" value="Word" onclick="btnWord()"/>
 		<input id="hlf" name='hfs'  type="hidden"/>
 		<input id="type" name='type'  type="hidden"/>
    </form>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>市交警特殊行业电动自行车二维码管理系统</title>
</head>
<frameset rows ="112px,*" border="0">
  	<frame noresize="noresize"  scrolling="no" name="header" id="header" src="<%=basePath%>mainAction/getHeader"/>
    <frameset id="frameset" cols="230px,*" border="0">
        <frame noresize="noresize" frameborder="0" scrolling="auto"   id="sidebar" name="sidebar" src="<%=basePath%>mainAction/getSidebar">
        <frame noresize="noresize" frameborder="0" scrolling="auto"  id="main" name="main" src="<%=basePath%>mainAction/getWelcome">
    </frameset>
</frameset><noframes></noframes>


<body>
	很抱歉，您使用的浏览器不支持框架功能，请采用新版本的浏览器。
</body>
</html>
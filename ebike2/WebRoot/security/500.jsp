<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="<%=basePath %>static/css/backhome.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>static/js/jquery-1.7.2.min.js" language="javascript"></script>

</head>

<body class="main">
	<div class="error">
    	<div class="error_con">
        	<div class="error_th"></div>
            <div class="error_info">系统报错，请联系管理员！<br/>
            <h2>错误内容： </h2>
           <%=exception%>
            </div> 
            <!-- <div class="operate"><a href="#" class="f_btn">关闭</a></div>  -->          
        </div>
        
    </div>	
</body>
</html>
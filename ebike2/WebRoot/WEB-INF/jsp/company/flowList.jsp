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

<title>业务流水查询</title>

<%@include file="../common/common.jsp"%>


<script type="text/javascript">
$(document).ready(function(){
	$.ajaxSetup ({
		   cache: false //缓存
		});
	var h = getHeight('dg');
	var size = getPageSize(h);
	var w = getWidth(400);
	$("#dg").datagrid({

		url : "<%=basePath%>ssdwAction/queryFlowList" ,
		title :  "业务流水查询",
		iconCls : 'icon-search',
		striped : true,
		fitColumns:true,   //数据列太少 未自适应
		pagination : true,
		rownumbers : true,
		pageSize:size,
		singleSelect : true,//只选中单行
		height:h,
		width:w,
		loadMsg:'正在加载,请稍等...',
		columns : [ [{
			field : 'DWMC',
			title : '单位名称',
			align:'center',
			width : 120
		},{
			field : 'LSH',
			title : '流水号',
			align:'center',
			width : 220
		},{
			field : 'DABH',
			title : '档案编号',
			align:'center',
			width : 220
		},{
			field : 'CPHM',
			title : '车牌号',
			align:'center',
			width : 120
		},{
			field : 'DJH',
			title : '电机号',
			align:'center',
			width : 120
		},{
			field : 'YWLX',
			title : '业务类型',
			align:'center',
			width : 120
		},{
			field : 'SSQY',
			title : '所属区域',
			align:'center',
			width : 120
		},{
			field : 'SLYJ',
			title : '受理意见',
			align:'center',
			width : 120,
			formatter:function(value,index){
				if(value == 0){
					return "同意";
				}else if(value == 1){
					return "不同意";
				}else{
					return "审批中 ";
				}
			}
		},{
			field : 'SLRQ',
			title : '申请时间',
			align:'center',
			width : 120,
			formatter:function(value,index){
				var unixTimestamp = new Date(value);   
				return unixTimestamp.toLocaleString();
			}   
		},{
			field : 'null',
			title:'操作',
			align:'center',
			width : 120,
			formatter:function(value,row,index){
				
				var query = "<a  href='javascript:void(0)'  onclick='queryRow("+row.ID+")'>查看</a>";
				return query;	
			}
		}

		] ],

		onLoadSuccess:function(){  
            $('#dg').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题  
        }
	});
	

});


//查询功能
function doSearch(){
	 $('#dg').datagrid('load',{
		ywlx: $("#ywlx").combobox('getValue'),
		djh: $('#djh').val(),
		cphm:$("#cphm").val(),
		dtstart:$('#dtstart').datebox('getValue'),// 获取日期输入框的值
		dtend:$('#dtend').datebox('getValue'),
		lsh:$("#lsh").val()
	}); 
}


//查看
function queryRow(id){
	window.location.href="<%=basePath%>ebikeWaterAction/queryInfoById?id="+id
	
}



</script>
</head>
<body class="easyui-layout">

	<div>
		<div id="tb" style="padding: 5px; background: #E8F1FF;">
				<span>业务类型</span>
				<select class="easyui-combobox" style="width:100px;height:32px; " id="ywlx">
					<option value="">--请选择--</option>
					<c:forEach items="${ywylxs }" var="lx">
						<option value="${lx.dmz }">${lx.dmms1 }</option>
					</c:forEach>
				</select>	
				<span>受理时间</span>
				<input id="dtstart" type="text" class="easyui-datebox" style="height: 30px;"></input> 至：  
				<input id="dtend" type="text" class="easyui-datebox" style="height: 30px;"></input>	<br>	
				<span>车牌号:</span> <input id="cphm" 
					class="easyui-validatebox" type="text" > 
				<span>电机号:</span> <input id="djh" 
					class="easyui-validatebox" type="text" >	
				<span>流水号:</span> <input id="lsh" 
					class="easyui-validatebox" type="text" >
				<a class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
			</div>
		<table id="dg" style="width:90%;">
		</table>
	</div>

	

</body>
</html>
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

<title>My JSP 'companyInfos.jsp' starting page</title>

<%@include file="../common/common.jsp"%>


<script type="text/javascript">
$(document).ready(function(){
	$.ajaxSetup ({
		   cache: false //缓存
		});
	var h = getHeight('dg');
	var size = getPageSize(h);
	var w = getWidth(400);
	var randomNu = (new Date().getTime()) ^ Math.random();
	$("#dg").datagrid({

		url : "<%=basePath%>ssdwQueryAction/queryAll?time=" + randomNu,
		title :  "电动车档案列表",
		iconCls : 'icon-danweixinxi',
		striped : true,
		fitColumns:true,   //数据列太少 未自适应
		pagination : true,
		rownumbers : true,
		pageSize:size,
		 pageList:[10,20,30,40,50,500],
		striped:true,  //striped  是否显示斑马线效果。
		height:h,
		width:w,
		loadMsg:'正在加载,请稍等...',
		columns : [ [{
			field : 'ID',
			title : 'ID',
			checkbox : true,
			align:'center',
			width : 10
		},{
			field : 'DABH',
			title : '档案编号',
			align:'center',
			width : 80,
			formatter:function(value,row,index){
				var query = "<a  href='javascript:void(0)'  onclick='queryDetaiList(\""+row.DABH+"\")'>"+value+"</a>";
				return query;
			}
		},{
			field : 'CPHM',
			title : '车牌号',
			align:'center',
			width : 50
		},{
			field : 'DJH',
			title : '电机号',
			align:'center',
			width : 50
		},{
			field : 'JSRXM1',
			title : '驾驶人1',
			align:'center',
			width : 50
		},{
			field : 'CSYSNAME',
			title : '车身颜色',
			align:'center',
			width : 50
		},{
			field : 'XSQY',
			title : '行驶区域',
			align:'center',
			width : 50
		},{
			field : 'SLRQ',
			title : '备案时间',
			align:'center',
			width : 120,
			formatter:function(value,index){
				var unixTimestamp = new Date(value);   
				return unixTimestamp.toLocaleString();
			}   
		},{
			field : 'ZT',
			title : '车辆状态',
			align:'center',
			width : 50,
			formatter:function(value,index){
				if(value == '注销'){
					
					return "<p style='color:red'>注销</p>";
				}else{
					return value;
				}
			}
		},{
			field : 'null',
			title:'操作',
			align:'center',
			width : 80,
			formatter:function(value,row,index){
				var query = "<a  href='javascript:void(0)'  onclick='queryRow("+row.ID+")'>查看</a>&nbsp;&nbsp;&nbsp;";
				var print = "<a  href='javascript:void(0)'  onclick='queryQRCode("+row.ID+")'>打印</a>&nbsp;&nbsp;&nbsp;";
				//var update = "<a  href='javascript:void(0)'  onclick='updateRow("+row.ID+")'>更正</a>";
				return query+print;	
			
			}
		}

		] ],
		toolbar : [{
			id : 'btn3',
			text : '导出',
			iconCls : 'icon-print',
			handler : function() {
				exportRowData();
			}
		}],
		onLoadSuccess:function(){  
            $('#dg').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题  
        }
	});
	
	//行驶区域
	$('#xsqy1').combobox({
		 url:'<%=basePath%>applyAction/getAllAreaAjax',    
		    valueField:'dmz',    
		    textField:'dmms1'
	})
	$('#zt').combobox({
		valueField: 'label',
		textField: 'value',
		data: [{
			label: 'A',
			value: '正常'
		},{
			label: 'E',
			value: '注销'
		}]
	})
});




//查询功能
function doSearch(){
	 $('#dg').datagrid('load',{
		dabh: $("#dabh").val(),
		djh: $('#djh').val(),
		cphm:$("#cphm").val(),
		jsrxm1:$("#jsrxm1").val(),
		xsqy:$("#xsqy1").combobox('getValue'),
		 zt:$("#zt").combobox("getValue")
	}); 
}


//查看
function queryRow(id){
	window.location.href="<%=basePath%>ebikeChangeAction/queryDetailById?id="+id;
}


//导出EXCEL
function exportRowData(){
	var titleArr = ["档案编号","车牌号","电机号","驾驶人1","车身颜色","行驶区域","审检日期"]; 
	var keysArr =["DABH","CPHM","DJH","JSRXM1","CSYSNAME","XSQY","SYRQ"];
	var rows = $('#dg').datagrid('getData').rows;
	for(var i in rows) {
		if(rows[i]['SLYJ'] == null){
			  rows[i]['SLYJ'] = "等待协会审批";
			}else if(rows[i]['SLYJ'] == 0){
				 rows[i]['SLYJ'] ="已审核(同意) ";
			}else if(rows[i]['SLYJ'] == 1){
				 rows[i]['SLYJ'] = "已审核(拒绝) ";
			}
		rows[i]['SYRQ'] = getLocalTime(rows[i]['SYRQ']);
	}
	var actionUrl = '<%=basePath%>ebikeQueryAction/exportExcel';
	var fileName="电动车变更信息";
	var content = JSON.stringify(rows);
	commonExcelExport(titleArr,keysArr,content,actionUrl,fileName);
}
//查看该档案的流水记录
function queryDetaiList(obj){
	window.location.href="<%=basePath%>ssdwAction/getFlowList?dabh="+obj
}


//查看基本信息以及二维码
function queryQRCode(id){
	window.location.href="<%=basePath%>ebikeQueryAction/queryQRCodeById?id="+id
}
//档案更正
function updateRow(id){
	window.location.href="<%=basePath%>ebikeChangeAction/updateDetailById?id="+id;
}

</script>
</head>
<body class="easyui-layout">

	<div>
		<div id="tb" style="padding: 5px; background: #E8F1FF;">
				<span>档案编号</span>
				<input id="dabh" type="text" class="easyui-validatebox" ></input>
				<span>电机号</span> <input id="djh"
					class="easyui-validatebox" type="text" >
				<span>车牌号</span> <input id="cphm"
					class="easyui-validatebox" type="text" >
				<br/>
				<span>驾驶人1</span> <input id="jsrxm1" 
				class="easyui-validatebox" type="text" >
				<span>行驶区域</span>	
				<input id="xsqy1" style="height:30px;width: 80px;" >
				<span>车辆状态</span>
				<input id="zt" style="height: 32px;">
				<a class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
			</div>
		<table id="dg" style="width:90%;">
		</table>
	</div>
	

</body>
</html>
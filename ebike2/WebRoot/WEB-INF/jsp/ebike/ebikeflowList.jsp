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
	var randomNu = (new Date().getTime()) ^ Math.random();
	$("#dg").datagrid({

		url : "<%=basePath%>ebikeWaterAction/queryAll?time=" + randomNu,
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
		toolbar : [ {
			id : 'btn1',
			text : '导出',
			iconCls : 'icon-print',
			handler : function() {
				excelExport();
			}
		}],
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
			formatter:function(value,row,index){
				if(value == null){
					if(row.SL_INDEX == 0){
						return "等待协会审批";
					}else{
						return "等待交警审批";
					}
				}else if(value == 0){
					return "已审核(同意) ";
				}else if(value == 1){
					return "已审核(拒绝) ";
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
	
	//加载下拉框
	$('#ssdw').combobox({    
	    url:'<%=basePath%>companyAction/getAllCompanyAjax',    
	    valueField:'id',    
	    textField:'dwmc'   
	}); 
});


//查询功能
function doSearch(){
	 $('#dg').datagrid('load',{
		ywlx: $("#ywlx").combobox('getValue'),
		djh: $('#djh').val(),
		cphm:$("#cphm").val(),
		dtstart:$('#dtstart').datebox('getValue'),// 获取日期输入框的值)
		dtend:$('#dtend').datebox('getValue'),
		ssdw:$("#ssdw").combobox('getValue')
	}); 
}


//查看
function queryRow(id){
	window.location.href="<%=basePath%>ebikeWaterAction/queryInfoById?id="+id
	
}

function excelExport(){
	var titleArr = ["单位名称","流水号","车牌号","电机号","业务类型","行驶区域","意见","申请时间"]; 
	var keysArr =["DWMC","LSH","CPHM","DJH","YWLX","SSQY","SLYJ","SLRQ"];
	var rows = $('#dg').datagrid('getData').rows;
	for(var i in rows) {
		if(rows[i]['SLYJ'] == 0){
			rows[i]['SLYJ'] = "办结";
		}else{
			rows[i]['SLYJ'] = "退办";
		}
		rows[i]['SLRQ'] = getLocalTime(rows[i]['SLRQ']);
	}
	var actionUrl = '<%=basePath%>ebikeQueryAction/exportExcel';
	var fileName="业务流水信息";
	var content = JSON.stringify(rows);
	commonExcelExport(titleArr,keysArr,content,actionUrl,fileName); 
	

}

</script>
</head>
<body class="easyui-layout">

			<div class="searchdiv"> 
		
			<div id="tb" >
				<span>业务类型</span>
				<select class="easyui-combobox" style="width:100px;height:32px; " id="ywlx">
					<option value="">--请选择--</option>
					<c:forEach items="${ywylxs }" var="lx">
						<option value="${lx.dmz }">${lx.dmms1 }</option>
					</c:forEach>
				</select>	
				<span>申请时间</span>
				<input id="dtstart" type="text" class="easyui-datebox" style="height: 30px;"></input> 至：  
				<input id="dtend" type="text" class="easyui-datebox" style="height: 30px;"></input><br>		
				<span>车牌号:</span> <input id="cphm" name="cphm"
					class="easyui-validatebox" type="text" >
				<span>电机号:</span> <input id="djh" name="djh"
					class="easyui-validatebox" type="text" >
				<span>公司名称:</span>
				 <input id="ssdw" style="height: 32px;">
				<a class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
			</div>
		<table id="dg" style="width:90%;">

		</table>
	</div>

	

</body>
</html>
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

		url : "<%=basePath%>ebikeChangeAction/queryAll?time=" + randomNu,
		title :  "电动车变更管理",
		iconCls : 'icon-danweixinxi',
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
			field : 'LSH',
			title : '流水号',
			align:'center',
			width : 50
		},{
			field : 'SSDWNAME',
			title : '单位名称',
			align:'center',
			width : 80,
			formatter:function(value,row,index){
				var query = "<a  href='javascript:void(0)'  onclick='queryHyxhDwDetail(\""+row.SSDWID+"\")'>"+value+"</a>";
				return query;	
			}
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
			width : 80
		},{
			field : 'PPXH',
			title : '品牌型号',
			align:'center',
			width : 50
		},{
			field : 'JSRXM1',
			title : '驾驶人1',
			align:'center',
			width : 50
		},{
			field : 'XSQYNAME',
			title : '行驶区域',
			align:'center',
			width : 50
		},{
			field : 'SLYJ',
			title : '审批状态',
			align:'center',
			width : 80,
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
			field : 'YWLXNAME',
			title : '类型',
			align:'center',
			width : 50
		},{
			field : 'SLRQ',
			title : '申请时间',
			align:'center',
			width : 80,
			formatter:function(value,index){
				var unixTimestamp = new Date(value);   
				return unixTimestamp.toLocaleString();
			}   
		},{
			field : 'null',
			title:'操作',
			align:'center',
			width : 150,
			formatter:function(value,row,index){
				
				var query = "<a  href='javascript:void(0)'  onclick='queryRow("+row.ID+")'>查看</a>&nbsp;&nbsp;&nbsp; |&nbsp;&nbsp;&nbsp; ";
				var update = "<a  href='javascript:void(0)'  onclick='updateRow("+row.ID+")'>审批</a>&nbsp;&nbsp;&nbsp;";
				if(row.SL_INDEX == 0){
					return query+update;
				}else{
					return query;
				}
							
			
			}
		}

		] ],

		onLoadSuccess:function(){  
            $('#dg').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题  
        }
	});
	
	//加载所属单位下拉框
	$('#ssdw').combobox({    
	    url:'<%=basePath%>companyAction/getAllCompanyAjax',    
	    valueField:'id',    
	    textField:'dwmc'   
	}); 
	//行驶区域
	$('#xsqy1').combobox({
		 url:'<%=basePath%>applyAction/getAllAreaAjax',    
		    valueField:'dmz',    
		    textField:'dmms1'
	})
});

function excelExport(){
	var titleArr = ["单位名称","档案编号","车牌号","电机号","品牌型号","驾驶人1","行驶区域","审批状态","业务类型","申请日期"]; 
	var keysArr =["SSDWNAME","DABH","CPHM","DJH","PPXH","JSRXM1","XSQYNAME","SLYJ","YWLXNAME","SLRQ"];
	var rows = $('#dg').datagrid('getData').rows;
	for(var i in rows) {
		if(rows[i]['SLYJ'] == null){
			rows[i]['SLYJ']=  "等待协会审批";
		}else if(rows[i]['SLYJ'] == 0){
			rows[i]['SLYJ']=  "已审核(同意) ";
		}else if(rows[i]['SLYJ'] == 1){
			rows[i]['SLYJ']=  "已审核(拒绝) ";
		}
		rows[i]['SLRQ'] = getLocalTime(rows[i]['SLRQ']);
	}
	var actionUrl = '<%=basePath%>ebikeQueryAction/exportExcel';
	var fileName="电动车变更信息";
	var content = JSON.stringify(rows);
	commonExcelExport(titleArr,keysArr,content,actionUrl,fileName); 
	

}
//查询功能
function doSearch(){
	 $('#dg').datagrid('load',{
		 	lsh:$("#lsh").val(),
			dabh: $("#dabh").val(),
			djh: $('#djh').val(),
			cphm:$("#cphm").val(),
			jsrxm1:$("#jsrxm1").val(),
			ywlx:$("#ywlx").combobox('getValue'),
			slyj:$("#slyj").combobox('getValue'),
			xsqy:$("#xsqy1").combobox('getValue'),
			ssdw:$("#ssdw").val()
	}); 
}
//审批
function updateRow(id){
	$.messager.progress({
		text:"正在处理，请稍候..."
	});
	window.location.href="<%=basePath%>ebikeWaterAction/queryInfoById?id="+id+"&&type=1";
	
}
//查看该档案的流水记录
function queryDetaiList(obj){
	window.location.href="<%=basePath%>ssdwAction/getFlowList?dabh="+obj
}

//查看
function queryRow(id){
	$.messager.progress({
		text:"正在处理，请稍候..."
	});
	window.location.href="<%=basePath%>ebikeWaterAction/queryInfoById?id="+id
	
}

//查看单位详情
function queryHyxhDwDetail(obj){

	$.ajax({
		type: "GET",
 	    url: "<%=basePath%>ssdwAction/querySsdwInfoById",
 	   data:{
 		id:obj
	   }, 
	   dataType: "json",
	   success:function(data){
			  if(data){
				 $('#dwformDiv').dialog('open').dialog('setTitle', '详情信息');
				 $('#dwform').form('load', data);
				 $("#dwimg_a").attr("href",data.vcShowPath);
				 $("#dwimg").attr("src",data.vcShowPath);
			  }
		  }
	})
}
</script>
</head>
<body class="easyui-layout">

	<div class="searchdiv">
		
			<div id="tb" >
				<span>档案编号</span>
				<input id="dabh" type="text" class="easyui-validatebox" name="dabh" ></input>
				<span>电机号</span> <input id="djh" 
					class="easyui-validatebox" type="text" >
				<span>车牌号</span> <input id="cphm" 	class="easyui-validatebox" type="text" >
				</select> 
				<span>流水号</span>
				 <input id="lsh"class="easyui-validatebox" type="text" >
				<br>
				<span>行驶区域</span>	
				<input id="xsqy1" style="height:30px;" >
				<span>公司名称</span>
				 <input id="ssdw" style="height: 32px;">
				 <span>类型</span>
				<select class="easyui-combobox" style="width:100px;height:32px; " id="ywlx">
					<option value="">所有</option>
					<option value="B">变更</option>
					<option value="C">转移</option>
					<option value="D">注销</option>
				</select>
				<span>审批状态</span>
				<select class="easyui-combobox" style="width:100px;height:32px; " id="slyj">
					<option value="">所有</option>
					<option value="-1">审批中</option>
					<option value="0">已同意</option>
					<option value="1">已拒绝</option>
				</select>
				<a class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
			</div>
		<table id="dg" style="width:90%;">

		</table>
	</div>
	
	 <!-- 单位信息详情 -->
	<div id="dwformDiv" class="easyui-dialog"
		style="width:500px;height:400px;padding:10px 20px 20px 20px;" closed="true">
		<form id="dwform" class="easyui-form">
			<table class="dialogtable" id="table2">
				<tr>
					<th>单位名称</th>
					<td><input  name=dwmc type="text" class="easyui-validatebox" style="height: 32px;width:100%;" readonly="readonly"></td>
				</tr>
				<tr>
					<th>组织机构代码证号</th>
					<td><input  name=zzjgdmzh type="text" class="easyui-validatebox" style="height: 32px;width:100%;" readonly="readonly"></td>
				</tr>
				<tr>
					<th>地址</th>
					<td><input  name="zsdz" type="text" class="easyui-validatebox" style="height: 32px;width:100%;" readonly="readonly"></td>
				</tr>
				<tr>
					<th>联系人</th>
					<td><input  name="lxr" type="text" class="easyui-validatebox" style="height: 32px;" readonly="readonly"></td>
				</tr>
				<tr>
					
					<th>联系电话</th>
					<td><input  name="lxdh" type="text" class="easyui-validatebox" style="height: 32px;" readonly="readonly"></td>
				</tr>
				<tr>
					<th>剩余配额</th>
					<td><input  name="dwpe" type="text" class="easyui-validatebox" style="height: 32px;" readonly="readonly"></td>
				</tr>
				<tr>
					<th>实际总配额</th>
					<td><input  name="totalPe" type="text" class="easyui-validatebox" style="height: 32px;" readonly="readonly"></td>
				</tr>
				<tr>
					
					<th>营业执照图片</th>
					<td><a id="dwimg_a" target="_blank"><img id="dwimg"  class="easyui-validatebox" style="width:300px"   /></a></td>
				</tr>
			</table>
				
		</form>
	</div>
</body>
</html>
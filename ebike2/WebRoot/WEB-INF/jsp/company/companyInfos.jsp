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
	var randomNu = (new Date().getTime()) ^ Math.random();
	$("#dg").datagrid({

		url : "<%=basePath%>companyAction/queryAll?time=" + randomNu,
		title :  "所属单位信息管理",
		iconCls : 'icon-danweixinxi',
		striped : true,
		fitColumns:true,   //数据列太少 未自适应
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height:700,
		columns : [ [{
			field : 'dwmc',
			title : '单位名称',
			align:'center',
			width : 120
		},{
			field : 'zzjgdmzh',
			title : '机构代码',
			align:'center',
			width : 220
		},{
			field : 'lxr',
			title : '联系人',
			align:'center',
			width : 120
		},{
			field : 'lxdh',
			title : '联系电话',
			align:'center',
			width : 120
		},{
			field : 'dwpe',
			title : '单位配额',
			align:'center',
			width : 120
		},{
			field : 'sqrq',
			title : '申请时间',
			align:'center',
			width : 120,
			formatter:function(value,index){
				var unixTimestamp = new Date(value);   
				return unixTimestamp.toLocaleString();
			}   
		},{
			field : 'zt',
			title : '状态',
			align:'center',
			width : 120,
			formatter:function(value,index){
				if(value == 0){
					return "禁用";
				}else{
					return "启用";
				}
			}
		},{
			field : 'id',
			title:'操作',
			align:'center',
			width : 120,
			formatter:function(value,row,index){
				
				return "<a  href='javascript:void(0)'  onclick='deleteRow("+row.id+")'>查看</a>";
			}
		}

		] ],

		toolbar : [ {
			id : 'btnadd2',
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				addRowData();
			}
		}],
		onLoadSuccess:function(){  
            $('#dg').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题  
        }
	});
	
	
});
//查询功能
function doSearch(){
	$('#dg').datagrid('load',{
		dwmc: $('#itemid').val(),
		lxr: $('#productid').val()
	});
}
</script>
</head>
<body class="easyui-layout">

	<div>
	<table id="dg" style="width:90%;">
		<div id="tb" style="padding: 5px; background: #E8F1FF;">
			<span>单位名称:</span> <input id="itemid"
				style="line-height:26px;border:1px solid #ccc"> <span>
				联系人:</span> <input id="productid"
				style="line-height:26px;border:1px solid #ccc"> 
				<a 	class="easyui-linkbutton" plain="true" onclick="doSearch()" iconCls="icon-search" >查询</a>
		</div>
	</table>
</div>
	<!-- 点编辑时弹出的表单 -->
	<div id="dgformDiv" class="easyui-dialog"
		style="width:550px;height:450px;padding:10px 20px 20px 20px;"
		closed="true" buttons="#dlg-buttons2">
		<form id="dgform" class="easyui-form" method="post"
			>
			<table class="table">
				<tr style="display: none">
					<td>id</td>
					<td><input class="easyui-validatebox" type="text" name="id" ></input>
					</td>
				</tr>
				<tr>
					<td>单位名称：</td>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true" name="dwmc" ></input></td>
				</tr>
				<tr>
					<td> 组织机构代码证号：</td>
					<td><input class="easyui-validatebox" type="text"
						name="zzjgdmzh" style="height: 32px;width:200px;"></input>
						</td>
				</tr>
				<tr>
					<td>住所地址</td>
					<td><input class="easyui-numberspinner" name="zsdz" data-options="increment:1" style="width:120px;height:30px;"></input>
					</td>
				</tr>
				<tr>
					<td>联系人：</td>
					<td><input class="easyui-validatebox" type="text"
						 name="lxr" style="height: 32px"></input>
					</td>
				</tr>
				<tr >
					<td>联系电话:</td>
					<td><input class="easyui-validatebox" type="text" name="lxdh" style="height: 32px"></input>
						 </td>
				</tr>
				<tr>
					<td>单位配额</td>
					<input class="easyui-numberspinner" name="dwpe" data-options="increment:1" style="width:120px;height:30px;"></input>
					</td>
				</tr>
			</table>
				<input type="hidden" name="nEnable">	
		</form>
		<div id="dlg-buttons2">
		<a href="javascript:void(0)" class="easyui-linkbutton" id="saveBtn"
			iconCls="icon-ok" onclick="updateSaveData()" style="width:90px">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#dgformDiv').dialog('close')"
			style="width:90px">取消</a>
	</div>
	</div>
	
</body>
</html>
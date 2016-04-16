<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>配额申请</title>
  <%@include file="../common/common.jsp"%>
  <style type="text/css">
  
  
  </style>
  	
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

		url : "<%=basePath%>applyAction/queryAllQtys?time=" + randomNu,
		title :  "配额申请",
		iconCls : 'icon-danweixinxi',
		striped : true,
		fitColumns:true,   //数据列太少 未自适应
		pagination : true,
		rownumbers : true,
		pageSize:size,
		singleSelect : true,//只选中单行
		//autoRowHeight:true,
		width:w,
		height:h, 
		loadMsg:'正在加载,请稍等...',
		columns : [ [{
			field : 'lsh',
			title : '流水号',
			align:'center',
			width : 120
		},{
			field : 'hyxhsqpe',
			title : '申请配额',
			align:'center',
			width : 220
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
			field : 'bjjg',
			title : '办结结果',
			align:'center',
			width : 120,
			formatter:function(value,index){
				if(value == null){
					return "<p style='color:red'>审批中</p>";
				}else if(value == '1'){
					return "不同意 ";
				}else if(value == '0'){
					return "同意 ";
				}
			}
		},{
			field : 'null',
			title:'操作',
			align:'center',
			width : 120,
			formatter:function(value,row,index){
				var query = "<a  href='javascript:void(0)'  onclick='queryRow("+row.id+")'>查看</a>&nbsp;&nbsp;&nbsp;"
				
				return query;
				
			}
		}

		] ],

		toolbar : [ {
			id : 'btn1',
			text : '新增申请',
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
		zt: $("#zt").combobox('getValue'),
		djh: $('#djh').val(),
		dtstart:$('#dtstart').datebox('getValue'),// 获取日期输入框的值)
		dtend:$('#dtend').datebox('getValue')
	}); 
}

//新增弹出
function addRowData(){
	$('#dgform').form('clear');
	
	$('#dgformDiv').dialog('open').dialog('setTitle', '新增配额申请');
		
}
//查看
function queryRow(id){
	window.location.href="<%=basePath%>applyAction/queryDdcHyxhBasbDetailById?id="+id
	
}

//保存操作
function updateSaveData(){
	$.messager.progress({
		text:"正在处理，请稍候..."
	});
	$('#dgform').form('submit', {
				url : "<%=basePath%>applyAction/saveOrUpdateQty",
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
						$.messager.show({ // show error message
							title : '提示',
							msg : data.message
						});
						$('#dgformDiv').dialog('close');
						
						$("#dg").datagrid('reload');
					}else{
						alert(data.message);
					}
					$.messager.progress('close'); // 如果提交成功则隐藏进度条

				}

			});
}



</script>
</head>
  
  </head>
  
  <body  class="easyui-layout">
    <div>
    	<div id="tb" style="padding: 5px; background: #E8F1FF;">
				<span>申报时间：</span>
				<input id="dtstart" type="text" class="easyui-datebox" style="height: 30px;"></input> 至：  
				<input id="dtend" type="text" class="easyui-datebox" style="height: 30px;"></input>				
				<a class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
					<div style="text-align: center;background-color:#E8F1FF; "><p style="color: red">剩余配额：${ddcHyxhBase.hyxhsjzpe }</p></div>
			</div>
		<table id="dg" style="width:90%;">
			
		</table>
	</div>
	
	<!-- 点新增，编辑时弹出的表单 -->
	<div id="dgformDiv" class="easyui-dialog" closed="true" style="width: 400px;height: 300px;"
		buttons="#dlg-buttons2">
		<form id="dgform" class="easyui-form" method="post"  >
			<table class="table">
				<tr>
					<td>申请配额:</td>
					<td><input id="ss" class="easyui-numberspinner" name="hyxhsqpe"
						data-options="increment:1,required:true,validType:'number'"  min="0"
						style="width:120px;height:30px;"></input></td>
				</tr>
				<tr>
					<td>申请备注:</td>
					<td><textarea rows="5" cols="25" name="bz"></textarea>
					</td>
				</tr>
			</table>
			<input type="hidden" name="id" />
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

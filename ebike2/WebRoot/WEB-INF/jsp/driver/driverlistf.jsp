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

<title>司机信息</title>

<%@include file="../common/common.jsp"%>


<script type="text/javascript">
$(document).ready(function(){
	var h = getHeight('dg');
	var size = getPageSize(h);
	var w = getWidth(400);

	$("#dg").datagrid({

		url : "<%=basePath%>driverAction/queryAll" ,
		title :  "司机信息管理",
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
		
		columns : [ [{
			field : 'id',
			title : '',
			align:'center',
			width : 120,
			hidden:true
		},{
			field : 'jsrxm',
			title : '驾驶人姓名',
			align:'center',
			width : 120
		},{
			field : 'xb',
			title : '性别',
			align:'center',
			width : 120,
			formatter:function(value,index){
				if(value == 0){
					return "男";
				}else if(value == 1){
					return "女";
				} 
				
			}   
		},{
			field : 'sfzhm',
			title : '身份证号',
			align:'center',
			width : 120
		},{
			field : 'lxdh',
			title : '联系电话',
			align:'center',
			width : 120
		},{
			field : 'userStatus',
			title : '用户状态',
			align:'center',
			width : 120,
			formatter:function(value,index){
				if(value == 0){
					return "未认证";
				}else if(value == 1){
					return "已实名认证";
				}else{
					return "星级用户";
				}
			} 
		},{
			field : 'null',
			title:'操作',
			align:'center',
			width : 120,
			formatter:function(value,row,index){
				var query = "<a  href='javascript:void(0)'  onclick='queryRow("+row.id+")'>查看</a>&nbsp;&nbsp;&nbsp;"
				var update = "<a  href='javascript:void(0)'  onclick='updateRow("+row.id+")'>修改</a>&nbsp;&nbsp;&nbsp;"
				var cancel = "<a  href='javascript:void(0)'  onclick='deleteRow("+row.id+")'>删除</a>"
				return query+update+cancel;
			}
		}

		] ],

		toolbar : [ {
			id : 'btn1',
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				addRowData();
			}
		},{
			id : 'btn3',
			text : '导出',
			iconCls : 'icon-print',
			handler : function() {
				excelExport();
			}
		}],
		onLoadSuccess:function(){  
            $('#dg').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题  
        }
	});
	
});

function excelExport(){
	var titleArr = ["姓名","性别","身份证号码","联系电话","认证状态"]; 
	var keysArr =["jsrxm","xb","sfzhm","lxdh","userStatus"];
	var rows = $('#dg').datagrid('getData').rows;
	for(var i in rows) {
		
			if(rows[i]['xb']  == 0){
				rows[i]['xb']= "男";
			}else{
				rows[i]['xb']=  "女";
			}
		if(rows[i]['userStatus'] == 0){
			rows[i]['userStatus']=  "未认证";
		}else if(rows[i]['userStatus'] == 1){
			rows[i]['userStatus']=  "已认证";
		}
	}
	var actionUrl = '<%=basePath%>ebikeQueryAction/exportExcel';
	var fileName="司机档案";
	var content = JSON.stringify(rows);
	commonExcelExport(titleArr,keysArr,content,actionUrl,fileName); 
	
	}

function deleteRow(id){
	$.messager.confirm('警告', '该条记录将删除，请确认', function(r){
		if (r){
			$.post("<%=basePath%>driverAction/deleteRow", 
					{id:id},    
					   function (data, textStatus)
					   {     
							
						if (data.isSuccess) {
							$.messager.show({ // show error message
								title : '提示',
								msg : data.message
							});
							$("#dg").datagrid('reload');
						
						}else{
							alert(data.message);
							$("#dg").datagrid('reload');
						}
					   }
				  ,"json");
		}
	});
}

//查询功能
function doSearch(){
	
	 $('#dg').datagrid('load',{
		 sfzhm: $('#sfzhm').val(),
		 jsrxm: $('#jsrxm').val(),
		 userStatus:$('#userStatus').combobox('getValue')
	}); 
}

//新增弹出
function addRowData(){
	$('#dgform').form('clear');
	$('#dgformDiv').dialog('open').dialog('setTitle', '新增车辆申报');		
	$('#xb').combobox({
		value:0
	});
}
//查看详情
function queryRow(id){
	$.messager.progress({
		text:"正在处理，请稍候..."
	});
	window.location.href="<%=basePath%>driverAction/queryInfoById?id="+id;
}
//修改详情
function updateRow(id){
	$.messager.progress({
		text:"正在处理，请稍候..."
	});
	window.location.href="<%=basePath%>driverAction/updateInfoById?id="+id;

}



//保存操作
function updateSaveData(){
	$.messager.progress({
		text:"正在处理，请稍候..."
	});
	$('#dgform').form('submit', {
				url : "<%=basePath%>driverAction/saveOrUpdate",
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
						$("#dg").datagrid('reload');
						$('#dgform').form('clear');
					}else{
						alert(data.message);
					}
					$.messager.progress('close'); // 如果提交成功则隐藏进度条

				}

			});
}


var AllowExt=".jpg|.jpeg|.png|" //允许上传的文件类型 ŀ为无限制 每个扩展名后边要加一个"|" 小写字母表示
function CheckFileSize(obj){
	 if(obj.value != ""){
         //检测类型
         var val = obj.value;
         var FileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
         if(AllowExt.indexOf(FileExt+"|") == -1){//判断文件类型是否允许上传
        	 $.messager.alert('警告','你上传的不是图片文件');    
         	return false;
         }
	 }     
}



</script>
</head>
<body class="easyui-layout">

	<div>
		<div id="tb" class="searchdiv">
				<span>身份证号:</span> <input id="sfzhm" class="easyui-validatebox">
				<span>姓名:</span>
				<input id="jsrxm" class="easyui-validatebox">
				<span>状态</span>	
				<select class="easyui-combobox" style="width:100px;height:32px; " id="userStatus">
					<option value="">所有</option>
					<option value="0">未认证</option>
					<option value="1">实名认证</option>
					<option value="2">星级认证</option>
				</select>
				 <a class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
				</div>	
			</div>
		<table id="dg" style="width:90%;">
		</table>
	</div>
	<!-- 点新增，编辑时弹出的表单 -->
	<div id="dgformDiv" class="easyui-dialog"
		style="width:750px;height:400px;padding:10px 20px 20px 20px;"
		closed="true">
		<form id="dgform" class="easyui-form" enctype="multipart/form-data"
			method="post">
			<table class="table">
				<tr style="display: none">
					<td>id</td>
					<td><input class="easyui-validatebox" type="text" name="id"></input>
					</td>
				</tr>
				
				<tr>
					<td>驾驶人姓名</td>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true" name="jsrxm" style="height: 32px"></td>
				</tr>
				<tr>
					<td>驾驶人性别</td>
					<td><select  class="easyui-combobox" name="xb" id="xb" data-options="required:true" 
						style="height:32px;width: 100px;">
							<option value="0" >男</option>
							<option value="1">女</option>
					</select></td>
					
				</tr>
				<tr>
					<td>身份证号码</td>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true,validType:'idcard'" name="sfzhm" style="height: 32px">
					</td>
					
				</tr>
				<tr>
					<td>联系电话</td>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true,validType:'phoneNum'" name="lxdh" style="height: 32px">
					</td>
					
				</tr>
				<tr>
					<td>驾驶人头像(300*400)</td>
					<td><input  type="file" onchange="CheckFileSize(this);" data-options="required:true" class="easyui-validatebox"
						name="headimg_jsr1"" /><br /></td>
				
				</tr>
				<tr>
					<td>驾驶人身份证正面(500*350)</td>
					<td><input  type="file" onchange="CheckFileSize(this);"data-options="required:true" class="easyui-validatebox"
						name="card1img_jsr1"/></td>
					
				</tr>
				<tr>
					<td>驾驶人身份证反面(500*350)</td>
					<td><input  type="file"  onchange="CheckFileSize(this);" data-options="required:true" class="easyui-validatebox"
						name="card2img_jsr1" /><br /></td>
				</tr>
				<tr>
					<td>驾驶人在职证明或居住证(600*400)</td>
					<td><input  type="file"  onchange="CheckFileSize(this);"
						name="vcUser1WorkImgfile" /><br /></td>
				</tr>
		
			</table>
		</form>
		<div class="table-btndiv">
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
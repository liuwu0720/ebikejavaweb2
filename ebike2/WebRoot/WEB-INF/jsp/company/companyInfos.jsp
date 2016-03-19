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
    
    <title>所属单位信息管理</title>
    
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

		url : "<%=basePath%>companyAction/queryAll?time=" + randomNu,
		title :  "所属单位信息管理",
		iconCls : 'icon-danweixinxi',
		striped : true,
		fitColumns:true,   //数据列太少 未自适应
		pagination : true,
		rownumbers : true,
		//singleSelect : true,//只选中单行
		pageSize:size,
		//singleSelect : true,//只选中单行
		height:h,
		loadMsg:'正在加载,请稍等...',
		columns : [ [{
			field : 'id',
			title : 'ID',
			checkbox : true,
			align:'center',
			width : 120
		},{
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
					return "<p style='color:red'>禁用</p>";
				}else{
					return "启用";
				}
			}
		},{
			field : 'synFlag',
			title : '是否同步',
			align:'center',
			width : 120,
			formatter:function(value,index){
				if(value == 'UC' || value == 'UW'){
					return "<p style='color:red'>已同步</p>";
				}else{
					return "未同步";
				}
			}
		},{
			field : 'null',
			title:'操作',
			align:'center',
			width : 120,
			formatter:function(value,row,index){
				var query = "<a  href='javascript:void(0)'  onclick='queryRow("+row.id+")'>查看</a>&nbsp;&nbsp;&nbsp;"
				var update = "<a  href='javascript:void(0)'  onclick='updateRow("+row.id+")'>修改</a>"
				if(row.synFlag == 'UC' || row.synFlag == 'UW'){
					return query;
				}else{
					return query+update;
				}
				
			
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
		}, {
			id : 'btn2',
			text : '同步',
			iconCls : 'icon-reload',
			handler : function() {
				changeRowData();
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
		dwmc: $('#itemid').val(),
		lxr: $('#productid').val()
	});
}

//新增弹出
function addRowData(){
	$('#dgform').form('clear');
	$('#dgformDiv').dialog('open').dialog('setTitle', '新增用户');
	//$("#img").attr("src",null);
	$("#img_tr").hide();
	$('#ss').spinner({    
	    required:true,    
	    value:0    
	});
}
//查看
function queryRow(id){
	$.ajax({
		type: "GET",
   	    url: "<%=basePath%>companyAction/queryInfoById",
   	   data:{
		  id:id
	   }, 
	   dataType: "json",
	   success:function(data){
 			 
 			  if(data){
 				 $('#dgformDiv2').dialog('open').dialog('setTitle', '详情信息');
 				 $('#dgform2').form('load', data);
 				 $("#img2").attr("src",data.vcShowPath);
 			  }
 		  }
	})
}

//修改
function updateRow(id){
	$('#dgform').form('clear');
	
	$.ajax({
		type: "GET",
   	    url: "<%=basePath%>companyAction/queryInfoById",
   	   data:{
		  id:id
	   }, 
	   dataType: "json",
	   success:function(data){
 			 
 			  if(data){
 				 $('#dgformDiv').dialog('open').dialog('setTitle', '详情信息');
 				 $('#dgform').form('load', data);
 				$("#img_tr").show();
 				 $("#img").attr("src",data.vcShowPath);
 			  }
 		  }
	})
}

//保存操作
function updateSaveData(){
	$.messager.progress({
		text:"正在处理，请稍候..."
	});
	$('#dgform').form('submit', {
				url : "<%=basePath%>companyAction/saveOrUpdate",
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
						window.location.reload();
						//$("#dg").datagrid('reload');
					}else{
						alert(data.message);
					}
					$.messager.progress('close'); // 如果提交成功则隐藏进度条

				}

			});
}

//同步
function changeRowData(){
	var selected = $('#dg').datagrid('getSelections');
	var array = [];
	for(var i in selected){
		array.push(selected[i].id);
	}
	if(selected.length == 0){
		alert("请至少选择一条数据");
	}else{
		$.messager.confirm('警告', '同步以后不能再修改，请确认', function(r){
			if (r){
				
				$.post("<%=basePath%>companyAction/changeRowData", 
						{"array[]":array},    
						   function (data, textStatus)
						   {     
								
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
						   }
					  ,"json");
			}
		});
	}
	
	
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
				<span>状态:</span>
				<select class="easyui-combobox" style="width:100px;height:32px; " id="zt">
					<option value="">全部</option>
					<option value="0">禁用</option>
					<option value="1">启用</option>
				</select>
				<a 	class="easyui-linkbutton" plain="true" onclick="doSearch()" iconCls="icon-search" >查询 </a>
				<div style="text-align: center;background-color:#E8F1FF; "><p style="color: red">剩余配额：${ddcHyxhBase.hyxhsjzpe }</p></div>
		</div>
	</table>
</div>
	<!-- 点新增，编辑时弹出的表单 -->
	<div id="dgformDiv" class="easyui-dialog"
		style="width:550px;height:550px;padding:10px 20px 20px 20px;"
		closed="true" buttons="#dlg-buttons2">
		<form id="dgform" class="easyui-form" enctype="multipart/form-data"  method="post">
			<table class="table">
				<tr style="display: none">
					<td>id</td>
					<td><input class="easyui-validatebox" type="text" name="id" ></input>
					</td>
				</tr>
				<tr>
					<td>单位名称：</td>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true" name="dwmc"  style="height: 32px;width:200px;"></input></td>
				</tr>
				<tr>
					<td> 组织机构代码证号：</td>
					<td><input class="easyui-validatebox" type="text" data-options="required:true" 
						name="zzjgdmzh" style="height: 32px;width:200px;"></input>
						</td>
				</tr>
				<tr>
					<td>住所地址</td>
					<td><input class="easyui-validatebox" data-options="required:true"  name="zsdz" type="text"  style="height: 32px;width:200px;"></input>
					</td>
				</tr>
				<tr>
					<td>联系人：</td>
					<td><input class="easyui-validatebox" type="text" data-options="required:true" 
						 name="lxr" style="height: 32px"></input>
					</td>
				</tr>
				<tr >
					<td>联系电话:</td>
					<td><input class="easyui-validatebox" data-options="required:true,validType:'phoneNum'"  type="text" name="lxdh" style="height: 32px"></input>
						 </td>
				</tr>
				<tr>
					<td>单位配额</td>
					<td><input id="ss" class="easyui-numberspinner" name="dwpe" data-options="increment:1,required:true,validType:'number'" value="0" min="0" style="width:120px;height:30px;"></input>
					</td>
				</tr>
				<tr>
					<td>选择上传</td>
					<td>
						<input class="easyui-filebox" style="width:300px"  id="file_upload" name="file_upload"/><br/>
					</td>
				</tr>
				<tr id="img_tr">
					<td>营业执照图片</td>
					<td>
						<img id="img"  class="easyui-validatebox" style="width:300px"   /><br/>
					</td>
				</tr>
			</table>
				<input class="easyui-validatebox" type="hidden"  
						 name="vcPicPath" style="height: 32px">
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
	
	<!-- 点查看时弹出的表单 -->
	<div id="dgformDiv2" class="easyui-dialog"
		style="width:550px;height:450px;padding:10px 20px 20px 20px;" closed="true" >
		<form id="dgform2" class="easyui-form" method="post">
			<table class="table">
				<tr style="display: none">
					<td>id</td>
					<td><input class="easyui-validatebox" type="text" name="id" readonly="readonly" ></input>
					</td>
				</tr>
				<tr>
					<td>单位名称：</td>
					<td><input class="easyui-validatebox" type="text" 
						 name="dwmc"  style="height: 32px;width:200px;" readonly="readonly" ></input></td>
				</tr>
				<tr>
					<td> 组织机构代码证号：</td>
					<td><input class="easyui-validatebox" type="text"  readonly="readonly" 
						name="zzjgdmzh" style="height: 32px;width:200px;"></input>
						</td>
				</tr>
				<tr>
					<td>住所地址</td>
					<td><input class="easyui-validatebox"  readonly="readonly"   name="zsdz" type="text"  style="height: 32px;width:200px;"></input>
					</td>
				</tr>
				<tr>
					<td>联系人：</td>
					<td><input class="easyui-validatebox" type="text"  readonly="readonly" 
						 name="lxr" style="height: 32px"></input>
					</td>
				</tr>
				<tr >
					<td>联系电话:</td>
					<td><input class="easyui-validatebox"  readonly="readonly"   type="text" name="lxdh" style="height: 32px"></input>
						 </td>
				</tr>
				<tr>
					<td>单位配额</td>
					<td><input class="easyui-numberspinner" name="dwpe" data-options="increment:1"  readonly="readonly"  style="width:120px;height:30px;"></input>
						
					</td>
				</tr>
				<tr>
					<td>营业执照图片</td>
					<td>
						<img id="img2"  class="easyui-validatebox" style="width:300px"   /><br/>
					</td>
				</tr>
			</table>
				
		</form>
		
	</div>
	
</body>
</html>
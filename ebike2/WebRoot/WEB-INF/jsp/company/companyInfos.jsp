
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
		iconCls : 'icon-edit',
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
			field : 'bz',
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

function deleteRow(id){
	$.messager.confirm('警告', '确认删除这条记录码', function(r){
		if (r){
			$.post("<%=basePath%>resourceAction/del", 
					{ id:id},     
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

//修改
function updateRowData(){
	
	 var row = $('#dg').datagrid('getSelected');
   if (row){
    	 $('#dgformDiv').dialog('open').dialog('setTitle', '编辑用户');
    	 $('#dgform').form('load', row);
     }else{
    	 $.messager.alert('提示','请选择你要修改的行');    
     } 

   $('#cc').combobox({    
	    url:'<%=basePath%>resourceAction/getParentResource',    
	    valueField:'vcResourceName',    
	    textField:'vcResourceName',
	    value:row.vcParent   //默认选中的值       
	}); 
 
}
//添加
function addRowData(){
	$('#dgform').form('clear');
	 $('#dgformDiv').dialog('open').dialog('setTitle', '编辑用户');
	$('#cc').combobox({    
	    url:'<%=basePath%>resourceAction/getParentResource',    
	    valueField:'vcResourceName',    
	    textField:'vcResourceName',
	    value:''
	});  

}
function clearSelect(){
	$('#cc').combobox("clear")
}

//保存操作

function updateSaveData(){
	$.messager.progress();
	$('#dgform').form('submit', {
				url : "<%=basePath%>resourceAction/saveOrUpdate",
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
<body class="easyui-layout">

	<div>
	<table id="dg" style="width:90%;">
		
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
					<td>菜单名称：</td>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true" name="vcResourceName" ></input></td>
				</tr>
				<tr>
					<td>链接地址：</td>
					<td><input class="easyui-validatebox" type="text"
						name="vcUrl" style="height: 32px;width:200px;"></input>
						<span  style="color: red;">根菜单的链接地址为空</span>
						</td>
				</tr>
				<tr>
					<td>排序</td>
					<td><input class="easyui-numberspinner" name="nSort" data-options="increment:1" style="width:120px;height:30px;"></input>
					</td>
				</tr>
				<tr>
					<td>菜单图标：</td>
					<td><input class="easyui-validatebox" type="text"
						 name="vcIcon" style="height: 32px"></input><span  style="color: red;">(菜单图案编码联系开发人员)</span>
					</td>
				</tr>
				<tr >
					<td>父菜单</td>
					<td><input id="cc" name="vcParent" style="height:30px;">
					<a style="cursor: pointer;" onclick="clearSelect()">清空</a><span  style="color: red;">(为空则为根菜单)</span>  
					</td>
				</tr>
				<tr>
					<td>功能描述</td>
					<td>
						<textarea rows="5" cols="30" name="vcDesc"></textarea>  
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
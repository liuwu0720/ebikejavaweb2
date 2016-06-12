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

<title>行业协会所属单位管理</title>

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

		url : "<%=basePath%>adminAction/queryAllCompany?time=" + randomNu,
		title :  "行业协会所属单位管理",
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
			field : 'dwmc',
			title : '单位名称',
			align:'center',
			width : 120
		},{
			field : 'userCode',
			title : '单位账号',
			align:'center',
			width : 120
		},{
			field : 'lxr',
			title : '联系人',
			align:'center',
			width : 220
		},{
			field : 'lxdh',
			title : '联系人电话',
			align:'center',
			width : 120
		},{
			field : 'totalPe',
			title : '总配额',
			align:'center',
			width : 120
		},{
			field : 'dwpe',
			title : '剩余配额',
			align:'center',
			width : 120
		},{
			field : 'sqrq',
			title : '申请日期',
			align:'center',
			width : 120,
			formatter:function(value,index){
				var unixTimestamp = new Date(value);   
				return unixTimestamp.toLocaleString();
			}   
		},{
			field : 'hyxhzhName',
			title : '所属协会',
			align:'center',
			width : 120
		},{
			field : 'zt',
			title : '状态',
			align:'center',
			width : 120,
			formatter:function(value,index){
				if(value == 0){
					return "<p style='color:red'>禁用</p>"
				}else{
					return "启用";
				}
			}
		}

		] ],
		toolbar : [ {
			id : 'btn2',
			text : '修改',
			iconCls : 'icon-edit',
			handler : function() {
				updateRow();
			}
		}],

		onLoadSuccess:function(){  
            $('#dg').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题  
        }
	});
	
	$('#hyxhzh').combobox({    
	    url:'<%=basePath%>adminAction/getAllIndustry',    
	    valueField:'hyxhzh',    
	    textField:'hyxhmc'   
	});  
});



//查询功能
function doSearch(){
	 $('#dg').datagrid('load',{
		 hyxhzh: $("#hyxhzh").combobox("getValue"),
		 dwmc:$("#dwmc").val()
	}); 
}


//修改
function updateRow(){
	 $('#dgform').form('clear');
	 var row = $('#dg').datagrid('getSelected');
	   if (row){
	    	 $('#dgformDiv').dialog('open').dialog('setTitle', '编辑信息');
	    	 $.ajax({
	    		 	type: "GET",
	    	   	    url: "<%=basePath%>adminAction/queryCompanyById",
	    	   	   data:{
	    			  id:row.id
	    		   }, 
	    		   dataType: "json",
	    		   success:function(data){
	    			 
	    			   $('#dgform').form('load', data);
	    			   if(data.vcShowPath==null){
	    					 $("#img_0").attr("src","<%=basePath%>static/images/iconfont-wu.png");
	    				}else{
	    					$("#img_0").attr("src",data.vcShowPath);
	    				}
	    		   }
	    	 })
	    	 
	     }else{
	    	 $.messager.alert('提示','请选择你要修改的行');    
	     } 

}

//保存操作

function updateSaveData(){
	$.messager.progress();
	$('#dgform').form('submit', {
				url : "<%=basePath%>adminAction/saveOrUpdateCompany",
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

	<div class="searchdiv">
		<div>
				<span>协会名称名称：</span>
				<input id="hyxhzh" style="height: 32px;">  
				
				<span>单位名称：</span>
				<input id="dwmc" type="text" class="easyui-validatebox"></input>
				<a class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
			</div>
		<table id="dg" style="width:90%;">
		</table>
	</div>
	
	
		<!-- 点查看时弹出的表单 -->
	<div id="dgformDiv" class="easyui-dialog"
		style="width:500px;height:400px;padding:10px 20px 20px 20px;"
		closed="true"  buttons="#dlg-buttons">
		<form id="dgform" class="easyui-form" enctype="multipart/form-data"
			method="post">
			<table class="dialogtable borderinput">
				<tr style="display: none">
					<td>id</td>
					<td><input class="easyui-validatebox" type="text" name="id"></input>
					</td>
				</tr>
				<tr>
					<th>单位名称:</th>
					<td><input  name="dwmc" class="easyui-validatebox"  style="height: 32px;" >  
					</td>
				<tr>
					<th>联系人：</th>
					<td><input name="lxr" type="text" class="easyui-validatebox" ></td>
				</tr>
				<tr>
					<th>单位地址：</th>
					<td><input name="zsdz" type="text" class="easyui-validatebox" ></td>
				</tr>
				<tr>
					<th>组织机构代码：</th>
					<td><input name="zzjgdmzh" type="text" class="easyui-validatebox" ></td>
				</tr>
				<tr>
					<th>备注：</th>
					<td>
						<textarea rows="5" cols="22" name="bz"></textarea>
					</td>
				</tr>
					<tr>
					<th>总配额</th>
					<td>
					<input   class="easyui-numberspinner" name="totalPe" 
						data-options="increment:1,required:true,validType:'number'"  min="0"
						style="width:120px;height:30px;"></input>
					</td>
				</tr>
				<tr id="hyxhsjzpe_tr">
					<th>剩余配额</th>
					<td>
					<input   class="easyui-numberspinner" name="dwpe"
						data-options="increment:1,validType:'number'"  min="0"
						style="width:120px;height:30px;"></input>
					</td>
				</tr>
			</table>
				<input type="hidden" name="lxdh">
				<input type="hidden" name="dwpe">
				<input type="hidden" name="totalPe">
				<input type="hidden" name="shbm">
				<input type="hidden" name="vcPicPath">
				<input type="hidden" name="shbm">
				<input type="hidden" name="hyxhzh">
				<input type="hidden" name="userCode">
				<input type="hidden" name="passWord">
				<input type="hidden" name="shFlag">
				<input type="hidden" name="synFlag">
				<input type="hidden" name="zt">
		</form>
		<div id="dlg-buttons">
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
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

		url : "<%=basePath%>companyQtyAction/queryAll?time="+ randomNu,
		title : "所属单位配额管理",
		iconCls : 'icon-danweixinxi',
		striped : true,
		fitColumns : true, //数据列太少 未自适应
		pagination : true,
		rownumbers : true,
		singleSelect : true,//只选中单行
		pageSize : size,
		height : h,
		width:w,
		loadMsg : '正在加载,请稍等...',
		columns : [ [
	
													{
														field : 'dwmc',
														title : '单位名称',
														align : 'center',
														width : 120
													},
													{
														field : 'zzjgdmzh',
														title : '机构代码',
														align : 'center',
														width : 220
													},
													{
														field : 'lxr',
														title : '联系人',
														align : 'center',
														width : 120
													},
													{
														field : 'lxdh',
														title : '联系电话',
														align : 'center',
														width : 120
													},
													{
														field : 'dwpe',
														title : '单位剩余配额',
														align : 'center',
														width : 120
													},{
														field : 'totalPe',
														title : '单位总配额',
														align : 'center',
														width : 120
													},
													{
														field : 'sqrq',
														title : '申请时间',
														align : 'center',
														width : 120,
														formatter : function(
																value, index) {
															var unixTimestamp = new Date(
																	value);
															return unixTimestamp
																	.toLocaleString();
														}
													},
													{
														field : 'null',
														title : '操作',
														align : 'center',
														width : 120,
														formatter : function(
																value, row,
																index) {
															var update = "<a  href='javascript:void(0)'  onclick='updateRow("
																	+ row.id
																	+ ","
																	+ row.totalPe
																	+ ")'>修改配额</a>"

															return update;
														}
													}

											] ],
											onLoadSuccess : function() {
												$('#dg').datagrid(
														'clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题  
											}
										});

					});
	//查询功能
	function doSearch() {
		$('#dg').datagrid('load', {
			dwmc : $('#itemid').val(),
			lxr : $('#productid').val()
		});
	}
	//修改
	function updateRow(obj, obj2) {

		$('#dgformDiv').dialog('open').dialog('setTitle', '修改配额');
		$('#dgform').form('load', {
			id : obj,
			totalPe : obj2
		});
	}
	
	//保存
	function updateSaveData(){
		$.messager.progress({
			text:"正在处理，请稍候..."
		});
		$('#dgform').form('submit', {
					url : "<%=basePath%>companyQtyAction/updateQty",
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
</script>
</head>
<body class="easyui-layout">

	<div  class="searchdiv">
			<div id="tb" style="padding: 5px; background: #E8F1FF;">
				<span>单位名称:</span> <input id="itemid"
					style="line-height:26px;border:1px solid #ccc"> <span>
					联系人:</span> <input id="productid"
					style="line-height:26px;border:1px solid #ccc"> <a
					class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
				<div style="text-align: center;background-color:#E8F1FF; "><p style="color: red">协会总配额：${ddcHyxhBase.totalPe }   剩余配额：${ddcHyxhBase.hyxhsjzpe }</p></div>
			</div>
		<table id="dg" style="width:90%;">
		</table>
	</div>
	<!-- 点新增，编辑时弹出的表单 -->
	<div id="dgformDiv" class="easyui-dialog" closed="true" style="width:350px;padding:10px 20px 20px 20px;"
		buttons="#dlg-buttons2">
		<form id="dgform" class="easyui-form" method="post"  >
			<table class="table">
				<tr>
					<td>总配额:</td>
					<td><input id="ss" class="easyui-numberspinner" name="totalPe"
						data-options="increment:1,required:true,validType:'number'"  min="0"
						style="width:120px;height:30px;"></input></td>
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
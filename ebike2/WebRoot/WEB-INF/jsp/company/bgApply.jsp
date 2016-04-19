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

		url : "<%=basePath%>ssdwChangeAction/queryAll?time=" + randomNu,
		title :  "电动车变更管理",
		iconCls : 'icon-danweixinxi',
		striped : true,
		fitColumns:true,   //数据列太少 未自适应
		pagination : true,
		rownumbers : true,
		pageSize:size,
		//singleSelect : true,//只选中单行
		striped:true,  //striped  是否显示斑马线效果。
		height:h,
		width:w,
		loadMsg:'正在加载,请稍等...',
		columns : [ [{
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
			field : 'YWLXNAME',
			title : '类型',
			align:'center',
			width : 50
		},{
			field : 'SYRQ',
			title : '审验日期',
			align:'center',
			width : 50,
			formatter:function(value,index){
				var unixTimestamp = new Date(value);   
				return unixTimestamp.toLocaleString();
			}   
		},{
			field : 'SLYJ',
			title : '审批状态',
			align:'center',
			width : 80,
			formatter:function(value,row,index){
				if(value == null){
				   return "等待协会审批";
				}else if(value == 0){
					return "已审核(同意) ";
				}else if(value == 1){
					return "已审核(拒绝) ";
				}
			}
		},{
			field : 'null',
			title:'操作',
			align:'center',
			width : 120,
			formatter:function(value,row,index){
				
				var query = "<a  href='javascript:void(0)'  onclick='queryRow("+row.ID+")'>查看</a>&nbsp;&nbsp;&nbsp; |&nbsp;&nbsp;&nbsp; ";
				var update = "<a  href='javascript:void(0)'  onclick='updateRow("+row.ID+")'>变更</a>&nbsp;&nbsp;&nbsp; |&nbsp;&nbsp;&nbsp;";
				var del = "<a  href='javascript:void(0)'  onclick='deleteRow("+row.ID+")'>注销</a>";
				if(row.SLYJ == null){
					return query;	
				}else{
					return query+update+del;	
				}
							
			
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
});
//注销
function deleteRow(id){
	$('#dgformDiv3').dialog('open').dialog('setTitle', '详情信息');
	 $('#dgform3').form('load',{
		 id:id
	 } );
	
}
function zhuxiaoSure(){
	var flag = checkValues();
	
		if(flag){
			$.messager.progress({
				text:"正在处理，请稍候..."
			});
			$('#dgform3').form('submit', {
						url : "<%=basePath%>ssdwChangeAction/zhuxiao",
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
								$('#dgformDiv3').dialog('close');
								
								$("#dg").datagrid('reload');
							}else{
								alert(data.message);
							}
							$.messager.progress('close'); // 如果提交成功则隐藏进度条

						}

					});
		}
		
	
}

function checkValues(){
	if($("[id='ywyys']:checked").length==0){
		alert("请选择业务原因！");
		return false;
	}else if($("[id='slzls']:checked").length==0){
		alert("请选择受理资料！");
		return false;
	}else{
		var vs="";
		$('[id=slzls]:checked').each(function(){
			vs += $(this).val()+',';
		});
		vs=vs.substr(0,vs.lastIndexOf(','));
		$("#slzllist").val(vs);
		return true;
	}	
}



//查询功能
function doSearch(){
	 $('#dg').datagrid('load',{
		dabh: $("#dabh").val(),
		djh: $('#djh').val(),
		cphm:$("#cphm").val(),
		jsrxm1:$("#jsrxm1").val(),
		ywlx:$("#ywlx").combobox('getValue'),
		slyj:$("#slyj").combobox('getValue'),
		xsqy:$("#xsqy1").combobox('getValue')
	}); 
}


//查看
function queryRow(id){
	window.location.href="<%=basePath%>ebikeChangeAction/queryDetailById?id="+id;
}

//修改
function updateRow(id){
	window.location.href="<%=basePath%>ssdwChangeAction/changeInfo?id="+id
}
//导出EXCEL
function exportRowData(){
	var titleArr = ["档案编号","车牌号","电机号","驾驶人1","车身颜色","行驶区域","类型","审检日期","审批状态"]; 
	var keysArr =["DABH","CPHM","DJH","JSRXM1","CSYSNAME","XSQY","YWLXNAME","SYRQ","SLYJ"];
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


</script>
</head>
<body class="easyui-layout">

	<div>
		<div id="tb" class="searchdiv">
				<span>档案编号</span>
				<input id="dabh" type="text" class="easyui-validatebox" name="dabh" ></input>
				<span>电机号</span> <input id="djh" name="djh"
					class="easyui-validatebox" type="text" >
				<span>车牌号</span> <input id="cphm" name="cphm"
					class="easyui-validatebox" type="text" >
				</select> <br>
				<span>驾驶人1</span> <input id="jsrxm1" name="cphm"
				class="easyui-validatebox" type="text" >
				<span>行驶区域</span>	
				<input id="xsqy1" style="height:30px;" >
				<span>类型</span>
				<select class="easyui-combobox" style="width:100px;height:32px; " id="ywlx">
					<option value="">所有</option>
					<option value="A">正常</option>
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
	
	
	<!--注销  -->
	<div id="dgformDiv3" class="easyui-dialog"
		style="width:550px;height:400px;padding:10px 20px 20px 20px;"
		closed="true" >
		<div>
		<form id="dgform3" class="easyui-form" enctype="multipart/form-data"
			method="post">
			<input type="hidden" name="id" id="daId'>
			<input type="text"  name="slzllist" id="slzllist">
		<ul>
			<li>业务原因：
				<c:forEach items="${ywyys }" var="yw">
				<input type="checkbox" id="ywyys" name="ywyys" value="${yw.dmz}" />${yw.dmms1 }</c:forEach>
			</li>
			<li>受理资料：
				<c:forEach items="${slzls }" var="zl">
					<p style="margin-left:55px;"><input type="checkbox" id="slzls" name="slzls" value="${zl.dmz}" />${zl.dmms1}</p>
				</c:forEach>
			</li>
			<li>受理备注：<textarea name="slbz" cols="63" rows="5"></textarea></li>
		</ul>
		</div>
		<div style="text-align: center;padding-top:25px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="saveBtn"
				iconCls="icon-ok" onclick="zhuxiaoSure()" style="width:90px">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#dgformDiv3').dialog('close')"
				style="width:90px">取消</a>
		</div>
		</form>
	</div>
</body>
</html>
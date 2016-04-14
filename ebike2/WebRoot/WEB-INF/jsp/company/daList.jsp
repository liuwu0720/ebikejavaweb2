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

		url : "<%=basePath%>ssdwQueryAction/queryAll?time=" + randomNu,
		title :  "电动车档案列表",
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
			field : 'ID',
			title : 'ID',
			checkbox : true,
			align:'center',
			width : 10
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
			field : 'SYRQ',
			title : '审验日期',
			align:'center',
			width : 50,
			formatter:function(value,index){
				var unixTimestamp = new Date(value);   
				return unixTimestamp.toLocaleDateString();
			}   
		},{
			field : 'null',
			title:'操作',
			align:'center',
			width : 120,
			formatter:function(value,row,index){
				var query = "<a  href='javascript:void(0)'  onclick='queryRow("+row.ID+")'>查看</a>";
				var print = "<a  href='javascript:void(0)'  onclick='queryQRCode("+row.ID+")'>打印</a>";
				return query+print;	
			
			}
		}

		] ],
		toolbar : [{
			id : 'btn3',
			text : '导出',
			iconCls : 'icon-redo',
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
		xsqy:$("#xsqy1").combobox('getValue')
	}); 
}


//查看
function queryRow(id){
	window.location.href="<%=basePath%>ebikeChangeAction/queryDetailById?id="+id;
}


//导出EXCEL
function exportRowData(){
	var content = $('.datagrid-view').html();
	window.open('data:application/vnd.ms-excel,' + encodeURIComponent(content));

}
//查看该档案的流水记录
function queryDetaiList(obj){
	window.location.href="<%=basePath%>ssdwAction/getFlowList?dabh="+obj
}


//查看基本信息以及二维码
function queryQRCode(id){
	window.location.href="<%=basePath%>ebikeQueryAction/queryQRCodeById?id="+id
}

</script>
</head>
<body class="easyui-layout">

	<div>
		<div id="tb" style="padding: 5px; background: #E8F1FF;">
				<span>档案编号</span>
				<input id="dabh" type="text" class="easyui-validatebox" name="dabh" ></input>
				<span>电机号</span> <input id="djh" name="djh"
					class="easyui-validatebox" type="text" >
				<span>车牌号</span> <input id="cphm" name="cphm"
					class="easyui-validatebox" type="text" >
				</select> 
				<span>驾驶人1</span> <input id="jsrxm1" name="cphm"
				class="easyui-validatebox" type="text" >
				<span>行驶区域</span>	
				<input id="xsqy1" style="height:30px;width: 80px;" >
				
				<a class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
			</div>
		<table id="dg" style="width:90%;">
		</table>
	</div>
	

</body>
</html>
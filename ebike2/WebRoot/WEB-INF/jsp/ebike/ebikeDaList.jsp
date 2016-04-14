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

<title>电动车档案查询</title>

<%@include file="../common/common.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>static/js/export.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	$.ajaxSetup ({
		   cache: false //缓存
		});
	var h = getHeight('dg');
	var size = getPageSize(h);
	var w = getWidth(400);
	var randomNu = (new Date().getTime()) ^ Math.random();
	grid = $("#dg").datagrid({

		url : "<%=basePath%>ebikeQueryAction/queryAll?time=" + randomNu,
		title :  "电动车档案查询管理",
		iconCls : 'icon-search',
		striped : true,
		fitColumns:true,   //数据列太少 未自适应
		pagination : true,
		rownumbers : true,
		pageSize:20,
		singleSelect : true,//只选中单行
		 autoRowHeight:true,
		 width:w,
		 height:h, 
		loadMsg:'正在加载,请稍等...',
		columns : [ [{
			field : 'ID',
			title : 'ID',
			checkbox : true,
			align:'center',
			width : 120
		},{
			field : 'DWMC',
			title : '单位名称',
			align:'center',
			width : 220
		},{
			field : 'DABH',
			title : '档案编号',
			align:'center',
			width : 120
		},{
			field : 'CPHM',
			title : '车牌号',
			align:'center',
			width : 120
		},{
			field : 'DJH',
			title : '电机号',
			align:'center',
			width : 120
		},{
			field : 'JSRXM1',
			title : '驾驶人',
			align:'center',
			width : 120
		},{
			field : 'XSQY',
			title : '行驶区域',
			align:'center',
			width : 120
		},{
			field : 'GDYJ',
			title : '归档意见',
			align:'center',
			width : 120,
			formatter:function(value,index){
				if(value == 0){
					return '办结';
				}else{
					return '退办'
				}
			}
		},{
			field : 'ZT',
			title : '车辆状态',
			align:'center',
			width : 120,
			formatter:function(value,index){
				if(value == '注销'){
					
					return "<p style='color:red'>注销</p>";
				}else{
					return value;
				}
			}
		},{
			field : 'null',
			title:'操作',
			align:'center',
			width : 120,
			formatter:function(value,row,index){
				var query = "<a  href='javascript:void(0)'  onclick='queryRow("+row.ID+")'>查看</a>&nbsp;&nbsp;&nbsp;";
				var print = "<a  href='javascript:void(0)'  onclick='detailRow("+row.ID+")'>打印</a>";
				return query;	
				
			}
		}

		] ],
		/* toolbar : [ {
			id : 'btn1',
			text : '导出',
			iconCls : 'icon-redo',
			handler : function() {
				expt(grid);
			}
		}], */
		onLoadSuccess:function(){  
            $('#dg').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题  
        }
	});
	
	//加载下拉框
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
						url : "<%=basePath%>ebikeChangeAction/zhuxiao",
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
		xsqy:$("#xsqy1").combobox('getValue'),
		ssdw:$("#ssdw").combobox('getValue')
	}); 
}


//查看
function queryRow(id){
	window.location.href="<%=basePath%>ebikeChangeAction/queryDetailById?id="+id
}

//修改
function updateRow(id){
	window.location.href="<%=basePath%>ebikeChangeAction/changeInfo?id="+id
}

function detailRow(id){
	window.location.href="<%=basePath%>ebikeChangeAction/queryDetailById?id="+id
}


var AllowExt=".jpg|.jpeg|.gif|.bmp|.png|" //允许上传的文件类型 ŀ为无限制 每个扩展名后边要加一个"|" 小写字母表示
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
<body  class="easyui-layout">

	<div>
		<div id="tb" class="searchdiv">
				<span>档案编号</span>
				<input id="dabh" type="text" class="easyui-validatebox" name="dabh"style="height:30px;width: 80px;" ></input>
				<span>电机号:</span> <input id="djh" 
					class="easyui-validatebox" type="text" style="height:30px;width: 80px;">
				<span>姓名:</span> <input id="jsrxm1" 
					class="easyui-validatebox" type="text"style="height:30px;width: 80px;" > <br>
				<span>车牌号</span> <input id="cphm" name="cphm"
					class="easyui-validatebox" type="text" style="height:30px;width: 80px;">	
				<span>行驶区域</span>	
				<input id="xsqy1" style="height:30px;width: 80px;" >	
				<span>公司名称:</span>
				 <input id="ssdw" style="height: 32px;">   &nbsp;&nbsp;&nbsp;
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
	<div id="dialog2"></div>
	
</body>
</html>
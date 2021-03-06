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

<title>备案申报</title>

<%@include file="../common/common.jsp"%>


<script type="text/javascript">
$(document).ready(function(){
	var h = getHeight('dg');
	var size = getPageSize(h);
	var w = getWidth(400);

	$("#dg").datagrid({

		url : "<%=basePath%>baAction/queryAll" ,
		title :  "备案申报",
		iconCls : 'icon-danweixinxi',
		striped : true,
		fitColumns:true,   //数据列太少 未自适应
		pagination : true,
		rownumbers : true,
		pageSize:size,
		pageList:[10,20,30,40,50,500,1000],
		singleSelect : true,//只选中单行
		height:h,
		width:w,
		loadMsg:'正在加载,请稍等...',
		rowStyler: function(index,row){
			if (row.SLYJ ==1){
				return 'background-color:#7AF1B5;color:#red;font-weight:bold;';
			}
		},
		columns : [ [{
			field : 'LSH',
			title : '流水号',
			align:'center',
			width : 120
		},{
			field : 'PPXH',
			title : '品牌型号',
			align:'center',
			width : 120
		},{
			field : 'CSYSNAME',
			title : '车身颜色',
			align:'center',
			width : 120
		},{
			field : 'DJH',
			title : '电机号',
			align:'center',
			width : 120
		},{
			field : 'JSRXM1',
			title : '驾驶人1',
			align:'center',
			width : 120
		},{
			field : 'LXDH1',
			title : '驾驶人1联系电话',
			align:'center',
			width : 120,
			hidden:true
		},{
			field : 'SFZMHM1',
			title : '驾驶人1身份证号码',
			align:'center',
			width : 120,
			hidden:true
		},{
			field : 'XSQYNAME',
			title : '行驶区域',
			align:'center',
			width : 120
		},{
			field : 'SQRQ',
			title : '申请时间',
			align:'center',
			width : 120,
			formatter:function(value,index){
				var unixTimestamp = new Date(value);   
				return unixTimestamp.toLocaleString();
			}   
		},{
			field : 'SLYJ',
			title : '审批状态',
			align:'center',
			width : 120,
			formatter:function(value,row,index){
				if(value == null){
					if(row.SL_INDEX == 0){
						return "等待协会审批";
					}else if(row.SL_INDEX >= 1){
						return "等待交警审批";
					}else if(row.SL_INDEX == -1){
						return "等待协会审批(资料不全)";
					}
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
				var query = "<a  href='javascript:void(0)'  onclick='queryRow("+row.ID+")'>查看</a>&nbsp;&nbsp;&nbsp;"
				var update = "<a  href='javascript:void(0)'  onclick='updateRow("+row.ID+")'>修改</a>&nbsp;&nbsp;&nbsp;"
				var cancel = "<a  href='javascript:void(0)'  onclick='cancelSb("+row.ID+")'>退回</a>"
				if(row.SL_INDEX <= 0){
					return query+update+cancel;
				}else if(row.SLYJ == 1){
					return query+update;
				}else{
					return query;
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
	
	//行驶区域
	$('#xsqy1').combobox({
		 url:'<%=basePath%>applyAction/getAllAreaAjax',    
		    valueField:'dmz',    
		    textField:'dmms1'
	})
});

function cancelSb(id){
	$.messager.confirm('警告', '退回后该条记录将删除，请确认', function(r){
		if (r){
			
			$.post("<%=basePath%>ssdwAction/cancelSb", 
					{id:id},    
					   function (data, textStatus)
					   {     
							
						if (data.isSuccess) {
							$.messager.show({ // show error message
								title : '提示',
								msg : data.message
							});
							$("#dg").datagrid('reload');
							window.location.href='<%=basePath%>baAction/getAll';
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
		zt: $("#zt").combobox('getValue'),
		djh: $('#djh').val(),
		dtStart:$('#dd').datebox('getValue'),
		dtend:$('#dtend').datebox('getValue'),
		xsqy:$("#xsqy1").combobox('getValue'),
		jsrxm1:$("#jsrxm1").val(),
		lsh:$("#lsh").val()
	}); 
}

//新增弹出
function addRowData(){
	$('#dgform').form('clear');
	$('#dgformDiv').dialog('open').dialog('setTitle', '新增车辆申报');		
	
	//车身颜色
	$('#cysy').combobox({
		 url:'<%=basePath%>applyAction/getAllColorsAjax',    
		    valueField:'dmz',    
		    textField:'dmms1',
		    value:"3"   //默认选中的值       
	});
	//行驶区域
	$('#xsqy').combobox({
		 url:'<%=basePath%>applyAction/getAllAreaAjax',    
		    valueField:'dmz',    
		    textField:'dmms1',
		    value:"440303"   //默认选中的值       
	})
	$('#jtzz').combobox({
		value:0
	});
	
	$('#sfzmhm1').combogrid({    
		panelWidth: 400,
		delay: 500,    
		mode: 'remote',    
	    idField:'sfzhm',    
	    textField:'jsrxm',   
	    fitColumns: true,
	    pagination : true,
	    url:'<%=basePath%>baAction/getDriverList',    
	    columns:[[    
	        {field:'sfzhm',title:'身份证号码',width:60},    
	        {field:'jsrxm',title:'驾驶人姓名',width:40}
	    ]]    
	});  
	
	$('#sfzmhm2').combogrid({    
		panelWidth: 400,
		delay: 500,    
		mode: 'remote',    
	    idField:'sfzhm',    
	    textField:'jsrxm',   
	    fitColumns: true,
	    pagination : true,
	    url:'<%=basePath%>baAction/getDriverList',    
	    columns:[[    
	        {field:'sfzhm',title:'身份证号码',width:60},    
	        {field:'jsrxm',title:'驾驶人姓名',width:40}
	    ]]    
	}); 
}
//查看备案审批详情
function queryRow(id){
	window.open("<%=basePath%>applyAction/queryRecordApprovalInfoById?id="+id,"_blank");
	//window.location.href="<%=basePath%>applyAction/queryRecordApprovalInfoById?id="+id;
}
//修改申报详情
function updateRow(id){
	
	$.post("<%=basePath%>baAction/checkStatus", 
			{id:id},    
			   function (data, textStatus)
			   {     
					
				if (data.isSuccess) {
					window.open("<%=basePath%>baAction/updateRecordApprovalInfoById?id="+id,"_blank");
					
					//window.location.href="<%=basePath%>baAction/updateRecordApprovalInfoById?id="+id;
				}else{
					alert(data.message);
					$("#dg").datagrid('reload');
				}
			   }
		  ,"json");
	

}



//保存操作
function updateSaveData(){
	$.messager.progress({
		text:"正在处理，请稍候..."
	});
	$('#dgform').form('submit', {
				url : "<%=basePath%>baAction/saveOrUpdateBaClSb",
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
				
				$.post("<%=basePath%>applyAction/changeRowData", 
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
var AllowExt=".jpg|.jpeg|.png|" //允许上传的文件类型 ŀ为无限制 每个扩展名后边要加一个"|" 小写字母表示
function CheckFileSize(obj){
	 if(obj.value != ""){
         //检测类型
         var val = obj.value;
         var FileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
         if(AllowExt.indexOf(FileExt+"|") == -1){//判断文件类型是否允许上传
        	 $.messager.alert('警告','你上传的不是图片文件');    
              obj.value="";
         	return false;
         }
	 }     
}

function exportRowData(){
	var selected = $('#dg').datagrid('getSelections');
	var array = [];
	for(var i in selected){
		array.push(selected[i].ID);
	}
	if(selected.length == 0){
		alert("请至少选择一条数据");
	}else{
		$.ajax({
			type:'post',							
			url: "<%=basePath%>applyAction/exportExcel",
			dataType: 'html',
			data:{"array[]":array}, 
			success:function(data){																	
				if(data=='2'){
					alert('下载失败!');
				}else{
					//var path = ""+data;
					//var path = data;
					//alert(data);
					//$("#downhelpid").attr("href",path);
					window.location = data;
				}
			}						
		});
	
	}
}

function excelExport(){
	var titleArr = ["流水号","品牌型号","车身颜色","电机号","驾驶人1","驾驶人1电话","驾驶人1身份证号码","行驶区域","申报单位","申请时间","审批状态"]; 
	var keysArr =["LSH","PPXH","CSYSNAME","DJH","JSRXM1","LXDH1","SFZMHM1","XSQYNAME","SSDWNAME","SQRQ","SLYJ"];
	var rows = $('#dg').datagrid('getData').rows;
	for(var i in rows) {
		if(rows[i]['SLYJ'] == null){
			if(rows[i]['SL_INDEX']  == 0){
				rows[i]['SLYJ']= "等待协会审批";
			}else if(rows[i]['SL_INDEX']  == -1){
				rows[i]['SLYJ']=  "资料不全";
			}else {
				rows[i]['SLYJ']=  "等待交警审批";
			}
		}else if(rows[i]['SLYJ'] == 0){
			rows[i]['SLYJ']=  "已审核(同意) ";
		}else if(rows[i]['SLYJ'] == 1){
			rows[i]['SLYJ']=  "已审核(拒绝) ";
		}
		rows[i]['SQRQ'] = getLocalDate(rows[i]['SQRQ']);
	}
	var actionUrl = '<%=basePath%>ebikeQueryAction/exportExcel';
	var fileName="电动车备案申报列表";
	var content = JSON.stringify(rows);
	commonExcelExport(titleArr,keysArr,content,actionUrl,fileName); 
	

}

</script>
</head>
<body class="easyui-layout">

	<div>
		<div id="tb" class="searchdiv">
				<span>申报时间</span>
				<input id="dd" type="text" class="easyui-datebox" style="height: 30px;"></input>至  
				<input id="dtend" type="text" class="easyui-datebox"style="height: 30px;"></input>				
				<span>电机号</span> <input id="djh" class="easyui-validatebox"><br/>
				<span>审批状态</span>
				<select class="easyui-combobox" style="width:100px;height:32px; " id="zt">
					<option value="">所有</option>
					<option value="-1">审批中</option>
					<option value="0">已同意</option>
					<option value="1">已拒绝</option>
					
				</select>
				<span>行驶区域</span>	
				<input id="xsqy1" style="height:30px;" >
				<span>驾驶人1</span>	
				<input id="jsrxm1" style="height:30px;" >
				<span>流水号</span>	
				<input id="lsh" style="height:30px;" >
				 <a class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
				<div class="searchspan">	
				<span>剩余配额:${ddcHyxhSsdw.dwpe }</span>		
				<span>总配额:${ddcHyxhSsdw.totalPe }</span>
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
					<td>车身照片(600*400)</td>
					<td><input  type="file" 
						name="ebike_img" /><br /></td>
					<td>购车发票(600*400)</td>
					<td><input  type="file"
						name="ebike_invoice_img" /><br /></td>
				</tr>
				<tr>
					<td>出厂合格证(600*400)</td>
					<td><input  type="file"
						name="vcQualifiedImgfile" /><br /></td>
					<td>投保证明(600*400)</td>
					<td><input  type="file"
						name="vcEbikeInsuranceImgfile" /><br /></td>
				</tr>
				<tr>
					<td>品牌型号</td>
					<td><input class="easyui-validatebox" type="text"
						   name="ppxh"
						style="height: 32px;"></input></td>
					<td>车身颜色</td>
					<td><input id="cysy" name="cysy"  style="height:30px;"></td>
				</tr>
				<tr>
					<td>电机号：</td>
					<td><input class="easyui-validatebox" type="text"
						 name="djh" style="height: 32px"></input>
					</td>
					<td>脚踏装置:</td>
					<td><select id="jtzz" class="easyui-combobox" name="jtzz" 
						style="height:32px;width: 50px;">
							<option value="0">有</option>
							<option value="1">无</option>
					</select></td>
				</tr>
				<tr>
					<td>行驶区域</td>
					<td><input id="xsqy" name="xsqy"  style="height:30px;"></td>
					<td>备注</td>
					<td><textarea rows="5" cols="25" name="bz"></textarea></td>
				</tr>
				<tr>
					<td>驾驶人姓名1</td>
					<td><input id="sfzmhm1" name="sfzmhm1" value="01" /> </td>

					<td>驾驶人姓名2</td>
					<td><input id="sfzmhm2" name="sfzmhm2" value="01" /> </td>
				</tr>
				
			</table>
			<input  type="hidden" name="vcEbikeImg">
			<input  type="hidden" name="vcUser1Img">
			<input type="hidden" name="vcUser2Img">
			<input type="hidden" name="vcUser1CardImg1">
			<input type="hidden" name="vcUser1CardImg2">
			<input type="hidden" name="vcUser2CardImg1">
			<input type="hidden" name="vcUser2CardImg2">
			<input  type="hidden" name="lsh">
			<input  type="hidden" name="vcUser1WorkImg">
			<input  type="hidden" name="vcUser2WorkImg">
			<input  type="hidden" name="vcQualifiedImg">
			<input  type="hidden" name="vcEbikeInsuranceImg">
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
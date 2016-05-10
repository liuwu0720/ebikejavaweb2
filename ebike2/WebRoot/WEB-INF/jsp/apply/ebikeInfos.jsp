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

<title>电动车备案申报管理</title>

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

		url : "<%=basePath%>applyAction/queryAll?time=" + randomNu,
		title :  "电动车备案申报管理",
		iconCls : 'icon-danweixinxi',
		striped : true,
		fitColumns:true,   //数据列太少 未自适应
		pagination : true,
		rownumbers : true,
		pageSize:size,
		//singleSelect : true,//只选中单行
		 width:w,
		 height:h,
		loadMsg:'正在加载,请稍等...',
		rowStyler: function(index,row){
			if (row.SLYJ ==1){
				return 'background-color:#7AF1B5;color:#red;font-weight:bold;';
			}
		},
		columns : [ [{
			field : 'ID',
			title : 'ID',
			checkbox : true,
			width : 50
		},{
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
			field : 'XSQYNAME',
			title : '行驶区域',
			align:'center',
			width : 120
		},{
			field : 'SSDWNAME',
			title : '申报单位名称',
			align:'center',
			width : 120,
			formatter:function(value,row,index){
				var query = "<a  href='javascript:void(0)'  onclick='queryHyxhDwDetail(\""+row.SSDWID+"\")'>"+value+"</a>";
				return query;	
			}
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
					}else{
						return "等待交警审批";
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
				var update = "<a  href='javascript:void(0)'  onclick='updateRow("+row.ID+")'>审核</a>"
				if(row.SL_INDEX == 0){
					return query+update;
				}else{
					return query;
				}
				
			
			}
		}

		] ],

		toolbar : [ {
			id : 'btn2',
			text : '批量审核',
			iconCls : 'icon-reload',
			handler : function() {
				changeRowData();
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
//查询功能
function doSearch(){
	 $('#dg').datagrid('load',{
		zt: $("#zt").combobox('getValue'),
		djh: $('#djh').val(),
		dtstart:$('#dtstart').datebox('getValue'),// 获取日期输入框的值)
		dtend:$('#dtend').datebox('getValue'),
		xsqy:$("#xsqy1").combobox('getValue'),
		jsrxm1:$("#jsrxm1").val(),
		ssdw:$("#ssdw").combobox('getValue')
	}); 
}

//新增弹出
function addRowData(){
	$('#dgform').form('clear');
	$('#dw').attr("readonly",false);
	$('#dgformDiv').dialog('open').dialog('setTitle', '新增车辆申报');
	//所属单位
	$('#dw').combobox({    
	    url:'<%=basePath%>companyAction/getAllCompanyAjax',    
	    valueField:'id',    
	    textField:'dwmc',	   
	    value:"请选择",   //默认选中的值       
	    onSelect:function(row){
	    	$('#pe').show();
	    	$('#pe').html("剩余配额："+row.dwpe);
	    }
	}); 
	
	


	
	//车身颜色
	$('#cysy').combobox({
		 url:'<%=basePath%>applyAction/getAllColorsAjax',    
		    valueField:'dmz',    
		    textField:'dmms1',
		    value:"请选择"   //默认选中的值       
	});
	//行驶区域
	$('#xsqy').combobox({
		 url:'<%=basePath%>applyAction/getAllAreaAjax',    
		    valueField:'dmz',    
		    textField:'dmms1',
		    value:"请选择"   //默认选中的值       
	})
	
	$('#xb1,#xb2').combobox({
		value:-1
	});
	$('#jtzz').combobox({
		value:0
	});
	$("#file_tr1,#file_tr2").hide();
}
//查看备案审批详情
function queryRow(id){
	$.messager.progress({
		text:"正在处理，请稍候..."
	});
	window.location.href="<%=basePath%>applyAction/queryRecordApprovalInfoById?id="+id+"&&type="+2;
}

//修改
function updateRow(id){
	$.messager.progress({
		text:"正在处理，请稍候..."
	});
	window.location.href="<%=basePath%>applyAction/queryRecordApprovalInfoById?id="+id+"&&type="+1;
}

//保存操作
function updateSaveData(){
	$.messager.progress({
		text:"正在处理，请稍候..."
	});
	$('#dgform').form('submit', {
				url : "<%=basePath%>applyAction/saveOrUpdate",
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



//批量审核
function changeRowData(){
	var selected = $('#dg').datagrid('getSelections');
	var array = [];
	for(var i in selected){
		array.push(selected[i].ID);
	}
	if(selected.length == 0){
		alert("请至少选择一条数据");
	}else{
		$.messager.confirm('警告', '确认审批通过，请确认', function(r){
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
function excelExport(){
	var titleArr = ["流水号","品牌型号","车身颜色","电机号","驾驶人1","行驶区域","申报单位","申请时间","审批状态"]; 
	var keysArr =["LSH","PPXH","CSYSNAME","DJH","JSRXM1","XSQYNAME","SSDWNAME","SQRQ","SLYJ"];
	var rows = $('#dg').datagrid('getData').rows;
	for(var i in rows) {
		if(rows[i]['SLYJ'] == null){
			if(rows[i]['SL_INDEX']  == 0){
				rows[i]['SLYJ']= "等待协会审批";
			}else{
				rows[i]['SLYJ']=  "等待交警审批";
			}
		}else if(rows[i]['SLYJ'] == 0){
			rows[i]['SLYJ']=  "已审核(同意) ";
		}else if(rows[i]['SLYJ'] == 1){
			rows[i]['SLYJ']=  "已审核(拒绝) ";
		}
		rows[i]['SQRQ'] = getLocalTime(rows[i]['SQRQ']);
	}
	var actionUrl = '<%=basePath%>ebikeQueryAction/exportExcel';
	var fileName="电动车备案申报列表";
	var content = JSON.stringify(rows);
	commonExcelExport(titleArr,keysArr,content,actionUrl,fileName); 
	

}


//查看单位信息
function queryDwInfo(id){
	$.ajax({
		type: "GET",
   	    url: "<%=basePath%>companyAction/queryInfoById",
   	   data:{
		  id:id
	   }, 
	   dataType: "json",
	   success:function(data){
 			 
 			  if(data){
 				 $('#dwinfoDiv').dialog('open').dialog('setTitle', '详情信息');
 				 $('#dwform').form('load', data);
 				 $("#dwimg").attr("src",data.vcShowPath);
 				$("#dwimg_a").attr("href",data.vcShowPath);
 			  }
 		  }
	})
}


//查看单位详情
function queryHyxhDwDetail(obj){
	$.ajax({
		type: "GET",
	    url: "<%=basePath%>ssdwAction/querySsdwInfoById",
	   data:{
		id:obj
	   }, 
	   dataType: "json",
	   success:function(data){
			  if(data){
				 $('#dwinfoDiv').dialog('open').dialog('setTitle', '详情信息');
				 $('#dwform').form('load', data);
				 $("#dwimg_a").attr("href",data.vcShowPath);
				 $("#dwimg").attr("src",data.vcShowPath);
			  }
		  }
	})
}
</script>
</head>
<body class="easyui-layout">

	<div  >
		<div id="tb" class="searchdiv">
				<span>申报时间：</span>
				<input id="dtstart" type="text" class="easyui-datebox" style="height: 30px;"></input> 至：  
				<input id="dtend" type="text" class="easyui-datebox" style="height: 30px;"></input>				
				<span>电机号:</span> <input id="djh"
					style="line-height:26px;border:1px solid #ccc">
				<span>审批状态:</span>
				<select class="easyui-combobox" style="width:100px;height:32px; " id="zt">
					<option value="">所有</option>
					<option value="-1">审批中</option>
					<option value="0">已同意</option>
					<option value="1">已拒绝</option>
				</select><br/>
				<span>行驶区域</span>	
				<input id="xsqy1" style="height:30px;" >
				<span>驾驶人1</span>	
				<input id="jsrxm1" style="height:30px;" >	
				<span>公司名称:</span>
				 <input id="ssdw" style="height: 32px;">
				 <a class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
			</div>
		<table id="dg" style="width:90%;">
		</table>
	</div>
		
	
	<!-- 单位信息详情的表单 -->
	<div id="dwinfoDiv" class="easyui-dialog"
		style="width:500px;height:400px;padding:10px 20px 20px 20px;" closed="true" >
		<form id="dwform" class="easyui-form" method="post">
			<table class="dialogtable">
				
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
					<td>单位总配额</td>
					<td><input  name="totalPe"   readonly="readonly"  readonly="readonly"   type="text"></input>					
					</td>
				</tr>
				<tr>
					<td>剩余配额</td>
					<td><input  name="dwpe" d readonly="readonly"  readonly="readonly"   type="text"></input>					
					</td>
				</tr>
				<tr>
					<td>营业执照图片</td>
					<td>
						<a id="dwimg_a" target="_blank"><img id="dwimg"  class="easyui-validatebox" style="width:300px"   /></a><br/>
					</td>
				</tr>
			</table>
				
		</form>
		
	</div>

</body>
</html>
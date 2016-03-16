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

		url : "<%=basePath%>applyAction/queryAll?time=" + randomNu,
		title :  "电动车备案申报管理",
		iconCls : 'icon-danweixinxi',
		striped : true,
		fitColumns:true,   //数据列太少 未自适应
		pagination : true,
		rownumbers : true,
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
			field : 'lsh',
			title : '流水号',
			align:'center',
			width : 120
		},{
			field : 'ppxh',
			title : '品牌型号',
			align:'center',
			width : 220
		},{
			field : 'cysyName',
			title : '车身颜色',
			align:'center',
			width : 120
		},{
			field : 'djh',
			title : '电机号',
			align:'center',
			width : 120
		},{
			field : 'xsqyName',
			title : '行驶区域',
			align:'center',
			width : 120
		},{
			field : 'jsrxm1',
			title : '驾驶人',
			align:'center',
			width : 120
		},{
			field : 'ssdwName',
			title : '申报单位',
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
			field : 'synFlag',
			title : '状态',
			align:'center',
			width : 120,
			formatter:function(value,index){
				if(value == null){
					return "<p style='color:red'>未同步</p>";
				}else if(value == 'UC'){
					return "待审核 ";
				}else if(value == 'UW'){
					return "已审核 ";
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
				if(row.synFlag == null){
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
		}, {
			id : 'btn2',
			text : '同步',
			iconCls : 'icon-reload',
			handler : function() {
				changeRowData();
			}
		},{
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
	
	
});
//查询功能
function doSearch(){
	 $('#dg').datagrid('load',{
		zt: $("#zt").combobox('getValue'),
		djh: $('#djh').val(),
		dtstart:$('#dtstart').datebox('getValue'),// 获取日期输入框的值)
		dtend:$('#dtend').datebox('getValue')
	}); 
}

//新增弹出
function addRowData(){
	$('#dgform').form('clear');
	$('#dgformDiv').dialog('open').dialog('setTitle', '新增用户');
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
	
	$('#xb1,#xb2,#jtzz').combobox({
		value:0
	});
	$("#file_tr1,#file_tr2").hide();
}
//查看
function queryRow(id){
	$.ajax({
		type: "GET",
   	    url: "<%=basePath%>applyAction/queryInfoById",
   	   data:{
		  id:id
	   }, 
	   dataType: "json",
	   success:function(data){
 			 
 			  if(data){
 				 $('#dgformDiv2').dialog('open').dialog('setTitle', '详情信息');
 				 $('#dgform2').form('load', data);
 				 $("#img_0").attr("src",data.vcShowEbikeImg);
 				 $("#img1_1").attr("src",data.vcShowUser1Img);
 				 $("#img2_2").attr("src",data.vcShowUser2Img);
 			  }
 		  }
	})
}

//修改
function updateRow(id){
	$('#dgform').form('clear');
	$("#file_tr1,#file_tr2").show();
	$.ajax({
		type: "GET",
   	    url: "<%=basePath%>applyAction/queryInfoById",
   	   data:{
		  id:id
	   }, 
	   dataType: "json",
	   success:function(data){
 			 console.log(data);
 			  if(data){
 				 $('#dgformDiv').dialog('open').dialog('setTitle', '详情信息');
 				 $('#dgform').form('load', data);
 				 $("#img").attr("src",data.vcShowEbikeImg);
 				 $("#img1").attr("src",data.vcShowUser1Img);
 				 $("#img2").attr("src",data.vcShowUser2Img);
 				//所属单位
 				$('#dw').combobox({    
 				    url:'<%=basePath%>companyAction/getAllCompanyAjax',    
 				    valueField:'id',    
 				    textField:'dwmc',
 				    value:data.ssdwId,   //默认选中的值       
 				    text:data.ssdwName
 				}); 
 				//车身颜色
 				$('#cysy').combobox({
 					 url:'<%=basePath%>applyAction/getAllColorsAjax',    
 					    valueField:'dmz',    
 					    textField:'dmms1',
 					    value:data.cysy  //默认选中的值       
 				});
 				//行驶区域
 				$('#xsqy').combobox({
 					 url:'<%=basePath%>applyAction/getAllAreaAjax',    
 					    valueField:'dmz',    
 					    textField:'dmms1',
 					    value:data.xsqy   //默认选中的值       
 				})
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

function exportRowData(){
	var selected = $('#dg').datagrid('getSelections');
	var array = [];
	for(var i in selected){
		array.push(selected[i].id);
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
				
			<%-- 	$.post("<%=basePath%>applyAction/exportExcel", 
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
					  ,"html"); --%>
			
		
	}
}

</script>
</head>
<body class="easyui-layout">

	<div>
		<table id="dg" style="width:90%;">

			<div id="tb" style="padding: 5px; background: #E8F1FF;">
				<span>申报时间：</span>
				<input id="dtstart" type="text" class="easyui-datebox" style="height: 30px;"></input> 至：  
				<input id="dtend" type="text" class="easyui-datebox" style="height: 30px;"></input>				
				<span>电机号:</span> <input id="djh"
					style="line-height:26px;border:1px solid #ccc"> &nbsp;&nbsp;&nbsp;<span>状态:</span>
				<select class="easyui-combobox" style="width:100px;height:32px; " id="zt">
					<option value="">未同步</option>
					<option value="UC">待审核</option>
					<option value="UW">已审核</option>
				</select> <a class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
			</div>
		</table>
	</div>
	<!-- 点新增，编辑时弹出的表单 -->
	<div id="dgformDiv" class="easyui-dialog"
		style="width:850px;height:550px;padding:10px 20px 20px 20px;"
		closed="true" buttons="#dlg-buttons2">
		<form id="dgform" class="easyui-form" enctype="multipart/form-data"
			method="post">
			<table class="table">
				<tr style="display: none">
					<td>id</td>
					<td><input class="easyui-validatebox" type="text" name="id"></input>
					</td>
				</tr>
				<tr>
					<td>申报单位：</td>
					<td><input id="dw" name="ssdwId" style="height:30px;"><span id="pe" style="color: red;display: none"></span></td>
					<td>车身照片</td>
					<td><input  type="file" id="file_upload"
						name="file_upload" /><br /></td>
				</tr>
				<tr>
					<td>品牌型号</td>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true" name="ppxh"
						style="height: 32px;"></input></td>
					<td>车身颜色</td>
					<td><input id="cysy" name="cysy" style="height:30px;"></td>
				</tr>
				<tr>
					<td>电机号：</td>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true" name="djh" style="height: 32px"></input>
					</td>
					<td>脚踏装置:</td>
					<td><select id="jtzz" class="easyui-combobox" name="jtzz"
						style="height:32px;width: 50px;">
							<option value="0">有</option>
							<option value="1">无</option>
					</select></td>
				</tr>
				<tr>
					<td>驾驶人姓名1</td>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true" name="jsrxm1" style="height: 32px"></td>

					<td>驾驶人姓名2</td>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:false" name="jsrxm2" style="height: 32px"></td>
				</tr>
				<tr>
					<td>驾驶人性别1</td>
					<td><select id="xb1" class="easyui-combobox" name="xb1" required="true"  
						style="height:32px;width: 50px;">
							<option value="0">男</option>
							<option value="1">女</option>
					</select></td>

					<td>驾驶人性别2</td>
					<td><select id="xb2" class="easyui-combobox" name="xb2" required="false"  
						style="height:32px;width: 50px;">
							<option value="0">男</option>
							<option value="1">女</option>
					</select></td>
				</tr>
				<tr>
					<td>身份证号码1</td>
					<td><input class="easyui-validatebox" type="text" id="sfzmhm1"
						data-options="required:true,validType:'idcard'" name="sfzmhm1" style="height: 32px">
					</td>
					<td>身份证号码2</td>
					<td><input class="easyui-validatebox" type="text"  validType="notequals['#sfzmhm1']" 
					  name="sfzmhm2" style="height: 32px">
					</td>
				</tr>
				<tr>
					<td>联系电话1</td>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:true,validType:'phoneNum'" name="lxdh1" style="height: 32px">
					</td>
					<td>联系电话2</td>
					<td><input class="easyui-validatebox" type="text"
						data-options="required:false,validType:'phoneNum'" name="lxdh2" style="height: 32px">
					</td>
				</tr>
				<tr>
					<td>驾驶人照片1</td>
					<td><input  type="file" 
						name="file_upload1" onchange="CheckFileSize(this);" /><br /></td>
					<td>驾驶人照片2</td>
					<td><input  type="file" id="file_upload2"
						name="file_upload2" /><br /></td>
				</tr>
				<tr>
					<td>行驶区域</td>
					<td><input id="xsqy" name="xsqy" style="height:30px;" required="true"  ></td>
					<td>备注</td>
					<td><textarea rows="5" cols="25" name="bz"></textarea></td>
				</tr>
				<tr id="file_tr1">
					<td colspan="2"><img id="img1" class="easyui-validatebox"
						style="width:300px" /></td>
					<td colspan="2"><img id="img2" class="easyui-validatebox"
						style="width:300px" /><br /></td>

				</tr>
				<tr id="file_tr2">
					<td>车身照片</td>
					<td colspan="3"><img id="img" class="easyui-validatebox"
						style="width:300px" /></td>
				</tr>
			</table>
			<input class="easyui-validatebox" type="hidden" name="vcEbikeImg"
				style="height: 32px">
				<input class="easyui-validatebox" type="hidden" name="vcUser1Img"
				style="height: 32px">
				<input class="easyui-validatebox" type="hidden" name="vcUser2Img"
				style="height: 32px">
			<input class="easyui-validatebox" type="hidden" name="lsh"
				style="height: 32px">
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
		style="width:850px;height:550px;padding:10px 20px 20px 20px;"
		closed="true" buttons="#dlg-buttons2">
		<form id="dgform2" class="easyui-form" enctype="multipart/form-data"
			method="post">
			<table class="table">
				<tr style="display: none">
					<td>id</td>
					<td><input class="easyui-validatebox" type="text" name="id"></input>
					</td>
				</tr>
				<tr>
					<td>申报单位：</td>
					<td colspan="3"><input  name="ssdwName" class="easyui-validatebox" type="text" style="height:30px;" readonly="readonly"></td>					
				</tr>
				<tr>
					<td>品牌型号</td>
					<td><input class="easyui-validatebox" type="text" readonly="readonly"
						data-options="required:true" name="ppxh"
						style="height: 32px;"></input></td>
					<td>车身颜色</td>
					<td><input  name="cysyName" type="text" style="height:30px;" readonly="readonly"></td>
				</tr>
				<tr>
					<td>电机号：</td>
					<td><input class="easyui-validatebox" type="text"
						 name="djh" style="height: 32px" readonly="readonly"></input>
					</td>
					<td>脚踏装置:</td>
					<td><select class="easyui-combobox" name="jtzz"  readonly="readonly"
						style="height:32px;width: 50px;">
							<option value="0">有</option>
							<option value="1">无</option>
					</select></td>
				</tr>
				<tr>
					<td>驾驶人姓名1</td>
					<td><input class="easyui-validatebox" type="text"  readonly="readonly"
					       name="jsrxm1" style="height: 32px"></td>

					<td>驾驶人姓名2</td>
					<td><input class="easyui-validatebox" type="text"  readonly="readonly"
						 name="jsrxm2" style="height: 32px"></td>
				</tr>
				<tr>
					<td>驾驶人性别1</td>
					<td><select  class="easyui-combobox" name="xb1"   readonly="readonly"
						style="height:32px;width: 50px;">
							<option value="0">男</option>
							<option value="1">女</option>
					</select></td>

					<td>驾驶人性别2</td>
					<td><select id="xb2" class="easyui-combobox" name="xb2"    readonly="readonly"
						style="height:32px;width: 50px;">
							<option value="0">男</option>
							<option value="1">女</option>
					</select></td>
				</tr>
				<tr>
					<td>身份证号码1</td>
					<td><input class="easyui-validatebox" type="text" id="sfzmhm1"  readonly="readonly"
						 name="sfzmhm1" style="height: 32px">
					</td>
					<td>身份证号码2</td>
					<td><input class="easyui-validatebox" type="text"  readonly="readonly" 
					  name="sfzmhm2" style="height: 32px">
					</td>
				</tr>
				<tr>
					<td>联系电话1</td>
					<td><input class="easyui-validatebox" type="text"  readonly="readonly"
						 name="lxdh1" style="height: 32px">
					</td>
					<td>联系电话2</td>
					<td><input class="easyui-validatebox" type="text"  readonly="readonly"
						 name="lxdh2" style="height: 32px">
					</td>
				</tr>
				<tr>
					<td>行驶区域</td>
					<td><input id="xsqy" name="xsqyName" style="height:30px;"  readonly="readonly" ></td>
					<td>备注</td>
					<td><textarea rows="5" cols="25" name="bz"></textarea></td>
				</tr>
				<tr id="file_tr1">
					<td colspan="2"><img id="img1_1" class="easyui-validatebox"
						style="width:300px" /></td>
					<td colspan="2"><img id="img2_2" class="easyui-validatebox"
						style="width:300px" /><br /></td>

				</tr>
				<tr id="file_tr2">
					<td>车身照片</td>
					<td colspan="3"><img id="img_0" class="easyui-validatebox"
						style="width:300px" /></td>
				</tr>
			</table>
		</form>
	
	</div>

</body>
</html>
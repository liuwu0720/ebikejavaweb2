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

		url : "<%=basePath%>ebikeChangeAction/queryAll?time=" + randomNu,
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
			field : 'zzjgdmzh',
			title : '单位名称',
			align:'center',
			width : 120
		},{
			field : 'dabh',
			title : '档案编号',
			align:'center',
			width : 220
		},{
			field : 'cphm',
			title : '车牌号',
			align:'center',
			width : 120
		},{
			field : 'djh',
			title : '电机号',
			align:'center',
			width : 120
		},{
			field : 'slrq',
			title : '归档时间',
			align:'center',
			width : 120,
			formatter:function(value,index){
				var unixTimestamp = new Date(value);   
				return unixTimestamp.toLocaleString();
			}   
		},{
			field : 'zt',
			title : '车辆状态',
			align:'center',
			width : 120
		},{
			field : 'null',
			title:'操作',
			align:'center',
			width : 120,
			formatter:function(value,row,index){
				var query = "<a  href='javascript:void(0)'  onclick='queryRow("+row.id+")'>查看</a>&nbsp;&nbsp;&nbsp; |"
				var update = "<a  href='javascript:void(0)'  onclick='updateRow("+row.id+")'>变更</a>"
			
					return query+update;				
			
			}
		}

		] ],

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
	
</body>
</html>
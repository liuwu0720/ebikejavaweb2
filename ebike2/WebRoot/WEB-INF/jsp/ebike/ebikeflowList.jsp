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

<title>业务流水查询</title>

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

		url : "<%=basePath%>ebikeWaterAction/queryAll?time=" + randomNu,
		title :  "业务流水查询",
		iconCls : 'icon-search',
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
			field : 'ID',
			title : 'ID',
			checkbox : true,
			align:'center',
			width : 120
		},{
			field : 'DWMC',
			title : '单位名称',
			align:'center',
			width : 120
		},{
			field : 'LSH',
			title : '流水号',
			align:'center',
			width : 220
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
			field : 'YWLX',
			title : '业务类型',
			align:'center',
			width : 120
		},{
			field : 'SSQY',
			title : '所属区域',
			align:'center',
			width : 120
		},{
			field : 'SLYJ',
			title : '受理意见',
			align:'center',
			width : 120,
			formatter:function(value,index){
				if(value == 0){
					return "同意";
				}else{
					return "不同意";
				}
			}
		},{
			field : 'SLRQ',
			title : '归档时间',
			align:'center',
			width : 120,
			formatter:function(value,index){
				var unixTimestamp = new Date(value);   
				return unixTimestamp.toLocaleString();
			}   
		},{
			field : 'null',
			title:'操作',
			align:'center',
			width : 120,
			formatter:function(value,row,index){
				
				var query = "<a  href='javascript:void(0)'  onclick='queryRow("+row.ID+")'>查看</a>";
				return query;	
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
		ywlx: $("#ywlx").combobox('getValue'),
		djh: $('#djh').val(),
		cphm:$("#cphm").val(),
		dtstart:$('#dtstart').datebox('getValue'),// 获取日期输入框的值)
		dtend:$('#dtend').datebox('getValue')
	}); 
}


//查看
function queryRow(id){
	$.ajax({
		type: "GET",
   	    url: "<%=basePath%>ebikeWaterAction/queryInfoById",
   	   data:{
		  id:id
	   }, 
	   dataType: "json",
	   success:function(data){
 			 
 			  if(data){
 				 $('#dgformDiv2').dialog('open').dialog('setTitle', '详情信息');
 				$('#dgform2').form('clear');
 				if(data.slyj == '0'){
 					data.slyj = '同意';
 				}else{
 					data.slyj = '不同意';
 				}
 				if(data.gdyj == '0'){
 					data.gdyj = '办洁';
 				}else{
 					data.gdyj = '退办';
 				}
 				
 				 $('#dgform2').form('load', data);
 				
 			 	if(data.vcShowEbikeImg == null){
					 $("#img_0").attr("src","<%=basePath%>static/images/iconfont-wu.png");
				}else{
					$("#img_0").attr("src",data.vcShowEbikeImg);
				}
				if(data.vcShowUser2Img == null){
					 $("#img2_2").attr("src","<%=basePath%>static/images/iconfont-wu.png");
				}else{
					$("#img2_2").attr("src",data.vcShowUser2Img);
				}
				if(data.vcShowUser1Img == null){
					 $("#img1_1").attr("src","<%=basePath%>static/images/iconfont-wu.png");
				}else{
					$("#img1_1").attr("src",data.vcShowUser1Img);
				} 
 			  }
 		  }
	})
}



</script>
</head>
<body class="easyui-layout">

	<div>
		<table id="dg" style="width:90%;">

			<div id="tb" style="padding: 5px; background: #E8F1FF;">
				<span>业务类型</span>
				<select class="easyui-combobox" style="width:100px;height:32px; " id="ywlx">
					<option value="">--请选择--</option>
					<c:forEach items="${ywylxs }" var="lx">
						<option value="${lx.dmz }">${lx.dmms1 }</option>
					</c:forEach>
				</select>	
				<span>受理时间：</span>
				<input id="dtstart" type="text" class="easyui-datebox" style="height: 30px;"></input> 至：  
				<input id="dtend" type="text" class="easyui-datebox" style="height: 30px;"></input>		
				<span>车牌号:</span> <input id="cphm" name="cphm"
					class="easyui-validatebox" type="text" > &nbsp;&nbsp;&nbsp;
				<span>电机号:</span> <input id="djh" name="djh"
					class="easyui-validatebox" type="text" > &nbsp;&nbsp;&nbsp;	
				<a class="easyui-linkbutton" plain="true" onclick="doSearch()"
					iconCls="icon-search">查询 </a>
			</div>
		</table>
	</div>
	
	
		<!-- 点查看时弹出的表单 -->
	<div id="dgformDiv2" class="easyui-dialog"
		style="width:850px;height:550px;padding:10px 20px 20px 20px;"
		closed="true" >
		<form id="dgform2" class="easyui-form" enctype="multipart/form-data"
			method="post">
			<table id="table1" class="table table-condensed">
				<tr style="display: none">
					<td>id</td>
					<td><input class="easyui-validatebox" type="text" name="id"></input>
					</td>
				</tr>
				<tr>
					<th>申报单位：</th>
					<td><input  name="zzjgdmzhName" class="easyui-validatebox" type="text" style="height:30px;width: 200px;" readonly="readonly"></td>					
					<th>业务流水号：</th>
					<td><input name="lsh"  type="text" class="easyui-validatebox" readonly="readonly"></td>
				</tr>
				<tr>
					<th>业务类型：</th>
					<td><input  name="ywlxName" class="easyui-validatebox" type="text" readonly="readonly"></td>					
					<th>业务原因：</th>
					<td><input name="ywyyName"  type="text" class="easyui-validatebox" readonly="readonly"></td>
				</tr>
				
				<tr>
					<th>品牌型号</th>
					<td><input class="easyui-validatebox" type="text" readonly="readonly"
						data-options="required:true" name="ppxh"
						style="height: 32px;"></input></td>
					<th>车身颜色</th>
					<td><input  name="cysyName" type="text" style="height:30px;" readonly="readonly"></td>
				</tr>
				<tr>
					<th>电机号：</th>
					<td><input class="easyui-validatebox" type="text"
						 name="djh" style="height: 32px" readonly="readonly"></input>
					</td>
					<th>脚踏装置:</th>
					<td><select class="easyui-combobox" name="jtzz"  readonly="readonly"
						style="height:32px;width: 50px;">
							<option value="0">有</option>
							<option value="1">无</option>
					</select></td>
				</tr>
				<tr>
					<th>驾驶人姓名1</th>
					<td><input class="easyui-validatebox" type="text"  readonly="readonly"
					       name="jsrxm1" style="height: 32px"></td>

					<th>驾驶人姓名2</th>
					<td><input class="easyui-validatebox" type="text"  readonly="readonly"
						 name="jsrxm2" style="height: 32px"></td>
				</tr>
				<tr>
					<th>驾驶人性别1</th>
					<td><select  id="xb1" class="easyui-combobox" name="xb1"   readonly="readonly"
						style="height:32px;width: 100px;">
							<option value="-1">请选择</option>
							<option value="0">男</option>
							<option value="1">女</option>
					</select></td>

					<th>驾驶人性别2</th>
					<td><select id="xb2" class="easyui-combobox" name="xb2"    readonly="readonly"
						style="height:32px;width: 100px;">
							<option value="-1">请选择</option>
							<option value="0">男</option>
							<option value="1">女</option>
					</select></td>
				</tr>
				<tr>
					<th>身份证号码1</th>
					<td><input class="easyui-validatebox" type="text" id="sfzmhm1"  readonly="readonly"
						 name="sfzmhm1" style="height: 32px">
					</td>
					<th>身份证号码2</th>
					<td><input class="easyui-validatebox" type="text"  readonly="readonly" 
					  name="sfzmhm2" style="height: 32px">
					</td>
				</tr>
				<tr>
					<th>联系电话1</th>
					<td><input class="easyui-validatebox" type="text"  readonly="readonly"
						 name="lxdh1" style="height: 32px">
					</td>
					<th>联系电话2</th>
					<td><input class="easyui-validatebox" type="text"  readonly="readonly"
						 name="lxdh2" style="height: 32px">
					</td>
				</tr>
				<tr>
					<th>行驶区域</th>
					<td><input id="xsqy" name="xsqyName"  style="height:30px;"  readonly="readonly" ></td>
					<th>备注</th>
					<td><textarea rows="5" cols="25" name="bz"></textarea></td>
				</tr>
				<tr>
					<th>受理意见</th>
					<td><input id="slyj" name="slyj"  style="height:30px;"   readonly="readonly" ></td>
					<th>受理日期</th>
					<td><input id="xsqy" name="slrq"  style="height:30px;"   readonly="readonly" ></td>
				</tr>
				<tr>
					<th>受理资料</th>
					<td colspan="3"><input id="slzlList" name="slzlList"  style="height:30px;width:100%"   readonly="readonly" ></td>
				</tr>
				<tr>
					<th>归档意见</th>
					<td><input id="slyj" name="gdyj"  style="height:30px;"   readonly="readonly" ></td>
					<th>归档日期</th>
					<td><input id="xsqy" name="gdrq"  style="height:30px;"   readonly="readonly" ></td>
				</tr>
					<tr>
					<td colspan="2">
					<div  class="imgdiv">
					<p>驾驶人1</p><img id="img1_1"  src="<%=basePath%>static/images/iconfont-wu.png"/></div>
					</td>
					<td colspan="2">
					<div  class="imgdiv">
					<p>驾驶人2</p><img id="img2_2" src="<%=basePath%>static/images/iconfont-wu.png" /></div></td>
				</tr>
				<tr>
					<th>车身照片</th>
					<td colspan="3"><div  class="imgdiv">
					<img id="img_0"  src="<%=basePath%>static/images/iconfont-wu.png" /></div></td>
				</tr>
			</table>
		</form>
	
	</div>
	

</body>
</html>
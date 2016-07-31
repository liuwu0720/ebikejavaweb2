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

<title>备案详情</title>

<%@include file="../common/common.jsp"%>
<script type="text/javascript">

	$(document).ready(function(){
		  
		$('#cysy').combobox({
			 url:'<%=basePath%>applyAction/getAllColorsAjax',    
			    valueField:'dmz',    
			    textField:'dmms1',
			    value:'${ddcHyxhSsdwclsb.cysy}'   
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
		
		$('#xsqy').combobox({
			url:'<%=basePath%>applyAction/getAllAreaAjax',    
		    valueField:'dmz',    
		    textField:'dmms1',
			value:${ddcHyxhSsdwclsb.xsqy }
		})
}) 
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
	//保存操作
	function updateSaveData(){
		var flag = true;
		if(flag){
			$.messager.progress({
				text:"正在处理，请稍候..."
			});
			$('#dgform').form('submit', {
					url : "<%=basePath%>baAction/saveOrUpdateBaClSb",
					onSubmit : function() {
						var isValid = $("#dgform").form('enableValidation').form('validate');

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
							window.location.reload();
							//window.location.href="<%=basePath%>baAction/getAllBa"
							} else {
										alert(data.message);
									}
									$.messager.progress('close'); // 如果提交成功则隐藏进度条

								}

							});
		}
	}
</script>

</head>

<body>
	<div class="maindiv">
		<form id="dgform" class="easyui-form" enctype="multipart/form-data"
			method="post">
			<h2>修改车辆备案申报</h2>
			<!--startprint-->
			<table id="main" class="table table-condensed" border="1"
				cellpadding="0" cellspacing="0" width="98%">

				<tr>
					<th>品牌型号</th>
					<td><input id="ppxh" class="easyui-validatebox" type="text"
						value="${ddcHyxhSsdwclsb.ppxh }" 
						name="ppxh"></td>
					<th>电机号</th>
					<td><input id="ppxh" class="easyui-validatebox" type="text"
						value="${ddcHyxhSsdwclsb.djh }" 
						name="djh"></td>
					<th>车身颜色</th>
					<td><input id="cysy" style="height: 32px;" name="cysy">
					</td>
					<th>脚踏装置:</th>
					<td><select id="jtzz" class="easyui-combobox" name="jtzz"
						value="${ddcHyxhSsdwclsb.jtzz }" style="height:32px;width: 50px;">
							<option value="0">有</option>
							<option value="1">无</option>
					</select></td>
				</tr>
				<tr>
					<th>流水号</th>
					<td>${ddcHyxhSsdwclsb.lsh }</td>

					<th>所属单位</th>
					<td>${ddcHyxhSsdwclsb.ssdwName }</td>

					<th>行驶区域</th>
					<td><input id="xsqy" name="xsqy" style="height:30px;"></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<th>驾驶人姓名1</th>
					<td><input id="sfzmhm1" name="sfzmhm1" value="${ddcHyxhSsdwclsb.jsrxm1 }"/> </td>

					<th>驾驶人姓名2</th>
					<td><input id="sfzmhm2" name="sfzmhm2"   value="${ddcHyxhSsdwclsb.jsrxm2 }"/> </td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>

				<tr>
					<th>申请备注</th>
					<td colspan="7"><textarea rows="5" cols="22" name="bz">${ddcHyxhSsdwclsb.bz }</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2"><input type="file" name=vcQualifiedImgfile
						onchange="CheckFileSize(this);" /></td>
					<td colspan="2"><input type="file" name="vcEbikeInsuranceImgfile"
						onchange="CheckFileSize(this);" /></td>
					<td colspan="2"><input type="file" name="ebike_img"
						onchange="CheckFileSize(this);" /></td>
					<td colspan="2"><input type="file" name="ebike_invoice_img"
						onchange="CheckFileSize(this);" /></td> 
				</tr>
				<tr>
					<td colspan="2">
						<div class="imgdiv">
							<p>车辆合格证</p>
							<a href="${ddcHyxhSsdwclsb.vcQualifiedImgShow }" target="_blank">
								<img src="${ddcHyxhSsdwclsb.vcQualifiedImgShow }" />
							</a>
						</div>
					</td>
					<td colspan="2">
						<div class="imgdiv">
							<p>投保凭证</p>
							<a href="${ddcHyxhSsdwclsb.vcEbikeInsuranceImgShow }"
								target="_blank"> <img
								src="${ddcHyxhSsdwclsb.vcEbikeInsuranceImgShow }" />
							</a>
						</div>
					</td>
					<td colspan="2"><a href="${ddcHyxhSsdwclsb.vcShowEbikeImg }"
						target="_blank">
							<div class="imgdiv">
								<p>车身照片</p>
								<img src="${ddcHyxhSsdwclsb.vcShowEbikeImg }" />
							</div>
					</a></td>
					<td colspan="2"><a
						href="${ddcHyxhSsdwclsb.vcEbikeInvoiceImgShow }" target="_blank">
							<div class="imgdiv">
								<p>购车发票</p>
								<img src="${ddcHyxhSsdwclsb.vcEbikeInvoiceImgShow }" />
							</div>
					</a></td>
				</tr>

				<tr>
					<td colspan="8" height="35" style="text-align:center;"><input
						type="button" value="修改 " class="btn" id="bgbtn"
						onclick="updateSaveData();">&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="button" value=" 返 回 " class="btn"
						onclick="javascript:window.history.back();"></td>
				</tr>
			</table>
			<input type="hidden" value="${ddcHyxhSsdwclsb.id }" name="id">
			<input type="hidden" name="slzllist" id="slzllist"> <input
				type="hidden" value="${ddcHyxhSsdwclsb.vcEbikeImg }"
				name="vcEbikeImg"> <input type="hidden"
				value="${ddcHyxhSsdwclsb.vcUser1Img }" name="vcUser1Img"> <input
				type="hidden" value="${ddcHyxhSsdwclsb.vcUser2Img }"
				name="vcUser2Img"> <input type="hidden"
				value="${ddcHyxhSsdwclsb.vcUser1CardImg1 }" name="vcUser1CardImg1">
			<input type="hidden" value="${ddcHyxhSsdwclsb.vcUser1CardImg2 }"
				name="vcUser1CardImg2"> <input type="hidden"
				value="${ddcHyxhSsdwclsb.vcUser2CardImg1 }" name="vcUser2CardImg1">
			<input type="hidden" value="${ddcHyxhSsdwclsb.vcUser2CardImg2 }"
				name="vcUser2CardImg2"> <input type="hidden"
				value="${ddcHyxhSsdwclsb.vcEbikeInvoiceImg }"
				name="vcEbikeInvoiceImg"> <input type="hidden"
				value="${ddcHyxhSsdwclsb.lsh }" name="lsh">
			<input  type="hidden" name="vcUser1WorkImg" value="${ddcHyxhSsdwclsb.vcUser1WorkImg }">
			<input  type="hidden" name="vcUser2WorkImg"  value="${ddcHyxhSsdwclsb.vcUser2WorkImg }">
			<input  type="hidden" name="vcQualifiedImg"  value="${ddcHyxhSsdwclsb.vcQualifiedImg }">
			<input  type="hidden" name="vcEbikeInsuranceImg"  value="${ddcHyxhSsdwclsb.vcEbikeInsuranceImg }">	
				
		</form>
	</div>

</body>
</html>

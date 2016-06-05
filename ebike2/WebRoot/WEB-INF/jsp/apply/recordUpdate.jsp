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
		$('#xb1').combobox({
			value:${ddcHyxhSsdwclsb.xb1 }
		});
		$('#xb2').combobox({
			value:${ddcHyxhSsdwclsb.xb2 }
		});
		
		
		$('#xsqy').combobox({
			url:'<%=basePath%>applyAction/getAllAreaAjax',    
		    valueField:'dmz',    
		    textField:'dmms1',
			value:${ddcHyxhSsdwclsb.xsqy }
		})
}) 
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
	//保存操作
	function updateSaveData(){
		var flag = true;
		if(flag){
			$.messager.progress({
				text:"正在处理，请稍候..."
			});
			$('#dgform').form('submit', {
					url : "<%=basePath%>ssdwAction/saveOrUpdateBaClSb",
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
							window.location.href="<%=basePath%>ssdwAction/getAllBa"
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
					<td><input class="easyui-validatebox" type="text"
						value="${ddcHyxhSsdwclsb.jsrxm1 }" data-options="required:true"
						name="jsrxm1"></input></td>
					<th>驾驶人性别1</th>
					<td><select id="xb1" class="easyui-combobox" name="xb1"
						value="${ddcHyxhSsdwclsb.xb1 }" style="height:32px;width: 80px;">
							<option value="-1">请选择</option>
							<option value="0">男</option>
							<option value="1">女</option>
					</select></td>
					<th>身份证号码1</th>
					<td><input class="easyui-validatebox" type="text"
						value="${ddcHyxhSsdwclsb.sfzmhm1 }"
						data-options="required:true,validType:'idcard'" name="sfzmhm1"></input>
					</td>
					<th>联系电话1</th>
					<td><input class="easyui-validatebox" type="text"
						value="${ddcHyxhSsdwclsb.lxdh1 }"
						data-options="required:true,validType:'phoneNum'" name="lxdh1"
						style="height: 32px"></td>

				</tr>
				<tr>
					<th>驾驶人姓名2</th>
					<td><input class="easyui-validatebox" type="text"
						value="${ddcHyxhSsdwclsb.jsrxm2 }" name="jsrxm2"></input></td>
					<th>驾驶人性别2</th>
					<td><select id="xb2" class="easyui-combobox" name="xb2"
						value="${ddcHyxhSsdwclsb.xb2 }" style="height:32px;width: 80px;">
							<option value="-1">请选择</option>
							<option value="0">男</option>
							<option value="1">女</option>
					</select></td>
					<th>身份证号码2</th>
					<td><input class="easyui-validatebox" type="text"
						value="${ddcHyxhSsdwclsb.sfzmhm2 }"
						data-options="validType:'idcard'" name="sfzmhm2"></input></td>
					<th>联系电话2</th>
					<td><input class="easyui-validatebox" type="text"
						value="${ddcHyxhSsdwclsb.lxdh2 }"
						data-options="validType:'phoneNum'" name="lxdh2"
						style="height: 32px"></td>
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
					<td colspan="2"><input type="file" name="headimg_jsr1"
						onchange="CheckFileSize(this);" /></td>
					<td colspan="2"><input type="file" name="card1img_jsr1"
						onchange="CheckFileSize(this);" /></td>

					<td colspan="2"><input type="file" name="card2img_jsr1"
						onchange="CheckFileSize(this);" /></td>

					 <td colspan="2"><input type="file" name="vcUser1WorkImgfile"
						onchange="CheckFileSize(this);" /></td> 

				</tr>
				<tr>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人1头像</p>
							<a href="${ddcHyxhSsdwclsb.vcShowUser1Img }" target="_blank">
								<img src="${ddcHyxhSsdwclsb.vcShowUser1Img }" />
							</a>
						</div>
					</td>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人1身份证正面</p>
							<a href="${ddcHyxhSsdwclsb.vcUser1CardImg1Show }" target="_blank">
								<img src="${ddcHyxhSsdwclsb.vcUser1CardImg1Show }" />
							</a>
						</div>
					</td>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人1身份证反面</p>
							<a href="${ddcHyxhSsdwclsb.vcUser1CardImg2Show }" target="_blank">
								<img src="${ddcHyxhSsdwclsb.vcUser1CardImg2Show }" />
							</a>
						</div>
					</td>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人1居住证或在职证明</p>
							<a href="${ddcHyxhSsdwclsb.vcUser1WorkImgShow }" target="_blank">
								<img src="${ddcHyxhSsdwclsb.vcUser1WorkImgShow }" />
							</a>
						</div>
					</td>
				</tr>

				<tr>
					<td colspan="2"><input type="file" name="headimg_jsr2"
						onchange="CheckFileSize(this);" /></td>
					<td colspan="2"><input type="file" name="card1img_jsr2"
						onchange="CheckFileSize(this);" /></td>

					<td colspan="2"><input type="file" name="card2img_jsr2"
						onchange="CheckFileSize(this);" /></td>

				 	<td colspan="2"><input type="file" name="vcUser2WorkImgfile"
						onchange="CheckFileSize(this);" /></td> 
				</tr>
				<tr>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人2头像</p>
							<a href="${ddcHyxhSsdwclsb.vcShowUser2Img }" target="_blank">
								<img src="${ddcHyxhSsdwclsb.vcShowUser2Img }" />
							</a>
						</div>
					</td>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人2身份证正面</p>
							<a href="${ddcHyxhSsdwclsb.vcUser2CardImg1Show }" target="_blank">
								<img src="${ddcHyxhSsdwclsb.vcUser2CardImg1Show }" />
							</a>
						</div>
					</td>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人2身份证反面</p>
							<a href="${ddcHyxhSsdwclsb.vcUser2CardImg2Show }" target="_blank">
								<img src="${ddcHyxhSsdwclsb.vcUser2CardImg2Show }" />
							</a>
						</div>
					</td>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人2在职证明或居住证</p>
							<a href="${ddcHyxhSsdwclsb.vcUser2WorkImgShow }" target="_blank">
								<img src="${ddcHyxhSsdwclsb.vcUser2WorkImgShow }" />
							</a>
						</div>
					</td>
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

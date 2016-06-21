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
<title>变更详情</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<%@include file="../common/common.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
	
	
	
	$('[name=ywyys]').each(function(){
		$(this).click(function(){
			if($('#cj').attr('checked')=='checked'||$('#hc').attr('checked')=='checked'||$('#jsr').attr('checked')=='checked'){
				$('#bgbtn').removeAttr('disabled');
			}else{
				$('#bgbtn').attr('disabled',true);
			}
			if($('#area').attr('checked')=='checked'){
				$('#jybtn').removeAttr('disabled');
			}else{
				$('#jybtn').attr('disabled',true);
			}
		});
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
	
});

function selectJSR(){
	
	if($('#jsr').attr('checked')=='checked'){
		$('#sfzmhm1').removeAttr('disabled');
		/*$('#jsrxm1').removeAttr('disabled');
		$('#xb1').removeAttr('disabled');
		$('#sfzmhm1').removeAttr('disabled');
		$('#lxdh1').removeAttr('disabled');
		$('#jsrxm2').removeAttr('disabled');
		$('#xb2').removeAttr('disabled');
		$('#sfzmhm2').removeAttr('disabled');
		$('#lxdh2').removeAttr('disabled');
		 $('#ebike_img').removeAttr('disabled');
		$('#headimg_jsr1').removeAttr('disabled');
		$('#headimg_jsr2').removeAttr('disabled');
		$('#card1img_jsr1').removeAttr('disabled');
		$('#card2img_jsr1').removeAttr('disabled');
		$('#card1img_jsr2').removeAttr('disabled');
		$('#card2img_jsr2').removeAttr('disabled');
		$('#vcUser1WorkImg_fileid').removeAttr('disabled');
		$('#vcUser2WorkImg_fileid').removeAttr('disabled'); */
		unselectArea();
	}else{
		/*$('#jsrxm1').attr('disabled',true);
		$('#xb1').attr('disabled',true);
		$('#sfzmhm1').attr('disabled',true);
		$('#lxdh1').attr('disabled',true);
		$('#jsrxm2').attr('disabled',true);
		$('#xb2').attr('disabled',true);
		$('#sfzmhm2').attr('disabled',true);
		$('#lxdh2').attr('disabled',true);
		 $('#ebike_img').attr('disabled',true);
		$('#headimg_jsr1').attr('disabled',true);
		$('#headimg_jsr2').attr('disabled',true);
		$('#card1img_jsr1').attr('disabled',true);
		$('#card2img_jsr1').attr('disabled',true);
		$('#card1img_jsr2').attr('disabled',true);
		$('#card2img_jsr2').attr('disabled',true);
		$('#vcUser1WorkImg_fileid').attr('disabled',true);
		$('#vcUser2WorkImg_fileid').attr('disabled',true); */
	}
}
function selectArea(){
	if($('#area').attr('checked')=='checked'){
		$('#fxsqy').attr('disabled',false);
		$('#cj').attr('checked',false);
		$('#hc').attr('checked',false);
		$('#jsr').attr('checked',false);
		
		selectJSR();
	}else{
		$('#fxsqy').attr('disabled',true);
	}
}
function unselectArea(){
	if($('#area').attr('checked')=='checked'){
		$('#area').attr('checked',false);
		$('#fxsqy').attr('disabled',true);
		$('#jybtn').attr('disabled',true);
	}
}

function changetype(state){
	//变更
	if(state == 1){
		var flag = checkVarible();
		if(flag){
			
			 $('#dgformDiv2').dialog('open').dialog('setTitle', '说明变更详情');	
		}
	
	
	}
	//转移
	if(state == 2){
		var flag = checkVarible();
		if(flag){
			$('#dgformDiv3').dialog('open').dialog('setTitle', '说明转移详情');	
		}
		
	}
}

//转移
function zhuanyi(){
	
	$("#bz").attr("value",$("#note2").val());
		$.messager.progress({
			text:"正在处理，请稍候..."
		});
		$('#dgform').form('submit', {
			url : "<%=basePath%>ssdwChangeAction/zhuanyi",
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
					history.go(-1);
				}else{
					alert(data.message);
				}
				$.messager.progress('close'); // 如果提交成功则隐藏进度条

			}

		});
	
	
}


//变更
function change(){
	var flag = checkVarible();
	if(flag){
		$("#bz").attr("value",$("#note").val());
		$.messager.progress({
			text:"正在处理，请稍候..."
		});
		$('#dgform').form('submit', {
			url : "<%=basePath%>ssdwChangeAction/changeData",
						onSubmit : function() {

							isValid = $("#dgform").form('enableValidation')
									.form('validate');

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
								history.go(-1);
							} else {
								alert(data.message);
							}
							$.messager.progress('close'); // 如果提交成功则隐藏进度条

						}

					});
		}

	}

	var AllowExt = ".jpg|.jpeg|.gif|.bmp|.png|" //允许上传的文件类型 ŀ为无限制 每个扩展名后边要加一个"|" 小写字母表示
	function CheckFileSize(obj) {
		if (obj.value != "") {
			//检测类型
			var val = obj.value;
			var FileExt = obj.value.substr(obj.value.lastIndexOf("."))
					.toLowerCase();
			if (AllowExt.indexOf(FileExt + "|") == -1) {//判断文件类型是否允许上传
				$.messager.alert('警告', '你上传的不是图片文件');
				return false;
			}
		}
	}
	function checkVarible() {
		if (!$("input[name='slzls']:checked").val()) {
			alert("请勾选变更资料")
			return false;
		} else {
			var vs = "";
			$('[id=slzls]:checked').each(function() {
				vs += $(this).val() + ',';
			});
			vs = vs.substr(0, vs.lastIndexOf(','));
			$("#slzllist").val(vs);
			return true;
		}
	}
</script>

</head>

<body>
	<div class="maindiv">
		<form id="dgform" class="easyui-form" enctype="multipart/form-data"
			method="post">
			<table class="table table-condensed" border="1" cellpadding="0"
				cellspacing="0" width="98%">

				<tr>
					<th>变更类型：</th>
					<td colspan="7" style="font-weight:bold;color:blue;"><input
						type="checkbox" id="jsr" name="ywyys" value="E"
						onclick="selectJSR()" />驾驶人 <input type="checkbox" id="area"
						name="ywyys" value="F" onclick="selectArea()" />行驶区域</td>
				</tr>
				<tr>
					<th>品牌型号</th>
					<td><input id="ppxh" class="easyui-validatebox" type="text"
						disabled="disabled" value="${ddcDaxxb.ppxh }"
						data-options="required:true" name="ppxh"></input></td>
					<th>电机号：</th>
					<td><input id="djh" class="easyui-validatebox" type="text"
						value="${ddcDaxxb.djh }" name="djh" disabled="disabled"></input></td>
					<th>车身颜色</th>
					<td>${ddcDaxxb.cysyName }</td>
					<th>脚踏装置:</th>
					<c:if test="${ddcDaxxb.jtzz ==0 }">
						<td>有</td>
					</c:if>
					<c:if test="${ddcDaxxb.jtzz ==1 }">
						<td>无</td>
					</c:if>

				</tr>

				<tr>
					<th>驾驶人姓名1</th>
					<td><input id="sfzmhm1" name="sfzmhm1" value="${ddcDaxxb.jsrxm1 }" /> </td>

					<th>驾驶人姓名2</th>
					<td><input id="sfzmhm2" name="sfzmhm2"   value="${ddcDaxxb.jsrxm2 }" /> </td>
					<td></td>
					<td></td>

				</tr>
				
				<tr>
					<th>行驶区域</th>
					<td colspan="3"><input id="xsqy" name="xsqy"
						style="height:30px;" disabled="disabled"
						value="${ddcDaxxb.xsqyName }"></td>
					<th>转入区域</th>
					<td colspan="3"><select id="fxsqy" name="newXsqy"
						disabled="disabled" style="height:32px;width: 100px;">

							<c:forEach items="${ssqySjzds }" var="qy">
								<c:if test="${qy.dmz != ddcDaxxb.xsqy }">
									<option value="${qy.dmz }">${qy.dmms1 }</option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>变更资料：</th>
					<td colspan="7"><c:forEach items="${bgDataSjzds }" var="bg">
							<input type="checkbox" id="slzls" name="slzls" value="${bg.dmz}" />${bg.dmms1 }<br />
						</c:forEach></td>

				</tr>
				<tr>
					<th>档案编号：</th>
					<td>${ddcDaxxb.dabh }</td>
					<th>车牌号码：</th>
					<td>${ddcDaxxb.cphm }</td>
					<th>审批日期：</th>
					<td>${ddcDaxxb.gdrq }</td>
					<th>审验日期：</th>
					<td>${ddcDaxxb.syrq }</td>
				</tr>

				<tr>
					<th>已受理资料：</th>
					<td colspan="3"><c:forEach items="${selectSlzls }" var="gd">
						${gd.dmms1 }<br />
						</c:forEach></td>
					<th>审批意见：</th>
					<td colspan="3"><c:if test="${ddcDaxxb.gdyj==null }">
							审批中
						</c:if> <c:if test="${ddcDaxxb.gdyj==0 }">
							办结
						</c:if> <c:if test="${ddcDaxxb.gdyj==1 }">
							退办
						</c:if></td>
				</tr>
				<tr>
					<th>审批备注：</th>
					<td colspan="7">${ddcDaxxb.gdbz }</td>
				</tr>
				<tr>
					<th>申请备注：</th>
					<td colspan="7">${ddcDaxxb.bz }</td>
				</tr>
				
		<!-- 		<tr>
					<td colspan="2"><input type="file" id="vcEbikeInsuranceImg_fileid"
						name="vcEbikeInsuranceImg_file" onchange="CheckFileSize(this);"
						disabled="disabled" /></td>
					<td colspan="2"><input type="file" id="vcQualifiedImg_fileid"
						name="vcQualifiedImg_file" onchange="CheckFileSize(this);"
						disabled="disabled" /></td>
					<td colspan="2"><input id="vcUser1WorkImg_fileid" type="file"
						name="vcUser1WorkImg_file" onchange="CheckFileSize(this);"
						disabled="disabled" /></td>
					<td colspan="2"><input id="vcUser2WorkImg_fileid" type="file"
						name="vcUser2WorkImg_file" onchange="CheckFileSize(this);"
						disabled="disabled" /></td>
				</tr> -->
				<tr>
					<td colspan="2">
						<div class="imgdiv">
							<p>投保凭证照片</p>
							<a href="${ddcDaxxb.vcEbikeInsuranceImgShow }" target="_blank"> <img
								src="${ddcDaxxb.vcEbikeInsuranceImgShow }" />
							</a>
						</div>
					</td>
					<td colspan="2"><a href="${ddcDaxxb.vcQualifiedImgShow }"
						target="_blank">
							<div class="imgdiv">
								<p>车身合格证照片</p>
								<img src="${ddcDaxxb.vcQualifiedImgShow }" />
							</div>
					</a></td>
					<td colspan="2"><a href="${ddcDaxxb.vcUser1WorkImgShow }"
						target="_blank">
							<div class="imgdiv">
								<p>驾驶人1在职证明或居住证</p>
								<img src="${ddcDaxxb.vcUser1WorkImgShow }" />
							</div>
					</a></td>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人2在职证明或居住证照片</p>
							<a href="${ddcDaxxb.vcUser2WorkImgShow }" target="_blank"> <img
								src="${ddcDaxxb.vcUser2WorkImgShow }" />
							</a>
						</div>
					</td>
				</tr>
				
		<!-- 		<tr>
					<td colspan="2"><input type="file" id="headimg_jsr1"
						name="headimg_jsr1" onchange="CheckFileSize(this);"
						disabled="disabled" /></td>
					<td colspan="2"><input type="file" id="headimg_jsr2"
						name="headimg_jsr2" onchange="CheckFileSize(this);"
						disabled="disabled" /></td>
					<td colspan="4"><input id="ebike_img" type="file"
						name="ebike_img" onchange="CheckFileSize(this);"
						disabled="disabled" /></td>
				</tr> -->
				<tr>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人1照片</p>
							<a href="${ddcDaxxb.vcShowUser1Img }" target="_blank"> <img
								src="${ddcDaxxb.vcShowUser1Img }" />
							</a>
						</div>
					</td>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人2照片</p>
							<a href="${ddcDaxxb.vcShowUser2Img }" target="_blank"> <img
								src="${ddcDaxxb.vcShowUser2Img }" />
							</a>
						</div>
					</td>
					<td colspan="2"><a href="${ddcDaxxb.vcShowEbikeImg }"
						target="_blank">
							<div class="imgdiv">
								<p>车身照片</p>
								<img src="${ddcDaxxb.vcShowEbikeImg }" />
							</div>
					</a></td>
					<td colspan="2"><a href="${ddcDaxxb.vcEbikeInvoiceImgShow }"
						target="_blank">
							<div class="imgdiv">
								<p>购车发票</p>
								<img src="${ddcDaxxb.vcEbikeInvoiceImgShow }" />
							</div>
					</a></td>
				</tr>
				<!-- <tr>

					<td colspan="2"><input type="file" id="card1img_jsr1"
						name="card1img_jsr1" onchange="CheckFileSize(this);"
						disabled="disabled" /></td>

					<td colspan="2"><input type="file" id="card2img_jsr1"
						name="card2img_jsr1" onchange="CheckFileSize(this);"
						disabled="disabled" /></td>

					<td colspan="2"><input id="card1img_jsr2" type="file"
						name="card1img_jsr2" onchange="CheckFileSize(this);"
						disabled="disabled" /></td>

					<td colspan="2"><input id="card2img_jsr2" type="file"
						name="card2img_jsr2" onchange="CheckFileSize(this);"
						disabled="disabled" /></td>

				</tr> -->
				<tr>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人1身份证正面</p>
							<a href="${ddcDaxxb.vcUser1CardImg1Show }" target="_blank"> <img
								src="${ddcDaxxb.vcUser1CardImg1Show }" />
							</a>
						</div>
					</td>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人1身份证反面</p>
							<a href="${ddcDaxxb.vcUser1CardImg2Show }" target="_blank"> <img
								src="${ddcDaxxb.vcUser1CardImg2Show }" />
							</a>
						</div>
					</td>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人2身份证正面</p>
							<a href="${ddcDaxxb.vcUser2CardImg1Show }" target="_blank"> <img
								src="${ddcDaxxb.vcUser2CardImg1Show }" />
							</a>
						</div>
					</td>
					<td colspan="2">
						<div class="imgdiv">
							<p>驾驶人2身份证反面</p>
							<a href="${ddcDaxxb.vcUser2CardImg2Show }" target="_blank"> <img
								src="${ddcDaxxb.vcUser2CardImg2Show }" />
							</a>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="8" height="35" style="text-align:center;"><input
						type="button" value=" 变 更 " disabled class="btn" id="bgbtn"
						onclick="changetype(1);">&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="button" value=" 转 移 " disabled class="btn" id="jybtn"
						onclick="changetype(2);">&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="button" value=" 返 回 " class="btn"
						onclick="javascript:window.history.back();"></td>
				</tr>
			</table>

			<input type="hidden" value="${ddcDaxxb.id }" name="id"> <input
				type="hidden" name="slzllist" id="slzllist"> <input
				type="hidden" value="${ddcDaxxb.vcEbikeImg }" name="vcEbikeImg">
			<input type="hidden" value="${ddcDaxxb.vcUser1Img }"
				name="vcUser1Img"> <input type="hidden"
				value="${ddcDaxxb.vcUser2Img }" name="vcUser2Img"> <input
				type="hidden" value="${ddcDaxxb.vcUser1CardImg1 }"
				name="vcUser1CardImg1"> <input type="hidden"
				value="${ddcDaxxb.vcUser1CardImg2 }" name="vcUser1CardImg2">
			<input type="hidden" value="${ddcDaxxb.vcUser2CardImg1 }"
				name="vcUser2CardImg1"> <input type="hidden"
				value="${ddcDaxxb.vcUser2CardImg2 }" name="vcUser2CardImg2">
			<input type="hidden"
				value="${ddcDaxxb.vcEbikeInvoiceImg }" name="vcEbikeInvoiceImg">	
				
			<input type="hidden"
				value="${ddcDaxxb.vcUser1WorkImg }" name="vcUser1WorkImg">
			<input type="hidden"
				value="${ddcDaxxb.vcUser2WorkImg }" name="vcUser2WorkImg">
			<input type="hidden"
				value="${ddcDaxxb.vcQualifiedImg }" name="vcQualifiedImg">
			<input type="hidden"
				value="${ddcDaxxb.vcQualifiedImg }" name="vcQualifiedImg">	
				
		  	<input type="hidden" id="bz" name="note">		
	<!-- 点变更时弹出的表单 -->
	<div id="dgformDiv2" class="easyui-dialog"
		style="width:550px;height:300px;padding:10px 20px 20px 10px;top:500px;"
		closed="true" buttons="#dlg-buttons">

		<div class="tbdiv">
			<input type="hidden" name="id" value="${ddcDaxxb.id }"> <input
				type="hidden" name="state">
			<ul>
				<li><p>备注:</p></li>
				<li><textarea id="note" rows="10" cols="65"></textarea></li>
			</ul>
		</div>

		<div id="dlg-buttons" style="text-align: center;">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="saveBtn"
				iconCls="icon-ok" onclick="change()" style="width:90px">确定</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#dgformDiv2').dialog('close')"
				style="width:90px">取消</a>
		</div>
	</div>
	
	<!-- 点转移时弹出的表单 -->
	<div id="dgformDiv3" class="easyui-dialog"
		style="width:550px;height:300px;padding:10px 20px 20px 10px;top:500px;"
		closed="true" buttons="#dlg-buttons3">

		<div class="tbdiv">
			<input type="hidden" name="id" value="${ddcDaxxb.id }"> <input
				type="hidden" name="state">
			<ul>
				<li><p>备注:</p></li>
				<li><textarea id="note2" rows="10" cols="65" ></textarea></li>
			</ul>
		</div>

		<div id="dlg-buttons3" style="text-align: center;">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="saveBtn"
				iconCls="icon-ok" onclick="zhuanyi()" style="width:90px">确定</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#dgformDiv3').dialog('close')"
				style="width:90px">取消</a>
		</div>
	</div>
		</form>

	</div>

	
</body>
</html>

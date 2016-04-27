<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'changeInfos.jsp' starting page</title>
  
	<%@include file="../common/common.jsp"%>
	<style type="text/css">
	*{
		margin:0;
		padding:0;
		font-size:12px;
	}
	#main{
		border-collapse:collapse;
	}
	#main,#main tr,#main th,#main td{
		border:1px solid #C4E1FF;
	}
	#main tr{
		height:30px;
		background-color:#EEF2FB;
		line-height:30px;
	}
	#main th{
		text-align:right;
		font-weight:bold;
		width:10%;
	}
	#main td{
		width:15%;
		text-align:left;
		padding-left:5px;
	}
	.maindiv{
		background-color: #E4E4FB;
		vertical-align:middle;
	}
	.maindiv input{
		vertical-align:middle;
	}
	
	</style>
	
<script type="text/javascript">
$(document).ready(function(){
	
	if('${ddcDaxxb.vcShowEbikeImg}'==''){
		 $("#img_0").attr("src","<%=basePath%>static/images/iconfont-wu.png");
	}else{
		$("#img_0").attr("src",'${ddcDaxxb.vcShowEbikeImg}');
	}
	if('${ddcDaxxb.vcShowUser2Img}'==''){
		 $("#img2_2").attr("src","<%=basePath%>static/images/iconfont-wu.png");
	}else{
		$("#img2_2").attr("src",'${ddcDaxxb.vcShowUser2Img}');
	}
	if('${ddcDaxxb.vcShowUser1Img}'==''){
		 $("#img1_1").attr("src","<%=basePath%>static/images/iconfont-wu.png");
	}else{
		$("#img1_1").attr("src",'${ddcDaxxb.vcShowUser1Img}');
	} 
	
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
});

function selectJSR(){
	
	if($('#jsr').attr('checked')=='checked'){
		$('#jsrxm1').removeAttr('disabled');
		$('#xb1').removeAttr('disabled');
		$('#sfzmhm1').removeAttr('disabled');
		$('#lxdh1').removeAttr('disabled');
		$('#jsrxm2').removeAttr('disabled');
		$('#xb2').removeAttr('disabled');
		$('#sfzmhm2').removeAttr('disabled');
		$('#lxdh2').removeAttr('disabled');
		$('#file_upload').removeAttr('disabled');
		$('#file_upload1').removeAttr('disabled');
		$('#file_upload2').removeAttr('disabled');
		unselectArea();
	}else{
		$('#jsrxm1').attr('disabled',true);
		$('#xb1').attr('disabled',true);
		$('#sfzmhm1').attr('disabled',true);
		$('#lxdh1').attr('disabled',true);
		$('#jsrxm2').attr('disabled',true);
		$('#xb2').attr('disabled',true);
		$('#sfzmhm2').attr('disabled',true);
		$('#lxdh2').attr('disabled',true);
		$('#file_upload').attr('disabled',true);
		$('#file_upload1').attr('disabled',true);
		$('#file_upload2').attr('disabled',true);
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

//转移
function zhuanyi(){
	var flag = checkVarible();
	if(flag){
		$.messager.progress({
			text:"正在处理，请稍候..."
		});
		$('#dgform').form('submit', {
			url : "<%=basePath%>ebikeChangeAction/zhuanyi",
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
	
}


//变更
function change(){
	var flag = checkVarible();
	if(flag){
		$.messager.progress({
			text:"正在处理，请稍候..."
		});
		$('#dgform').form('submit', {
			url : "<%=basePath%>ebikeChangeAction/changeData",
			onSubmit : function() {
				
				
				 isValid = $("#dgform").form('enableValidation').form(
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
function checkVarible(){
	if(!$("input[name='slzls']:checked").val()){
		alert("请勾选变更资料")
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
</script>
	
  </head>
  
  <body>
   <div class="maindiv">
   		<form id="dgform"  enctype="multipart/form-data"
			method="post">
			<table id="main" class="table table-condensed"  border="1" cellpadding="0" cellspacing="0" width="98%">
				<tr style="display: none">
					<td>id</td>
					<td><input class="easyui-validatebox" type="text" name="id" value="${ddcDaxxb.id }"></input>
					</td>
				</tr>
				<tr >
					<th>变更类型：</th>
					<td colspan="7" style="font-weight:bold;color:blue;">
					<input type="checkbox" id="jsr" name="ywyys" value="E" onclick="selectJSR()"/>驾驶人
		   			<input type="checkbox" id="area" name="ywyys" value="F" onclick="selectArea()"/>行驶区域
					</td>					
				</tr>
				<tr>
					<th>品牌型号</th>
					<td colspan="3"><input id="ppxh" class="easyui-validatebox" type="text" disabled="disabled" value="${ddcDaxxb.ppxh }"
						data-options="required:true" name="ppxh"></input></td>
					<th>电机号：</th>
					<td colspan="3"><input id="djh" class="easyui-validatebox" type="text" value="${ddcDaxxb.djh }"
						 name="djh" style="width: 200px"  disabled="disabled"></input>
					</td>	
					
				</tr>
				<tr>
						
					<th>车身颜色</th>
					<td colspan="3">${ddcDaxxb.cysyName }</td>
					<th>脚踏装置:</th>
					<c:if test="${ddcDaxxb.jtzz ==0 }">
					<td colspan="3">有</td>
					</c:if>
					<c:if test="${ddcDaxxb.jtzz ==1 }">
					<td colspan="3">无</td>
					</c:if>
				</tr>
				<tr>
					<th>驾驶人1姓名</th>
					<td colspan="3"><input id="jsrxm1"  class="easyui-validatebox" type="text" data-options="required:true" value="${ddcDaxxb.jsrxm1 }"    disabled="disabled"
					       name="jsrxm1" style="height: 32px"></td>

					<th>驾驶人2姓名</th>
					<td colspan="3"> <input id="jsrxm2" class="easyui-validatebox" type="text"  value="${ddcDaxxb.jsrxm2 }"  disabled="disabled"
						 name="jsrxm2" style="height: 32px"></td>
				</tr>
				<tr>
					<th>驾驶人1性别</th>
					<td  colspan="3"><select  id="xb1"  name="xb1" disabled="true" 
						style="height:32px;width: 100px;">
						<option value="-1">请选择</option>
							<option value="0" <c:if test="${ddcDaxxb.xb1==0 }">selected</c:if> >男</option>
							<option value="1" <c:if test="${ddcDaxxb.xb1==1 }">selected</c:if>>女</option>
					</select></td>

					<th>驾驶人2性别</th>
					<td  colspan="3"><select id="xb2"  name="xb2"    disabled="true" 
						style="height:32px;width: 100px;">
							<option value="-1">请选择</option>
							<option value="0" <c:if test="${ddcDaxxb.xb2==0 }">selected</c:if> >男</option>
							<option value="1" <c:if test="${ddcDaxxb.xb2==1 }">selected</c:if>>女</option>
					</select></td>
				</tr>
				<tr>
					<th>身份证号码1</th>
					<td  colspan="3"><input class="easyui-validatebox" type="text" id="sfzmhm1" data-options="required:true,validType:'idcard'"  disabled="disabled" value="${ddcDaxxb.sfzmhm1 }"
						 name="sfzmhm1" style="height: 32px">
					</td>
					<th >身份证号码2</th>
					<td colspan="3"><input class="easyui-validatebox" type="text"  id="sfzmhm2"  validType="notequals['#sfzmhm1']"   disabled="disabled" value="${ddcDaxxb.sfzmhm2 }"
					  name="sfzmhm2" style="height: 32px">
					</td>
				</tr>
				<tr>
					<th>联系电话1</th>
					<td  colspan="3"><input class="easyui-validatebox" data-options="required:true,validType:'phoneNum'" type="text" id="lxdh1"  disabled="disabled" value="${ddcDaxxb.lxdh1 }"
						 name="lxdh1" style="height: 32px">
					</td>
					<th >联系电话2</th>
					<td colspan="3"><input class="easyui-validatebox" data-options="required:false,validType:'phoneNum'" type="text"  id="lxdh2"  disabled="disabled" value="${ddcDaxxb.lxdh2 }"
						 name="lxdh2" style="height: 32px">
					</td>
				</tr>
				<tr>
					<th>行驶区域</th>
					<td  colspan="3">
					<input id="xsqy" name="xsqy" style="height:30px;"  disabled="disabled" value="${ddcDaxxb.xsqyName }"></td>
					<th>转入区域</th>
					<td  colspan="3">
						<select id="fxsqy"  name="newXsqy"    disabled="disabled" 
						style="height:32px;width: 100px;">
							
							<c:forEach items="${ssqySjzds }" var="qy">
							<c:if test="${qy.dmz != ddcDaxxb.xsqy }">
							<option value="${qy.dmz }">${qy.dmms1 }</option>
							</c:if>
							</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
	   			<th>变更资料：</th>
	   			<td colspan="7">
	   				<c:forEach items="${bgDataSjzds }" var="bg">
	   				<input type="checkbox" id="slzls"  name="slzls" value="${bg.dmz}" />${bg.dmms1 }<br/>
	   				</c:forEach>
	   			</td>
	   			
	   		</tr>
	   		<tr>
	   			<th>档案编号：</th>
				<td>${ddcDaxxb.dabh }</td>
				<th>车牌号码：</th>
				<td>${ddcDaxxb.cphm }</td>
				<th>车辆状态：</th>
				<td>${ddcDaxxb.ztName }</td>
				<th>审验日期：</th>
				<td>${ddcDaxxb.syrq }</td>
	   		</tr>
	   		<tr>
				<th>协会名称：</th>
				<td>${ddcDaxxb.hyxhzh }</td>
				<th>单位名称：</th>
				<td>${ddcDaxxb.zzjgdmzhName }</td>
				<th>业务类型：</th>
				<td>${ddcDaxxb.ywlxName }</td>
				<th>业务原因：</th>
				<td>${ddcDaxxb.ywyyName }</td>
			</tr>
			<tr>
				<th>受理人：</th>
				<td>${ddcDaxxb.slr }</td>
				<th>受理意见：</th>
				<td>${ddcDaxxb.slyj=='0'?'同意':'不同意' }</td>
				<th>受理部门：</th>
				<td>${ddcDaxxb.slbm }</td>
				<th>受理日期：</th>
				<td>${ddcDaxxb.slrq }</td>
			</tr>
			<tr>
				<th>审批资料：</th>
				<td colspan="3">
					<c:forEach items="${gdDataSjzds }" var="gd">
						${gd.dmms1 }<br/>
					</c:forEach>
				</td>
				<th>审批意见：</th>
				<td colspan="3">${ddcDaxxb.gdyj=='0'?'同意':'不同意' }</td>
			</tr>
			<tr>
				<th>审批备注：</th>
				<td colspan="7">${ddcDaxxb.gdbz }</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td colspan="7">${ddcDaxxb.bz }</td>
			</tr>
			<tr>
				<th>驾驶人1照片</th>
				<td><input  type="file" id="file_upload1" name="file_upload1" onchange="CheckFileSize(this);" disabled="disabled" /></td>
				<th>驾驶人2照片</th>
				<td><input  type="file"  id="file_upload2" name="file_upload2" onchange="CheckFileSize(this);" disabled="disabled" /></td>
				<th>车身照片</th>
				<td  colspan="3"><input  id="file_upload" type="file" name="file_upload" onchange="CheckFileSize(this);"  disabled="disabled"/></td>
			</tr>
				<tr>
					<td colspan="2">
					<div class="imgdiv"> 
					<p>驾驶人1照片</p>
					<img id="img1_1"  src="<%=basePath%>static/images/iconfont-wu.png"/></div>
					</td>
					<td colspan="2">
					<div  class="imgdiv">
					<p>驾驶人2照片</p>
					<img id="img2_2"  src="<%=basePath%>static/images/iconfont-wu.png"/>
					</div><br /></td>
					<td colspan="4">
					<div  class="imgdiv">
					<p>车身照片</p>
					<img id="img_0"  src="<%=basePath%>static/images/iconfont-wu.png"/>
					</div><br /></td>
				</tr>
				<tr>
				<td colspan="8" height="35" style="text-align:center;">
					<input type="button" value=" 变 更 " disabled class="btn" id="bgbtn"  onclick="change();">&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value=" 转 移 " disabled class="btn" id="jybtn" onclick="zhuanyi();">&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value=" 返 回 " class="btn" onclick="javascript:window.history.back();">
				</td>
				</tr>
			</table>
		
			<input type="hidden"  name="slzllist" id="slzllist">
			<input type="hidden" value="${ddcDaxxb.vcEbikeImg }" name="vcEbikeImg">
			<input type="hidden" value="${ddcDaxxb.vcUser1Img }" name="vcUser1Img">
			<input type="hidden"  value="${ddcDaxxb.vcUser2Img }" name="vcUser2Img">
		</form>
	
   </div>
  </body>
</html>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
 String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>模板信息</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
 <script language="javascript">
  String.prototype.trim = function() {
     return this.replace(/(^\s*)|(\s*$)/g, "");
  }

 //(用于onKeypress事件)浮点数字框不能输入其他字符
 function numberFilter() {
		var berr=true;
		if (!(event.keyCode==46 || (event.keyCode>=48 && event.keyCode<=57)))
		{
			alert("该字段只能输入数字！");
			berr=false;
		}
		return berr;
	}

  //(用于onKeypress事件)浮点数字框不能输入其他字符
  function integerInputFilter() {
		var berr=true;
		if (!((event.keyCode>=48 && event.keyCode<=57)))
		{
			alert("该字段只能输入正整数！");
			berr=false;
		}
		return berr;
	}

 //在确定提交前的编码里
 function isInteger(obj,name){
	var value1 = obj.value;
	for(i=0;i<obj.value.length;i++){
		var ch=obj.value.charAt(i);
		if((ch<'0' || ch>'9')){
			alert("<"+name+">只能是数字！");
			obj.focus();
		   return false;
		}
		if(value1<=0){
			alert("<"+name+">应该是一个大于0的数字！");
			obj.focus();
		   return false;
		}
	}
	return true;	    
}

 function submitRequest(form){
    var name = form.name.value.trim();
	var templateId = form.templateId.value.trim();

    if(templateId == ""){
		alert("模板编号不能为空！");
		form.templateId.focus();
		return ;
	 }

    if(name == ""){
		alert("模板名称不能为空！");
		form.code.focus();
		return ;
	 }
    
	form.submit();
	window.parent.reload();
	window.close();

 }

</script>
</head>
<body>
<center>
<form name="iForm" method="post" ENCTYPE="multipart/form-data"
	class="x-form"
	action="<%=request.getContextPath()%>/mx/sys/template/save"
	onsubmit="return checkForm();"><c:if test="${not empty nodeId}">
	<input type="hidden" name="nodeId" value="${nodeId}">
</c:if>

<div class="content-block" style="width: 80%;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="编辑模板信息">&nbsp;编辑模板信息
 
</div>
<br>
 
<table border=0 cellspacing=0 cellpadding=2>
	<tbody>
		<tr class="x-content-hight">
			<td align="left" width="18%" noWrap height="28"><span>编号</span></td>
			<td align="left" height="28"><c:choose>
				<c:when test="${not empty template.templateId }">
					${template.templateId}
					<input type="hidden" name="templateId"
						value="${template.templateId}">
				</c:when>
				<c:otherwise>
					<input name="templateId" size="50" type="text" class="input-xlarge x-text" maxlength="255"
						value="${template.templateId}">
				</c:otherwise>
			</c:choose></td>
		</tr>

		<tr class="x-content-hight">
			<td align="left" width="18%" noWrap height="28"><span>名称</span></td>
			<td align="left" height="28">
			<input name="name" type="text" size="50" class="input-xlarge x-text" maxlength="255"
				value="${template.name}">
			</td>
		</tr>

		<tr class="x-content-hight">
			<td align="left" width="18%" noWrap height="28"><span>主题</span></td>
			<td align="left" height="28">
			<input name="title" type="text" size="50" class="input-xlarge x-text" maxlength="255"
				value="${template.title}">
			</td>
		</tr>



		<tr class="x-content-hight">
			<td align="left" width="18%" align="left" height="28"><span>模板文件</span></td>
			<td align="left" width="82%" height="28">
			<input type="file" name="file1" size="50" class="input-file x-text">
			 <br/>（如果要更新模板文件，请重新上传）
			</td>
		</tr>

		<tr class="x-content-hight">
			<td align="left" width="18%" height="148">描述</td>
			<td align="left" rowspan="1" colspan="4" width="81%" height="143">
			<textarea
				id="description" name="description" displayName="描述"
				style="height: 142.0px; width: 330px; text-align: left; vertical-align: middle;"
				class="input-xlarge x-textarea" />${template.description}</textarea>
			</td>
		</tr>

		<tr>
			<td align="left" width="18%" height="32">启用/锁定</td>
			<td align="left" width="82%" height="32">
			    <input type="hidden" id="locked"
				name="locked" value="${template.locked}">
			启用 <input type="radio" id="lockedx" name="lockedx" value="0"
				<c:if test="${template.locked == 0}">checked</c:if>
				onclick='javascript:document.getElementById("locked").value="0";'>
			锁定 <input type="radio" id="lockedx" name="lockedx" value="1"
				<c:if test="${template.locked == 1}">checked</c:if>
				onclick='javascript:document.getElementById("locked").value="1";'>
			</td>
		</tr>

	</tbody>
</table>
 

<div align="center"><br />

<input type="button" class="btn btn-primary" value="确定"
	onclick="javascript:submitRequest(this.form);" /> <input type="button"
	value="返回" name="back" class="btn"
	onclick="javascript:history.back();" /> <br />
<br />
</div>

</div>
</form>
</center>
<br />
<br />

<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
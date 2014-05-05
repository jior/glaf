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
    var name = form.code.value.trim();
	var title = form.name.value.trim();

    if(name == ""){
		alert("名称不能为空！");
		form.code.focus();
		return ;
	 }
	if(title == ""){
		alert("标题不能为空！");
		form.name.focus();
		return ;
	 }
    
	form.submit();

 }

</script>
<body>
<center><br>
<br>




<div class="content-block" style="width: 68%;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="查看模板信息">&nbsp;查看模板信息
 </div>

 <br>
<table border=0 cellspacing=0 cellpadding=2>
	<tbody>
		<tr class="x-content-hight">
			<td align="left" style="width: 140px; padding-right: 10px"><span>编码</span></td>
			<td align="left"><c:choose>
				<c:when test="${not empty template.templateId }">
					${template.templateId}
					<input type="hidden" name="code"
						value="${template.templateId}">
				</c:when>
				<c:otherwise>
					<input name="code" size="50" class="input-xlarge" maxlength="255"
						value="${template.templateId}">
				</c:otherwise>
			</c:choose></td>
		</tr>
		<tr class="x-content-hight">
			<td align="left" style="width: 140px; padding-right: 10px"><span>名称</span></td>
			<td align="left">${template.name}</td>
		</tr>

		<tr class="x-content-hight">
			<td align="left" style="width: 140px; padding-right: 10px"><span>主题</span></td>
			<td align="left">${template.title}</td>
		</tr>

		<tr class="x-content-hight">
			<td align="left" style="width: 140px; padding-right: 10px"><span>描述</span></td>
			<td align="left">${template.description}</td>
		</tr>

		<tr class="x-content-hight">
			<td align="left" style="width: 140px; padding-right: 10px"><span>文件</span>
			</td>
			<td align="left" ><iframe id="newFrame" name="newFrame" width="0" height="0"></iframe>
			<a
				href="<%=request.getContextPath()%>/mx/lob/lob/download?fileId=${template.templateId}"
				target="newFrame">${template.dataFile}&nbsp;<img
				src="<%=request.getContextPath()%>/images/download.gif"
				border="0"></a></td>
		</tr>

	</tbody>
</table>
 
<div align="center"><br />
<input type="button" value="返回" name="back" class="btn"
	onclick="javascript:history.back(0);" /> <br />
<br />
</div>

</div>

</center>
<br />
<br />

<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
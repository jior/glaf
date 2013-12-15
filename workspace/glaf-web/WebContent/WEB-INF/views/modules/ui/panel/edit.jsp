<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.glaf.core.security.*"%>
<%
    String context = request.getContextPath();
	String isSystem = request.getParameter("isSystem"); 
	LoginContext loginContext = RequestUtils.getLoginContext(request);
%>
<!DOCTYPE html>
<html>
<head>
<title>桌面板块</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<script language="javascript" src="<%=request.getContextPath()%>/scripts/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript">


  KE.show({  id : 'content',
	         allowFileManager : true,
	         imageUploadJson : '<%=request.getContextPath()%>/mx/uploadJson',
			 fileManagerJson : '<%=request.getContextPath()%>/mx/fileManagerJson'
        });

 	function changeDiv(state){
		if(state=='L'){
			$("#urlDir").show();
			$("#divDir").hide();
			document.getElementById("type").value = state;
		}
		if(state=='T'){
			$("#divDir").show();
			$("#urlDir").hide();
			document.getElementById("type").value = state;
		}
	}

    function submitXY(){
	  var subject = document.getElementById("title").value;
	  if(subject == ""){
		  alert("标题是必须的！");
          document.getElementById("title").focus();
		  return;
	  }
	  var type = document.getElementById("type").value;
	  if("L" == type){
		  var link = document.getElementById("link").value;
	      if(link == ""){
		    alert("链接地址是必须的！");
            document.getElementById("link").focus();
		    return;
	      }
	  }

	  if("T" == type){
		  document.getElementById("content").value=KE.html('content');
		  var content = document.getElementById("content").value;
	      if(content == ""){
		    alert("文本内容是必须的！");
            document.getElementById("content").focus();
		    return;
	      }
	  }
	  document.getElementById("content").value=KE.html('content');
	  document.getElementById("iForm").submit();
	}
</script>
</head>
<body>
<center> 
<form id="iForm" name="iForm" class="x-form" method="post"
	action="<%=request.getContextPath()%>/mx/panel/save"><input
	type="hidden" name="panelId" value="${panel.id}">
<c:if test="${ panel.id > 0 }">
	<input type="hidden" name="id" value="${panel.id}">
</c:if> 
<c:if test="${ isSystem eq 'true'}">
	<input type="hidden" id="isSystem" name="isSystem"
		value="${isSystem}">
</c:if>
<div class="content-block" style="width: 745px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="首页板块设置">&nbsp;首页板块设置</div>
<br>
<table width="95%" align="center" border="0" cellspacing="0"
	cellpadding="5">

	<tr height="27">
		<td align="left" width="30%" valign="top">版块名称 *</td>
		<td align="left" width="70%"><input id="title" name="title" type="text" size="50"
			maxlength="50" class="input-xlarge x-text"
			value="${panel.title}" datatype="string"></td>
	</tr>

	<%if (loginContext.isSystemAdministrator() && StringUtils.equals(isSystem, "true")) {%>
	<tr height="27">
		<td align="left" width="30%" valign="top">版块编码 *</td>
		<td align="left" width="70%"><input name="name" type="text" size="50"
			maxlength="20" class="input-xlarge x-text" value="${panel.name}"
			datatype="string"></td>
	</tr>

	<tr height="27">
		<td align="left" width="30%" valign="top">模块名称</td>
		<td align="left" width="70%"><input name="moduleName" type="text" size="50"
			maxlength="50" class="input-xlarge x-text"
			value="${panel.moduleName}" datatype="string"></td>
	</tr>
	<tr height="27">
		<td align="left" width="30%" valign="top">模块编码</td>
		<td align="left" width="70%"><input name="moduleId" type="text" size="50"
			maxlength="20" class="input-xlarge x-text"
			value="${panel.moduleId}" datatype="string"></td>
	</tr>

	<%}%>

	<tr height="27">
		<td align="left" width="30%" valign="top">模块列位置</td>
		<td align="left" width="70%">
		    <select id="columnIndex" name="columnIndex" class="span2">
				<option value="0" <c:if test="${panel.columnIndex == '0'}">selected</c:if>>第一列</option>
				<option value="1" <c:if test="${panel.columnIndex == '1'}">selected</c:if>>第二列</option>
				<option value="2" <c:if test="${panel.columnIndex == '2'}">selected</c:if>>第三列</option>
		    </select>
		</td>
	</tr>

	<tr>
		<td width="30%" align="left" valign="top">模块内容类型</td>
		<td width="70%" align="left" valign="top" >  
			<input id="type" name="type" type="radio" value="L" onclick="changeDiv('L');" 
			<c:if test="${panel.type == 'L'}">checked</c:if> />链接地址
			<input id="type" name="type" type="radio" value="T" onclick="changeDiv('T');" 
			<c:if test="${panel.type == 'T'}">checked</c:if> />文本内容
			<br>
			<div id="urlDir" style="display:block;">
		      <textarea id="link" name="link" rows="5" cols="39"
			    class="input-xlarge x-textarea" style="width: 350px; height: 90px; text-align: left;"><c:out
			    value="${panel.link}" escapeXml="false" /></textarea>
			</div>
			<div id="divDir" style="display:none;">
		       <textarea id="content" name="content" rows="8" cols="60"  
			style="width: 550px; height: 350px; text-align: left;"><c:out
			value="${panel.content}" escapeXml="false" /></textarea>
			</div>
		</td>
	</tr>

	<tr>
		<td align="left" width="30%"   valign="top">高度</td>
		<td align="left" align="left" valign="middle" >
			<input type="text" name="height" value="${panel.height}" size="3" maxLength="3" class="input-mini x-text" /> (例如: 300, 显示一个高度为300像素的模块)
		</td>
	</tr>

	<!-- <tr>
		<td align="left" width="30%"   valign="top">样式</td>
		<td align="left" align="left" valign="middle" >
			<input type="text" name="style" class="input-xlarge x-text" />
			<br> (例如 height:320px; 显示一个高度为320像素的模块)
		</td>
	</tr> -->

	<tr>
		<td align="left" width="30%" valign="top">是否可收缩</td>
		<td align="left" align="left" valign="middle" >
			<input type="radio" name="collapsible" value="true"
						<c:if test="${panel.collapsible == 1}">checked</c:if>>是&nbsp;&nbsp;
			<input type="radio" name="collapsible" value="false"
						<c:if test="${panel.collapsible == 0}">checked</c:if>>否
		</td>
	</tr>

	<tr>
		<td align="left" width="30%" valign="top">是否可关闭</td>
		<td align="left" align="left" valign="middle" >
			<input type="radio" name="close" value="true"
						<c:if test="${panel.close == 1}">checked</c:if>>是&nbsp;&nbsp;
			<input type="radio" name="close" value="false"
						<c:if test="${panel.close == 0}">checked</c:if>>否
		</td>
	</tr>

	<tr height="27">
		<td align="left" width="30%" valign="top">是否启用</td>
		<td align="left"><input type="radio" name="locked" value="0"
			<c:if test="${panel.locked == 0}">checked</c:if>>是&nbsp;&nbsp;
		<input type="radio" name="locked" value="1"
			<c:if test="${panel.locked == 1}">checked</c:if>>否</td>
	</tr>

</table>
 
<div style="width: 645px;" align="center"><br />
<input name="btn_save2" type="button" value="保存" class="btn btn-primary" onclick="javascript:submitXY();">
<input name="btn_back" type="button" value="返回" class="btn"
	   onclick="javascript:history.back();"> <br />
<br />
</div>
</div>

</form>
</center>
<script type="text/javascript">
   <c:if test="${!empty panel.type}">
    changeDiv('${panel.type}');
   </c:if>
</script>
</body>
</html>

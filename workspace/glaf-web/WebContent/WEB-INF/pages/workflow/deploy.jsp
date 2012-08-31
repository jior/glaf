<%@ page contentType="text/html;charset=UTF-8" %>
<%
String context = request.getContextPath();
%>
<html>
<head>
<title>上传附件</title>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<script language="JavaScript">
    function checkForm(){
       var file = document.getElementById("file").value;
	   if(file == ""){
		   alert("请选择业务流程包！");
		   return false;
	   }
	   return true;
    }
</script>
</head>
<body onLoad="DynarchMenu.setup('menu1', { context: true});" id="document">
<jsp:include page="/WEB-INF/views/module/header.jsp" flush="true"/>
<br><br><br>
<form action="<%=request.getContextPath()%>/workflow/deployController.jspa?method=deploy" 
      method="post"  ENCTYPE="multipart/form-data" name="attachForm" onsubmit="return checkForm();">
<div align="center" style="height: 535px">
 <font size="2">流程包(必须是zip格式)</font>&nbsp;&nbsp;
 <input type="file" id="file" name="file" size="50" class="txt">
 <input type="submit" value="确定" class="button">
</div>
</form>
</body>
</html>
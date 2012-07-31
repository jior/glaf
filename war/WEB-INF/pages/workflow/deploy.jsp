<%@ page contentType="text/html;charset=UTF-8" %>
 
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<html>
<head>
<title>上传附件</title>
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
<body background="<%=request.getContextPath()%>/workflow/images/bk3.gif" leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<br><br><br>
<form action="<%=request.getContextPath()%>/workflow/deployController.jspa?method=deploy" 
      method="post"  ENCTYPE="multipart/form-data" name="attachForm" onsubmit="return checkForm();">
<div align="center" style="height: 535px">
 <font size="2">业务流程包</font>&nbsp;&nbsp;
 <input type="file" id="file" name="file" size="50" class="txt">
 <input type="submit" value="确定" class="button">
</div>
</form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程发布</title>
<%@ include file="/WEB-INF/views/tm/header.jsp"%>
<script language="JavaScript">

	function submitRequest(form){
		 if(document.getElementById("file").value==""){
             alert("请选择您要发布的格式为zip、jar、par的流程包！");
			 return;
		 }
          if(confirm("您准备发布新的流程版本，确认吗？")){
             document.iForm.submit();
		  }
	}

</script>
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<br>
<br>
<br>
<center>
<form action="<%=request.getContextPath()%>/mx/activiti/deploy" method="post"
	enctype="multipart/form-data" name="iForm" class="x-form">
<div class="content-block"  style=" width: 80%;" >
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="流程发布">&nbsp;流程发布
</div>
 
<div align="center">
<br>
<label for="file">请选择要发布的流程包，必须是zip格式</label>&nbsp;&nbsp;
<input type="file" id="file" name="file" size="50" class="input-file"> <br>
</div>
<br>

<div align="center"><br />
<input type="button" name="bt01" value="确定" class="btn btn-primary"
	onclick="javascript:submitRequest(this.form);" /> 
<br />
<br />
</div>

</div>
</form>
</center>
</body>
</html>
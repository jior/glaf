<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<html>
<head>
<title>流程发布</title>
<script language="javascript">

	function submitRequest(form){
		 if(document.getElementById("file").value==""){
             alert("请选择您要发布的格式为xml的流程配置包！");
			 return;
		 }
          if(confirm("您准备发布新的流程配置，确认吗？")){
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
<form
	action="<%=request.getContextPath()%>/mx/jbpm/deploy/reconfig"
	method="post" enctype="multipart/form-data" name="iForm" class="x-form">
<div class="content-block" style="width: 90%;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="流程配置">&nbsp;流程配置
</div>


<fieldset class="x-fieldset" style="width: 98%;"><legend>流程配置</legend>
<div align="center"><br>
<label for="file">请选择流程配置定义文件</label>&nbsp;&nbsp; <input type="file"
	id="file" name="file" size="50" class="input-xlarge"> <br>
</div>
<br>
</fieldset>

<div align="center"><br />
<input type="button" name="bt01" value="确定" class="btn"
	onclick="javascript:submitRequest(this.form);" /> <br />
<br />
</div>

</div>
</form>
</center>
</body>
</html>
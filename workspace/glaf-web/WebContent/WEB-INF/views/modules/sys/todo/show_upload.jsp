<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<html>
<head>
<title>文件上传</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<script language=javascript>
var sAllowExt = "XLS";
// 是否有效的扩展名
function IsExt(url, opt){
	var sTemp;
	var b=false;
	var s=opt.toUpperCase().split("|");
	for (var i=0;i<s.length ;i++ ){
		sTemp=url.substr(url.length-s[i].length-1);
		sTemp=sTemp.toUpperCase();
		s[i]="."+s[i];
		if (s[i]==sTemp){
			b=true;
			break;
		}
	}
	return b;
}
// 检测上传表单
function submitMyForm() {
	if(document.getElementById("file").value=""){
		alert("请选择要导入的Todo模板文件，格式为Excel，扩展名是.xls");
		return;
	}
	if(IsExt(document.getElementById("file").value, "xls")){
	    document.TodoForm.submit();
	} else {
		alert("请选择要导入的Excel模板文件，扩展名是.xls");
	}
}
</script>
</head>
<body>
<br><br>
<form method="post" id="TodoForm" name="TodoForm" action="${contextPath}/sys/todo.do?method=uploadFile" enctype="multipart/form-data">
<table align="center" class="table-border" cellspacing="0"
	cellpadding="0" width="90%">
	<tr>
		<td align="center">
           <input type="file" id="file" name="file" size="1" style="width:50%" >
		   <input type="button" name="btn" value=" 确 定 " class="button" onclick="javascript:submitMyForm();">
        </td>
	</tr>
</table>
</form>

</body>
</html>

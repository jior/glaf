<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.jpage.jbpm.util.*" %>

<html>
<head>
<title>通用流程</title>
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<script language="javascript" src="<%=request.getContextPath()%>/workflow/includes/js/fixdate.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/workflow/includes/js/selectdate.js"></script>
<script language="JavaScript">

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
function integerFilter() {
	var berr=true;
	if (!((event.keyCode>=48 && event.keyCode<=57)))
	{
		alert("该字段只能输入正整数！");
		berr=false;
	}
	return berr;
}

//(用于onKeypress事件)浮点数字框不能输入其他字符
function checkDataInput(fieldId) {
	var berr=true;
	if (!((event.keyCode>=48 && event.keyCode<=57)))
	{
		alert("该字段只能输入正整数！");
		berr=false;
	}
    try{
		var aa = "<%=Constant.PROCESS_DATAFIELD_PREFIX%>"+fieldId;
		var bb = "persistence_"+fieldId;
		var value = document.getElementById(bb).value;
		document.getElementById(aa).value = value;
	}catch(exc){
	}
	return berr;
}

</script>
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<center>
<form name="iForm" method="post" 
      action="<%=request.getContextPath()%>/workflow/mainController">
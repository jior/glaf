<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<title>${title?if_exists}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link type="text/css"
	href="<%=request.getContextPath()%>/scripts/calendar/skins/aqua/theme.css"
	rel="stylesheet" />
<script src="<%=request.getContextPath()%>/scripts/calendar/calendar.js"
	language="javascript"></script>
<script
	src="<%=request.getContextPath()%>/scripts/calendar/lang/calendar-zh.js"
	language="javascript"></script>
<script
	src="<%=request.getContextPath()%>/scripts/calendar/calendar-setup.js"
	language="javascript"></script>
<script src="<%=request.getContextPath()%>/scripts/jquery/jquery.min.js"
	language="javascript"></script>
 
<script language="javascript">
    var contextPath = "<%=request.getContextPath()%>";

    function ajaxRequestXY(elementId, elementValue, queryId, objectId, paramValue){
          var req = AjaxRequest();
          handleEmpty(elementId);
		  var handlerFunction = getReadyStateHandler(req, chooseXY, elementId, elementValue);
          req.onreadystatechange = handlerFunction;
          req.open("POST", "<%=request.getContextPath()%>/QueryService", true);
          req.setRequestHeader("Content-Type", 
                       "application/x-www-form-urlencoded");
		  if(paramValue == ""){
		     
		  }
          req.send("queryId="+queryId+"&"+objectId+"="+paramValue);
    }

 
  function closeXY(){
     if(window.opener != null){
		 window.close();
	 } else {
        history.back();
	 }
  }

</script>
 
</head>
<body id="x-form-body" leftmargin="0" topmargin="0" marginheight="0"
	marginwidth="0">
 <center>
 <br>
 <input type="hidden" id="x_json" name="x_json"
	value="<c:out value="#F{x_json}" escapeXml="true"/>">
<br>
<div class="content-block"  style=" width: 90%;" >
<br>
<div class="x_content_title">
<img
	src="<%=request.getContextPath()%>/pages/images/window.png"
	alt="${title?if_exists}">&nbsp;  ${title?if_exists} 
</div>

<fieldset class="x-fieldset" style=" width: 95%;">
<legend>${res_basic_info?if_exists}</legend>
 
 <table width="95%" border="0" cellspacing="1" cellpadding="1" align="center" >
   <tr>
    <td style="padding-top:20px; padding-left:20px; ">
		 ${x_form_content?if_exists}
    </td>
   </tr>
 </table>

</fieldset>


<div id="x_form_files">
<fieldset class="x-fieldset" style=" width: 95%;"><legend> <img
	src="<%=request.getContextPath()%>/pages/images/window.png"
	alt="${res_files?if_exists}">&nbsp; <font
	style="font-size: 15px;">${res_files?if_exists}</font> </legend>
<div id="x_files"></div>
<script language="javascript">
	$('#x_files').load('<%=request.getContextPath()%>/dataFile.do?method=dataFiles&serviceKey=${modelName}&resourceId=<c:out value="#F{requestQuery.resourceId}"/>');
</script>
<br />
</fieldset>
</div>

<div id="x-toolbar" align="center"> 
<br />

 <input type="button" name="button" value="${res_button_back?if_exists}"
	class="button" onclick="javacsript:closeXY();">  
<br />
</div>

</div>

</form>

</center>
<br />
<br />
<%@ include file="/pages/tm/footer.jsp"%>
</body>
</html>
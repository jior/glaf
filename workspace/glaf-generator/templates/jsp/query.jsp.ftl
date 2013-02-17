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

  </script>
</head>
<body id="x-body" leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<br />
<center>
<form id="${modelName?if_exists}" name="${modelName?if_exists}"
	class="x-form" method="post"
	action="<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=list">
<input type="hidden" id="x_query" name="x_query" value="true">
<input type="hidden" id="actionType" name="actionType" value="list">
<input type="hidden" id="gridType" name="gridType" value="<c:out value="#F{gridType}"/>">

<div class="content-block"  style=" width: 90%;" >
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/pages/images/window.png"
	alt="${res_query?if_exists}">&nbsp;${res_query?if_exists}</h3>
</div>
<fieldset class="x-fieldset" style=" width: 95%;"><legend>${res_form_props?if_exists}</legend>
<br />
 <table width="95%" border="0" cellspacing="1" cellpadding="1" align="center" >
   <tr>
    <td style="padding-top:20px; padding-left:20px; ">
		 ${x_query_content?if_exists}
    </td>
   </tr>
 </table>
 </fieldset>
 
<div id="x-toolbar" align="center"><br />
<br />
<input type="submit" name="submit" value="${res_search?if_exists}" 	class="button">
<script language="javascript"> 
	document.getElementById("actionType").value="list";
</script>
<input type="button" name="button"
	value="${res_button_back?if_exists}" class="button"
	onclick="javascript:history.back();"> <br />
<br />
<br />
</div>
 
</div>

</form>
</center>
<%@ include file="/pages/tm/footer.jsp"%>
</body>
</html>
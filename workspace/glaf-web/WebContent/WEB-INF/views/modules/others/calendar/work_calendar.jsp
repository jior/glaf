<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %> 
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
Calendar cal=Calendar.getInstance(); 
int year =ParamUtil.getIntAttribute(request, "year", cal.get(Calendar.YEAR)); 
%>
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%=context%>/css/calendar.css">
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/interface/WorkCalendarAjaxService.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/engine.js'></script>

<script Language="JavaScript"> 
var year = <%=year%>;

function goYear(i){  
  year=year+i;
  window.location="workCalendar.do?method=showList&year="+year;
}
function selDay(obj, month, day){
  if(obj.checked){
    //alert("选择");
	WorkCalendarAjaxService.createData(year, month, day);
  }else{
    //alert("删除");
	WorkCalendarAjaxService.deleteData(year, month, day);
  }
}
</script> 
</head>

<body onLoad="DynarchMenu.setup('menu1', { context: true});" id="document">
<jsp:include page="/WEB-INF/views/modules/header.jsp" flush="true"/>

<table width="90%" border="0" cellpadding="2" cellspacing="2" align="center">
  <tr>
    <td colspan="3" align="center"><span onClick="goYear(-1)" title="上一年" class="changeDate">&lt;&lt;</span>&nbsp;&nbsp;&nbsp;<b><%=year%> 工作日历</b>&nbsp;&nbsp;&nbsp;<span onClick="goYear(1)" title="下一年" class="changeDate">&gt;&gt;</span></td>
  </tr>
  <tr>
    <td valign="top">
	<jsp:include page="/others/workCalendar.do?method=showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="0"/>	
	</jsp:include>	
	</td>
    <td valign="top">
	<jsp:include page="/others/workCalendar.do?method=showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="1"/>	
	</jsp:include>	
	</td>
    <td valign="top">
	<jsp:include page="/others/workCalendar.do?method=showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="2"/>	
	</jsp:include>	
	</td>
  </tr>
  <tr>
    <td valign="top">
	<jsp:include page="/others/workCalendar.do?method=showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="3"/>	
	 </jsp:include>	
	</td>
    <td valign="top">
	<jsp:include page="/others/workCalendar.do?method=showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="4"/>	
	 </jsp:include>	
	</td>
    <td valign="top">
	<jsp:include page="/others/workCalendar.do?method=showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="5"/>	
	 </jsp:include>	
	</td>
  </tr>
  <tr>
    <td valign="top">
	<jsp:include page="/others/workCalendar.do?method=showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="6"/>	
	 </jsp:include>	
	</td>
	<td valign="top">
	<jsp:include page="/others/workCalendar.do?method=showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="7"/>	
	 </jsp:include>	
	</td>
	<td valign="top">
	<jsp:include page="/others/workCalendar.do?method=showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="8"/>	
	</jsp:include>	
    </td>
  </tr>
  <tr>
    <td valign="top">
	<jsp:include page="/others/workCalendar.do?method=showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="9"/>	
	</jsp:include>	
	</td>
    <td valign="top">
	<jsp:include page="/others/workCalendar.do?method=showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="10"/>	
	 </jsp:include>	
	</td>
    <td valign="top">
	<jsp:include page="/others/workCalendar.do?method=showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="11"/>	
	 </jsp:include>	
	</td>
  </tr>
</table>
<br><br>
</body>
</html>

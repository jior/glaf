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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/calendar.css">
<script language="javascript" src='<%=request.getContextPath()%>/scripts/verify.js'></script>
<script language="javascript" src='<%=request.getContextPath()%>/scripts/main.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 

<script Language="javascript"> 
var year = <%=year%>;

function goYear(i){  
  year=year+i;
  window.location="<%=request.getContextPath()%>/mx/sys/workCalendar/showList?year="+year;
}
function selDay(obj, month, day){
  if(obj.checked){
    //alert("选择");
	//WorkCalendarAjaxService.createData(year, month, day);
	 
	jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/mx/sys/workCalendar/createData?year='+year+'&month='+month+'&day='+day,
			dataType:  'json',
				error: function(data){
					alert('服务器处理错误！');
				},
				success: function(data){
					   
				 }
		});
	 
  } else {
    //alert("删除");
	//WorkCalendarAjaxService.deleteData(year, month, day);
	jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/mx/sys/workCalendar/deleteData?year='+year+'&month='+month+'&day='+day,
			dataType:  'json',
				error: function(data){
					alert('服务器处理错误！');
				},
				success: function(data){
					   
				 }
		});
  }
}
</script> 
</head>

<body  id="document">
 
<table width="90%" border="0" cellpadding="2" cellspacing="2" align="center">
  <tr>
    <td colspan="3" align="center"><span onClick="goYear(-1)" title="上一年" class="changeDate">&lt;&lt;</span>&nbsp;&nbsp;&nbsp;<b><%=year%> 工作日历</b>&nbsp;&nbsp;&nbsp;<span onClick="goYear(1)" title="下一年" class="changeDate">&gt;&gt;</span></td>
  </tr>
  <tr>
    <td valign="top">
	<jsp:include page="/mx/sys/workCalendar/showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="0"/>	
	</jsp:include>	
	</td>
    <td valign="top">
	<jsp:include page="/mx/sys/workCalendar/showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="1"/>	
	</jsp:include>	
	</td>
    <td valign="top">
	<jsp:include page="/mx/sys/workCalendar/showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="2"/>	
	</jsp:include>	
	</td>
  </tr>
  <tr>
    <td valign="top">
	<jsp:include page="/mx/sys/workCalendar/showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="3"/>	
	 </jsp:include>	
	</td>
    <td valign="top">
	<jsp:include page="/mx/sys/workCalendar/showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="4"/>	
	 </jsp:include>	
	</td>
    <td valign="top">
	<jsp:include page="/mx/sys/workCalendar/showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="5"/>	
	 </jsp:include>	
	</td>
  </tr>
  <tr>
    <td valign="top">
	<jsp:include page="/mx/sys/workCalendar/showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="6"/>	
	 </jsp:include>	
	</td>
	<td valign="top">
	<jsp:include page="/mx/sys/workCalendar/showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="7"/>	
	 </jsp:include>	
	</td>
	<td valign="top">
	<jsp:include page="/mx/sys/workCalendar/showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="8"/>	
	</jsp:include>	
    </td>
  </tr>
  <tr>
    <td valign="top">
	<jsp:include page="/mx/sys/workCalendar/showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="9"/>	
	</jsp:include>	
	</td>
    <td valign="top">
	<jsp:include page="/mx/sys/workCalendar/showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="10"/>	
	 </jsp:include>	
	</td>
    <td valign="top">
	<jsp:include page="/mx/sys/workCalendar/showCalendar" flush="true">
	  <jsp:param name="year" value="<%=year%>"/>
	  <jsp:param name="month" value="11"/>	
	 </jsp:include>	
	</td>
  </tr>
</table>
<br><br>
</body>
</html>

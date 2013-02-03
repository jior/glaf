<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %> 
<%@ page import="com.glaf.base.utils.*"%>
<% 
Calendar cal=Calendar.getInstance();
int year = ParamUtil.getIntAttribute(request, "year", cal.get(Calendar.YEAR));//年份
int month = ParamUtil.getIntAttribute(request, "month", cal.get(Calendar.MONTH));//月份
int weeks = ParamUtil.getIntAttribute(request, "weeks", 0);//当月的周数
List list = (List) request.getAttribute("list");
String days[] = (String[])request.getAttribute("days");
%> 
</head> 
<body>
<table width="280" height="80" class="mainTable" border=0 cellspacing=1 cellpadding=2>
  <tr>
    <td colspan=7 class="list-title"><table width=100% cellspacing=0 cellpadding=0>
      <tr>
        <td align="center"><font color="#0066ff"><b><%=month+1%>月</b></font> </td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td align="center" class="weekDay">日</td>
    <td align="center" class="weekDay">一
        </th>
    <td align="center" class="weekDay">二
        </th>
    <td align="center" class="weekDay">三
        </th>
    <td align="center" class="weekDay">四
        </th>
    <td align="center" class="weekDay">五
        </th>
    <td align="center" class="weekDay">六</td>
  </tr>
  <%
  for(int j=0; j<=5; j++) { 
  %>
  <tr>
    <%
	for(int i=j*7; i<(j+1)*7; i++) { 
	%>
    <td class="<%=(i%7==0 || i%7==6)?"weekend":"eachDay"%>" align="left" height="30" width="40">
	  <%
	  if(!"".equals(days[i])){
	  %>
      <input type="checkbox" name="id" value="" onClick="selDay(this, <%=month+1%>, <%=days[i]%>);" <%=list.contains(new Integer(days[i]))?"checked":""%>><%=days[i]%>
      <%
	  }else{
	    out.print("&nbsp;");
	  }	
	  %>	  	
    </td>
    <%}%>
  </tr>
  <%}%>
</table>
</body> 
</html>  


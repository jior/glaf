<%@page contentType="text/html;charset=gbk"%>
<%@page import="java.net.URLDecoder"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int total = ParamUtil.getIntParameter(request, "total", 0);
int pageCount = ParamUtil.getIntParameter(request, "page_count", 0);
int pageSize = ParamUtil.getIntParameter(request, "page_size", 10);
int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
String url = ParamUtil.getParameter(request, "url");
String params = URLDecoder.decode(ParamUtil.getParameter(request, "params"));
int go = pageNo;
%>
<table width="319"  border="0" align="right" cellpadding="0" cellspacing="0">
  <tr> 
    <td class="pageborder"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="center">��ǰ��<%=pageNo%>ҳ/��<%=pageCount%>ҳ,<%=total%>����¼</td>
          <td align="center">
              <a href="<%=url%>?<%=WebUtil.getQueryString(params + "&page_no=1")%>">��ҳ</a></td>
          <td align="center">
<%
if(pageNo>1) go=pageNo-1;
%>	
	<a href="<%=url%>?<%=WebUtil.getQueryString(params + "&page_no=" + go)%>">��һҳ</a>
		  </td>
          <td align="center">
<%
go = pageNo;
if(pageNo+1<=pageCount) go=pageNo+1;
%>	
	<a href="<%=url%>?<%=WebUtil.getQueryString(params + "&page_no=" + go)%>">��һҳ</a>         
		 </td>
          <td align="center">
              <a href="<%=url%>?<%=WebUtil.getQueryString(params + "&page_no=" + (pageCount==0?1:pageCount))%>">βҳ</a></td>
        </tr>
      </table></td>
  </tr>
</table>

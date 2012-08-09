<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
int parent = ParamUtil.getIntParameter(request, "parent", 0);
List list = (List)request.getAttribute("list");
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysApplication bean=(SysApplication)iter.next();	
%>  
  <li><a href="javascript:jump('<%=request.getContextPath()%><%=bean.getUrl()%>');" onmouseover="createChildNode(<%=bean.getId()%>)"><%=bean.getName()%></a>
    <%if(bean.getShowMenu()!=0){//有子菜单%>
	<ul id='node_<%=bean.getId()%>'></ul>
	<%}%>
  </li>
<%
  }//while
}
%>

<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
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
  <li><a href="javascript:jump('<%=bean.getUrl()%>');" onmouseover="createChildNode(<%=bean.getId()%>)"><%=bean.getName()%></a>
    <%if(bean.getShowMenu()!=0){//ÓÐ×Ó²Ëµ¥%>
	<ul id='node_<%=bean.getId()%>'></ul>
	<%}%>
  </li>
<%
  }//while
}
%>

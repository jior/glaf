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
if(list!=null && list.size()>0){
  int i=1;
  Iterator iter=list.iterator();
  while(iter.hasNext()){
    SubjectCode bean=(SubjectCode)iter.next();
	String code=bean.getSubjectCode();
	code=code.substring(code.indexOf(".")+1);
	
	String value = "{id:"+bean.getId() + ",name:\\\\'" + bean.getSubjectName() + "\\\\',code:\\\\'"+ code + "\\\\'}" ;
%>
node.childNodes[node.childNodes.length] = new Node(<%=bean.getId()%>, <%=parent%>, "<%=bean.getSubjectName()%>", "", "<%=value%>");
<%
    i++;
  }
}
%>
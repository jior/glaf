<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.utils.*"%>
<%@ page import="com.glaf.base.modules.others.*"%>
<%@ page import="com.glaf.base.modules.others.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
List list = (List)request.getAttribute("list");
List list_old = (List)request.getAttribute("list_old");//旧审批意见列表
String height = ParamUtil.getParameter(request, "height", "80px");
%>
<table width="100%" id="main" border="0" cellspacing="1" cellpadding="0">
  <tr>
    <td height="20">审批意见</td>
  </tr>
</table>
<div style="width:100%; height:<%= height %>;overflow-x:auto; overflow-y:auto;">
<table width="100%" id="main" border="0" cellspacing="1" cellpadding="0" class="list-box">
<tr class="list-title" style="position:relative; top:expression(this.offsetParent.scrollTop-2);">
   <TD width="13%" nowrap>部门</TD>
   <TD width="20%" nowrap>审批人</TD>
   <TD width="18%" nowrap>是否审批通过</TD>
   <TD width="35%" nowrap>意见</TD>
   <TD width="14%" nowrap>日期</TD>
</TR>
<%
//显示旧审批意见列表
if(list_old!=null){
  Iterator iter = list_old.iterator();
  while(iter.hasNext()){
    Audit bean = (Audit)iter.next();
%>
<TR>
   <TD height="18"><%=bean.getDeptName()%></TD>
   <TD><%=bean.getLeaderName()%></TD>
   <TD><%=bean.getFlag()==1?"是":"否"%></TD>
   <TD title="<%=bean.getMemo()%>"><%=bean.getMemo()%></TD>
   <TD><%=glafUtil.dateToString(bean.getCreateDate())%></TD>
</TR>
<%
  }
}
%>
<%
//显示新审批意见列表
if(list!=null){
  Iterator iter = list.iterator();
  while(iter.hasNext()){
    Audit bean = (Audit)iter.next();
%>
<TR>
   <TD height="18"><%=bean.getDeptName()%></TD>
   <TD><%=bean.getLeaderName()%></TD>
   <TD><%=bean.getFlag()==1?"是":"否"%></TD>
   <TD title="<%=bean.getMemo()%>"><%=bean.getMemo()%></TD>
   <TD><%=glafUtil.dateToString(bean.getCreateDate())%></TD>
</TR>
<%
  }
}
%>
</TABLE>
</div>

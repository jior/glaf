<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.utils.*"%>
<%@ page import="com.glaf.base.modules.others.*"%>
<%@ page import="com.glaf.base.modules.others.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
List list = (List)request.getAttribute("list");
List list_old = (List)request.getAttribute("list_old");//����������б�
String height = ParamUtil.getParameter(request, "height", "80px");
%>
<table width="100%" id="main" border="0" cellspacing="1" cellpadding="0">
  <tr>
    <td height="20">�������</td>
  </tr>
</table>
<div style="width:100%; height:<%= height %>;overflow-x:auto; overflow-y:auto;">
<table width="100%" id="main" border="0" cellspacing="1" cellpadding="0" class="list-box">
<tr class="list-title" style="position:relative; top:expression(this.offsetParent.scrollTop-2);">
   <TD width="13%" nowrap>����</TD>
   <TD width="20%" nowrap>������</TD>
   <TD width="18%" nowrap>�Ƿ�����ͨ��</TD>
   <TD width="35%" nowrap>���</TD>
   <TD width="14%" nowrap>����</TD>
</TR>
<%
//��ʾ����������б�
if(list_old!=null){
  Iterator iter = list_old.iterator();
  while(iter.hasNext()){
    Audit bean = (Audit)iter.next();
%>
<TR>
   <TD height="18"><%=bean.getDeptName()%></TD>
   <TD><%=bean.getLeaderName()%></TD>
   <TD><%=bean.getFlag()==1?"��":"��"%></TD>
   <TD title="<%=bean.getMemo()%>"><%=bean.getMemo()%></TD>
   <TD><%=glafUtil.dateToString(bean.getCreateDate())%></TD>
</TR>
<%
  }
}
%>
<%
//��ʾ����������б�
if(list!=null){
  Iterator iter = list.iterator();
  while(iter.hasNext()){
    Audit bean = (Audit)iter.next();
%>
<TR>
   <TD height="18"><%=bean.getDeptName()%></TD>
   <TD><%=bean.getLeaderName()%></TD>
   <TD><%=bean.getFlag()==1?"��":"��"%></TD>
   <TD title="<%=bean.getMemo()%>"><%=bean.getMemo()%></TD>
   <TD><%=glafUtil.dateToString(bean.getCreateDate())%></TD>
</TR>
<%
  }
}
%>
</TABLE>
</div>

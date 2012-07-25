<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
List list = (List)request.getAttribute("list");
SysUser user = (SysUser)request.getAttribute("user");
Set roleId=new HashSet();
Iterator roles = user.getRoles().iterator();
while(roles.hasNext()){  
  SysDeptRole role=(SysDeptRole)roles.next();
  roleId.add(new Long(role.getId()));
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="JavaScript">
function checkForm(form){
  var isChecked = false;
  for(var i = 0; i < form.elements.length; i++){
    var e = form.elements[i];
    if(e.name != "chkall" && e.type == "checkbox" && e.checked == true){
      isChecked = true;
      break;
    }
  }
  if(!isChecked){
    alert("����û��ѡ��Ҫ��Ȩ�Ľ�ɫ.");
    return false;
  }
}
</SCRIPT>
</head>

<body>
<div class="nav-title"><span class="Title">�û�����</span>&gt;&gt;�����û� <b><%=user.getName()%></b> ��Ȩ��</div>
<html:form action="/sys/user.do?method=setRole" method="post" target="_self" onsubmit="return checkForm(this);"> 
<input type="hidden" name="user_id" value="<%=user.getId()%>">
<table width="95%" border="0" align="center" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"> 
    <td width="9%" align="center"><input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this)">
    </td>
    <td width="10%" align="center">���</td>
    <td width="37%" align="center">����</td>
    <td width="44%" align="center">����</td>
  </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysDeptRole bean=(SysDeptRole)iter.next();	
%>
  <tr <%=i%2==0?"":"class='list-back'"%>>
    <td class="td-cb"><input type="checkbox" name="id" value="<%=bean.getId()%>" <%=roleId.contains(new Long(bean.getId()))?"checked":""%>>
    </td>
    <td class="td-no"><%= i+1%></td>
    <td class="td-text"><%=bean.getRole().getName()%>&nbsp;</td>
    <td class="td-text"><%=bean.getRole().getDesc()%>&nbsp;</td>
  </tr>
  <%
    i++;
  }
}
for(; i<10; i++){
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td height="20">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp; </td>
    <td>&nbsp;</td>
    </tr>
<%
}
%>
</table>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" height="30" valign="bottom"><input name="btn_add" type="submit" value="����" class="button">
    </td>
  </tr>
</table>
</html:form> 
</body>
</html>

<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysUser bean=(SysUser)request.getAttribute("bean");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="javascript" src='<%=context%>/js/verify.js'></script></head>
<script language="JavaScript">
function checkForm(form){
  if(verifyAll(form)){
     if(form.password.value!=form.password2.value){
	   alert("������ȷ�����벻ƥ��");
	 }else{
	   return true;
	 }
  }
   return false;
}
function setValue(obj){
  obj.value=obj[obj.selectedIndex].value;
}
</script>
</head>

<body>
<div class="nav-title">�޸��û���Ϣ</div>
<html:form action="/sys/user.do?method=saveModifyInfo" method="post" target="hiddenFrame" onsubmit="return checkForm(this);"> 
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lt">&nbsp;</td>
        <td class="box-mt">&nbsp;</td>
        <td class="box-rt">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="box-mm"><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td width="20%" class="input-box">Ա������</td>
        <td width="80%"><%=bean.getCode()%></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">�ա�����</td>
        <td><%=bean.getName()%></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">�ܡ�����*</td>
        <td><input name="password" type="password" size="30" class="input" value="<%=bean.getPassword()%>" datatype="string" nullable="no" minsize="6" maxsize="20" chname="����"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">ȷ������*</td>
        <td><input name="password2" type="password" size="30" class="input" value="<%=bean.getPassword()%>"  datatype="string" nullable="no" minsize="6" maxsize="20" chname="ȷ������"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">�֡�����*</td>
        <td>
          <input name="mobile" type="text" size="30" class="input" datatype="string" value="<%=bean.getMobile()%>" nullable="no" maxsize="12" chname="�ֻ�">        </td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">�ʡ�����*</td>
        <td>
          <input name="email" type="text" size="30" class="input" datatype="email" value="<%=bean.getEmail()%>" nullable="no" maxsize="50" chname="�ʼ�">        </td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">�칫�绰*</td>
        <td>
          <input name="telephone" type="text" size="30" class="input" datatype="string" value="<%=bean.getTelephone()%>" nullable="no" maxsize="20" chname="�칫�绰">        </td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save2" type="submit" value="����" class="button"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lb">&nbsp;</td>
        <td class="box-mb">&nbsp;</td>
        <td class="box-rb">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script language="javascript">
attachFrame();
</script>
</body>
</html>

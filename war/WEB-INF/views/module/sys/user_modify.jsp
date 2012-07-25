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
List  list = (List)request.getAttribute("parent");
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
<div class="nav-title"><span class="Title">�û�����</span>&gt;&gt;�޸��û�</div>
<html:form action="/sys/user.do?method=saveModify" method="post"  onsubmit="return checkForm(this);"> 
<input type="hidden" name="id" value="<%=bean.getId()%>">
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
        <td width="20%" class="input-box">Ա������*</td>
        <td width="80%"><%=bean.getCode()%></td>
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
        <td class="input-box2" valign="top">��������</td>
        <td><select name="parent" onChange="javascript:setValue(this);">
          <%
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysTree bean2=(SysTree)iter.next();	
	SysDepartment dept = bean2.getDepartment();
%>
          <option value="<%=dept!=null?dept.getId():""%>">
          <%
for(int i=1;i<bean2.getDeep();i++){
  out.print("&nbsp;&nbsp;");
}
out.print(bean2.getName());
%>
          </option>
          <%    
  }
}
%>
        </select>
		<script language="JavaScript">								
          document.all.parent.value="<%=bean.getDepartment().getId()%>";	
	    </script>		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">�ա�����*</td>
        <td><input name="name" type="text" size="28" class="input" value="<%=bean.getName()%>" datatype="string" nullable="no" maxsize="20" chname="����"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">�ԡ�����</td>
        <td>
          <input type="radio" name="gender" value="0" <%=bean.getGender()==0?"checked":""%>>
��
<input type="radio" name="gender" value="1" <%=bean.getGender()==1?"checked":""%>>
Ů</td>
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
        <td class="input-box2" valign="top">�Ƿ���Ч</td>
        <td>
          <input type="radio" name="blocked" value="0" <%=bean.getBlocked()==0?"checked":""%>>
          ��
          <input type="radio" name="blocked" value="1" <%=bean.getBlocked()==1?"checked":""%>>
��</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">ְ������</td>
        <td>
          <input name="headship" type="text" size="30" class="input" datatype="string" nullable="no" maxsize="20" chname="ְ��" value="<%=bean.getHeadship()%>"onClick="selectData('ZD0019', null, this, document.all.userType)">
		  <input type="hidden" name="userType" value="<%=bean.getUserType()%>">        </td>
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
</body>
</html>

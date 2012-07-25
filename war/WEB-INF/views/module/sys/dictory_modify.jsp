<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
Dictory bean=(Dictory)request.getAttribute("bean");
List list = (List)request.getAttribute("parent");
int parent = ParamUtil.getIntParameter(request, "parent", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>����ƽ̨ϵͳ</title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="javascript" src='<%=context%>/js/verify.js'></script></head>
</head>

<body>
<div class="nav-title"><span class="Title">�ֵ����</span>&gt;&gt;�޸��ֵ�</div>
<html:form action="/sys/dictory.do?method=saveModify" method="post" onsubmit="return verifyAll(this);"> 
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
        <td width="21%" class="input-box">��������<font color="red">*</font></td>
        <td width="79%"><input type="text" name="name" class="input" value="<%=bean.getName()%>" datatype="string" nullable="no" maxsize="50" chname="����"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">��������</td>
        <td><input type="text" name="code" class="input" datatype="string" nullable="yes" maxsize="10" value="<%=bean.getCode()%>" chname="����"></td>
      </tr>
      <%
      if(17 == parent){
      %>
      <tr>
        <td class="input-box2" valign="top">1 ��Ԫ =<font color="red">*</font></td>
        <td><input type="text" name="ext1" class="input" datatype="float" nullable="no" maxsize="100" chname="��Ԫ����" value="<%=bean.getExt1() == null ? "" : bean.getExt1()%>"> �����<font color="red">[����]</font></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">1 ��Ԫ =<font color="red">*</font></td>
        <td><input type="text" name="ext2" class="input" datatype="float" nullable="no" maxsize="100" chname="��Ԫ����" value="<%=bean.getExt2() == null ? "" : bean.getExt2()%>"> �����<font color="red">[����]</font></td>
      </tr>
      <%}else{ %>
      <tr>
      	<td class="input-box2" valign="top">��չ�ֶ�1</td>
      	<td><input type="text" name="ext1" class="input" datatype="string" nullable="yes" maxsize="200" chname="��չ�ֶ�1" value="<%=bean.getExt1() == null ? "" : bean.getExt1()%>"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">��չ�ֶ�2</td>
        <td><input type="text" name="ext2" class="input" datatype="string" nullable="yes" maxsize="200" chname="��չ�ֶ�2" value="<%=bean.getExt2() == null ? "" : bean.getExt2()%>"></td>
      </tr>
      <%} %>
        
      
      <tr>
        <td class="input-box2" valign="top">�Ƿ���Ч</td>
        <td>
          <input type="radio" name="blocked" value="1" <%=bean.getBlocked()==1?"checked":""%>>
��
<input type="radio" name="blocked" value="0" <%=bean.getBlocked()==0?"checked":""%>>
��</td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save" type="submit" value="����" class="button"></td>
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

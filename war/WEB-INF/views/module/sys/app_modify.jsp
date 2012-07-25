<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysApplication bean=(SysApplication)request.getAttribute("bean");
List  list = (List)request.getAttribute("parent");
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
  if(verifyAll(form)){
     if(form.parent.value=='<%=bean.getId()%>'){
	   alert("��ǰģ�鲻��ѡ��Ϊ����ģ��");
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
<div class="nav-title">ģ�����&gt;&gt;����ģ��</div>
<html:form action="/sys/application.do?method=saveModify" method="post"  onsubmit="return checkForm(this);"> 
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
        <td class="input-box">�ϼ�ģ��</td>
        <td><select name="parent" onChange="javascript:setValue(this);" class="input">
          <%
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysTree bean2=(SysTree)iter.next();
%>
          <option value="<%=bean2.getId()%>">
          <%
for(int i=0;i<bean2.getDeep();i++){
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
		  document.all.parent.value="<%=bean.getNode().getParent()%>";	
	    </script>
		</td>
      </tr>
      <tr>
        <td class="input-box">��������*</td>
        <td><input name="name" type="text" class="input" value="<%=bean.getName()%>" size="37" datatype="string" nullable="no" maxsize="20" chname="����"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">�衡����</td>
        <td><textarea name="desc" cols="35" rows="8" class="input-multi" datatype="string" nullable="yes" maxsize="100" chname="����"><%=bean.getDesc()%></textarea></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">��������</td>
        <td><input name="url" type="text" class="input" value="<%=bean.getUrl()%>" size="37"  datatype="string" nullable="yes" maxsize="200" chname="����"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">�Ƿ񵯳���</td>
        <td>
          <input type="radio" name="showMenu" value="2" <%=bean.getShowMenu()==2?"checked":""%>>
��
<input type="radio" name="showMenu" value="1" <%=bean.getShowMenu()!=2?"checked":""%>>
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

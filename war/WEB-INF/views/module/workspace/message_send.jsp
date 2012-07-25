<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.workspace.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
  Message bean = (Message) request.getAttribute("bean");
    String title = "";
	String recverIds = "";
	String recverName = "";
	String recverEmail = "";
	if (bean != null) {
		title = "Re:" + bean.getTitle();
		SysUser recver = bean.getSender();
		recverIds = recver == null ? "" : recver.getId() + "";
		recverName = recver == null ? "" : recver.getName()+"";
		recverEmail =recver == null ? "" : recver.getEmail();
	
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="../../css/site.css" type="text/css" rel="stylesheet">
<link href="<%= request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<script type='text/javascript' src="<%= request.getContextPath() %>/js/css.js"></script>
<script type='text/javascript' src='<%= request.getContextPath() %>/js/main.js'></script>
<script type='text/javascript' src="<%= request.getContextPath() %>/js/verify.js"></script>
<script type="text/javascript">
function changeSelect() {
  var recverType = getRadioValue('recverType');
	  $('recverName').value = '';
	  $('recverIds').value = '';
	   $('toEmail').value='';
	if (recverType == 0) {
	 
	  $('add_user').disabled = false;
	  $('add_supplier').disabled = true;
	  $('add_dept').disabled = true;
	} else if(recverType ==1){
	
	  $('add_user').disabled = true;
	  $('add_dept').disabled = false;
	  $('add_supplier').disabled = true;
	} else if(recverType == 2){
	
	  $('add_user').disabled = true;
	  $('add_dept').disabled = true;
	  $('add_supplier').disabled = false;
	}
}

function selectSpForEmail(type,referId,referTitle,referCode,category){
    var url =  '<%=request.getContextPath()%>/spadmin/supplier.do?method=showSpForEmailList';
	if (type) {
		url += '&query_type_ex=' + type;
	}
	if (category) {
		url += '&query_category_el=' + category;
	}
	return ShowDialog(url, 500, 450, false, false, referId, referTitle, referCode);
}

function selectSysUser(deptId,referId,referTitle,referCode){
   var url = '<%=request.getContextPath()%>/sys/user.do?method=selectSysUser';
   
   return ShowDialog(url,430,450,false,false,referId, referTitle, referCode);

}

function saveMessage(form){
    if($('recverName').value==''){
     alert("��ѡ���ռ���");
     return false;
    }
    if($('title').value==''){
     alert("���ⲻ��Ϊ��");
     return false;
    }
    form.action="<%=request.getContextPath()%>/workspace/message.do?method=saveSend";
    form.submit();
}
function saveEmail(form){
    if($('recverName').value==''){
     alert("��ѡ���ռ���");
     return false;
    }
    if($('title').value==''){
     alert("���ⲻ��Ϊ��");
     return false;
    }
    form.action="<%=request.getContextPath()%>/workspace/message.do?method=saveEmail";
    form.submit();

}

function saveBoth(form){
    if($('recverName').value==''){
     alert("��ѡ���ռ���");
     return false;
    }
    if($('title').value==''){
     alert("���ⲻ��Ϊ��");
     return false;
    }
    form.action= "<%=request.getContextPath()%>/workspace/message.do?method=saveBoth";
    form.submit();
}

</script>
<title>������Ϣ</title>
</head>

<body>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td class="nav-title"><span class="Title">����̨</span>&gt;&gt; ������Ϣ</td>
  </tr>
</table>
<html:form action="/workspace/message.do?method=saveSend" method="post" onsubmit="return verifyAll(this);" >
<input type="hidden" name="sysType" value="1">
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
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
    <td class="box-mm"><table width="95%" border="0" align="center" cellpadding="5" cellspacing="0">
      <tr>
        <td width="50" class="input-box">�ռ���<font color="#FF0000">*</font></td>
        <td><input name="recverName" type="text" class="input" size="25" value="<%= recverName %>" readonly="readonly" datatype="string" nullable="no" chname="������">
            <input name="recverIds" type="hidden" value="<%= recverIds %>">
            
            <input name="recverType" type="radio" value="0" checked onClick="changeSelect()">
            <input name="add_user" type="button" class="button" value="�����û�" onClick="selectSysUser(0, $('recverIds'), $('recverName'),$(toEmail))">
          <input type="radio" name="recverType" value="1" onClick="changeSelect()">
          <input name="add_dept" type="button" class="button" value="ѡ����" onClick="selectDept(5, $('recverIds'), $('recverName'),document.all.toEmail)" disabled="disabled">
          <input type="radio" name="recverType" value="2" onClick="changeSelect()">
          <input name="add_supplier" type="button" class="button" value="ѡ��Ӧ��"  onClick="selectSpForEmail(-1, $('recverIds'), $('recverName'),document.all.toEmail)" disabled="disabled">
           </td>
      </tr>
      <tr><td width="50" class="input-box">��&nbsp;&nbsp;��</td>
          <td><input name="toEmail" type="text" datatype="string" nullable="no" searchflag="1" size="25"  readonly="readonly" class="input" value="<%=recverEmail %>" >
            &nbsp;&nbsp;ע�⣺ *ѡ��Ӧ�̱�����ϵͳ�û����ܷ���ϵͳ��Ϣ*
          </td>
      </tr>
      <tr>
        <td class="input-box">��&nbsp;&nbsp;��<font color="#FF0000">*</font></td>
        <td><input name="title" type="text" class="input" size="60" maxlength="50" value="<%= title %>" datatype="string" nullable="no" chname="����" maxsize="50"></td>
      </tr>
      <tr>
        <td valign="top" class="input-box2">��&nbsp;&nbsp;��</td>
        <td><textarea name="content" cols="58" rows="6" class="input" datatype="string" nullable="no" chname="����" maxsize="2000"></textarea></td>
      </tr>
      <tr>
        <td valign="top">&nbsp;</td>
        <td><input name="btn_save" type="button" class="button" value="����ϵͳ��Ϣ"  onClick="saveMessage(this.form)">
            <input name="btn_email" type="button" class="button" value="����Email"  onClick="saveEmail(this.form)">
            <input name="btn_both" type="button" class="button" value="˫����"  onClick="saveBoth(this.form)">
        </td>
      </tr>
    </table>
    </td>
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

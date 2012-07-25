<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysDepartment bean=(SysDepartment)request.getAttribute("bean");
List  list = (List)request.getAttribute("parent");
Set histortDeparts = bean.getHistoryDeparts();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script></head>
<script language="JavaScript">
function checkForm(form){
  if(verifyAll(form)){
     if(form.parent.value=='<%=bean.getId()%>'){
	   alert("��ǰ���Ų���ѡ��Ϊ��������");
	 }else{
	   selectCB("historyId");
	   return true;
	 }
  }
   return false;
}
function setValue(obj){
  obj.value=obj[obj.selectedIndex].value;
}
//�����ʷ����
function addHistory(form){
	var departIds = "";
	var departNames = "";
	var url = context + "/sys/department.do?method=showDeptAllSelect&parent=5";
  	var features="dialogHeight:310px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
  	var ret = window.showModalDialog(url, window, features);
  	if(ret!=null){
  		for(var i=0; i<ret.length; i++){
			var depart ;
			eval("depart="+ret[i]);
			if(!isAddDepart(depart)){
				addTableRow(depart);
			}
		}
  	}
}
//��ӵ�table��
function addTableRow(depart){
	var index = $("historyTb").rows.length;
	var row = $("historyTb").insertRow(index);
	var col = row.insertCell(0); 
	col.align="center"; 
	col.className = "td-cb";
	col.innerHTML = "<input type=\"checkbox\" name=\"historyId\" value=\""+ depart.id +"\">";
	col = row.insertCell(1); 
	col.align="center"; 
	col.className = "td-no";
	col.innerHTML = ""+index; 
	
	col = row.insertCell(2); 
	col.align="center"; 
	col.className = "td-text";
	col.innerHTML = ""+depart.name;
	
	if(index % 2 == 0){
		row.className="list-back";
	}
}
//ȫѡ��ʷ����
function selectCB(name){
	var obj = document.getElementsByName(name);
	for (var i = 0; i < obj.length; i++) {
		 var o = obj.item(i);
		 if (o.type=="checkbox") {
				o.checked = true;
		 }
	}
}
//�ж��Ƿ������ӵ�table��
function isAddDepart(depart){
	var ret = false;
	var obj = document.getElementsByName("historyId");
	for (var i = 0; i < obj.length; i++) {
		 var o = obj.item(i);
		 if (o.type=="checkbox" && o.value == depart.id) {
				ret = true;
				break;
		 }
	}
	return ret;
}
//ɾ����ʷ����
function deleteHistory(form){
	var num = getCheckedBoxNum(form,"historyId");
	var rows = $("historyTb").rows
	if(num == 0){
		alert("��ѡ����Ҫɾ���Ĳ���!");
		return;
	}else{
		var obj = document.getElementsByName("historyId");
		for (var i = 0; i < obj.length; i++) {
			 var o = obj.item(i);
			 if (o.type=="checkbox" && o.checked == true) {
			 	row = o.parentNode.parentNode;
				$("historyTb").deleteRow(row.rowIndex);
				i=i-1;
			 }
			 
		}
		reloadHistoryTable();
	}
}

function reloadHistoryTable(){
	var tab = $("historyTb");
	for(var i = 1; i< tab.rows.length; i++){
		var row = tab.rows[i];
		var col = row.cells[1];
		col.innerHTML = ""+i;
		if(i % 2 == 0){
			row.className="list-back";
		}else{
			row.className="";
		}
	}
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title">���Ź���</span>&gt;&gt;�޸Ĳ���</div>
<html:form action="/sys/department.do?method=saveModify" method="post"  onsubmit="return checkForm(this);"> 
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
        <td class="input-box">�ϼ�����</td>
        <td><select name="parent" onChange="javascript:setValue(this);" class="input">
          <%
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysTree bean2=(SysTree)iter.next();
%>
          <option value="<%=bean2.getId()%>">
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
		  document.all.parent.value="<%=bean.getNode().getParent()%>";
	    </script>		</td>
      </tr>
      <tr>
        <td class="input-box">��������*</td>
        <td><input name="name" type="text" class="input" id="name" value="<%=bean.getName()%>" size="37" datatype="string" nullable="no" maxsize="20" chname="��������"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">�衡����</td>
        <td><textarea name="desc" cols="35" rows="8" class="input" id="desc" datatype="string" nullable="yes" maxsize="1000" chname="����"><%=bean.getDesc()%></textarea></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">��������*</td>
        <td><input name="code" type="text" class="input" id="code" value="<%=bean.getCode()%>" size="37"  datatype="string" nullable="no" maxsize="10" chname="����"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">�ࡡ����*</td>
        <td><input name="no" type="text" class="input" id="no" value="<%=bean.getNo()%>" size="37"  datatype="string" nullable="no" maxsize="10" chname="����"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">��������*</td>
        <td><input name="code2" type="text" size="37" class="input" value="<%=bean.getCode2()%>" datatype="string" nullable="no" maxsize="10" chname="��������"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">�Ƿ���Ч*</td>
        <td><input type="radio" name="status" value="0" <%=bean.getStatus()==0?"checked":""%>>
          ��
          <input type="radio" name="status" value="1" <%=bean.getStatus()==1?"checked":""%>>
��</td>
      </tr>
      <tr>
        <td colspan="2" valign="top" class="input-box2">��ʷ�����б�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <!--input name="add" type="button" class="button" value="���" onClick="addHistory(this.form)">&nbsp;&nbsp;<input name="delete" type="button" class="button" value="ɾ��" onClick="deleteHistory(this.form)"-->
		  <DIV id="listDiv" align="center" style="width:100%; height:110px;overflow-x:auto; overflow-y:auto;">
		<table id="historyTb" width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title" style="position:relative; top:expression(this.offsetParent.scrollTop-2);"> 
    <td width="10%" align="center">ѡ��</td>
    <td width="14%" align="center">���</td>
    <td width="76%" align="center">����</td>
    </tr>
  <%
	int i=0;
	if(histortDeparts!=null){
		Iterator iter=histortDeparts.iterator();   
		while(iter.hasNext()){
			SysDepartHistory history = (SysDepartHistory)iter.next();
			SysDepartment historyBean = history.getOldDepart();	
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td class="td-cb"align="center"> <input type="checkbox" name="historyId" value="<%=historyBean.getId()%>"></td>
    <td class="td-no"><%= i+1 %></td>
    <td class="td-text"><%=historyBean.getName()%></td>
    </tr>
  <%
    		i++;
  		}
	}
%>
</table>
		</DIV>
		</td>
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
<script language="javascript">
attachFrame();
</script>
</html:form> 
</body>
</html>

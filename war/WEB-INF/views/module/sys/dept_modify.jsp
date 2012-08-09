<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysDepartment bean=(SysDepartment)request.getAttribute("bean");
List  list = (List)request.getAttribute("parent");
//Set histortDeparts = bean.getHistoryDeparts();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script></head>
<script language="JavaScript">
function checkForm(form){
  if(verifyAll(form)){
     if(form.parent.value=='<%=bean.getId()%>'){
	   alert("当前部门不能选择为所属部门");
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
//添加历史部门
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
//添加到table中
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
//全选历史部门
function selectCB(name){
	var obj = document.getElementsByName(name);
	for (var i = 0; i < obj.length; i++) {
		 var o = obj.item(i);
		 if (o.type=="checkbox") {
				o.checked = true;
		 }
	}
}
//判断是否可以添加到table中
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
//删除历史部门
function deleteHistory(form){
	var num = getCheckedBoxNum(form,"historyId");
	var rows = $("historyTb").rows
	if(num == 0){
		alert("请选择需要删除的部门!");
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
<div class="nav-title"><span class="Title">部门管理</span>&gt;&gt;修改部门</div>
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
        <td class="input-box">上级部门</td>
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
        <td class="input-box">部门名称*</td>
        <td><input name="name" type="text" class="input" id="name" value="<%=bean.getName()%>" size="37" datatype="string" nullable="no" maxsize="20" chname="部门名称"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">描　　述</td>
        <td><textarea name="desc" cols="35" rows="8" class="input" id="desc" datatype="string" nullable="yes" maxsize="1000" chname="描述"><%=bean.getDesc()%></textarea></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">代　　码*</td>
        <td><input name="code" type="text" class="input" id="code" value="<%=bean.getCode()%>" size="37"  datatype="string" nullable="no" maxsize="10" chname="代码"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">编　　码*</td>
        <td><input name="no" type="text" class="input" id="no" value="<%=bean.getNo()%>" size="37"  datatype="string" nullable="no" maxsize="10" chname="编码"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">部门区分*</td>
        <td><input name="code2" type="text" size="37" class="input" value="<%=bean.getCode2()%>" datatype="string" nullable="no" maxsize="10" chname="部门区分"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">是否有效*</td>
        <td><input type="radio" name="status" value="0" <%=bean.getStatus()==0?"checked":""%>>
          是
          <input type="radio" name="status" value="1" <%=bean.getStatus()==1?"checked":""%>>
          否
		</td>
      </tr>
     
    </table>
		</DIV>
		</td>
        </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save" type="submit" value="保存" class="button"></td>
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

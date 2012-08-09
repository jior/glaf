<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.utils.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysUser user = (SysUser)request.getAttribute("user");
List list = (List)request.getAttribute("authorizedUser");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>XXXX基础平台系统</title>
<base target="_self">
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<link href="<%=request.getContextPath()%>/js/calendar/skins/aqua/theme.css"  type="text/css" title="Aqua" rel="stylesheet"/>
<script src="<%=context%>/js/calendar/calendar.js" language="javascript"></script>
<script src="<%=context%>/js/calendar/lang/calendar-en.js" language="javascript"></script>
<script src="<%=context%>/js/calendar/lang/calendar-setup.js" language="javascript"></script>
<script type='text/javascript' src='<%=context%>/dwr/interface/SysUserRoleAjaxService.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/util.js'></script>
</head>
<script language="javascript">
var fromUserId=<%=user.getId()%>;
var num=0;
var userRole = "";

function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
    document.all.btn_cancel.disabled=false;
  }else{
    document.all.btn_cancel.disabled=true;
  }
}
function selectSysUser(referId,referTitle){
  var url = '<%=context%>/sys/user.do?method=selectSysUser&multDate=1';
  return ShowDialog(url,430,450,false,false,referId, referTitle,$('userEmail'),$('userCode'), false);
}

function cancel(form){
  //if(confirm("真的要取消授权吗？")){
  var obj=document.getElementsByName('id');
   for (var i=0; i<obj.length; i++) {
     var e = obj[i];
     if (e.checked){
     	delRow('listTable','td_'+e.value);
     	delUserRole(e.value);
     }
   }
	
   checkOperation(form);
  //}
}
function add(){
  if($("userId").value.length==0){
    alert("请选择用户");
	return;
  }
  if($("startDate").value.length==0){
    alert("请选择开始日期");
	return;
  }
  if($("endDate").value.length==0){
    alert("请选择结束日期");
	return;
  }
  if(checkDate($("startDate").value, $("endDate").value, true)){
    alert("开始日期不能晚于结束日期");
	return;
  }
   var idNum=0;
   if(userRole!=''){
   		var userRoleArray = userRole.split(',');
   		idNum = userRoleArray.length;
   }
   
   if(idNum<1){
	   if(checkUserRole($("userId").value)){
		    var outStr = new Array();
		    
		    outStr[0]='';
		    outStr[1]='<input type="checkbox" id="id" name="id" value="'+$("userId").value+'" onClick="checkOperation(this.form)">'+
		    			'<input type="hidden" name="userIds" value="'+$("userId").value+'">'+
		    			'<input type="hidden" name="startDate_'+$("userId").value+'" value="'+$("startDate").value+'">'+
		    			'<input type="hidden" name="endDate_'+$("userId").value+'" value="'+$("endDate").value+'">';
		    outStr[2]=$('userName').value+'['+$('userCode').value+']';
		    outStr[3]=$("startDate").value+' - '+$("endDate").value;
		    
		    addRow('listTable',outStr,$("userId").value);
		    
		    addUserRole($("userId").value);
		}else{
			alert($("userName").value + "不能重复授权!");
		}
	}else{
		alert("不能授权给二个人以上!");
	}
}

function save(){
	if(userRole==''){
		alert('请添加授权!');
		return false;
	}
	if(confirm("确认要授权吗？")){
		document.forms[0].submit();
		if($('btn_save')){$('btn_save').disabled=true;}
		if($('btn_cancel2')){$('btn_cancel2').disabled=true;}
		if($('btn_cancel')){$('btn_cancel').disabled=true;}
		if($('btn_add')){$('btn_add').disabled=true;}
	}
}

function checkUserRole(userid){
	var userRoleArray = userRole.split(',');
	for(var i=0;i<userRoleArray.length;i++){
		if(parseFloat(userid)==parseFloat(userRoleArray[i])){
			return false;
		}
	}
	return true;
}

function addUserRole(userid){
	if(userRole==''){
    	userRole=userid;
    }else{
    	userRole=userRole+','+userid;
    }
}

function delUserRole(userid){
	var temp = '';
	var userRoleArray = userRole.split(',');
	for(var i=0;i<userRoleArray.length;i++){
		if(parseFloat(userid)==parseFloat(userRoleArray[i])){
			continue;
		}else{
			temp = temp+userRoleArray[i]+',';
		}
	}
	if(temp!=''){
		temp = temp.substring(0,temp.length-1);
	}
	
	userRole = temp;
}

function addRow(table,str,userid){
	var row =document.getElementById(table).insertRow();
	//row.id=row.rowIndex;
	row.id='td_'+userid;
	
	var col=null; 
	var lastid=0;
	for(var i=0;i<str.length-1;i++){
		col = row.insertCell(i); 
		col.align = "center"; 
		col.innerHTML = str[i+1];
		lastid=i+1;
	}
}

function delRow(table,id){
	var index=document.getElementById(id).rowIndex;
	document.getElementById(table).deleteRow(index);
}
</script>
<body>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
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
      <td class="box-mm">
		<html:form method="post" action="/sys/sysUserRole.do?method=saveUserSysAuth" target="_self">
		<input type="hidden" name="uid" value="<%=user.getId()%>">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="m-box">
		  <tr>
		    <td height="27" align="center"><b>用户<%=user.getName()%>[<%=user.getAccount()%>]的权限</b></td>
		  </tr>
		  <tr>
		    <td>已授权给</td>
		    </tr>
		</table>
		<div id="listDiv" style="width:390px; height:160px;overflow-x:auto; overflow-y:auto;">
		<table id="listTable" width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
		  <tr class="list-title" style="position:relative; top:expression(this.offsetParent.scrollTop-2);">
		    <td width="10%" align="center"><input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)"></td>
		    <td width="40%" align="center">用户</td>
		    <td width="50%" align="center">有效期</td>
		  </tr>
		</table>
		</div>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td height="22"><input type="button" name="btn_cancel" value="取消授权" onClick="cancel(this.form)" disabled></td>
		  </tr>
		  <tr>
		    <td height="22">&nbsp;</td>
		  </tr>
		  <tr>
		    <td height="22">授权给</td>
		  </tr>
		  <tr>
		    <td height="22">用　户:
		    <input name="userName" type="text" class="input" size="30" title="点击选择用户" readonly onClick="selectSysUser( $('userId'), $('userName'))">
			<input type="hidden" name="userId" value="">
			<input type="hidden" name="userEmail" value="">
			<input type="hidden" name="userCode" value="">
			</td>
		  </tr>
		  <tr>
		    <td height="22">有效期:
		      <input name="startDate" type="text" class="input" id="startDate" size="10" datatype="date" nullable="no" maxsize="20" chname="预计开始时间" value="" readonly searchflag=1>
		      <img src="<%=context%>/images/system_pic_35.gif" style="cursor:pointer" onClick="return showCalendar('startDate', '%Y-%m-%d')"> -
		      <input name="endDate" type="text" class="input" id="endDate" size="10" datatype="date" nullable="no" maxsize="20" chname="预计结束时间" value="" readonly searchflag=1>
		      <img src="<%=context%>/images/system_pic_35.gif" style="cursor:pointer" onClick="return showCalendar('endDate', '%Y-%m-%d')"> </td>
		  </tr>
		  <tr>
		    <td height="22"><input type="button" name="btn_add" value="添加授权" onClick="add()"></td>
		  </tr>
		</table>
		</html:form>
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
<center><input type="button" name="btn_save" value="确定" onClick="save();">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="btn_cancel2" value="退出" onClick="self.close();"></center>
</body>
</html>
<script type="text/javascript">
<!--
<%
Iterator iter = list.iterator();
while(iter.hasNext()){
  Object[] bean = (Object[])iter.next();
  SysUser user2 = (SysUser)bean[0];
  Date startDate=(Date)bean[1];
  Date endDate=(Date)bean[2];
%>
	var outStr = new Array();
		    
	outStr[0]='';
	outStr[1]='<input type="checkbox" id="id" name="id" value="<%=user2.getId() %>" onClick="checkOperation(this.form)">'+
				'<input type="hidden" name="userIds" value="<%=user2.getId() %>">'+
				'<input type="hidden" name="startDate_<%=user2.getId() %>" value="<%=glafUtil.dateToString(startDate) %>">'+
				'<input type="hidden" name="endDate_<%=user2.getId() %>" value="<%=glafUtil.dateToString(endDate) %>">';
	outStr[2]='<%=user2.getName() %>[<%=user2.getAccount() %>]';
	outStr[3]='<%=glafUtil.dateToString(startDate) %> - <%=glafUtil.dateToString(endDate) %>';
	
	addRow('listTable',outStr,'<%=user2.getId() %>');
	
	addUserRole('<%=user2.getId() %>');
<%
}
%>
//-->
</script>

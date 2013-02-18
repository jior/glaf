<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/jpage.tld" prefix="jpage"%>
<%@ page import="java.util.*"%>
<%@ page import="org.jpage.jbpm.util.*"%>
<%@ page import="org.jpage.jbpm.model.*"%>
<%

     String processInstanceId = request.getParameter("processInstanceId");
	 String selectedScript = (String)request.getAttribute("selectedScript");
	 String noselectedScript = (String)request.getAttribute("noselectedScript");
	 String taskNameScript = (String)request.getAttribute("taskNameScript");

	 if(selectedScript == null) {
		 selectedScript = "";
	 }
	 if(noselectedScript == null) {
		 noselectedScript = "";
	 }
	 if(taskNameScript == null) {
		 taskNameScript = "";
	 }
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">

<script language="JavaScript">

 function addElement() {
		if(document.getElementById("taskName").value==""){
			    alert("提示:请选择要重新分派任务的名称！");
			   document.getElementById("taskName").focus();
			   return;
		 }
        var list = document.typeForm.noselected;
        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
                var value = list.options[i].value;
                var text = list.options[i].text;
                addToList(value, text);
				list.remove(i);
				i=i-1;
            }
        }

    }

 function addToList(value, text) {
        var list = document.typeForm.selected;
        if (list.length > 0) {
            for (k = 0; k < list.length; k++) {
                if (list.options[k].value == value) {
                    return;
                }
            }
        }

        var len = list.options.length;
        list.length = len + 1;
        list.options[len].value = value;
        list.options[len].text = text;
    }

 function removeElement() {
		if(document.getElementById("taskName").value==""){
			   alert("提示:请选择要重新分派任务的名称！");
			   document.getElementById("taskName").focus();
			   return;
		 }
        var list = document.typeForm.selected;
		var slist = document.typeForm.noselected;
        if (list.length == 0 || list.selectedIndex < 0 || list.selectedIndex >= list.options.length)
            return;

        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
			    var value = list.options[i].value;
                var text = list.options[i].text;
                list.options[i] = null;
                i--;
				var len = slist.options.length;
				slist.length = len+1;
                slist.options[len].value = value;
                slist.options[len].text = text;				
            }
        }
    }
 

  function submitMxRequest() {
 
   if(document.getElementById("taskName").value==""){
	    alert("提示:请选择要重新分派任务的名称！");
	   document.getElementById("taskName").focus();
	   return;
   }

    var len=document.typeForm.selected.length;
	var result="";
	for (var i=0;i<len;i++) {
      result = result+","+document.typeForm.selected.options[i].value;
    }

	document.getElementById("actorIdXY").value = result;

	 if(confirm("系统将重新分配任务，确认吗?")){
         document.typeForm.submit();
	 }

  }

  function chooseUser(){
          document.typeForm.action="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=chooseUser";
		  document.typeForm.submit();
  }

  function goback(){
	    location.href='<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=task&processInstanceId=<%=processInstanceId%>';
}
</script>
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<center><br>
<br>
<form name="typeForm" method="post"
	action="<%=request.getContextPath()%>/workflow/processMonitorController.jspa?method=reassign"
	class="x-form"><input type="hidden" id="actorIdXY"
	name="actorIdXY"> <input type="hidden" id="processInstanceId"
	name="processInstanceId" value="<%=processInstanceId%>">


<div class="content-block" style="width: 685px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="任务分派">
任务分派</div>
<br>

<fieldset class="x-fieldset" style="width: 95%;">

<table class="table-border" align="center" cellpadding="4"
	cellspacing="1" width="80%">
	<tbody>
		<tr>
			<td align="center" colspan="5" valign="top">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="5">
			<div align="center">&nbsp;请选择要重新分派任务的名称 <select class="list"
				id="taskName" name="taskName" onchange="javascript:chooseUser();">
				<option value="">----请选择----</option>
				<%=taskNameScript%>
			</select></div>
			</td>
		</tr>
		<tr>
			<td class="table-content" height="26" valign="top" width="220">
			<div align="center">
			<div align="center"><br>
			可选人员<br>
			</div>
			<select class="list" style="width: 180px; height: 320px;"
				multiple="multiple" size="12" name="noselected"
				ondblclick="javascript:addElement();">
				<%=noselectedScript%>
			</select></div>
			</td>
			<td width="90">
			<div align="center">
			<input name="add" value="添加->"
				onClick="javascript:addElement();" class="button" type="button">
			<br>
			<br>
			<input name="remove" value="<-删除" 
			       onClick="javascript:removeElement();" class="button" type="button">
			</div>
			</td>
			<td class="table-content" height="26" valign="top" width="220">
			<div align="center">
			<div align="center"><br>
			已选人员<br>
			</div>
			<select class="list" style="width: 180px; height: 320px;"
				multiple="multiple" size="12" name="selected"
				ondblclick="javascript:removeElement();">
				<%=selectedScript%>
			</select></div>
			</td>
		</tr>
	</tbody>
</table>

</fieldset>


<div align="center"><br />
<input value="确 定" class="button" name="button" type="button"
	onclick="javacsript:submitMxRequest();"> <input value="返 回"
	class="button" name="button" type="button"
	onclick="javacsript:goback();"> <br />
<br />
</div>

</div>
</form>
</center>
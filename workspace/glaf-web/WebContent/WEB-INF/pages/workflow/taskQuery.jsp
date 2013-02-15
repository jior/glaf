<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<script language="javascript" src="<%=request.getContextPath()%>/workflow/includes/scripts/fixdate.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/workflow/includes/scripts/selectdate.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/workflow/includes/scripts/calendar/skins/aqua/theme.css"  rel="stylesheet"/>
<script src="<%=request.getContextPath()%>/workflow/includes/scripts/calendar/calendar.js" language="javascript"></script>
<script src="<%=request.getContextPath()%>/workflow/includes/scripts/calendar/lang/calendar-en.js" language="javascript"></script>
<script src="<%=request.getContextPath()%>/workflow/includes/scripts/calendar/calendar-setup.js" language="javascript"></script>

<script language="JavaScript">

 //(用于onKeypress事件)浮点数字框不能输入其他字符
  function integerInputFilter() {
		var berr=true;
		if (!((event.keyCode>=48 && event.keyCode<=57)))
		{
			alert("该字段只能输入正整数！");
			berr=false;
		}
		return berr;
	}

  function formSubmit() {
    document.typeForm.submit();
  }

</script>

<body leftmargin="0" topmargin="0"  marginheight="0" marginwidth="0">
<center>
  <br>
  <center>
    <span class="subject">
	<img src="<%=request.getContextPath()%>/workflow/images/title.gif" alt="任务查询"> 任务查询
	</span><br><br>
</center>
<form name="typeForm" method="post" action="<%=request.getContextPath()%>/workflow/processMonitorController.jspa">
<input type="hidden" name="method" value="taskInstances" >
<table class="table-border" align="center" cellpadding="4" cellspacing="1" width="90%">
      <tbody>
	   <tr>
        <td class="beta" width="12%"  align="center"><b>流程名称</b></td>
        <td class="table-content" width="38%">
		 <select name="processName" class="smallselect">
			    <option value="">----请选择----</option>
		    <%
				    Map deployMap = (Map)request.getAttribute("deployMap");
					if(deployMap != null){
						Iterator iterator = deployMap.keySet().iterator();
						while(iterator.hasNext()){
							String processName = (String)iterator.next();
							out.println("\n<option value=\""+processName+"\">"+processName+"</option>");
						}
					}
				 %>
			</select>
		</td>
		<td class="beta" width="12%"  align="center"><b>执行者</b></td>
        <td class="table-content" width="38%">
		    <select name="actorId" class="smallselect">
			    <option value="">----请选择----</option>
				<%
				    Map userMap = (Map)request.getAttribute("userMap");
					if(userMap != null){
						Iterator iterator = userMap.keySet().iterator();
						while(iterator.hasNext()){
							String actorId = (String)iterator.next();
							org.jpage.actor.User u = (org.jpage.actor.User)userMap.get(actorId);
							out.println("\n<option value=\""+actorId+"\">"+actorId+" ["+u.getName()+"]"+"</option>");
						}
					}
				 %>
			</select>
		</td>
       </tr>
	   <tr>
		<td class="beta" width="12%"  align="center"><b>任务状态</b></td>
        <td class="table-content" colspan="3">
		     <input type="radio" name="actionType" value="running" checked> 待审核
             <input type="radio" name="actionType" value="finished"> 已审核
		</td>
       </tr>
       <tr>
        <td class="table-content" colspan="5" valign="top">
          <div align="center">
            <input type="button" value="确 定" class="button" name="submit252" onclick="javacsript:formSubmit();">          　
            <input type="button" value="返 回" class="button" name="submit253" onclick="javacsript:history.back();">        　
          </div>
        </td>
      </tr>
    </tbody>
   </table>
  </form>
</center>

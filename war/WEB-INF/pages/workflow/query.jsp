<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<script language="javascript" src="<%=request.getContextPath()%>/workflow/includes/js/fixdate.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/workflow/includes/js/selectdate.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/workflow/includes/js/calendar/skins/aqua/theme.css"  rel="stylesheet"/>
<script src="<%=request.getContextPath()%>/workflow/includes/js/calendar/calendar.js" language="javascript"></script>
<script src="<%=request.getContextPath()%>/workflow/includes/js/calendar/lang/calendar-en.js" language="javascript"></script>
<script src="<%=request.getContextPath()%>/workflow/includes/js/calendar/calendar-setup.js" language="javascript"></script>

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
	<img src="<%=request.getContextPath()%>/workflow/images/title.gif" alt="流程查询"> 流程查询
	</span><br><br>
</center>
<form name="typeForm" method="post" action="<%=request.getContextPath()%>/workflow/processMonitorController.jspa">
<input type="hidden" name="method" value="businessInstances" >
<table class="table-border" align="center" cellpadding="4" cellspacing="1" width="90%">
      <tbody>
       <tr>
        <td class="beta" width="12%"  align="center"><b>主题</b></td>
        <td class="table-content" width="38%">
		<input type="text" name="query_title" size="40" class="line">
		</td>
		<td class="beta" width="12%"  align="center"><b>代码值</b></td>
        <td class="table-content" width="38%">
		<input type="text" name="query_businessValue" size="40" class="line">
		</td>
       </tr>
	   <tr>
        <td class="beta" width="12%"  align="center"><b>业务状态码</b></td>
        <td class="table-content" width="38%">
		<input type="text" name="query_businessStatus" size="40" class="line"
		       onKeyPress="javascript:return integerInputFilter();">
		</td>
		<td class="beta" width="12%"  align="center"><b>流程状态码</b></td>
        <td class="table-content" width="38%">
		<input type="text" name="query_wfStatus" size="40" class="line"
		       onKeyPress="javascript:return integerInputFilter();">
		</td>
       </tr>
	   <tr>
        <td class="beta" width="12%"  align="center"><b>流程名称</b></td>
        <td class="table-content" width="38%">
		 <select name="query_processName" class="smallselect">
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
		<td class="beta" width="12%"  align="center"><b>流程实例编号</b></td>
        <td class="table-content" width="38%">
		<input type="text" name="query_processInstanceId" size="40" class="line"
		       onKeyPress="javascript:return integerInputFilter();">
		</td>
       </tr>
	   <tr>
        <td class="beta" width="12%"  align="center"><b>启动者</b></td>
        <td class="table-content" width="38%">
		    <select name="query_requesterId" class="smallselect">
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
		<td class="beta" width="12%"  align="center"><b>流程状态</b></td>
        <td class="table-content" width="38%">
		    <select name="query_running" class="smallselect">
			    <option value="">----请选择----</option>
			    <option value="1">进行中</option>
				<option value="0">已完成</option>
			</select>
		</td>
       </tr>
	   <tr>
        <td class="beta" width="12%"  align="center"><b>启动日期</b></td>
        <td class="table-content" width="38%">
		 <input id="query_startDate_start" name="query_startDate_start" size="10" type="text" class="line" value="" />&nbsp;
		 <img src="images/calendar.gif" id="f_trigger_1" style="cursor: pointer; border: 1px solid red;"/>
		 <input id="query_startDate_end" name="query_startDate_end" size="10" type="text" class="line" value="" />&nbsp;
		 <img src="images/calendar.gif" id="f_trigger_2" style="cursor: pointer; border: 1px solid red;"/>
		</td>
		<td class="beta" width="12%"  align="center"><b>完成日期</b></td>
        <td class="table-content" width="38%">
         <input id="query_endDate_start" name="query_endDate_start" size="10" type="text" class="line" value="" />&nbsp;
		 <img src="images/calendar.gif" id="f_trigger_3" style="cursor: pointer; border: 1px solid red;"/>
		 <input id="query_endDate_end" name="query_endDate_end" size="10" type="text" class="line" value="" />&nbsp;
		 <img src="images/calendar.gif" id="f_trigger_4" style="cursor: pointer; border: 1px solid red;"/> 
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

<script type="text/javascript">

Calendar.setup({
			inputField     :    "query_startDate_start",     
			ifFormat       :    "%Y-%m-%d",      
			button         :    "f_trigger_1",  
			align          :    "Bl",          
			singleClick    :    true,
			showsTime      :    false
	});
	
Calendar.setup({
			inputField     :    "query_startDate_end",     
			ifFormat       :    "%Y-%m-%d",      
			button         :    "f_trigger_2",  
			align          :    "Bl",          
			singleClick    :    true,
			showsTime      :    false
	});

Calendar.setup({
			inputField     :    "query_endDate_start",     
			ifFormat       :    "%Y-%m-%d",      
			button         :    "f_trigger_3",  
			align          :    "Bl",          
			singleClick    :    true,
			showsTime      :    false
	});

Calendar.setup({
			inputField     :    "query_endDate_end",     
			ifFormat       :    "%Y-%m-%d",      
			button         :    "f_trigger_4",  
			align          :    "Bl",          
			singleClick    :    true,
			showsTime      :    false
	});
</script> 
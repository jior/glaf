<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.base.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.job.*"%>
<%@ page import="com.glaf.core.query.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.dts.domain.*"%>
<%@ page import="com.glaf.dts.query.*"%>
<%@ page import="com.glaf.dts.service.*"%>
<%
	String contextPath = request.getContextPath();
	Scheduler scheduler = null;
	String taskId = request.getParameter("taskId");
	if(taskId != null){
		ISysSchedulerService sysSchedulerService = ContextFactory.getBean("sysSchedulerService");
		scheduler = sysSchedulerService.getSchedulerByTaskId(taskId);
		if (scheduler != null) {
			pageContext.setAttribute("scheduler", scheduler);
		}
	}

    ITableDefinitionService tableDefinitionService =  ContextFactory.getBean("tableDefinitionService");
    TableDefinitionQuery query = new TableDefinitionQuery();
	query.type(com.glaf.dts.util.Constants.DTS_TASK_TYPE);
	List<TableDefinition> tables = tableDefinitionService.getTableDefinitionsByQueryCriteria(0,1000,query);
%>
<!DOCTYPE html>
<html>
<head>
<title>任务调度管理</title>

<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<link type="text/css"
	href="<%=request.getContextPath()%>/scripts/calendar/skins/aqua/theme.css"
	rel="stylesheet" />
<script src="<%=request.getContextPath()%>/scripts/calendar/calendar.js"
	language="javascript"></script>
<script
	src="<%=request.getContextPath()%>/scripts/calendar/lang/calendar-en.js"
	language="javascript"></script>
<script
	src="<%=request.getContextPath()%>/scripts/calendar/calendar-setup.js"
	language="javascript"></script>

 <script language="javascript">
  String.prototype.trim = function() {
     return this.replace(/(^\s*)|(\s*$)/g, "");
  }

 function chooseTable(){
	 var tableName = document.getElementById("tableName").value;
	 if(tableName != ""){
	   document.getElementById("attribute").value=tableName;
	 }
 }

  function switchTbl(){
	      var jobClass = document.getElementById("jobClass").value;
		  var obj = document.getElementById("attribute_block");
	       if("com.glaf.dts.job.MxTransformTableJob" == jobClass){
                 obj.style.display = 'block';
		   }else{
			    obj.style.display = 'none';
		   }
  }

 function submitRequest() {
		var taskName = document.iForm.taskName.value.trim();
		var title = document.iForm.title.value.trim();

		if(taskName == "")	 {
			alert("任务名称不能为空！");
			document.iForm.taskName.focus();
			return;
		 }

		 if(title == "")	 {
			alert("任务主题不能为空！");
			document.iForm.title.focus();
			return;
		 }

              var params = jQuery("#iForm").formSerialize();
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/dts/scheduler/save?q=1',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   alert('操作成功完成！');
				   }
			 });
 }

</script>
</head>
<body style="padding-left:20px;padding-right:20px">
<center>
<br> 
<form id="iForm" name="iForm" method="post" class="x-form"
	action="<%=request.getContextPath()%>/rs/dts/scheduler/save"><input
	type="hidden" name="status" value="0"> <input type="hidden"
	id="method" name="method" value="save"> <c:if
	test="${not empty scheduler.taskId}">
	<input type="hidden" name="taskId"
		value="${scheduler.taskId}">
</c:if>
<input type="hidden" id="attribute" name="attribute" value="${scheduler.attribute}
<div class="content-block" style="width: 685px;"> 
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="任务调度信息">&nbsp;任务调度信息</div>
<br>
<table border="0" cellpadding="0" cellspacing="0" width="95%">
	<tr>
		<td align="left" width="25%" height="27">名称&nbsp;*</td>
		<td align="left" height="27">
		<input name="taskName" size="50" maxlength="50"
			type="text" class="input-xlarge span5 x-text" value="${scheduler.taskName}">
		</td>
	</tr>

	<tr>
		<td align="left" width="25%" height="27">主题&nbsp;*</td>
		<td align="left" height="27">
		<input name="title" size="50" type="text" class="input-xlarge span5 x-text"
			maxlength="255" value="${scheduler.title}">
		</td>
	</tr>

	<tr>
		<td align="left" width="25%" height="27">内容&nbsp;&nbsp;</td>
		<td align="left" height="27">
		<textarea name="content" rows="8" cols="52"
			class="input-xlarge span5 x-textarea">${scheduler.content}</textarea>
		</td>
	</tr>

	<tr>
		<td align="left" width="25%" height="27">任务类名&nbsp;</td>
		<td align="left" height="27"> 
		      <select id="jobClass" name="jobClass" onchange="switchTbl();" >
		      <%
			   Properties props =  JobProperties.getProperties();
			   Enumeration<?> e = props.keys();
			   while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = props.getProperty(key);
			  %>
				<option value="<%=key%>"><%=value%></option>
			  <%}%>
		      </select>
			  <script type="text/javascript">
                      document.getElementById("jobClass").value="${scheduler.jobClass}";
			  </script>
			  &nbsp; &nbsp; 
			  <span id="attribute_block" style="display:none;">
				 表名&nbsp; 
					  <select id="tableName" name="tableName"  onchange="chooseTable();">
					   <%for(TableDefinition table: tables){%>
						<option value="{'tableName':'<%=table.getTableName()%>'}"><%=table.getTitle() != null ? table.getTitle() +"["+table.getTableName()+"]" : table.getTableName()%></option>
						<%}%>
					  </select>
					  <script type="text/javascript">
							  document.getElementById("tableName").value='${scheduler.attribute}';
					  </script>
			</span>
		</td>
	</tr>

    <tr>
		<td align="left" width="25%" height="27">时间表达式&nbsp;</td>
		<td align="left" height="27">
		<input name="expression" size="20" type="text" class="input-xlarge span3 x-text" maxlength="50"
			value="${scheduler.expression}">
		<br />示例：每周一到周五凌晨5点执行一次
		<br />（0 0 5 ? * MON-FRI）
		<br />&nbsp;(可不填,可以参考<a href="<%=request.getContextPath()%>/quartz.txt">quartz</a>文件)</td>
	</tr>

	<tr>
		<td align="left" width="25%" height="27">开始日期&nbsp;&nbsp;</td>
		<td align="left" height="27">
		<input id="startDate" name="startDate" size="15"
			type="text"  class="input-medium x-text" required="true"
			value="<fmt:formatDate value="${scheduler.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />&nbsp;
		<img src="<%=request.getContextPath()%>/images/calendar.png"
			id="f_trigger_1" style="cursor: pointer; border: 1px solid red;" />
		</td>
	</tr>

	<tr>
		<td align="left" width="25%" height="27">结束日期&nbsp;&nbsp;</td>
		<td align="left" height="27">
		<input id="endDate" name="endDate" size="15"
			type="text"   class="input-medium x-text" required="true"
			value="<fmt:formatDate value="${scheduler.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />&nbsp;
		<img src="<%=request.getContextPath()%>/images/calendar.png"
			id="f_trigger_2" style="cursor: pointer; border: 1px solid red;" />
		</td>
	</tr>

	

	<tr>
		<td align="left" width="25%" height="27">重复次数&nbsp;</td>
		<td align="left" height="27">
		<input name="repeatCount" size="5" type="text"
			class="input-mini x-text" maxlength="255"
			value="${scheduler.repeatCount}"
			onKeyPress="return check_integer(this);">
		&nbsp;(-1代表不限制重复次数)</td>
	</tr>

	<tr>
		<td align="left" width="25%" height="27">间隔时间&nbsp;</td>
		<td align="left" height="27">
		<input name="repeatInterval" type="text"
			class="input-mini x-text" maxlength="6" size="5"
			value="${scheduler.repeatInterval}"
			onKeyPress="return check_integer(this);">
			 &nbsp;
		(以秒计算，必须是大于0的整数)</td>
	</tr>

	<tr>
		<td align="left" width="25%" height="27">是否自动启动&nbsp;</td>
		<td align="left" height="27"><c:choose>
			<c:when test="${scheduler.autoStartup}">
				<input type="radio" name="autoStartup" value="true" checked>是
			 <input type="radio" name="autoStartup" value="false">否
		</c:when>
			<c:otherwise>
				<input type="radio" name="autoStartup" value="true">是
			 <input type="radio" name="autoStartup" value="false" checked>否
        </c:otherwise>
		</c:choose></td>
	</tr>
</table>
 

<div align="center"><br />

<input type="button" class="btn btn-primary" value="确定"
	onclick="javascript:submitRequest(this.form);" />  
<input type="button" value="返回"
	name="back" class="btn" onclick="javascript:history.back();" /> <br />
<br />
</div>

</div>
</form>

<script language="javascript">

 Calendar.setup({
			inputField     :    "startDate",     // id of the input field
			ifFormat       :    "%Y-%m-%d %H:%M:%S",      // format of the input field
			button         :    "f_trigger_1",  // trigger for the calendar (button ID)
			align          :    "Bl",           // alignment (defaults to "Bl")
			singleClick    :    true,
			showsTime      :    true
	});


 Calendar.setup({
			inputField     :    "endDate",     // id of the input field
			ifFormat       :    "%Y-%m-%d %H:%M:%S",      // format of the input field
			button         :    "f_trigger_2",  // trigger for the calendar (button ID)
			align          :    "Bl",           // alignment (defaults to "Bl")
			singleClick    :    true,
			showsTime      :    true
	});


</script></center>
</html>
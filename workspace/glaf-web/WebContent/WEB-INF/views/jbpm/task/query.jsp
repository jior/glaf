<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<title>流程任务</title> 
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<link type="text/css"
	href="<%=request.getContextPath()%>/scripts/calendar/skins/aqua/theme.css"
	rel="stylesheet" />
<script src="<%=request.getContextPath()%>/scripts/calendar/calendar.js"
	language="javascript"></script>
<script
	src="<%=request.getContextPath()%>/scripts/calendar/lang/calendar-zh.js"
	language="javascript"></script>
<script
	src="<%=request.getContextPath()%>/scripts/calendar/calendar-setup.js"
	language="javascript"></script>
<br>
<center>
<div class="content-block" style="width: 95%;"><br>
<div class="x_content_title" style="width: 95%"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt=" 任务实例查询"> 任务实例查询</div>
<br />
<form id="iForm" name="iForm" method="post" class="x-form"
	action="<%=request.getContextPath()%>/mx/jbpm/task?q=1">
<table width="95%" border="0" cellspacing="1" cellpadding="1"
	align="center" class="x-table-shadow">
	<tr>
		<td width='15%' height="27" noWrap align="left">流程编号</td>
		<td width='35%' height="27" noWrap align="left"><input id="processName"
			name="processName" type="text" size="20" class="input-medium span3" /></td>
		<td width='15%' noWrap align="left">流程名称</td>
		<td width='35%' noWrap align="left"><input id="processDescription"
			name="processDescription" type="text" size="20" class="input-medium span3" /></td>
	</tr>

	<tr>
		<td width='15%' height="27" noWrap align="left">流程实例编号</td>
		<td width='35%' height="27" noWrap align="left"><input id="processInstanceId"
			name="processInstanceId" type="text" size="20" class="input-medium span3"
			dataType="INTEGER" onKeyPress="return check_integer(this);" /></td>
		<td width='15%' noWrap align="left">单据编号</td>
		<td width='35%' noWrap align="left"><input id="businessKey"
			name="businessKey" type="text" size="20" class="input-medium span3" /></td>
	</tr>

	<tr>
		<td width='15%' height="27" noWrap align="left">执行人</td>
		<td width='35%' height="27" noWrap align="left"><input id="actorId"
			name="actorId" type="text" size="20" class="input-medium span3" /></td>
		<td width='15%' noWrap align="left">任务名称</td>
		<td width='35%' noWrap align="left"><input id="taskDescription"
			name="taskDescription" type="text" size="20" class="input-medium span3" /></td>
	</tr>

	<tr>
		<td width='15%' height="27" noWrap align="left">开始日期</td>
		<td width='35%' height="27" noWrap align="left">
		<input id="afterTaskCreateDate"
			name="afterTaskCreateDate" type="text" size="20" dataType="DATE"
			class="span2" readonly="true" /> 
	<script type="text/javascript">
		try{
			Calendar.setup({
			inputField     :    "afterTaskCreateDate",
			ifFormat       :    "%Y-%m-%d",
			button         :    "afterTaskCreateDate",
			align          :    "Bl",
			singleClick    :    true,
			showsTime      :    false
		  });
		}catch(exe){
	   }
	</script>
 ~ 
<input id="beforeTaskCreateDate" name="beforeTaskCreateDate"
			type="text" size="20" dataType="DATE" class="span2" readonly="true" />
		<script type="text/javascript">
    try{
		Calendar.setup({
		inputField     :    "beforeTaskCreateDate",
		ifFormat       :    "%Y-%m-%d",
		button         :    "beforeTaskCreateDate",
		align          :    "Bl",
		singleClick    :    true,
		showsTime      :    false
	  });
    }catch(exe){
   }
</script>
</td>
		<td width='15%' noWrap align="left">结束日期</td>
		<td width='35%' noWrap align="left">
		<input id="afterTaskEndDate"
			name="afterTaskEndDate" type="text" size="20" dataType="DATE"
			class="span2" readonly="true" /> 
	<script type="text/javascript">
		try{
			Calendar.setup({
			inputField     :    "afterTaskEndDate",
			ifFormat       :    "%Y-%m-%d",
			button         :    "afterTaskEndDate",
			align          :    "Bl",
			singleClick    :    true,
			showsTime      :    false
		  });
		}catch(exe){
	   }
	</script> &nbsp;
<input id="beforeTaskEndDate" name="beforeTaskEndDate"
			type="text" size="20" dataType="DATE" class="span2" readonly="true" />
		<script type="text/javascript">
    try{
		Calendar.setup({
		inputField     :    "beforeTaskEndDate",
		ifFormat       :    "%Y-%m-%d",
		button         :    "beforeTaskEndDate",
		align          :    "Bl",
		singleClick    :    true,
		showsTime      :    false
	  });
    }catch(exe){
   }
</script>
		</td>
	</tr>
</table>


<div id="x-toolbar" align="center"><br />
<input type="submit" name="submit" value="搜索" class="btn"> <input
	type="button" name="button" value="返回 " class="btn"
	onclick="javascript:history.back();"></div>

</form>
</div>
</center>
<%@ include file="/WEB-INF/views/tm/footer.jsp"%>
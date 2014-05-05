<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<title>流程定义列表</title> 
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
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
</head>
<body>
<br>
<center>
<div class="content-block" style="width: 95%;"><br>
<div class="x_content_title" style="width: 95%"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt=" 流程实例查询"> 流程实例查询</div>
<br />
<form id="iForm" name="iForm" method="post" class="x-form"
	action="<%=request.getContextPath()%>/mx/jbpm/monitor/processInstances">
<div style="width: 95%" align="center">

<table width="90%" border="0" cellspacing="1" cellpadding="1"
	align="center" class="x-table-shadow">
	<tr>
		<td width='15%' height="27" align="left" noWrap>流程编号</td>
		<td width='35%' height="27" align="left" noWrap><input id="processName"
			name="processName" type="text" size="20" class="input-medium span3" /></td>
		<td width='15%' align="left" noWrap>流程名称</td>
		<td width='35%' align="left" noWrap><input id="processDescription"
			name="processDescription" type="text" size="20" class="input-medium span3" /></td>
	</tr>
	<tr>
		<td width='15%' height="27" align="left" noWrap>流程实例编号</td>
		<td width='35%' height="27" align="left" noWrap><input id="processInstanceId"
			name="processInstanceId" type="text" size="20" class="input-medium span3"
			dataType="INTEGER" onKeyPress="return check_integer(this);" /></td>
		<td width='15%' align="left" noWrap>单据编号</td>
		<td width='35%' align="left" noWrap><input id="businessKey"
			name="businessKey" type="text" size="20" class="input-medium span3" /></td>
	</tr>
	<tr>
		<td width='15%' height="27" align="left" noWrap>启动日期</td>
		<td width='35%' height="27" align="left" noWrap>
		<input id="afterProcessStartDate"
			name="afterProcessStartDate" type="text" size="20" dataType="DATE"
			class="span2" readonly="true" /> 
		<script type="text/javascript">
		try{
			Calendar.setup({
			inputField     :    "afterProcessStartDate",
			ifFormat       :    "%Y-%m-%d",
			button         :    "afterProcessStartDate",
			align          :    "Bl",
			singleClick    :    true,
			showsTime      :    false
		  });
		}catch(exe){
	   }
	</script>
     ~
   <input id="beforeProcessStartDate"
			name="beforeProcessStartDate" type="text" size="20" dataType="DATE"
			class="span2" readonly="true" /> <script type="text/javascript">
    try{
		Calendar.setup({
		inputField     :    "beforeProcessStartDate",
		ifFormat       :    "%Y-%m-%d",
		button         :    "beforeProcessStartDate",
		align          :    "Bl",
		singleClick    :    true,
		showsTime      :    false
	  });
    }catch(exe){
   }
</script>
</td>
		<td width='15%' align="left" noWrap>结束日期</td>
		<td width='35%' align="left" noWrap>
	<input id="afterProcessEndDate"
			name="afterProcessEndDate" type="text" size="20" dataType="DATE"
			class="span2" readonly="true" /> 
		<script type="text/javascript">
		try{
			Calendar.setup({
			inputField     :    "afterProcessEndDate",
			ifFormat       :    "%Y-%m-%d",
			button         :    "afterProcessEndDate",
			align          :    "Bl",
			singleClick    :    true,
			showsTime      :    false
		  });
		}catch(exe){
	   }
	</script>
    ~
   <input id="beforeProcessEndDate"
			name="beforeProcessEndDate" type="text" size="20" dataType="DATE"
			class="span2" readonly="true" /> <script type="text/javascript">
    try{
		Calendar.setup({
		inputField     :    "beforeProcessEndDate",
		ifFormat       :    "%Y-%m-%d",
		button         :    "beforeProcessEndDate",
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
<br />
<input type="submit" name="submit" value="搜索" class="btn"> <input
	type="button" name="button" value="返回 " class="btn"
	onclick="javascript:history.back();"> <br />
<br />
</div>
</div>
</form>
</div>
</center>
<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.jbpm.graph.def.*"%>
<%@ page import="org.jbpm.graph.exe.*"%>
<%@ page import="org.jbpm.taskmgmt.def.*"%>
<%@ page import="com.glaf.core.identity.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.jbpm.util.*"%>
<%@ page import="com.glaf.jbpm.model.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%
	int sortNo = 1;
    List array = new ArrayList();
	//MxTodoHelper todoHelper = new MxTodoHelper();
	Map userMap = MxIdentityFactory.getUserMap();
	List rows = (List)request.getAttribute("taskItems");
    if(rows != null && rows.size() > 0){
		int start = 1;
		Iterator iterator = rows.iterator();
		while(iterator.hasNext()){
            com.glaf.jbpm.model.TaskItem taskItem = (com.glaf.jbpm.model.TaskItem)iterator.next();
	        if("task55".equals(taskItem.getTaskName()) || 
				"task555".equals(taskItem.getTaskName()) || 
				"task5555".equals(taskItem.getTaskName())){
		        continue;
	   }
	  Map row = new HashMap();   
	  row.put("id", String.valueOf(taskItem.getTaskInstanceId()));
	  row.put("taskInstanceId", String.valueOf(taskItem.getTaskInstanceId()));
              row.put("processInstanceId", String.valueOf(taskItem.getProcessInstanceId()));
	  row.put("processName", taskItem.getProcessName());
	  row.put("processDescription", taskItem.getProcessDescription());
	  row.put("taskName", taskItem.getTaskName());
	  row.put("taskDescription", taskItem.getTaskDescription());
	  row.put("actorId", taskItem.getActorId());
	  row.put("rowId", taskItem.getRowId());
	  row.put("sortNo", new Integer(++start));
              row.put("createDate", DateUtils.getDateTime(taskItem.getCreateDate()));
	  if(taskItem.getStartDate() != null){
		  row.put("startDate", DateUtils.getDate(taskItem.getStartDate()));
	  }
	  if(taskItem.getEndDate() != null){
		  row.put("endDate", DateUtils.getDate(taskItem.getEndDate()));
	  }
 
  /**
      try{
		 double pastDue = todoHelper.pastDue(taskItem.getProcessName(), taskItem.getTaskName(), taskItem.getCreateDate());
		 if(pastDue >0 ){
	       row.put("pastDue", new Double(pastDue));
		 }

		 double limitDay = todoHelper.getLimitDay(taskItem.getProcessName(), taskItem.getTaskName());
		 if(limitDay > 0 ){
	      row.put("limitDay", new Double(limitDay));
		 }
		 if(pastDue > 0 &&  limitDay >0 ){
	      row.put("x_pastDue", "1");
		 }
	}catch(Exception ex){
	}*/
               
	 if(StringUtils.isNotEmpty(taskItem.getActorId())){
		User user = (User)userMap.get(taskItem.getActorId());		
	     if(user != null){
            row.put("actorName", user.getName());
		  } else {
	        row.put("actorName", taskItem.getActorId());
		 }
	 }
	 
	  array.add(row);   
		}
	}
%>
<!DOCTYPE html>
<html>
<title>任务列表</title> 
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<script language="javascript">
 function query(){
    location.href="<%=request.getContextPath()%>/mx/jbpm/task/query";
}
</script>
</head>
<body style="padding-left:20px;padding-right:20px">
<center><br>
<div class="x_content_title" style="width: 98%"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="流程任务信息">&nbsp;流程任务信息</div>
<div style="width: 98%" height="28" align="right"><input
	type="button" value="查询" name="query" class="btn"
	onclick="javascript:query();"></div>
<br>
<form name="iForm" method="post"
	action="<%=request.getContextPath()%>/mx/jbpm/task?method=list">
<input type="hidden" name="processDefinitionId"
	value="${processDefinitionId}"> <input
	type="hidden" name="processName"
	value="${processName}"> <input
	type="hidden" name="actionType" value="${actionType}">
<table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="98%">
	<thead>
		<tr class="x-title">
			<td align="center" noWrap>序号</td>
			<!-- <td  align="center" noWrap>流程实例编号</td>
			<td  align="center" noWrap>单据编号</td>
			<td  align="center" noWrap>任务编号</td> -->
			<td align="center" noWrap>流程名称</td>
			<td align="center" noWrap>任务名称</td>
			<td align="center" noWrap>执行人</td>
			<td align="center" noWrap>开始日期</td>
			<td align="center" noWrap>期限</td>
			<td align="center" noWrap>超时</td>
			<td align="center" noWrap>功能键</td>
		</tr>
	</thead>
	<tbody>
		<%
		 Iterator iter = array.iterator();
		 while(iter.hasNext()){
			 Map row = (Map)iter.next();
		 %>
		<tr onmouseover="this.className='x-row-over';"
			onmouseout="this.className='x-row-out';" class="x-content">
			<td align="center" noWrap><%=sortNo++%></td>
			<!-- <td  align="left" noWrap>
					&nbsp;<%=ParamUtils.getString(row, "processInstanceId","")%>
				</td>
				<td  align="left" noWrap>
				&nbsp;<%=ParamUtils.getString(row, "rowId","")%>
				</td>
				<td  align="left" noWrap>
				&nbsp;<%=ParamUtils.getString(row, "taskInstanceId","")%>
				</td> -->
			<td align="left" noWrap>&nbsp; <%=ParamUtils.getString(row, "processDescription","")%>
			</td>
			<td align="left" noWrap>&nbsp;<%=ParamUtils.getString(row, "taskDescription","")%>
			</td>
			<td align="left" noWrap>&nbsp;<%=ParamUtils.getString(row, "actorName","")%>
			[<%=ParamUtils.getString(row, "actorId","")%>]</td>
			<td align="center" noWrap>&nbsp;<%=ParamUtils.getString(row, "createDate","")%>
			</td>
			<td align="center" noWrap>&nbsp;<%=ParamUtils.getDouble(row, "limitDay")%>天

			</td>
			<td align="center" noWrap>&nbsp; <%if(StringUtils.isNotEmpty(ParamUtils.getString(row, "x_pastDue"))){%>
			<font color="red"><b><%=ParamUtils.getDouble(row, "pastDue")%></b>天</font>
			<%}else{%> <%=ParamUtils.getDouble(row, "pastDue")%>天 <%}%>
			</td>
			<td align="center" noWrap>&nbsp;<a
				href="<%=request.getContextPath()%>/mx/jbpm/task/task?processInstanceId=<%=ParamUtils.getString(row, "processInstanceId","")%>"
				target="_blank"><img
				src="<%=request.getContextPath()%>/images/view.gif" border="0"
				title="查看流程任务信息">查看</a></td>
		</tr>
		<%}%>
	</tbody>
</table>
</form>
</center>


<%@ include file="/WEB-INF/views/tm/footer.jsp"%>
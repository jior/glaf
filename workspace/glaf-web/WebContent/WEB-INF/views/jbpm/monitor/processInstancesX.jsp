<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.jbpm.graph.def.*"%>
<%@ page import="org.jbpm.graph.exe.*"%>
<%@ page import="org.jbpm.taskmgmt.def.*"%>
<%@ page import="com.glaf.base.todo.*"%>
<%@ page import="com.glaf.base.service.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.jbpm.util.*"%>
<%@ page import="com.glaf.jbpm.model.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%
	List array = new ArrayList();
    Paging jpage = (Paging)request.getAttribute("jpage");
	if(jpage == null) {
	  jpage = Paging.EMPTY_PAGE;
    }
	int sortNo = (jpage.getCurrentPage()-1) * jpage.getPageSize() + 1;
	request.setAttribute("imagePath", request.getContextPath()+"/images/go.gif");
	ITodoService todoService = (ITodoService) ContextFactory.getBean("todoService");
    String processName = (String)request.getAttribute("processName");
	Map taskMap = (Map) request.getAttribute("taskMap");
	Map userMap = (Map) request.getAttribute("userMap");
	List taskItems = (List) request.getAttribute("taskItems");
	List rows = jpage.getRows();
    if(rows != null && rows.size() > 0){
		int start = (jpage.getCurrentPage()-1) * jpage.getPageSize();
		Iterator iterator = rows.iterator();
		while(iterator.hasNext()){
              ProcessInstance pi = (ProcessInstance)iterator.next();
			  Map row = new HashMap();   
			  row.put("id", String.valueOf(pi.getId()));
			  row.put("sortNo", new Integer(++start));
					  row.put("startDate", DateUtils.getDate(pi.getStart()));
			  if(pi.getEnd() != null){
				  row.put("endDate", DateUtils.getDate(pi.getEnd()));
			  }
              row.put("version", new Integer(pi.getVersion()));
	          array.add(row);   
		}
	}
%>
<!DOCTYPE html>
<html>
<title>流程定义列表</title> 
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<script language="javascript">
 function query(){
    location.href="<%=request.getContextPath()%>/mx/jbpm/monitor?method=query";
}
</script>
</head>
<body style="padding-left:20px;padding-right:20px">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="流程实例信息">&nbsp;流程实例信息</div>
<div style="width: 90%" height="30" align="right"> 
<input type="button"
	name="button" value="返回 " class="btn"
	onclick="javascript:history.back();"></div>
<div style="width: 90%" height="30" align="left">说明：S-开始日期 E-结束日期
P-超期天数</div>
<br>
<form name="iForm" method="post"
	action="<%=request.getContextPath()%>/mx/jbpm/monitor/processInstancesX">
<input type="hidden" name="processDefinitionId"
	value="${processDefinitionId}"> <input
	type="hidden" name="processName"
	value="${processName}"> <input
	type="hidden" name="actionType"
	value="<%=RequestUtils.getString(request, "actionType", "")%>">
<table align="center"  class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="90%">
	<thead>
		<tr class="x-paging">
			<td colspan="20" align="right"><glaf:paging form="iForm" /></td>
		</tr>
		<tr class="x-title">
			<td align="center" noWrap>序号</td>
			<td align="center" noWrap>流程实例编号</td>
			<td align="center" noWrap>开始日期</td>
			<%
		   if(processName != null && taskMap != null && taskMap.size() >0){
			   Iterator iter01 = taskMap.values().iterator();
			   while(iter01.hasNext()){
			       Task task = (Task)iter01.next();
				    if(StringUtils.equals(task.getName(), "task55") ||
					   StringUtils.equals(task.getName(), "task555") ||
					   StringUtils.equals(task.getName(), "task5555") ){
					    continue;
					}
				    String code01 = processName+"_"+task.getName();
					Todo todo = todoService.getTodo(code01);
					if (todo != null && todo.getLimitDay() > 0) {

					}
			%>
			<td align="center" noWrap><%=task.getDescription() != null ? task.getDescription(): task.getName()%></td>
			<%  }
		       }
		   %>
			<td align="center" noWrap>完成日期</td>
			<td align="center" noWrap>功能键</td>
		</tr>
	</thead>
	<tbody>
		<%
		 Iterator iter = array.iterator();
		 while(iter.hasNext()){
			 Map row = (Map)iter.next();
			 Long pid = ParamUtils.getLong(row, "id");
		 %>
		<tr onmouseover="this.className='x-row-over';"
			onmouseout="this.className='x-row-out';" class="x-content">
			<td align="center" noWrap><%=sortNo++%></td>
			<td align="left" noWrap>&nbsp; <%=ParamUtils.getString(row, "id","")%>
			</td>
			<td align="center" noWrap>&nbsp;<%=ParamUtils.getString(row, "startDate","")%>
			</td>

			<%
				   if(processName != null && taskMap != null && taskMap.size() >0){
					   Iterator iter01 = taskMap.values().iterator();
					   while(iter01.hasNext()){
						   Task task = (Task)iter01.next();
						   if(StringUtils.equals(task.getName(), "task55") ||
							  StringUtils.equals(task.getName(), "task555") ||
							  StringUtils.equals(task.getName(), "task5555") ){
							   continue;
						   }
							   
						    out.println("<td  align=\"left\" valign=\"top\" noWrap>");
							if(taskItems != null && taskItems.size() > 0){
								Iterator iter02 = taskItems.iterator();
                                while(iter02.hasNext()){
                                      com.glaf.jbpm.model.TaskItem ti = (com.glaf.jbpm.model.TaskItem)iter02.next();
									   if(StringUtils.equals(task.getName(), ti.getTaskName()) &&  pid==ti.getProcessInstanceId()){
									       pageContext.setAttribute("ti", ti);
										  
									  %>
			<br>
			${ti.actorName}
			<br>
			S:
			<fmt:formatDate value="${ti.createDate}" pattern="yyyy-MM-dd HH:mm" />
			<br>
			<%
										   if(ti.getPastDueDay() > 0){
                                             out.println("<font color=red>");
									       }
									  %>
			E:
			<fmt:formatDate value="${ti.endDate}" pattern="yyyy-MM-dd HH:mm" />
			<br>
			<c:if test="${ti.pastDueDay > 0 }">
                                       P:${ti.pastDueDay}天<br>
			</c:if>
			<%
										   if(ti.getPastDueDay() > 0){
                                              out.println("</font >");
										   }
										   %>
			<%
										   				
									   }
								}
							}
							out.println("</td>");
					     }
					   }
				   %>
			<td align="center" noWrap>&nbsp; <%=ParamUtils.getString(row, "endDate","")%>
			</td>
			<td align="center" noWrap>&nbsp;<a
				href="<%=request.getContextPath()%>/mx/jbpm/task/task?processInstanceId=<%=ParamUtils.getString(row, "id","")%>&actionType=running"
				target="_blank"><img
				src="<%=request.getContextPath()%>/images/view.gif" border="0"
				title="查看流程任务信息"> 查看</a></td>
		</tr>
		<%}%>
		<tr class="x-paging">
			<td colspan="20" align="right"><glaf:paging form="iForm" /></td>
		</tr>
	</tbody>
</table>
</form>
</center>


<%@ include file="/WEB-INF/views/tm/footer.jsp"%>
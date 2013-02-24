<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.jbpm.graph.def.*"%>
<%@ page import="org.jbpm.graph.exe.*"%>
<%@ page import="org.jbpm.taskmgmt.def.*"%>
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

	Map variables = (Map) request.getAttribute("variables");
	Map userMap = (Map) request.getAttribute("userMap");
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
	  if(variables != null ){
                   Map dataMap = (Map) variables.get(String.valueOf(pi.getId())) ;
	       if(dataMap != null){
                         row.put("rowId", ParamUtils.getString(dataMap, Constant.PROCESS_ROWID, ""));
				 row.put("title", ParamUtils.getString(dataMap, Constant.PROCESS_TITLE, ""));
				 row.put("processName", ParamUtils.getString(dataMap, Constant.PROCESS_NAME, ""));
				 String actorId =  ParamUtils.getString(dataMap, Constant.PROCESS_STARTERID, "");
				 row.put("requesterId", actorId);
				 if(StringUtils.isNotEmpty(actorId)){
					  User user = (User)userMap.get(actorId);		
					 if(user != null){
                                 row.put("requesterName", user.getName());
					 } else {
						  row.put("requesterName", actorId);
					 }
				 }
				 row.put("processDefinitionId", ParamUtils.getString(dataMap, Constant.PROCESS_DEFINITION_ID, ""));
		   }
	  }
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
    location.href="<%=request.getContextPath()%>/mx/jbpm/monitor/query";
}
function ptx(){
	 location.href="<%=request.getContextPath()%>/mx/jbpm/monitor/processInstancesX?processDefinitionId=${processDefinitionId}&processType=<%=RequestUtils.getString(request, "processType", "")%>";
}
</script>
</head>
<body style="padding-left:20px;padding-right:20px">
<center><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="流程实例信息">&nbsp;流程实例信息</div>
<div style="width: 90%" height="30" align="right">
<input
	type="button" value="查询" name="query" class="btn"
	onclick="javascript:query();">  
<input
	type="button" name="button" value="返回 " class="btn"
	onclick="javascript:history.back();"></div>
<br>
<form name="iForm" method="post"
	action="<%=request.getContextPath()%>/mx/jbpm/monitor/processInstances">
<input type="hidden" name="processDefinitionId"
	value="${processDefinitionId}"> <input
	type="hidden" name="processName"
	value="${processName}"> <input
	type="hidden" name="processType"
	value="<%=RequestUtils.getString(request, "processType", "")%>">
<table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="98%">
	<thead>
		<tr class="x-paging">
			<td colspan="20" align="right"><glaf:paging form="iForm" /></td>
		</tr>
		<tr class="x-title">
			<td align="center" noWrap>序号</td>
			<td align="center" noWrap>主题</td>
			<td align="center" noWrap>启动者</td>
			<td align="center" noWrap>开始日期</td>
			<td align="center" noWrap>完成日期</td>
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
			<td align="left" noWrap>&nbsp;<%=ParamUtils.getString(row, "title","")%>
			</td>
			<td align="left" noWrap>&nbsp;<%=ParamUtils.getString(row, "requesterName","")%>
			</td>
			<td align="center" noWrap>&nbsp;<%=ParamUtils.getString(row, "startDate","")%>
			</td>
			<td align="center" noWrap>&nbsp;<font color="green"><b><%=ParamUtils.getString(row, "endDate","")%></b></font>
			</td>
			<td align="center" noWrap>&nbsp;<a
				href="<%=request.getContextPath()%>/mx/jbpm/task/task?processInstanceId=<%=ParamUtils.getString(row, "id","")%>&processType=running"
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
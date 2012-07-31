<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jpage.jbpm.model.*" %>
<%@ page import="org.jpage.jbpm.cmd.*" %>
<%@ page import="org.jpage.jbpm.service.*" %>
<%@ page import="org.jpage.jbpm.datafield.*" %>
<%@ page import="org.jpage.util.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%
       StartProcessCmd cmd = new StartProcessCmd();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String rowId = request.getParameter("rowId");
		if(rowId != null){
			// 审核表的记录ID
			paramMap.put("rowId", rowId);
			// 流程名称（英文的名称）
			paramMap.put("processName", "Test");
			// 工作流控制参数，合法的JSON格式字符串
			String datafields = "{tableName:'TEST'}";
			paramMap.put("json", datafields);
			String processInstanceId = cmd.startProcess("joy", paramMap);
			// 如果流程实例编号不为空，那么已经成功创建流程实例，否则抛出异常
			if (processInstanceId != null) {
				 out.println("<br>processInstanceId="+processInstanceId);
				 out.println("<br>OK!");
			}
		}
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%@ page import="org.jpage.jbpm.model.*" %>
<%@ page import="org.jpage.jbpm.context.*" %>
<%@ page import="org.jpage.jbpm.service.*" %>
<%@ page import="org.jpage.jbpm.datafield.*" %>
<%@ page import="org.jpage.util.*" %>
<%
        ProcessContainer container = ProcessContainer.getContainer();
 		String rowId = request.getParameter("rowId");
		if(rowId != null){
			ProcessContext ctx = new ProcessContext();
			ctx.setRowId(rowId);// 审核表的记录ID
			ctx.setActorId("joy");//用户账户
			ctx.setTitle("单据编号：" + rowId);
			ctx.setProcessName("Test");
             
			DataField df01 = new DataField();
			df01.setName("money");
			df01.setValue(5000000D);
 
			ctx.addDataField(df01);//工作流控制参数

			String processInstanceId = container.startProcess(ctx);
			// 如果流程实例编号不为空，那么已经成功创建流程实例，否则抛出异常
			if (processInstanceId != null) {
				 out.println("<br>processInstanceId="+processInstanceId);
				 out.println("<br>OK!");
			}
		}
%>
<%@ page contentType="text/plain;charset=UTF-8" %><%@ page import="java.util.*" %><%@ page import="org.apache.commons.lang.*" %><%@ page import="com.glaf.activiti.model.*" %><%@ page import="com.glaf.activiti.context.*" %><%@ page import="com.glaf.activiti.container.*" %><%@ page import="com.glaf.activiti.datafield.*" %><%@ page import="com.glaf.core.util.*" %><%
        //http://127.0.0.1:8080/glaf/workflow/activiti/startProcess.jsp?rowId=15
        ProcessContainer container = ProcessContainer.getContainer();
 		String rowId = request.getParameter("rowId");
		System.out.println("#rowId="+rowId);
		if(rowId != null){
			ProcessContext ctx = new ProcessContext();
			ctx.setBusinessKey(rowId);// 审核表的记录ID
			ctx.setActorId("joy");//用户账户
			ctx.setTitle("单据编号：" + rowId);
			ctx.setProcessName("Test");
             
			DataField df01 = new DataField();
			df01.setName("money");
			df01.setValue(5000000D);
 
			ctx.addDataField(df01);//工作流控制参数
            try {
				Object processInstanceId = container.startProcess(ctx);
				// 如果流程实例编号不为空，那么已经成功创建流程实例，否则抛出异常
				if (processInstanceId != null) {
					 System.out.println("{message:\"成功！\"}");
					 out.println("{message:\"成功！\"}");
					 out.flush();
				} else {
					 System.out.println("{message:\"失败！\"}");
					 out.println("{message:\"失败！\"}");
					 out.flush();
				}
			}catch (Exception ex) {
			  ex.printStackTrace();
			  throw new RuntimeException(ex);
		   }
		}
%>
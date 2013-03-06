<%@ page contentType="text/plain;charset=UTF-8" %><%@ page import="java.util.*" %><%@ page import="org.apache.commons.lang.*" %><%@ page import="com.glaf.activiti.model.*" %><%@ page import="com.glaf.activiti.context.*" %><%@ page import="com.glaf.activiti.container.*" %><%@ page import="com.glaf.activiti.datafield.*" %><%@ page import="com.glaf.core.util.*" %><%
        //http://127.0.0.1:8080/glaf/workflow/activiti/completeTask.jsp?processInstanceId=9
        RequestUtils.setRequestParameterToAttribute(request);
        Map params = new HashMap();
		String approve = request.getParameter("approve");
		String actorId = request.getParameter("actorId");
		String opinion = request.getParameter("opinion");
		String processInstanceId = request.getParameter("processInstanceId");
		 
		if(actorId == null){
			actorId = "joy";
		}
		if(approve == null){
			approve = "true";
		}
		 

		DataField datafield = new DataField();
		datafield.setName("approve");
        datafield.setValue(Boolean.valueOf(approve));


		DataField datafield6 = new DataField();
		datafield6.setName("effectiveDate");
        datafield6.setValue(new java.util.Date());

		DataField datafield7 = new DataField();
		datafield7.setName("subscriber");
        datafield7.setValue(Integer.valueOf(1));

		DataField datafield8 = new DataField();
		datafield8.setName("bookDate");
        datafield8.setValue(new java.util.Date());

		
        List dataFields = new ArrayList();
        dataFields.add(datafield);
		dataFields.add(datafield6);
		dataFields.add(datafield7);
		dataFields.add(datafield8);

		if(request.getParameter("name") != null && request.getParameter("value") != null){
		    DataField datafield4 = new DataField();
		    datafield4.setName(request.getParameter("name"));
            datafield4.setValue(request.getParameter("value"));
			dataFields.add(datafield4);
		}

		try {
			if(processInstanceId != null && processInstanceId.length()>0 && actorId != null){
		        ProcessContainer container = ProcessContainer.getContainer();
 				ProcessContext ctx = new ProcessContext();
				ctx.setActorId(actorId);
				ctx.setProcessInstanceId(processInstanceId);
				ctx.setDataFields(dataFields);
				boolean isOK = container.completeTask(ctx);
				if(isOK){
					System.out.println("{message:\"成功！\"}");
					out.println("{message:\"成功！\"}");
				    out.flush();
				} else {
					System.out.println("{message:\"失败！\"}");
					out.println("{message:\"失败！\"}");
				    out.flush();
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
%>
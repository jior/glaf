<%@ page contentType="text/plain;charset=UTF-8" %><%@ page import="java.util.*" %><%@ page import="org.apache.commons.lang.*" %><%@ page import="org.jpage.jbpm.model.*" %><%@ page import="org.jpage.jbpm.context.*" %><%@ page import="org.jpage.jbpm.service.*" %><%@ page import="org.jpage.jbpm.datafield.*" %><%@ page import="org.jpage.util.*" %><%
        RequestUtil.setRequestParameterToAttribute(request);
        Map params = new HashMap();
		String isAgree = request.getParameter("isAgree");
		String actorId = request.getParameter("actorId");
		String opinion = request.getParameter("opinion");
		String processInstanceId = request.getParameter("processInstanceId");
		 
		if(actorId == null){
			actorId = "test";
		}
		if(isAgree == null){
			isAgree = "true";
		}
		 

		DataField datafield = new DataField();
		datafield.setName("isAgree");
        datafield.setValue(isAgree);

		DataField datafield4 = new DataField();
		datafield4.setName(request.getParameter("name"));
        datafield4.setValue(request.getParameter("value"));

		DataField datafield5 = new DataField();
		datafield5.setName(request.getParameter("rowId"));
        datafield5.setValue(request.getParameter("rowValue"));

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
		dataFields.add(datafield4);
		dataFields.add(datafield5);
		dataFields.add(datafield6);
		dataFields.add(datafield7);
		dataFields.add(datafield8);

		try {
			if(processInstanceId != null && processInstanceId.length()>0 && actorId != null){
		        ProcessContainer container = ProcessContainer.getContainer();
 				ProcessContext ctx = new ProcessContext();
				ctx.setActorId(actorId);
				ctx.setProcessInstanceId(processInstanceId);
				ctx.setDataFields(dataFields);
				boolean isOK = container.completeTask(ctx);
				if(isOK){
					out.println("{message:\"成功！\"}");
				    out.flush();
				} else {
					out.println("{message:\"失败！\"}");
				    out.flush();
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
%>
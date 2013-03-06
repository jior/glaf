<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%@ page import="com.glaf.activiti.model.*" %>
<%@ page import="com.glaf.activiti.context.*" %>
<%@ page import="com.glaf.activiti.container.*" %>
<%@ page import="com.glaf.activiti.service.*" %>
<%@ page import="com.glaf.activiti.datafield.*" %>
<%@ page import="com.glaf.core.util.*" %>
<%
        //http://127.0.0.1:8080/glaf/workflow/activiti/endTask.jsp
        RequestUtils.setRequestParameterToAttribute(request);
        Map params = new HashMap();
		String approve = request.getParameter("approve");
		String actorId = request.getParameter("actorId");
		String opinion = request.getParameter("opinion");
		String processInstanceId = request.getParameter("processInstanceId");
		 
		if(actorId == null){
			actorId = "test";
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
			if(processInstanceId != null && processInstanceId.length() > 0 && actorId != null){
		        ProcessContainer container = ProcessContainer.getContainer();
 				ProcessContext ctx = new ProcessContext();
				ctx.setActorId(actorId);
				ctx.setProcessInstanceId(processInstanceId);
				ctx.setDataFields(dataFields);
				boolean isOK = container.completeTask(ctx);
				out.println("isOK="+isOK);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
%>

<script language="javascript">
    function submitForm(approve){
        document.getElementById("approve").value=approve;
		document.iForm.bt01.disabled=true;
		document.iForm.bt02.disabled=true;
        document.iForm.submit();
     }
</script>
<center>
<form name="iForm" method="post" action="">
  <table align="center" class="table-border" cellspacing="1" cellpadding="4" width="90%" nowrap>
    <tr class="table-bar" align="middle" nowrap>
	<td align="center"  nowrap> 
    <input type="hidden" id="approve" name="approve" value=""><br>
	process actorId 
	</td>
	<td align="left"  nowrap> 
	<input type="text" name="actorId" value="<c:out value="${actorId}"/>" size="50">
	</td>
	</tr>
    <tr class="table-bar" align="middle" nowrap>
	<td align="center"  nowrap> processInstanceId 
    </td>
	<td align="left"  nowrap> 
	<input type="text" name="processInstanceId" value="<c:out value="${processInstanceId}"/>" size="50">
	</td>
	<tr class="table-bar" align="middle" nowrap>
	<td align="center"  nowrap> 
	name:</td>
	<td align="left"  nowrap> 
	<input type="text" name="name" value="<c:out value="${name}"/>" size="50">
	</td>
	</tr>
	<tr class="table-bar" align="middle" nowrap>
	<td align="center"  nowrap> 
	value:
	</td>
	<td align="left"  nowrap> 
	<input type="text" name="value" value="<c:out value="${value}"/>" size="50">
	</td>
	</tr>
	<tr class="table-bar" align="middle" nowrap>
	<td align="center"  nowrap> 
	row Id:</td>
	<td align="left"  nowrap> 
	<input type="text" name="rowId" value="<c:out value="${rowId}"/>" size="50">
	</td>
	</tr>
	<tr class="table-bar" align="middle" nowrap>
	<td align="center"  nowrap> 
	row value:</td>
	<td align="left"  nowrap> 
	<input type="text" name="rowValue" value="<c:out value="${rowValue}"/>" size="50">
	</td>
	</tr>
    <tr class="table-bar" align="middle" nowrap>
	<td align="center"  nowrap> 
	opinion:</td>
	<td align="left"  nowrap> 
	 <textarea name="opinion" rows="5" cols="40"></textarea>
	</td>
	</tr>
	<tr class="table-bar" align="middle" nowrap>
	<td align="center" colspan="4"  nowrap> 
	<input type="button" name="bt01" value="通过" onclick="submitForm('true');">
	<input type="button" name="bt02" value="不通过" onclick="submitForm('false');">
	</td>
	</tr>
  </table>
</form>
</center>

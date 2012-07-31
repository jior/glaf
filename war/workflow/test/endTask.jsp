<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ page import="org.jbpm.graph.def.*" %>
<%@ page import="org.jbpm.graph.exe.*" %>
<%@ page import="org.jbpm.db.*" %>
<%@ page import="org.jbpm.*" %>
<%@ page import="org.jpage.jbpm.model.*" %>
<%@ page import="org.jpage.jbpm.cmd.*" %>
<%@ page import="org.jpage.jbpm.service.*" %>
<%@ page import="org.jpage.jbpm.datafield.*" %>
<%@ page import="org.jpage.util.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%
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
			if(processInstanceId != null && processInstanceId.length()>0){
		        CompleteTaskCmd cmd = new CompleteTaskCmd();
				Map<String, Object> paramMap = new HashMap<String, Object>();
				// 流程实例编号，如果没有设置流程实例编号，必须始终任务实例编号
				paramMap.put("processInstanceId", processInstanceId);
				// 任务实例编号，如果没有设置任务实例编号，必须始终流程实例编号
				//paramMap.put("taskInstanceId", "565");
				// 转移路径名称，可选值，如果设置了该值，流程即按该路径转移，否则根据表达式自动选择转出路径
				//paramMap.put("transitionName", "提交下一步审核");
				// 是否通过，通过为true，否决为false
				paramMap.put("isAgree", isAgree);
				// 审核意见
				//paramMap.put("opinion", "同意！");
				// 工作流控制参数，合法的JSON格式字符串
				 
				paramMap.put("dataFields", dataFields);
				boolean isOK = cmd.completeTask(actorId, paramMap);
				out.println("isOK="+isOK);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
%>

<script language="JavaScript">
    function submitForm(isAgree){
        document.getElementById("isAgree").value=isAgree;
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
    <input type="hidden" id="isAgree" name="isAgree" value=""><br>
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

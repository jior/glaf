<%@page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.alibaba.fastjson.*"%>
<%@ page import="org.activiti.engine.repository.*"%>
<%@ page import="com.glaf.core.identity.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.activiti.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>代理人设置</title>
<meta content="text/html; charset=UTF-8" http-equiv="content-type">
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/jquery.treeTable.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/datepicker/config.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/datepicker/lang/zh-cn.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/scripts/datepicker/skin/WdatePicker.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/scripts/datepicker/skin/default/datepicker.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/datepicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery.treeTable.js"></script>
<style>
body {
	font-size: 12px;
}

table {
	font-size: 12px;
}

td {
	font-size: 12px;
}

 
</style>
<script language="javascript">
 var contextPath = "<%=request.getContextPath()%>";
  function checkAll() {
	var checkFlag = document.iForm.checkFlag.value;
    if(checkFlag == 1){
	   document.iForm.checkFlag.value="0";
	   for (var i=0;i<document.iForm.elements.length;i++)
		{
		    var e = document.iForm.elements[i];
			if (e.getAttribute("lck") == "1"){
			e.checked = false;
		  }
		}
	}
	else {
       document.iForm.checkFlag.value="1";
	   for (var i=0;i<document.iForm.elements.length;i++)
		{
		    var e = document.iForm.elements[i];
			if (e.getAttribute("lck") == "1"){
			e.checked = true;
		  }
		}
    }
 }

 function clearRow(prefix){
	  agentIds = document.getElementById(prefix+"_agentIds");
	  if(agentIds !=null ){
		  agentIds.value="";
	  }
	  agentNames = document.getElementById(prefix+"_agentNames");
	  if(agentNames !=null ){
		  agentNames.value="";
	  }
	  startDate = document.getElementById(prefix+"_startDate");
	  if(startDate !=null ){
		  startDate.value="";
	  }
	  endDate = document.getElementById(prefix+"_endDate");
	  if(endDate !=null ){
		  endDate.value="";
	  }
	  locked = document.getElementById(prefix+"_locked");
	  if(locked !=null ){
		  locked.checked=false;
	  }
 }

 function clearRow2(prefix){
	  agentIds = document.getElementById(prefix);
	  if(agentIds !=null ){
		  agentIds.value="";
	  }
	  agentNames = document.getElementById(prefix+"_name");
	  if(agentNames !=null ){
		  agentNames.value="";
	  }
	  startDate = document.getElementById(prefix+"_startDate");
	  if(startDate !=null ){
		  startDate.value="";
	  }
	  endDate = document.getElementById(prefix+"_endDate");
	  if(endDate !=null ){
		  endDate.value="";
	  }
	  locked = document.getElementById(prefix+"_locked");
	  if(locked !=null ){
		  locked.checked=false;
	  }
 }

  function submitRequestX() {
	  document.iForm.submit();
  }

  function submitRequest(){
		if(confirm("确定要保存数据吗？")){
			 var params = jQuery("#iForm").formSerialize();
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/activiti/agent/save',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
						alert('操作成功完成！');
				   }
			 });
		 }
    }

</script>
</head>
<body id="c1" style="padding-left: 20px; padding-right: 20px; padding-top: 20px;">
<div class="x_content_title" align="center" style="width: 90%"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="代理人设置">
代理人设置 <br>
</div>
<div id="doc3">
<div id="bd">
<div id="doc4" class="yui-t2">
<div id="yui-main">
<div>
<form id="iForm" name="iForm" method="post"
	action="<%=request.getContextPath()%>/mx/activiti/agent/save">
<input type="hidden" id="assignFrom" name="assignFrom"
	value="${assignFrom}"> <input type="hidden"
	id="checkFlag" name="checkFlag" value="1">
<div align="left" style="width: 90%"><input type="button"
	value="保存设置" class="btn btn-primary" id="confirm" name="confirm"
	onclick="javacsript:submitRequest();"> <input type="button"
	name="button" value="返回 " class="btn"
	onclick="javascript:history.back();"></div>
<br>
<table class=" table table-striped table-bordered table-condensed treeTable  " width="90%" cellspacing="1" cellpadding="4">
	<thead>
		<tr class="x-title">
			<th width="40%" noWrap>名称</th>
			<th width="15%" noWrap>受托人</th>
			<th width="15%" noWrap>开始日期</th>
			<th width="15%" noWrap>结束日期</th>
			<th width="15%" noWrap><input type="checkbox" name="" checked
				onclick="javascript:checkAll();">启用</th>
		</tr>
	</thead>
	<tbody>
		<tr id="node_0" class="x-node-content">
			<td noWrap>
			<label title="点击展开"><img
				src="<%=request.getContextPath()%>/images/darrow.gif">&nbsp;全局代理</label></td>
			<td noWrap><input type="hidden" id="global_agentIds"
				name="global_agentIds"
				value="<%=RequestUtils.getAttribute(request, "global_agentIds")%>">
			<input type="text" id="global_agentNames" name="global_agentNames"
				value="<%=RequestUtils.getAttribute(request, "global_agentNames")%>"
				onclick="javacsript:selectUser('global_agentIds','global_agentNames');"
				size="15" style="cursor: pointer;" class="input-large" readonly>
			&nbsp; <img
				src="<%=request.getContextPath()%>/images/btn_delete.png"
				style="cursor: pointer;" title="清空"
				onclick="javascript:clearRow('global');"></td>
			<td noWrap><input type="text" id="global_startDate"
				name="global_startDate" style="cursor: pointer;" class="input-medium"
				readonly
				onclick="WdatePicker({skin:'ext',startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				value="<fmt:formatDate value="${global_startDate}" pattern="yyyy-MM-dd HH:mm:ss" />">
			</td>
			<td noWrap><input type="text" id="global_endDate"
				name="global_endDate" style="cursor: pointer;" class="input-medium"
				readonly
				onclick="WdatePicker({skin:'ext',startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				value="<fmt:formatDate value="${global_endDate}" pattern="yyyy-MM-dd HH:mm:ss" />">
			</td>
			<td noWrap align="center">&nbsp; <input type="checkbox"
				id="global_locked" name="global_locked" lck="1"
				<c:if test="${global_locked == 0}">checked</c:if>></td>
		</tr>
<%
   List  processDefinitions = (List)request.getAttribute("processDefinitions");
   Map agentMap = (Map)request.getAttribute("agentMap");
   Map dataMap = (Map)request.getAttribute("dataMap");
   if(processDefinitions != null && processDefinitions.size() > 0){
	   Iterator iter99 = processDefinitions.iterator();
	   while(iter99.hasNext()){
		   ProcessDefinition pd = (ProcessDefinition) iter99.next();
		   pageContext.removeAttribute("startDate");
		   pageContext.removeAttribute("endDate");
		    pageContext.removeAttribute("locked");
		   Date startDate = null;
		   Date endDate = null;
		   if(agentMap != null && agentMap.get(pd.getKey()) != null){
			       Agent agent = (Agent) agentMap.get(pd.getKey()) ;
				   startDate = agent.getStartDate();
				   endDate = agent.getEndDate();
                   pageContext.setAttribute("startDate", startDate);
				   pageContext.setAttribute("endDate", endDate);
				   pageContext.setAttribute("locked", agent.getLocked());
		   }
  %>
		<tr id="node_<%=pd.getKey()%>" class="child-of-node_0 x-node-content">
			<td noWrap>
			<label title="点击展开"><img
				src="<%=request.getContextPath()%>/images/darrow.gif">&nbsp;<%=pd.getName() != null ? pd.getName() : pd.getKey()%></label></td>
			<td><input type="hidden" id="<%=pd.getKey()%>"
				name="<%=pd.getKey()%>"
				value="<%=RequestUtils.getAttribute(request, "x_" + pd.getKey() + "_agentIds")%>">
			<input type="text" id="<%=pd.getKey()%>_name"
				name="<%=pd.getKey()%>_name" style="cursor: pointer;"
				class="input-large" readonly
				value="<%=RequestUtils.getAttribute(request, "x_" + pd.getKey() + "_agentNames")%>"
				onclick="javacsript:selectUser('<%=pd.getKey()%>','<%=pd.getKey()%>_name');"
				size="15"> <%if(StringUtils.isNotEmpty(RequestUtils.getAttribute(request, "x_" + pd.getKey() + "_agentIds"))){%>
			&nbsp; <img
				src="<%=request.getContextPath()%>/images/btn_delete.png"
				style="cursor: pointer;" title="清空"
				onclick="javascript:clearRow2('<%=pd.getKey()%>');"> <%}%>
			</td>
			<td noWrap><input type="text" id="<%=pd.getKey()%>_startDate"
				name="<%=pd.getKey()%>_startDate" style="cursor: pointer;"
				class="input-medium" readonly
				onclick="WdatePicker({skin:'ext',startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd HH:mm:ss" />">
			</td>
			<td noWrap><input type="text" id="<%=pd.getKey()%>_endDate"
				name="<%=pd.getKey()%>_endDate" style="cursor: pointer;"
				class="input-medium" readonly
				onclick="WdatePicker({skin:'ext',startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd HH:mm:ss" />">
			</td>
			<td noWrap align="center">&nbsp; <input type="checkbox"
				id="<%=pd.getKey()%>_locked" name="<%=pd.getKey()%>_locked"
				lck="1" <c:if test="${locked == 0}">checked</c:if>></td>
		</tr>
		<%
		   if(dataMap != null && dataMap.get(pd.getKey()) != null){
		     Collection tasks = (Collection)dataMap.get(pd.getKey());
			 if(tasks != null && tasks.size() > 0) {
				 Iterator iter9 = tasks.iterator();
				 while(iter9.hasNext()){
					 UserTask task = (UserTask) iter9.next();
					 pageContext.removeAttribute("startDate");
					 pageContext.removeAttribute("endDate");
					 pageContext.removeAttribute("locked");
					 if(agentMap != null && agentMap.get(pd.getKey()+"_"+task.getId()) != null){
					     Agent agent = (Agent) agentMap.get(pd.getKey()+"_"+task.getId()) ;
					     startDate = agent.getStartDate();
					     endDate = agent.getEndDate();
					     pageContext.setAttribute("startDate", startDate);
					     pageContext.setAttribute("endDate", endDate);
						 pageContext.setAttribute("locked", agent.getLocked());
			         }
	  %>
		<tr id="node_<%=pd.getKey()%>_<%=task.getId()%>"
			class="child-of-node_<%=pd.getKey()%> x-node-content">
			<td class="tree" noWrap><%=task.getName()!= null ? task.getName(): task.getId()%></td>
			<td noWrap><input type="hidden"
				id="<%=pd.getKey()%>_<%=task.getId()%>"
				name="<%=pd.getKey()%>_<%=task.getId()%>"
				value="<%=RequestUtils.getAttribute(request, "x_" + pd.getKey() + "_"+task.getId()+"_agentIds")%>">
			<input type="text" id="<%=pd.getKey()%>_<%=task.getId()%>_name"
				name="<%=pd.getKey()%>_<%=task.getId()%>_name"
				style="cursor: pointer;" class="input-large" readonly
				value="<%=RequestUtils.getAttribute(request, "x_" + pd.getKey()+ "_"+task.getId() + "_agentNames")%>"
				onclick="javacsript:selectUser('<%=pd.getKey()%>_<%=task.getId()%>','<%=pd.getKey()%>_<%=task.getId()%>_name');"
				size="15"> <%if(StringUtils.isNotEmpty(RequestUtils.getAttribute(request, "x_" + pd.getKey() + "_"+task.getId()+"_agentIds"))){%>
			&nbsp; <img
				src="<%=request.getContextPath()%>/images/btn_delete.png"
				style="cursor: pointer;" title="清空"
				onclick="javascript:clearRow2('<%=pd.getKey()%>_<%=task.getId()%>');">
			<%}%>
			</td>
			<td noWrap><input type="text"
				id="<%=pd.getKey()%>_<%=task.getId()%>_startDate"
				name="<%=pd.getKey()%>_<%=task.getId()%>_startDate"
				style="cursor: pointer;" class="input-medium" readonly
				onclick="WdatePicker({skin:'ext',startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd HH:mm:ss" />">
			</td>
			<td noWrap><input type="text"
				id="<%=pd.getKey()%>_<%=task.getId()%>_endDate"
				name="<%=pd.getKey()%>_<%=task.getId()%>_endDate"
				style="cursor: pointer;" class="input-medium" readonly
				onclick="WdatePicker({skin:'ext',startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd HH:mm:ss" />">
			</td>
			<td noWrap align="center">&nbsp; <input type="checkbox"
				id="<%=pd.getKey()%>_<%=task.getId()%>_locked"
				name="<%=pd.getKey()%>_<%=task.getId()%>_locked" lck="1"
				<c:if test="${locked == 0}">checked</c:if>></td>
		</tr>

		<%
				 }
			  }
			 }
		  }
	   }
  %>
	</tbody>
</table>

<div align="left" style="width: 90%">
<input type="button"
	value="保存设置" class="btn btn-primary" id="confirm" name="confirm"
	onclick="javacsript:submitRequest();"> 
<input type="button"
	name="button" value="返回 " class="btn"
	onclick="javascript:history.back();">
<br>
<br>
</div>
</form>
<script type="text/javascript">
       jQuery(document).ready(function(){
           var $tree = $(".treeTable");
           $tree.treeTable({clickableNodeNames:true});
           $('tbody tr:not(".header"):not(".latest"):first', $tree).toggleBranch();                    
        });
 </script></div>
</div>
</div>
</div>
</body>
</html>
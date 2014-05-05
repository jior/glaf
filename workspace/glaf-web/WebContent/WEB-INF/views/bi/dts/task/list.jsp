<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.query.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.dts.domain.*"%>
<%@ page import="com.glaf.dts.query.*"%>
<%@ page import="com.glaf.dts.service.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%!
     public static String displayTime(double time) {
		String str = "";
		if (time >= 3600) {
			int size = (int)(time * 1.0D /  3600);
			size = Math.round(size ) ;
			str =  size + " 小时";
			time = time % 3600;
		} 
		
		if (time >= 60) {
			int size = (int) (time * 1.0D /  60);
			size = Math.round(size ) ;
			str =  str +  size + " 分";
			time = time % 60;
		} 

          str =  str +   Math.round(time) +" 秒";
		  return str;
	}
%>
<%
     String queryId = request.getParameter("queryId");
     ITransformTaskService transformTaskService =  ContextFactory.getBean("transformTaskService");
	 IQueryDefinitionService queryDefinitionService =  ContextFactory.getBean("queryDefinitionService");
	 QueryDefinitionQuery query = new QueryDefinitionQuery();
	 query.type("DTS");
	 List<QueryDefinition> queries = queryDefinitionService.list(query);
	 if(queryId == null){
	   if(queries!=null && queries.size()>0){
           queryId = queries.get(0).getId();
		}
	 }

	 //System.out.println("queryId:"+queryId);
     List<TransformTask> tasks = null;
     if(queryId != null){
         TransformTaskQuery query2 = new TransformTaskQuery();
         query2.queryId(queryId);
	     tasks = transformTaskService.list(query2);
	 }

	 if(tasks == null){
		 tasks = new ArrayList<TransformTask>();
	 }

%>
<!DOCTYPE html >
<html>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<head>
	<title>Task List </title>
	<meta http-equiv="refresh" content="10">
	<style type="text/css">
	    .warn {
			  background:#FFFF00;
	     }
	</style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>		
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>	
	<script type="text/javascript">

	function tablePage(tableName){
		document.getElementById("tableName").value=tableName;
		document.getElementById("iForm").action="<%=request.getContextPath()%>/mx/dts/table/edit";
		document.getElementById("iForm").submit();
	}

    function retryTaskAll(queryId){
			if(confirm("重新执行还未成功的任务，确定吗？")){
			 var params = jQuery("#iForm").formSerialize();
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/dts/transform/task/retryAll/'+queryId,
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   alert('操作成功完成！');
				   }
			 });
			}
	    }

		function retryTask(taskId, queryId){
			if(confirm("重新执行该任务，确定吗？")){
			 var params = jQuery("#iForm").formSerialize();
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/dts/transform/task/retry/'+taskId,
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					    alert('操作成功完成！');
						location.href="<%=request.getContextPath()%>/mx/dts/task?queryId="+queryId;
				   }
			 });
			}
	    }

	</script>

</head>
<body style="padding-left:20px;padding-right:20px">

<br />
<div class="x_content_title">
    <img src="<%=request.getContextPath()%>/images/window.png" alt="任务列表">&nbsp;任务列表
</div>
<br>

<form id="iForm"  name="iForm" method="GET" action="<%=request.getContextPath()%>/mx/dts/task">
 
<table align="center" class="table-border" cellspacing="0"
	cellpadding="0" width="90%">
	<tr>
		<td height="28" align="right">
		     <%
		     StringBuffer sb = new StringBuffer();
	         for(QueryDefinition qd: queries){
                 if(queryId != null ){
					sb.append("<option value=\"").append(qd.getId()).append("\"");
					if(qd.getId().equals(queryId)){
						sb.append(" selected ");
					}
					sb.append(">");
					sb.append(qd.getTitle()).append("</option>");
				}
	         }
	       %>
	  <%if(sb.length()>0){%>
      <select id="queryId" name="queryId" onchange="javascript:document.iForm.submit();">
		<%=sb.toString()%>
      </select>
	  &nbsp;&nbsp;
	  <%}%>
      
	  <input type="button" value="再次执行未成功的任务" name="edit" class="btn" 
			     onclick="javascript:retryTaskAll('<%=queryId%>');"> 
		</td>
	</tr>
</table>
 <br />
<table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="90%" border="0">
	<tr class="x-title">
		<td>标题</td>
		<td>开始日期</td>
		<td>结束日期</td>
		<td>用时（秒）</td>
		<td>状态</td>
		<td>功能键</td>
	</tr>
	
 <%
        double total = 0;
		int okQty = 0;
		int errorQty = 0;
	    for(TransformTask task: tasks){
			if(task.getStatus() == 9){
				okQty = okQty + 1;
			    total +=  task.getDuration() / 1000.0D;
			}
			if(task.getStatus() == 2){
				errorQty = errorQty + 1;
			}
			pageContext.setAttribute("task", task);
		 
	%>
	 <tr class="x-content <%=task.getDuration() / 1000.0D > 10 ? "warn" : ""%>">
	    <td>
          <label title="参数：${task.parameter}">${task.title}</label>
	     </td>
 		<td>
			 <fmt:formatDate value="${task.startTime}" pattern="yyyy-MM-dd HH:mm" />
		</td>
		<td>
			 <fmt:formatDate value="${task.endTime}" pattern="yyyy-MM-dd HH:mm" />
		</td>
		<td>
          ${task.duration *1.0 / 1000.0}
	     </td>
		 <td>
          <c:choose>
			<c:when test="${task.status == 9}">
				  <font color="#00ff00"><strong>成功</strong></font>
			</c:when>
			<c:when test="${task.status == 2}">
				   <font color="#ff0000"><strong>失败</strong></font>
			</c:when>
			<c:when test="${task.status == 1}">
				   <font color="#0033FF"><strong>进行中</strong></font>
			</c:when>
			<c:when test="${task.status == 0}">
				   <font color="#000000"><strong>未开始</strong></font>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	     </td>
		<td>
		  &nbsp;
		  <c:if test="${task.status != 9}">
		   <span onclick="javascript:retryTask('${task.id}','${task.queryId}');" style="cursor:hand;">
		       重新执行
           </span>
		   </c:if>
		   &nbsp;
		   <a href="<%=request.getContextPath()%>/mx/dts/query/edit?queryId=${task.queryId}">查看定义</a>
         </td>
      </tr>
	  <%}%>

      <tr class="x-content">
	  <td colspan="10" align="right">
	           其中：成功<font color="#00ff00"><strong><%=okQty%></strong></font>个，
			   失败<font color="#ff0000"><strong><%=errorQty%></strong></font>个。
	   </td>
	   </tr>
		
</table>

</form>
 
</body>
</html>
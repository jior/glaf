<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.query.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.dts.domain.*"%>
<%@ page import="com.glaf.dts.query.*"%>
<%@ page import="com.glaf.dts.service.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
     ITableDefinitionService tableDefinitionService =  ContextFactory.getBean("tableDefinitionService");
	 IQueryDefinitionService queryDefinitionService =  ContextFactory.getBean("queryDefinitionService");

	 TableDefinitionQuery query = new TableDefinitionQuery();
	 query.type(com.glaf.dts.util.Constants.DTS_TASK_TYPE);
	 List<TableDefinition> tables = tableDefinitionService.getTableDefinitionsByQueryCriteria(0,1000,query);
	 QueryDefinitionQuery q = new QueryDefinitionQuery();
	 List<QueryDefinition> queries = queryDefinitionService.list(q);
%>
<!DOCTYPE html >
<html>
<head>
<title>表管理 </title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<script type="text/javascript">
         
		 function transformTable(tableName){
			 if(confirm("确定重新获取数据吗？")){
				 var params = jQuery("#iForm").formSerialize();
				  jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/rs/dts/table/transformTable?tableName='+tableName,
					   data: params,
					   dataType:  'json',
					   error: function(data){
						   alert('服务器处理错误！');
					   },
					   success: function(data){
							if(data.message != null){
							   alert(data.message);
							} else {
							   alert('操作成功完成！');
							}
					   }
				 });
		  }
	  }

	     function rebuild(tableName, type){
			 if(confirm("重建会清空表数据，确定吗？")){
				 document.getElementById("tableName").value=tableName;
				 document.getElementById("actionType").value=type;
				 var params = jQuery("#iForm").formSerialize();
				  jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/rs/dts/table/saveTable',
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

	   function deleteTbl(tableName){
			 if(confirm("删除表定义将删除关联查询，数据删除后不能恢复，确定吗？")){
				 document.getElementById("tableName").value=tableName;
				 var params = jQuery("#iForm").formSerialize();
				  jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/rs/dts/table/deleteTable',
					   data: params,
					   dataType:  'json',
					   error: function(data){
						   alert('服务器处理错误！');
					   },
					   success: function(data){
							alert('操作成功完成！');
							location.href="<%=request.getContextPath()%>/mx/dts/table";
					   }
				 });
		  }
	  }

	 function loadAndFetchData(){
          if(confirm("重新加载数据并取数，确定吗？")){
				 var params = jQuery("#iForm").formSerialize();
				  jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/rs/dts/table/transformAll',
					   data: params,
					   dataType:  'json',
					   error: function(data){
						   alert('服务器处理错误！');
					   },
					   success: function(data){
						   if(data.message != null){
                             alert(data.message);
						   } else {
							 alert('操作成功完成！');
						   }
					   }
				 });
		  }
	 }
 
	 function showData(tableName){
		var link= '<%=request.getContextPath()%>/mx/dts/table/resultList?q=1';
		document.getElementById("tableName").value=tableName;
        document.iForm.action=link;
		document.iForm.submit();
	 }

	function editTable(tableName){
		document.getElementById("tableName").value=tableName;
		document.getElementById("iForm").action="<%=request.getContextPath()%>/mx/dts/table/edit";
		document.getElementById("iForm").submit();
	}

</script>
</head>
<body style="padding-left:20px;padding-right:20px">
<form id="iForm"  name="iForm" method="post" >
<input type="hidden" id="tableName" name="tableName" />
<input type="hidden" id="actionType" name="actionType" />
<br> 
<div class="x_content_title">
    <img src="<%=request.getContextPath()%>/images/window.png" alt="表管理">&nbsp;表管理
</div>
 

<div style="width:100%;" >
<table align="center" class="table-border" cellspacing="0"
	cellpadding="0" width="90%">
	<tr>
		<td height="28" align="right">
		    <!-- <input type="button" value="查询定义" name="edit" class="btn" 
			           onclick="javascript:location.href='<%=request.getContextPath()%>/mx/dts/query';"> -->
			<input type="button" value="加载并抽取数据" name="edit" class="btn btn-primary" 
			       onclick="javascript: loadAndFetchData(); ">
		</td>
	</tr>
</table>
</div>
<br>

 <table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="90%" border="0">
	<tr class="x-title">
		<td>表名</td>
		<td>标题</td>
		<td>关联查询</td>
		<td>创建日期</td>
		<td>功能键</td>
	</tr>
	
 <%
        
	    for(TableDefinition table: tables){
			pageContext.setAttribute("table", table);
	%>
	 <tr   class="x-content">
	    <td>
		  <span onclick="javascript:editTable('${table.tableName}');" style="cursor:hand;">
          ${table.tableName}
	      </span>
	     </td>
		<td>
           ${table.title}
		</td>
		<td>
		       <%
		            for(QueryDefinition qd: queries){
		                if(table.getTableName().equals(qd.getTargetTableName())){
                %>
				      <a href="<%=request.getContextPath()%>/mx/dts/query/edit?queryId=<%=qd.getId()%>"><%=qd.getTitle()%></a>
				<%
						}
	                }
		       %>
		</td>
		<td>
			 <fmt:formatDate value="${table.createTime}" pattern="yyyy-MM-dd HH:mm" />
		</td>
		<td align="center">
		     <a href="#"  
		       onclick="javascript:showData('<c:out value="${table.tableName}"/>');"/>
			   <img src="<%=request.getContextPath()%>/images/prop.gif" border="0"> 数据</a>
		    <a href="#"  
		       onclick="javascript:transformTable('<c:out value="${table.tableName}"/>');"/>
			   <img src="<%=request.getContextPath()%>/images/refresh.gif" border="0">重新取数</a>
			&nbsp;
			<a href="#" onclick="javascript:editTable('<c:out value="${table.tableName}"/>');" style="cursor:hand;">
              <img src="<%=request.getContextPath()%>/images/update.gif" border="0"> 修改
	         </a>
			 &nbsp;
		    <a href="#"  
		       onclick="javascript:deleteTbl('<c:out value="${table.tableName}"/>');"/>
			   <img src="<%=request.getContextPath()%>/images/delete.gif" border="0">删除</a>
		</td>
      </tr>
	  <%}%>
		
</table>

</form>

</body>
</html>
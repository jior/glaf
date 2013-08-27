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
<%
     IQueryDefinitionService queryDefinitionService = (IQueryDefinitionService)ContextFactory.getBean("queryDefinitionService");
	 QueryDefinitionQuery query = new QueryDefinitionQuery();
	 query.type(com.glaf.dts.util.Constants.DTS_TASK_TYPE);
	 List<QueryDefinition> queries = queryDefinitionService.getQueryDefinitionsByQueryCriteria(0,1000,query);
%>
<!DOCTYPE html>
<html>
<head>
	<title>Query Layout </title>
	<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
 	<style type="text/css" media="print">
 
	html, body {
		overflow:	visible 	!important;
		width:		auto		!important;
		height:		auto		!important;
	}
 
	</style>
 

	<script type="text/javascript">

 
	 function queryPage(  id ){
		location.href='<%=request.getContextPath()%>/mx/dts/query/edit?queryId='+id;
	  }

	function tablePage(tableName){
		document.getElementById("tableName").value=tableName;
		document.getElementById("iForm").action="<%=request.getContextPath()%>/mx/dts/table/edit";
		document.getElementById("iForm").submit();
	}

		function deleteQuery(queryId){
			if(confirm("数据删除后不能恢复，确定吗？")){
			 var params = jQuery("#iForm").formSerialize();
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/dts/query/delete/'+queryId,
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
						alert('操作成功完成！');
						location.href="<%=request.getContextPath()%>/mx/dts/query";
				   }
			 });
			}
	    }

	</script>

</head>
<body style="padding-left:20px;padding-right:20px">

<br />
<div class="x_content_title">
    <img src="<%=request.getContextPath()%>/images/window.png" alt="查询定义列表">&nbsp;查询定义列表
</div>
<br>

<table align="center" cellspacing="0"
	cellpadding="0" width="90%">
	<tr>
		<td height="28" align="right">
		    <input type="button" value="新建查询" name="edit" class="btn" 
			           onclick="javascript:queryPage('');"> 
		</td>
	</tr>
</table>
 
<br>
<table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="90%" border="0">
	<tr class="x-title">
		<td>标题</td>
		<td>名称</td>
		<td>目标表</td>
		<td>父查询</td>
		<td>创建日期</td>
		<td>功能键</td>
	</tr>
	
 <%
        Map map = new HashMap();
        for(QueryDefinition q: queries){
            map.put(q.getId(), q);
        }
	    for(QueryDefinition q: queries){
			pageContext.setAttribute("q", q);
			pageContext.removeAttribute("parent");
			if(q.getParentId() != null){
				QueryDefinition parent = (QueryDefinition)map.get(q.getParentId());
				if(parent != null){
					    pageContext.setAttribute("parent", parent);
				}
			}
	%>
	 <tr   class="x-content">
	    <td>
		  <span onclick="javascript:queryPage('<%=q.getId()%>');" style="cursor:hand;">
          ${q.title}
	      </span>
	     </td>
		<td>
           ${q.name}
		</td>
		<td>
		<span onclick="javascript:tablePage('${q.targetTableName}');" style="cursor:hand;">
          ${q.targetTableName}
	      </span>
		</td>
		<td>
		     &nbsp;${parent.title}
		</td>
		<td>
			 <fmt:formatDate value="${q.createTime}" pattern="yyyy-MM-dd HH:mm" />
		</td>
		<td>
	       <a
				href="<%=request.getContextPath()%>/mx/dts/query/edit?queryId=${q.id}">
			<img src="<%=request.getContextPath()%>/images/update.gif"
				title="修改记录" border="0" style="cursor: hand;">修改</a> 
          &nbsp;
		   <span onclick="javascript:deleteQuery('${q.id}');" style="cursor:hand;">
		    <img src="<%=request.getContextPath()%>/images/delete.gif"
				title="删除记录" border="0" style="cursor: hand;">删除</span>
         </td>
      </tr>
	  <%}%>
		
</table>

</form>
 
</body>
</html>
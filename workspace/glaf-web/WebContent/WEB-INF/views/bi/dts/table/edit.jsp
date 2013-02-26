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
        String encoding = request.getParameter("encoding");
		if (encoding == null) {
			encoding = "UTF-8";
		}
      
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		response.setContentType("text/html;charset=" + encoding);
		IQueryDefinitionService queryDefinitionService = ContextFactory.getBean("queryDefinitionService");
		ITableDefinitionService tableDefinitionService = ContextFactory.getBean("tableDefinitionService");
		String queryId = null;
        String tableName = request.getParameter("tableName");
		if(tableName == null){
			TableDefinitionQuery query = new TableDefinitionQuery();
			query.type(com.glaf.dts.util.Constants.DTS_TASK_TYPE);
	        List<TableDefinition> tables = tableDefinitionService.list(query);
			if(tables!=null && tables.size()>0){
                  tableName = tables.get(0).getTableName();
			}
		} 
        
	    TableDefinition table = tableDefinitionService.getTableDefinition(tableName);
		if(table != null){
             pageContext.setAttribute("table", table);
			 List<QueryDefinition> queries =  queryDefinitionService.getQueryDefinitionByTableName(tableName);
             if(queries != null && queries.size() > 0){
				 queryId = queries.get(0).getId();
			 }
		}
%>
<!DOCTYPE html >
<html>
<head>
	<title>表管理</title>
	<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>		
	<script type="text/javascript">
	     function openQx(){
			var elementValue = $('#queryIds').val();
			var link = '<%=request.getContextPath()%>/mx/dts/query/chooseQuery?elementId=queryIds&elementName=queryNames&elementValue='+elementValue;
			var x=100;
			var y=100;
			if(is_ie) {
				x=document.body.scrollLeft+event.clientX-event.offsetX-200;
				y=document.body.scrollTop+event.clientY-event.offsetY-200;
			}
			openWindow(link,self,x, y, 695, 480);
	    }

		function transformTable(){
			 if(confirm("确定重新获取数据吗？")){
				 var tableName = document.getElementById("tableName").value
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

	     function saveForm(type){
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
						if("transformAll" == type){
							window.open("<%=request.getContextPath()%>/mx/dts/task");
						}
						location.href="<%=request.getContextPath()%>/mx/dts/table";
						
				   }
			 });
	}
	</script>
</head>
<body>
<br>
<div class="x_content_title">
    <img src="<%=request.getContextPath()%>/images/window.png" alt="编辑表信息">&nbsp;编辑表信息
</div>
<form id="iForm"  name="iForm" method="post" action="">
<input type="hidden" id="actionType" name="actionType" />
<input type="hidden" id="tableName" name="tableName" value="${table.tableName}"/>
<div class="content-block" style="width: 90%;"><br>
 
<table align="center" class="x-table-border" cellspacing="1"
	cellpadding="1" width="95%" border="0">
	<tr>
		<td >表名</td>
		<td class="x-content" colspan="3"> ${table.tableName}</td>
	</tr>
	<tr> 
		<td >标题</td>
		<td class="x-content" colspan="3">
		<input type="text" id="title" name="title" class="x-text" size= "50" value="${table.title}"/>
		</td>
	</tr>
	<tr> 
		<td >聚合主键</td>
		<td class="x-content" colspan="3">
		<input type="text" id="aggregationKeys" name="aggregationKeys" class="x-text" size= "50" value="${table.aggregationKeys}"/> （构成每条记录唯一性的字段列表，多个字段以半角的逗号隔开“,”）
		</td>
	</tr>
	<tr>
		<td >查询数据集</td>
        <td class="x-content" colspan="3">
			<input type="hidden" id="queryIds" name="queryIds" value="${table.queryIds}">
			<input type="text" id="queryNames" name="queryNames" value="${queryNames}"
				   readonly="true" size="80" onclick="javascript:openQx();" class="x-text">
			&nbsp;
			<a href="#" onclick="javascript:openQx();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0"
				title="如果表数据由多个查询数据集组成，请先建好查询数据再选择。">
			</a>
            &nbsp;请选择构成该数据表的多个查询数据集
		</td>
	</tr>
    <tr>
		<td >执行次序</td>
		<td class="x-content" colspan="3">
		    <select id="sortNo" name="sortNo" class="span2">
			    <option value="0">0</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
		    </select>&nbsp;&nbsp;（提示：数值小的先执行）
			<script type="text/javascript">
			    document.getElementById("sortNo").value="${table.sortNo}";
			</script>
		</td>
	</tr>
	<tr> 
	    <td >是否临时表</td>
		<td class="x-content" colspan="3">
            <select id="temporaryFlag" name="temporaryFlag" class="span2">
			    <option value="0">否</option>
				<option value="1">是</option>
		    </select>&nbsp;&nbsp;（提示：临时表在每次抓取数据都将清空该表数据，并且表命名必须包含"_tmp_"）
			<script type="text/javascript">
			    document.getElementById("temporaryFlag").value="${table.temporaryFlag}";
			</script>
		</td>
	</tr>
	<tr> 
	    <td >每次抓取前删除</td>
		<td class="x-content" colspan="3">
            <select id="deleteFetch" name="deleteFetch" class="span2">
			    <option value="0">否</option>
				<option value="1">是</option>
		    </select>&nbsp;&nbsp;（提示：如果选择每次抓取前删除，将删除当天及${input_yyyymmdd}中指定的记录）
			<script type="text/javascript">
			    document.getElementById("deleteFetch").value="${table.deleteFetch}";
			</script>
		</td>
	</tr>
 
	
	<tr>
		<td >描述</td>
		<td class="x-content" colspan="3">
		    <textarea id="description" name="description" rows="8" cols="66" class="x-textarea span5" style="align:left;"><c:out value="${table.description}" /></textarea>
		</td>
	</tr>
</table>

<table align="center"  cellspacing="1"
	cellpadding="4" width="95%">
 
   <tr >
		<td>字段名</td>
		<td>数据类型</td>
		<td>标题</td>
		<td>名称</td>
		<td>长度</td>
	</tr>
    <%
	    for(ColumnDefinition column : table.getColumns()){
			if(column.getLength()==0){
				column.setLength(250);
			}
			pageContext.setAttribute("column", column);
	%>
      <tr >
		<td>${column.columnName}</td>
		<td>
		    <c:choose>
				<c:when test="${column.javaType == 'Integer' }">
					 整型
				</c:when>
				<c:when test="${column.javaType == 'Long' }">
					 长整型
				</c:when>
				<c:when test="${column.javaType == 'Double' }">
					 数值型
				</c:when>
				<c:when test="${column.javaType == 'Boolean' }">
					 逻辑型
				</c:when>
				<c:when test="${column.javaType == 'Date' }">
					 日期型
				</c:when>
				<c:otherwise>
				     字符串型
				</c:otherwise>
			</c:choose>
			 
		</td>
		<td>
		<input type="text" name="${column.columnName}_title" class="input-medium" 
		value="${column.title}"/>
		</td>
		<td>
		<input type="text" name="${column.columnName}_name" class="input-small" 
		value="${column.name}"/>
		</td>
		<td>
		<input type="text" name="${column.columnName}_length" class="input-mini" 
		value="${column.length}" size="3" maxLength="4"/>
		</td>
	</tr>
	<%}%>
</table>
  <br />
	<div align="center">
	    <input type="button"  name="save" value="保存" class="btn btn-primary" onclick="javascript:saveForm('');"/>
		<%if( table.getColumns() != null &&  table.getColumns().size()>0){%>
		<input type="button"  name="saveAndRecreate" value="保存并更新表" class="btn" 
		       onclick="javascript:saveForm('alterTable');"/>
		<input type="button"  name="saveAndRecreate" value="重新取数" class="btn" 
		       onclick="javascript:transformTable();"/>
		<%}%>
	</div>
</fieldset>
</div>
</form>
 
</body>
</html>
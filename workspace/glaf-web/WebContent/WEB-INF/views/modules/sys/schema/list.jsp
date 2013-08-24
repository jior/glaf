<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.query.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.base.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
<title>表模式管理 </title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<script type="text/javascript">
 
	    function rebuild(systemName){
			if(confirm("用最新的类注解更新表结构，确定吗？")){
				 var params = jQuery("#iForm").formSerialize();
				  jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/mx/sys/schema/rebuild?systemName='+systemName,
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
 

	    function rebuildAll(){
			if(confirm("用最新的类注解更新表结构，确定吗？")){
				 var params = jQuery("#iForm").formSerialize();
				  jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/mx/sys/schema/rebuildAll',
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

</script>
</head>
<body style="padding-left:20px;padding-right:20px">
<form id="iForm"  name="iForm" method="post" >
<input type="hidden" id="tableName" name="tableName" />
<input type="hidden" id="actionType" name="actionType" />
<br> 
<div class="x_content_title">
    <img src="<%=request.getContextPath()%>/images/window.png" alt="表模式管理">&nbsp;表模式管理
</div>
 
<div style="width:100%;" >
<table align="center" class="table-border" cellspacing="0"
	cellpadding="0" width="90%">
	<tr>
		<td height="28" align="right">
		    <input type="button" value="更新全部" name="edit" class="btn btn-primary" 
			       onclick="javascript:rebuildAll(); ">
		</td>
	</tr>
</table>
</div>
<br>
 <table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="90%" border="0">
	<tr class="x-title">
	    <td>名称</td>
		<td>标题</td>
		<td>类型</td>
		<td>URL</td>
		<td>功能键</td>
	</tr>
	<c:forEach items="${rows}" var="a">
	 <tr  class="x-content">
	    <td>${a.name}</td>
	    <td>${a.subject}</td>
		<td>${a.type}</td>
		<td>${a.url}</td>
		<td align="center">
		     <a href="#" onclick="javascript:rebuild('${a.name}');"><img src="<%=request.getContextPath()%>/images/data.png" border="0">&nbsp;更新表结构</a>
		</td>
      </tr>
     </c:forEach>
 </table>
</form>
</body>
</html>
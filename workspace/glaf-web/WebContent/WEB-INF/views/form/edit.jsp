<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${formApplication.title}</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		var businessKey = jQuery("#businessKey").val();
		var link="<%=request.getContextPath()%>/mx/form/updateData";
		if(businessKey==""){
			link = '<%=request.getContextPath()%>/mx/form/saveData';
		} 
		jQuery.ajax({
				   type: "POST",
				   url: link,
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						 alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
					   if (window.opener) {
						  window.opener.location.reload();
					   } else if (window.parent) {
						  window.parent.location.reload();
					   }
				   }
			 });
	}


</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑记录</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="appId" name="appId" value="${formApplication.id}"/>
  <input type="hidden" id="businessKey" name="businessKey" value="${dataModel.businessKey}"/>
  <table class="easyui-form" style="width:600px;" align="center">
    <tbody>
	<c:forEach items="${columns}" var="column">
	<tr>
		<td width="20%" align="left">${column.columnName}</td>
		<td align="left">
		    <c:choose>
			<c:when test="${column.javaType == 'Date'}">
               <input id="${column.columnName}" name="${column.columnName}" type="text" size="20"
			          class="easyui-datebox"
				      value='<fmt:formatDate value="${column.value}" pattern="yyyy-MM-dd HH:mm:ss"/>'/>
			</c:when>
			<c:when test="${column.javaType =='Integer'}">
               <input id="${column.columnName}" name="${column.columnName}" type="text" size="20"
			          class="easyui-numberbox"
				      value="${column.value}"/>
			</c:when>
			<c:when test="${column.javaType =='Long'}">
               <input id="${column.columnName}" name="${column.columnName}" type="text" size="20"
			          class="easyui-numberbox"
				      value="${column.value}"/>
			</c:when>
			<c:when test="${column.javaType =='Double'}">
               <input id="${column.columnName}" name="${column.columnName}" type="text" size="20"
			          class="easyui-numberbox"
				      value="${column.value}"/>
			</c:when>
			<c:otherwise>
			   <input id="${column.columnName}" name="${column.columnName}" type="text" size="60"
			          class="easyui-validatebox"
				      value="${column.value}"/>
		    </c:otherwise>
		   </c:choose>
		</td>
	</tr>
	</c:forEach>
    </tbody>
  </table>
  </form>
</div>
</div>
</body>
</html>
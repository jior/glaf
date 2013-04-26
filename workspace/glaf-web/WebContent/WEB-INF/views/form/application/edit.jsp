<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="org.jbpm.*"%>
<%@ page import="org.jbpm.graph.def.*"%>
<%@ page import="com.glaf.form.core.domain.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);

    FormApplication formApplication = (FormApplication)request.getAttribute("formApplication");
	if(formApplication == null){
		formApplication = new FormApplication();
	}

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表单应用</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/system/form/application.do?method=saveFormApplication',
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
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		document.getElementById("appId").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/system/form/application.do?method=saveFormApplication',
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
	<span class="x_content_title">编辑表单应用</span>
	<!-- <input type="button" name="save" value=" 保存 " class="button btn btn-primary" onclick="javascript:saveData();">
	<input type="button" name="saveAs" value=" 另存 " class="button btn" onclick="javascript:saveAsData();">
	<input type="button" name="back" value=" 返回 " class="button btn" onclick="javascript:history.back();"> -->
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
	<!-- 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-saveas'" onclick="javascript:saveAsData();" >另存</a> 
        -->
	<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-back'" onclick="javascript:history.back();">返回</a> -->	
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="nodeId" name="nodeId" value="${nodeId}" >
  <input type="hidden" id="id" name="id" value="${formApplication.id}"/>
  <input type="hidden" id="appId" name="appId" value="${formApplication.id}"/>
  <table class="easyui-form" style="width:600px;" align="center">
    <tbody>
      <tr>
		<td align="left"  width="20%" height="27" noWrap>应用名称</td>
		<td align="left"  width="80%" noWrap>
		<input type="text" name="name" value="${formApplication.name}" 
               class="easyui-validatebox" data-options="required:true,validType:'length[3,50]'"
			   maxLength="50" size="30">（只能输入字母）
		</td>
	</tr>
	<tr>
		<td align="left"  width="20%" height="27" noWrap>应用主题</td>
		<td align="left"  width="80%" noWrap>
		<input type="text" name="title" value="${formApplication.title}" 
               class="easyui-validatebox" data-options="required:true,validType:'length[3,50]'"
			   maxLength="50" size="30">
	    </td>
	</tr>
	<tr>
		<td align="left"  width="20%" height="27">列表网格控件名称</td>
		<td align="left"  width="80%">
		<select id="listControllerName" name="listControllerName">
		    <option value="">----请选择----</option>
			<c:forEach items="${dataItems}" var="a">
				<option value="${a.code}">${a.name}</option>
			</c:forEach>
		</select> 
		<c:if test="${!empty formApplication.listControllerName}">
			<script language="javascript">
		        document.getElementById("listControllerName").value="${formApplication.listControllerName}";
			</script>
		</c:if>
		</td>
	</tr>
	<tr>
		<td align="left"  width="20%" height="27">列表模板编号</td>
		<td align="left"  width="80%">
		<select id="listTemplateId" name="listTemplateId">
		    <option value="">----请选择----</option>
			<c:forEach items="${listTemplates}" var="a">
				<option value="${a.templateId}">${a.name}</option>
			</c:forEach>
		</select> 
		<c:if test="${! empty formApplication.listTemplateId}">
			<script language="javascript">
		        document.getElementById("listTemplateId").value="${formApplication.listTemplateId}";
			</script>
		</c:if>
		</td>
	</tr>

	<tr>
		<td align="left"  width="20%" height="27">表单渲染控件名称</td>
		<td align="left"  width="80%">
		<select id="linkControllerName" name="linkControllerName">
		   <option value="">----请选择----</option>
			<c:forEach items="${dataItems2}" var="b">
				<option value="${b.code}">${b.name}</option>
			</c:forEach>
		</select> 
		<c:if test="${!empty formApplication.linkControllerName}">
			<script language="javascript">
		        document.getElementById("linkControllerName").value="${formApplication.linkControllerName}";
			</script>
		</c:if>
		</td>
	</tr>


	<tr>
		<td align="left"  width="20%" height="27">表单模板编号</td>
		<td align="left"  width="80%">
		 <select id="linkTemplateId" name="linkTemplateId">
		   <option value="">----请选择----</option>
			<c:forEach items="${formTemplates}" var="a">
				<option value="${a.templateId}">${a.name}</option>
			</c:forEach>
		</select> 
		<c:if test="${! empty formApplication.linkTemplateId}">
			<script language="javascript">
		        document.getElementById("linkTemplateId").value="${formApplication.linkTemplateId}";
			</script>
		</c:if>
		</td>
	</tr>
	<tr>
		<td align="left"  width="20%" height="27">数据表</td>
		<td align="left"  width="80%">
		 <c:choose>
		 <c:when test="${! empty formApplication.tableName}">
		    ${formApplication.tableName}
		 </c:when>
		 <c:otherwise>
		   <select id="tableName" name="tableName">
		   <option value="">----请选择----</option>
			<c:forEach items="${entityDefinitions}" var="a">
				<option value="${a.tablename}">${a.title}[${a.tablename}]</option>
			</c:forEach>
		   </select> 
		   <span style="color:red">(选择后不允许修改)</span>
         </c:otherwise>
		 </c:choose>
		</td>
	</tr>
	<tr>
		<td align="left"  width="20%" height="27">业务流程</td>
		<td align="left"  width="80%">
		<select id="processName" name="processName" class="x-form-select-one">
			<option value="">----请选择----</option>
		<%
        List processDefinitions = (List)request.getAttribute("processDefinitions");
         if(processDefinitions != null && processDefinitions.size() > 0){
        	Set names = new HashSet();
        	Iterator iterator = processDefinitions.iterator();
        	while(iterator.hasNext()){
        		  ProcessDefinition pd = (ProcessDefinition)iterator.next();        
        		  if(!names.contains(pd.getName())){
        			   names.add(pd.getName());
        			   String selected = "";
        			   if(formApplication != null && StringUtils.equals(formApplication.getProcessName(), pd.getName())){
        				   selected = " selected ";
        			   }
        			   out.print("\n<option value=\""+pd.getName()+"\" "+selected+">"+pd.getName());	
        			   out.print(pd.getDescription() != null ? " "+pd.getDescription() : ""); 
        			   out.print(" V"+pd.getVersion()+".0");
        			   out.print("</option>");
        			  }
        	    }
        }
   %>
		</select>
	  </td>
	</tr>
	<td align="left"  width="20%" height="27">默认表单</td>
	<td align="left"  width="80%">
	  <select id="formName" name="formName" class="x-form-select-one">
		<option value="">----请选择----</option>
		<%
        List formDefinitions = (List)request.getAttribute("formDefinitions");
        if(formDefinitions != null && formDefinitions.size() > 0){
        	Set names = new HashSet();
        	Iterator iterator = formDefinitions.iterator();
        	while(iterator.hasNext()){
        		  FormDefinition formDefinition = (FormDefinition)iterator.next();        
        		  if(!names.contains(formDefinition.getName())){
        			   names.add(formDefinition.getName());
        			   String selected = "";
        			   if(formApplication != null && StringUtils.equals(formApplication.getFormName(), formDefinition.getName())){
        				   selected = " selected ";
        			   }
        			   out.print("\n<option value=\""+formDefinition.getName()+"\" "+selected+">"+formDefinition.getName());	
        			   out.print("  "+formDefinition.getTitle()); 
        			   out.print(" V"+formDefinition.getVersion()+".0");
        			   out.print(" [" +DateUtils.getDate(formDefinition.getCreateDate())+"]");
        			   out.print("</option>");
        			  }
        	    }
        }
  %>
	</select>
	</td>
	</tr>
	<tr>
		<td align="left"  width="20%" height="27">流程运行方式</td>
		<td align="left"  width="80%">
		<input type="hidden" id="manualRouteFlag" name="manualRouteFlag" value="${formApplication.manualRouteFlag}">
		  <c:choose>
			<c:when test="${formApplication.manualRouteFlag eq 'MANUAL' }">
				<input type="radio" name="_manualRouteFlag"
					onclick="document.getElementById('manualRouteFlag').value='AUTO';"> 自动流程
                <input type="radio" name="_manualRouteFlag" checked
					onclick="document.getElementById('manualRouteFlag').value='MANUAL';"> 人工流程
				<input type="radio" name="_manualRouteFlag"
					onclick="document.getElementById('manualRouteFlag').value='FREE';"> 自由流程
	           </c:when>
			<c:when test="${formApplication.manualRouteFlag eq 'FREE' }">
				<input type="radio" name="_manualRouteFlag"
					onclick="document.getElementById('manualRouteFlag').value='AUTO';"> 自动流程
                <input type="radio" name="_manualRouteFlag"
					onclick="document.getElementById('manualRouteFlag').value='MANUAL';"> 人工流程
				<input type="radio" name="_manualRouteFlag" checked
					onclick="document.getElementById('manualRouteFlag').value='FREE';"> 自由流程
	           </c:when>
			<c:otherwise>
				<input type="radio" name="_manualRouteFlag" checked
					onclick="document.getElementById('manualRouteFlag').value='AUTO';"> 自动流程
                <input type="radio" name="_manualRouteFlag"
					onclick="document.getElementById('manualRouteFlag').value='MANUAL';"> 人工流程
				<input type="radio" name="_manualRouteFlag"
					onclick="document.getElementById('manualRouteFlag').value='FREE';"> 自由流程
			</c:otherwise>
		</c:choose>
	  </td>
	</tr>

	<tr>
		<td align="left"  width="20%" height="27">是否发布</td>
		<td align="left"  width="80%">
		<input type="hidden" id="releaseFlag" name="releaseFlag" value="${formApplication.releaseFlag}"> 
			<c:choose>
			<c:when test="${formApplication.releaseFlag eq 'Y' }">
				<input type="radio" name="_releaseFlag" checked
					onclick="document.getElementById('releaseFlag').value='Y';"> 是
                <input type="radio" name="_releaseFlag"
					onclick="document.getElementById('releaseFlag').value='N';"> 否
	           </c:when>
			<c:otherwise>
				<input type="radio" name="_releaseFlag"
					onclick="document.getElementById('releaseFlag').value='Y';"> 是
                <input type="radio" name="_releaseFlag" checked
					onclick="document.getElementById('releaseFlag').value='N';"> 否
			</c:otherwise>
		   </c:choose>
		</td>
	</tr>

	<tr>
		<td align="left"  width="20%" height="27">允许上传附件</td>
		<td align="left"  width="80%">
		<input type="hidden" id="uploadFlag" name="uploadFlag" value="${formApplication.uploadFlag}"> 
			<c:choose>
			<c:when test="${formApplication.uploadFlag eq 'Y' }">
				<input type="radio" name="_uploadFlag" checked
					onclick="document.getElementById('uploadFlag').value='Y';"> 是
                <input type="radio" name="_uploadFlag"
					onclick="document.getElementById('uploadFlag').value='N';"> 否
	           </c:when>
			<c:otherwise>
				<input type="radio" name="_uploadFlag"
					onclick="document.getElementById('uploadFlag').value='Y';"> 是
                <input type="radio" name="_uploadFlag" checked
					onclick="document.getElementById('uploadFlag').value='N';"> 否
			</c:otherwise>
		</c:choose>
		</td>
	</tr>

	<tr>
		<td align="left"  width="20%" height="27">审核时允许上传附件</td>
		<td align="left"  width="80%">
		<input type="hidden" id="auditUploadFlag" name="auditUploadFlag" value="${formApplication.auditUploadFlag}">
		<c:choose>
			<c:when test="${formApplication.auditUploadFlag eq 'Y' }">
				<input type="radio" name="_auditUploadFlag" checked
					onclick="document.getElementById('auditUploadFlag').value='Y';"> 是
                <input type="radio" name="_auditUploadFlag"
					onclick="document.getElementById('auditUploadFlag').value='N';"> 否
	           </c:when>
			<c:otherwise>
				<input type="radio" name="_auditUploadFlag"
					onclick="document.getElementById('auditUploadFlag').value='Y';"> 是
               <input type="radio" name="_auditUploadFlag" checked
					onclick="document.getElementById('auditUploadFlag').value='N';"> 否
			</c:otherwise>
		</c:choose>
	   </td>
	</tr>

    </tbody>
  </table>
 </form>
 <br/>
 <br/>
</div>
</div>
</body>
</html>
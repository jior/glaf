<%--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.district.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	List list = (List)request.getAttribute("districts");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑区域</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/district/saveDistrict',
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
					   /*
					   if (window.opener) {
						  //window.opener.location.reload();
						  window.opener.reloadGrid();
					   } else if (window.parent) {
						  //window.parent.location.reload();
						  window.parent.reloadGrid();
					   }
					   window.close();
					   */
					   location.href='<%=com.glaf.core.util.RequestUtils.decodeURL(request.getParameter("fromUrl"))%>';
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/district/saveDistrict',
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

	 

	function setValue(obj){
	  obj.value=obj[obj.selectedIndex].value;
	  document.getElementById("parentId").value=obj.value;
	}

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑区域</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
	   onclick="javascript:saveData();" >保存</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-back'"
	   onclick="javascript:window.history.go(-1);">返回</a>    
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${district.id}"/>
  <c:choose>
	<c:when test="${!empty district }">
       <input type="hidden" id="parentId" name="parentId" value="${district.parentId}"/>
    </c:when>
    <c:otherwise>
	   <input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
    </c:otherwise>
  </c:choose>
  <input type="hidden" id="type" name="type" value="${type}"/>
  <table class="easyui-form" style="width:600px;" align="left">
    <tbody>
	<tr>
		<td width="20%" align="left">上级节点</td>
		<td align="left">
            <select name="parent" onChange="javascript:setValue(this);">
			  <option value="0">根目录/</option>
			  <%
				if(list!=null && !list.isEmpty()){
				  Iterator iter=list.iterator();   
				  while(iter.hasNext()){
					DistrictEntity bean2=(DistrictEntity)iter.next();
				%>
			  <option value="<%=bean2.getId()%>">
			  <%
				for(int i=1;i<bean2.getLevel();i++){
				  out.print("&nbsp;&nbsp;--");
				}
				out.print(bean2.getName());
				%>
			  </option>
			  <%    
			   }
			 }
			%>
			</select>
			<script language="javascript">
			  <c:choose>
				<c:when test="${!empty district }">
				    document.all.parent.value="${district.parentId}";	
				</c:when>
				<c:otherwise>
				   document.all.parent.value="${parentId}";	
				</c:otherwise>
			  </c:choose>
			</script>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">名称</td>
		<td align="left">
            <input id="name" name="name" type="text"  size="50"
			       class="easyui-validatebox x-text"  
			       data-options="required:true"
				   value="${district.name}"/>
		</td>
	</tr>
	 <tr>
		<td width="20%" align="left">代码</td>
		<td align="left">
            <input id="code" name="code" type="text"  size="50"
			       class="easyui-validatebox x-text"  
			       data-options="required:false"
				   value="${district.code}"/>
		</td>
	</tr> 
	
	<tr>
		<td width="20%" align="left">显示顺序</td>
		<td align="left">
			<input id="sort" name="sortNo" type="text"  size="5"
			       class="easyui-numberspinner"  
				   increment="1"  
				   value="${district.sortNo}"/>&nbsp;(同级区域顺序越大越靠前)
		</td>
	</tr>
	 
	<tr>
		<td width="20%" align="left">是否启用</td>
		<td align="left">
			 <select  id="locked" name="locked">
				<option value="0" selected>启用
				<option value="1">禁用
		    </select>
			 <script type="text/javascript">
			    jQuery("#locked").val("${district.locked}");
			 </script>
		</td>
	</tr>

	<tr>
	    <td width="20%" align="left"></td>
		<td align="left" >
            <br><input type="button" value=" 保存 " onclick="javascript:saveData();" class="btnGreen">
		</td>
	</tr>
    </tbody>
  </table>
 </form>
<p>&nbsp;</p>
</div>
</div>
</body>
</html>
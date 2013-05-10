<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<style>.required{color:red}</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/apps/contract.do?method=saveContract',
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

	function saveAsData(){
		document.getElementById("id").value="";
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/apps/contract.do?method=saveContract',
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

	function saveForm(){
		var params = jQuery("#iForm").formSerialize();
		//alert(params);
		if(confirm("确定保存数据吗？")){
		   document.iForm.action="<%=request.getContextPath()%>/apps/contract.do?method=save";
           document.iForm.submit();
		}
	}

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑合同</span>
	 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
	 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">

  <form:form id="iForm" name="iForm" method="post" modelAttribute="contract"  
        action="${request.contextPath}/apps/contract.do?method=save">
  <c:if test="${not empty contract}">
  <form:input path="id" type="hidden"  value="${contract.id}"/>
  </c:if>
  <table class="easyui-form" style="width:600px;" align="center">
    <tbody>
	<tr>
		<td width="20%" align="left">合同名称&nbsp;<span class="required">*</span></td>
		<td align="left">
            <form:input path="contactName"   type="text" 
			       class="easyui-validatebox" data-options="required:true"/>
				             
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">项目名称&nbsp;<span class="required">*</span></td>
		<td align="left">
            <form:input path="projrctName"  type="text" 
			       class="easyui-validatebox" data-options="required:true" />
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">我方签约单位</td>
		<td align="left">
            <form:input path="companyName"   type="text" 
			       class="easyui-validatebox"   />
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">对方签约单位</td>
		<td align="left">
            <form:input path="suppliserName"   type="text" 
			       class="easyui-validatebox"  />
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">结算币种</td>
		<td align="left">
			<form:input path="currency"   type="text" 
			       class="easyui-numberspinner"  
				   increment="10"   />
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">合同金额</td>
		<td align="left">
            <form:input path="contractSum"   type="text" 
			       class="easyui-validatebox" />
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">付款方式</td>
		<td align="left">
			<form:input path="payType"   type="text" 
			       class="easyui-numberspinner" 
				   increment="10" />
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">备注</td>
		<td align="left">
            <form:input path="remarks"   type="text" 
			       class="easyui-validatebox" />
		</td>
	</tr>
	 
	<tr>
		<td width="20%" align="left">申请人</td>
		<td align="left">
            <form:input path="appUser"   type="text" 
			       class="easyui-validatebox"  />
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">申请时间</td>
		<td align="left">
			<form:input path="appDate"   type="text" 
			       class="easyui-datebox" />
            <script type="text/javascript">
                jQuery("#appDate").val('<fmt:formatDate value="${contract.appDate}" pattern="yyyy-MM-dd"/>');
            </script>
		</td>
	</tr>
	 
	<tr>
		<td width="20%" align="left"> </td>
		<td align="left"><br>
             <input type="button" value="存储数据" onclick="javascript:saveForm();">
		</td>
	</tr>
 
    </tbody>
  </table>
 </form:form>
</div>
</div>
</body>
</html>
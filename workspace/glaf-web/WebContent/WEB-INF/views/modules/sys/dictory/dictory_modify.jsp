<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
	String context = request.getContextPath();
	Dictory bean=(Dictory)request.getAttribute("bean");
	List list = (List)request.getAttribute("parent");
	int parent = ParamUtil.getIntParameter(request, "parent", 0);
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础平台系统</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src='<%=context%>/scripts/main.js'></script>
<script type="text/javascript" src='<%=context%>/scripts/verify.js'></script> 
</head>
<body style="margin:10px;">
<html:form id="editForm" action="${contextPath}/sys/dictory.do?method=saveModify" method="post" 
     onsubmit="return verifyAll(this);"> 
<input type="hidden" name="id" value="<%=bean.getId()%>">
<div class="easyui-panel" title="修改数据字典" style="width:450px;padding:10px">  
 <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      
      <tr>
        <td width="21%" class="input-box">名称&nbsp;<font color="red">*</font></td>
        <td width="79%">
		<input type="text" name="name" value="<%=bean.getName()%>" datatype="string" nullable="no" maxsize="50"  chname="名称"
		       class="easyui-validatebox" data-options="required:true" >
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">代码</td>
        <td>
		<input type="text" name="code" class="easyui-validatebox" datatype="string" nullable="yes" maxsize="50" 
		       value="<%=bean.getCode() != null ? bean.getCode() : ""%>" 
		       chname="代码">
		</td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">属性值</td>
        <td>
		<input type="text" name="value" class="easyui-validatebox" datatype="string" nullable="yes" maxsize="20" 
		       value="<%=bean.getValue() != null ? bean.getValue() : ""%>" 
		       chname="属性值">
		</td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">描述</td>
        <td>
		<input type="text" name="desc" class="easyui-validatebox" datatype="string" nullable="yes" maxsize="20" 
		       value="<%=bean.getDesc() != null ? bean.getDesc() : ""%>" 
		       chname="描述">
		</td>
      </tr>
	 
      <c:forEach items="${list}" var="a">
	  <tr>
        <td class="input-box2" valign="top">
		${a.title}&nbsp;<c:if test="${a.required==1}"><font color="red">*</font></c:if>
		</td>
        <td>
		   <c:choose>
		     <c:when test="${a.type=='String'}">
			    <input type="text" name="${a.name}"  datatype="string" nullable="${a.nullable}" maxsize="${a.length}" chname="${a.title}" value="${a.value}"
				class="easyui-validatebox"
				<c:if test="${a.required==1}">data-options="required:true"</c:if>
				>
             </c:when>
			 <c:when test="${a.type=='Date'}">
			    <input type="text" name="${a.name}" class="easyui-datebox" datatype="datetime" nullable="${a.nullable}" maxsize="${a.length}" chname="${a.title}" 
				value="<fmt:formatDate value="${a.value}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				<c:if test="${a.required==1}">data-options="required:true"</c:if>
				>&nbsp;
				 
             </c:when>
			 <c:when test="${a.type=='Long'}">
			    <input type="text" name="${a.name}" datatype="integer" nullable="${a.nullable}" maxsize="12" chname="${a.title}" value="${a.value}"
				class="easyui-validatebox"
				<c:if test="${a.required==1}">data-options="required:true"</c:if>
				>
             </c:when>
			 <c:when test="${a.type=='Double'}">
			    <input type="text" name="${a.name}" datatype="double" nullable="${a.nullable}" maxsize="20" chname="${a.title}" value="${a.value}"
				class="easyui-validatebox"
				<c:if test="${a.required==1}">data-options="required:true"</c:if>
				>
             </c:when>
		   </c:choose>
		
		</td>
      </tr>
	  </c:forEach>

      <tr>
        <td class="input-box2" valign="top">是否有效</td>
        <td>
          <input type="radio" name="blocked" value="1" <%=bean.getBlocked()==1?"checked":""%>>否
          <input type="radio" name="blocked" value="0" <%=bean.getBlocked()==0?"checked":""%>>是
        </td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
           <input name="btn_save" type="submit" value="保存" class="button"></td>
      </tr>
    </table> 
 </div>
</html:form>
</body>
</html>

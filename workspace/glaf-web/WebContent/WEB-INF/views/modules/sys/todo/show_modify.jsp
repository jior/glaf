<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.todo.*" %>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="com.glaf.base.modules.todo.*"%>
<%@ page import="com.glaf.base.modules.todo.model.*"%>
<%@ page import="com.glaf.base.modules.todo.service.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    String context = request.getContextPath();
    SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
	
	Map params = com.glaf.core.util.RequestUtils.getQueryParams(request);

	TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
	String id = request.getParameter("id");
    Todo todo = bean.getTodo(Long.parseLong(id));
	pageContext.setAttribute("todo", todo);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>基础平台系统</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<script type="text/javascript" src="<%=context%>/scripts/main.js"></script>
<script type="text/javascript" src="<%=context%>/scripts/site.js"></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script type="text/javascript">
function checkForm(form){
  if(verifyAll(form)){
     return true;
  }
   return false;
}
</script>
</head>
<body id="document" style="padding-left:120px;padding-right:120px">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="修改TODO信息"> &nbsp;修改TODO信息
</div>
<br>
<html:form action="${contextPath}/sys/todo.do?method=save" method="post" onsubmit="return checkForm(this);"  > 

<input type="hidden" name="id" value="${todo.id}">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" 
       class="table table-striped table-bordered table-condensed">

      <tr>
        <td width="20%" valign="top" class="input-box2">模块名称</td>
        <td width="80%"><c:out value="${todo.moduleName}"/></td>
      </tr>
	  <tr>
        <td width="20%" valign="top" class="input-box2">角色名称</td>
        <td width="80%"><c:out value="${todo.roleCode}"/></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">标&nbsp;&nbsp;题*</td>
        <td>
		<input name="title" type="text" size="30"  class="input span5 x-text" 
		       value="${todo.title}" datatype="string" nullable="no" minsize="6" maxsize="50" chname="标  题">
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">内&nbsp;&nbsp;容*</td>
        <td>
          <input name="content" type="text" size="30"  class="input span5 x-text" datatype="string" 
		             value="${todo.content}" nullable="no" maxsize="255" chname="内容"> </td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">期&nbsp;&nbsp;限*</td>
        <td>
          <input name="limitDay" type="text" size="30"  class="input span2 x-text" datatype="int" 
		             value="${todo.limitDay}" nullable="no" maxsize="2" chname="期限"> 天
		  </td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">a*</td>
        <td>
          <input name="xa" type="text" size="30"  class="input span2 x-text" datatype="int" 
		             value="${todo.xa}" nullable="no" maxsize="3" chname="a"> 小时
		  </td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">b*</td>
        <td>
          <input name="xb" type="text" size="30"  class="input span2 x-text" datatype="int" 
		             value="${todo.xb}" nullable="no" maxsize="3" chname="b"> 小时
		  </td>
      </tr>

	 <tr>
     <td class="input-box2" valign="top">是否启用</td>
     <td>
	   <input type="radio" name="enableFlag" value="1" <c:if test="${todo.enableFlag == 1}">checked</c:if>>启用&nbsp;&nbsp;
	   <input type="radio" name="enableFlag" value="0" <c:if test="${todo.enableFlag == 0}">checked</c:if>>禁用
     </td>
   </tr>
  </table>

  <div align="center">
     <input name="btn_save2" type="submit" value="保存" class="btn btn-primary">
  </div>

</html:form>

</body>
</html>

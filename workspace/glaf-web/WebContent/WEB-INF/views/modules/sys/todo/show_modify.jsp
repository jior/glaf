<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*" %>
<%@ page import="com.glaf.base.modules.todo.*"%>
<%@ page import="com.glaf.base.modules.todo.model.*"%>
<%@ page import="com.glaf.base.modules.todo.service.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="com.glaf.base.modules.*" %>
<%@ page import="com.glaf.base.modules.todo.service.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    String context = request.getContextPath();
    SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
	
	Map params = com.glaf.core.util.RequestUtils.getQueryParams(request);

	TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
	String id = request.getParameter("id");
    ToDo todo = bean.getToDo(Long.parseLong(id));
	pageContext.setAttribute("todo", todo);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>基础平台系统</title>
<link href="../css/site.css" rel="stylesheet" type="text/css">
<link href="<%=context%>/css/site.css" rel="stylesheet" type="text/css">
<script src="<%=context%>/scripts/main.js" language="javascript"></script>
<style type="text/css"> 
@import url("<%=context%>/scripts/hmenu/skin-yp.css");
.STYLE1 {color: #FF0000}
</style>
<script type="text/javascript">
_dynarch_menu_url = "<%=context%>/scripts/hmenu";
</script>
<script type="text/javascript" src="<%=context%>/scripts/hmenu/hmenu.js"></script>
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
<body onLoad="DynarchMenu.setup('menu1', { context: true});" id="document">
<jsp:include page="/WEB-INF/views/module/header.jsp" flush="true"/>
<div class="nav-title">修改TODO信息</div>

<html:form action="${contextPath}/sys/todo.do?method=save" method="post" onsubmit="return checkForm(this);"  > 

<input type="hidden" name="id" value="${todo.id}">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lt">&nbsp;</td>
        <td class="box-mt">&nbsp;</td>
        <td class="box-rt">&nbsp;</td>
      </tr>
     </table>
	</td>
  </tr>
  <tr>
    <td class="box-mm">
	  <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
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
		<input name="title" type="text" size="30" class="input" 
		       value="${todo.title}" datatype="string" nullable="no" minsize="6" maxsize="50" chname="标  题">
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">内&nbsp;&nbsp;容*</td>
        <td>
          <input name="content" type="text" size="30" class="input" datatype="string" 
		             value="${todo.content}" nullable="no" maxsize="255" chname="内容"> </td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">期&nbsp;&nbsp;限*</td>
        <td>
          <input name="limitDay" type="text" size="30" class="input" datatype="int" 
		             value="${todo.limitDay}" nullable="no" maxsize="2" chname="期限"> 天
		  </td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">a*</td>
        <td>
          <input name="xa" type="text" size="30" class="input" datatype="int" 
		             value="${todo.xa}" nullable="no" maxsize="3" chname="a"> 小时
		  </td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">b*</td>
        <td>
          <input name="xb" type="text" size="30" class="input" datatype="int" 
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

      <tr>
	    <td>&nbsp;&nbsp;</td>
        <td align="left" valign="bottom" height="30">&nbsp;
          <br><input name="btn_save2" type="submit" value="保存" class="button">
		</td>
      </tr>
    </table>
	</td>
  </tr>
  <tr>
    <td>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lb">&nbsp;</td>
        <td class="box-mb">&nbsp;</td>
        <td class="box-rb">&nbsp;</td>
      </tr>
     </table>
	</td>
  </tr>
</table>
</html:form>

</body>
</html>

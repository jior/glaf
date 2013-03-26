<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int nodeId=ParamUtil.getIntParameter(request, "nodeId", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础平台系统</title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/scripts/jquery.min.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>

<script type="text/javascript">
   function clearItems(){
	   <c:forEach items="${list}" var="a">
		   $('#${a.name}_title').val('');
	   </c:forEach>
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title">字典管理</span>&gt;&gt;字典设置</div>
<html:form action="${contextPath}/sys/dictoryDefinition.do?method=save" method="post" onsubmit="return verifyAll(this);">
<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}">
<input type="hidden" id="target" name="target" value="${target}">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lt">&nbsp;</td>
        <td class="box-mt">&nbsp;</td>
        <td class="box-rt">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="box-mm"><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
	  <tr class="box">
        <td class="input-box2" width="20%">&nbsp;名称</td>
        <td class="input-box2" width="20%">&nbsp;类型</td>
        <td class="input-box2" width="20%">&nbsp;必填</td>
		<td class="input-box2" width="40%">&nbsp;标题</td>
      </tr>
	  <c:forEach items="${list}" var="a">
      <tr>
        <td width="20%"  >${a.name}</td>
        <td width="20%"  >
		   <c:choose>
		     <c:when test="${a.type=='String'}">
			    字符串
             </c:when>
			 <c:when test="${a.type=='Date'}">
			    日期
             </c:when>
			 <c:when test="${a.type=='Long'}">
			    长整型
             </c:when>
			 <c:when test="${a.type=='Double'}">
			    数值型
             </c:when>
		   </c:choose>
		</td>
		<td width="20%"  >
		  <input type="checkbox" name="${a.name}_required" value="${a.required}" >
		</td>
		<td width="40%"  >
		  <input type="hidden" id="${a.name}_name" name="${a.name}_name" value="${a.name}">
		  <input type="text" id="${a.name}_title" name="${a.name}_title"  value="${a.title}" class="input" length="50" datatype="string" nullable="yes" maxsize="50" chname="标题">
		</td>
      </tr>
     </c:forEach>
 
      <tr>
        <td colspan="4" align="center" valign="bottom" height="30">&nbsp;
            <input name="btn_save3" type="submit" value="保存" class="button">
			<input name="clearItems" type="button" value="全部清空" class="button" onclick="javascript:clearItems();">
			
		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lb">&nbsp;</td>
        <td class="box-mb">&nbsp;</td>
        <td class="box-rb">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form> 
</body>
</html>

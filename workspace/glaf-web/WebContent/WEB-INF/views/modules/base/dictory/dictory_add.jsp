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
int parent=ParamUtil.getIntParameter(request, "parent", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础平台系统</title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/scripts/calendar/skins/aqua/theme.css"/>
<script language="javascript" src="<%=request.getContextPath()%>/scripts/calendar/calendar.js" ></script>
<script language="javascript" src="<%=request.getContextPath()%>/scripts/calendar/lang/calendar-en.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/scripts/calendar/calendar-setup.js"></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script></head>
</head>

<body>
<div class="nav-title"><span class="Title">字典管理</span>&gt;&gt;增加字典</div>
<html:form action="${contextPath}/base/dictory.do?method=saveAdd" method="post" onsubmit="return verifyAll(this);">
<input type="hidden" name="nodeId" value="<%=parent%>">
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
      <tr>
        <td width="21%" class="input-box">名称&nbsp;<font color="red">*</font></td>
        <td width="79%">
		<input type="text" name="name" class="input" datatype="string" nullable="no" maxsize="50" chname="名称">
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">代码</td>
        <td>
		<input type="text" name="code" class="input" datatype="string" nullable="yes" maxsize="20" chname="代码">
		</td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">描述</td>
        <td>
		<input type="text" name="desc" class="input" datatype="string" nullable="yes" maxsize="20" chname="描述">
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
			    <input type="text" name="${a.name}" class="input" datatype="string" nullable="${a.nullable}" maxsize="${a.length}" chname="${a.title}" value="${a.value}">
             </c:when>
			 <c:when test="${a.type=='Date'}">
			    <input type="text" name="${a.name}" class="input" datatype="datetime" nullable="${a.nullable}" maxsize="${a.length}" chname="${a.title}" 
				value="<fmt:formatDate value="${a.value}" pattern="yyyy-MM-dd HH:mm:ss"/>">&nbsp;
				<img src="<%=request.getContextPath()%>/images/calendar.png"
			     id="f_trigger_${a.name}" style="cursor: pointer; border: 1px solid red;" />
				  <script type="text/javascript">
				    Calendar.setup({
							inputField     :    "${a.name}",     // id of the input field
							ifFormat       :    "%Y-%m-%d %H:%M:%S",      // format of the input field
							button         :    "f_trigger_${a.name}",  // trigger for the calendar (button ID)
							align          :    "Bl",           // alignment (defaults to "Bl")
							singleClick    :    true,
							showsTime      :    true
					});
				  </script>
             </c:when>
			 <c:when test="${a.type=='Long'}">
			    <input type="text" name="${a.name}" class="input" datatype="integer" nullable="${a.nullable}" maxsize="12" chname="${a.title}" value="${a.value}">
             </c:when>
			 <c:when test="${a.type=='Double'}">
			    <input type="text" name="${a.name}" class="input" datatype="double" nullable="${a.nullable}" maxsize="20" chname="${a.title}" value="${a.value}">
             </c:when>
		   </c:choose>
		
		</td>
      </tr>
	  </c:forEach>
	   
      <tr>
        <td class="input-box2" valign="top">是否有效</td>
        <td>
          <input type="radio" name="blocked" value="1">否
          <input type="radio" name="blocked" value="0" checked>是
        </td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save3" type="submit" value="保存" class="button"></td>
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

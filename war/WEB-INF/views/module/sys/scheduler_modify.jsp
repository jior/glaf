<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.job.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
 String contextPath = request.getContextPath();
%>
<html>
<head>
<title>任务调度管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/js/calendar/skins/aqua/theme.css"/>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/css/site.css" >
<script language="javascript" src='<%=contextPath%>/js/main.js'></script>
<script language="javascript" src='<%=contextPath%>/js/verify.js'></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar/calendar.js" ></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar/lang/calendar-en.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar/calendar-setup.js"></script>
<style type="text/css"> 
@import url("<%=contextPath%>/js/hmenu/skin-yp.css");
.STYLE1 {color: #FF0000}
</style>
<script type="text/javascript">
_dynarch_menu_url = "<%=contextPath%>/js/hmenu";
</script>
<script type="text/javascript" src="<%=contextPath%>/js/hmenu/hmenu.js"></script>
<script language="javascript">
  String.prototype.trim = function() {
     return this.replace(/(^\s*)|(\s*$)/g, "");
  }

 function check_integer(xx){
	 if (!(event.keyCode==45 || (event.keyCode>=48 && event.keyCode<=57))) {
		alert("该字段只能输入数字！");
		return false;
	 }
	var x = xx.value*1;
	if(x >2147483647 || x <-2147483648){
		 return false;
	 }
	 return true;
 }

 function submitRequest() {
    var taskName = document.schedulerForm.taskName.value.trim();
	var title = document.schedulerForm.title.value.trim();

    if(taskName == "")	 {
		alert("任务名称不能为空！");
		document.schedulerForm.taskName.focus();
		return;
	 }

	 if(title == "")	 {
		alert("任务主题不能为空！");
		document.schedulerForm.title.focus();
		return;
	 }

     document.schedulerForm.submit();
 }

</script>
<body onLoad="DynarchMenu.setup('menu1', { context: true});" id="document">
<jsp:include page="/WEB-INF/views/module/header.jsp" flush="true"/>
<br><br>
<html:form action="/sys/scheduler.do?method=saveModify" method="post"  onsubmit="return verifyAll(this);">
<input type="hidden" name="status" value="0">
<c:if test="${not empty scheduler.id}">
<input type="hidden" name="id" value="${scheduler.id}">
</c:if>

<div class="nav-title"><span class="Title">调度管理</span>&gt;&gt;修改调度</div>
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
    <td class="box-mm" width="95%">

<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="25%" height="27">名称&nbsp;*</td>
		<td height="27"><input name="taskName" size="50" maxlength="50"
			class="input" value="${scheduler.taskName}" datatype="string" nullable="no" maxsize="50" chname="调度名称">
		</td>
	</tr>

	<tr>
		<td width="25%" height="27">主题&nbsp;*</td>
		<td height="27"><input name="title" size="50" class="input"
			maxlength="255" value="${scheduler.title}" datatype="string" nullable="no" maxsize="50" chname="调度主题">
		</td>
	</tr>

	<tr>
		<td width="25%" height="27">内容&nbsp;&nbsp;</td>
		<td height="27"><textarea name="content" rows="8" cols="39"
			class="input"><c:out value="${scheduler.content}" /></textarea>
		</td>
	</tr>

	<tr>
		<td width="25%" height="27">任务类名&nbsp;</td>
		<td height="27">
			<select id="jobClass" name="jobClass" nullable="no" chname="任务类名">
			<%
			  Properties props =  JobProperties.getProperties();
			  Enumeration<?> e = props.keys();
			  while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = props.getProperty(key);
			%>
				<option value="<%=key%>"><%=value%></option>
			<%}%>
			</select>
			<script type="text/javascript">
			    document.getElementById("jobClass").value="${scheduler.jobClass}";
			</script>
		</td>
	</tr>

   <tr>
		<td width="25%" height="60">时间表达式&nbsp;</td>
		<td height="60" ><input name="expression" size="50"
			class="input" maxlength="255"
			value="${scheduler.expression}">
			<br />&nbsp;(可不填,可以参考<a href="<%=request.getContextPath()%>/quartz.txt">quartz</a>文件)
			<br>示例：每周一到周五凌晨5点执行一次（ 0 0 5 ? * MON-FRI  ）
			<br>  每天早上6点、中午1点和下午5点各执行一次（ 0 0 6,13,17 * * ?  ）
		    </td>
	</tr>

	<tr>
		<td width="25%" height="27">开始日期&nbsp;&nbsp;</td>
		<td height="27"><input id="startDate" name="startDate" size="18"
			type="text" class="input" required="true"
			value="<fmt:formatDate value="${scheduler.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />&nbsp;
		<img src="<%=request.getContextPath()%>/images/calendar.png"
			id="f_trigger_1" style="cursor: pointer; border: 1px solid red;" />
		</td>
	</tr>

	<tr>
		<td width="25%" height="27">结束日期&nbsp;&nbsp;</td>
		<td height="27"><input id="endDate" name="endDate" size="18"
			type="text" class="input" required="true"
			value="<fmt:formatDate value="${scheduler.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />&nbsp;
		<img src="<%=request.getContextPath()%>/images/calendar.png"
			id="f_trigger_2" style="cursor: pointer; border: 1px solid red;" />
		</td>
	</tr>

	<tr>
		<td width="25%" height="27">重复次数&nbsp;</td>
		<td height="27"><input name="repeatCount" size="18"
			class="input" maxlength="255"
			value="${scheduler.repeatCount}"
			onKeyPress="return check_integer(this);">
		&nbsp;(-1代表不限制重复次数)</td>
	</tr>

	<tr>
		<td width="25%" height="27">间隔时间&nbsp;</td>
		<td height="27"><input name="repeatInterval" size="18"
			class="input" maxlength="255"
			value="${scheduler.repeatInterval}"
			onKeyPress="return check_integer(this);"> &nbsp;
		(以秒计算，必须是大于0的整数)</td>
	</tr>

	<tr>
		<td width="25%" height="27">是否自动启动&nbsp;</td>
		<td height="27">
		<c:choose>
			<c:when test="${scheduler.autoStartup == 1}">
				<input type="radio" name="autoStartup" value="1" checked>是
				<input type="radio" name="autoStartup" value="0">否
			</c:when>
			<c:otherwise>
				<input type="radio" name="autoStartup" value="1">是
				<input type="radio" name="autoStartup" value="0" checked>否
			</c:otherwise>
		</c:choose>
		</td>
	</tr>

	<tr>
		<td width="25%" height="27">是否启用&nbsp;</td>
		<td height="27">
		<c:choose>
			<c:when test="${scheduler.locked == 0}">
				<input type="radio" name="locked" value="0" checked>是
				<input type="radio" name="locked" value="1">否
			</c:when>
			<c:otherwise>
				<input type="radio" name="locked" value="0">是
				<input type="radio" name="locked" value="1" checked>否
			</c:otherwise>
		</c:choose>
		</td>
	</tr>

      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save" type="submit" value="保存" class="button"></td>
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

<script language="javascript">

 Calendar.setup({
			inputField     :    "startDate",     // id of the input field
			ifFormat       :    "%Y-%m-%d %H:%M:%S",      // format of the input field
			button         :    "f_trigger_1",  // trigger for the calendar (button ID)
			align          :    "Bl",           // alignment (defaults to "Bl")
			singleClick    :    true,
			showsTime      :    true
	});


 Calendar.setup({
			inputField     :    "endDate",     // id of the input field
			ifFormat       :    "%Y-%m-%d %H:%M:%S",      // format of the input field
			button         :    "f_trigger_2",  // trigger for the calendar (button ID)
			align          :    "Bl",           // alignment (defaults to "Bl")
			singleClick    :    true,
			showsTime      :    true
	});


</script>
 
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<script language="JavaScript">
  function formSubmit() {
    document.typeForm.submit();
  }
</script>

<body leftmargin="0" topmargin="0"  marginheight="0" marginwidth="0">
<center>
  <br>
  <center>
    <span class="subject">
	<img src="<%=request.getContextPath()%>/workflow/images/title.gif" alt="发送邮件"> 
	发送邮件
	</span>
</center>
<form name="typeForm" method="post" action="<%=request.getContextPath()%>/workflow/processMonitorController.jspa">
<input type="hidden" name="method" value="sendMail" >
<input type="hidden" name="processInstanceId" value="<c:out value="${processInstanceId}"/>" >
<table class="table-border" align="center" cellpadding="4" cellspacing="1" width="90%">
      <tbody>
       <tr class="beta">
        <td width="12%" height="12" align="center"><b>主题</b></td>
        <td class="table-content" width="88%">
		<input type="text" name="title" value="<c:out value="${title}"/>" size="70" class="txt"></td>
       </tr>
	   <tr class="beta">
        <td width="12%" height="12" align="center"><b>内容</b></td>
        <td class="table-content" width="88%">
		<textarea name="content" rows="10" cols="60"><c:out value="${content}"/></textarea>
       </tr>
       <tr>
        <td class="beta" colspan="5" valign="top">
          <div align="center">
            <input type="button" value="发 送" class="button" name="submit252" onclick="javacsript:formSubmit();">          　
            <input type="button" value="关 闭" class="button" name="submit253" onclick="javacsript:window.close();">        　
          </div>
        </td>
      </tr>
    </tbody>
   </table>
  </form>
</center>
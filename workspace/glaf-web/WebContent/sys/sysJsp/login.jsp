<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld" %>
<%@ taglib prefix="logic" uri="/sys/sysTld/struts-logic.tld" %>
<%@ taglib prefix="pri" uri="/sys/sysTld/privilegeTag.tld" %>

<html>
  <head>    
    <title>广汽三菱  GMMC订单指示系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<LINK rel=stylesheet type=text/css href="<%=request.getContextPath()%>/sys/sysCss/menu/css.css">
	<LINK rel=stylesheet type=text/css href="<%=request.getContextPath()%>/sys/sysCss/menu/style-sys.css">
	<LINK rel=stylesheet type=text/css href="<%=request.getContextPath()%>/sys/sysCss/menu/style-custom.css">
  </head>
  <script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>
  
<body bgcolor="#C5C1AA">
	<html:form method="POST" action="login.do">
	<input type="hidden" value="runLogin" name="actionMethodId">
<div align="center">
  <table width="99%" border="0" cellspacing="0" cellpadding="0">
   <tr align="center" valign="middle">
    <td height="630" valign="middle" background="<%=request.getContextPath()%>/sys/sysImages/bg.gif">
      <div align="center">
        <table width="496" height="361" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td background="<%=request.getContextPath()%>/sys/sysImages/loginbg.png"><div align="center">
              <table width="294" height="231" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td colspan="2"><div align="right"></div></td>
                </tr>
                <tr>
                  <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                  <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                  <td width="73"><div align="left">用户名</div></td>
                  <td width="221"><html:text property="userId" onkeydown="if(event.keyCode==13)doSubmit(); "/></td>
                </tr>
                <tr>
                  <td><div align="left">密码</div></td>
                  <td><html:password property="password" onkeydown="if(event.keyCode==13)doSubmit(); "/></td>
                </tr>
                <tr>
                  <td colspan="2"><div align="center">
                    
                    <img name="login" onClick="doSubmit();" src="<%=request.getContextPath()%>/sys/sysImages/loginbt.gif" width="60" height="20">
                  &nbsp;
                    
                    <img name="reset" onClick="doReset();" src="<%=request.getContextPath()%>/sys/sysImages/cz.gif" width="60" height="20">
                  </div></td>
                </tr>
              </table>
            </div></td>
          </tr>
        </table>
    </div></td>
   </tr>
   <tr>
    <td class="menuxia" height="35"><div align="center">&copy; 2012 广汽三菱公司 版权所有</div></td>
   </tr>
  </table>
</div>
</html:form>

<script type="text/javascript" language="Javascript">    
	function doSubmit(){
	    objFrm = document.forms[0];
	   
	    clearErrorColor();
		if (validateLoginForm(objFrm)){
			submitForm(objFrm,"<bean:message key='baseSample.doubleSubmit'/>");
		}
	}	
	function doReset(){
	    document.getElementsByName("password")[0].value="";
	    document.getElementsByName("userId")[0].value="";
	    
	}	
	
</script>

</body>
<html:javascript formName="loginForm"/>
<jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>  
</html>

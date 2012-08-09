<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
SysRole bean=(SysRole)request.getAttribute("bean");
List  list = (List)request.getAttribute("parent");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<link type="text/css" href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/verify.js" language="javascript"></script>
</head>

<body>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <th width="100%" valign="top"><div align="left">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0" background="<%=request.getContextPath()%>/images/content_title_bg.jpg">
        <tr>
          <td width="200" height="45" valign="bottom"><table width="100%" height="100%"  border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td width="25"><div align="center"><img src="<%=request.getContextPath()%>/images/content_lt.jpg" width="11" height="34"></div></td>
                  <td><span class="style2">角色管理</span></td>
            </tr>
          </table></td>
          <td valign="middle"><table width="95%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><div align="right"><img src="<%=request.getContextPath()%>/images/icon_1.jpg" width="11" height="12"> 
                      <span class="font">修改角色</span></div></td>
            </tr>
          </table></td>
        </tr>
      </table>
	    <html:form action="/sys/role.do?method=saveModify" method="post"  onsubmit="return verifyAll(this);"> 
		<input type="hidden" name="id" value="<%=bean.getId()%>">
        <table width="95%" align="center" border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td width="100%"> <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="90%" class="tdborder"> <table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td height="30"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                              <td width="20" align="center"><img src="<%=request.getContextPath()%>/images/icon_6.jpg" width="6" height="7"></td>
                              <td class="fontlist" align="right">角色名称：</td>
                              <td>&nbsp; <input name="name" type="text" size="28" class="textbox" value="<%=bean.getName()%>" datatype="string" nullable="no" maxsize="20" chname="角色名称"></td>
                            </tr>
                            <tr> 
                              <td align="center" valign="top"><img src="<%=request.getContextPath()%>/images/icon_6.jpg" width="6" height="7"></td>
                              <td class="fontlist" align="right" valign="top">描　　述：</td>
                              <td>&nbsp; <textarea name="desc" cols="35" rows="6" class="textbox" datatype="string" nullable="yes" maxsize="100" chname="描述"><%=bean.getDesc()%></textarea>                              </td>
                            </tr>
                            <tr>
                              <td align="center"><img src="<%=request.getContextPath()%>/images/icon_6.jpg" width="6" height="7"></td>
                              <td class="fontlist" align="right">等　　级：</td>
                              <td><span class="fontlist"> &nbsp;
                                    <input type="radio" name="grade" value="0" <%=bean.getGrade()==0?"checked":""%>>
                                普通角色
                                <input type="radio" name="grade" value="1" <%=bean.getGrade()==1?"checked":""%>>
                                部门管理员</span></td>
                            </tr>
                            <tr> 
                              <td align="center">&nbsp;</td>
                              <td class="fontlist" align="right">&nbsp;</td>
                              <td>&nbsp; <input name="btn_save" type="submit" value="保存" class="butt-normal"></td>
                            </tr>
                          </table>
                          
                        </td>
                      </tr>
                    </table></td>
                </tr>
              </table></td>
          </tr>
        </table>
        </html:form> </div></th>
  </tr>
</table>
</body>
</html>

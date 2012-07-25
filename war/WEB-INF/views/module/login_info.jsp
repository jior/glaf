<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%
SysUser user = (SysUser)session.getAttribute(SysConstants.LOGIN);
Iterator iter = user.getNestingDepartment().iterator();
while(iter.hasNext()){
  SysDepartment bean = (SysDepartment)iter.next();
  out.print(bean.getName() + "\\");
}
out.print(user.getName());
%>
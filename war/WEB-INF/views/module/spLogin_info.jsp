<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%
SysUser user = (SysUser)session.getAttribute(SysConstants.LOGIN);
List list = null;
if(user != null){
	list = user.getNestingDepartment();
	if(list != null){
		Iterator iter = list.iterator();
		while(iter.hasNext()){
		  SysDepartment bean = (SysDepartment)iter.next();
		  out.print(bean.getName() + "\\");
		}
	}
	out.print(user.getName());
}
%>
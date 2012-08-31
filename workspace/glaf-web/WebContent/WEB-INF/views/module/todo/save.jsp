<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.todo.*"%>
<%@ page import="com.glaf.base.modules.todo.model.*"%>
<%@ page import="com.glaf.base.modules.todo.service.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="org.jpage.util.*" %>
<%@ page import="org.jpage.core.query.paging.*" %>
<%@ page import="com.glaf.base.modules.*" %>
<%@ page import="com.glaf.base.modules.todo.service.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    String context = request.getContextPath();
    SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
	if(!"root".equals(user.getAccount())){
		return;
	}
	Map params = org.jpage.util.RequestUtil.getQueryParams(request);

	TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
	String id = request.getParameter("id");
	String enableFlag = request.getParameter("enableFlag");
	String limitDay = request.getParameter("limitDay");
	String xa = request.getParameter("xa");
	String xb = request.getParameter("xb");
    ToDo todo = bean.getToDo(new  Long(id).longValue());
	 todo.setTitle(request.getParameter("title"));
	 todo.setContent(request.getParameter("content"));
	
	 try{
		  todo.setEnableFlag(new Integer(enableFlag).intValue());
		  todo.setLimitDay(new Integer(limitDay).intValue());
		  todo.setXa(new Integer(xa).intValue());
		  todo.setXb(new Integer(xb).intValue());
	      bean.update(todo);
	 }catch(Exception ex){
%>
<script language="JavaScript">
     alert("操作失败！");
	 location.href="todo_edit_list.jsp";
</script>
<%
	return;
	 }
%>
<script language="JavaScript">
     alert("修改成功！");
	 location.href="todo_edit_list.jsp";
</script>
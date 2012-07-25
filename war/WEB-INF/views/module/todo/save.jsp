<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
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
    SysUser user = (SysUser) request.getSession().getAttribute(SysConstants.LOGIN);
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
     alert("����ʧ�ܣ�");
	 location.href="todo_edit_list.jsp";
</script>
<%
	return;
	 }
%>
<script language="JavaScript">
     alert("�޸ĳɹ���");
	 location.href="todo_edit_list.jsp";
</script>
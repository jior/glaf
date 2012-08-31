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
<%@ page import="org.jpage.jbpm.task.*" %>
<%@ page import="org.jpage.jbpm.model.*" %>
<%@ page import="org.jpage.jbpm.service.ProcessContainer" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
 
    long start = System.currentTimeMillis();
	TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
	 try{
		     bean.createTasksFromSQL();
			 bean.createTasksFromWorkflow();
	 } catch(Exception ex){
		 ex.printStackTrace();
	 }
%>
<script language="JavaScript">
     location.href="todo_user_list.jsp";
</script>
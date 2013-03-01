<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.glaf.core.todo.*" %>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="com.glaf.base.modules.todo.*"%>
<%@ page import="com.glaf.base.modules.todo.model.*"%>
<%@ page import="com.glaf.base.modules.todo.service.*"%>
<%@ page import="com.glaf.base.modules.todo.util.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
      TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
	  if(bean != null){
		  bean.sendMessageToAllUsers();
		  out.println("execute finished");
	  } else {
		  out.println("bean is null");
	  }
%>
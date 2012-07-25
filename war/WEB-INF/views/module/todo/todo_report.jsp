<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jpage.util.*" %>
<%@ page import="org.jpage.context.*" %>
<%@ page import="org.jpage.core.task.*" %>
<%@ page import="org.jpage.core.task.model.*" %>
<%@ page import="org.jpage.core.mail.*" %>
<%@ page import="org.jpage.core.mail.model.*" %>
<%@ page import="org.jpage.services.*" %>
<%@ page import="org.jpage.persistence.*" %>
<%@ page import="org.jpage.component.chart.*" %>
<%@ page import="org.jpage.core.query.paging.Page" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.awt.*" %>
<%@ page import="org.jfree.ui.*" %>
<%@ page import="org.jfree.chart.*" %>
<%@ page import="org.jfree.chart.urls.*"%> 
<%@ page import="org.jfree.chart.labels.*" %> 
<%@ page import="org.jfree.chart.entity.*"%> 
<%@ page import="org.jfree.chart.imagemap.*" %> 
<%@ page import="org.jfree.chart.axis.*" %>
<%@ page import="org.jfree.chart.plot.*" %>
<%@ page import="org.jfree.chart.title.*" %>
<%@ page import="org.jfree.data.*" %>
<%@ page import="org.jfree.data.general.*"%> 
<%@ page import="org.jfree.data.category.*" %>
<%@ page import="org.jfree.chart.renderer.category.*" %>
<%@ page import="org.jfree.chart.servlet.ServletUtilities"%> 
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
    SysUser user = (SysUser) request.getSession().getAttribute(SysConstants.LOGIN);
%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/dojo.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/button.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/styles.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/WEB-INF/views/module/todo/dojo.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/WEB-INF/views/module/todo/styles.css" type="text/css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dojo/dojo.js"></script>
<script language="JavaScript" type="text/javascript">
	dojo.require("dojo.widget.*");
</script>
<script language="JavaScript">
    function changeiFrame(url){
		var iframe = document.getElementById("iframe");
		if(iframe != null){
		    iframe.src=url;
		}
    }
</script>
<style>
html, body {
	height: 100%;
	width: 100%;
	overflow: hidden;
}
#main {
	height: 97%;
	width: 98%;
	left: 1%;
	top: 1%;
	position: relative;
}

</style>
<body leftMargin=0 topMargin=0 marginwidth="0" marginheight="0">
<div dojoType="LayoutContainer"
	 layoutChildPriority='none'
	 style="border: 1px solid #eeeeee;padding: 1px; margin: 1px;" id="main">
	<div dojoType="ContentPane" layoutAlign="top" 
	     style="background-color: #eeeeee;  margin: 1px;">
		  <input type="button" name="btn02" class="bigbutton" 
		         style="height:40px;width:90px;font-size:13px;"
				 value="全部" 
				 onclick="changeiFrame('todoxy.jsp');">
		  <input type="button" name="btn02" class="bigbutton" 
		         style="height:40px;width:90px;font-size:13px;"
				 value="按模块分组" 
				 onclick="changeiFrame('todo_module.jsp');">
		  <!-- <input type="button" name="btn02" class="bigbutton" 
		         style="height:40px;width:90px;font-size:13px;"
				 value="按用户分组" 
				 onclick="changeiFrame('todo_user.jsp');">
		  <input type="button" name="btn02" class="bigbutton" 
		         style="height:40px;width:90px;font-size:13px;"
				 value="按部门分组" 
				 onclick="changeiFrame('todo_dept.jsp');">
		  <input type="button" name="btn02" class="bigbutton" 
		         style="height:40px;width:90px;font-size:13px;"
				 value="按角色分组" 
				 onclick="changeiFrame('todo_role.jsp');">
			<input type="button" name="btn02" class="bigbutton" 
		         style="height:40px;width:90px;font-size:13px;"
				 value="用户统计图" 
				 onclick="changeiFrame('report_by_user.jsp');">
		  <input type="button" name="btn02" class="bigbutton" 
		         style="height:40px;width:90px;font-size:13px;"
				 value="部门统计图" 
				 onclick="changeiFrame('report_by_dept.jsp');">
		  <input type="button" name="btn02" class="bigbutton" 
		         style="height:40px;width:90px;font-size:13px;"
				 value="角色统计图" 
				 onclick="changeiFrame('report_by_role.jsp');">
		  <input type="button" name="btn02" class="bigbutton" 
		         style="height:40px;width:90px;font-size:13px;"
				 value="TODO统计图" 
				 onclick="changeiFrame('report_by_todo.jsp');"> -->
		   <%if("root".equals(user.getAccount())){%>
		   <input type="button" name="btn02" class="bigbutton" 
		         style="height:40px;width:90px;font-size:13px;"
				 value="TODO维护" 
				 onclick="changeiFrame('todo_edit_list.jsp');">
		  <%}%>
		  <input type="button" name="btn02" class="bigbutton" 
		         style="height:40px;width:90px;font-size:13px;"
				 value="我的工作台" 
				 onclick="location.href='<%=request.getContextPath()%>/sys/frame.do';">
	</div>
	<div dojoType="ContentPane" layoutAlign="bottom" style="background-color: #eeeeee; margin: 1px;">	
	</div>
	<div dojoType="ContentPane" layoutAlign="client" style="background-color: #eeeeee; padding: 1px; margin: 1px;">
	<iframe id="iframe" src="todo_list.jsp" height="100%" width="100%" frameborder="0"/>
	</div>
</div>
</body>
</html>

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
      MailSender mailSender = (MailSender) JbpmContextFactory.getBean("mailSender");
	  MailMessage m = new MailMessage();
	  m.setTo("jior2008@gmail.com");
	  m.setSubject("测试");
	  m.setText("测试");
      mailSender.send(m);
%>
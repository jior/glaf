<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.glaf.jbpm.context.*" %>
<%@ page import="com.glaf.jbpm.mail.*" %>
<%@ page import="com.glaf.core.mail.service.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
      MailSender mailSender = (MailSender) JbpmContextFactory.getBean("mailSender");
	  MailMessage m = new MailMessage();
	  m.setTo("joy.huang@gzgi.com");
	  m.setSubject("测试");
	  m.setText("测试");
      mailSender.send(m);
%>
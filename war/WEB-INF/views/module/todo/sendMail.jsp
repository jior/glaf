<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jpage.jbpm.context.*" %>
<%@ page import="org.jpage.jbpm.mail.*" %>
<%@ page import="org.jpage.core.mail.service.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
      MailSender mailSender = (MailSender) JbpmContextFactory.getBean("mailSender");
	  MailMessage m = new MailMessage();
	  m.setTo("joy.huang@gzgi.com");
	  m.setSubject("测试");
	  m.setText("测试");
      mailSender.send(m);
%>
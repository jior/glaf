<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.mail.config.*"%>
<%
       String contextPath = request.getContextPath();
	   String host = request.getParameter("host");
	   String port = request.getParameter("port");
	   String mailFrom = request.getParameter("mailFrom");
	   String username = request.getParameter("username");
	   String password = request.getParameter("password");
	   String text = request.getParameter("text");
	   String encoding = request.getParameter("encoding");
	   String actionType = request.getParameter("actionType");
	   String auth = request.getParameter("auth");
	   
	   MailConfig cfg = new MailConfig();
	   cfg.setHost(host);
	   cfg.setPort(Integer.parseInt(port));
	   cfg.setMailFrom(mailFrom);
	   cfg.setUsername(username);
	   cfg.setPassword(password);
	   cfg.setEncoding(encoding);
	   cfg.setText(text);

	   if("true".equals(auth)){
            cfg.setAuth(true);
	   }
       if("reconfig".equals(actionType)){
		   try{
               cfg.config();
%>
<font color="green" size="5"><br>如果您收到测试邮件，则说明邮件服务已经配置成功，否则请重新配置。 <br>
如要配置生效，请重新启动应用服务器！ </font>
<%
		   }catch(Exception ex){
	        ex.printStackTrace();
%>
<font color="red" size="4">配置失败：<%=ex.getMessage()%></font>
<%
         }
	   } else {
		     try{
		        cfg.check();
%>
<font color="green" size="5">发送邮件成功！</font>
<%
		   }catch(Exception ex){
%>
<font color="red" size="4">测试失败：<%=ex.getMessage()%></font>
<%
         }
	   }
%>

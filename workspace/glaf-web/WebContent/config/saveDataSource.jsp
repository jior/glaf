<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.setup.conf.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%
	    String contextPath = request.getContextPath();
	    String type = request.getParameter("type");
	    String host = request.getParameter("host");
	    String port = request.getParameter("port");
	    String url = request.getParameter("url");
 	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    String actionType = request.getParameter("actionType");
	    String databaseName = request.getParameter("databaseName");
	    String datasourceName = request.getParameter("datasourceName");
 	   
 	    String appPath = com.glaf.core.context.ApplicationContext.getAppPath();
        DatabaseConfig cfg = new DatabaseConfig();
	    List<Database> rows = cfg.getDatabases(appPath);
        Database database = null;
	    for(Database db : rows){
	     if(db.getName() != null && db.getName().equals(type)){
            database = db;
			if(datasourceName !=null && datasourceName.length() > 0 ){
				database.setDatasourceName(datasourceName);
			}
			database.setHost(host);
			if(port != null && port.trim().length()>0 &&
			   Integer.valueOf(port).intValue() > 0 && Integer.valueOf(port).intValue() < 65536){
			   database.setPort(Integer.valueOf(port).intValue());
			}
			if(url != null && url.trim().length()>0){
				database.setUrl(url);
			}
			database.setUsername(username);
			database.setPassword(password);
			database.setDatabaseName(databaseName);
			break;
		  }
		}

       if("reconfig".equals(actionType)){
		   try{
			   cfg.check(appPath, database);
               cfg.reconfig(appPath, database);
%>
              <font color="green" size="5">配置成功，请重新启动应用服务器！ </font>
<%
		   } catch(Exception ex){
	          ex.printStackTrace();
%>
<font color="red" size="4">配置失败：<%=ex.getMessage()%></font>
<%
         }
	   } else {
		     try{
		        cfg.check(appPath, database);
%>
             <font color="green" size="5">测试成功！</font>
<%
		   } catch(Exception ex){
%>
            <font color="red" size="4">测试失败：<%=ex.getMessage()%></font>
<%
         }
	   }
%>

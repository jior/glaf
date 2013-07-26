<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.hibernate.SessionFactory"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.base.entity.hibernate.*"%>
<%

        long start = System.currentTimeMillis();
		String systemName = request.getParameter(Environment.CURRENT_SYSTEM_NAME);
		if(systemName !=null){
			System.out.println("systemName:"+systemName);
			Environment.setCurrentSystemName(systemName);
			HibernateBeanFactory.reload();
			SessionFactory sessionFactory = HibernateBeanFactory.getSessionFactory();
			out.println(sessionFactory);
			out.println("<br>成功！" );
		}
	    long times = System.currentTimeMillis() - start;
	    System.out.println("总共耗时(毫秒):" + times);
		Map dataSourceProperties = DBConfiguration.getDataSourceProperties();

%>
<form method="post" action="">
	<table>
	 <tr>
          <td style="white-space: nowrap"><b>系统名：</b>&nbsp;&nbsp;</td>
          <td style="width: 200px; white-space: nowrap">
          <select name="<%=Environment.CURRENT_SYSTEM_NAME%>">
          <option value="<%=Environment.DEFAULT_SYSTEM_NAME%>" selected>默认</option>		
           <%
		    dataSourceProperties.remove(Environment.DEFAULT_SYSTEM_NAME);
		    Iterator iter = dataSourceProperties.values().iterator();
          	while(iter.hasNext()){
          		Properties p =(Properties)iter.next();
          		String tile = p.getProperty("subject");
          		String name = p.getProperty(DBConfiguration.JDBC_NAME);
           %>
            <option value="<%=name%>"><%=tile != null ? tile : name%></option>
            <%
            }
           %>
           </select>
           <input type="submit" value="确定">
      </td>
	</tr>
	</table>
</form>
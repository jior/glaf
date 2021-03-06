<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.hibernate.SessionFactory"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.entity.hibernate.*"%>
<%

        long start = System.currentTimeMillis();
		String systemName = request.getParameter(Environment.CURRENT_SYSTEM_NAME);
		if(systemName !=null){
			System.out.println("systemName:"+systemName);
			Environment.setCurrentSystemName(systemName);
			HibernateBeanFactory.reload();
			SessionFactory sessionFactory = HibernateBeanFactory.getSessionFactory();
			out.println(sessionFactory);
			out.println("<br><span style=\"color:green\">成功！</span>" );
		}
	    long times = System.currentTimeMillis() - start;
	    System.out.println("总共耗时(毫秒):" + times);
		Map dataSourceProperties = DBConfiguration.getDataSourceProperties();

%>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<center>
<form method="post" action="<%=request.getContextPath()%>/mx/sys/table/initHB">
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
           <input type="submit" value=" 确定 "  class="button">
      </td>
	</tr>
 </table>
</form>
</center>
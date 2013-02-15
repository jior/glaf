<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="org.htmlparser.*" %>
<%@ page import="org.htmlparser.tags.*" %>
<%@ page import="org.htmlparser.filters.*" %>
<%@ page import="org.htmlparser.util.*" %>
<%@ page import="org.apache.commons.httpclient.*" %>
<%@ page import="org.apache.commons.httpclient.methods.*" %>
<%@ page import="org.apache.commons.httpclient.params.*" %>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.modules.*" %>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="com.glaf.base.modules.todo.model.*" %>
<%@ page import="com.glaf.base.modules.todo.service.*" %>
<%
        StringBuffer buffer = new StringBuffer();
		String todoId = request.getParameter("todoId");
		String actorId = request.getParameter("actorId");
		String rowIds = request.getParameter("rowIds");

        PostMethod postMethod = new PostMethod("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/sys/authorize.do?method=login&type=x");
        SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
        HttpClient client = new HttpClient();
		client.setConnectionTimeout(800);
        postMethod.addParameter("%%ModDate","0000000000000000");
        postMethod.addParameter("account", user.getAccount());
        postMethod.addParameter("password", user.getPassword());
        try {
			       TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
                   ToDo todo = bean.getToDo(new Long(todoId).longValue());
				   String link = todo.getListLink();
			       int statusCode = client.executeMethod(postMethod);
				   postMethod = new PostMethod("http://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath()+link);
                   postMethod.addParameter("x_resultType", "xml");
				   postMethod.addParameter("todoId", todoId);
				   postMethod.addParameter("actorId", actorId);
                   postMethod.addParameter("rowIds", rowIds);
				   statusCode = client.executeMethod(postMethod);
				   InputStream in = postMethod.getResponseBodyAsStream();
				   if (in != null) {
					    byte[] bytes = org.jpage.util.FileTools.getBytes(in);
						Parser parser = Parser.createParser(new String(bytes, "GBK"), "UTF-8");
					    NodeFilter xfilter = new TagNameFilter("div");     
						NodeList nodes = parser.extractAllNodesThatMatch(xfilter);						
						if(nodes != null){
							  for(int i=0;i<nodes.size();i++){
									  Div div = (Div)nodes.elementAt(i);
									  String id = div.getAttribute("id");
									  if("listDiv".equals(id)){
										  buffer.append(div.toHtml());
										  break;
									  }
							  }
						}
				   }
         } catch (HttpException ex) {
            System.err.println("Fatal protocol violation: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("Fatal transport error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>基础平台系统</title>
<script language="javascript" src='<%=request.getContextPath()%>/scripts/verify.js'></script>
<script language="javascript" src='<%=request.getContextPath()%>/scripts/main.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/hmenu/hmenu.js"></script>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/scripts/calendar/skins/aqua/theme.css"  type="text/css" title="Aqua" rel="stylesheet"/>
<style type="text/css"> 
@import url("<%=request.getContextPath()%>/scripts/hmenu/skin-yp.css");
</style>
<script type="text/javascript">
    _dynarch_menu_url = "<%=request.getContextPath()%>/scripts/hmenu";
</script>
<br><br>
<center>
<table id="screen" width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
  <tr>
    <td>
      <%=buffer.toString()%>
    </td>
  </tr>
</table>
</center>
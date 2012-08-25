<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
 
    long start = System.currentTimeMillis();
	
	Map rowMap = new LinkedHashMap();
	rowMap.put("a", "重财未生效");
    rowMap.put("b", "采购申请未审批");
	rowMap.put("c", "合同未签署完毕");
	rowMap.put("d", "供应商、价格未决");
	rowMap.put("e", "验收未完毕");
	rowMap.put("f", "支付未完毕");

	Map dataMap = new HashMap();
	List r01 = new ArrayList();
	r01.add(new Long(6000));
	r01.add(new Long(6001));
	r01.add(new Long(6002));
	r01.add(new Long(6003));
	r01.add(new Long(6004));
	r01.add(new Long(6006));
    dataMap.put("a", r01);

	List r02 = new ArrayList();
	//采购申请
	r02.add(new Long(1001));
	r02.add(new Long(1002));
	r02.add(new Long(1003));
	r02.add(new Long(1004));
	r02.add(new Long(1005));
	r02.add(new Long(1006));
	r02.add(new Long(1007));
	r02.add(new Long(1008));
	r02.add(new Long(1009));
	r02.add(new Long(1021));
	r02.add(new Long(1022));
	r02.add(new Long(1023));
	//采购申请变更
	r02.add(new Long(2001));
	r02.add(new Long(2002));
	r02.add(new Long(2003));
	r02.add(new Long(2004));
	r02.add(new Long(2005));
	r02.add(new Long(2006));
	r02.add(new Long(2007));
	r02.add(new Long(2008));
	r02.add(new Long(2009));
	r02.add(new Long(2021));
	r02.add(new Long(2022));
	r02.add(new Long(2023));
    //采购申请废止
	r02.add(new Long(3001));
	r02.add(new Long(3002));
	r02.add(new Long(3003));
	r02.add(new Long(3004));
	r02.add(new Long(3005));
	r02.add(new Long(3006));
	r02.add(new Long(3007));
	r02.add(new Long(3008));
	r02.add(new Long(3009));
    dataMap.put("b", r02);

    List r03 = new ArrayList();
	r03.add(new Long(8001));
	r03.add(new Long(8002));
	r03.add(new Long(8003));
	r03.add(new Long(8004));
	r03.add(new Long(8005));
	r03.add(new Long(8006));
	r03.add(new Long(8007));
	r03.add(new Long(8008));
	r03.add(new Long(9001));
	r03.add(new Long(9002));
	r03.add(new Long(9003));
	r03.add(new Long(9004));
    dataMap.put("c", r03);

	List r04 = new ArrayList();
	r04.add(new Long(1009));
	r04.add(new Long(2009));
	r04.add(new Long(3007));
	r04.add(new Long(3008));
	r04.add(new Long(3009));
	r04.add(new Long(5001));
	r04.add(new Long(5002));
	r04.add(new Long(5003));
	r04.add(new Long(5004));
	r04.add(new Long(5005));
	r04.add(new Long(5006));
	r04.add(new Long(5007));
    dataMap.put("d", r04);

	List r05 = new ArrayList();
	r05.add(new Long(7001));
	r05.add(new Long(7002));
	r05.add(new Long(7004));
    dataMap.put("e", r05);

	List r06 = new ArrayList();
	r06.add(new Long(15001));
    r06.add(new Long(16001));
	r06.add(new Long(17001));
	r06.add(new Long(18001));
	r06.add(new Long(19001));
	r06.add(new Long(20001));
    dataMap.put("f", r06);

    String mx = request.getParameter("mx");
	if(mx == null){
	    mx = "a";
	}

    org.jpage.util.RequestUtil.setRequestParameterToAttribute(request);
	Map params = org.jpage.util.RequestUtil.getQueryParams(request);

        StringBuffer buffer = new StringBuffer();
		String todoId = request.getParameter("todoId");
		String actorId = request.getParameter("actorId");
		String rowIds = request.getParameter("rowIds");

        PostMethod postMethod = new PostMethod("http://"+request.getServerName()+":"+request.getServerPort()+"<%=request.getContextPath()%>/sys/authorize.do?method=login&type=x");
        SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
        HttpClient client = new HttpClient();
		client.setConnectionTimeout(800);
        postMethod.addParameter("%%ModDate","0000000000000000");
        postMethod.addParameter("account", user.getAccount());
        postMethod.addParameter("password", user.getPassword());
		TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
        try {
			      
                   ToDo todo = bean.getToDo(new Long(todoId).longValue());
				   request.setAttribute("todo", todo);
				   String link = todo.getListLink();
			       int statusCode = client.executeMethod(postMethod);
				   postMethod = new PostMethod("http://"+request.getServerName()+":"+request.getServerPort()+"<%=request.getContextPath()%>"+link);
                   //postMethod.addParameter("x_resultType", "xml");
				   postMethod.addParameter("todoId", todoId);
				   postMethod.addParameter("actorId", actorId);
                   postMethod.addParameter("rowIds", rowIds);
				   statusCode = client.executeMethod(postMethod);
				   InputStream in = postMethod.getResponseBodyAsStream();
				   if (in != null) {
					    byte[] bytes = org.jpage.util.FileTools.getBytes(in);
						Parser parser = Parser.createParser(new String(bytes, "UTF-8"), "UTF-8");
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
        } catch (Exception ex) {
            System.err.println(" error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }


      Map userMap = bean.getUserMap();
	  SysUser userx = (SysUser)userMap.get(actorId);
      if(userx != null){
		  SysDepartment deptx = (SysDepartment)userx.getDepartment();
		  pageContext.setAttribute("deptx", deptx);
		  pageContext.setAttribute("userx", userx);
		  if(deptx != null){
              SysDepartment parentDept =  bean.getParentDepartment(deptx.getId());
			  pageContext.setAttribute("parentDept", parentDept);
		  }
	 }

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Todo List</title>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=request.getContextPath()%>/js/verify.js'></script>
<script language="javascript" src='<%=request.getContextPath()%>/js/main.js'></script>
<script language="JavaScript">
function selDept(parent, refer){
  var ret = selectDeptList2(parent);
  if(ret==null) return;
  if(refer)refer.value=ret[1];
  $("query_deptId").value=ret[0];
}
</script>
<body>
<br>
<form name="PurchaseForm" method="post" action="todo_user_list.jsp" >
<input type="hidden" name="method" value="showList" />
<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" >
      <tr class="box">
        <td class="box-lt">&nbsp;</td>
        <td class="box-mt">&nbsp;</td>
        <td class="box-rt">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="box-mm"><table  border="0" align="center" cellpadding="5" cellspacing="0" >
      <tr>
        <td nowrap  class="input-box"> 部门
          <input name="query_deptId" type="text" class="input" size="20" value=""  readonly onClick="selDept(5, this)">
        </td>
        <td  class="input-box"> 用户
          <input name="query_actorName" type="text" class="input" size="20" value="">
        </td>
        <td><input type="hidden" name="mx" value="<%=mx%>">
              <input name="Submit" type="submit" value=" " class="submit-search">
        </td>
      </tr>
    </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="line">
	    <tr>
		 <td>
		<table  width="90%" border="0" cellspacing="1" cellpadding="0">
				<tr>
				  <td height="30"><br>
				  <font size="4" color="#0066FF">
				  <b> <center>
				          <c:out value="${parentDept.name}"/>
				          <c:out value="${deptx.name}"/>
                          <c:out value="${userx.name}"/>
                          <c:out value="${todo.title}"/>
						  </center>
					</b>
				  </font> 
				  </td>
				 </tr>
		</table>
		 </td>
		 </tr>
          <tr>
            <td><table width="90%" border="0" cellspacing="1" cellpadding="0" align="center">
              <tr>
                <td>&nbsp;
                    <table border="0" cellspacing="0" cellpadding="0" class="tabs-box">
                      <tr>
                        <%
					    Iterator iterator = rowMap.keySet().iterator();
						while(iterator.hasNext()){
							String mxid = (String)iterator.next();
							String title = (String)rowMap.get(mxid);
							int lengthx = title.length() * 15;
							if(mxid.equals(mx)){
					%>
                        <td class="tab-lc">&nbsp;</td>
                        <td width="<%=lengthx%>" class="tab-mc"><a href="todo_user_list.jsp?mx=<%=mxid%>&query_deptId=<c:out value="${query_deptId}"/>&query_actorName=
                          <c:out value="${query_actorName}"/>
                          "><%=title%></a> </td>
                        <td class="tab-rc">&nbsp;</td>
                        <%}else{%>
                        <td class="tab-l">&nbsp;</td>
                        <td width="<%=lengthx%>" class="tab-m"><a href="todo_user_list.jsp?mx=<%=mxid%>&query_deptId=<c:out value="${query_deptId}"/>&query_actorName=
                          <c:out value="${query_actorName}"/>
                          "><%=title%></a> </td>
                        <td class="tab-r">&nbsp;</td>
                        <%
				            }
						}
				  %>
                      </tr>
                    </table>
                    <table align="center" width="100%" border="0" cellspacing="1" cellpadding="0">
                      <tr>
                        <td>
						<%
						   if(buffer.length() > 0){
						        out.println(buffer.toString());
						    } else {
                                out.println("<br><br><br><center>没有数据<br><br><br></center>");
							}%>
						</td>
                      </tr>
                  </table></td>
              </tr>
            </table></td>
          </tr>
        </table>
      </td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lb">&nbsp;</td>
        <td class="box-mb">&nbsp;</td>
        <td class="box-rb">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>

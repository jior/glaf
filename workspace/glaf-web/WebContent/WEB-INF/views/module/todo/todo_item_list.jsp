<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<%@ page import="org.jpage.jbpm.task.*" %>
<%@ page import="org.jpage.jbpm.model.*" %>
<%@ page import="org.jpage.jbpm.service.ProcessContainer" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    String context = request.getContextPath();
    String actorId = request.getParameter("actorId");
	TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
	
      Map userMap = bean.getUserMap();
	  SysUser user = (SysUser)userMap.get(actorId);
      if(user != null){
		  SysDepartment dept = (SysDepartment)user.getDepartment();
		  if(dept != null){
              SysDepartment parentDept =  bean.getParentDepartment(dept.getId());
			  pageContext.setAttribute("parentDept", parentDept);
		  }
		  pageContext.setAttribute("dept", dept);
		  pageContext.setAttribute("user", user);
	 }

    Collection rows = bean.getTodoInstances(actorId);
	//System.out.println("@@@@@@@@@rows size:"+ rows.size());
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
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Todo</title>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<form name="PurchaseForm" method="get" action="todo_user_list.jsp" target="_self">
<input type="hidden" name="method" value="showList" />
<br>
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
            <td>&nbsp;</td>
          </tr>
        </table>

      <table style="width:620px; " width="80%" border="0" cellspacing="1" cellpadding="0" align="center">
	     <tr>
		 <td>
		<table align="center" width="100%" border="0" cellspacing="1" cellpadding="0">
				  <tr><td height="30"><br>
				  <font size="5" color="#0066FF">
				  <b><c:out value="${parentDept.name}"/> <c:out value="${dept.name}"/> <c:out value="${user.name}"/></b>
				  </font> 
				  </td></tr>
		</table>
		 </td>
		 </tr>
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
                  <td width="<%=lengthx%>" class="tab-mc">
				    <a href="todo_user_list.jsp?mx=<%=mxid%>&query_deptId=<c:out value="${query_deptId}"/>&query_actorName=<c:out value="${query_actorName}"/>"><%=title%></a> 
				  </td>
                  <td class="tab-rc">&nbsp;</td>
                            <%}else{%>
                  <td class="tab-l">&nbsp;</td>
                  <td width="<%=lengthx%>" class="tab-m">
				    <a href="todo_user_list.jsp?mx=<%=mxid%>&query_deptId=<c:out value="${query_deptId}"/>&query_actorName=<c:out value="${query_actorName}"/>"><%=title%></a> 
				  </td>
                  <td class="tab-r">&nbsp;</td>
                  <%
				            }
						}
				  %>
                </tr>
                </table>
                <div id="listDivx" style="width:620px; height:500px;overflow-x:auto; overflow-y:auto;">
                  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">                    
					<tr class="list-title">
                      <td align="center" class="title">用户</td>
                      <td width="125" align="center" class="title">PastDue</td>
                      <td width="125" align="center" class="title">Caution</td>
                    </tr>
                    <%if(rows != null && rows.size()> 0){
							  Iterator iterator008 = rows.iterator();
							  while(iterator008.hasNext()){
								   ToDoInstance tdi = (ToDoInstance) iterator008.next();
								    /*	System.out.println(tdi.getToDo().getId()+"\tPastDue:"+tdi.getQty03()+"\tCaution:"+tdi.getQty02()+"\tOK:"+tdi.getQty01()+"\t"
																   +tdi.getToDo().getTitle());

                                   System.out.println(tdi.getToDo().getId()+"\tPastDue:"+tdi.getToDo().getPastDue()+"\tCaution:"+tdi.getToDo().getCaution()+"\tOK:"+tdi.getToDo().getOk()+"\t"
																   +tdi.getToDo().getTitle());
								   */
								   List todoIds = (List)dataMap.get(mx);
								   if( todoIds == null || !todoIds.contains(new Long(tdi.getToDo().getId()))){
									   continue;
								   }
								   if(tdi.getQty01() == 0 && tdi.getQty02() == 0 && tdi.getQty03() == 0){
									   continue;
								   }
								   if(tdi.getQty02() == 0 && tdi.getQty03() == 0){
									   continue;
								   }

								   Collection rowIds = new HashSet();
                                   rowIds.addAll(tdi.getToDo().getPastDue());
								   rowIds.addAll(tdi.getToDo().getCaution());
								  
								   pageContext.setAttribute("todo", tdi.getToDo());
								   pageContext.setAttribute("tdi",tdi);

								   StringBuffer sb = new StringBuffer();
								   Iterator it55 = rowIds.iterator();
								   while(it55.hasNext()){
									   String xid = (String)it55.next();
									   sb.append(xid);
									   if(it55.hasNext()){
										   sb.append(",");
									   }
								   }

							  %>
                  <tr class="list-a">
                    <td height="20" align="left">
					  <a href="<%=context%>/module/todo/clientxy.jsp?actorId=<%=actorId%>&mx=<%=mx%>&todoId=<c:out value="${todo.id}"/>&rowIds=<%=sb.toString()%>" 
                      title="<c:out value="${todo.title}"/>-<c:out value="${todo.content}"/>" target="_blank"><c:out value="${todo.title}"/>(<c:out value="${todo.content}"/>) </a> 
					  </td>
                    <td align="center" class="red"><c:out value="${tdi.qty03}"/>
                    </td>
                    <td align="center" class="yellow"><c:out value="${tdi.qty02}"/>
                    </td>
                  </tr>
                  <%     }
					}
				  %>
                  </table>
              </div>
			  </td>
          </tr>
      </table></td>
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

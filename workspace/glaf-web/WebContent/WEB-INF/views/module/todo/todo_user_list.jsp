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
 
    long start = System.currentTimeMillis();
	TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
	
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

    List todoIds = (List)dataMap.get(mx);
    params.put("todoIds", todoIds);
	params.put("orderBy", "deptId");
	Collection todoList = bean.getToDoInstanceList(params);
    //System.out.println("todoList size:"+todoList.size());
	Map userMap = bean.getUserMap();
	Collection rows = new ArrayList();

	if(todoList != null && todoList.size() > 0){
			Map xmap = new LinkedHashMap();
			Iterator iterator = todoList.iterator();
			while(iterator.hasNext()){
				ToDoInstance tdi = (ToDoInstance)iterator.next();
				 int status = TodoConstants.getTodoStatus(tdi);
				 //System.out.println(" create :"+ tdi.getStartDate() + "\t caution :"+ tdi.getAlarmDate()+ "\t past due:"+ tdi.getPastDueDate());
				 if(status == TodoConstants.OK_STATUS){
					 continue;
				 }
				//System.out.println("status:"+status);
				String actorIdx = tdi.getActorId();
				if(actorIdx != null){
						 ToDoInstance xx = (ToDoInstance)xmap.get(actorIdx);
						 if(xx == null){
							xx = new ToDoInstance();
							xx.setActorId(actorIdx);
							SysUser u = (SysUser)userMap.get(actorIdx);
							if(u != null){
								 xx.setActorName(u.getName());
							}else{
								xx.setActorName(actorIdx);
							}
						 }
					   switch(status){
					    case TodoConstants.OK_STATUS:
							xx.setQty01(xx.getQty01()+1);
							break;
						case TodoConstants.CAUTION_STATUS:
							xx.setQty02(xx.getQty02()+1);
							break;
						case TodoConstants.PAST_DUE_STATUS:
							xx.setQty03(xx.getQty03()+1);
							break;
						default:
							break;
				       }
				     xmap.put(actorIdx, xx);		
				 } else {
                        String appId = tdi.getAppId()+"";
						Collection actorIds = bean.getAppActorIds(appId);
						if(actorIds != null && actorIds.size() > 0){
							Iterator iter005 = actorIds.iterator();
							while(iter005.hasNext()){
                                 actorIdx = (String)iter005.next();
								 ToDoInstance xx = (ToDoInstance)xmap.get(actorIdx);
								 if(xx == null){
									xx = new ToDoInstance();
									xx.setActorId(actorIdx);
									SysUser u = (SysUser)userMap.get(actorIdx);
									if(u != null){
										 xx.setActorName(u.getName());
									}else{
										xx.setActorName(actorIdx);
									}
								 }
							   switch(status){
								case TodoConstants.OK_STATUS:
									xx.setQty01(xx.getQty01()+1);
									break;
								case TodoConstants.CAUTION_STATUS:
									xx.setQty02(xx.getQty02()+1);
									break;
								case TodoConstants.PAST_DUE_STATUS:
									xx.setQty03(xx.getQty03()+1);
									break;
								default:
									break;
							   }
							 xmap.put(actorIdx, xx);	
							}
						}
				 }
		    }
	     rows = xmap.values();
	}

    long end = System.currentTimeMillis();
    //System.out.println("总共用时:" + (end - start) + " ms.");

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
<form name="PurchaseForm" method="get" action="?" target="_self">
<input type="hidden" name="method" value="showList" />
<br>
<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
  <tr>
    <td>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" >
      <tr class="box">
        <td class="box-lt">&nbsp;</td>
        <td class="box-mt">&nbsp;</td>
        <td class="box-rt">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="box-mm">
	<table  border="0" align="center" cellpadding="5" cellspacing="0" >
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
				    <a href="?mx=<%=mxid%>&query_deptId=<c:out value="${query_deptId}"/>&query_actorName=<c:out value="${query_actorName}"/>"><%=title%></a> 
				  </td>
                  <td class="tab-rc">&nbsp;</td>
                            <%}else{%>
                  <td class="tab-l">&nbsp;</td>
                  <td width="<%=lengthx%>" class="tab-m">
				    <a href="?mx=<%=mxid%>&query_deptId=<c:out value="${query_deptId}"/>&query_actorName=<c:out value="${query_actorName}"/>"><%=title%></a> 
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
					<td align="center">用户</td>
					<td width="125" align="center">PastDue</td>
					<td width="125" align="center">Caution</td>
				  </tr>
				  <%if(rows != null && rows.size()> 0){
						  Iterator iterator008 = rows.iterator();
						  while(iterator008.hasNext()){
							   ToDoInstance tdi = (ToDoInstance)iterator008.next();
							   pageContext.setAttribute("tdi", tdi);
							   SysUser user = (SysUser)userMap.get(tdi.getActorId());
							   if(user != null){
								   SysDepartment dept = (SysDepartment)user.getDepartment();
								   pageContext.setAttribute("dept", dept);
								   SysDepartment parentDept =  bean.getParentDepartment(dept.getId());
			                       pageContext.setAttribute("parentDept", parentDept);
							   }
					  %>
				  <tr class="list-a">
					<td height="20" align="left"><c:out value="${parentDept.name}"/>&nbsp;<c:out value="${dept.name}"/>
					  &nbsp; <a href="todo_item_list.jsp?actorId=<c:out value="${tdi.actorId}"/>&mx=<%=mx%>">
					  <c:out value="${tdi.actorName}"/>
					</a> </td>
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

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
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    Map rowMap = new LinkedHashMap();
    rowMap.put("8881", "采购申请");
	rowMap.put("8884", "供应商管理");
	rowMap.put("8885", "定厂定价");
    rowMap.put("8886", "重财管理");
	rowMap.put("8887", "验收管理");
	rowMap.put("8888", "合同管理");
	rowMap.put("8889", "订单管理");
	rowMap.put("8890", "支付管理");

    String moduleId = request.getParameter("moduleId");
	String actorId = request.getParameter("actorId");
	if(moduleId == null){
	    moduleId = "8881";
	}

	Map params = org.jpage.util.RequestUtil.getQueryParams(request);
	params.put("moduleId", new Integer(moduleId));
    if(actorId != null){
	    params.put("actorId", actorId);
    }

    System.out.println("---------------------------------------------params:"+params);

   TodoJobBean bean = (TodoJobBean)BaseDataManager.getInstance().getBean("todoJobBean");
	Collection rows = bean.getToDoInstanceList(params);
	Map dataMap = new LinkedHashMap();
	Map todoMap = bean.getToDoMap();
	Map userMap = bean.getUserMap();

   System.out.println("---------------------------------------------row size:"+rows.size());

	if(rows != null && rows.size()> 0){
		    
			  Iterator iterator008 = rows.iterator();
			  while(iterator008.hasNext()){
                   ToDoInstance tdi = (ToDoInstance)iterator008.next();
				   int status = TodoConstants.getTodoStatus(tdi);
				   tdi.setStatus(status);
				   String actorIdx = tdi.getActorId();
				   String roleCode = tdi.getRoleCode();

                   if(actorIdx != null){
					   ToDoInstance xx = (ToDoInstance)dataMap.get(actorIdx);
					    if(xx == null){
						   xx = new ToDoInstance();
						   xx.setActorId(actorIdx);
						   SysUser u = (SysUser)userMap.get(actorIdx);
						   if(u != null){
							   xx.setActorName(u.getName());
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
				       dataMap.put(actorIdx, xx);
				   }
			  }
	}

	rows = dataMap.values();

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Todo</title>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">

<script type="text/JavaScript">

function checkOperation(){
 
  var num = getCheckedboxNums("id");
  if (num > 1) {
	   if ($('btn_del')) { $('btn_del').disabled = false; }
  } else if (num == 1) {
		if ($('btn_del')) { $('btn_del').disabled = false; }
	} else {
	   if ($('btn_del')) { $('btn_del').disabled = true; }
	}
}

</script>
</head>

<body>
<br>
<form name="PurchaseForm" method="get" action="?" target="_self">
<input type="hidden" name="method" value="showList" />
  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
    <tr>
      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr class="box">
          <td class="box-lt">&nbsp;</td>
          <td class="box-mt">&nbsp;</td>
          <td class="box-rt">&nbsp;</td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td class="box-mm"><table width="95%" border="0" align="center" cellpadding="5" cellspacing="0">
        <tr>
          <td nowrap  class="input-box">
            部门 <input name="query_deptName" type="text" class="input" size="20" value="">
		  </td>
          <td  class="input-box">
            用户 <input name="query_actorName" type="text" class="input" size="20" value="">
		  </td>
		  <td>
          <input type="hidden" name="moduleId" value="<%=moduleId%>">
		  <input name="Submit" type="submit" value=" " class="submit-search">
		  </td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="line">
            <tr>
              <td>&nbsp;</td>
            </tr>
        </table>
        <br>
          <table id="screen" width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="100%">
			   <table border="0" cellspacing="0" cellpadding="0" class="tabs-box">
                  <tr>    
					<%
					    Iterator iterator = rowMap.keySet().iterator();
						while(iterator.hasNext()){
							String mxid = (String)iterator.next();
							String title = (String)rowMap.get(mxid);
							if(mxid.equals(moduleId)){
					%>
					        <td class="tab-lc">&nbsp;</td>
							<td width="60" class="tab-mc"><%=title%></td>
							<td class="tab-rc">&nbsp;</td>
							<%}else{%>
							<td class="tab-l">&nbsp;</td>
							<td width="60" class="tab-m">
							<a href="todo_module.jsp?moduleId=<%=mxid%>"><%=title%></a>
							</td>
							<td class="tab-r">&nbsp;</td>
							<%}
							}
						%>
                  </tr>
                </table>
                  <div id="listDiv" style="width:980px; height:415px;overflow-x:auto; overflow-y:auto;">
                    <table border="0" cellspacing="1" cellpadding="0" class="list-box">
                      <tr>
                       <td colspan="10"  height="400" valign="top" >
					   <table align="center"  valign="top"  width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
						  <tr class="list-title">
							<td align="center">用户</td>
							<td width="95" align="center">PastDue</td>
							<td width="95" align="center">Caution</td>
							<td width="95" align="center">OK</td>
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
								   }
							  %>
						  <tr class="list-a">
							<td height="20" align="left">
							<c:out value="${dept.name}"/>&nbsp;
							<a href="todo_list.jsp?query_actorId=<c:out value="${tdi.actorId}"/>&query_moduleId=<%=moduleId%>" >
							 <c:out value="${tdi.actorName}"/>
							</a>
							</td>
							<td align="center" class="red">
								 <c:out value="${tdi.qty03}"/>
							</td>
							<td align="center" class="yellow">
								 <c:out value="${tdi.qty02}"/>
							</td>
							<td align="center" class="green">
								 <c:out value="${tdi.qty01}"/>
							</td>
						  </tr>
						  <%     }
								  }
						  %>
						</table>
					   </td>
                      </tr>
                    </table>
                </div>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="38%" height="35" valign="bottom"></td>
                      <td width="62%" valign="bottom"></td>
                    </tr>
                </table></td>
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

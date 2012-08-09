<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.glaf.base.utils.PageResult"%>
<%@ page import="com.glaf.base.utils.WebUtil"%>
<%@ page import="com.glaf.base.modules.Constants"%>
<%@ page import="com.glaf.base.modules.BaseDataManager"%>
<%@ page import="com.glaf.base.modules.purchase.model.Purchase"%>
<%@ page import="com.glaf.base.modules.finance.model.Finance"%>
<%@ page import="com.glaf.base.modules.purchase.model.PurchaseApply"%>
<%@ page import="com.glaf.base.modules.purchase.PurchaseConstants"%>
<%
String context = request.getContextPath();
int pageSize=Constants.PAGE_SIZE;
BaseDataManager bdm = BaseDataManager.getInstance();

PageResult pager = (PageResult)request.getAttribute("pager");
List list = null;
if(pager != null){
	list = pager.getResults();
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>XXXX基础平台系统</title>
<link href="../../css/site.css" type="text/css" rel="stylesheet">
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="javascript">
	//根据部门选择相应部门下的员工
	function selectContact(dept, str, obj){
		if(dept.value != ""){
			if(str == 'use'){
				selectUserByDept(dept.value, $('useDeptContact'), obj);
			}else{
				selectUserByDept(dept.value, $('dutyDeptContact'), obj);
			}
		}else{
			if(str == 'use'){
				selectAllUserList($('useDeptContact'), obj);
			}else{
				selectAllUserList($('dutyDeptContact'), obj);
			}
		}
	}

	//显示进度查询页面
	function showStatusLook(){
		var ids = document.getElementsByName("id");
		var purchaseId;
		for(var i=0; i<ids.length; i++){
			if(ids[i].checked==true){
				purchaseId = ids[i].value;
			}
		}
		openMaxWindow('<%=context%>/others/statusView.do?method=lookStatus&purchaseId=' + purchaseId);
	}
	
	function doSearch(form){
		var url = "<%=context%>/others/statusView.do?method=showList&" + getSearchElement(form);
		window.location = url;
	}
</script>
</head>

<body onLoad="DynarchMenu.setup('menu1', { context: true});" id="document">
<jsp:include page="/WEB-INF/views/module/header.jsp" flush="true"/>
<form action="<%=context%>/others/statusView.do?method=showList" method="get">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="m-box">
  <tr>
    <td width="49%" class="nav-title">项目进度管理&gt;&gt;项目进展查询列表</td>
    <td width="51%" height="27" align="right"><jsp:include page="/WEB-INF/views/module/login_info.jsp" flush="true"/> &nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>  
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
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
  
    <td class="box-mm"><table width="100%" border="0" align="center" cellpadding="5" cellspacing="0">
      <tr>
        <td width="76">采购申请单No</td>
        <td width="132"><input name="purchaseNo" type="text" class="input" size="15" searchflag="1"></td>
        <td width="57">项目名称</td>
        <td width="167"><input name="name" type="text" class="input" size="20" searchflag="1"></td>
        <td width="55">使用部门</td>
        <td width="138">
			<input name="useDeptTitle" type="text" class="input" size="15" onClick=" selectDept('5', $(useDept), this)" readonly>
			<input type="hidden" id="useDept" name="useDept" value="" searchflag="1">
		</td>
        <td width="89">使用部门经办人</td>
        <td width="119">
			<input name="useDeptContactTitle" type="text" class="input" size="15" onClick="selectContact($(useDept), 'use', this)" readonly>
			<input type="hidden" name="useDeptContact" id="useDeptContact" value="" searchflag="1">
		</td>
        <td width="57">&nbsp;</td>
      </tr>
      <tr>
        <td>重财No</td>
        <td><input name="financeNo" type="text" class="input" size="15" searchflag="1"></td>
        <td>采购担当</td>
        <td>
			<input name="chargeTitle" type="text" class="input" size="15" onClick="selectUserDuty('310','R010',$('charge'),this)" readonly>
			<input type="hidden" id="charge" name="charge" value="" searchflag="1">
		</td>
        <td>归口部门</td>
        <td>
			<input name="dutyDeptTitle" type="text" class="input" size="15" onClick="selectDept('5', $(dutyDept), this)" readonly>
			<input type="hidden" name="dutyDept" id="dutyDept" searchflag="1">
		</td>
        <td>归口部门经办人</td>
        <td>
			<input name="dutyDeptContactTitle" type="text" class="input" size="15" onClick="selectContact($(dutyDept), 'duty', this)" readonly>
			<input type="hidden" id="dutyDeptContact" name="dutyDeptContact" value="" searchflag="1">
		</td>
        <td align="right"><input name="btn_search" type="button" value=" " class="submit-search" onClick="doSearch(this.form);"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="line">
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
    <br>
    <table id="screen" width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><div id="listDiv" style="width:980px; height:270px;overflow-x:auto; overflow-y:auto;">
		  <table border="0" cellspacing="1" cellpadding="0" class="list-box">
            <tr class="list-title">
              <td width="40" align="center" nowrap class="title">选择</td>
              <td width="40" align="center" nowrap class="title">序号</td>
              <td width="90" align="center" nowrap class="title">采购方式</td>
              <td width="60" align="center" nowrap class="title">申请性质</td>
              <td width="100" align="center" nowrap class="title">采购申请单No</td>
              <td width="255" align="center" nowrap class="title">项目名称</td>
              <td width="100" align="center" nowrap class="title">采购类别</td>
              <td width="60" align="center" nowrap class="title">采购性质</td>
              <td width="100" align="center" nowrap class="title">使用部门</td>
              <td width="100" align="center" nowrap class="title">使用部门经办人</td>
              <td width="100" align="center" nowrap class="title">归口部门</td>
              <td width="100" align="center" nowrap class="title">归口部门经办人</td>
              <td width="100" align="center" nowrap class="title">重财No</td>
              <td width="80" align="center" nowrap class="title">采购担当</td>
              <td width="200" align="center" nowrap class="title">供应商</td>
            </tr>
			<%
			int i = 0;
			if(list != null && list.size()>0){
				Iterator it = list.iterator();
				while(it.hasNext()){
					Purchase purchase = (Purchase)it.next();
					if(purchase != null){
						PurchaseApply purchaseApply = purchase.getCurrentPurchaseApply();
						Finance finance = purchase.getFinance();
						String fullName = purchase.getSupplier();
						if(fullName == null){
							fullName = "";
						}
						String name1 = purchase.getName();  //中文项目名称
						String name2 = purchase.getName2(); //日文项目名称
						String name = "中文项目名：" + name1;
						if(!"".equals(name2)){
							 name += "\n" + "日文项目名：" + name2;  //"\n"__换行
						}
						String useDept = bdm.getStringValue(purchase.getUseDept(), "ZD0001"); //使用部门
						String dutyDept = bdm.getStringValue(purchase.getDutyDept(), "ZD0001"); //归口部门
			%>
            <tr <%= ((i++)%2==0)?"class=\"list-back\"":"" %>>
              <td height="22"><input type="checkbox" name="id" value="<%=purchase.getId()%>" onClick="editBtnOperation(document.forms[0],'id',$('btn_look'));">&nbsp;</td>
              <td class="td-no"><%=((pager.getCurrentPageNo()-1)*pageSize + i)%></td>
              <td align="center"><%=PurchaseConstants.getApplyType(purchase.getApplyType())%></td>
              <td align="center"><%//PurchaseConstants.getCurrentApplyType(purchaseApply.getApplyType())%>
              <%
              if(purchaseApply.getApplyType()==3){
              %>
              	<a href="javascript:showRejectReason(<%= purchase.getId() %>)"><%=PurchaseConstants.getCurrentApplyType(purchaseApply.getApplyType())%></a>
              <%
              }else{
              	out.print(PurchaseConstants.getCurrentApplyType(purchaseApply.getApplyType()));
              }
              %>
              </td>
              <td align="center"><a href="javascript:openMaxWindow('<%=context%>/purchase/purchase.do?method=showPurchase&id=<%=purchase.getId()%>');"><%=purchase.getPurchaseNo()%></a></td>
              <td title="<%=name%>"><%=name1%></td>
              <td title="<%=bdm.getStringValue(purchase.getCategory(), "ZD0003")%>"><%=bdm.getStringValue(purchase.getCategory(), "ZD0003")%></td>
              <td align="center">
              <%=bdm.getStringValue(purchase.getType(), "ZD0004")%>
              </td>
              <td title="<%=useDept%>"><%=useDept%></td>
              <td><%=bdm.getStringValue(purchase.getUseDeptContact(), "ZD0000")%></td>
              <td title="<%=dutyDept%>"><%=dutyDept%></td>
              <td><%=bdm.getStringValue(purchase.getDutyDeptContact(), "ZD0000")%></td>
              <td align="center">
			<%
			  if(finance != null){
			%>
			  <a href="javascript:openWindow('<%=context%>/finance/finance.do?method=view&id=<%=finance.getId()%>', screen.width, 650,'yes');"><%=finance.getFinanceNo()%></a>
			<%
			  }else{
				out.println("");
			  }
			%>
			  </td>
              <td><%=bdm.getStringValue(purchase.getCharge(), "ZD0000")%></td>
              <td title="<%=fullName%>"><%=fullName%></td>
            </tr>
			<%
					}
				  }
				}
				for(; i<pageSize; i++ ){			
			%>
			<tr <%= (i%2==0)?"class=\"list-back\"":"" %>>
              <td height="22">&nbsp;</td>
              <td align="center">&nbsp;</td>
              <td align="center">&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td align="center">&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
			<%
			  }
			  String params = WebUtil.getQueryString(request);
			%>
          </table>
		  </div>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="44%" height="25" valign="bottom"><!--input name="btn_release" type="button" class="button" onClick="release(this.form)" value="冻结" disabled-->
                <input id="btn_look" name="btn_status" type="button" class="button" value="查看状态" onClick="showStatusLook();" disabled></td>
              <td width="43%" align="right">
			  <jsp:include page="/WEB-INF/views/inc/show_page.jsp" flush="true">
                <jsp:param name="total" value="<%=pager.getTotalRecordCount()%>"/>            
                <jsp:param name="page_count" value="<%=pager.getTotalPageCount()%>"/>                    
                <jsp:param name="page_size" value="<%=pageSize%>"/>                    
                <jsp:param name="page_no" value="<%=pager.getCurrentPageNo()%>"/>                    
                <jsp:param name="url" value="statusView.do"/>                    
                <jsp:param name="params" value="<%=java.net.URLEncoder.encode(params)%>"/>                    
  			  </jsp:include>
              </td>
            </tr>
          </table>
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
<script language="javascript">
attachFrame();
</script>
</body>
</html>

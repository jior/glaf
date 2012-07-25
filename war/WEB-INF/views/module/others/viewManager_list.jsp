<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.glaf.base.utils.PageResult"%>
<%@ page import="com.glaf.base.utils.ParamUtil"%>
<%@ page import="com.glaf.base.utils.WebUtil"%>
<%@ page import="com.glaf.base.modules.Constants"%>
<%@ page import="com.glaf.base.modules.BaseDataManager"%>
<%@ page import="com.glaf.base.modules.purchase.model.Purchase"%>
<%@ page import="com.glaf.base.modules.budget.model.Budget" %>
<%@ page import="com.glaf.base.modules.finance.model.Finance"%>
<%@ page import="com.glaf.base.modules.purchase.model.PurchaseApply"%>
<%@ page import="com.glaf.base.modules.purchase.PurchaseConstants"%>
<%@ page import="com.glaf.base.modules.finance.FinanceConstants"%>
<%@ page import="com.glaf.base.modules.price.PriceConstants"%>
<%@ page import="com.glaf.base.modules.order.OrderConstants"%>
<%@ page import="com.glaf.base.modules.order.model.Order"%>
<%@ page import="com.glaf.base.modules.price.model.Decision"%>
<%@ page import="com.glaf.base.modules.contract.model.Contract"%>
<%@ page import="com.glaf.base.modules.contract.model.ContractApply"%>
<%@ page import="com.glaf.base.modules.contract.ContractConstants"%>
<%@ page import="com.glaf.base.modules.purchase.model.PurchaseGoods"%>

<%
String context = request.getContextPath();
int pageSize=12;
BaseDataManager bdm = BaseDataManager.getInstance();

String all = String.valueOf(request.getAttribute("all"));


PageResult pager = (PageResult)request.getAttribute("pager");
List list = (List)request.getAttribute("allList");

String parameters = "";
String purchaseNo = ParamUtil.getParameter(request, "purchaseNo", null);
if(purchaseNo != null && !"".equals(purchaseNo.trim())){
	parameters += "&purchaseNo=" + purchaseNo;
}
String name = ParamUtil.getParameter(request, "name", null);
if(name != null && !"".equals(name.trim())){
	parameters += "&name=" + name;
}
String financeNo = ParamUtil.getParameter(request, "financeNo", null);
if(financeNo != null && !"".equals(financeNo.trim())){
	parameters += "&financeNo=" + financeNo;
}
String applyType = ParamUtil.getParameter(request, "applyType", null);
if(applyType != null && !"".equals(applyType.trim())){
	parameters += "&applyType=" + applyType;
}
String category = ParamUtil.getParameter(request, "category", null);
if(category != null && !"".equals(category.trim())){
	parameters += "&category=" + category;
}
String purchaseApplyStatus = ParamUtil.getParameter(request, "purchaseApplyStatus", null);
if(purchaseApplyStatus != null && !"".equals(purchaseApplyStatus.trim())){
	parameters += "&purchaseApplyStatus=" + purchaseApplyStatus;
}
String useDept = ParamUtil.getParameter(request, "useDept", null);
if(useDept != null && !"".equals(useDept.trim())){
	parameters += "&useDept=" + useDept;
}
String useContact = ParamUtil.getParameter(request, "useContact", null);
if(useContact != null && !"".equals(useContact.trim())){
	parameters += "&useContact=" + useContact;
}
String dutyDept = ParamUtil.getParameter(request, "dutyDept", null);
if(dutyDept != null && !"".equals(dutyDept.trim())){
	parameters += "&dutyDept=" + dutyDept;
}
String charge = ParamUtil.getParameter(request, "charge", null);
if(charge != null && !"".equals(charge.trim())){
	parameters += "&charge=" + charge;
}
String financeStatus = ParamUtil.getParameter(request, "financeStatus", null);
if(financeStatus != null && !"".equals(financeStatus.trim())){
	parameters += "&financeStatus=" + financeStatus;
}
String decisionNo = ParamUtil.getParameter(request, "decisionNo", null);
if(decisionNo != null && !"".equals(decisionNo.trim())){
	parameters += "&decisionNo=" + decisionNo;
}
String orderNo = ParamUtil.getParameter(request, "orderNo", null);
if(orderNo != null && !"".equals(orderNo.trim())){
	parameters += "&orderNo=" + orderNo;
}
String contractNo = ParamUtil.getParameter(request, "contractNo", null);
if(contractNo != null && !"".equals(contractNo.trim())){
	parameters += "&contractNo=" + contractNo;
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>XXXX基础平台系统</title>
<link href="../../css/site.css" type="text/css" rel="stylesheet">
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/interface/StatusViewAjaxService.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=context%>/dwr/util.js'></script>
<script language="javascript">	
	function doSearch(form){
		try{
			var url = "<%=context%>/others/statusView.do?method=showPurchaseList&all=<%=all%>&" + getSearchElement(form);
			window.location = url;
		}catch(e){}
	}
	
	function allCheckBox(form, cbAll){
		checkAll(form, cbAll);
		var num = getCheckedBoxNum(form,"id");
		if(cbAll.checked){
			if(num>1){
				form.btnSee.disabled = true;
			}else if(num==1){
				form.btnSee.disabled = false;
			}else{
				form.btnSee.disabled = true;
			}
		}else{
			if(num>1){
				form.btnSee.disabled = true;
			}else if(num==1){
				form.btnSee.disabled = false;
			}else{
				form.btnSee.disabled = true;
			}
		}
	}
	
	
	function buttonDisabled(form){
		var num = 0;
		var cb = document.getElementsByName("id");
		for(var i=0;i<cb.length;i++){
			if(cb[i].checked==true){
			  num+=1;
			 }
		}
		if(num!=12){ //如果有一个没选中，全选框则取消全选
			form.ckAll.checked = false;
		}
	/*	if(num>=1){
			form.btnExp.disabled = false;
		}else{
			form.btnExp.disabled = true;
		}*/
		editBtnOperation(document.forms[0], 'id', document.all.btnSee);
	}
	
	function exportPurchase(form){
		if(form.ckAll.checked){
			url = "<%=context%>/others/statusView.do?method=ExportPurchaseList&all=<%=all%><%=parameters%>";
		}else{

			var purchaseId = getCheckboxValue("id");
			var url = "<%=context%>/others/statusView.do?method=ExportPurchaseList&purchaseId=" + purchaseId;
		}
		window.location.href=url;
	}
	
	
	//显示进度查询页面
	function showStatusLook(){
		var ids = document.getElementsByName("id");
		var purchaseId;
		for(var i=0; i<ids.length; i++){
			if(ids[i].checked==true){
				purchaseId = ids[i].value;
				if($("dumpFlag_" + purchaseId).value == 1){
					alert("该单未走工作流,故无法查看进度流程");
					return;
				}
			}
		}
		openMaxWindow('<%=context%>/others/statusView.do?method=lookStatus&all=<%= all%>&purchaseId=' + purchaseId);
	}
</script>
</head>

<body>
<jsp:include page="/WEB-INF/views/module/header.jsp" flush="true"/>
<form action="<%=context%>/others/statusView.do?method=showPurchaseList" method="get">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="m-box">
  <tr>
    <td width="49%" class="nav-title">进度管理看板&gt;&gt;进展查询列表</td>
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
    <td class="box-mm">
	  <table width="98%" border="0" align="center" cellpadding="2" cellspacing="0">
		<tr>
		  <td class="input-box" width="9%">采购申请单No</td>
		  <td width="16%"><input name="purchaseNo" type="text" class="input" size="15" searchflag="1"></td>
		  <td width="11%" class="input-box">重财No</td>
		  <td width="17%"><input name="financeNo" type="text" class="input" size="15" searchflag="1"></td>
		  <td width="10%" class="input-box">决裁No</td>
		  <td width="16%"><input name="decisionNo" type="text" class="input" size="15" searchflag="1"></td>
		  <td width="7%" class="input-box">订单No</td>
		  <td width="14%"><input name="orderNo" type="text" class="input" size="15" searchflag="1"></td>
		</tr>
		<tr>
		  <td class="input-box">合同No</td>
		  <td><input name="contractNo" type="text" class="input" size="15" searchflag="1"></td>
		  <td height="30" class="input-box">采购方式</td>
		  <td><select name="applyType" searchflag="1">
              <option value="-1">全部</option>
              <option value="0">基础平台</option>
              <option value="1">自助采购</option>
            </select>          </td>
		  <td class="input-box">采购类别</td>
		  <td><input name="categoryName" type="text" class="input" value="" size="12" onClick="selectGoodsCategoryList(0, document.all.category, this);" readonly>
              <input type="hidden" name="category" value="" searchflag="1">          </td>
		  <td class="input-box">采购担当</td>
		  <td><input name="chargeTitle" type="text" class="input" size="15" onClick="selectUserDuty('310','R010',$('charge'),this)" readonly>
              <input type="hidden" id="charge" name="charge" value="" searchflag="1"></td>
		</tr>
		<tr>
		  <td class="input-box">使用部门</td>
		  <td>
		  	<input name="useDeptTitle" type="text" class="input" size="15" onClick=" selectDept('5', $(useDept), this)" readonly>
			<input type="hidden" id="useDept" name="useDept" value="" searchflag="1">		  </td>
		  <td class="input-box">使用部门经办人</td>
		  <td><input name="useContactTitle" type="text" class="input" size="15" onClick="selectAllUserList($('useContact'),this)" readonly>
              <input type="hidden" id="useContact" name="useContact" value="" searchflag="1">
          </td>
		  <td class="input-box">归口管理部门</td>
		  <td><input name="dutyDeptTitle" type="text" class="input" size="15" onClick="selectDept('5', $(dutyDept), this)" readonly>
              <input type="hidden" name="dutyDept" id="dutyDept" searchflag="1">          </td>
		  <td class="input-box">重财状态</td>
		  <td><select name="financeStatus" searchflag="1">
              <option value="-1">全部</option>
              <option value="1">已审批完毕</option>
              <option value="0">未审批完毕</option>
            </select></td>
		</tr>
		<tr>
		  <td class="input-box">采购申请状态</td>
		  <td><select name="purchaseApplyStatus" searchflag="1">
              <option value="-1">全部</option>
              <option value="1">已审批完毕</option>
              <option value="0">未审批完毕</option>
          </select></td>
		  <td class="input-box">项目名称</td>
		  <td ><input name="name" type="text" class="input" size="25" searchflag="1"></td>
		  
		   <td class="input-box">预算编号</td>
		  <td colspan="3" align="left"><input name="budgetNo" type="text" class="input" size="15" searchflag="1"></td>
		  <td colspan="2" align="right"><input name="btn_search" type="button" value=" " class="submit-search" onClick="doSearch(this.form);"></td>
		  </tr>
	  </table>
	</td>
  </tr>
  <tr>
  	<td class="box-mm"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" id="screen">
	  <tr><td><hr></td></tr>
	  <tr>
	    <td align="left" height="30">
		      以字体颜色表示审批现状:<font class="line-font-#55EE55">绿色</font>-表示已经审批完毕,<font class="line-font-#E56061">红色</font>-表示未处理（呈批）完毕;&quot;——&quot;-表示没有此环节;</td>
	  </tr>
	  <tr><td>
	  	<div id="listDiv" style="width:980px; height:317px;overflow-x:auto; overflow-y:auto;">
	    <table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
      	  <tr class="list-title">
		  	<td width="30" align="center"><input type="checkbox" name="ckAll" onClick="allCheckBox(this.form, this)"></td>
        	<td width="40" align="center">序号</td>
			<td width="60" align="center">采购方式</td>
			<td width="120" align="center">采购类别</td>
        	<td width="90" align="center">采购申请单No</td>
			<td width="80" align="center">提交日期</td>
        	<td width="180" align="center">项目名称</td>
			<td width="100" align="center">使用部门</td>
			<td width="100" align="center">使用部门经办人</td>
			<td width="100" align="center">归口管理部门</td>
			<td width="80" align="center">采购担当</td>
        	<td width="65" align="center">重财No</td>
        	
        	<td width="80" align="center">决裁No</td>
        	<td width="80" align="center">预算编号</td>
        	<td width="80" align="center">订单No</td>
        	<td width="93" align="center">合同No</td>
			<td width="90" align="center">验收</td>
			<td width="90" align="center">累计支付</td>
      	  </tr>
	  	  <%
	  		int i = 0;
	  		if(list != null && list.size() > 0){
		    	Iterator it = list.iterator();
				while(it.hasNext()){
					List detailList = (List)it.next();
					if(detailList != null){
						//int detailSize = detailList.size();
						int m = 0;
						int j = 1;
						int k = 2;
						int n = 3;
						int h=4;
						int p = 5;
						int q = 6;
						boolean flag = false;
						boolean sign = false;
						boolean tag = false;
						/*if(detailSize > 0){
							int count = detailSize / 4;
							for(int x=0; x<count;x++){*/
								Purchase purchase = (Purchase)detailList.get(m);
								if(purchase != null){
									Finance finance = purchase.getFinance();
									Decision decision = (Decision)detailList.get(j);
									Order order = (Order)detailList.get(k);
									Contract contract = (Contract)detailList.get(n);
									
									Budget budget=(Budget)detailList.get(h);//happy 2011-4-21 
											
									String checkDate = (String)detailList.get(p);
									String payRate = (String)detailList.get(q);
									long purchaseId = purchase.getId();
									String purchaseCategory = bdm.getWithParentString(purchase.getCategory(), "ZD0003");
									String purchaseUseDept = bdm.getStringValue(purchase.getUseDept(), "ZD0001");
									String purchaseDutyDept = bdm.getStringValue(purchase.getDutyDept(), "ZD0001");
									PurchaseApply purchaseApply = purchase.getCurrentPurchaseApply();
									int dumpFlag = 0;
									if(purchaseApply != null){
										dumpFlag = purchaseApply.getDumpFlag();
									}
									m = m + 4;
									j = j + 4;
									h=h+4;
									k = k + 4;
									n = n + 4;
	  	  %>
      	  <tr <%= ((i++)%2==0)?"class=\"list-back\"":""%>>
		  	<td align="center" height="22">
				<input type="checkbox" name="id" value="<%=purchaseId%>" onClick="buttonDisabled(this.form);">
				<input type="hidden" id="dumpFlag_<%=purchaseId%>" value="<%=dumpFlag%>">
				<input type="hidden" name="hidOrderId" value="<%=((order!=null)?order.getId():0)%>">
				<input type="hidden" name="hidContractId" value="<%=((contract!=null)?contract.getId():0)%>">
				<input type="hidden" name="contractPayRate" value="<%=((contract != null && contract.getRealPayRate() != 0) ? contract.getRealPayRate() + "%" : "")%>">
			</td>
        	<td align="center"><%=((pager.getCurrentPageNo()-1)*pageSize + i)%></td>
			<td class="td-c"><%=purchase.getApplyType()==0?" 基础平台":"目录采购"%></td>
			<td title="<%=purchaseCategory%>"><%=purchaseCategory%></td>
			<%
				if(purchaseApply != null){
					if(purchaseApply.getStatus()==PurchaseConstants.STAT_PUR[5] && 
						purchaseApply.getStatusA()==PurchaseConstants.PURCHASE_SA2 && 
						purchaseApply.getStatusB()==PurchaseConstants.PURCHASE_SB2 && 
						purchaseApply.getStatusC()==1){
						flag = true;
			%>
			<td align="center" style="background-color:#55EE55">
			<%
					}else{
						flag = false;
			%>
			<td align="center" style="background-color:#E56061">
			<%
					}
				}
			%>
			<a href="javascript:openMaxWindow('<%=context%>/purchase/purchase.do?method=showPurchase&id=<%=purchaseId%>')"><%=purchase.getPurchaseNo()%>			</a>			</td>
			<td class="td-date"><%=WebUtil.dateToString(purchase.getCurrentPurchaseApply().getSubmitDate())%></td>
        	<td class="td-text" title="<%=purchase.getName()%>">
				<%=purchase.getName()%>			</td>
			<td class="td-text" title="<%=purchaseUseDept%>"><%=purchaseUseDept%></td>
			<td class="td-text"><%=bdm.getStringValue(purchase.getUseDeptContact(), "ZD0000")%></td>
			<td class="td-text" title="<%=purchaseDutyDept%>"><%=purchaseDutyDept%></td>
			<td class="td-text"><%=bdm.getStringValue(purchase.getCharge(), "ZD0000")%></td>
			<%
				if(finance != null){
					if(finance.getStatus()==FinanceConstants.STAT_FIN[6] && finance.getStatusA()==FinanceConstants.STATA_FIN[3]){
						tag = true;
			%>
        	<td align="center" style="background-color:#55EE55">
			<%
					}else{
						tag = false;
			%>
			<td align="center" style="background-color:#E56061">
			<%
					}
			%>
			<a href="javascript:openWindow('<%=context%>/finance/finance.do?method=view&id=<%=finance.getId()%>', 850, 640,'yes')">
				<%=finance.getFinanceNo()%>			</a>			</td>
			<%
				}else{//无重财则默认为tag = true;
					tag = true;
			%>
			<td align="center">——</td>
			<%
				}
			%>
			<%
				if(decision != null){
					if(decision.getStatus()==PriceConstants.STAT_DEC[3]){//已退回
						sign = false;
			%>
					<td>&nbsp;</td>
			<%
					}else{
						if(decision.getStatus()==PriceConstants.STAT_DEC[2]){
							if(decision.getStatus2()==PriceConstants.STAT_DEC2[0]){
								sign = true;					
			%>
							<td align="center" style="background-color:#55EE55">
			<%
							}else{
								sign = false;
			%>
							<td align="center" style="background-color:#E56061">
			<%
							}		
						}else{
							sign = false;
			%>
						<td align="center" style="background-color:#E56061">
			<%
						}
						if(all != null && !"false".equals(all)){   //采购部查看总进度
			%>
							<a href="javascript:openMaxWindow('<%=context%>/price/price.do?method=showDecision&decisionId=<%=decision.getId()%>')"><%=decision.getDecisionNo()%></a>
			<%
						}else{
			%>
							<%=decision.getDecisionNo()%>
			<%
						}
			%>						</td>
			<%	
					}
				}else{
					if(dumpFlag == 1){//针对 IT导入的数据可能没有决裁书而有订单和合同
						sign = true;
					}
			%>
				<td>&nbsp;</td>
				
			<%
				}
			if(budget!=null){
			%>
				<td align="center"><%=budget.getBudgetNo() %></td>
			<%
			}else{
				%>
				<td align="center">——</td>
			<%
			}
			if(order != null){
					if(flag == true && sign == true && tag == true){//基础平台单、重财（有的话）、决裁审批通过
						int orderStatus = order.getStatus();
						if(orderStatus == OrderConstants.ORDER_S8){//已废止
			%>
						<td align="center" title="该订单已废止">&nbsp;</td>
			<%
						}else{
							if(orderStatus >= OrderConstants.ORDER_S3){//已发出
			%>
						<td align="center" style="background-color:#55EE55">
			<%
							}else if(orderStatus < OrderConstants.ORDER_S3){			
			%>
						<td align="center" style="background-color:#E56061">
			<%
							}
							if(all != null && !"false".equals(all)){   //采购部查看总进度
			%>
								<a href="javascript:openMaxWindow('<%=context%>/order/order.do?method=showOrderView&id=<%=order.getId()%>')"><%=order.getOrderNo()%></a>
			<%
							}else{
			%>
								<%=order.getOrderNo()%>
			<%
							}
			%>			</td>
			<%
						}
					}else{
			%>
					<td>&nbsp;</td>
			<%
					}
				}else{
			%>
				<td>&nbsp;</td>
			<%
				}
				if(contract != null){
					ContractApply contractApply = contract.getCurrentContractApply();
					int status = 0;
					if(contractApply != null){
						status = contractApply.getStatus();
					}
					if(flag == true && sign == true && tag == true){
						if(contract.getStatus()!=ContractConstants.CONTRACT_V1 && 
						contract.getInureDate()!=null && (status == ContractConstants.CONTRACT_S4 || status == ContractConstants.CONTRACT_S11)){
			%>
						<td align="center" style="background-color:#55EE55">
			<%
						}else{
			%>
						<td align="center" style="background-color:#E56061">
			<%
						}
						if(all != null && !"false".equals(all)){   //采购部查看总进度
							if(contract.getInputFlag() == 1){
			%>
								<a href="javascript:openMaxWindow('<%=context%>/contract/contract.do?method=showContractView1&id=<%=contract.getId()%>')"><%=contract.getContractNo()%></a>
			<%
							}else{
			%>
								<a href="javascript:openMaxWindow('<%=context%>/contract/contract.do?method=showContractView&id=<%=contract.getId()%>')"><%=contract.getContractNo()%></a>
			<%
							}
						}else{
			%>
							<%=contract.getContractNo()%>
			<%
						}
			%>			</td>
			<%
					}else{
			%>
					<td>&nbsp;</td>
			<%
					}
				}else{
					if(order != null && order.getStatus() != OrderConstants.ORDER_S8 && 
					order.getNeedContract()==OrderConstants.ORDER_NEED0){
			%>
					<td align="center">——</td>
					
			<%
					}else{
			%>
					<td>&nbsp;</td>
			<%
					}
				}
			%>
			<td class="td-date"><%=checkDate%></td>
			<td class="td-num"><%=payRate%></td>
      	  </tr>
	  	  <%
		  						}
		  					}
		  				}
	  				/*}
	  			}*/
			}
			for(; i<pageSize; i++){
	  	  %>
      	  <tr <%= (i%2==0)?"class=\"list-back\"":"" %>>
        	<td height="20" align="center">&nbsp;</td>
			<td height="20" align="center">&nbsp;</td>
        	<td height="22" align="center">&nbsp;</td>
        	<td height="22" class="td-text">&nbsp;</td>
        	<td height="22" align="center">&nbsp;</td>
			<td height="22" align="center">&nbsp;</td>
        	<td height="22" class="td-text">&nbsp;</td>
			<td height="22" align="center">&nbsp;</td>
			<td height="22" align="center">&nbsp;</td>
        	<td height="22" align="center">&nbsp;</td>
        	<td height="22" class="td-text">&nbsp;</td>
        	<td height="22" align="center">&nbsp;</td>
        	<td height="22" class="td-text">&nbsp;</td>
        	<td height="22" class="td-text">&nbsp;</td>
        	<td height="22" align="center">&nbsp;</td>
			<td height="22" class="td-text">&nbsp;</td>
        	<td height="22" align="center">&nbsp;</td>
        	<td height="22" align="center">&nbsp;</td>
      	  </tr>
      	  <%
	  		}
			String params = WebUtil.getQueryString(request);
	 	  %>
    	</table>
		</div>
		</td>
	  </tr>
	</table></td>
  </tr>
  <tr>
    <td align="right" height="24" class="box-mm" valign="middle">
	    <table width="100%" border="0" cellspacing="0" cellpadding="4">
          <tr>
		  	<td>
				<input type="button" name="btnSee" class="button" value="查看进展" onClick="showStatusLook()" disabled>
				<!--input type="button" name="btnExp" class="button" value="导出清单" onClick="exportPurchase(this.form);" disabled-->
			</td>
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
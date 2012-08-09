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
<%@ page import="com.glaf.base.modules.others.model.StatusView"%>
<%
String context = request.getContextPath();
int pageSize=Constants.PAGE_SIZE;
BaseDataManager bdm = BaseDataManager.getInstance();

Purchase purchase = (Purchase)request.getAttribute("purchase");
List list = (List)request.getAttribute("list");
String all = String.valueOf(request.getAttribute("all"));

if(purchase == null){
	out.println("对不起，您要查看的基础平台单不存在！！！");
}else{
	PurchaseApply purchaseApply = purchase.getCurrentPurchaseApply();
	Finance finance = purchase.getFinance();
	String fullName = purchase.getSupplier();
	if(fullName == null){
		fullName = "";
	}
	int financeStatus = 0;
	if(finance != null){
		financeStatus = 1;
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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>XXXX基础平台系统</title>
<link href="../../css/site.css" type="text/css" rel="stylesheet">
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<style>
.tooltip{
	visibility:hidden;
	background-color:#e6ecff;
	border:1px solid #003399;
	padding:5px;
	font-size:12px;
	font-family:arial,helvetica,sans-serif;
	color:#000066;
	position:absolute;
	left: 121px;
	top: 9px;
}
</style>
<script language="javascript">
	function showStatusPoint(){
	<%
		if(list != null && list.size()>0){
			int length = list.size();
			for(int i=0; i<length; i++){
				StatusView sv = (StatusView)list.get(i);
				String tooltip = sv.getTooltip();
				tooltip = tooltip.replace(";","\\n\\n");
	%>
				var obj = document.getElementsByName("picSta").item(<%=i%>);
				obj.src = "<%=context%><%=sv.getSrc()%>";
				obj.title = "<%=tooltip%>";
	<%
			}
		}
	%>
	}
	
	function linkPage(index){
	<%
		if(list != null && list.size() > 0){
			int length = list.size();
			for(int i=0; i<length; i++){
	%>
				if(index==<%=i%>){
					<%
						StatusView sv = (StatusView)list.get(i);
						if(sv.getStatus()==1){
							if(!"#".equals(sv.getPath().trim())){
					%>
								if(index == 24 || index == 25){
									openWindow('<%=context%><%=sv.getPath()%>', 750, 390,'yes')
								}else{
									openMaxWindow('<%=context%><%=sv.getPath()%>');
								}
					<%
							}else{
								if(i >= 10 && "false".equals(all)){
					%>
									alert('对不起，您无权查看该项！');
					<%
								}
							}
						}
					%>
				}
	<%
			}
		}
	%>
	}
</script>
</head>

<body onLoad="showStatusPoint();">
<form action="#" method="post">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="m-box">
  <tr>
    <td width="100%" class="nav-title">进度管理看板&gt;&gt;项目进展查询</td>
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
    <td class="box-mm" align="center"><div id="listDiv" style="width:980px; height:70px;overflow-x:auto; overflow-y:auto;"><table border="0" cellspacing="1" cellpadding="0" class="list-box">
      <tr class="list-title">
        <td width="90" height="22" align="center" nowrap class="title">采购方式</td>
        <td width="60" align="center" nowrap class="title">申请性质</td>
        <td width="100" align="center" nowrap class="title">采购申请单No</td>
        <td width="255" align="center" nowrap class="title">项目名称</td>
        <td width="120" align="center" nowrap class="title">采购类别</td>
        <td width="60" align="center" nowrap class="title">采购性质</td>
        <td width="100" align="center" nowrap class="title">使用部门</td>
        <td width="100" align="center" nowrap class="title">使用部门经办人</td>
        <td width="100" align="center" nowrap class="title">归口部门</td>
        <td width="100" align="center" nowrap class="title">归口部门经办人</td>
        <td width="100" align="center" nowrap class="title">重财No</td>
        <td width="100" align="center" nowrap class="title">采购担当</td>
        <td width="200" align="center" nowrap class="title">供应商</td>
      </tr>
      <tr>
        <td height="27" align="center"><%=PurchaseConstants.getApplyType(purchase.getApplyType())%></td>
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
		%>        </td>
        <td><%=bdm.getStringValue(purchase.getCharge(), "ZD0000")%></td>
        <td title="<%=fullName%>"><%=fullName%></td>
      </tr>
    </table></div>
	<br>
    <table width="99%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><hr></td>
          </tr>
     </table>
     <br>
     <table width="100%" border="0" cellspacing="7" cellpadding="0">
       <tr>
         <td><table width="989" height="1561" border="0" align="center" cellpadding="0" cellspacing="0" background="<%=context%>/module/others/status_see/images/process.jpg" id="Table_01" style="background-repeat:no-repeat;">
             <tr>
               <td height="57" colspan="41">&nbsp;</td>
               <td width="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="3" rowspan="38">&nbsp;</td>
               <td width="25" height="25"><img name="picSta" width="25" height="25" style="cursor:pointer" border="0" onMouseOver="" onClick="linkPage(0);" tooltip=""></td>
               <td colspan="34" rowspan="2">&nbsp;</td>
               <td colspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(1);"/></td>
               <td width="61" rowspan="52">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td rowspan="2">&nbsp;</td>
               <td height="71" colspan="2">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="33" rowspan="6">&nbsp;</td>
               <td colspan="2" rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(2);"/></td>
			   <td width="1" rowspan="46"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td height="1"><img src="status_see/mages/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(3);"/></td>
               <td height="24"><img src="images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="2" rowspan="44">&nbsp;</td>
               <td height="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="21">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="25"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(4);"/></td>
               <td><img src="status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td rowspan="2">&nbsp;</td>
               <td height="49"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="4" rowspan="5">&nbsp;</td>
               <td colspan="2" rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(5);"/></td>
			   <td colspan="27" rowspan="8">&nbsp;</td>
               <td height="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(6);"/></td>
               <td height="24"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="2" rowspan="3">&nbsp;</td>
               <td height="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="49">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(7);"/></td>
               <td height="2"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="3" rowspan="25">&nbsp;</td>
               <td colspan="2" rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(8);"/></td>
			   <td width="4" rowspan="39"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td height="23"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td rowspan="38">&nbsp;</td>
               <td height="2"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="2" rowspan="37">&nbsp;</td>
               <td height="37"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td width="144" rowspan="36">&nbsp;</td>
               <td height="25" colspan="5"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(9);"/></td>
			   <td colspan="21" rowspan="2">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="5" rowspan="2">&nbsp;</td>
               <td height="49"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="13" rowspan="4">&nbsp;</td>
               <td colspan="3" rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(10);"/></td>
	           <td colspan="5" rowspan="10">&nbsp;</td>
               <td height="2"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="5" rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(11);"/></td>
			   <td height="23"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="3" rowspan="8">&nbsp;</td>
               <td height="2"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="47" colspan="5">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="25" colspan="5"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(12);"/></td>
               <td colspan="6" rowspan="2">&nbsp;</td>
               <td colspan="4"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(13);"/></td>
               <td colspan="3" rowspan="10">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="142" colspan="5">&nbsp;</td>
               <td colspan="4">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="4" rowspan="2"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td height="25" colspan="5"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(14);"/></td>
               <td width="67" rowspan="28">&nbsp;</td>
               <td colspan="4"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(15);"/></td>
               <td width="3" rowspan="8"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="52" colspan="5">&nbsp;</td>
               <td colspan="4" rowspan="7">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="3" rowspan="3"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td height="25" colspan="5"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(16);"/></td>
               <td width="3" rowspan="26"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="5" rowspan="2">&nbsp;</td>
               <td height="43"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td width="4" rowspan="24"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td colspan="3" rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(17);"/></td>
			   <td colspan="4" rowspan="5">&nbsp;</td>
			   <td height="2"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td width="2" rowspan="23"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td colspan="5" rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(18);"/></td>
			   <td colspan="2" rowspan="7"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td height="23"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="3" rowspan="13">&nbsp;</td>
               <td height="2"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="49" colspan="5">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="5" rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(19);"/></td>
			   <td colspan="3" rowspan="4"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td colspan="4" rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(20);"/></td>
               <td width="50" rowspan="20">&nbsp;</td>
               <td height="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="2" rowspan="10">&nbsp;</td>
               <td width="25" rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(21);"/></td>
               <td width="103" rowspan="19">&nbsp;</td>
               <td height="24"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="5" rowspan="2">&nbsp;</td>
               <td colspan="4" rowspan="2">&nbsp;</td>
               <td height="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td rowspan="17">&nbsp;</td>
               <td height="43"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="2" rowspan="12"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td height="25" colspan="5"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(22);"/></td>
               <td colspan="2" rowspan="16"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td colspan="4"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(23);"/></td>
               <td width="1" rowspan="16"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="5" rowspan="3">&nbsp;</td>
               <td colspan="4" rowspan="15">&nbsp;</td>
               <td height="43"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td width="135" rowspan="14">&nbsp;</td>
               <td width="25" height="25"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(24);"/></td>
               <td width="6" rowspan="14"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="25" rowspan="14">&nbsp;</td>
               <td width="25"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(25);"/></td>
               <td width="95" rowspan="14">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td rowspan="13">&nbsp;</td>
               <td rowspan="13">&nbsp;</td>
               <td height="38"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="25" colspan="5"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(26);"/></td>
			   <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="39" colspan="5">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="5" rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(27);"/></td>
               <td height="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td width="4" rowspan="9"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td colspan="3" rowspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(28);"/></td>
               <td width="52" rowspan="9">&nbsp;</td>
               <td height="24"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="5" rowspan="2">&nbsp;</td>
               <td height="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="3" rowspan="7">&nbsp;</td>
               <td height="64"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="25" colspan="5"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(29);"/></td>
			   <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="42" colspan="5">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td width="1" rowspan="4"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td height="25" colspan="5"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(30);"/></td>
               <td width="1" rowspan="4"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="1" rowspan="4"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td colspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(31);"/></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td colspan="5" rowspan="3">&nbsp;</td>
               <td height="39" colspan="2">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="25" colspan="2"><img name="picSta" width="25" height="25" style="cursor:pointer" onClick="linkPage(32);"/></td>
			   <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="101" colspan="2">&nbsp;</td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
             </tr>
             <tr>
               <td height="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="4"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="21"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="3"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="18"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="2"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="3"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="20"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="1"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="17"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="4"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="4"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td width="24"><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td><img src="<%=context%>/module/others/status_see/images/spacer.gif" width="1" height="1" alt="" /></td>
               <td></td>
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
<!--script language="javascript">
attachFrame();
</script-->

<div id="list" class="tooltip" style="visibility:hidden">
  <table height="40" border="0">
  	<tr>
		<td>张三</td>
		<td>任务开始时间:</td>
		<td>超期天数:</td>
	</tr>
	<tr>
		<td>李四</td>
		<td>任务开始时间:</td>
		<td>超期天数:</td>
	</tr>
  </table>
</div>
<div id="altlayer" class="tooltip"></div>
<script type="text/javascript" src='<%=context%>/js/tooltip.js'></script>
<script type="text/javascript">
	addTipSupport(document.body);  //此处的参数可换成其它，以局限在某容器内支持TOOLTIP风格。
</script>

<script language="javascript">
	function showDetail(obj){
	  obj.tooltip=document.all.list.innerHTML ;
	}
</script>
</body>
</html>
<%
	}
%>

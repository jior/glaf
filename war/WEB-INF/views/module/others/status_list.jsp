<%@ page contentType="text/html;charset=gbk" language="java"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>XXXX����ƽ̨ϵͳ</title>
<link href="../../css/site.css" type="text/css" rel="stylesheet">
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="javascript">
	//���ݲ���ѡ����Ӧ�����µ�Ա��
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

	//��ʾ���Ȳ�ѯҳ��
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
    <td width="49%" class="nav-title">��Ŀ���ȹ���&gt;&gt;��Ŀ��չ��ѯ�б�</td>
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
        <td width="76">�ɹ����뵥No</td>
        <td width="132"><input name="purchaseNo" type="text" class="input" size="15" searchflag="1"></td>
        <td width="57">��Ŀ����</td>
        <td width="167"><input name="name" type="text" class="input" size="20" searchflag="1"></td>
        <td width="55">ʹ�ò���</td>
        <td width="138">
			<input name="useDeptTitle" type="text" class="input" size="15" onClick=" selectDept('5', $(useDept), this)" readonly>
			<input type="hidden" id="useDept" name="useDept" value="" searchflag="1">
		</td>
        <td width="89">ʹ�ò��ž�����</td>
        <td width="119">
			<input name="useDeptContactTitle" type="text" class="input" size="15" onClick="selectContact($(useDept), 'use', this)" readonly>
			<input type="hidden" name="useDeptContact" id="useDeptContact" value="" searchflag="1">
		</td>
        <td width="57">&nbsp;</td>
      </tr>
      <tr>
        <td>�ز�No</td>
        <td><input name="financeNo" type="text" class="input" size="15" searchflag="1"></td>
        <td>�ɹ�����</td>
        <td>
			<input name="chargeTitle" type="text" class="input" size="15" onClick="selectUserDuty('310','R010',$('charge'),this)" readonly>
			<input type="hidden" id="charge" name="charge" value="" searchflag="1">
		</td>
        <td>��ڲ���</td>
        <td>
			<input name="dutyDeptTitle" type="text" class="input" size="15" onClick="selectDept('5', $(dutyDept), this)" readonly>
			<input type="hidden" name="dutyDept" id="dutyDept" searchflag="1">
		</td>
        <td>��ڲ��ž�����</td>
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
              <td width="40" align="center" nowrap class="title">ѡ��</td>
              <td width="40" align="center" nowrap class="title">���</td>
              <td width="90" align="center" nowrap class="title">�ɹ���ʽ</td>
              <td width="60" align="center" nowrap class="title">��������</td>
              <td width="100" align="center" nowrap class="title">�ɹ����뵥No</td>
              <td width="255" align="center" nowrap class="title">��Ŀ����</td>
              <td width="100" align="center" nowrap class="title">�ɹ����</td>
              <td width="60" align="center" nowrap class="title">�ɹ�����</td>
              <td width="100" align="center" nowrap class="title">ʹ�ò���</td>
              <td width="100" align="center" nowrap class="title">ʹ�ò��ž�����</td>
              <td width="100" align="center" nowrap class="title">��ڲ���</td>
              <td width="100" align="center" nowrap class="title">��ڲ��ž�����</td>
              <td width="100" align="center" nowrap class="title">�ز�No</td>
              <td width="80" align="center" nowrap class="title">�ɹ�����</td>
              <td width="200" align="center" nowrap class="title">��Ӧ��</td>
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
						String name1 = purchase.getName();  //������Ŀ����
						String name2 = purchase.getName2(); //������Ŀ����
						String name = "������Ŀ����" + name1;
						if(!"".equals(name2)){
							 name += "\n" + "������Ŀ����" + name2;  //"\n"__����
						}
						String useDept = bdm.getStringValue(purchase.getUseDept(), "ZD0001"); //ʹ�ò���
						String dutyDept = bdm.getStringValue(purchase.getDutyDept(), "ZD0001"); //��ڲ���
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
              <td width="44%" height="25" valign="bottom"><!--input name="btn_release" type="button" class="button" onClick="release(this.form)" value="����" disabled-->
                <input id="btn_look" name="btn_status" type="button" class="button" value="�鿴״̬" onClick="showStatusLook();" disabled></td>
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

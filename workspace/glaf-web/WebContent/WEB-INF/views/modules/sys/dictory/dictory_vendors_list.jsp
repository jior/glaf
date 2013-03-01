<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.spadmin.model.*"%>
<%@ page import="com.glaf.base.modules.spadmin.SupplierConstants"%>
<%@ page import="com.glaf.base.modules.Constants"%>
<%
int pageSize = Constants.PAGE_SIZE;
String type = ParamUtil.getParameter(request, "query_type_ex");
boolean isMult = ParamUtil.getBooleanParameter(request, "mult", "true");
String context = request.getContextPath();
com.glaf.core.util.PageResult pager = (com.glaf.core.util.PageResult) request.getAttribute("pager");
List list = pager.getResults();
Iterator iter = list.iterator();


String fullName = ParamUtil.getParameter(request, "query_fullName_like");
String supplierNo = ParamUtil.getParameter(request, "query_supplierNo_like");
String code = ParamUtil.getParameter(request, "query_code_ex");
%>
<html>
<head>
<base target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>供应商Vendor</title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript">
function selData(){	
  if(getRadioValue('id')==''){
  	alert('请选择供应商');
  }else{
	  var val = getRadioValue('id', 'value', 'title', 'alt');
	 
  	window.returnValue = val;
  	window.close();
  }
}
function selMultData(){	
  window.returnValue = getCheckboxValue('id', 'value', 'title', 'alt');
  window.close();
}
function noSelect() {
  window.returnValue = null;
  window.close();
}
//清空
function cleanValue(){
	document.all.categoryTitle.value="";
	document.all.query_category_el.value="";
}
//选择采购类别
function selCategory(title){
	selectSupplierGoodsCategory($('query_goodsCategorys.id_xel'),title,-1);
	/*var rst = selectGoodsCategoryList(0);
	if(rst){
		var goodsCat;//id,name,applyDeptId,applyDeptName,dutyDeptId,dutyDeptName,chargeId,chargeName,code
		eval("goodsCat=" + rst[1]);
		$('GoodsCategorysTitle').value = goodsCat.name;
		$('query_goodsCategorys.id_xel').value = goodsCat.id;
	}*/
}
</script>
</head>
<body>
<form action="?method=showSelectList2&mult=<%= ParamUtil.getParameter(request, "mult") %>" method="post" name="supplierSearch">
<input name="query_type_ex" type="hidden" value="<%= type %>" />
<input type="hidden" name="goodsCategoryFlag" value="-1">
<table width="480" border="0" align="center" cellspacing="0" cellpadding="0">      
      <tr>
        <td class="nav-title">请选择供应商：&nbsp;</td>
      </tr>
</table>
<table width="480" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
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
    <td class="box-mm"><table width="100%" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td align="right">名&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
        <td width="145"><input name="query_vendor_name_like" type="text" class="input" size="15" value="" datatype="String" nullable="yes"></td>
        <td align="right">编&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
        <td width="165"><input name="query_segment1_like" type="text" class="input" size="12" value="" datatype="String" nullable="yes"></td>
        </tr>
      <tr>
        <td align="right" colspan="4"><input name="Submit" type="submit" value=" " class="submit-search"></td>
        </tr>
    </table>
      <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="25"><a href="javascript:noSelect()">不选择</a></td>
        </tr>
      </table>
      <table width="95%" align="center" border="0" cellspacing="1" cellpadding="3" class="list-box">
      <tr class="list-title" >
          <td width="15%" align="center" class="title"><input type="checkbox" name="all" onClick="javascript:checkAll(this.form,this);">全选</td>
           <td width="65%" align="center" class="title">供应商名称</td>
           <td width="20%" align="center" class="title">供应商编号</td>
        </tr>
		  <%
				int i = 0;
while(iter.hasNext()){
  i++;
  Vendors bean = (Vendors) iter.next();
%>
        <tr class="<%= i % 2 == 0 ? "" : "list-back" %>">
          <td class="td-cb"><input type="<%= isMult ? "checkbox" : "radio" %>" name="id" value="<%=bean.getVendor_id()%>" title="<%=bean.getVendor_name()%>" alt="<%= bean.getVendor_type() %>" onDblClick="<%= isMult ? "selMultData()" : "selData()" %>"></td>
          <td class="td-text"><%= bean.getVendor_name() %></td>
          <td class="td-no"><%= bean.getSegment1() %></td>
        </tr>
        <%
}
%>
      </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="30" align="center"><%
String params = WebUtil.getQueryString(request) ;
%>
                <jsp:include page="/WEB-INF/views/inc/show_page.jsp" flush="true">
                <jsp:param name="total" value="<%=pager.getTotalRecordCount()%>"/>    
                <jsp:param name="page_count" value="<%=pager.getTotalPageCount()%>"/>    
                <jsp:param name="page_size" value="<%=pageSize%>"/>    
                <jsp:param name="page_no" value="<%=pager.getCurrentPageNo()%>"/>    
                <jsp:param name="url" value=""/>    
                <jsp:param name="params" value="<%=java.net.URLEncoder.encode(params)%>"/>    
              </jsp:include></tr>
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
<table width="300" border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" align="center"><input type="button" name="btn_save" value="确定" class="button" onClick="<%= isMult ? "selMultData()" : "selData()" %>" /></td>
  </tr>
</table>
</form>
</body>
</html>

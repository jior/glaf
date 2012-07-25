<%@ page contentType="text/html;charset=gbk" language="java"%>

<%
String context = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/js/verify.js'></script>
<script language="javascript" src='<%=context%>/js/main.js'></script>
<script language="javascript">
function selSupplier(selectType, supplierType, refer){
  var ret = selectSupplierList(selectType, supplierType);
  if(ret==null) return;
  if(refer)refer.value=ret[1];
}
function selGoods(refer){
  var ret = selectGoodsList();
  if(ret==null) return;
  if(refer)refer.value=ret[1];
}
function selBudget(refer){
  var ret = selectBudgetsList();
  if(ret==null) return;
  if(refer)refer.value=ret[1];
}
function selBudget2(refer){
  var ret = selectFeeBudgetsList();
  if(ret==null) return;
  if(refer)refer.value=ret[1];
}
function selFinance(refer){
  var ret = selectFinanceList();
  if(ret==null) return;
  if(refer)refer.value=ret[1];
}
function selDept(parent, refer){
  var ret = selectDeptList(parent);
  if(ret==null) return;
  if(refer)refer.value=ret[1];
  $("deptId").value=ret[0];
}
function selDuty(refer){
  var ret = selectDuty($("deptId").value, "R001");
  if(ret==null) return;
  if(refer)refer.value=ret[1]+","+ret[3];
}

function selGoodCategory(applyDeptId, refer){
  var ret = selectGoodsCategoryList(applyDeptId);
  if(ret==null) return;  
  var goodsCat;//id,name,applyDeptId,applyDeptName,dutyDeptId,dutyDeptName,chargeId,chargeName
  eval("goodsCat=" + ret[1]);
  if(refer)refer.value=goodsCat.chargeName;
}
function selSubjectCode(parent,referCode){
	selectSubjectList(parent,$("subjectId"),$("subjectName"),referCode);	
}
function selectCode(){
	alert("id = "+ $("subjectId").value +"  name = "+ $("subjectName").value +"   code = "+ $("subjectCode").value)
}
function doComment(referType, referId){
  var url = context + "/others/audit.do?method=prepareComment&referType="+referType+"&referId="+referId;
  openWindow(url, 350, 350);
}

function send(){
  $("money").value=$("money").currency;
}
function showUser(){
  var userId = $("userId").value;
  var url = context + "/sys/user.do?method=showUser&userId="+userId;
  openWindow(url, 350, 250);
}
</script>
<title>�ޱ����ĵ�</title>
</head>
<body>
<form name="main" method="post">
<table width="637" border="0">
  <tr>
    <td width="218">��Ӧ��ѡ��</td>
    <td width="409"><input type="text" name="supplier" value="" class="input" onClick="selSupplier(2, 0, this)" readonly/></td>
  </tr>
  <tr>
    <td>��Ʒѡ��</td>
    <td><input type="text" name="goods" value="" class="input" onClick="selGoods(this)" readonly/></td>
  </tr>
  <tr>
    <td>Ͷ��Ԥ��ѡ��</td>
    <td><input type="text" name="budget" value="" class="input" onClick="selBudget(this)" readonly/></td>
  </tr>
  <tr>
    <td>����Ԥ��ѡ��</td>
    <td><input type="text" name="budget2" value="" class="input" onClick="selBudget2(this)" readonly/></td>
  </tr>
  <tr>
    <td>�ز���Ϣѡ��</td>
    <td><input type="text" name="finance" value="" class="input" onClick="selFinance(this)" readonly/></td>
  </tr>
  <tr>
    <td>������Ϣѡ��</td>
    <td><input type="text" name="dept" value="" class="input" onClick="selDept(5, this)" readonly/>
	<input type="hidden" name="deptId" value="">	</td>
  </tr>
  <tr>
    <td>��ɫ�û���Ϣѡ��</td>
    <td><input type="text" name="roleUser" value="" class="input" onClick="selDuty(this)" readonly/></td>
  </tr>
  <tr>
    <td>�û���Ϣѡ��</td>
    <td><input type="text" name="user" value="" class="input" readonly/></td>
  </tr>  
  <tr>
    <td>�ɹ�����ѡ��</td>
    <td><input type="text" name="category" value="" class="input" onClick="selGoodCategory(303, this)" readonly/></td>
  </tr>
      <tr>
    <td>��Ŀ����ѡ��</td>
    <td><input type="text" name="subjectCode" value="" class="input" onClick="selSubjectCode(0,this)" readonly/>
		<input type="hidden" name="subjectId" vlaue="" >
		<input type="hidden" name="subjectName" value=""><input type="button" onClick="selectCode();" value="�鿴">	</td>
  </tr>
  <tr>
    <td>�������������</td>
    <td><input type="text" name="money" value="" datatype="float" component="currency" class="input" currency="" style="text-align:right"/></td>
  </tr>
  <tr>
    <td>��ʾ�û���Ϣ��</td>
    <td><input type="text" name="userId" value="" class="input"/>
      <input name="button" type="button" onClick="showUser();" value="�鿴"></td>
  </tr>
  <tr>
    <td>���������</td>
    <td><input type="button" name="yes" value="ͬ��" onClick="doComment(0, 1)">&nbsp;<input type="button" name="no" value="��ͬ��" onClick="doComment(0, 1)"></td>
  </tr>
  <tr>
    <td colspan="2" align="center"><input type="submit" name="Submit" value="�ύ" onClick="send();" /></td>
  </tr>
</table>
</form>
���룺<input type="text" name="urlcode" value=""><input type="button" name="btnDecode" value="decoder" onClick="alert(URLDecode($('urlcode').value))">
</body>
</html>

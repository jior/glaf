<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/pages/common/style.jsp"%>
<%
	 String optionScript = (String)request.getAttribute("optionScript");
     String hiddenScript = (String)request.getAttribute("hiddenScript");

     if(optionScript == null)
	 {
		 optionScript = "";
	 }

     if(hiddenScript == null)
	 {
		 hiddenScript = "";
	 }
%>
<SCRIPT	language="javascript">

function up(){
	var i;
	var temp;
	var temp2;
	i=document.iForm.iChoose.selectedIndex;
	if(i>0){
		temp=document.iForm.iChoose[i].text;
		document.iForm.iChoose[i].text=document.iForm.iChoose[i-1].text;
		document.iForm.iChoose[i-1].text=temp;
		temp2=document.iForm.iChoose[i].value;
		document.iForm.iChoose[i].value=document.iForm.iChoose[i-1].value;
		document.iForm.iChoose[i-1].value=temp2;
		document.iForm.iChoose.selectedIndex=i-1;
	}
}

function down(){
	var len;
	var i;
	var temp;
	len=document.iForm.iChoose.length;
	i=document.iForm.iChoose.selectedIndex;
	if(i<len-1){
		temp=document.iForm.iChoose[i].text;
		document.iForm.iChoose[i].text=document.iForm.iChoose[i+1].text;
		document.iForm.iChoose[i+1].text=temp;
		temp2=document.iForm.iChoose[i].value;
		document.iForm.iChoose[i].value=document.iForm.iChoose[i+1].value;
		document.iForm.iChoose[i+1].value=temp2;
		document.iForm.iChoose.selectedIndex=i+1;
	}
}

function list(){
    var len=document.iForm.iChoose.length;
    var value="";
    for (var i=0;i<len;i++) {
      value=value+";"+document.iForm.iChoose(i).value;
    }
    document.iForm.objectIds.value=value;
    document.iForm.submit();
}
</SCRIPT>
<br>
<table border=0 cellpadding=0 cellspacing=0 width=100% height=100% valign=middle>
<tr>
<td align=center height=200>
<form name="iForm" method=post action="<c:out value="${action}"/>">
<input type="hidden" name="method" value="<c:out value="${method}"/>" >
<input type="hidden" name="actionType" value="<c:out value="${method}"/>" >
<input type="hidden" name="objectIds" >
<%=hiddenScript%>
<select name=iChoose size=10 style="width:200px;height=300px">
 <%=optionScript%>
</select>
</td></tr>
<tr>
<td align=center valign=top>
<br>
<input class="button" type="button" value="向上" onclick="javascript:up();">
<input class="button" type="button" value="向下" onclick="javascript:down();">
<input class="button" type="button" value="确定" onclick="javascript:list()">
<input class="button" type="button" value="返回" onclick="javascript:history.back(0);">
</form>
</td></tr>
</table>
</td>
  <td width="0%" valign="top"></td>
</tr>
<tr>
  <td colspan="3"></td>
</tr>
<tr>
  <td colspan="3"></td>
</tr>
</table>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jpage.core.type.*" %>
<%@ page import="org.jpage.base.om.service.*" %>
<%@ page import="org.jpage.base.om.beans.*" %>
<%@ page import="org.jpage.util.*" %>
<%
   	 String typeScript = (String)request.getAttribute("typeScript");
	 String selectedScript = (String)request.getAttribute("selectedScript");
	 String noselectedScript = (String)request.getAttribute("noselectedScript");
	 String hiddenScript = (String)request.getAttribute("hiddenScript");

	 if(typeScript == null){
		 typeScript = "";
	 }
	 if(selectedScript == null)
	 {
		 selectedScript = "";
	 }
	 if(noselectedScript == null)
	 {
		 noselectedScript = "";
	 }
	 if(hiddenScript == null)
	 {
		 hiddenScript = "";
	 }
%>
<script language="JavaScript">

 function addElement() {

        var list = document.typeForm.noselected;
        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
                var value = list.options[i].value;
                var text = list.options[i].text;
                addToList(value, text);
				list.remove(i);
				i=i-1;
            }
        }

    }

 function addToList(value, text) {
        var list = document.typeForm.selected;
        if (list.length > 0) {
            for (k = 0; k < list.length; k++) {
                if (list.options[k].value == value) {
                    return;
                }
            }
        }

        var len = list.options.length;
        list.length = len + 1;
        list.options[len].value = value;
        list.options[len].text = text;
    }

 function removeElement() {
        var list = document.typeForm.selected;
		var slist = document.typeForm.noselected;
        if (list.length == 0 || list.selectedIndex < 0 || list.selectedIndex >= list.options.length)
            return;

        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
			    var value = list.options[i].value;
                var text = list.options[i].text;
                list.options[i] = null;
                i--;
				var len = slist.options.length;
				slist.length = len+1;
                slist.options[len].value = value;
                slist.options[len].text = text;				
            }
        }
    }

</script>

<script language="JavaScript">
  function jump() {
    var objectId=document.typeForm.typeList.value;
    location.href="<c:out value="${jumpLink}"/>?method=<c:out value="${method}"/>&objectId="+objectId;
  }
</script>

<script language="JavaScript">
  function formSubmit() {
    var len=document.typeForm.selected.length;
	var result="";
	for (var i=0;i<len;i++) {
      result=result+"<c:out value="${setting.split}"/>"+document.typeForm.selected.options[i].value;
    }
    document.typeForm.<c:out value="${setting.paramName}"/>.value=result;
	return true;
  }
</script>


<body leftmargin="0" topmargin="0"  marginheight="0" marginwidth="0">

<center>
  <br>
  <center>
    <span class="subject">
	<img src="images/title.gif" alt="<c:out value="${setting.subject}"/>"> 
	<c:out value="${setting.subject}"/>
	</span>
</center>

<form name="typeForm" method="post" 
      action="<c:out value="${setting.action}"/>" 
      onsubmit="return formSubmit();">
<%=hiddenScript%>
<input type="hidden" name="<c:out value="${setting.paramName}"/>" >
<input type="hidden" name="actionType" value="<c:out value="${method}"/>" >
    <table class="table-border" align="center" cellpadding="4" cellspacing="1" width="90%">
      <tbody><tr>
        <td class="beta" align="center" colspan="5" valign="top">
		 <select size="1" name="typeList" onChange="javascript:jump();">
		 <%=typeScript%>
		 </select>
         </td>
      </tr>
      <tr>
        <td class="beta" colspan="2">
          <div align="center">可选<c:out value="${setting.selectName}"/></div>
        </td>
         <td class="beta"></td>
		<td class="beta" colspan="3">
          <div align="center">已选<c:out value="${setting.selectName}"/></div>
        </td>
	  </tr>
      <tr>
        <td class="beta" width="10">&nbsp;</td>
        <td class="table-content" height="26" valign="top" width="236">
          <div align="center">
            <select class="list" style="width: 200px; height: 220px;" 
			multiple="multiple" size="12" name="noselected"
			ondblclick="addElement()"><%=noselectedScript%>
			</select>
          </div>
        </td>
        <td class="beta" width="121">
		<div align="center">
			<input name="add" value="添加->" onclick="addElement()" class="button" type="button">
			<br>
			<br>
			<input name="remove" value="<-删除" onclick="removeElement()" class="button" type="button">
        </div>
		</td>
        <td class="table-content" height="26" valign="top" width="239">
          <div align="center">
            <select class="list" style="width: 200px; height: 220px;" 
			multiple="multiple" size="12" name="selected"
			ondblclick="removeElement()"><%=selectedScript%>
			</select>
          </div>
        </td>
        <td class="beta" width="12">&nbsp;</td>
      </tr>
      <tr>
        <td class="beta" colspan="5" valign="top">
          <div align="center">
            <input value="确 定" class="button" name="submit252" type="submit">          　
            <input value="返 回" class="button" name="submit253" onclick="javacsript:history.back();" type="button">
          </div>
        </td>
      </tr>
    </tbody></table>
    <br>
    <p align="center">&nbsp;</p>
    <p align="center">&nbsp;</p>
  </form>
  <br>
</center>


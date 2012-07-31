<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ page import="org.jpage.util.*" %>
<%
	 String selectedScript = (String)request.getAttribute("selectedScript");
	 String noselectedScript = (String)request.getAttribute("noselectedScript");

	 if(selectedScript == null)
	 {
		 selectedScript = "";
	 }
	 if(noselectedScript == null)
	 {
		 noselectedScript = "";
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
function doSelectedUser()
 {
    var username = "";
	var userId = "";
	var list = document.typeForm.selected;
    var len = document.typeForm.selected.length;

	for (var i=0;i<len;i++) {
      userId = userId+list.options[i].value+"<c:out value="${setting.split}"/>";
	}
	for (var i=0;i<len;i++) {
      username = username+list.options[i].innerText+"<c:out value="${setting.split}"/>";
	}
  
	window.opener.document.getElementById("<c:out value="${elementId}"/>").value = username;
	window.opener.document.getElementById("<c:out value="${performerId}"/>").value = userId;
	window.close();
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
<form name="typeForm" method="post" >
    <table class="table-border" align="center" cellpadding="4" cellspacing="1" width="90%">
      <tbody><tr>
        <td class="beta" align="center" colspan="5" valign="top">&nbsp;</td>
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
            <select class="list" style="width: 150px; height: 180px;" 
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
            <select class="list" style="width: 150px; height: 180px;" 
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
            <input value="确 定" class="button" name="submit252" type="button" onclick="javascript:doSelectedUser()">          　
            <input value="关 闭" class="button" name="submit253" onclick="javacsript:window.close();" type="button">
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


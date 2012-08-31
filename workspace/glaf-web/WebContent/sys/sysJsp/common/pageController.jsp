<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld" %>

	<table>
	<tr>
		<td>
		<bean:message key='pageCtrl.recordCount'/><html:text readonly="true" style="border-width:0px;width:30px" property="recordCount"/>
		</td>
		<td>	
		<bean:message key='pageCtrl.nowPage'/>
		<html:text readonly="true" style="border-width:0px;width:30px;text-align:right" property="pageNo"/>
		/<html:text readonly="true" style="border-width:0px;width:30px" property="pageNoMax"/>
		</td>
		<td>	
			<a href="#" name="pageStart" onclick="doPageStart()"><bean:message key='pageCtrl.start'/></a>
		</td>
		<td>
			<a href="#" name="pageBefore" onclick="doPageBefore()"><bean:message key='pageCtrl.before'/></a>
		</td>
		<td>
			<a href="#" name="pageNext" onclick="doPageNext()"><bean:message key='pageCtrl.next'/></a>
		</td>
		<td>
			<a href="#" name="pageEnd" onclick="doPageEnd()"><bean:message key='pageCtrl.end'/></a>
		</td>
		<td>	<bean:message key='pageCtrl.jump'/><input style="width:20px " name="textfield2"  type="text" id="goPageID"><bean:message key='pageCtrl.page'/><input type="button" name="searchPageGo"  id="searchPageGo" value="GO" onclick="doPageGo()">
			
		</td>
	</tr>
	</table>

	<html:hidden property="pageNoBefore"/>
	<html:hidden property="pageNoNext"/>
	<html:hidden property="pageNoEnd"/>
	
<script type="text/javascript" language="Javascript"> 
	function doPageStart(){
	    objFrm = document.forms[0];
	    objFrm.actionMethodId.value = "runPageCtrl";
	    objFrm.pageNo.value = "1";
	    objFrm.pageNoBefore.value = "";
	    objFrm.pageNoNext.value = "";
	    objFrm.pageNoEnd.value = "";
		objFrm.submit();
		}
	function doPageBefore(){
	    objFrm = document.forms[0];
	    objFrm.actionMethodId.value = "runPageCtrl";
		objFrm.pageNoBefore.value = objFrm.pageNo.value - 1;
	    objFrm.pageNo.value = "";
	    objFrm.pageNoNext.value = "";
	    objFrm.pageNoEnd.value = "";
		objFrm.submit();
		}
	function doPageNext(){
	    objFrm = document.forms[0];
	    objFrm.actionMethodId.value = "runPageCtrl";
	    objFrm.pageNoNext.value = objFrm.pageNo.value - 0 + 1;
		objFrm.pageNo.value = "";
	    objFrm.pageNoBefore.value = "";
	    objFrm.pageNoEnd.value = "";
		objFrm.submit();
		}
	function doPageEnd(){
	    objFrm = document.forms[0];
	    objFrm.actionMethodId.value = "runPageCtrl";
	    objFrm.pageNoEnd.value = objFrm.pageNoMax.value ;
		objFrm.pageNo.value = "";
	    objFrm.pageNoBefore.value = "";
	    objFrm.pageNoNext.value = "";
		objFrm.submit();
		}
	function doPageGo(){
		var vGoPageID = document.getElementById("goPageID").value;
		objFrm = document.forms[0];
		objFrm.actionMethodId.value = "runPageCtrl";
	    objFrm.pageNo.value = vGoPageID;
	    objFrm.pageNoBefore.value = "";
	    objFrm.pageNoNext.value = "";
	    objFrm.pageNoEnd.value = "";
        objFrm.submit();
		}
</script>

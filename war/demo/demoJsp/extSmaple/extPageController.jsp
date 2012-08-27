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
		<html:text readonly="true" style="border-width:0px;width:40px;text-align:right" property="pageNo"/>
		/<html:text readonly="true" style="border-width:0px;width:40px" property="pageNoMax"/>
		</td>
		<td>	
			<a href="#" name="pageStart"><bean:message key='pageCtrl.start'/></a>
		</td>
		<td>
			<a href="#" name="pageBefore"><bean:message key='pageCtrl.before'/></a>
		</td>
		<td>
			<a href="#" name="pageNext"><bean:message key='pageCtrl.next'/></a>
		</td>
		<td>
			<a href="#" name="pageEnd"><bean:message key='pageCtrl.end'/></a>
		</td>
		<td>	转入<input style="width:20px " name="textfield2"  type="text" id="goPageID">页<input type="button" name="searchPageGo"  id="searchPageGo" value="GO">
			
		</td>
	</tr>
	</table>

	<html:hidden property="pageNoBefore"/>
	<html:hidden property="pageNoNext"/>
	<html:hidden property="pageNoEnd"/>
	<html:hidden property="gridData"/>
	
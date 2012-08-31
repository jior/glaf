<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld"%>
<%@ taglib prefix="logic" uri="/sys/sysTld/struts-logic.tld"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Array Grid Example</title>

		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/sys/sysJs/ext/resources/css/ext-all.css" />
		<jsp:include page="/sys/sysJsp/common/msgVariable.jsp" flush="true"/>
		<!-- GC -->
		<!-- LIBS -->
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/sys/sysJs/ext/ext-all.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>
		<script type="text/javascript">
			var myData = [];
			<% 
				String s = "";
				if (null != request.getAttribute("myDefaultDetailData")){
					s = (String)request.getAttribute("myDefaultDetailData"); 
					%>
					myData = <%=s%>;
					<%
				}
			%>
		</script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/sys/sysJs/commonUtility.js"></script>
		<!-- ENDLIBS -->

		<script type="text/javascript">
		Ext.onReady( function() {
			//初始化table，并返回初始化后的grid对象，以便后续刷新数据或其他操作时使用
			var grid = initGrid("grid-example");
			
			//根据ID获得按钮组件
	        var btn = Ext.get("submit");
	        //为提交按钮绑定点击事件
	        btn.on("click" , function(){
	                   //异步提交，并将检索到的数据刷新至table中
	                   objFrm = document.forms[0];
			    	   objFrm.actionMethodId.value = "runPageCtrl";
			    	   objFrm.pageNo.value = "1";
			    	   asynchSubmit(objFrm,grid);
			    	    });
                        
            //初始化分页控件
            objFrm = document.forms[0];
            initPageController(objFrm,grid);

		});
		
		function test(){
			alert("test");
		}

		function rowClick(record){
			alert(record.get("company"));
		}
</script>
		<link rel="stylesheet" type="text/css" href="grid-examples.css" />

		<!-- Common Styles for the examples -->
	</head>
	<body>
		<h1> 
			&quot;Array Grid Example 
		</h1>
		<p>
			This example shows how to create a grid from Array data.
			测试中日文切换：<bean:message key='funLeft.menu'/>
		</p>
		<html:form method="POST" action="arrayGrid.do">
			<input type="hidden" value="runPageCtrl" name="actionMethodId">
			<input type="button" value="检索" name="submit" >
			公司名称<html:text property="s_Name"/>
			<div id="grid-example"></div>
			<table id="grid-example-table" width="800" height="270">
			  <tr clickHander="rowClick(record)">
			    <td type="checkCol"></td>
				<td dataIndex="company" title="Company" width="70"></td>
				<td dataIndex="price" title="Price" width="50"></td>
				<td dataIndex="change" title="Change" width="50"></td>
				<td dataIndex="pctChange" title="% Change" width="80"></td>
				<td dataIndex="lastChange" title="Last Updated" width="150" hidden="true"></td>
				<td type="link" dataIndex="lastChange" title="link test" width="150" href="#" 
				    clickHander="test()"></td>
			  </tr>
			</table>
			<jsp:include page="/sys/sysJsp/extSmaple/extPageController.jsp" flush="true"/>
		</html:form>
	</body>
</html>

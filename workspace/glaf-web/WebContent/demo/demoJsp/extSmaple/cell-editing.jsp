<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld"%>
<%@ taglib prefix="logic" uri="/sys/sysTld/struts-logic.tld"%>
<%@ taglib uri="/sys/sysTld/struts-nested.tld" prefix="nested"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Array Grid Example</title>

		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/sys/sysJs/ext/resources/css/ext-all.css" />

		<!-- GC -->
		<!-- LIBS -->
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/sys/sysJs/ext/ext-all.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>//sys/sysJs/frameWorkUtility.js"></script>
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
		</script><script type="text/javascript"
			src="<%=request.getContextPath()%>/sys/sysJs/commonUtility.js"></script>
		
		<script type="text/javascript">
		
		



Ext.onReady(function(){
    
    //初始化table，并返回初始化后的grid对象，以便后续刷新数据或其他操作时使用
    var grid = initGrid("editor-grid");
    
    //装载初始化数据
    grid.store.loadData(myData);
    
    //为插入按钮绑定点击事件
    var btn = Ext.get("insertBut");
    btn.on("click" , function(){
    		   //传入当前新增的grid，并传入每个单元格的默认值
               insertData(grid,"{select:'',common:'New Plant 1',light:'1',price:'0',availDate:''}");
                   });
    
    //为复制按钮绑定点击事件
    var btn = Ext.get("copyBut");
    btn.on("click" , function(){
               copyData(grid);
                   });
    
    //根据ID获得按钮组件
      var btn = Ext.get("save");
      //为提交按钮绑定点击事件
      btn.on("click" , function(){
            //异步提交，并将检索到的数据刷新至table中
            objFrm = document.forms[0];
    	    objFrm.actionMethodId.value = "runSave";
			asynchSubmit(objFrm,grid);
                     });
    
});	

function buttonTest(record){
	alert("buttonTest:" + record.get("common"));
}

</script>
		<link rel="stylesheet" type="text/css" href="grid-examples.css" />

		<!-- Common Styles for the examples -->
	</head>
	<body>
		<h1>Cell Editing Grid Example</h1>
    <p>This example shows how to enable users to edit the contents of a grid.</p>
    <p>Note that the js is not minified so it is readable. </p>
    <html:form method="POST" action="cellEditing.do">
			<input type="hidden" value="runGetData" name="actionMethodId">
			
			<input type="button" value="保存" name="save">
			<div id="editor-grid"></div>
			<table id="editor-grid-table" width="800" height="280">
			  <tr>
			  	<td type="tbar" dataIndex="insert"></td>
			  	<td type="tbar" dataIndex="copy"></td>
			  </tr>
			  <tr>
			    <td type="checkCol"></td>
			    <td type="checkbox" dataIndex="select" title="select" 
			        width="70"></td>
			    <td type="text" dataIndex="common" title="Common Name" width="70"
			        allowBlank="false"></td>
				<td type="combobox" dataIndex="light" title="Light" 
				    comboData="<bean:write name="lightDTOJson"/>" width="70"></td>
				<td type="button" dataIndex="testButton" title="testButton" 
				    width="70" icon="<%=request.getContextPath()%>/sys/sysImages/bj.png" 
				    clickHander="buttonTest(record)"></td>
				<td type="textNum" dataIndex="price" title="Price" 
				    minValue="0" maxValue="10" allowBlank="false" width="70"></td>
				<td type="textDate" dataIndex="availDate" title="Available" 
				    format="m/d/Y" width="70"></td>
			  </tr>
			</table>
			
		</html:form>
		
	</body>
<jsp:include page="//sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>
</html>

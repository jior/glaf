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
    var grid = initGrid("manageBatch");
    
    //装载初始化数据
    //grid.store.loadData(myData);
    
    //为插入按钮绑定点击事件
    var btn = Ext.get("insertBut");
    btn.on("click" , function(){
    		   //传入当前新增的grid，并传入每个单元格的默认值
               insertData(grid,"{batName:'',startDate:'',interval:'86400000',batClassName:'',what:'',isUse:'1',status:'stay'}");
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
   
   //根据ID获得按钮组件
     var btn = Ext.get("select");
     //为提交按钮绑定点击事件
     btn.on("click" , function(){
                //异步提交，并将检索到的数据刷新至table中
                objFrm = document.forms[0];
   	   objFrm.actionMethodId.value = "runPageCtrl";
   	   asynchSubmit(objFrm,grid);
   	    });
   	
   	//根据ID获得按钮组件
      var btn = Ext.get("delete");
      //为提交按钮绑定点击事件
      btn.on("click" , function(){
            //异步提交，并将检索到的数据刷新至table中
            objFrm = document.forms[0];
    	    objFrm.actionMethodId.value = "runDelete";
			asynchSubmit(objFrm,grid);
                     });
    
});	


function getFormatData(formatDTO){

	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	xmlhttp.open("POST","commonDTO.do?actionMethodId=getCommonDTO&dtoName=" + formatDTO, true);
	xmlhttp.send();

    var x = "";
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			//document.getElementById("grid-example").innerHTML=xmlhttp.responseText;
			x = xmlhttp.responseText;
			alert(x);
		}
	}
	return x;
}

</script>
		<link rel="stylesheet" type="text/css" href="grid-examples.css" />

		<!-- Common Styles for the examples -->
	</head>
	<body>
		<h1>batch管理画面</h1>
    <html:form method="POST" action="manageBatch.do">
			<input type="hidden" value="runGetData" name="actionMethodId">
			<input type="hidden" value="" name="curBatName">
			<input type="hidden" value="" name="curBatStatus">
			<input type="hidden" value="" name="curBatWhat">
			<input type="button" value="检索" name="select">
			<input type="button" value="保存" name="save">
			<input type="button" value="删除" name="delete">
			batch名<html:text property="s_Name"/>
			<div id="manageBatch"></div>
			<table id="manageBatch-table" width="800" height="280">
			  <tr>
			  	<td type="tbar" dataIndex="insert"></td>
			  </tr>
			  <tr>
			    <td type="text" dataIndex="batName" title="batch名" allowBlank="false"
			        width="70"></td>
			    <td type="text" dataIndex="startDate" title="最近执行时间" width="130"
			        allowBlank="false"></td>
				<td type="text" dataIndex="interval" title="间隔时间" width="80"
			        allowBlank="false"></td>
			    <td type="text" dataIndex="what" title="执行内容" width="160"
			        allowBlank="false"></td>
				<!-- <td type="combobox" dataIndex="isUse" title="启用标志" 
				    comboData="startDTOJson" width="70"></td> -->
				<td dataIndex="status" title="当前状态" width="80"
			        allowBlank="false"></td>
				
			  </tr>
			</table>
			<jsp:include page="/sys/sysJsp/extSmaple/extPageController.jsp" flush="true"/>
		</html:form>
		
	</body>
<jsp:include page="//sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>
</html>

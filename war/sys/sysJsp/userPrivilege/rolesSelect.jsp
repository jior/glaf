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
		<title>角色选择</title>

		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/sys/sysJs/ext/resources/css/ext-all.css" />
		<jsp:include page="/sys/sysJsp/common/msgVariable.jsp" flush="true" />
		<!-- GC -->
		<!-- LIBS -->
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/sys/sysJs/ext/ext-all.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>//sys/sysJs/frameWorkUtility.js"></script>
		<script type="text/javascript">
			var myData = [];
			<%String s = "";
			if (null != request.getAttribute("myDefaultDetailData")) {
				s = (String) request.getAttribute("myDefaultDetailData");%>
					myData = <%=s%>;
					<%}%>
		</script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/sys/sysJs/commonUtility.js"></script>
		<!-- ENDLIBS -->
		<script type="text/javascript">
		</script>
		<script type="text/javascript">
Ext.onReady(function(){
    
    //初始化table，并返回初始化后的grid对象，以便后续刷新数据或其他操作时使用

    var grid = initGrid("editor-grid");
    
    //装载初始化数据

    grid.store.loadData(myData);
});	

function onOk() {
	//获取grid中的所有选中行数据



	var selectedRecords = grid.getSelectionModel().getSelection();
    var recordsJsonStr = "";
    Ext.each(selectedRecords,function(arecord){
        recordsJsonStr = recordsJsonStr + "," + JsonToStr(arecord.data);
                  });                 
    //过滤掉{}和"
    recordsJsonStr = recordsJsonStr.substring(1,recordsJsonStr.length);
    recordsJsonStr = recordsJsonStr.replace(/{}/g,"''");
    recordsJsonStr = recordsJsonStr.replace(/\"/g,"'");
    recordsJsonStr = "[" + recordsJsonStr + "]";

	if(recordsJsonStr=="[]"){	
		var userid= document.getElementById("userId");		
		if(!confirm("确定删除该用户对应的所有角色么?"))
			return;
	}
	
    objFrm = document.forms[0];
    objFrm.actionMethodId.value = "runSave";
	asynchSubmit(objFrm,grid);
}

function extCalllBack(){
	window.returnValue = "OK"; 
  	window.close();
}
</script>
		<link rel="stylesheet" type="text/css" href="grid-examples.css" />

	</head>
	<body>
		<html:form method="POST" action="rolesSelect.do">
			<input type="hidden" value="runGetData" name="actionMethodId">
			<html:hidden property="userId"></html:hidden>
			<html:hidden property="extCBFlag"/>		
			<div id="editor-grid"></div>
			<table id="editor-grid-table" width="300" height="200">
				<tr>
					<td type="checkCol" width="60"></td>
					<td dataIndex="p_roleName" title="角色名称" width="180"
						allowBlank="false"></td>
					<td dataIndex=p_roleId title="角色ID" width="80" hidden="true"></td>
				</tr>
			</table>
		</html:form>
		<div>
			<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input name="btnOk" type="button" value="确定" onclick="onOk();" /></td>
			<td><input name="btnCancle" type="button" class="button" value="取消"
					onclick="javascript:window.close();" /></td>
			</tr>
		</div>
	</body>
	<jsp:include page="//sys/sysJsp/common/showAllTypeMessage.jsp"
		flush="true" />
</html>

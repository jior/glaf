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
		<title>用户信息维护</title>

		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/sys/sysJs/ext/resources/css/ext-all.css" />
		<jsp:include page="/sys/sysJsp/common/msgVariable.jsp" flush="true" />
		<!-- GC -->
		<!-- LIBS -->
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/sys/sysJs/ext/ext-all.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>
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
		
Ext.onReady(function(){
    
    //初始化table，并返回初始化后的grid对象，以便后续刷新数据或其他操作时使用


    var grid = initGrid("editor-grid");
    //装载初始化数据



    grid.store.loadData(myData);
    //为插入按钮绑定点击事件



    var btn = Ext.get("insertBut");
    btn.on("click" , function(){
    		   //传入当前新增的grid，并传入每个单元格的默认值
               insertData(grid,"{p_userID:'',p_Name:'',p_Password:'',p_Email:'',p_Status:'启用',p_rolesName:''}");
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
      		//检查数据


      		if(!check("save")) return;
            //异步提交，并将检索到的数据刷新至table中


            objFrm = document.forms[0];
    	    objFrm.actionMethodId.value = "runSave";
			asynchSubmit(objFrm,grid);
                     });
  	 //根据ID获得按钮组件
      var btn = Ext.get("delete");
      //为提交按钮绑定点击事件



      btn.on("click" , function(){
      		//检查数据


      		if(!check("delete")) return;
            //异步提交，并将检索到的数据刷新至table中


            objFrm = document.forms[0];
    	    objFrm.actionMethodId.value = "runDelete";
			asynchSubmit(objFrm,grid);
                     });
	  //根据ID获得按钮组件
      var btn = Ext.get("query");
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
	
	
function buttonTest(record){
	if(record.get("p_flag") == ""){
		alert("请先保存该行新增数据后再维护其角色信息！");
		return;
	}
	if(record.get("p_userID") != record.get("p_flag")){
		alert("请先保存该行修改数据后再维护其角色信息！");
		return;
	}
	var rolesId = record.get("p_rolesId");
	var userId = record.get("p_userID");
	var src = "<%=request.getContextPath()%>";
	var url = src + "/rolesSelect.do" + "?rolesId=" + rolesId + "&userId=" + userId;
	var arr = window.showModalDialog(url,window,"dialogWidth:" + 350
			+ "px;dialogHeight:" + 350
			+ "px;center:yes;help:no;resizable:no;status:no;scroll:no"
			);
	if (arr) {
		//保存成功刷新页面
		objFrm = document.forms[0];
		objFrm.actionMethodId.value = "runPageCtrl";
        asynchSubmit(objFrm,grid);
	}
}

function check(doflag){
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
	if(recordsJsonStr != "[]"){
		if(doflag == "delete"){
			if(!confirm("确定删除所选用户?")){
				return false;
			}else
			{
				return true;
			}			
		}
		if(doflag == "save"){
			var returnArray=eval(recordsJsonStr);			
			for(var i=0;i<returnArray.length;i++){
				var p_userID = returnArray[i].p_userID;
   				if(p_userID == undefined  || Trim(p_userID) == ""){
   					alert("用户名不能为空！");
   					return false;
   				}
   				
   				var p_Name = returnArray[i].p_Name;
   				if(p_Name == undefined  || Trim(p_Name) == ""){
   					alert("姓名不能为空！");
   					return false;
   				}
   				
   				var p_Password = returnArray[i].p_Password;
   				if(p_Password == undefined  || Trim(p_Password) == ""){
   					alert("密码不能为空！");
   					return false;
   				}
   				
   				if(Trim(p_userID).length > 10){
   					alert("用户名长度必须小于等于10位！");
   					return false;
   				}
   				
   				if(Trim(p_Name).length > 15){
   					alert("姓名长度必须小于等于15位！");
   					return false;
   				}

   				if(Trim(p_Password).length<7 || Trim(p_Password).length > 15){
   					alert("密码长度必须大于等于7位小于等于15位！");
   					return false;
   				}
			}
			return true;
		}
	}else{
		alert("没有选择任何数据！");
		return false;
	}
}

</script>
		<link rel="stylesheet" type="text/css" href="grid-examples.css" />

	</head>
	<body style="overflow-x: hidden;" bgcolor="#f5f9ed">

		<html:form method="POST" action="userManage.do">
			<input type="hidden" value="runGetData" name="actionMethodId">
			<div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="27" class="Title style1" align="center">
							用户信息维护
						</td>
					</tr>
					<tr>
						<td align="left" style="line-height: 20px" height="30"
							bgcolor="#f5f9ed">
							<table width="813" height="50" border="0" cellpadding="0"
								cellspacing="0" style="line-height: 40px;">
								<tr>
									<td nowrap width="22%">
										用户名

										<html:text property="queryUserId"></html:text>
									</td>
									<td nowrap width="22%" align="right">
										姓名
									</td>
									<td>
										<html:text property="queryUseName"></html:text>
									</td>
								</tr>
								<tr>
								<td rowspan="3">
									<input type="button" value="查询" name="query">
									<input type="button" value="保存" name="save">
									<input type="button" value="删除" name="delete">
								</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<br />
			<div id="editor-grid"></div>
			<table id="editor-grid-table" width="800" height="280">
				<tr>
					<td type="tbar" dataIndex="insert"></td>
					<td type="tbar" dataIndex="copy"></td>
				</tr>
				<tr>
					<td type="text" dataIndex="p_userID" title="用户名" allowBlank="false"
						width="70"></td>
					<td type="text" dataIndex="p_Name" title="姓名" allowBlank="false"
						width="70"></td>
					<td type="text" dataIndex="p_Password" title="密码"
						allowBlank="false" width="70"></td>
					<td type="text" dataIndex="p_Email" title="Email" allowBlank="true"
						width="70"></td>
					<td type="combobox" dataIndex="p_Status" title="状态" 
				    	comboData="<bean:write name="lightDTOJson"/>" width="70"></td>
					<td dataIndex="p_rolesName" title="角色名称" width="200"></td>
					<td type="button" dataIndex="testButton" title="编辑角色" width="70"
						icon="<%=request.getContextPath()%>/sys/sysImages/bj.png"
						clickHander="buttonTest(record)"></td>
				</tr>
			</table>
			<jsp:include page="/sys/sysJsp/extSmaple/extPageController.jsp" flush="true" />
		</html:form>

	</body>
	<jsp:include page="//sys/sysJsp/common/showAllTypeMessage.jsp"
		flush="true" />
</html>

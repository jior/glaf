<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="baseSrc.common.Constants"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld"%>
<%@ taglib uri="/sys/sysTld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/sys/sysTld/struts-logic.tld" prefix="logic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>上传下载</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<LINK rel="stylesheet" type="text/css" href="sys/sysCss/menu/css.css">
<LINK rel=stylesheet type=text/css href="sys/sysCss/menu/style-custom.css">
<link href="sys/sysCss/menu/jquery.css" rel="stylesheet" type="text/css"
	media="screen">
	<style type="text/css">
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
			overflow-X: hidden;
		}
		</style>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/calendar/WdatePicker.js"></script>
	
<body>
	<html:form method="POST" action="uploadDown.do">
		<input type="hidden" value="runPageIscSample" name="actionMethodId">
		<input type="hidden" name="screenId" value="${yfclForm.screenId }">
		<div style="width: 840px; height:700px; overflow-y: auto; float: left;">
		<table width="800px" border="0" cellspacing="0" cellpadding="0">
			<tr >
				<!-- 按钮区域 -->
				<td align="right" style="line-height: 20px" height="30"	bgcolor="#f5f9ed" >
					<table width="100%" border="0">
						<tr>
							<td class="Title style1" align="left">
							图片上传（显示）
							</td>
							<td class="Title style1" align="right" width="30px">
								<input type="button" 
									style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px"
									value="上传" 
									name="search" 
									onclick="doUpload1();" >
							</td>
						</tr>
					</table>
				</td>				
			</tr>
			<tr>
				<!-- 数据显示区域 -->
				<td >
						<div id="showTable1"  style="width: 800px; height:80px; overflow-y: scroll;">
							<table  class=EOS_table border=0 width="780px" align="left">
								<thead>
									<tr>
										<th width="60%" nowrap class="T2">文件名称(全路径)</th>
										<th width="30%" nowrap class="T2">文件编号</th>
										<th  nowrap class="T2">操作</th>
									</tr>
								</thead>
							</table>
						</div>
				</td>		
			</tr>
			
			
			<tr >
				<!-- 按钮区域 -->
				<td align="right" style="line-height: 20px" height="30"	bgcolor="#f5f9ed" >
					<table width="100%" border="0">
						<tr>
							<td class="Title style1" align="left">
							图片上传（不显示）
							</td>
							<td class="Title style1" align="right" width="30px">
								<input type="button" 
									style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px"
									value="上传" 
									name="search" 
									onclick="doUpload2();" >
							</td>
						</tr>
					</table>
				</td>				
			</tr>
			<tr>
				<!-- 数据显示区域 -->
				<td >
						<div  id="showTable2"  style="width: 800px; height:80px; overflow-y: scroll;">
							<table  class=EOS_table border=0 width="780px" align="left">
								<thead>
									<tr>
										<th width="60%" nowrap class="T2">文件名称(全路径)</th>
										<th width="30%" nowrap class="T2">文件编号</th>
										<th  nowrap class="T2">操作</th>
									</tr>
								</thead>
							</table>
						</div>
				</td>		
			</tr>
			
			
			<tr >
				<!-- 按钮区域 -->
				<td align="right" style="line-height: 20px" height="30"	bgcolor="#f5f9ed" >
					<table width="100%" border="0">
						<tr>
							<td class="Title style1" align="left">
							文件上传
							</td>
							<td class="Title style1" align="right" width="30px">
								<input type="button" 
									style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px"
									value="上传" 
									name="search" 
									onclick="doUpload3();" >
							</td>
						</tr>
					</table>
				</td>				
			</tr>
			<tr>
				<!-- 数据显示区域 -->
				<td >
						<div id="showTable3"  style="width: 800px; height:80px; overflow-y: scroll;">
							<table  class=EOS_table border=0 width="780px" align="left">
								<thead>
									<tr>
										<th width="60%" nowrap class="T2">文件名称(全路径)</th>
										<th width="30%" nowrap class="T2">文件编号</th>
										<th  nowrap class="T2">操作</th>
									</tr>
								</thead>
							</table>
						</div>
				</td>		
			</tr>
			
			
			<tr >
				<!-- 按钮区域 -->
				<td align="right" style="line-height: 20px" height="30"	bgcolor="#f5f9ed" >
					<table width="100%" border="0">
						<tr>
							<td class="Title style1" align="left">
							批量上传
							</td>
							<td class="Title style1" align="right" width="30px">
								<input type="button" 
									style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px"
									value="上传" 
									name="search" 
									onclick="doUpload4();" >
							</td>
						</tr>
					</table>
				</td>				
			</tr>
			<tr>
				<!-- 数据显示区域 -->
				<td >
						<div id="showTable4" style="width: 800px; height:80px; overflow-y: scroll;">
							<table  class=EOS_table border=0 width="780px" align="left">
								<thead>
									<tr>
										<th width="60%" nowrap class="T2">文件名称(全路径)</th>
										<th width="30%" nowrap class="T2">文件编号</th>
										<th  nowrap class="T2">操作</th>
									</tr>
								</thead>
							</table>
						</div>
				</td>		
			</tr>
			
			<tr >
				<!-- 按钮区域 -->
				<td align="right" style="line-height: 20px" height="30"	bgcolor="#f5f9ed" >
					<table width="100%" border="0">
						<tr>
							<td class="Title style1" align="left">
							上传后打包压缩
							</td>
							<td class="Title style1" align="right" width="30px">
								<input type="button" 
									style="background:url('<%=request.getContextPath()%>/sys/sysImages/btn_01.gif');border:0;height:25px;width:82px"
									value="上传" 
									name="search" 
									onclick="doUpload6();" >
							</td>
						</tr>
					</table>
				</td>				
			</tr>
			<tr>
				<!-- 数据显示区域 -->
				<td >
						<div id="showTable6" style="width: 800px; height:80px; overflow-y: scroll;">
							<table  class=EOS_table border=0 width="780px" align="left">
									<tr>
										<th width="60%" nowrap class="T2">文件名称</th>
										<th width="30%" nowrap class="T2">文件编号</th>
										<th  nowrap class="T2">操作</th>
									</tr>
							</table>
						</div>
				</td>		
			</tr>
		</table>
		</div>		
		<div id="showimg" style="width: 150px;height: 150px;">图片显示
		</div>
	</html:form>
	
	<script type="text/javascript" language="Javascript">
	var showtr = "<table  class=EOS_table border=0 width='780px' align='left'><tr><th width='60%' nowrap class='T2'>文件名称(全路径)</th><th  width='30%' nowrap class='T2'>文件编号</th><th  nowrap class='T2'>操作</th></tr>";
	function doUpload1(){
		openIMGUploadWithCallBack(callBack1,'127.0.0.1','DemoPage','yeah01','YES','gif');
	}
	function doUpload2(){
		openIMGUploadWithCallBack(callBack2,'127.0.0.1','DemoPage','yeah01','NO','xlsx,xls,gif');
	}
	function doUpload3(){
		openOneUploadWithCallBack(callBack3,'127.0.0.1','DemoPage','yeah01');
	}
	function doUpload4(){
		openUploadWithCallBack(callBack4,'127.0.0.1','DemoPage','yeah01');
	}
	function doUpload5(){
		openZIPUploadWithCallBack(callBack5,'127.0.0.1','DemoPage','yeah01');
	}
	function doUpload6(){
		openZIPAfterUploadWithCallBack(callBack6,'127.0.0.1','DemoPage','yeah01','啊');
	}
	function callBack1(back,showFlag){
		var showTable = document.getElementById("showTable1");
		var showimg = document.getElementById("showimg");
		var str = back[1];
		var strs = str.split(",");
		var tr = showtr;
		var inum = parseInt(back[0]);
		for(var i=0;i<strs.length;i++){
			tr += "<tr><td width='60%'>"+strs[i]+"</td><td width='30%'>"+(inum - strs.length + i + 1) + "</td><td><a id=\"down\" href=\"Download?fileId="+(inum - strs.length + i + 1)+"\">下载</a></td></tr>";
		}
		tr +="</table>";
		showTable.innerHTML=tr;        
        if(showFlag=='YES'){
        	showimg.innerHTML="<img width='150px' height='150px' alt='测试' src='<%=request.getContextPath()%><%=Constants.UPLOAD_DIR%>"+strs[0]+"' />";
        }
     }
	function callBack2(back,showFlag){
		var showTable = document.getElementById("showTable2");
		var showimg = document.getElementById("showimg");
		var str = back[1];
		var strs = str.split(",");
		var tr = showtr;
		var inum = parseInt(back[0]);
		for(var i=0;i<strs.length;i++){
			tr += "<tr><td width='60%'>"+strs[i]+"</td><td width='30%'>"+(inum - strs.length + i + 1) + "</td><td><a id=\"down\" href=\"Download?fileId="+(inum - strs.length + i + 1)+"\">下载</a></td></tr>";
		}
		tr +="</table>";
		showTable.innerHTML=tr;        
        if(showFlag=='YES'){
        	showimg.innerHTML="<img width='150px' height='150px' alt='测试' src='<%=request.getContextPath()%><%=Constants.UPLOAD_DIR%>"+back[1]+"' />";
        }
     }
	
	function callBack3(back){
		var showTable = document.getElementById("showTable3");
		var str = back[1];
		var strs = str.split(",");
		var tr = showtr;
		var inum = parseInt(back[0]);
		for(var i=0;i<strs.length;i++){
			tr += "<tr><td width='60%'>"+strs[i]+"</td><td width='30%'>"+(inum - strs.length + i + 1) + "</td><td><a id=\"down\" href=\"Download?fileId="+(inum - strs.length + i + 1)+"\">下载</a></td></tr>";
		}
		tr +="</table>";
		showTable.innerHTML=tr;        
     }
	function callBack4(back){
		var showTable = document.getElementById("showTable4");
		var str = back[1];
		var strs = str.split(",");
		var tr = showtr;
		var inum = parseInt(back[0]);
		for(var i=0;i<strs.length;i++){
			tr += "<tr><td width='60%'>"+strs[i]+"</td><td width='30%'>"+(inum - strs.length + i + 1) + "</td><td><a id=\"down\" href=\"Download?fileId="+(inum - strs.length + i + 1)+"\">下载</a></td></tr>";
		}
		tr +="</table>";
		showTable.innerHTML=tr;        
     }
	
	function callBack5(back){
		var showTable = document.getElementById("showTable5");
		var str = back[1];
		var strs = str.split(",");
		var tr = showtr;
		var inum = parseInt(back[0]);
		for(var i=0;i<strs.length;i++){
			tr += "<tr><td width='60%'>"+strs[i]+"</td><td width='30%'>"+(inum - strs.length + i + 1) + "</td><td><a id=\"down\" href=\"Download?fileId="+(inum - strs.length + i + 1)+"\">下载</a></td></tr>";
		}
		tr +="</table>";
		showTable.innerHTML=tr;        
     }
	function callBack6(back){
		var showTable = document.getElementById("showTable6");
		var str = back[1];
		var strs = str.split(",");
		var tr = showtr;
		var inum = parseInt(back[0]);
		for(var i=0;i<strs.length;i++){
			tr += "<tr><td width='60%'>"+strs[i]+"</td><td width='30%'>"+(inum - strs.length + i + 1) + "</td><td><a id=\"down\" href=\"Download?fileId="+(inum - strs.length + i + 1)+"\">下载</a></td></tr>";
		}
		tr +="</table>";
		showTable.innerHTML=tr;    
     }
	</script>	
</body>
 <jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp" flush="true"/>
</html>
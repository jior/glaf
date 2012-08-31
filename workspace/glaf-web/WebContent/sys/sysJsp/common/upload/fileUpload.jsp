<%@page import="baseSrc.common.BaseUtility"%>
<%@page import="baseSrc.framework.BaseConstants"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="baseSrc.common.upload.*"%>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld"%>
<%
  String cip = request.getParameter("cip");
  String cid = request.getParameter("cid");
  String uid = request.getParameter("uid");
  String rarFlag = request.getParameter("rarFlag");
  String zipFileName = request.getParameter("zipFileName");
  if(!BaseUtility.isStringNull(zipFileName)){
	  zipFileName = new String(zipFileName.getBytes("ISO-8859-1"),"gbk");
  }
  String oneFlag = request.getParameter("oneFlag");
  request.getSession().setAttribute("cip",cip);
  request.getSession().setAttribute("cid",cid);
  request.getSession().setAttribute("uid",uid);
  //上传文件是否压缩  YES:压缩； NO:不压缩，默认不压缩
  request.getSession().setAttribute("rarFlag",rarFlag);
  double fileSizeMax = (int)(((double)(BackGroundService.UPLOAD_FILE_SIZE_MAX / 1024 / 1024.0)) * 100) / 100.0;
  int zipNameFiv = BaseConstants.ISC_ZIPNAME_SIZE_FIVE;
	String type = request.getParameter("type");
	if (type == null) {
	  type = "";
	}
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<base target="_self" />
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<link href="./css/fileUpload.css" type="text/css" rel="stylesheet"/>
<link href="./css/site.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="./js/prototype.js"></script>
<script type="text/javascript" src="./js/AjaxWrapper.js"></script>
<script type="text/javascript" src="../../js/verify.js"></script>
<script type="text/javascript">

 function IsUploadFileType(file,type){
	 var types = type.split(",");
	 var fileName = file.value;
	 file.style.backgroundColor = "white";
	 for(var i=0;i<types.length;i++){
		 var typ = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length);
		 if(typ.toUpperCase() == types[i].toUpperCase()){
			 return true;
		 }
	 }
	 file.style.backgroundColor = "red";
	 return false;
 }
 function checkFileUploadForm() {
	var bos = document.getElementById("upload-box");
	var files = bos.getElementsByTagName("input");
	var rarFlag = document.getElementById("zipName");
	var startFile,doubleFile;
	for(var i = 0;i<files.length;i++){
		startFile = files[i];
		if (files[i].value == '') {
			var alrMeg = document.getElementById("nullMeg");
			alert(alrMeg.value);
			return false;
		}
		var type = '<%= type %>';
		if (type.length > 0 && !IsUploadFileType(files[i], type)) {
			var errorMeg = document.getElementById("errorMeg");
		  alert(errorMeg.value + type);
		  return false;
		}
		for(var j=i+1;j<files.length;j++){
			doubleFile = files[j];
			if(startFile.type=='file'&&doubleFile.type=='file'){
				startFile.style.backgroundColor = "white";
				doubleFile.style.backgroundColor = "white";
				if(startFile.value == doubleFile.value){
					startFile.style.backgroundColor = "red";
					doubleFile.style.backgroundColor = "red";
					alert("<bean:message key='upload.doubleError'/>");
					return false;
				}
			}
		}
	}
	document.getElementById("cancelUploadButton").disabled=false;
	
	objFrm = document.forms[0];
	if(confirm("<bean:message key='upload.isUpload'/>")){
		if(rarFlag){
			if(rarFlag.value!=null&&rarFlag.value!=""){
				var partment = rarFlag.value;
				partment=encodeURI(partment); 
				partment=encodeURI(partment); 
				objFrm.action="./BackGroundService.action?rarFileName="+partment;
			}
		}
		startProgress();
		objFrm.submit();
	}
	
 }
 function addFileUpload(){
	 var isadd = true;
	 var boxs = document.getElementById("upload-box"); 
	 var inputs = boxs.getElementsByTagName("input");
	 var index = inputs.length;
	 for(var i=0;i<inputs.length;i++){
		 if(inputs[i].value==""){
			 isadd = false;
		 }
	 }
	 if(isadd){
		 var child = document.createElement("input");
		 child.name = "file";
		 child.type = "file";
		 child.style.backgroundColor = "white";
		 child.size = "40";
		 boxs.appendChild(child);
		 //var child1 = document.createElement("input");
		 //child1.type = "button";
		 //child1.value = "删除";
		 //child1.style.border = "0px";
		 //child1.style.backgroundColor = "white";
		 var child1 = document.createElement("img");
		 child1.alt = "<bean:message key='upload.delete' />";
		 child1.src = "<%=request.getContextPath()%>/sys/sysImages/mdelete.png";
		 child1.onclick=function(){
			 deletefile(child,child1);
		 };
		 boxs.appendChild(child1);
	 }else{
		 alert('<bean:message key="upload.addnullerror" />');
	 }
 }
 function deletefile(chil,deletea){
	
	 var boxs = document.getElementById("upload-box");
	 boxs.removeChild(chil);
	 boxs.removeChild(deletea);
 }
</script>
<title><bean:message key="upload.title" /></title>

</head>
<body>
<div id="controlPanel" >
  <input type="hidden" value="<bean:message key="upload.nullerror" />" id="nullMeg" />
  <input type="hidden" value="<bean:message key="upload.error" />" id="errorMeg" />
  <div id="readme"><bean:message key="upload.shuoming" />&nbsp;&nbsp;<bean:message key="upload.max" /><%= fileSizeMax %>M</div>
  <div id="uploadFileUrl"></div>
  <%if("YESTOZIP".equals(rarFlag)){%>
    	<bean:message key="upload.zipFile" /><input style="background-color: white;" name="zipName" type="text" id="zipName" value="<%=zipFileName %>" size="26" maxlength="<%=zipNameFiv %>" />
    <%} %>
  <form id="fileUploadForm" name="fileUploadForm" action="./BackGroundService.action" enctype="multipart/form-data" method="post">
   <div style="width: 415px; height:120px; overflow-y: scroll;">
    <div id="upload-box">
		<input style="background-color: white;" name="file" type="file" size="40" />
		</div>
    </div>
    <%if(!"YES".equals(oneFlag)){ %>
    	<input name="addButton" type="button" class="button" id="addButton" value="<bean:message key="upload.add" />" onclick="addFileUpload()"/>
    <%} %>
    <input name="uploadButton" type="button" class="button" id="uploadButton" value="<bean:message key="upload.submit" />" onclick="checkFileUploadForm()"/>
    <input name="cancelUploadButton" type="button" class="button" id="cancelUploadButton" value="<bean:message key="upload.close" />" onclick="window.close();" />
  </form>
    <br />
  <div id="progressBar">
    <div id="theMeter">
      <div id="progressBarText"></div>
      <div id="totalProgressBarBox">
        <div id="totalProgressBarBoxContent"></div>
      </div>
    </div>
    <div id="progressStatusText"></div>
  </div>
</div>
<script type="text/javascript" src="./js/upload.js"></script>
</body>
</html>
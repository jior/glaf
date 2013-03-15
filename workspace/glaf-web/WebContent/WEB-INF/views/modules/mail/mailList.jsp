<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件信息</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<%
  Paging jpage = (Paging)request.getAttribute("jpage");
  if(jpage == null) {
	  jpage = Paging.EMPTY_PAGE;
  }
  request.setAttribute("rows", jpage.getRows());
  
  request.setAttribute("imagePath", request.getContextPath()+"/images/go.gif");
  int sortNo = (jpage.getCurrentPage()-1) * jpage.getPageSize() + 1;
%>
 

<script language="javascript">

var contextPath = "<%=request.getContextPath()%>";

function query(){
    location.href="<%=request.getContextPath()%>/mx/mail/query?mailType=${mailType}";
}

function create(){
   location.href='<%=request.getContextPath()%>/mx/mail/compose?mailType=${mailType}';
}

function remove(mailId){
	if(confirm("您真的要删除记录吗？")){
     var link='<%=request.getContextPath()%>/mx/mail/delete?mailBox=${mailBox}&mailType=${mailType} &mailId='+mailId;
     location.href=link;
	}
}

function deleteAll(){
	  var tmp = "";
	  for (var i=0; i<document.iForm.elements.length; i++){
	      var e = document.iForm.elements[i];
	      if (e.name == "mailId"){
		      if(e.checked == true){
		         tmp = tmp + e.value+",";
	       }
		  }
	  }
	  if(tmp == ""){
          alert("请选择至少一条记录！");
		  return;
	  }

    if(confirm("您真的要删除记录吗？")){
		document.iForm.mailIds.value=tmp;
		document.iForm.action="<%=request.getContextPath()%>/mx/mail/deleteAll";
		document.iForm.submit();
	}
}

 function checkAll()
 {
	var checkFlag = document.iForm.checkFlag.value;
    if(checkFlag == 1){
	   document.iForm.checkFlag.value="0";
	   for (var i=0;i<document.iForm.elements.length;i++)
		{
		    var e = document.iForm.elements[i];
			if (e.name == "mailId"){
			e.checked = false;
		  }
		}
	}
	else {
       document.iForm.checkFlag.value="1";
	   for (var i=0;i<document.iForm.elements.length;i++)
		{
		    var e = document.iForm.elements[i];
			if (e.name == "mailId"){
			e.checked = true;
		  }
		}
    }
 }

 function showXY(){
     var obj = document.getElementById("query_div");
	 if(obj.style.display == "block"){
	   obj.style.display = 'none';
	 } else {
	   if(obj.style.display == "none"){
		 obj.style.display = 'block';
		}
	 }  
  }


</script>
</head>
<body style="padding-left:20px;padding-right:20px">
<center><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="邮件列表">
邮件列表</div>
<table align="center" class="table-border" cellspacing="0"
	cellpadding="0" width="95%">
	<tr>
		<td height="30" align="right">
		<input type="button" value="查询"
			name="query" class="btn" onclick="javascript:showXY();"> 
		<input
			type="button" value="新增" name="create" class="btn"
			onclick="javascript:create();">
		<%if(jpage.getRows().size() > 0){%>
		<input type="button" value="删除" name="delete" class="btn"
			onclick="javascript:deleteAll();"> <%}%>
		</td>
	</tr>
</table>
 
<div id="query_div" class="content-block"
	style="display: none; width: 95%;"><br>
<form id="mailForm" name="mailForm" class="x-form" method="post"
	action="<%=request.getContextPath()%>/mx/mail/mailList">
<input type="hidden" name="mailType"
	value="${mailType}"> <input type="hidden"
	name="mailBox" value="${mailBox}">
<table id='query_table' align='center' width='98%'>
	<tr height="27">
		<td width='25%'>主题 &nbsp;&nbsp; <input id="x_query_subject"
			name="x_query_subject" type="text" size="20" class="input-medium" /></td>
		<td width='25%'>发件人 &nbsp;&nbsp; <input type="hidden"
			id="x_query_senderId" name="x_query_senderId" value="" /> <input
			type="text" id="x_users_name" name="x_users_name" class="input-medium"
			size="20" readonly style="cursor: pointer;"
			onclick="javacsript:selectUser( 'x_query_senderId','x_users_name');"
			value=""></td>
		<td width='25%'>收件人 &nbsp;&nbsp; <input type="hidden"
			id="x_query_receiverId" name="x_query_receiverId" value="" /> <input
			type="text" id="x_receive_users_name" name="x_receive_users_name"
			class="input-medium" size="20" readonly style="cursor: pointer;"
			onclick="javacsript:selectUser( 'x_query_receiverId','x_receive_users_name');"
			value=""></td>
		<td width='25%'><input type="submit" name="submit" value=" 检索 "
			class="btn"></td>
	</tr>
</table>
</form>
</div>
 

<form name="iForm" method="post"
	action="<%=request.getContextPath()%>/mx/mail?method=mailList">
<input type="hidden" name="mailType"
	value="${mailType}"> <input type="hidden"
	name="mailBox" value="${mailBox}"> <input
	type="hidden" id="checkFlag" name="checkFlag" value=""> <input
	type="hidden" id="mailIds" name="mailIds" value="">
<table id="screen" width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td noWrap>
		<table border="0" cellspacing="0" cellpadding="0" class="x-tabs-box">
			<tr>
				<td class="x-tab-l<c:if test="${ mailBox eq 'R' }">c</c:if>">&nbsp;</td>
				<td width="65"
					class="x-tab-m<c:if test="${  mailBox eq 'R' }">c</c:if>"><a
					href="<%=request.getContextPath()%>/mx/mail/mailList?mailType=${mailType}&mailBox=R">收件箱</a>
				</td>
				<td class="x-tab-r<c:if test="${  mailBox eq 'R'}">c</c:if>">&nbsp;</td>

				<td class="x-tab-l<c:if test="${  mailBox eq 'S'}">c</c:if>">&nbsp;</td>
				<td width="65"
					class="x-tab-m<c:if test="${  mailBox eq 'S'}">c</c:if>"><a
					href="<%=request.getContextPath()%>/mx/mail/mailList?mailType=${mailType}&mailBox=S">发件箱</a>
				</td>
				<td class="x-tab-r<c:if test="${  mailBox eq 'S'}">c</c:if>">&nbsp;</td>

				<td class="x-tab-l<c:if test="${  mailBox eq 'T'}">c</c:if>">&nbsp;</td>
				<td width="65"
					class="x-tab-m<c:if test="${  mailBox == 'T'}">c</c:if>"><a
					href="<%=request.getContextPath()%>/mx/mail/mailList?mailType=${mailType}&mailBox=T">保存箱</a>
				</td>
				<td class="x-tab-r<c:if test="${  mailBox eq 'T'}">c</c:if>">&nbsp;</td>

				<td class="x-tab-l<c:if test="${  mailBox eq 'W'}">c</c:if>">&nbsp;</td>
				<td width="65"
					class="x-tab-m<c:if test="${  mailBox == 'W'}">c</c:if>"><a
					href="<%=request.getContextPath()%>/mx/mail/mailList?mailType=${mailType}&mailBox=W">废件箱</a>
				</td>
				<td class="x-tab-r<c:if test="${  mailBox eq 'W'}">c</c:if>">&nbsp;</td>
		</table>
		<div class="menu_line"></div>
		<table id="x_grid" style="display: none"></table>
		</td>
	</tr>
</table>

<table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="95%">
	<thead>
	<tr class="x-title">
		<td align="center" valign="top"><input type="checkbox"
			name="selectall" style="width: 12px; height: 12px"
			onclick="return checkAll();" value="on"></td>
		<td align="center" width="60" noWrap>序号</td>
		<td align="center" noWrap>发件人</td>
		<td align="center" noWrap>收件人</td>
		<td align="center" noWrap>主题</td>
		<td align="center" noWrap>日期</td>
		<!-- <td align="center" noWrap>大小</td> -->
		<td align="center" noWrap>状态</td>
		<td align="center" noWrap>功能键</td>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${rows}" var="mail">
		<tr onmouseover="this.className='x-row-over';"
			onmouseout="this.className='x-row-out';" class="x-content">
			<td align="center" noWrap><input type="checkbox" name="mailId"
				value="${mail.mailId}"></td>

			<td height="22" align="center" noWrap><a
				href="<%=request.getContextPath()%>/mx/mail/view?mailId=${mail.mailId}"
				target="_blank"><%=sortNo++%></a></td>

			<td align="left" noWrap>${mail.mailFrom}</td>

			<td align="left" noWrap>${mail.mailTo}</td>

			<td align="left" noWrap><a
				href="<%=request.getContextPath()%>/mx/mail/view?mailId=${mail.mailId}"
				target="_blank"> ${mail.subject}&nbsp; <c:if
				test="${mail.status == 0 and  mailBox eq 'R' }">
				<img src="<%=request.getContextPath()%>/images/new.gif"
					style="cursor: hand;" border="0"></a> </c:if></td>

			<td align="center" noWrap><fmt:formatDate
				value="${mail.createDate}" pattern="yyyy-MM-dd HH:mm" /></td>

			<!--  <td align="right" noWrap>
	  ${mail.mailSize}
	  </td> -->

			<td align="center" noWrap><c:choose>
				<c:when test="${mail.status == 0 }">
					<font color="#000000"><strong>未读</strong></font>
				</c:when>
				<c:when test="${mail.status == 1 }">
					<font color="#2222DD"><strong>已读</strong></font>
				</c:when>
				<c:when test="${mail.status == 2 }">
					<font color="#000000"><strong>草稿</strong></font>
				</c:when>
				<c:when test="${mail.status == 3 }">
					<font color="green"><strong>发送成功</strong></font>
				</c:when>
				<c:when test="${mail.status == 4}">
					<font color="#ff0000"><strong>发送失败</strong></font>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			</td>
			<td align="center" noWrap>&nbsp;<a
				href="<%=request.getContextPath()%>/mx/mail/compose?mailId=${mail.mailId}&iType=Re&x_actorIds=${mail.senderId}&mailType=${mailType}"
				title="回复邮件"><img
				src="<%=request.getContextPath()%>/images/e_back.gif"
				style="cursor: hand;" border="0"> 回复</a> &nbsp;<a
				href="<%=request.getContextPath()%>/mx/mail/compose?mailId=${mail.mailId}&iType=Fw&mailType=${mailType}"
				title="转发邮件"><img
				src="<%=request.getContextPath()%>/images/e_forward.gif"
				style="cursor: hand;" border="0"> 转发</a> &nbsp;<a title="删除邮件"><img
				src="<%=request.getContextPath()%>/images/delete.gif"
				style="cursor: hand;" border="0"
				onclick="remove('${mail.mailId}');"> 删除</a></td>
		</tr>
	</c:forEach>
	<c:if test="${ empty mailList }">
		<tr class="x-content">
			<td colspan="20" align="left">&nbsp;&nbsp;没有记录！</td>
		</tr>
	</c:if>
	</tbody>
	<thead>
	   	<tr class="x-paging">
			<td colspan="20" align="right"><glaf:paging form="iForm" /></td>
		</tr>
    </thead>
</table>
</form>
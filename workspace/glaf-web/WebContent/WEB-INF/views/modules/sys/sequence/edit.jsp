<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.dbid.*"%>
<%
String context = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础平台系统</title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=context%>/scripts/jquery.min.js'></script>
<script language="javascript" src='<%=context%>/scripts/jquery.form.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script type="text/javascript">

    function saveForm(){
        <c:forEach items="${rows}" var="a">
			var old = document.getElementById("${a.name}_old").value;
		    var now = document.getElementById("${a.name}").value;
			if(old*1 > now*1){
				alert("新序列值必须大于当前值:"+old);
                document.getElementById("${a.name}").focus();
				return;
			}
			if(old*1+10000 < now*1){
				alert("新序列值设置过大，建议输入值小于:"+(old*1 +10000));
                document.getElementById("${a.name}").focus();
				return;
			}
		</c:forEach>
		var form = document.getElementById("iForm");
        if(verifyAll(form)){
			 var params = jQuery("#iForm").formSerialize();
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/system/sequence/save',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
				   }
			 });
	    }
	}


</script>
</head>

<body>
<br><br>
<html:form name="iForm" id="iForm" action="" method="post">
<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}">
<input type="hidden" id="target" name="target" value="${target}">
<table width="65%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lt">&nbsp;</td>
        <td class="box-mt">&nbsp;</td>
        <td class="box-rt">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="box-mm"><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
	  <tr class="box">
        <td class="input-box2" width="20%">&nbsp;名称</td>
        <td class="input-box2" width="20%">&nbsp;标题</td>
        <td class="input-box2" width="60%">&nbsp;序列值</td>
      </tr>
	  <c:forEach items="${rows}" var="a">
      <tr>
        <td width="20%">${a.name}</td>
        <td width="20%">${a.title}</td>
		<td width="60%">
		  <input type="hidden" id="${a.name}_old" name="${a.name}_old" value="${a.value}">
		  <input type="text" id="${a.name}" name="${a.name}"  value="${a.value}" class="input" length="80" datatype="integer" nullable="yes" maxsize="50" chname="序列值">
		</td>
      </tr>
     </c:forEach>
 
      <tr>
        <td colspan="4" align="center" valign="bottom" height="30">
		    <br>&nbsp;
            <input name="btn_save3" type="button" value="保存" class="button" onclick="javascript:saveForm();">
			<!-- <input name="clearItems" type="button" value="全部清空" class="button" onclick="javascript:clearItems();"> -->
		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lb">&nbsp;</td>
        <td class="box-mb">&nbsp;</td>
        <td class="box-rb">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form> 
</body>
</html>

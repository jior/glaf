<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>参数设置</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<script type="text/javascript">
	     function saveForm(){
			 var params = jQuery("#iForm").formSerialize();
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/property/saveCfg',
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
	</script>
</head>
<body style="padding-left:20px;padding-right:20px">
 <br />
 <div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="参数设置">&nbsp;参数设置
</div>
<br>
<form id="iForm"  name="iForm" method="post" action="">

<input type="hidden" id="category" name="category" value="${category}">
<table align="center" class="x-table-border table table-striped table-bordered table-condensed" cellspacing="1"
	cellpadding="4" width="95%">
 
   <tr class="x-title">
        <td>代码</td>
		<td>标题</td>
		<td>说明</td>
		<!-- <td>初始值</td> -->
		<td>参数值</td>
	</tr>
     <c:forEach items="${rows}" var="p">
      <tr >
		<td><c:out value="${p.name}"/></td>
		<td><c:out value="${p.title}"/></td>
		<td><c:out value="${p.description}"/></td>
		<!-- <td><c:out value="${p.initValue}"/></td> -->
		<td valign="middle">
		  <c:choose>
		     <c:when test="${p.inputType == 'combobox'}">
			    <select id="<c:out value="${p.name}"/>" name="<c:out value="${p.name}"/>">
					 <c:out value="${p.selectedScript}" escapeXml="false"/>
			    </select>
                <script type="text/javascript">
                    document.getElementById("<c:out value="${p.name}"/>").value="<c:out value="${p.value}"/>";
                </script>
			 </c:when>
			 <c:otherwise>
			    <input type="text" name="<c:out value="${p.name}"/>" class="span3 x-text" 
		               value="<c:out value="${p.value}"/>" size="60" maxLength="500"/>
			 </c:otherwise>
		  </c:choose>
		</td>
	</tr>
	</c:forEach>
</table>
  
	<div align="center">
	    <input type="button"  name="save" value=" 保存 " class="btn btn-primary" onclick="javascript:saveForm('');"/>
	</div>
</form>
 <br/>
 <br/>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysDepartment bean=(SysDepartment)request.getAttribute("bean");
List  list = (List)request.getAttribute("parent");
int parentId=ParamUtil.getIntParameter(request, "parent", 0);
//Set histortDeparts = bean.getHistoryDeparts();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/site.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script></head>
<script language="javascript">
function checkForm(form){
  if(verifyAll(form)){
     if(form.parent.value=='${bean.id}'){
	   alert("当前部门不能选择为所属部门");
	 }else{
	   return true;
	 }
  }
   return false;
}
function setValue(obj){
  obj.value=obj[obj.selectedIndex].value;
}
  
function saveData(){
	if(checkForm(document.getElementById("iForm"))){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=context%>/sys/department.do?method=save',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						 alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
					   if (window.opener) {
						  //window.opener.location.reload();
						  window.opener.reloadGrid();
					   } else if (window.parent) {
						  //window.parent.location.reload();
						  window.parent.reloadGrid();
					   }
					   window.close();
				   }
			 });
	}
}
 
</script>
</head>
<body>
<div class="nav-title"><span class="Title">部门管理</span>&gt;&gt;修改部门</div>
<form  id="iForm" name="iForm" method="post"> 
<input type="hidden" name="id" value="${bean.id}">
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td class="input-box">上级部门</td>
        <td><select name="parent" onChange="javascript:setValue(this);" class="input">
          <%
			if(list!=null){
			  Iterator iter=list.iterator();   
			  while(iter.hasNext()){
				SysTree bean2=(SysTree)iter.next();
			%>
				<option value="<%=bean2.getId()%>">
			<%
			for(int i=1;i<bean2.getDeep();i++){
			  out.print("&nbsp;&nbsp;");
			}
			out.print(bean2.getName());
			%>
			   </option>
			<%    
			  }
			}
			%>
        </select>
		<script language="javascript">	
		<c:choose>
		  <c:when test="${!empty bean}">
		      document.all.parent.value="${bean.node.parentId}";	
		  </c:when>
		  <c:otherwise>
		      document.all.parent.value="<%=parentId%>";	
		  </c:otherwise>
		</c:choose>
	    </script>		
		</td>
      </tr>
      <tr>
        <td class="input-box">部门名称*</td>
        <td><input name="name" type="text" class="input" id="name" value="${bean.name}" size="37" datatype="string" nullable="no" maxsize="20" chname="部门名称"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">描　　述</td>
        <td><textarea name="desc" cols="35" rows="8" class="input" id="desc" datatype="string" nullable="yes" maxsize="1000" chname="描述">${bean.desc}</textarea></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">代　　码*</td>
        <td><input name="code" type="text" class="input" id="code" value="${bean.code}" size="37"  datatype="string" nullable="no" maxsize="10" chname="代码"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">编　　码*</td>
        <td><input name="no" type="text" class="input" id="no" value="${bean.no}" size="37"  datatype="string" nullable="no" maxsize="10" chname="编码"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">部门区分*</td>
        <td><input name="code2" type="text" size="37" class="input" value="${bean.code2}" datatype="string" nullable="no" maxsize="10" chname="部门区分"></td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">部门层级</td>
        <td>
		    <select id="level" name="level">
			  <option value="0">----请选择----</option>
			  <c:forEach items="${dictories}" var="a">
				<option value="${a.ext11}">${a.name} [${a.code}]</option>
			  </c:forEach>
		   </select>
		   <script type="text/javascript">
		        document.getElementById("level").value="${bean.level}";
		   </script>
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">是否有效*</td>
        <td><input type="radio" name="status" value="0" <c:if test="${bean.status == 0}">checked</c:if>>
          是
          <input type="radio" name="status" value="1" <c:if test="${bean.status == 1}">checked</c:if>>
          否
		</td>
      </tr>
	  <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save" type="button" value="保存" class="button" onclick="javascript:saveData();">
		</td>
      </tr>
</table>
<form> 
</body>
</html>
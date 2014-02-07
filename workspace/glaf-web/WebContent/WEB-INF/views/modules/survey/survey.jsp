<%--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	int index = 0;
%>
<!DOCTYPE html>
<html>
<head>
<title>调查</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/views/inc/mobile_include.jsp" %>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function submitVote(){
        <c:choose>
		    <c:when test="${!empty survey.relations}">
              <c:forEach items="${survey.relations}" var="relation">
		         <c:choose>
                    <c:when test="${relation.multiFlag == 1}">
					var arr_${relation.id} = document.getElementsByName("result_${relation.id}_1");
					var result_${relation.id}="";
					for(var i = 0; i < arr_${relation.id}.length; i++){
						if(arr_${relation.id}.item(i).checked){
							result_${relation.id} = result_${relation.id}+arr_${relation.id}.item(i).value+",";
						}
					}
					jQuery("#result_${relation.id}").val(result_${relation.id});
					</c:when>
					<c:otherwise>
					//var result_${relation.id} = jQuery("#result_${relation.id}").val();
					</c:otherwise>
				 </c:choose>
		     </c:forEach>
	    </c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${survey.multiFlag == 1}">
				var arr = document.getElementsByName("result_1");
				var result="";
				for(var i = 0; i < arr.length; i++){
					if(arr.item(i).checked){
						result = result+arr.item(i).value+",";
					}
				}
				jQuery("#result").val(result);
				</c:when>
				<c:otherwise>
				//var result = jQuery("#result").val();
				</c:otherwise>
			</c:choose>
            </c:otherwise>
        </c:choose>
		var params = jQuery("#iForm").formSerialize();
	    //alert(params);
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/website/public/survey/post/${survey.id}',
				   dataType:  'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						 alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
				   }
			 });
	}
</script>
</head>
<body>
    <div data-role="page">
        <div data-role="header" data-theme="b">
            <h1>${survey.title}</h1>
        </div>
        <div data-role="content">
            <div style="margin-left: auto; margin-right: auto; width: 90%;">
       
                <form id="iForm" name="iForm" method="post"
				      action="<%=request.getContextPath()%>/website/wx/vote/post/${survey.id}" >
				
				<c:choose>
				 <c:when test="${!empty survey.relations}">
                   <c:forEach items="${survey.relations}" var="relation">
				     <input type="hidden" id="result_${relation.id}" name="result_${relation.id}">
				     <c:if test="${!empty relation.icon && relation.showIconFlag == 1}">
						 <p align="center">
						   <img src="<%=request.getContextPath()%>/${relation.icon}"/>
						 </p>
						 </c:if>
						 <c:if test="${!empty relation.desc}">
						 <p>
						   ${relation.desc}
						 </p>
						 </c:if>
                         <fieldset data-role="controlgroup">  
						 <c:choose>
						 <c:when test="${relation.multiFlag == 1}">
						 <c:forEach items="${relation.items}" var="item">
							<input type="checkbox" id="result_${relation.id}_<%=index%>" name="result_${relation.id}_1" value="${item.value}"/>
							<label for="result_${relation.id}_<%=index++%>">${item.name}</label>
						 </c:forEach>
						 </c:when>
						 <c:otherwise>
						   <c:forEach items="${relation.items}" var="item">
							<input type="radio" id="result_${relation.id}_<%=index%>" name="result_${relation.id}_1"
								   onclick="jQuery('#result_${relation.id}').val('${item.value}');"/>
							<label for="result_${relation.id}_<%=index++%>">${item.name}</label>
						   </c:forEach>
						 </c:otherwise>
						 </c:choose>
						</fieldset>
 			       </c:forEach>
				 </c:when>
                 <c:otherwise>
				 <c:if test="${!empty survey.icon && survey.showIconFlag == 1}">
				 <p align="center">
				   <img src="<%=request.getContextPath()%>/${survey.icon}"/>
				 </p>
				 </c:if>
				 <c:if test="${!empty survey.desc}">
				 <p>
				   ${survey.desc}
				 </p>
				 </c:if>
				 <input type="hidden" id="result" name="result">
				 <fieldset data-role="controlgroup">  
				 <c:choose>
				 <c:when test="${survey.multiFlag == 1}">
                 <c:forEach items="${survey.items}" var="item">
					<input type="checkbox" id="result_<%=index%>" name="result_1" value="${item.value}"/>
					<label for="result_<%=index++%>">${item.name}</label>
                 </c:forEach>
				 </c:when>
				 <c:otherwise>
				   <c:forEach items="${survey.items}" var="item">
				    <input type="radio" id="result_<%=index%>" name="result_1"
						   onclick="jQuery('#result').val('${item.value}');"/>
					<label for="result_<%=index++%>">${item.name}</label>
				   </c:forEach>
				 </c:otherwise>
                 </c:choose>
				</fieldset>
				</c:otherwise>
                </c:choose>
                <p>
                    <input type="button" value="确认" data-role="button" data-icon="star" data-theme="b" 
					       onclick="javascript:submitVote();"/>
                </p>
                </form>

            </div>
        </div>
</body>
</html>

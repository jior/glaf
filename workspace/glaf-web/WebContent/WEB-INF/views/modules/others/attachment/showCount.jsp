<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.others.*"%>
<%@ page import="com.glaf.base.modules.others.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%
    String referId = ParamUtil.getParameter(request, "referId");
	String referIds = StringUtils.replace(referId, ",", "");
	int referType = ParamUtil.getIntParameter(request, "referType", 0);
	String count =(String)request.getAttribute("count");
	int number = ParamUtil.getIntParameter(request,"number",0);
%>
(共<span id="referId-<%= referType + referIds+number %>"><%=count %></span>个)
<script type="text/javascript">
 
function getCount<%= referType + referIds+number %>() {
 
	jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/others/attachment.do?method=showCount&referId=<%=referId%>&referType=<%=referId%>',
			dataType:  'json',
				error: function(data){
					alert('服务器处理错误！');
				},
				success: function(data){
					 
				 }
		});
	
}
 
</script>
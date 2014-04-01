<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="refresh" content="${timeoutSeconds}"/>
<title>用户在线</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript">
	function remainOnline(){
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/public/online/doRemain',
				   dataType:  'json',
				   error: function(data){
					    
				   },
				   success: function(data){
					   
				   }
			 });
	}
</script>
</head>
<body onload="javascript:remainOnline();">
</body>
</html>
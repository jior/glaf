<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.glaf.com/jsp/jstl" prefix="glaf"%>
<html>
 <head>
  <title>权限页面</title>
 </head>

 <body>

 <glaf:permission key="R007,R010" operator="OR">
     <!--采购联络员或采购担当都可以提交-->
     <input type="button" value="提交" onclick="javascript:submitForm();">
 </glaf:permission>
  
 </body>
</html>

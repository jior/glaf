<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="/sys/sysTld/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld" %>
<%@ taglib prefix="logic" uri="/sys/sysTld/struts-logic.tld" %>
<script type="text/javascript" language="Javascript"> 
 
<logic:present name="errorDto">
	var errorObjIds = [];
	<logic:iterate id="errorDtoId" name="errorDto"> 
	       errorObjIds.push("<bean:write name='errorDtoId'/>") 
	</logic:iterate> 
</logic:present>

    var allTypeMessage = "<html:errors/>";
    <logic:messagesPresent message="true">
	
		<html:messages id="message" message="true"> 
		  allTypeMessage = "<bean:write name='message'/>" ;
		  </script>
		  <input type="hidden" value="<bean:write name='message'/>" name="messages">
		  <script type="text/javascript" language="Javascript"> 
		</html:messages>
		
	</logic:messagesPresent>
	
</script>

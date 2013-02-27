<%@ page contentType="image/png;charset=UTF-8"%><%@ page import="java.io.*"%><%@ page import="org.springframework.util.*"%><%
        InputStream inputStream = (InputStream)request.getAttribute("processimage");
		if(inputStream != null){
			OutputStream outputStream = response.getOutputStream();
			FileCopyUtils.copy(inputStream, outputStream);
			outputStream.flush();
			outputStream.close();
		}
%>
<%@ page contentType="text/html; charset=gb2312" language="java" import="java.io.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<% 
   //声明要下载的文件loadFile
   File loadFile=new File(request.getParameter("filepath"));
   //通知客户要下载文件类型
   //response.setContentType("application/msword");
   //定义下载文件的长度
   long fileLength=loadFile.length();
   
   //if(fileLength > 100000000){
   if(fileLength > 100){
	   //使用对话框保存文件
	   response.setHeader("Cache-Control","no-cache");
	   response.setDateHeader("Expires", 0);
	    
	   out.write("<script type='text/javascript' language='Javascript'>window.history.go(-1);alert('文件大小超过1G，请与管理员联系！'); </script>");
	   out.flush();
	   //out.clear();
	   
	   //out = pageContext.pushBody();
   }else{
	   //使用对话框保存文件
	   response.setHeader("Content-disposition","attachment;filename="+request.getParameter("file"));
   
	   //把长整形的文件长度转换为字符串
	   String length=String.valueOf(fileLength);
	   response.setHeader("content_Length",length);
	    //声明一个输入文件流clientFile,接收并读取己下载的文件
	   FileInputStream clientFile=new FileInputStream(loadFile);
	   //声明一个输出流serverFile获取要下载的文件
	   OutputStream serverFile=response.getOutputStream();
	
	   //把要下载的文件的内容读入输入流clientFile
	   int n=0;
	   byte b[]=new byte[100];
	   while((n=clientFile.read(b))!=-1)
	   {
	       serverFile.write(b,0,n);
	   }
	   serverFile.close();
	   clientFile.close();
	   
	   out.clear();
	   out = pageContext.pushBody();
   }
   
%>
<script type="javascript">

  window.close(); 
</script>
</body>
</html>


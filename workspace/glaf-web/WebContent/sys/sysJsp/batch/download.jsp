<%@ page contentType="text/html; charset=gb2312" language="java" import="java.io.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
</head>

<body>
<% 
   //����Ҫ���ص��ļ�loadFile
   File loadFile=new File(request.getParameter("filepath"));
   //֪ͨ�ͻ�Ҫ�����ļ�����
   //response.setContentType("application/msword");
   //���������ļ��ĳ���
   long fileLength=loadFile.length();
   
   //if(fileLength > 100000000){
   if(fileLength > 100){
	   //ʹ�öԻ��򱣴��ļ�
	   response.setHeader("Cache-Control","no-cache");
	   response.setDateHeader("Expires", 0);
	    
	   out.write("<script type='text/javascript' language='Javascript'>window.history.go(-1);alert('�ļ���С����1G���������Ա��ϵ��'); </script>");
	   out.flush();
	   //out.clear();
	   
	   //out = pageContext.pushBody();
   }else{
	   //ʹ�öԻ��򱣴��ļ�
	   response.setHeader("Content-disposition","attachment;filename="+request.getParameter("file"));
   
	   //�ѳ����ε��ļ�����ת��Ϊ�ַ���
	   String length=String.valueOf(fileLength);
	   response.setHeader("content_Length",length);
	    //����һ�������ļ���clientFile,���ղ���ȡ�����ص��ļ�
	   FileInputStream clientFile=new FileInputStream(loadFile);
	   //����һ�������serverFile��ȡҪ���ص��ļ�
	   OutputStream serverFile=response.getOutputStream();
	
	   //��Ҫ���ص��ļ������ݶ���������clientFile
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


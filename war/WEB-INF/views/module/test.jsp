<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ page import="org.springframework.web.context.*"%>
<%@ page import="org.springframework.web.context.support.*"%>
<%@ page import="com.glaf.base.utils.StringUtil"%>
<%@ page import="com.glaf.base.modules.workspace.model.*"%>
<%@ page import="com.glaf.base.modules.workspace.service.*"%>
<%@ page import="com.glaf.base.modules.inf.*"%>
<%
DequeueInterface dequeue = new DequeueInterface();
		String xmlString = "";
		InputStream ins = null;
		int bytesRead = 0;
		try {
			ins = new FileInputStream(new File("d:/test.xml"));
			byte[] b = new byte[1024];
			if (ins == null) {
				System.out.println("源模板文件不存在");
			}else{
				while (true) {
					bytesRead = ins.read(b, 0, 1024); // return final read bytes counts
					if (bytesRead == -1) {// end of InputStream
						System.out.println("读取模板文件结束");
						break;
					}
					
					xmlString += new String(b, 0, bytesRead);
				}
			}
			//System.out.println(xmlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dequeue.xmlToDataBase("block3_test_queue", xmlString, "", "123");
%>
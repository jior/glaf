<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jpage.jbpm.model.*" %>
<%@ page import="org.jpage.jbpm.cmd.*" %>
<%@ page import="org.jpage.jbpm.service.*" %>
<%@ page import="org.jpage.jbpm.datafield.*" %>
<%@ page import="org.jpage.util.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%
       StartProcessCmd cmd = new StartProcessCmd();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String rowId = request.getParameter("rowId");
		if(rowId != null){
			// ��˱�ļ�¼ID
			paramMap.put("rowId", rowId);
			// �������ƣ�Ӣ�ĵ����ƣ�
			paramMap.put("processName", "Test");
			// ���������Ʋ������Ϸ���JSON��ʽ�ַ���
			String datafields = "{tableName:'TEST'}";
			paramMap.put("json", datafields);
			String processInstanceId = cmd.startProcess("joy", paramMap);
			// �������ʵ����Ų�Ϊ�գ���ô�Ѿ��ɹ���������ʵ���������׳��쳣
			if (processInstanceId != null) {
				 out.println("<br>processInstanceId="+processInstanceId);
				 out.println("<br>OK!");
			}
		}
%>
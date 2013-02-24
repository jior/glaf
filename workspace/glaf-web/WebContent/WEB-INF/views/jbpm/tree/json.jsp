<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.jbpm.graph.def.*"%>
<%@ page import="org.jbpm.taskmgmt.def.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%
                 com.glaf.core.util.RequestUtils.setRequestParameterToAttribute(request);
				 List  rows = (List) request.getAttribute("rows");
				 List  tasks = (List) request.getAttribute("tasks");
                 String location = request.getParameter("location");
				 String process_name = request.getParameter("process_name");
				 if (StringUtils.isEmpty(process_name)) {
					  process_name = request.getParameter("node");
				 }
				 StringBuffer buffer = new StringBuffer();
	             buffer.append("[");

                 if(tasks != null && tasks.size()>0){
					 ProcessDefinition pd = (ProcessDefinition) request.getAttribute("processDefinition");
                       for(int j=0;j<tasks.size();j++){
						    Task x = (Task)tasks.get(j);
							String text = x.getDescription() != null ? x.getDescription() : x.getName();
						    buffer.append("{\"text\":\"").append(text).append("\"");							 
							buffer.append(",\"id\":\"x_task:").append(x.getName()).append("\"");
							buffer.append(",\"leaf\":true,\"cls\":\"file\",\"allowDrag\":false");
 							 
							buffer.append(",\"hrefTarget\":\"mainframe\"");
							buffer.append(",\"href\":\"").append(request.getContextPath());
							if(StringUtils.isEmpty(location)){
								  location = "/mx/jbpm/tree?q=1";
							} else {
								if(location.indexOf("?") == -1){
									location = location+"?q=1";
								}
							}
							buffer.append(location).append("&task_name=").append(x.getName())
								      .append("&taskName=").append(x.getName())
							          .append("&process_name=").append(pd.getName())
								      .append("&processName=").append(pd.getName())
								      .append("&processDefinitionId=").append(pd.getId());

						 
							buffer.append("&nodeId=").append(x.getId());
							buffer.append("&nodeCode=").append(x.getName());
							buffer.append("\"");
							buffer.append("}");
							buffer.append(",");
						 }
				 } else  if(rows != null && rows.size()>0){
                       for(int i=0;i<rows.size();i++){
						    ProcessDefinition x = (ProcessDefinition)rows.get(i);
							String text = x.getDescription() != null ? x.getDescription() : x.getName();
							if(!StringUtils.equals(process_name, "source")){
                               text = text+" V"+x.getVersion()+".0";
							}
						    buffer.append("{\"text\":\"").append(text).append("\"");
							if(!StringUtils.equals(process_name, "source")){
								 buffer.append(",\"id\":\"x_pdid:").append(x.getId()).append("\"");
								 buffer.append(",\"leaf\":false,\"cls\":\"folder\",\"allowDrag\":false");
							} else {
							     buffer.append(",\"id\":\"x_pdname:").append(x.getName()).append("\"");
								 buffer.append(",\"leaf\":false,\"cls\":\"folder\",\"allowDrag\":false");
							}
							 
							buffer.append(",\"hrefTarget\":\"mainframe\"");
							buffer.append(",\"href\":\"").append(request.getContextPath());
							if(StringUtils.isEmpty(location)){
								  location = "/mx/jbpm/tree?q=1";
							} else {
								if(location.indexOf("?") == -1){
									location = location+"?q=1";
								}
							}
							buffer.append(location).append("&process_name=").append(x.getName())
								      .append("&processName=").append(x.getName());
							if(!StringUtils.equals(process_name, "source")){
								  buffer.append("&processDefinitionId=").append(x.getId());
							}
						 
							buffer.append("&nodeId=").append(x.getId());
							buffer.append("&nodeCode=").append(x.getName());
							buffer.append("\"");
							buffer.append("}");
							buffer.append(",");
						 }
				 }

				 buffer.delete(buffer.length()-1, buffer.length());
	             buffer.append("\n]");
		         out.println(buffer.toString());
                 out.flush();
                 //System.out.println(buffer.toString());
%>
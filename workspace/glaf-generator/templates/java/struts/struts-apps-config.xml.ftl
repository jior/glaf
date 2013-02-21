<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE struts-config PUBLIC
	  "-//Apache Software Foundation//DTD Struts Configuration 1.2//EN"
	  "http://jakarta.apache.org/struts/dtds/struts-config_1_2.dtd">
      
<struts-config>
	 
	<!--Action Mapping Definitions-->
	<action-mappings>
	  
	    <!--请将此节点内容复制到WebContent\WEB-INF\conf\struts\struts-apps-config.xml中-->
		<action path="/apps/${modelName}"
			parameter="method"
			scope="request"
			name="GenericForm"
			type="org.springframework.web.struts.DelegatingActionProxy">
			<forward name="show_edit" path="/WEB-INF/views/apps/${modelName}/edit.jsp"/>
			<forward name="show_modify" path="/WEB-INF/views/apps/${modelName}/edit.jsp"/>
			<forward name="show_list" path="/WEB-INF/views/apps/${modelName}/list.jsp"/>
			<forward name="show_view" path="/WEB-INF/views/apps/${modelName}/view.jsp"/>
		</action>
    
	</action-mappings>
</struts-config>
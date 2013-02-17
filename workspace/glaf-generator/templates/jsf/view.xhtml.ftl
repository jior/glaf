<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:c="http://java.sun.com/jstl/core"
	xmlns:fmt="http://java.sun.com/jstl/fmt"
	xmlns:p="http://primefaces.org/ui"
	template="/apps/templates/template.xhtml">

	<ui:define name="content">

		<h:form id="iForm">

			<p:growl id="growl2" showDetail="true" life="3000" />

			<p:panel header="#JSF{msg.res_view}${classDefinition.title}" toggleable="false"
				closable="false" toggleSpeed="500" style="text-align:left;">
				<f:facet name="header">
					<h:panelGroup>
						<p:commandLink update="growl2"
							onclick="location.href='#JSF{request.contextPath}/apps/${modelName}/edit.jsf?${modelName}Id=#JSF{${modelName}ViewBean.${modelName}.${idField.name}}'">
							<p:graphicImage value="/images/edit.gif" style="border:0;" />
							<h:outputText value="#JSF{msg.res_update}" />
						</p:commandLink>

						<h:outputText value="  " />
						<p:commandLink actionListener="#JSF{${modelName}ViewBean.removeAction}"
							rendered="#JSF{!empty ${modelName}ViewBean.${modelName}.${idField.name}}"
							onclick="if(!confirm('#JSF{msg.res_delete_confirm}')){return;};"
							update="growl2">
							<p:graphicImage value="/images/delete.png" style="border:0;" />
							<h:outputText value="#JSF{msg.res_delete}" />
						</p:commandLink>

						<h:outputText value="  " />
						<p:commandLink onclick="history.back();">
							<p:graphicImage value="/images/back.gif" style="border:0;" />
							<h:outputText value="#JSF{msg.res_back}" />
						</p:commandLink>

						<h:inputHidden id="${modelName}Id" value="#JSF{${modelName}ViewBean.${modelName}.${idField.name}}"
							rendered="#JSF{!empty ${modelName}ViewBean.${modelName}.${idField.name}}" />

					</h:panelGroup>

				</f:facet>

				<h:panelGrid id="view${entityName}" columns="2" cellpadding="5"
					style="text-align:left; ">

<#if pojo_fields?exists>
         <#list  pojo_fields as field>	
		 <#if field.editable?exists && field.editable>
		            <h:outputLabel for="${field.name}" value="${field.title}: " />
         <#if field.type?exists && field.type== 'Date'>
                    <h:outputText id="${field.name}"
						value="#JSF{${modelName}ViewBean.${modelName}.${field.name}}">
						<f:convertDateTime pattern="yyyy-MM-dd HH:mm" />
					</h:outputText>
		 <#else>
		        <#if field.dataCode?exists>
		            <h:outputText id="${field.name}"
						value="#JSF{${modelName}ViewBean.${modelName}.${field.name}Name}">
					</h:outputText>
			    <#else>
		            <h:outputText id="${field.name}"
						value="#JSF{${modelName}ViewBean.${modelName}.${field.name}}">
					</h:outputText>
				 </#if>
			 </#if>
         </#if>
    </#list>
</#if>
 

				</h:panelGrid>

			</p:panel>

		</h:form>

	</ui:define>

</ui:composition>

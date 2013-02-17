<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:c="http://java.sun.com/jstl/core"
	xmlns:fmt="http://java.sun.com/jstl/fmt"
	xmlns:p="http://primefaces.org/ui"
	template="/apps/templates/template.xhtml">

	<ui:define name="content">

		<h:form>
			<p:growl id="growl2" showDetail="true" life="3000" />

			<p:panel header="#JSF{msg.res_edit}${classDefinition.title}" toggleable="false"
				closable="false" toggleSpeed="500" style="text-align:left;">
				<f:facet name="header">
					<h:panelGroup>
						<p:commandLink update="growl2"
							actionListener="#JSF{${modelName}ViewBean.saveAction}">
							<p:graphicImage value="/images/add.png" style="border:0;" />
							<h:outputText value="#JSF{msg.res_save}" />
						</p:commandLink>

						<h:outputText value="  " />
						<p:commandLink update="growl2"
							actionListener="#JSF{${modelName}ViewBean.removeAction}"
							rendered="#JSF{!empty ${modelName}ViewBean.${modelName}.${idField.name}}"
							onclick="if(!confirm('#JSF{msg.res_delete_confirm}')){return;};">
							<p:graphicImage value="/images/delete.png" style="border:0;" />
							<h:outputText value="#JSF{msg.res_delete}" />
						</p:commandLink>

						<h:outputText value="  " />
						<p:commandLink onclick="history.back();">
							<p:graphicImage value="/images/back.gif" style="border:0;" />
							<h:outputText value="#JSF{msg.res_back}" />
						</p:commandLink>

						<h:inputHidden id="${modelName}Id" value="#JSF{${modelName}ViewBean.${modelName}.${idField.name}}" />

					</h:panelGroup>

				</f:facet>

				<p:messages id="messages" />

				<h:panelGrid id="edit${entityName}" columns="2" cellpadding="5"
					style="text-align:left; ">

			<#if pojo_fields?exists>
					 <#list  pojo_fields as field>	

					 <#if field.editable?exists && field.editable>

					<h:outputLabel for="${field.name}" value="${field.title}: " />

					 <#if field.type?exists && field.type== 'Date'>

					<p:calendar id="${field.name}" value="#JSF{${modelName}ViewBean.${modelName}.${field.name}}" 
					     pattern="yyyy-MM-dd" mode="popup" showOn="button" popupIconOnly="true"
						 <#if !field.nullable  >required="true"</#if>/>

					 <#elseif field.type?exists && field.type== 'Integer'>

					<p:inputMask id="${field.name}" value="#JSF{${modelName}ViewBean.${modelName}.${field.name}}"
					     <#if field.mask?exists>mask="${field.mask}"</#if>/>

					 <#elseif field.type?exists && field.type== 'Long'>

					<p:inputMask id="${field.name}" value="#JSF{${modelName}ViewBean.${modelName}.${field.name}}" 
					     <#if field.mask?exists>mask="${field.mask}"</#if>/>

					 <#elseif field.type?exists && field.type== 'Double'>

					<p:inputMask id="${field.name}" value="#JSF{${modelName}ViewBean.${modelName}.${field.name}}" 
					     <#if field.mask?exists>mask="${field.mask}"</#if>/>

					 <#else>

                    <#if field.mask?exists>

					<p:inputMask id="${field.name}" value="#JSF{${modelName}ViewBean.${modelName}.${field.name}}" 
					     <#if field.mask?exists>mask="${field.mask}"</#if>
						 <#if !field.nullable  >required="true"</#if>/>

					<#else>

                   <#if field.dataCode?exists>

					<h:selectOneMenu id="${field.name}" value="#JSF{${modelName}ViewBean.${modelName}.${field.name}}">
					  <f:selectItems value="#JSF{${modelName}ViewBean.${field.name}Items}"></f:selectItems>                       
				    </h:selectOneMenu> 
                   
				   <#else>
					<h:inputText id="${field.name}"    
						 value="#JSF{${modelName}ViewBean.${modelName}.${field.name}}" 
						 label="${field.title}"
						 <#if !field.nullable  >required="true"</#if>>
						<#if field.minLength &gt; 0 && field.maxLength &gt; 0  >
						<f:validateLength minimum="${field.minLength}" maximum="${field.maxLength}" />  
						</#if>
						<#if !field.nullable  >
						<f:validateLength minimum="1" />  
						</#if>
						<p:ajax event="blur" update="messages" />  
					</h:inputText> 
					</#if>
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

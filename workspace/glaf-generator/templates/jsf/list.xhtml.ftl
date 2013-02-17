<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:c="http://java.sun.com/jstl/core"
	xmlns:fmt="http://java.sun.com/jstl/fmt"
	xmlns:p="http://primefaces.org/ui"
	template="/apps/templates/template.xhtml">

	<ui:define name="head">
		<style type="text/css">
		.locked_1 {
			background-color: #FFEF00 !important;
			background-image: none !important;
			color: #000000 !important;
		}
        </style>
	</ui:define>

	<ui:define name="content">

	 <div class="x_content_title">
	 <h:graphicImage value="/images/window.png"
		title="${classDefinition.title}#JSF{msg.res_list}" 
		style="border:0"/>
		 ${classDefinition.title}#JSF{msg.res_list}
	  </div>
	  <br/>

		<h:form id="iForm">

			<p:panel header="toolbar" toggleable="false" closable="false"
				toggleSpeed="500" style="text-align:left;">
				 <f:facet name="header">
			   		<h:panelGroup>

							<p:commandLink style="margin-left:10px;"
								onclick="location.href='#JSF{request.contextPath}/apps/${modelName}/edit.jsf'">
								<p:graphicImage value="/images/add.png" style="border:0;" />
								<h:outputText value="#JSF{msg.res_create}" />
							</p:commandLink>

							<p:commandLink update="${modelName}s"
								actionListener="#JSF{${modelName}Bean.removeManyAction}"
								onclick="if(!confirm('#JSF{msg.res_delete_confirm}')){return;};"
								style="margin-left:10px;">
								<p:graphicImage value="/images/delete.png"
									style="border:0;" />
								<h:outputText value="#JSF{msg.res_delete}" />
							</p:commandLink>

							<p:commandLink oncomplete="${modelName}SearchDialog.show()"
								style="margin-left:10px;">
								<p:graphicImage value="/images/search.png"
									style="border:0;" />
								<h:outputText value="#JSF{msg.res_adv_search}" />
							</p:commandLink>

			    </h:panelGroup>
               </f:facet>

               <h:panelGrid columns="4">


			   </h:panelGrid>

				<h:panelGrid columns="4">
					<p:commandButton value="#JSF{msg.res_search}"
							image="ui-icon icon-search" update="${modelName}s"
							action="#JSF{${modelName}Bean.search}" />
				</h:panelGrid>
			</p:panel>	

			<p:growl id="growl8" showDetail="true" life="3000" />

			<p:dataTable id="${modelName}s" var="${modelName}"  
			     value="#JSF{${modelName}Bean.lazy${entityName}s}"
				paginator="true" rows="10" lazy="true" dynamic="true"
				paginatorTemplate="{CurrentPageResultReport}  {FirstPageResultLink} {PreviousPageResultLink} {PageResultLinks} {NextPageResultLink} {LastPageResultLink} {RowsPerPageResultDropdown}"
				style="text-align:left; " rowsPerPageResultTemplate="10,15,20,50,100">

<#if pojo_fields?exists>
         <#list  pojo_fields as field>	
		 <#if field.displayType == 4>
			 <#if field.type?exists && field.type== 'Date'>

				<p:column 
					sortBy="#JSF{${modelName}.${field.name}}" style="text-align:center; "
					>
					<f:facet name="header">
						<h:outputText value="${field.title}" />
					</f:facet>
					<h:outputText value="#JSF{${modelName}.${field.name}}">
						<f:convertDateTime pattern="yyyy-MM-dd HH:mm:ss" />
					</h:outputText>
				</p:column>

			 <#elseif field.type?exists && field.type== 'Integer'>

                  <p:column sortBy="#JSF{${modelName}.${field.name}}" style="text-align:center; ">
					<f:facet name="header">
						<h:outputText value="${field.title}" />
					</f:facet>
					<h:outputText value="#JSF{${modelName}.${field.name}}">
					</h:outputText>
				</p:column>

			 <#elseif field.type?exists && field.type== 'Long'>

                  <p:column sortBy="#JSF{${modelName}.${field.name}}" style="text-align:center; ">
					<f:facet name="header">
						<h:outputText value="${field.title}" />
					</f:facet>
					<h:outputText value="#JSF{${modelName}.${field.name}}">
					</h:outputText>
				</p:column>

			 <#elseif field.type?exists && field.type== 'Double'>

                  <p:column sortBy="#JSF{${modelName}.${field.name}}" style="text-align:center; ">
					<f:facet name="header">
						<h:outputText value="${field.title}" />
					</f:facet>
					<h:outputText value="#JSF{${modelName}.${field.name}}">
					</h:outputText>
				</p:column>

			 <#else>

                  <p:column sortBy="#JSF{${modelName}.${field.name}}" style="text-align:center; ">
					<f:facet name="header">
						<h:outputText value="${field.title}" />
					</f:facet>
					<#if field.dataCode?exists>
		            <h:outputText value="#JSF{${modelName}.${field.name}Name}"/>
			        <#else>
		            <h:outputText value="#JSF{${modelName}.${field.name}}"/>
				    </#if>
				</p:column>

             </#if>
		 </#if>
    </#list>
</#if>

 

				<p:column style="text-align:center; ">
					<f:facet name="header">
						<h:outputText value="#JSF{msg.res_functionKey}" />
					</f:facet>
					<h:outputLink						
						value="#JSF{request.contextPath}/apps/${modelName}/view.jsf?${modelName}Id=#JSF{${modelName}.${idField.name}}">
						<p:graphicImage value="/images/view.gif" style="border:0;" />
						<h:outputText value="#JSF{msg.res_view}" />
					</h:outputLink>

					<h:outputLink
						value="#JSF{request.contextPath}/apps/${modelName}/edit.jsf?${modelName}Id=#JSF{${modelName}.${idField.name}}"
						style="margin-left:10px;">
						<p:graphicImage value="/images/edit.gif" style="border:0;" />
						<h:outputText value="#JSF{msg.res_edit}" />
					</h:outputLink>

				</p:column>

			</p:dataTable>

		</h:form>

	</ui:define>

</ui:composition>

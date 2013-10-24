<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC 
	"-//Hibernate/Hibernate Mapping DTD 3.0//EN"
	"http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
	<class name="${packageName}.domain.${entityName}" table="${tableName}"
		dynamic-update="true" dynamic-insert="true">
<#if idField?exists >
    <#if idField.type?exists >
	   <#if idField.type=='Integer' >
		<id column="${idField.columnName}" name="${idField.name}" type="int" unsaved-value="0">
			<generator class="assigned" />
		</id>
		<#elseif idField.type== 'Long' >
        <id column="${idField.columnName}" name="${idField.name}" type="long" unsaved-value="0">
			<generator class="assigned" />
		</id>
		<#else>
		<id column="${idField.columnName}" name="${idField.name}" type="string" unsaved-value="null">
			<generator class="assigned" />
		</id>
	   </#if>
    </#if>
</#if>

<#if pojo_fields?exists>
<#list  pojo_fields as field>	
    <#if field.columnName?exists && field.type?exists >
	  <#if ( field.type== 'Integer')>
        <property column="${field.columnName}" name="${field.name}" type="integer" />
	  <#elseif ( field.type== 'Long' )>
		<property column="${field.columnName}" name="${field.name}" type="long" />
	  <#elseif ( field.type== 'Double' )>
		<property column="${field.columnName}" name="${field.name}" type="double" />	 
	  <#elseif ( field.type== 'Boolean' )>
		<property column="${field.columnName}" name="${field.name}" type="boolean" />
	  <#elseif ( field.type== 'Date' )>
		<property column="${field.columnName}" name="${field.name}" type="timestamp" />
	  <#else>
        <property column="${field.columnName}" name="${field.name}" type="string" length="<#if field.length gt 0>${field.length?string("####")}<#else>255</#if>" />  
	</#if>
	</#if>
</#list>
</#if>
	</class>
</hibernate-mapping>
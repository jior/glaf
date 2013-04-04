 
create table ${tableName} (
<#if  idField.type== 'Integer' >
        ${idField.columnName} INTEGER,
<#elseif idField.type== 'Long' >
        ${idField.columnName} NUMBER(19,0),
<#else>
        ${idField.columnName} NVARCHAR2(<#if idField.length gt 0>${idField.length}<#else>50</#if>),
</#if>
<#if pojo_fields?exists>
  <#list  pojo_fields as field>
   <#if field.name?exists && field.columnName?exists && field.type?exists>
	<#if field.type?exists && ( field.type== 'Integer' )>
        ${field.columnName} INTEGER,
        <#elseif field.type?exists && ( field.type== 'Long' )>
        ${field.columnName} NUMBER(19,0),
	<#elseif field.type?exists && ( field.type== 'Boolean' )>
        ${field.columnName} INTEGER,
	<#elseif field.type?exists && ( field.type== 'Double' )>
        ${field.columnName} NUMBER(*,10),
	<#elseif field.type?exists && ( field.type== 'Date')>
        ${field.columnName} TIMESTAMP(6),
	<#elseif field.type?exists && ( field.type== 'String')>
        ${field.columnName} NVARCHAR2(<#if field.length gt 0>${field.length?string("####")}<#else>255</#if>),
	</#if>
    </#if>
  </#list>
</#if>
        primary key (${idField.columnName})
) ;

 
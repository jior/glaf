 
create table ${tableName} (
<#if  idField.type== 'Integer' >
        ${idField.columnName} int,
<#elseif idField.type== 'Long' >
        ${idField.columnName} numeric(5),
<#else>
        ${idField.columnName} varchar(<#if idField.length gt 0>${idField.length}<#else>50</#if>),
</#if>
<#if pojo_fields?exists>
  <#list  pojo_fields as field>
   <#if field.name?exists && field.columnName?exists && field.type?exists>
	<#if field.type?exists && ( field.type== 'Integer' )>
        ${field.columnName}  int,
        <#elseif field.type?exists && ( field.type== 'Long' )>
        ${field.columnName} numeric(5),
	<#elseif field.type?exists && ( field.type== 'Boolean' )>
        ${field.columnName} tinyint,
	<#elseif field.type?exists && ( field.type== 'Double' )>
        ${field.columnName} float,
	<#elseif field.type?exists && ( field.type== 'Date')>
        ${field.columnName} datetime,
	<#elseif field.type?exists && ( field.type== 'String')>
        ${field.columnName} varchar(<#if field.length gt 0>${field.length?string("####")}<#else>255</#if>),
	</#if>
    </#if>
  </#list>
</#if>
        primary key (${idField.columnName})
) ;

  
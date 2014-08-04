package ${packageName}.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ${entityName}Query extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<${idField.type}> ${idField.name}s;
	protected Collection<String> appActorIds;
 <#if pojo_fields?exists>
    <#list  pojo_fields as field>
     <#if field.name != 'processInstanceId' >
      <#if field.type?exists && ( field.type== 'Integer' || field.type== 'Long')>
  	protected ${field.type} ${field.firstLowerName};
  	protected ${field.type} ${field.firstLowerName}GreaterThanOrEqual;
  	protected ${field.type} ${field.firstLowerName}LessThanOrEqual;
  	protected List<${field.type}> ${field.firstLowerName}s;
      <#elseif field.type?exists && ( field.type== 'Boolean')>
  	protected ${field.type} ${field.firstLowerName};
      <#elseif field.type?exists && ( field.type== 'Date')>
        protected ${field.type} ${field.firstLowerName}GreaterThanOrEqual;
  	protected ${field.type} ${field.firstLowerName}LessThanOrEqual;
      <#elseif field.type?exists && ( field.type== 'Double')>
  	protected ${field.type} ${field.firstLowerName}GreaterThanOrEqual;
  	protected ${field.type} ${field.firstLowerName}LessThanOrEqual;
      <#elseif field.type?exists && ( field.type== 'String')>
  	protected ${field.type} ${field.firstLowerName};
  	protected ${field.type} ${field.firstLowerName}Like;
  	protected List<${field.type}> ${field.firstLowerName}s;
       </#if>
      </#if>
    </#list>
</#if>

    public ${entityName}Query() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }

 <#if pojo_fields?exists>
    <#list  pojo_fields as field>
     <#if field.name != 'processInstanceId' >
        <#if field.type?exists && ( field.type== 'Integer' || field.type== 'Long' )>

    public ${field.type} get${field.firstUpperName}(){
        return ${field.firstLowerName};
    }

    public ${field.type} get${field.firstUpperName}GreaterThanOrEqual(){
        return ${field.firstLowerName}GreaterThanOrEqual;
    }

    public ${field.type} get${field.firstUpperName}LessThanOrEqual(){
	return ${field.firstLowerName}LessThanOrEqual;
    }

    public List<${field.type}> get${field.firstUpperName}s(){
	return ${field.firstLowerName}s;
    }
	<#elseif field.type?exists && ( field.type== 'Double' || field.type== 'Date')>

    public ${field.type} get${field.firstUpperName}GreaterThanOrEqual(){
        return ${field.firstLowerName}GreaterThanOrEqual;
    }

    public ${field.type} get${field.firstUpperName}LessThanOrEqual(){
	return ${field.firstLowerName}LessThanOrEqual;
    }

	<#elseif field.type?exists && ( field.type== 'Boolean')>

    public ${field.type} get${field.firstUpperName}(){
        return ${field.firstLowerName};
    }

	<#elseif field.type?exists && ( field.type== 'String')>

    public ${field.type} get${field.firstUpperName}(){
        return ${field.firstLowerName};
    }

    public ${field.type} get${field.firstUpperName}Like(){
	    if (${field.firstLowerName}Like != null && ${field.firstLowerName}Like.trim().length() > 0) {
		if (!${field.firstLowerName}Like.startsWith("%")) {
		    ${field.firstLowerName}Like = "%" + ${field.firstLowerName}Like;
		}
		if (!${field.firstLowerName}Like.endsWith("%")) {
		   ${field.firstLowerName}Like = ${field.firstLowerName}Like + "%";
		}
	    }
	return ${field.firstLowerName}Like;
    }

    public List<${field.type}> get${field.firstUpperName}s(){
	return ${field.firstLowerName}s;
    }

	</#if>
      </#if>
    </#list>
</#if>

 
 <#if pojo_fields?exists>
    <#list  pojo_fields as field>
      <#if field.name != 'processInstanceId' >
        <#if field.type?exists && ( field.type== 'Integer' || field.type== 'Long' )>

    public void set${field.firstUpperName}(${field.type} ${field.firstLowerName}){
        this.${field.firstLowerName} = ${field.firstLowerName};
    }

    public void set${field.firstUpperName}GreaterThanOrEqual(${field.type} ${field.firstLowerName}GreaterThanOrEqual){
        this.${field.firstLowerName}GreaterThanOrEqual = ${field.firstLowerName}GreaterThanOrEqual;
    }

    public void set${field.firstUpperName}LessThanOrEqual(${field.type} ${field.firstLowerName}LessThanOrEqual){
	this.${field.firstLowerName}LessThanOrEqual = ${field.firstLowerName}LessThanOrEqual;
    }

    public void set${field.firstUpperName}s(List<${field.type}> ${field.firstLowerName}s){
        this.${field.firstLowerName}s = ${field.firstLowerName}s;
    }

	<#elseif field.type?exists && ( field.type== 'Double' || field.type== 'Date')>

    public void set${field.firstUpperName}GreaterThanOrEqual(${field.type} ${field.firstLowerName}GreaterThanOrEqual){
        this.${field.firstLowerName}GreaterThanOrEqual = ${field.firstLowerName}GreaterThanOrEqual;
    }

    public void set${field.firstUpperName}LessThanOrEqual(${field.type} ${field.firstLowerName}LessThanOrEqual){
	this.${field.firstLowerName}LessThanOrEqual = ${field.firstLowerName}LessThanOrEqual;
    }

	<#elseif field.type?exists && ( field.type== 'Boolean')>

    public void set${field.firstUpperName}(${field.type} ${field.firstLowerName}){
        this.${field.firstLowerName} = ${field.firstLowerName};
    }

	<#elseif field.type?exists && ( field.type== 'String')>

    public void set${field.firstUpperName}(${field.type} ${field.firstLowerName}){
        this.${field.firstLowerName} = ${field.firstLowerName};
    }

    public void set${field.firstUpperName}Like( ${field.type} ${field.firstLowerName}Like){
	this.${field.firstLowerName}Like = ${field.firstLowerName}Like;
    }

    public void set${field.firstUpperName}s(List<${field.type}> ${field.firstLowerName}s){
        this.${field.firstLowerName}s = ${field.firstLowerName}s;
    }

        </#if>
      </#if>
    </#list>
</#if>


 <#if pojo_fields?exists>
    <#list  pojo_fields as field>
      <#if field.name != 'processInstanceId' >
        <#if field.type?exists && ( field.type== 'Integer' || field.type== 'Long')>

    public ${entityName}Query ${field.firstLowerName}(${field.type} ${field.firstLowerName}){
	if (${field.firstLowerName} == null) {
            throw new RuntimeException("${field.firstLowerName} is null");
        }         
	this.${field.firstLowerName} = ${field.firstLowerName};
	return this;
    }

    public ${entityName}Query ${field.firstLowerName}GreaterThanOrEqual(${field.type} ${field.firstLowerName}GreaterThanOrEqual){
	if (${field.firstLowerName}GreaterThanOrEqual == null) {
	    throw new RuntimeException("${field.firstLowerName} is null");
        }         
	this.${field.firstLowerName}GreaterThanOrEqual = ${field.firstLowerName}GreaterThanOrEqual;
        return this;
    }

    public ${entityName}Query ${field.firstLowerName}LessThanOrEqual(${field.type} ${field.firstLowerName}LessThanOrEqual){
        if (${field.firstLowerName}LessThanOrEqual == null) {
            throw new RuntimeException("${field.firstLowerName} is null");
        }
        this.${field.firstLowerName}LessThanOrEqual = ${field.firstLowerName}LessThanOrEqual;
        return this;
    }

    public ${entityName}Query ${field.firstLowerName}s(List<${field.type}> ${field.firstLowerName}s){
        if (${field.firstLowerName}s == null) {
            throw new RuntimeException("${field.firstLowerName}s is empty ");
        }
        this.${field.firstLowerName}s = ${field.firstLowerName}s;
        return this;
    }

    <#elseif field.type?exists && ( field.type== 'Double' || field.type== 'Date')>


    public ${entityName}Query ${field.firstLowerName}GreaterThanOrEqual(${field.type} ${field.firstLowerName}GreaterThanOrEqual){
	if (${field.firstLowerName}GreaterThanOrEqual == null) {
	    throw new RuntimeException("${field.firstLowerName} is null");
        }         
	this.${field.firstLowerName}GreaterThanOrEqual = ${field.firstLowerName}GreaterThanOrEqual;
        return this;
    }

    public ${entityName}Query ${field.firstLowerName}LessThanOrEqual(${field.type} ${field.firstLowerName}LessThanOrEqual){
        if (${field.firstLowerName}LessThanOrEqual == null) {
            throw new RuntimeException("${field.firstLowerName} is null");
        }
        this.${field.firstLowerName}LessThanOrEqual = ${field.firstLowerName}LessThanOrEqual;
        return this;
    }


        <#elseif field.type?exists && ( field.type== 'Boolean')>

    public ${entityName}Query ${field.firstLowerName}(${field.type} ${field.firstLowerName}){
	if (${field.firstLowerName} == null) {
	    throw new RuntimeException("${field.firstLowerName} is null");
        }         
	this.${field.firstLowerName} = ${field.firstLowerName};
	return this;
    }

        <#elseif field.type?exists && ( field.type== 'String')>

    public ${entityName}Query ${field.firstLowerName}(${field.type} ${field.firstLowerName}){
	if (${field.firstLowerName} == null) {
	    throw new RuntimeException("${field.firstLowerName} is null");
        }         
	this.${field.firstLowerName} = ${field.firstLowerName};
	return this;
    }

    public ${entityName}Query ${field.firstLowerName}Like( ${field.type} ${field.firstLowerName}Like){
        if (${field.firstLowerName}Like == null) {
            throw new RuntimeException("${field.firstLowerName} is null");
        }
        this.${field.firstLowerName}Like = ${field.firstLowerName}Like;
        return this;
    }

    public ${entityName}Query ${field.firstLowerName}s(List<${field.type}> ${field.firstLowerName}s){
        if (${field.firstLowerName}s == null) {
            throw new RuntimeException("${field.firstLowerName}s is empty ");
        }
        this.${field.firstLowerName}s = ${field.firstLowerName}s;
        return this;
    }

        </#if>
      </#if>
    </#list>
</#if>


    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

 <#if pojo_fields?exists>
    <#list  pojo_fields as field>	
        <#if field.type?exists && field.columnName?exists && ( field.type== 'Integer' || field.type== 'Long' || field.type== 'Double' || field.type== 'Date' || field.type== 'String')> 
            if ("${field.firstLowerName}".equals(sortColumn)) {
                orderBy = "E.${field.columnName}" + a_x;
            } 

	</#if>
    </#list>
</#if>	
        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("${idField.name}", "${idField.columnName}");
<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
        <#if field.type?exists && ( field.type== 'Integer' || field.type== 'Long' || field.type== 'Double' || field.type== 'Date' || field.type== 'Boolean' ||field.type== 'String' )>
	   <#if field.columnName?exists >
        addColumn("${field.firstLowerName}", "${field.columnName}");
           </#if>
	</#if>
    </#list>
</#if>
    }

}
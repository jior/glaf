package ${packageName}.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import ${packageName}.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "${tableName}")
public class ${entityName} implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "${idField.columnName}", nullable = false)
        protected ${idField.type} ${idField.name};

<#if pojo_fields?exists>
<#list  pojo_fields as field>	
  <#if field.type?exists >
    <#if field.columnName?exists >
	<#if ( field.type== 'Integer')>
        @Column(name = "${field.columnName}")
	<#elseif ( field.type== 'Long' )>
        @Column(name = "${field.columnName}")	 
	<#elseif ( field.type== 'Double' )>
        @Column(name = "${field.columnName}")	 
	<#elseif ( field.type== 'Date' )>
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "${field.columnName}")	
	<#elseif ( field.type== 'Clob' )>
	@javax.persistence.Lob
        @Column(name = "${field.columnName}") 
	<#elseif ( field.type== 'String' )>
        @Column(name = "${field.columnName}", length=${field.length?c}) 
	<#else>
        @Column(name = "${field.columnName}")  
        </#if>
    <#else>
        @Transient
    </#if>
        <#if ( field.type== 'Clob')>
	protected String ${field.name};
	<#else>
        protected ${field.type} ${field.name};
	</#if>

  </#if>
</#list>
</#if>

         
	public ${entityName}() {

	}

	public ${idField.type} get${idField.firstUpperName}(){
	    return this.${idField.name};
	}

	public void set${idField.firstUpperName}(${idField.type} ${idField.name}) {
	    this.${idField.name} = ${idField.name}; 
	}


<#if pojo_fields?exists>
<#list  pojo_fields as field>	
  <#if  field.type?exists >
        <#if ( field.type== 'Clob')>
	public String get${field.firstUpperName}(){
	    return this.${field.name};
	}
	<#else>
        public ${field.type} get${field.firstUpperName}(){
	    return this.${field.name};
	}
	</#if>
  </#if>
</#list>
</#if>


<#if pojo_fields?exists>
<#list  pojo_fields as field>	
  <#if  field.type?exists >
        <#if ( field.type== 'Clob')>
	public void set${field.firstUpperName}(String ${field.name}) {
	    this.${field.name} = ${field.name}; 
	}
	<#else>
        public void set${field.firstUpperName}(${field.type} ${field.name}) {
	    this.${field.name} = ${field.name}; 
	}
	</#if>
  </#if>
</#list>
</#if>


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		${entityName} other = (${entityName}) obj;
		if (${idField.name} == null) {
			if (other.${idField.name} != null)
				return false;
		} else if (!${idField.name}.equals(other.${idField.name}))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((${idField.name} == null) ? 0 : ${idField.name}.hashCode());
		return result;
	}


	public ${entityName} jsonToObject(JSONObject jsonObject) {
            return ${entityName}JsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ${entityName}JsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ${entityName}JsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}

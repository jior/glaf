package ${packageName}.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;

@Entity
@Table(name = "${tableName}")
public class ${entityName} implements Serializable {
	private static final long serialVersionUID = 1L;

        @Id
	@Column(name = "${idField.columnName}", length = 50, nullable = false)
	protected ${idField.type} ${idField.name};

	${jpa_fields?if_exists}

         
	public ${entityName}() {

	}

	public ${idField.type} get${idField.firstUpperName}(){
	    return this.${idField.name};
	}



	${jpa_get_methods?if_exists}


	public void set${idField.firstUpperName}(${idField.type} ${idField.name}) {
	    this.${idField.name} = ${idField.name}; 
	}

	${jpa_set_methods?if_exists}


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
		${entityName} model= new ${entityName}();
		if (jsonObject.containsKey("${idField.name}")) {
		    model.set${idField.firstUpperName}(jsonObject.get${idField.type}("${idField.name}"));
		}
<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
        <#if field.type?exists >
	    <#if ( field.type == 'Integer')>
		if (jsonObject.containsKey("${field.name}")) {
			model.set${field.firstUpperName}(jsonObject.getInteger("${field.name}"));
		}
	    <#elseif ( field.type == 'Long')>
		if (jsonObject.containsKey("${field.name}")) {
			model.set${field.firstUpperName}(jsonObject.getLong("${field.name}"));
		}
	    <#elseif ( field.type == 'Double')>
		if (jsonObject.containsKey("${field.name}")) {
			model.set${field.firstUpperName}(jsonObject.getDouble("${field.name}"));
		}
	    <#elseif ( field.type == 'Boolean')>
		if (jsonObject.containsKey("${field.name}")) {
			model.set${field.firstUpperName}(jsonObject.getBoolean("${field.name}"));
		}
	    <#elseif ( field.type == 'Date')>
		if (jsonObject.containsKey("${field.name}")) {
			model.set${field.firstUpperName}(jsonObject.getDate("${field.name}"));
		}
	    <#elseif ( field.type == 'BigDecimal')>
		if (jsonObject.containsKey("${field.name}")) {
			model.set${field.firstUpperName}(jsonObject.getBigDecimal("${field.name}"));
		}
	    <#elseif ( field.type == 'String')>
		if (jsonObject.containsKey("${field.name}")) {
			model.set${field.firstUpperName}(jsonObject.getString("${field.name}"));
		}
            <#else>
		if (jsonObject.containsKey("${field.name}")) {
			model.set${field.firstUpperName}(jsonObject.getString("${field.name}"));
		}
	    </#if>
	</#if>
    </#list>
</#if>
	    return model;
	}


	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("${idField.name}", ${idField.name});
		jsonObject.put("_${idField.name}_", ${idField.name});
		<#if idField.name == 'id'>
		jsonObject.put("_o${idField.name}_", ${idField.name});
		</#if>
<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
        <#if field.type?exists && ( field.type== 'Integer' || field.type== 'Long' || field.type== 'Double' || field.type== 'Boolean')>
        jsonObject.put("${field.name}", ${field.name});
	    <#elseif field.type?exists && ( field.type== 'Date')>
                if (${field.name} != null) {
                      jsonObject.put("${field.name}", DateUtils.getDate(${field.name}));
		      jsonObject.put("${field.name}_date", DateUtils.getDate(${field.name}));
		      jsonObject.put("${field.name}_datetime", DateUtils.getDateTime(${field.name}));
                }
	    <#else>
		if (${field.name} != null) {
			jsonObject.put("${field.name}", ${field.name});
		   <#if field.name == 'id'>
                        jsonObject.put("_${field.name}_", ${field.name});
			jsonObject.put("_o${field.name}_", ${field.name});
		   </#if>
		} 
	    </#if>
    </#list>
</#if>
		return jsonObject;
	}

	public ObjectNode toObjectNode(){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("${idField.name}", ${idField.name});
		jsonObject.put("_${idField.name}_", ${idField.name});
		<#if idField.name == 'id'>
		jsonObject.put("_o${idField.name}_", ${idField.name});
		</#if>
<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
        <#if field.type?exists && ( field.type== 'Integer' || field.type== 'Long' || field.type== 'Double' || field.type== 'Boolean')>
                jsonObject.put("${field.name}", ${field.name});
	<#elseif field.type?exists && ( field.type== 'Date')>
                if (${field.name} != null) {
                      jsonObject.put("${field.name}", DateUtils.getDate(${field.name}));
		      jsonObject.put("${field.name}_date", DateUtils.getDate(${field.name}));
		      jsonObject.put("${field.name}_datetime", DateUtils.getDateTime(${field.name}));
                }
	<#else>
                if (${field.name} != null) {
                     jsonObject.put("${field.name}", ${field.name});
		   <#if field.name == 'id'>
                     jsonObject.put("_${field.name}_", ${field.name});
		     jsonObject.put("_o${field.name}_", ${field.name});
		   </#if>
                } 
	</#if>
    </#list>
</#if>
                return jsonObject;
	}


	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}

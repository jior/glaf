package ${packageName}.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import ${packageName}.domain.*;


/**
 * 
 * JSONπ§≥ß¿‡
 *
 */
public class ${entityName}JsonFactory {

	public static ${entityName} jsonToObject(JSONObject jsonObject) {
            ${entityName} model = new ${entityName}();
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

	public static JSONObject toJsonObject(${entityName} model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("${idField.name}", model.get${idField.firstUpperName}());
		jsonObject.put("_${idField.name}_", model.get${idField.firstUpperName}());
		<#if idField.name == 'id'>
		jsonObject.put("_o${idField.name}_", model.get${idField.firstUpperName}());
		</#if>
<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
        <#if field.type?exists && ( field.type== 'Integer' || field.type== 'Long' || field.type== 'Double' || field.type== 'Boolean')>
        jsonObject.put("${field.name}", model.get${field.firstUpperName}());
	    <#elseif field.type?exists && ( field.type== 'Date')>
                if (model.get${field.firstUpperName}() != null) {
                      jsonObject.put("${field.name}", DateUtils.getDate(model.get${field.firstUpperName}()));
		      jsonObject.put("${field.name}_date", DateUtils.getDate(model.get${field.firstUpperName}()));
		      jsonObject.put("${field.name}_datetime", DateUtils.getDateTime(model.get${field.firstUpperName}()));
                }
	    <#else>
		if (model.get${field.firstUpperName}() != null) {
			jsonObject.put("${field.name}", model.get${field.firstUpperName}());
		   <#if field.name == 'id'>
                        jsonObject.put("_${field.name}_", model.get${field.firstUpperName}());
			jsonObject.put("_o${field.name}_", model.get${field.firstUpperName}());
		   </#if>
		} 
	    </#if>
    </#list>
</#if>
		return jsonObject;
	}


	public static ObjectNode toObjectNode(${entityName} model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("${idField.name}", model.get${idField.firstUpperName}());
		jsonObject.put("_${idField.name}_", model.get${idField.firstUpperName}());
		<#if idField.name == 'id'>
		jsonObject.put("_o${idField.name}_", model.get${idField.firstUpperName}());
		</#if>
<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
        <#if field.type?exists && ( field.type== 'Integer' || field.type== 'Long' || field.type== 'Double' || field.type== 'Boolean')>
                jsonObject.put("${field.name}", model.get${field.firstUpperName}());
	<#elseif field.type?exists && ( field.type== 'Date')>
                if (model.get${field.firstUpperName}() != null) {
                      jsonObject.put("${field.name}", DateUtils.getDate(model.get${field.firstUpperName}()));
		      jsonObject.put("${field.name}_date", DateUtils.getDate(model.get${field.firstUpperName}()));
		      jsonObject.put("${field.name}_datetime", DateUtils.getDateTime(model.get${field.firstUpperName}()));
                }
	<#else>
                if (model.get${field.firstUpperName}() != null) {
                     jsonObject.put("${field.name}", model.get${field.firstUpperName}());
		   <#if field.name == 'id'>
                     jsonObject.put("_${field.name}_", model.get${field.firstUpperName}());
		     jsonObject.put("_o${field.name}_", model.get${field.firstUpperName}());
		   </#if>
                } 
	</#if>
    </#list>
</#if>
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<${entityName}> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (${entityName} model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<${entityName}> arrayToList(JSONArray array) {
		java.util.List<${entityName}> list = new java.util.ArrayList<${entityName}>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			${entityName} model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ${entityName}JsonFactory() {

	}

}

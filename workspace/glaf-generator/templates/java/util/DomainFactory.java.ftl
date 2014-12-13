package ${packageName}.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;


/**
 * 
 * 实体数据工厂类
 *
 */
public class ${entityName}DomainFactory {

    public static final String TABLENAME = "${tableName}";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("${idField.name}", "${idField.columnName}");
<#if pojo_fields?exists>
<#list pojo_fields as field>	
  <#if field.type?exists >
    <#if field.columnName?exists >
        columnMap.put("${field.name}", "${field.columnName}");
    </#if>
  </#if>
</#list>
</#if>  

	javaTypeMap.put("${idField.name}", "${idField.type}");
<#if pojo_fields?exists>
<#list pojo_fields as field>	
  <#if field.type?exists >
    <#if field.columnName?exists >
        javaTypeMap.put("${field.name}", "${field.type}");
    </#if>
  </#if>
</#list>
</#if> 
    }


    public static Map<String, String> getColumnMap() {
	return columnMap;
    }

    public static Map<String, String> getJavaTypeMap() {
	return javaTypeMap;
    }

    public static TableDefinition getTableDefinition(){
        return getTableDefinition(TABLENAME);
    }

    public static TableDefinition getTableDefinition(String tableName) {
        tableName = tableName.toUpperCase();
        TableDefinition tableDefinition = new TableDefinition();
        tableDefinition.setTableName(tableName);
        tableDefinition.setName("${entityName}");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("${idField.name}");
        idColumn.setColumnName("${idField.columnName}");
        idColumn.setJavaType("${idField.type}");
	<#if ( idField.type== 'String' )>
        idColumn.setLength(${idField.length?c});
        </#if>
        tableDefinition.setIdColumn(idColumn);


<#if pojo_fields?exists>
<#list pojo_fields as field>	
  <#if field.type?exists >
    <#if field.columnName?exists >
        ColumnDefinition ${field.name} = new ColumnDefinition();
        ${field.name}.setName("${field.name}");
        ${field.name}.setColumnName("${field.columnName}");
        ${field.name}.setJavaType("${field.type}");
	<#if ( field.type== 'String' )>
        ${field.name}.setLength(${field.length?c});
        </#if>
        tableDefinition.addColumn(${field.name});

    </#if>
  </#if>
</#list>
</#if>      
       return tableDefinition;
     }

	 
    public static TableDefinition createTable() {
        TableDefinition tableDefinition = getTableDefinition(TABLENAME);
        if (!DBUtils.tableExists(TABLENAME)) {
            DBUtils.createTable(tableDefinition);
        } else {
            DBUtils.alterTable(tableDefinition);
        }
        return tableDefinition;
    }


    public static TableDefinition createTable(String tableName) {
        TableDefinition tableDefinition = getTableDefinition(tableName);
        if (!DBUtils.tableExists(tableName)) {
            DBUtils.createTable(tableDefinition);
        } else {
            DBUtils.alterTable(tableDefinition);
        }
        return tableDefinition;
    }


    	public static void processDataRequest(DataRequest dataRequest) {
	    if (dataRequest != null) {
		if (dataRequest.getFilter() != null) {
			if (dataRequest.getFilter().getField() != null) {
				dataRequest.getFilter().setColumn(
						columnMap.get(dataRequest.getFilter().getField()));
				dataRequest.getFilter().setJavaType(
						javaTypeMap.get(dataRequest.getFilter().getField()));
			}

			List<FilterDescriptor> filters = dataRequest.getFilter()
					.getFilters();
			for (FilterDescriptor filter : filters) {
				filter.setParent(dataRequest.getFilter());
				if (filter.getField() != null) {
					filter.setColumn(columnMap.get(filter.getField()));
					filter.setJavaType(javaTypeMap.get(filter.getField()));
				}

				List<FilterDescriptor> subFilters = filter.getFilters();
				for (FilterDescriptor f : subFilters) {
					f.setColumn(columnMap.get(f.getField()));
					f.setJavaType(javaTypeMap.get(f.getField()));
					f.setParent(filter);
				}
			}
		}
	    }
	}

    private ${entityName}DomainFactory() {

    }

}

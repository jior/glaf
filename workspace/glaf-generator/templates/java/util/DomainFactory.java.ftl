package ${packageName}.util;


import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;


/**
 * 
 * 实体数据工厂类
 *
 */
public class ${entityName}DomainFactory {

    public static TableDefinition getTableDefinition(){
        return getTableDefinition("${tableName}");
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
        TableDefinition tableDefinition = getTableDefinition("${tableName}");
        if (!DBUtils.tableExists(tableName)) {
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

    private ${entityName}DomainFactory() {

    }

}

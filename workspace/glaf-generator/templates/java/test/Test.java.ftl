package ${packageName}.test;

import java.util.*;

import org.junit.Test;

import ${packageName}.domain.${entityName};
import ${packageName}.query.${entityName}Query;
import ${packageName}.service.${entityName}Service;
import com.glaf.test.AbstractTest;

/**
 * 
 * µ•‘™≤‚ ‘¿‡
 *
 */
public class ${entityName}Test extends AbstractTest {

	protected ${entityName}Service ${modelName}Service;

	public ${entityName}Test() {
		${modelName}Service = getBean("${modelName}Service");
	}

	@Test
	public void insert${entityName}() {
		for (int i = 0; i < 10; i++) {
			${entityName} ${modelName} = new ${entityName}();
 <#if pojo_fields?exists>
    <#list  pojo_fields as field>	
      <#if field.type?exists && ( field.type== 'Integer')>
                     ${modelName}.set${field.firstUpperName}(1);
      <#elseif field.type?exists && ( field.type== 'Long')>
                     ${modelName}.set${field.firstUpperName}(100L);
      <#elseif field.type?exists && ( field.type== 'Double')>
                     ${modelName}.set${field.firstUpperName}(50.25D);
      <#elseif field.type?exists && ( field.type== 'Date')>
                     ${modelName}.set${field.firstUpperName}(new Date());
      <#elseif field.type?exists && ( field.type== 'String')>
                    ${modelName}.set${field.firstUpperName}("${field.title}");
      </#if>
    </#list>
</#if>
      
			${modelName}Service.save(${modelName});
		}
	}

	@Test
	public void list() {
		${entityName}Query ${modelName}Query = new ${entityName}Query();

		logger.debug("---------------------total----------------------");
		int total = ${modelName}Service.get${entityName}CountByQueryCriteria(${modelName}Query);

		logger.debug("row count:" + total);

		logger.debug("----------------------list---------------------");

		if (total > 0) {
			List<${entityName}> list = ${modelName}Service.get${entityName}sByQueryCriteria(0, 10,
					${modelName}Query);
			logger.debug(list);
		}
	}

}

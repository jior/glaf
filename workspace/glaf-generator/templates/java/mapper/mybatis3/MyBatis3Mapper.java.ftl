package ${packageName}.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import ${packageName}.domain.*;
import ${packageName}.query.*;

/**
 * 
 * Mapper�ӿ�
 *
 */

@Component
public interface ${entityName}Mapper {

	void delete${entityName}s(${entityName}Query query);

	void delete${entityName}ById(${idField.type} id);

	${entityName} get${entityName}ById(${idField.type} id);

	int get${entityName}Count(${entityName}Query query);

	List<${entityName}> get${entityName}s(${entityName}Query query);

	void insert${entityName}(${entityName} model);

	void update${entityName}(${entityName} model);

}

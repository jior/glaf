package ${packageName}.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import ${packageName}.domain.*;
import ${packageName}.query.*;

 
@Transactional(readOnly = true)
public interface ${entityName}Service {
	 
         /**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(${idField.type} id);

        /**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<${idField.type}> ${idField.name}s);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<${entityName}> list(${entityName}Query query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int get${entityName}CountByQueryCriteria(${entityName}Query query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<${entityName}> get${entityName}sByQueryCriteria(int start, int pageSize,
			${entityName}Query query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ${entityName} get${entityName}(${idField.type} id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(${entityName} ${modelName});

}

package ${packageName}.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import ${packageName}.domain.*;
import ${packageName}.query.*;

 
@Transactional(readOnly = true)
public interface ${entityName}Service {
	 
         /**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(${idField.type} id);

        /**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<${idField.type}> ${idField.name}s);

          /**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	 List<${entityName}> list(${entityName}Query query);

         /**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	 int get${entityName}CountByQueryCriteria(${entityName}Query query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	 List<${entityName}> get${entityName}sByQueryCriteria(int start, int pageSize,
			${entityName}Query query) ;

         /**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	 ${entityName} get${entityName}(${idField.type} id);

        /**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	 void save(${entityName} ${modelName});

}

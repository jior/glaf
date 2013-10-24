package ${packageName}.service;

import java.util.*;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.*;
import com.glaf.base.entity.*;
import com.glaf.base.id.*;
import com.glaf.base.utils.*;

import ${packageName}.domain.*;


@Service
@Transactional(readOnly = true)
public class ${entityName}Service {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected PersistenceDAO persistenceDAO;

	@Resource
	protected IdGenerator idGenerator;

	public ${entityName}Service() {

	}

	public void clear() {
	 
	}

	@Transactional
	public void deleteById(String id) {
		${entityName} ${modelName} = (${entityName}) persistenceDAO.getPersistObject(
				${entityName}.class, id);
		if (${modelName} != null) {
			this.clear();
			persistenceDAO.delete(${modelName});
		}
	}

	@Transactional
	public void deleteByIds(Collection<Object> ${idField.name}s) {
		if (${idField.name}s != null && ${idField.name}s.size() > 0) {
		    Map<String, Object> params = new HashMap<String, Object>();
		    StringBuffer buffer = new StringBuffer();
		    buffer.append(" delete from ")
				.append(${entityName}.class.getSimpleName())
				.append(" a where 1=1 ");
			buffer.append(" and ( ");
			int index = 0;
			for (Object object : ${idField.name}s) {
				if (index > 0) {
					buffer.append(" or ");
				}
				String p_name = "${idField.name}_" + index;
				buffer.append(" a.id = :").append(p_name);
				params.put(p_name, object);
				index++;
			}
			buffer.append(" ) ");
			persistenceDAO.executeUpdate(buffer.toString(), params);
		        this.clear();
		}
	}

	public ${entityName} get${entityName}(String id) {
		${entityName} ${modelName} = (${entityName}) persistenceDAO.getPersistObject(
				${entityName}.class, id);
		return ${modelName};
	}

	public PageResult getPageResult${entityName}s(QueryContext queryContext) {
		Map<String, Object> paramMap = queryContext.getQueryParams();
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" from " + ${entityName}.class.getSimpleName()
				+ " as a where 1=1 ");
 

<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
         <#if field.type?exists && field.type== 'Date'>

	    if (paramMap.get("${field.name}") != null) {
		    Object object = paramMap.get("${field.name}");
		    if (object instanceof java.util.Date) {
			params.put("${field.name}", paramMap.get("${field.name}"));
		    } else {
			 String dateTime = (String) object;
			 if (!dateTime.endsWith(" 00:00:00")) {
				dateTime += " 00:00:00";
			  }
			Date date = DateTools.toDate(dateTime);
			params.put("${field.name}", date);
		         }
		   buffer.append(" and ( a.${field.name} >= :${field.name} )");
		}

		if (paramMap.get("${field.name}GreaterThanOrEqual") != null) {
			Object object = paramMap.get("${field.name}GreaterThanOrEqual");
			if (object instanceof java.util.Date) {
				params.put("${field.name}GreaterThanOrEqual", paramMap.get("${field.name}GreaterThanOrEqual"));
			} else {
				String dateTime = (String) object;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("${field.name}GreaterThanOrEqual", date);
			}
			buffer.append(" and ( a.${field.name} >= :${field.name}GreaterThanOrEqual )");
		}

		if (paramMap.get("${field.name}LessThanOrEqual") != null) {
			Object object = paramMap.get("${field.name}LessThanOrEqual");
			if (object instanceof java.util.Date) {
				params.put("${field.name}LessThanOrEqual", paramMap.get("${field.name}LessThanOrEqual"));
			} else {
				String dateTime = (String) object;
				if (!dateTime.endsWith(" 00:00:00")) {
					dateTime += " 00:00:00";
				}
				Date date = DateTools.toDate(dateTime);
				params.put("${field.name}LessThanOrEqual", date);
			}
			buffer.append(" and ( a.${field.name} >= :${field.name}LessThanOrEqual )");
		}

	<#elseif field.type?exists && field.type== 'Integer'>

	    if (paramMap.get("${field.name}") != null) {
			params.put("${field.name}", paramMap.get("${field.name}"));
			buffer.append(" and  a.${field.name} = :${field.name} ");
		}

		if (paramMap.get("${field.name}GreaterThanOrEqual") != null) {
			params.put("${field.name}GreaterThanOrEqual", paramMap.get("${field.name}GreaterThanOrEqual"));
			buffer.append(" and  a.${field.name} = :${field.name}GreaterThanOrEqual ");
		}

		if (paramMap.get("${field.name}LessThanOrEqual") != null) {
			params.put("${field.name}LessThanOrEqual", paramMap.get("${field.name}LessThanOrEqual"));
			buffer.append(" and  a.${field.name} = :${field.name}LessThanOrEqual ");
		}

	<#elseif field.type?exists && field.type== 'Long'>

	    if (paramMap.get("${field.name}") != null) {
			params.put("${field.name}", paramMap.get("${field.name}"));
			buffer.append(" and  a.${field.name} = :${field.name} ");
		}

		if (paramMap.get("${field.name}GreaterThanOrEqual") != null) {
			params.put("${field.name}GreaterThanOrEqual", paramMap.get("${field.name}GreaterThanOrEqual"));
			buffer.append(" and  a.${field.name} = :${field.name}GreaterThanOrEqual ");
		}

		if (paramMap.get("${field.name}LessThanOrEqual") != null) {
			params.put("${field.name}LessThanOrEqual", paramMap.get("${field.name}LessThanOrEqual"));
			buffer.append(" and  a.${field.name} = :${field.name}LessThanOrEqual ");
		}

	<#elseif field.type?exists && field.type== 'Double'>

        if (paramMap.get("${field.name}") != null) {
			params.put("${field.name}", paramMap.get("${field.name}"));
			buffer.append(" and  a.${field.name} = :${field.name} ");
		}

		if (paramMap.get("${field.name}GreaterThanOrEqual") != null) {
			params.put("${field.name}GreaterThanOrEqual", paramMap.get("${field.name}GreaterThanOrEqual"));
			buffer.append(" and  a.${field.name} = :${field.name}GreaterThanOrEqual ");
		}

		if (paramMap.get("${field.name}LessThanOrEqual") != null) {
			params.put("${field.name}LessThanOrEqual", paramMap.get("${field.name}LessThanOrEqual"));
			buffer.append(" and  a.${field.name} = :${field.name}LessThanOrEqual ");
		}
        
	<#elseif field.type?exists && field.type== 'Boolean'>

	    if (paramMap.get("${field.name}") != null) {
			params.put("${field.name}", paramMap.get("${field.name}"));
			buffer.append(" and  a.${field.name} = :${field.name} ");
		}

	<#else>

	    if (paramMap.get("${field.name}") != null) {
			params.put("${field.name}", paramMap.get("${field.name}"));
			buffer.append(" and  a.${field.name} = :${field.name} ");
		}

		if (paramMap.get("${field.name}Like") != null) {
			params.put("${field.name}Like",  paramMap.get("${field.name}Like"));
			buffer.append(" and  a.${field.name} like :${field.name}Like ");
		}
         </#if>
    </#list>
</#if>

		List<OrderByField> orderByFields = queryContext.getOrderByFields();
		StringBuffer orderBuffer = new StringBuffer();
		if (orderByFields != null && !orderByFields.isEmpty()) {
			orderBuffer.append("order by ");
			Iterator<OrderByField> iterator = orderByFields.iterator();
			while (iterator.hasNext()) {
				OrderByField orderBy = iterator.next();
				orderBuffer.append("a.").append(orderBy.getName());
				if (orderBy.getSort()==1) {
					orderBuffer.append(" desc ");
				}
				if (iterator.hasNext()) {
					orderBuffer.append(", ");
				}
			}
		}

		if (orderBuffer.length() == 0) {
			orderBuffer.append(" order by a.createDate desc ");
		}

		Executor queryExecutor = new Executor();
		queryExecutor.setParams(params);
		queryExecutor.setQuery(buffer.toString() + orderBuffer.toString());

		Executor countExecutor = new Executor();
		countExecutor.setParams(params);
		countExecutor.setQuery(" select count(a.id) " + buffer.toString());

		return persistenceDAO.getPageResult(queryContext.getPageResultNo(),
				queryContext.getPageResultSize(), countExecutor, queryExecutor);
	}

        @Transactional
	public void save(${entityName} ${modelName}) {
		this.clear();
		if (StringUtils.isEmpty(${modelName}.getId())) {
                        ${modelName}.setId(idGenerator.getNextId());
		        ${modelName}.setCreateDate(new Date());
			persistenceDAO.save(${modelName});
		} else {
		        persistenceDAO.update(${modelName});
		}
	}

	 
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	public void setPersistenceDAO(PersistenceDAO persistenceDAO) {
		this.persistenceDAO = persistenceDAO;
	}

}

package ${packageName}.service;

import java.util.*;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.*;
import com.glaf.base.id.*;
import com.glaf.base.entity.*;

 
import ${packageName}.domain.*;

@Service
@Transactional(readOnly = true)
public class ${entityName}Service {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

        protected IdGenerator idGenerator;

        protected EntityDAO entityDAO;

	public ${entityName}Service() {

	}

	public void clear() {

	}

	@Transactional
	public void deleteById(String id) {
		entityDAO.delete("delete${entityName}ById", id);
	}

	@Transactional
	public void deleteByIds(List<Object> ${idField.name}s) {
		entityDAO.deleteAll("delete${entityName}s", ${idField.name}s);
	}

	public PageResult getPageResult${entityName}s(QueryContext queryContext) {
		Map<String, Object> paramMap = queryContext.getQueryParams();
		PageResult jpage = new PageResult();
		int count = entityDAO.getCount("get${entityName}Count", paramMap);
		if (count > 0) {
			List<OrderByField> orderByFields = queryContext.getOrderByFields();
			if (orderByFields != null && !orderByFields.isEmpty()) {
				paramMap.put("orderByFields", orderByFields);
				paramMap.put("orderByField", orderByFields.get(0));
				OrderByField orderBy = orderByFields.get(0);
<#if pojo_fields?exists>
    <#list  pojo_fields as field>	
     <#if field.columnName?exists>
${blank16}if(StringUtils.equals(orderBy.getName(), "${field.name}")){
${blank16}${blank4}paramMap.put("sortColumn", "${field.columnName}");
${blank16}${blank4}if(orderBy.getSort()==1){
${blank16}${blank4}${blank4}paramMap.put("sortOrder", "desc");
${blank16}${blank4}} else {
${blank16}${blank4}${blank4}paramMap.put("sortOrder", "asc");
${blank16}${blank4}}
${blank16}}
     </#if>
    </#list>
</#if>
			} else {
				paramMap.put("sortColumn", "CREATEDATE_");
				paramMap.put("sortOrder", "desc");
			}
 
			SqlExecutor queryExecutor = new SqlExecutor();
			queryExecutor.setStatementId("get${entityName}s");
			queryExecutor.setParameter(paramMap);
			List<Object> rows = entityDAO.getList(
					queryContext.getPageResultNo(), queryContext.getPageResultSize(),
					queryExecutor);
			jpage.setTotalRecordCount(count);
			jpage.setCurrentPageResult(queryContext.getPageResultNo());
			jpage.setPageResultSize(queryContext.getPageResultSize());
			jpage.setRows(rows);
		} else {
			jpage.setPageResultSize(0);
			jpage.setCurrentPageResult(0);
			jpage.setTotalRecordCount(0);
		}
		return jpage;
	}

	public ${entityName} get${entityName}(String id) {
		${entityName} ${modelName} = (${entityName})entityDAO.getObject("get${entityName}ById", id);
		return ${modelName};
	}

	@Transactional
	public void save(${entityName} ${modelName}) {
		this.clear();
		if (StringUtils.isEmpty(${modelName}.getId())) {
			${modelName}.setId(idGenerator.getNextId());
			${modelName}.setCreateDate(new Date());
			entityDAO.insert("insert${entityName}", trip);
		} else {
			entityDAO.update("update${entityName}", trip);
		}
	}

	@Resource
	@Qualifier("myBatis2DbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	@Qualifier("myBatis2EntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

}

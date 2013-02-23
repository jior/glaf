package ${packageName}.service;

import java.util.*;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;

import ${packageName}.mapper.*;
import ${packageName}.model.*;
import ${packageName}.query.*;

@Service("${modelName}Service")
@Transactional(readOnly = true) 
public class ${entityName}ServiceImpl implements ${entityName}Service {
	protected final static Log logger = LogFactory.getLog(${entityName}ServiceImpl.class);

<#if idField.type?exists && ( idField.type== 'Integer' )>        
        protected LongIdGenerator idGenerator;
<#elseif idField.type?exists && ( idField.type== 'Long' )>
        protected LongIdGenerator idGenerator;
<#elseif idField.type?exists && ( idField.type== 'String')>
	protected IdGenerator idGenerator;
</#if>

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ${entityName}Mapper ${modelName}Mapper;

	public ${entityName}ServiceImpl() {

	}

	@Transactional
	public void deleteById(${idField.type} id) {
	     if(id != null ){
		${modelName}Mapper.delete${entityName}ById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<${idField.type}> rowIds) {
	    if(rowIds != null && !rowIds.isEmpty()){
		${entityName}Query query = new ${entityName}Query();
		query.rowIds(rowIds);
		${modelName}Mapper.delete${entityName}s(query);
	    }
	}

	public int count(${entityName}Query query) {
		query.ensureInitialized();
		return ${modelName}Mapper.get${entityName}Count(query);
	}

	public List<${entityName}> list(${entityName}Query query) {
		query.ensureInitialized();
		List<${entityName}> list = ${modelName}Mapper.get${entityName}s(query);
		return list;
	}

         
	public int get${entityName}CountByQueryCriteria(${entityName}Query query) {
		return ${modelName}Mapper.get${entityName}Count(query);
	}

	 
	public List<${entityName}> get${entityName}sByQueryCriteria(int start, int pageSize,
			${entityName}Query query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<${entityName}> rows = sqlSessionTemplate.selectList(
				"get${entityName}s", query, rowBounds);
		return rows;
	}


	public ${entityName} get${entityName}(${idField.type} id) {
	        if(id == null){
		    return null;
		}
		${entityName} ${modelName} = ${modelName}Mapper.get${entityName}ById(id);
		return ${modelName};
	}

	@Transactional
	public void save(${entityName} ${modelName}) {
	<#if idField.type?exists && ( idField.type== 'Integer' )>
            if ( ${modelName}.get${idField.firstUpperName}()  == 0) {
	<#elseif idField.type?exists && ( idField.type== 'Long' )>
            if ( ${modelName}.get${idField.firstUpperName}()  == 0L) {
	<#else>
           if (StringUtils.isEmpty(${modelName}.get${idField.firstUpperName}())) {
	</#if>
			${modelName}.set${idField.firstUpperName}(idGenerator.getNextId());
			//${modelName}.setCreateDate(new Date());
			${modelName}Mapper.insert${entityName}(${modelName});
		} else {
			${modelName}Mapper.update${entityName}(${modelName});
		}
	}

	@Resource
	<#if idField.type?exists && ( idField.type== 'Integer' )>
	@Qualifier("myBatisDbLongIdGenerator")
	public void setLongIdGenerator(LongIdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
	<#elseif idField.type?exists && ( idField.type== 'Long' )>
	@Qualifier("myBatisDbLongIdGenerator")
	public void setLongIdGenerator(LongIdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
	<#elseif idField.type?exists && ( idField.type== 'String')>
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
	</#if>

	@Resource
	public void set${entityName}Mapper(${entityName}Mapper ${modelName}Mapper) {
		this.${modelName}Mapper = ${modelName}Mapper;
	}

        @Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}

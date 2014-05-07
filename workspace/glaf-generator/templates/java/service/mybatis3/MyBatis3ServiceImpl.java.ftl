package ${packageName}.service;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import ${packageName}.mapper.*;
import ${packageName}.domain.*;
import ${packageName}.query.*;

@Service("${modelName}Service")
@Transactional(readOnly = true) 
public class ${entityName}ServiceImpl implements ${entityName}Service {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
    protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

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
	public void deleteByIds(List<${idField.type}> ${idField.name}s) {
	    if(${idField.name}s != null && !${idField.name}s.isEmpty()){
		for(${idField.type} id : ${idField.name}s){
		    ${modelName}Mapper.delete${entityName}ById(id);
		}
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

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int get${entityName}CountByQueryCriteria(${entityName}Query query) {
		return ${modelName}Mapper.get${entityName}Count(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
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
            if ( ${modelName}.get${idField.firstUpperName}()  == null) {
	        ${modelName}.set${idField.firstUpperName}(idGenerator.nextId("${tableName}").intValue());
	<#elseif idField.type?exists && ( idField.type== 'Long' )>
            if ( ${modelName}.get${idField.firstUpperName}()  == null) {
	        ${modelName}.set${idField.firstUpperName}(idGenerator.nextId("${tableName}"));
	<#else>
           if (StringUtils.isEmpty(${modelName}.get${idField.firstUpperName}())) {
	        ${modelName}.set${idField.firstUpperName}(idGenerator.getNextId("${tableName}"));
	</#if>
		//${modelName}.setCreateDate(new Date());
		//${modelName}.setDeleteFlag(0);
		${modelName}Mapper.insert${entityName}(${modelName});
	       } else {
		${modelName}Mapper.update${entityName}(${modelName});
	      }
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
 

	@javax.annotation.Resource
	public void set${entityName}Mapper(${entityName}Mapper ${modelName}Mapper) {
		this.${modelName}Mapper = ${modelName}Mapper;
	}

    @javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}

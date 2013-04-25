
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.core.service.impl;

import java.util.*;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.mapper.*;
import com.glaf.core.domain.*;
import com.glaf.core.query.*;
import com.glaf.core.service.EntityDefinitionService;

@Service("entityDefinitionService")
@Transactional(readOnly = true) 
public class MxEntityDefinitionServiceImpl implements EntityDefinitionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EntityDefinitionMapper entityDefinitionMapper;

	public MxEntityDefinitionServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		entityDefinitionMapper.deleteEntityDefinitionById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
	    if(rowIds != null && !rowIds.isEmpty()){
		EntityDefinitionQuery query = new EntityDefinitionQuery();
		query.rowIds(rowIds);
		entityDefinitionMapper.deleteEntityDefinitions(query);
	    }
	}

	public int count(EntityDefinitionQuery query) {
		query.ensureInitialized();
		return entityDefinitionMapper.getEntityDefinitionCount(query);
	}

	public List<EntityDefinition> list(EntityDefinitionQuery query) {
		query.ensureInitialized();
		List<EntityDefinition> list = entityDefinitionMapper.getEntityDefinitions(query);
		return list;
	}

         
	public int getEntityDefinitionCountByQueryCriteria(EntityDefinitionQuery query) {
		return entityDefinitionMapper.getEntityDefinitionCount(query);
	}

	 
	public List<EntityDefinition> getEntityDefinitionsByQueryCriteria(int start, int pageSize,
			EntityDefinitionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EntityDefinition> rows = sqlSessionTemplate.selectList(
				"getEntityDefinitions", query, rowBounds);
		return rows;
	}


	public EntityDefinition getEntityDefinition(String id) {
	        if(id == null){
		    return null;
		}
		EntityDefinition entityDefinition = entityDefinitionMapper.getEntityDefinitionById(id);
		return entityDefinition;
	}

	@Transactional
	public void save(EntityDefinition entityDefinition) {
           if (StringUtils.isEmpty(entityDefinition.getId())) {
			entityDefinition.setId(idGenerator.getNextId("SYS_ENTITY"));
			//entityDefinition.setCreateDate(new Date());
			//entityDefinition.setDeleteFlag(0);
			entityDefinitionMapper.insertEntityDefinition(entityDefinition);
		} else {
			entityDefinitionMapper.updateEntityDefinition(entityDefinition);
		}
	}

	@Resource(name="myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	 
	@Resource(name="myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
 

	@Resource
	public void setEntityDefinitionMapper(EntityDefinitionMapper entityDefinitionMapper) {
		this.entityDefinitionMapper = entityDefinitionMapper;
	}

        @Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}

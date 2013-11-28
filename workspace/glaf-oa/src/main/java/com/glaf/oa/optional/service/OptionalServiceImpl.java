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
package com.glaf.oa.optional.service;

import java.util.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.oa.optional.mapper.*;
import com.glaf.oa.optional.model.*;
import com.glaf.oa.optional.query.*;

@Service("optionalService")
@Transactional(readOnly = true)
public class OptionalServiceImpl implements OptionalService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected OptionalMapper optionalMapper;

	public OptionalServiceImpl() {

	}

	@Transactional
	public void deleteById(Integer id) {
		if (id != null) {
			optionalMapper.deleteOptionalById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Integer> optionalIds) {
		if (optionalIds != null && !optionalIds.isEmpty()) {
			for (Integer id : optionalIds) {
				optionalMapper.deleteOptionalById(id);
			}
		}
	}

	public int count(OptionalQuery query) {
		query.ensureInitialized();
		return optionalMapper.getOptionalCount(query);
	}

	public List<Optional> list(OptionalQuery query) {
		query.ensureInitialized();
		List<Optional> list = optionalMapper.getOptionals(query);
		return list;
	}

	public int getOptionalCountByQueryCriteria(OptionalQuery query) {
		return optionalMapper.getOptionalCount(query);
	}

	public List<Optional> getOptionalsByQueryCriteria(int start, int pageSize,
			OptionalQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Optional> rows = sqlSessionTemplate.selectList("getOptionals",
				query, rowBounds);
		return rows;
	}

	public Optional getOptional(Integer id) {
		if (id == null) {
			return null;
		}
		Optional optional = optionalMapper.getOptionalById(id);
		return optional;
	}

	@Transactional
	public void save(Optional optional) {
		if (optional.getOptionalId() == null) {
			optional.setOptionalId(idGenerator.nextId("OA_OPTIONAL").intValue());
			// optional.setCreateDate(new Date());
			// optional.setDeleteFlag(0);
			optionalMapper.insertOptional(optional);
		} else {
			optionalMapper.updateOptional(optional);
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
	public void setOptionalMapper(OptionalMapper optionalMapper) {
		this.optionalMapper = optionalMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
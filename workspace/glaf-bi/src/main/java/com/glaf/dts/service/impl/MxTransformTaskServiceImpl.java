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

package com.glaf.dts.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.IdGenerator;
import com.glaf.dts.domain.TransformTask;
import com.glaf.dts.mapper.TransformTaskMapper;
import com.glaf.dts.query.TransformTaskQuery;
import com.glaf.dts.service.ITransformTaskService;

@Service("transformTaskService")
@Transactional(readOnly = true)
public class MxTransformTaskServiceImpl implements ITransformTaskService {
	protected final static Log logger = LogFactory
			.getLog(MxTransformTaskServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TransformTaskMapper transformTaskMapper;

	public MxTransformTaskServiceImpl() {

	}

	public int count(TransformTaskQuery query) {
		query.ensureInitialized();
		return transformTaskMapper.getTransformTaskCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			transformTaskMapper.deleteTransformTaskById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			TransformTaskQuery query = new TransformTaskQuery();
			query.rowIds(rowIds);
			transformTaskMapper.deleteTransformTasks(query);
		}
	}

	@Transactional
	public void deleteByQueryId(String queryId) {
		if (queryId != null) {
			transformTaskMapper.deleteTransformTaskByQueryId(queryId);
		}
	}

	@Transactional
	public void deleteByTableName(String tableName) {
		if (tableName != null) {
			transformTaskMapper.deleteTransformTaskByTableName(tableName);
		}
	}

	public TransformTask getTransformTask(String id) {
		if (id == null) {
			return null;
		}
		TransformTask transformTask = transformTaskMapper
				.getTransformTaskById(id);
		return transformTask;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTransformTaskCountByQueryCriteria(TransformTaskQuery query) {
		return transformTaskMapper.getTransformTaskCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TransformTask> getTransformTasksByQueryCriteria(int start,
			int pageSize, TransformTaskQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TransformTask> rows = sqlSessionTemplate.selectList(
				"getTransformTasks", query, rowBounds);
		return rows;
	}

	/**
	 * 插入多条记录
	 * 
	 */
	@Transactional
	public void insertAll(String queryId, List<TransformTask> transformTasks) {
		transformTaskMapper.deleteTransformTaskByQueryId(queryId);
		for (TransformTask transformTask : transformTasks) {
			if (StringUtils.isEmpty(transformTask.getId())) {
				transformTask.setId(idGenerator.getNextId());
			}
			transformTaskMapper.insertTransformTask(transformTask);
		}
	}

	public List<TransformTask> list(TransformTaskQuery query) {
		query.ensureInitialized();
		List<TransformTask> list = transformTaskMapper.getTransformTasks(query);
		return list;
	}

	@Transactional
	public void save(TransformTask transformTask) {
		if (StringUtils.isEmpty(transformTask.getId())) {
			transformTask.setId(idGenerator.getNextId());
			transformTaskMapper.insertTransformTask(transformTask);
		} else {
			TransformTask model = this.getTransformTask(transformTask.getId());
			if (model == null) {
				transformTaskMapper.insertTransformTask(transformTask);
			} else {
				transformTaskMapper.updateTransformTask(transformTask);
			}
		}
	}

	@Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Resource
	public void setTransformTaskMapper(TransformTaskMapper transformTaskMapper) {
		this.transformTaskMapper = transformTaskMapper;
	}

}
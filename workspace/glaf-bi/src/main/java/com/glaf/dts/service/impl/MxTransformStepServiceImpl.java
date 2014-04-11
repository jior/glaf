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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.IdGenerator;
import com.glaf.dts.domain.TransformStep;
import com.glaf.dts.mapper.TransformStepMapper;
import com.glaf.dts.query.TransformStepQuery;
import com.glaf.dts.service.ITransformStepService;

@Service("transformStepService")
@Transactional(readOnly = true)
public class MxTransformStepServiceImpl implements ITransformStepService {
	protected final static Log logger = LogFactory
			.getLog(MxTransformStepServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TransformStepMapper transformStepMapper;

	public MxTransformStepServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			transformStepMapper.deleteTransformStepById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			TransformStepQuery query = new TransformStepQuery();
			query.rowIds(rowIds);
			transformStepMapper.deleteTransformSteps(query);
		}
	}

	public int count(TransformStepQuery query) {
		query.ensureInitialized();
		return transformStepMapper.getTransformStepCount(query);
	}

	public List<TransformStep> list(TransformStepQuery query) {
		query.ensureInitialized();
		List<TransformStep> list = transformStepMapper.getTransformSteps(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTransformStepCountByQueryCriteria(TransformStepQuery query) {
		return transformStepMapper.getTransformStepCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TransformStep> getTransformStepsByQueryCriteria(int start,
			int pageSize, TransformStepQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TransformStep> rows = sqlSessionTemplate.selectList(
				"getTransformSteps", query, rowBounds);
		return rows;
	}

	public TransformStep getTransformStep(String id) {
		if (id == null) {
			return null;
		}
		TransformStep transformStep = transformStepMapper
				.getTransformStepById(id);
		return transformStep;
	}

	@Transactional
	public void save(TransformStep transformStep) {
		if (StringUtils.isEmpty(transformStep.getId())) {
			transformStep.setId(idGenerator.getNextId());
			// transformStep.setCreateDate(new Date());
			transformStepMapper.insertTransformStep(transformStep);
		} else {
			transformStepMapper.updateTransformStep(transformStep);
		}
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setTransformStepMapper(TransformStepMapper transformStepMapper) {
		this.transformStepMapper = transformStepMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
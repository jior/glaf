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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.IdGenerator;
import com.glaf.dts.domain.TransformExcecution;
import com.glaf.dts.mapper.TransformExcecutionMapper;
import com.glaf.dts.query.TransformExcecutionQuery;
import com.glaf.dts.service.ITransformExcecutionService;

@Service("transformExcecutionService")
@Transactional(readOnly = true)
public class MxTransformExcecutionServiceImpl implements
		ITransformExcecutionService {
	protected final static Log logger = LogFactory
			.getLog(MxTransformExcecutionServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TransformExcecutionMapper transformExcecutionMapper;

	public MxTransformExcecutionServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			transformExcecutionMapper.deleteTransformExcecutionById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			TransformExcecutionQuery query = new TransformExcecutionQuery();
			query.rowIds(rowIds);
			transformExcecutionMapper.deleteTransformExcecutions(query);
		}
	}

	public int count(TransformExcecutionQuery query) {
		query.ensureInitialized();
		return transformExcecutionMapper.getTransformExcecutionCount(query);
	}

	public List<TransformExcecution> list(TransformExcecutionQuery query) {
		query.ensureInitialized();
		List<TransformExcecution> list = transformExcecutionMapper
				.getTransformExcecutions(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTransformExcecutionCountByQueryCriteria(
			TransformExcecutionQuery query) {
		return transformExcecutionMapper.getTransformExcecutionCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TransformExcecution> getTransformExcecutionsByQueryCriteria(
			int start, int pageSize, TransformExcecutionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TransformExcecution> rows = sqlSessionTemplate.selectList(
				"getTransformExcecutions", query, rowBounds);
		return rows;
	}

	public TransformExcecution getTransformExcecution(String id) {
		if (id == null) {
			return null;
		}
		TransformExcecution transformExcecution = transformExcecutionMapper
				.getTransformExcecutionById(id);
		return transformExcecution;
	}

	@Transactional
	public void save(TransformExcecution transformExcecution) {
		if (StringUtils.isEmpty(transformExcecution.getId())) {
			transformExcecution.setId(idGenerator.getNextId());
			// transformExcecution.setCreateDate(new Date());
			transformExcecutionMapper
					.insertTransformExcecution(transformExcecution);
		} else {
			transformExcecutionMapper
					.updateTransformExcecution(transformExcecution);
		}
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setTransformExcecutionMapper(
			TransformExcecutionMapper transformExcecutionMapper) {
		this.transformExcecutionMapper = transformExcecutionMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
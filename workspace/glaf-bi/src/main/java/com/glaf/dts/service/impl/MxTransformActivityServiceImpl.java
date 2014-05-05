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
import com.glaf.dts.domain.TransformActivity;
import com.glaf.dts.mapper.TransformActivityMapper;
import com.glaf.dts.query.TransformActivityQuery;
import com.glaf.dts.service.ITransformActivityService;

@Service("transformActivityService")
@Transactional(readOnly = true)
public class MxTransformActivityServiceImpl implements
		ITransformActivityService {
	protected final static Log logger = LogFactory
			.getLog(MxTransformActivityServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TransformActivityMapper transformActivityMapper;

	public MxTransformActivityServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			transformActivityMapper.deleteTransformActivityById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			TransformActivityQuery query = new TransformActivityQuery();
			query.rowIds(rowIds);
			transformActivityMapper.deleteTransformActivitys(query);
		}
	}

	public int count(TransformActivityQuery query) {
		query.ensureInitialized();
		return transformActivityMapper.getTransformActivityCount(query);
	}

	public List<TransformActivity> list(TransformActivityQuery query) {
		query.ensureInitialized();
		List<TransformActivity> list = transformActivityMapper
				.getTransformActivitys(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTransformActivityCountByQueryCriteria(
			TransformActivityQuery query) {
		return transformActivityMapper.getTransformActivityCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TransformActivity> getTransformActivitysByQueryCriteria(
			int start, int pageSize, TransformActivityQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TransformActivity> rows = sqlSessionTemplate.selectList(
				"getTransformActivitys", query, rowBounds);
		return rows;
	}

	public TransformActivity getTransformActivity(String id) {
		if (id == null) {
			return null;
		}
		TransformActivity transformActivity = transformActivityMapper
				.getTransformActivityById(id);
		return transformActivity;
	}

	@Transactional
	public void save(TransformActivity transformActivity) {
		if (StringUtils.isEmpty(transformActivity.getId())) {
			transformActivity.setId(idGenerator.getNextId());
			// transformActivity.setCreateDate(new Date());
			transformActivityMapper.insertTransformActivity(transformActivity);
		} else {
			transformActivityMapper.updateTransformActivity(transformActivity);
		}
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setTransformActivityMapper(
			TransformActivityMapper transformActivityMapper) {
		this.transformActivityMapper = transformActivityMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
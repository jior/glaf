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

package com.glaf.survey.service.impl;

import java.util.*;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.survey.mapper.*;
import com.glaf.survey.domain.*;
import com.glaf.survey.query.*;
import com.glaf.survey.service.SurveyItemService;

@Service("surveyItemService")
@Transactional(readOnly = true)
public class SurveyItemServiceImpl implements SurveyItemService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SurveyItemMapper surveyItemMapper;

	public SurveyItemServiceImpl() {

	}

	public int count(SurveyItemQuery query) {
		query.ensureInitialized();
		return surveyItemMapper.getSurveyItemCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			surveyItemMapper.deleteSurveyItemById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				surveyItemMapper.deleteSurveyItemById(id);
			}
		}
	}

	public SurveyItem getSurveyItem(Long id) {
		if (id == null) {
			return null;
		}
		SurveyItem surveyItem = surveyItemMapper.getSurveyItemById(id);
		return surveyItem;
	}

	public int getSurveyItemCountByQueryCriteria(SurveyItemQuery query) {
		return surveyItemMapper.getSurveyItemCount(query);
	}

	public List<SurveyItem> getSurveyItemsByQueryCriteria(int start,
			int pageSize, SurveyItemQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SurveyItem> rows = sqlSessionTemplate.selectList("getSurveyItems",
				query, rowBounds);
		return rows;
	}

	public List<SurveyItem> list(SurveyItemQuery query) {
		query.ensureInitialized();
		List<SurveyItem> list = surveyItemMapper.getSurveyItems(query);
		return list;
	}

	@Transactional
	public void save(SurveyItem surveyItem) {
		if (surveyItem.getId() == null) {
			surveyItem.setId(idGenerator.nextId());
			surveyItemMapper.insertSurveyItem(surveyItem);
		} else {
			surveyItemMapper.updateSurveyItem(surveyItem);
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
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSurveyItemMapper(SurveyItemMapper surveyItemMapper) {
		this.surveyItemMapper = surveyItemMapper;
	}

}

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
import com.glaf.survey.service.SurveyResultService;

@Service("surveyResultService")
@Transactional(readOnly = true)
public class SurveyResultServiceImpl implements SurveyResultService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SurveyResultMapper surveyResultMapper;

	public SurveyResultServiceImpl() {

	}

	public int count(SurveyResultQuery query) {
		query.ensureInitialized();
		return surveyResultMapper.getSurveyResultCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			surveyResultMapper.deleteSurveyResultById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				surveyResultMapper.deleteSurveyResultById(id);
			}
		}
	}

	public SurveyResult getSurveyResult(Long id) {
		if (id == null) {
			return null;
		}
		SurveyResult surveyResult = surveyResultMapper.getSurveyResultById(id);
		return surveyResult;
	}

	public int getSurveyResultCountByQueryCriteria(SurveyResultQuery query) {
		return surveyResultMapper.getSurveyResultCount(query);
	}

	public List<SurveyResult> getSurveyResultsByQueryCriteria(int start,
			int pageSize, SurveyResultQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SurveyResult> rows = sqlSessionTemplate.selectList(
				"getSurveyResults", query, rowBounds);
		return rows;
	}

	public List<SurveyResult> list(SurveyResultQuery query) {
		query.ensureInitialized();
		List<SurveyResult> list = surveyResultMapper.getSurveyResults(query);
		return list;
	}

	@Transactional
	public void save(SurveyResult surveyResult) {
		if (surveyResult.getId() == null) {
			surveyResult.setId(idGenerator.nextId());
			surveyResult.setSurveyDate(new Date());
			surveyResultMapper.insertSurveyResult(surveyResult);
		} else {
			surveyResultMapper.updateSurveyResult(surveyResult);
		}
	}

	@Transactional
	public void saveAll(List<SurveyResult> surveyResults) {
		for (SurveyResult surveyResult : surveyResults) {
			if (surveyResult.getId() == null) {
				surveyResult.setId(idGenerator.nextId());
				surveyResult.setSurveyDate(new Date());
				surveyResultMapper.insertSurveyResult(surveyResult);
			} else {
				surveyResultMapper.updateSurveyResult(surveyResult);
			}
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
	public void setSurveyResultMapper(SurveyResultMapper surveyResultMapper) {
		this.surveyResultMapper = surveyResultMapper;
	}

}

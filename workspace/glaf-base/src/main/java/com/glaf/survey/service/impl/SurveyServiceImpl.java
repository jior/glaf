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
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.util.StringTools;
import com.glaf.core.dao.*;
import com.glaf.survey.mapper.*;
import com.glaf.survey.domain.*;
import com.glaf.survey.query.*;
import com.glaf.survey.service.SurveyService;

@Service("surveyService")
@Transactional(readOnly = true)
public class SurveyServiceImpl implements SurveyService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SurveyMapper surveyMapper;

	protected SurveyItemMapper surveyItemMapper;

	protected SurveyResultMapper surveyResultMapper;

	public SurveyServiceImpl() {

	}

	public int count(SurveyQuery query) {
		query.ensureInitialized();
		return surveyMapper.getSurveyCount(query);
	}

	@Transactional
	public void deleteById(Long surveyId) {
		if (surveyId != null) {
			surveyItemMapper.deleteSurveyItemBySurveyId(surveyId);
			surveyResultMapper.deleteSurveyResultBySurveyId(surveyId);
			surveyMapper.deleteSurveyById(surveyId);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				this.deleteById(id);
			}
		}
	}

	public Survey getSurvey(Long id) {
		if (id == null) {
			return null;
		}
		Survey survey = surveyMapper.getSurveyById(id);
		if (survey != null) {
			List<SurveyItem> items = surveyItemMapper
					.getSurveyItemsBySurveyId(survey.getId());
			survey.setItems(items);
			if (StringUtils.isNotEmpty(survey.getRelationIds())) {
				List<Long> relationIds = StringTools.splitToLong(survey
						.getRelationIds());
				if (relationIds != null && !relationIds.isEmpty()) {
					for (Long surveyId : relationIds) {
						Survey relation = surveyMapper.getSurveyById(surveyId);
						List<SurveyItem> childItems = surveyItemMapper
								.getSurveyItemsBySurveyId(relation.getId());
						relation.setItems(childItems);
						survey.addRelation(relation);
					}
				}
			}
		}

		return survey;
	}

	/**
	 * 获取最新的一条投票结果
	 * 
	 * @param surveyId
	 *            投票记录
	 * @param ip
	 *            IP地址
	 * @return
	 */
	public SurveyResult getLatestSurveyResult(Long surveyId, String ip) {
		SurveyResultQuery query = new SurveyResultQuery();
		query.surveyId(surveyId);
		query.ip(ip);
		return surveyResultMapper.getLatestSurveyResult(query);
	}

	public int getSurveyCountByQueryCriteria(SurveyQuery query) {
		return surveyMapper.getSurveyCount(query);
	}

	public List<Survey> getSurveysByQueryCriteria(int start, int pageSize,
			SurveyQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Survey> rows = sqlSessionTemplate.selectList("getSurveys", query,
				rowBounds);
		return rows;
	}

	public List<Survey> list(SurveyQuery query) {
		query.ensureInitialized();
		List<Survey> list = surveyMapper.getSurveys(query);
		return list;
	}

	@Transactional
	public void save(Survey survey) {
		if (survey.getId() == null) {
			survey.setId(idGenerator.nextId());
			survey.setCreateDate(new Date());
			surveyMapper.insertSurvey(survey);
		} else {
			surveyItemMapper.deleteSurveyItemBySurveyId(survey.getId());
			surveyMapper.updateSurvey(survey);
		}
		if (survey.getItems() != null && !survey.getItems().isEmpty()) {
			for (SurveyItem item : survey.getItems()) {
				item.setId(idGenerator.nextId());
				item.setSurveyId(survey.getId());
				surveyItemMapper.insertSurveyItem(item);
			}
		} else {
			if (StringUtils.isNotEmpty(survey.getContent())) {
				int sort = 0;
				StringTokenizer token = new StringTokenizer(survey.getContent());
				while (token.hasMoreTokens()) {
					String tmp = token.nextToken();
					if (StringUtils.contains(tmp, "=")) {
						SurveyItem item = new SurveyItem();
						item.setId(idGenerator.nextId());
						item.setSurveyId(survey.getId());
						item.setValue(tmp.substring(0, tmp.indexOf("=")));
						item.setName(tmp.substring(tmp.indexOf("=") + 1,
								tmp.length()));
						item.setSort(sort++);
						surveyItemMapper.insertSurveyItem(item);
					}
				}
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
	public void setSurveyItemMapper(SurveyItemMapper surveyItemMapper) {
		this.surveyItemMapper = surveyItemMapper;
	}

	@javax.annotation.Resource
	public void setSurveyMapper(SurveyMapper surveyMapper) {
		this.surveyMapper = surveyMapper;
	}

	@javax.annotation.Resource
	public void setSurveyResultMapper(SurveyResultMapper surveyResultMapper) {
		this.surveyResultMapper = surveyResultMapper;
	}

}

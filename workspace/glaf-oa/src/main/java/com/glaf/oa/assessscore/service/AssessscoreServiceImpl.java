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
package com.glaf.oa.assessscore.service;

import java.util.*;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.oa.assessscore.mapper.*;
import com.glaf.oa.assessscore.model.*;
import com.glaf.oa.assessscore.query.*;

@Service("assessscoreService")
@Transactional(readOnly = true)
public class AssessscoreServiceImpl implements AssessscoreService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected AssessscoreMapper assessscoreMapper;

	public AssessscoreServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			assessscoreMapper.deleteAssessscoreById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> scoreids) {
		if (scoreids != null && !scoreids.isEmpty()) {
			for (Long id : scoreids) {
				assessscoreMapper.deleteAssessscoreById(id);
			}
		}
	}

	public int count(AssessscoreQuery query) {
		query.ensureInitialized();
		return assessscoreMapper.getAssessscoreCount(query);
	}

	public List<Assessscore> list(AssessscoreQuery query) {
		query.ensureInitialized();
		List<Assessscore> list = assessscoreMapper.getAssessscores(query);
		return list;
	}

	public int getAssessscoreCountByQueryCriteria(AssessscoreQuery query) {
		return assessscoreMapper.getAssessscoreCount(query);
	}

	public List<Assessscore> getAssessscoresByQueryCriteria(int start,
			int pageSize, AssessscoreQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Assessscore> rows = sqlSessionTemplate.selectList(
				"getAssessscores", query, rowBounds);
		return rows;
	}

	public Assessscore getAssessscore(Long id) {
		if (id == null) {
			return null;
		}
		Assessscore assessscore = assessscoreMapper.getAssessscoreById(id);
		return assessscore;
	}

	@Transactional
	public void save(Assessscore assessscore) {
		if (assessscore.getScoreid() == null) {
			assessscore.setScoreid(idGenerator.nextId("oa_assessscore"));
			// assessscore.setCreateDate(new Date());
			// assessscore.setDeleteFlag(0);
			assessscoreMapper.insertAssessscore(assessscore);
		} else {
			assessscoreMapper.updateAssessscore(assessscore);
		}
	}

	@Resource(name = "myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Resource(name = "myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setAssessscoreMapper(AssessscoreMapper assessscoreMapper) {
		this.assessscoreMapper = assessscoreMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
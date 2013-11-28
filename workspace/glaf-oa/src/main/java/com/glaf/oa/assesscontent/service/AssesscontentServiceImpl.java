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
package com.glaf.oa.assesscontent.service;

import java.util.List;



import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.assesscontent.mapper.AssesscontentMapper;
import com.glaf.oa.assesscontent.model.Assesscontent;
import com.glaf.oa.assesscontent.model.AssesscontentAndScore;
import com.glaf.oa.assesscontent.query.AssesscontentQuery;
import com.glaf.oa.assessscore.query.AssessscoreQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("assesscontentService")
@Transactional(readOnly = true)
public class AssesscontentServiceImpl implements AssesscontentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected AssesscontentMapper assesscontentMapper;

	public AssesscontentServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			assesscontentMapper.deleteAssesscontentById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> contentids) {
		if (contentids != null && !contentids.isEmpty()) {
			for (Long id : contentids) {
				assesscontentMapper.deleteAssesscontentById(id);
			}
		}
	}

	public int count(AssesscontentQuery query) {
		query.ensureInitialized();
		return assesscontentMapper.getAssesscontentCount(query);
	}

	public List<Assesscontent> list(AssesscontentQuery query) {
		query.ensureInitialized();
		List<Assesscontent> list = assesscontentMapper.getAssesscontents(query);
		return list;
	}

	public int getAssesscontentCountByQueryCriteria(AssesscontentQuery query) {
		return assesscontentMapper.getAssesscontentCount(query);
	}

	public List<Assesscontent> getAssesscontentsByQueryCriteria(int start,
			int pageSize, AssesscontentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Assesscontent> rows = sqlSessionTemplate.selectList(
				"getAssesscontents", query, rowBounds);
		return rows;
	}

	public Assesscontent getAssesscontent(Long id) {
		if (id == null) {
			return null;
		}
		Assesscontent assesscontent = assesscontentMapper
				.getAssesscontentById(id);
		return assesscontent;
	}

	@Transactional
	public void save(Assesscontent assesscontent) {
		if (assesscontent.getContentid() == null) {
			assesscontent.setContentid(idGenerator.nextId("oa_assesscontent"));
			// assesscontent.setCreateDate(new Date());
			// assesscontent.setDeleteFlag(0);
			assesscontentMapper.insertAssesscontent(assesscontent);
		} else {
			assesscontentMapper.updateAssesscontent(assesscontent);
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
	public void setAssesscontentMapper(AssesscontentMapper assesscontentMapper) {
		this.assesscontentMapper = assesscontentMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<AssesscontentAndScore> getAssesscontentAndScoreList(
			AssessscoreQuery scoreQuery) {
		List<AssesscontentAndScore> list = assesscontentMapper
				.getAssesscontentAndScoreList(scoreQuery);
		return list;
	}

	public void deleteByParentId(Long parentId) {
		if (parentId != null) {
			assesscontentMapper.deleteAssesscontentByParentId(parentId);
		}
	}

}
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
package com.glaf.oa.assessquestion.service;

import java.util.List;



import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.assessquestion.mapper.AssessquestionMapper;
import com.glaf.oa.assessquestion.model.Assessquestion;
import com.glaf.oa.assessquestion.query.AssessquestionQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("assessquestionService")
@Transactional(readOnly = true)
public class AssessquestionServiceImpl implements AssessquestionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected AssessquestionMapper assessquestionMapper;

	public AssessquestionServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			assessquestionMapper.deleteAssessquestionById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> qustionids) {
		if (qustionids != null && !qustionids.isEmpty()) {
			for (Long id : qustionids) {
				assessquestionMapper.deleteAssessquestionById(id);
			}
		}
	}

	public int count(AssessquestionQuery query) {
		query.ensureInitialized();
		return assessquestionMapper.getAssessquestionCount(query);
	}

	public List<Assessquestion> list(AssessquestionQuery query) {
		// query.ensureInitialized();
		List<Assessquestion> list = assessquestionMapper
				.getAssessquestions(query);
		return list;
	}

	public int getAssessquestionCountByQueryCriteria(AssessquestionQuery query) {
		return assessquestionMapper.getAssessquestionCount(query);
	}

	public List<Assessquestion> getAssessquestionsByQueryCriteria(int start,
			int pageSize, AssessquestionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Assessquestion> rows = sqlSessionTemplate.selectList(
				"getAssessquestions", query, rowBounds);
		return rows;
	}

	public Assessquestion getAssessquestion(Long id) {
		if (id == null) {
			return null;
		}
		Assessquestion assessquestion = assessquestionMapper
				.getAssessquestionById(id);
		return assessquestion;
	}

	@Transactional
	public void save(Assessquestion assessquestion) {
		if (assessquestion.getQustionid() == null) {
			assessquestion
					.setQustionid(idGenerator.nextId("oa_assessquestion"));
			// assessquestion.setCreateDate(new Date());
			// assessquestion.setDeleteFlag(0);
			assessquestionMapper.insertAssessquestion(assessquestion);
		} else {
			assessquestionMapper.updateAssessquestion(assessquestion);
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
	public void setAssessquestionMapper(
			AssessquestionMapper assessquestionMapper) {
		this.assessquestionMapper = assessquestionMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
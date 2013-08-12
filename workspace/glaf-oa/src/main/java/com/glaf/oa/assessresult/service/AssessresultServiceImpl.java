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
package com.glaf.oa.assessresult.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.assessresult.mapper.AssessresultMapper;
import com.glaf.oa.assessresult.model.Assessresult;
import com.glaf.oa.assessresult.model.JobEvaluation;
import com.glaf.oa.assessresult.query.AssessresultQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("assessresultService")
@Transactional(readOnly = true)
public class AssessresultServiceImpl implements AssessresultService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected AssessresultMapper assessresultMapper;

	public AssessresultServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			assessresultMapper.deleteAssessresultById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> resultids) {
		if (resultids != null && !resultids.isEmpty()) {
			for (Long id : resultids) {
				assessresultMapper.deleteAssessresultById(id);
			}
		}
	}

	public int count(AssessresultQuery query) {
		query.ensureInitialized();
		return assessresultMapper.getAssessresultCount(query);
	}

	public List<Assessresult> list(AssessresultQuery query) {
		query.ensureInitialized();
		List<Assessresult> list = assessresultMapper.getAssessresults(query);
		return list;
	}

	public int getAssessresultCountByQueryCriteria(AssessresultQuery query) {
		return assessresultMapper.getAssessresultCount(query);
	}

	public List<Assessresult> getAssessresultsByQueryCriteria(int start,
			int pageSize, AssessresultQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Assessresult> rows = sqlSessionTemplate.selectList(
				"getAssessresults", query, rowBounds);
		return rows;
	}

	public Assessresult getAssessresult(Long id) {
		if (id == null) {
			return null;
		}
		Assessresult assessresult = assessresultMapper.getAssessresultById(id);
		return assessresult;
	}

	@Transactional
	public void save(Assessresult assessresult) {
		if (assessresult.getResultid() == null) {
			assessresult.setResultid(idGenerator.nextId("oa_assessresult"));
			// assessresult.setCreateDate(new Date());
			// assessresult.setDeleteFlag(0);
			assessresultMapper.insertAssessresult(assessresult);
		} else {
			assessresultMapper.updateAssessresult(assessresult);
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
	public void setAssessresultMapper(AssessresultMapper assessresultMapper) {
		this.assessresultMapper = assessresultMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<JobEvaluation> listForJob(AssessresultQuery query) {
		return assessresultMapper.getAssessresultsForJob(query);
	}

	public List<JobEvaluation> listForResultView(AssessresultQuery query) {
		return assessresultMapper.getAssessresultsForResult(query);
	}

}
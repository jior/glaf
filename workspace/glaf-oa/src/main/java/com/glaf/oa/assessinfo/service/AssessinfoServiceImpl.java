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
package com.glaf.oa.assessinfo.service;

import java.util.List;



import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.assessinfo.mapper.AssessinfoMapper;
import com.glaf.oa.assessinfo.model.Assessinfo;
import com.glaf.oa.assessinfo.query.AssessinfoQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("assessinfoService")
@Transactional(readOnly = true)
public class AssessinfoServiceImpl implements AssessinfoService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected AssessinfoMapper assessinfoMapper;

	public AssessinfoServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			assessinfoMapper.deleteAssessinfoById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> indexids) {
		if (indexids != null && !indexids.isEmpty()) {
			for (Long id : indexids) {
				assessinfoMapper.deleteAssessinfoById(id);
			}
		}
	}

	public int count(AssessinfoQuery query) {
		query.ensureInitialized();
		return assessinfoMapper.getAssessinfoCount(query);
	}

	public List<Assessinfo> list(AssessinfoQuery query) {
		query.ensureInitialized();
		List<Assessinfo> list = assessinfoMapper.getAssessinfos(query);
		return list;
	}

	public int getAssessinfoCountByQueryCriteria(AssessinfoQuery query) {
		return assessinfoMapper.getAssessinfoCount(query);
	}

	public List<Assessinfo> getAssessinfosByQueryCriteria(int start,
			int pageSize, AssessinfoQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Assessinfo> rows = sqlSessionTemplate.selectList("getAssessinfos",
				query, rowBounds);
		return rows;
	}

	public Assessinfo getAssessinfo(Long id) {
		if (id == null) {
			return null;
		}
		Assessinfo assessinfo = assessinfoMapper.getAssessinfoById(id);
		return assessinfo;
	}

	@Transactional
	public void save(Assessinfo assessinfo) {
		if (assessinfo.getIndexid() == null) {
			assessinfo.setIndexid(idGenerator.nextId("oa_assessinfo"));
			// assessinfo.setCreateDate(new Date());
			// assessinfo.setDeleteFlag(0);
			assessinfoMapper.insertAssessinfo(assessinfo);
		} else {
			assessinfoMapper.updateAssessinfo(assessinfo);
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
	public void setAssessinfoMapper(AssessinfoMapper assessinfoMapper) {
		this.assessinfoMapper = assessinfoMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
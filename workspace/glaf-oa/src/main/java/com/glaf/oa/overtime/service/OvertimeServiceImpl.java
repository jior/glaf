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
package com.glaf.oa.overtime.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.oa.overtime.mapper.*;
import com.glaf.oa.overtime.model.*;
import com.glaf.oa.overtime.query.*;

@Service("overtimeService")
@Transactional(readOnly = true)
public class OvertimeServiceImpl implements OvertimeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected OvertimeMapper overtimeMapper;

	public OvertimeServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			overtimeMapper.deleteOvertimeById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				overtimeMapper.deleteOvertimeById(Long.parseLong(id));
			}
		}
	}

	public int count(OvertimeQuery query) {
		query.ensureInitialized();
		return overtimeMapper.getOvertimeCount(query);
	}

	public List<Overtime> list(OvertimeQuery query) {
		query.ensureInitialized();
		List<Overtime> list = overtimeMapper.getOvertimes(query);
		return list;
	}

	public int getOvertimeCountByQueryCriteria(OvertimeQuery query) {
		return overtimeMapper.getOvertimeCount(query);
	}

	public List<Overtime> getOvertimesByQueryCriteria(int start, int pageSize,
			OvertimeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Overtime> rows = sqlSessionTemplate.selectList("getOvertimes",
				query, rowBounds);
		return rows;
	}

	public Overtime getOvertime(Long id) {
		if (id == null) {
			return null;
		}
		Overtime overtime = overtimeMapper.getOvertimeById(id);
		return overtime;
	}

	@Transactional
	public void save(Overtime overtime) {
		if (overtime.getId() == 0L) {
			overtime.setId(idGenerator.nextId("oa_overtime"));
			// overtime.setCreateDate(new Date());
			// overtime.setDeleteFlag(0);
			overtimeMapper.insertOvertime(overtime);
		} else {
			overtimeMapper.updateOvertime(overtime);
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
	public void setOvertimeMapper(OvertimeMapper overtimeMapper) {
		this.overtimeMapper = overtimeMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public int getReviewOvertimeCountByQueryCriteria(OvertimeQuery query) {
		return overtimeMapper.getReviewOvertimeCount(query);
	}

	public List<Overtime> getReviewOvertimesByQueryCriteria(int start,
			int pageSize, OvertimeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Overtime> rows = sqlSessionTemplate.selectList(
				"getReviewOvertimes", query, rowBounds);
		return rows;
	}

}
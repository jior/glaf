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
package com.glaf.oa.travelfee.service;

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
import com.glaf.oa.travelfee.mapper.*;
import com.glaf.oa.travelfee.model.*;
import com.glaf.oa.travelfee.query.*;

@Service("travelfeeService")
@Transactional(readOnly = true)
public class TravelfeeServiceImpl implements TravelfeeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TravelfeeMapper travelfeeMapper;

	public TravelfeeServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			travelfeeMapper.deleteTravelfeeById(id);
		}
	}

	@Transactional
	public void deleteByTravelid(Long travelid) {
		if (travelid != null) {
			travelfeeMapper.deleteTravelfeeByTravelid(travelid);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> feeids) {
		if (feeids != null && !feeids.isEmpty()) {
			for (Long id : feeids) {
				travelfeeMapper.deleteTravelfeeById(id);
			}
		}
	}

	public int count(TravelfeeQuery query) {
		query.ensureInitialized();
		return travelfeeMapper.getTravelfeeCount(query);
	}

	public List<Travelfee> list(TravelfeeQuery query) {
		query.ensureInitialized();
		List<Travelfee> list = travelfeeMapper.getTravelfees(query);
		return list;
	}

	public int getTravelfeeCountByQueryCriteria(TravelfeeQuery query) {
		return travelfeeMapper.getTravelfeeCount(query);
	}

	public List<Travelfee> getTravelfeesByQueryCriteria(int start,
			int pageSize, TravelfeeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Travelfee> rows = sqlSessionTemplate.selectList("getTravelfees",
				query, rowBounds);
		return rows;
	}

	public Travelfee getTravelfee(Long id) {
		if (id == null) {
			return null;
		}
		Travelfee travelfee = travelfeeMapper.getTravelfeeById(id);
		return travelfee;
	}

	@Transactional
	public void save(Travelfee travelfee) {
		if (travelfee.getFeeid() == 0L) {
			travelfee.setFeeid(idGenerator.nextId("oa_travelfee"));
			// travelfee.setCreateDate(new Date());
			// travelfee.setDeleteFlag(0);
			travelfeeMapper.insertTravelfee(travelfee);
		} else {
			travelfeeMapper.updateTravelfee(travelfee);
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
	public void setTravelfeeMapper(TravelfeeMapper travelfeeMapper) {
		this.travelfeeMapper = travelfeeMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
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
package com.glaf.oa.travelpersonnel.service;

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
import com.glaf.oa.travelpersonnel.mapper.*;
import com.glaf.oa.travelpersonnel.model.*;
import com.glaf.oa.travelpersonnel.query.*;

@Service("travelpersonnelService")
@Transactional(readOnly = true)
public class TravelpersonnelServiceImpl implements TravelpersonnelService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TravelpersonnelMapper travelpersonnelMapper;

	public TravelpersonnelServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			travelpersonnelMapper.deleteTravelpersonnelById(id);
		}
	}

	@Transactional
	public void deleteByTravelid(Long travelid) {
		if (travelid != null) {
			travelpersonnelMapper.deleteTravelpersonnelByTravelid(travelid);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> personnelids) {
		if (personnelids != null && !personnelids.isEmpty()) {
			for (Long id : personnelids) {
				travelpersonnelMapper.deleteTravelpersonnelById(id);
			}
		}
	}

	public int count(TravelpersonnelQuery query) {
		query.ensureInitialized();
		return travelpersonnelMapper.getTravelpersonnelCount(query);
	}

	public List<Travelpersonnel> list(TravelpersonnelQuery query) {
		query.ensureInitialized();
		List<Travelpersonnel> list = travelpersonnelMapper
				.getTravelpersonnels(query);
		return list;
	}

	public int getTravelpersonnelCountByQueryCriteria(TravelpersonnelQuery query) {
		return travelpersonnelMapper.getTravelpersonnelCount(query);
	}

	public List<Travelpersonnel> getTravelpersonnelsByQueryCriteria(int start,
			int pageSize, TravelpersonnelQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Travelpersonnel> rows = sqlSessionTemplate.selectList(
				"getTravelpersonnels", query, rowBounds);
		return rows;
	}

	public Travelpersonnel getTravelpersonnel(Long id) {
		if (id == null) {
			return null;
		}
		Travelpersonnel travelpersonnel = travelpersonnelMapper
				.getTravelpersonnelById(id);
		return travelpersonnel;
	}

	@Transactional
	public void save(Travelpersonnel travelpersonnel) {
		if (travelpersonnel.getPersonnelid() == 0L) {
			travelpersonnel.setPersonnelid(idGenerator
					.nextId("oa_travelpersonnel"));
			// travelpersonnel.setCreateDate(new Date());
			// travelpersonnel.setDeleteFlag(0);
			travelpersonnelMapper.insertTravelpersonnel(travelpersonnel);
		} else {
			travelpersonnelMapper.updateTravelpersonnel(travelpersonnel);
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
	public void setTravelpersonnelMapper(
			TravelpersonnelMapper travelpersonnelMapper) {
		this.travelpersonnelMapper = travelpersonnelMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
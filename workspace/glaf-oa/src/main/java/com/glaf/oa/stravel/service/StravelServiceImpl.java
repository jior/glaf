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
package com.glaf.oa.stravel.service;

import java.util.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.oa.stravel.mapper.*;
import com.glaf.oa.stravel.model.*;
import com.glaf.oa.stravel.query.*;
import com.glaf.oa.traveladdress.model.Traveladdress;
import com.glaf.oa.travelfee.model.Travelfee;
import com.glaf.oa.travelpersonnel.model.Travelpersonnel;

@Service("stravelService")
@Transactional(readOnly = true)
public class StravelServiceImpl implements StravelService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected StravelMapper stravelMapper;

	public StravelServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			stravelMapper.deleteStravelById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> travelids) {
		if (travelids != null && !travelids.isEmpty()) {
			for (Long id : travelids) {
				stravelMapper.deleteStravelById(id);
			}
		}
	}

	public int count(StravelQuery query) {
		query.ensureInitialized();
		return stravelMapper.getStravelCount(query);
	}

	public List<Stravel> list(StravelQuery query) {
		query.ensureInitialized();
		List<Stravel> list = stravelMapper.getStravels(query);
		return list;
	}

	public int getStravelCountByQueryCriteria(StravelQuery query) {
		return stravelMapper.getStravelCount(query);
	}

	public List<Stravel> getStravelsByQueryCriteria(int start, int pageSize,
			StravelQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Stravel> rows = sqlSessionTemplate.selectList("getStravels",
				query, rowBounds);
		return rows;
	}

	public Stravel getStravel(Long id) {
		if (id == null) {
			return null;
		}
		Stravel stravel = stravelMapper.getStravelById(id);
		return stravel;
	}

	@Transactional
	public void save(Stravel stravel) {
		if (stravel.getTravelid() == 0L) {
			stravel.setTravelid(idGenerator.nextId("oa_stravel"));
			// stravel.setCreateDate(new Date());
			// stravel.setDeleteFlag(0);
			stravelMapper.insertStravel(stravel);
		} else {
			stravelMapper.updateStravel(stravel);
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
	public void setStravelMapper(StravelMapper stravelMapper) {
		this.stravelMapper = stravelMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<Traveladdress> getTraveladdressList(Long travelid) {
		List<Traveladdress> list = stravelMapper.getTraveladdressList(travelid);
		return list;
	}

	public List<Travelpersonnel> getTravelpersonnelList(Long travelid) {
		List<Travelpersonnel> list = stravelMapper
				.getTravelpersonnelList(travelid);
		return list;
	}

	public List<Travelfee> getTravelfeeList(Long travelid) {
		List<Travelfee> list = stravelMapper.getTravelfeeList(travelid);
		return list;
	}

	public int getReviewStravelCountByQueryCriteria(StravelQuery query) {
		return stravelMapper.getReviewStravelCount(query);
	}

	public List<Stravel> getReviewStravelsByQueryCriteria(int start,
			int pageSize, StravelQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Stravel> rows = sqlSessionTemplate.selectList("getReviewStravels",
				query, rowBounds);
		return rows;
	}

}
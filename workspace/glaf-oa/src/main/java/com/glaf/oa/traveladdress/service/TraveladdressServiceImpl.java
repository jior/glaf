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
package com.glaf.oa.traveladdress.service;

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
import com.glaf.oa.traveladdress.mapper.*;
import com.glaf.oa.traveladdress.model.*;
import com.glaf.oa.traveladdress.query.*;

@Service("traveladdressService")
@Transactional(readOnly = true)
public class TraveladdressServiceImpl implements TraveladdressService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TraveladdressMapper traveladdressMapper;

	public TraveladdressServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			traveladdressMapper.deleteTraveladdressById(id);
		}
	}

	@Transactional
	public void deleteByTravelid(Long travelid) {
		if (travelid != null) {
			traveladdressMapper.deleteTraveladdressByTravelid(travelid);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> addressids) {
		if (addressids != null && !addressids.isEmpty()) {
			for (Long id : addressids) {
				traveladdressMapper.deleteTraveladdressById(id);
			}
		}
	}

	public int count(TraveladdressQuery query) {
		query.ensureInitialized();
		return traveladdressMapper.getTraveladdressCount(query);
	}

	public List<Traveladdress> list(TraveladdressQuery query) {
		query.ensureInitialized();
		List<Traveladdress> list = traveladdressMapper.getTraveladdresss(query);
		return list;
	}

	public List<Traveladdress> getTraveladdresssByParentId(Long parentId) {
		List<Traveladdress> list = traveladdressMapper
				.getTraveladdresssByParentId(parentId);
		return list;
	}

	public int getTraveladdressCountByQueryCriteria(TraveladdressQuery query) {
		return traveladdressMapper.getTraveladdressCount(query);
	}

	public List<Traveladdress> getTraveladdresssByQueryCriteria(int start,
			int pageSize, TraveladdressQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Traveladdress> rows = sqlSessionTemplate.selectList(
				"getTraveladdresss", query, rowBounds);
		return rows;
	}

	public Traveladdress getTraveladdress(Long id) {
		if (id == null) {
			return null;
		}
		Traveladdress traveladdress = traveladdressMapper
				.getTraveladdressById(id);
		return traveladdress;
	}

	@Transactional
	public void save(Traveladdress traveladdress) {
		if (traveladdress.getAddressid() == 0L) {
			traveladdress.setAddressid(idGenerator.nextId("oa_traveladdress"));
			// traveladdress.setCreateDate(new Date());
			// traveladdress.setDeleteFlag(0);
			traveladdressMapper.insertTraveladdress(traveladdress);
		} else {
			traveladdressMapper.updateTraveladdress(traveladdress);
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
	public void setTraveladdressMapper(TraveladdressMapper traveladdressMapper) {
		this.traveladdressMapper = traveladdressMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
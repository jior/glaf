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
package com.glaf.oa.ltravel.service;

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
import com.glaf.oa.ltravel.mapper.*;
import com.glaf.oa.ltravel.model.*;
import com.glaf.oa.ltravel.query.*;

@Service("ltravelService")
@Transactional(readOnly = true)
public class LtravelServiceImpl implements LtravelService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected LtravelMapper ltravelMapper;

	public LtravelServiceImpl() {

	}

	@Transactional
	public void deleteById(Long travelid) {
		if (travelid != 0L) {
			ltravelMapper.deleteLtravelById(travelid);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> travelids) {
		if (travelids != null && !travelids.isEmpty()) {
			for (Long travelid : travelids) {
				ltravelMapper.deleteLtravelById(travelid);
			}
		}
	}

	public int count(LtravelQuery query) {
		query.ensureInitialized();
		return ltravelMapper.getLtravelCount(query);
	}

	public List<Ltravel> list(LtravelQuery query) {
		query.ensureInitialized();
		List<Ltravel> list = ltravelMapper.getLtravels(query);
		return list;
	}

	public int getLtravelCountByQueryCriteria(LtravelQuery query) {
		return ltravelMapper.getLtravelCount(query);
	}

	public List<Ltravel> getLtravelsByQueryCriteria(int start, int pageSize,
			LtravelQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Ltravel> rows = sqlSessionTemplate.selectList("getLtravels",
				query, rowBounds);
		return rows;
	}

	public Ltravel getLtravel(Long travelid) {
		if (travelid == 0L) {
			return null;
		}
		Ltravel ltravel = ltravelMapper.getLtravelById(travelid);
		return ltravel;
	}

	@Transactional
	public void save(Ltravel ltravel) {
		if (ltravel.getTravelid() == 0L) {
			ltravel.setTravelid(idGenerator.nextId("oa_ltravel"));
			// ltravel.setCreateDate(new Date());
			// ltravel.setDeleteFlag(0);
			ltravelMapper.insertLtravel(ltravel);
		} else {
			ltravelMapper.updateLtravel(ltravel);
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
	public void setLtravelMapper(LtravelMapper ltravelMapper) {
		this.ltravelMapper = ltravelMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public int getReviewLtravelCountByQueryCriteria(LtravelQuery query) {
		return ltravelMapper.getReviewLtravelCount(query);
	}

	public List<Ltravel> getReviewLtravelsByQueryCriteria(int start,
			int pageSize, LtravelQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Ltravel> rows = sqlSessionTemplate.selectList("getReviewLtravels",
				query, rowBounds);
		return rows;
	}

}
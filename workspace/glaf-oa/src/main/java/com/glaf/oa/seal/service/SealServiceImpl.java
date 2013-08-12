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
package com.glaf.oa.seal.service;

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
import com.glaf.oa.seal.mapper.*;
import com.glaf.oa.seal.model.*;
import com.glaf.oa.seal.query.*;

@Service("sealService")
@Transactional(readOnly = true)
public class SealServiceImpl implements SealService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SealMapper sealMapper;

	public SealServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			sealMapper.deleteSealById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> sealids) {
		if (sealids != null && !sealids.isEmpty()) {
			for (Long id : sealids) {
				sealMapper.deleteSealById(id);
			}
		}
	}

	public int count(SealQuery query) {
		query.ensureInitialized();
		return sealMapper.getSealCount(query);
	}

	public List<Seal> list(SealQuery query) {
		query.ensureInitialized();
		List<Seal> list = sealMapper.getSeals(query);
		return list;
	}

	public int getSealCountByQueryCriteria(SealQuery query) {
		return sealMapper.getSealCount(query);
	}

	public List<Seal> getSealsByQueryCriteria(int start, int pageSize,
			SealQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Seal> rows = sqlSessionTemplate.selectList("getSeals", query,
				rowBounds);
		return rows;
	}

	public Seal getSeal(Long id) {
		if (id == null) {
			return null;
		}
		Seal seal = sealMapper.getSealById(id);
		return seal;
	}

	@Transactional
	public void save(Seal seal) {
		if (seal.getSealid() == null) {
			seal.setSealid(idGenerator.nextId("oa_seal"));
			// seal.setCreateDate(new Date());
			// seal.setDeleteFlag(0);
			sealMapper.insertSeal(seal);
		} else {
			sealMapper.updateSeal(seal);
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
	public void setSealMapper(SealMapper sealMapper) {
		this.sealMapper = sealMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int getReviewSealCountByQueryCriteria(SealQuery query) {
		return sealMapper.getReviewSealCount(query);
	}

	@Override
	public List<Seal> getReviewSealsByQueryCriteria(int start, int pageSize,
			SealQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Seal> rows = sqlSessionTemplate.selectList("getReviewSeals",
				query, rowBounds);
		return rows;
	}

}
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
package com.glaf.oa.reimbursement.service;

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
import com.glaf.oa.reimbursement.mapper.*;
import com.glaf.oa.reimbursement.model.*;
import com.glaf.oa.reimbursement.query.*;

@Service("reimbursementService")
@Transactional(readOnly = true)
public class ReimbursementServiceImpl implements ReimbursementService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ReimbursementMapper reimbursementMapper;

	public ReimbursementServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			reimbursementMapper.deleteReimbursementById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> reimbursementids) {
		if (reimbursementids != null && !reimbursementids.isEmpty()) {
			for (Long id : reimbursementids) {
				reimbursementMapper.deleteReimbursementById(id);
			}
		}
	}

	public int count(ReimbursementQuery query) {
		query.ensureInitialized();
		return reimbursementMapper.getReimbursementCount(query);
	}

	public List<Reimbursement> list(ReimbursementQuery query) {
		query.ensureInitialized();
		List<Reimbursement> list = reimbursementMapper.getReimbursements(query);
		return list;
	}

	public int getReimbursementCountByQueryCriteria(ReimbursementQuery query) {
		return reimbursementMapper.getReimbursementCount(query);
	}

	public List<Reimbursement> getReimbursementsByQueryCriteria(int start,
			int pageSize, ReimbursementQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Reimbursement> rows = sqlSessionTemplate.selectList(
				"getReimbursements", query, rowBounds);
		return rows;
	}

	public Reimbursement getReimbursement(Long id) {
		if (id == null) {
			return null;
		}
		Reimbursement reimbursement = reimbursementMapper
				.getReimbursementById(id);
		return reimbursement;
	}

	@Transactional
	public void save(Reimbursement reimbursement) {
		if (reimbursement.getReimbursementid() == null) {
			reimbursement.setReimbursementid(idGenerator
					.nextId("oa_reimbursement"));
			// reimbursement.setCreateDate(new Date());
			// reimbursement.setDeleteFlag(0);
			reimbursementMapper.insertReimbursement(reimbursement);
		} else {
			reimbursementMapper.updateReimbursement(reimbursement);
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
	public void setReimbursementMapper(ReimbursementMapper reimbursementMapper) {
		this.reimbursementMapper = reimbursementMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public int getReimbursementApproveCountByQueryCriteria(
			ReimbursementQuery query) {
		return reimbursementMapper.getReimbursementApproveCount(query);
	}

	public List<Reimbursement> getReimbursementsApproveByQueryCriteria(
			int start, int pageSize, ReimbursementQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Reimbursement> rows = sqlSessionTemplate.selectList(
				"getReimbursementsApprove", query, rowBounds);
		return rows;
	}

}
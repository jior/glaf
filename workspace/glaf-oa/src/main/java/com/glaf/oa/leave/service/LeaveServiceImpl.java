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
package com.glaf.oa.leave.service;

import java.util.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.oa.leave.mapper.*;
import com.glaf.oa.leave.model.*;
import com.glaf.oa.leave.query.*;

@Service("leaveService")
@Transactional(readOnly = true)
public class LeaveServiceImpl implements LeaveService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected LeaveMapper leaveMapper;

	public LeaveServiceImpl() {

	}

	@Transactional
	public void deleteById(Long leaveid) {
		if (leaveid != 0L) {
			leaveMapper.deleteLeaveById(leaveid);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> leaveids) {
		if (leaveids != null && !leaveids.isEmpty()) {
			for (Long leaveid : leaveids) {
				leaveMapper.deleteLeaveById(leaveid);
			}
		}
	}

	public int count(LeaveQuery query) {
		query.ensureInitialized();
		return leaveMapper.getLeaveCount(query);
	}

	public List<Leave> list(LeaveQuery query) {
		query.ensureInitialized();
		List<Leave> list = leaveMapper.getLeaves(query);
		return list;
	}

	public int getLeaveCountByQueryCriteria(LeaveQuery query) {
		return leaveMapper.getLeaveCount(query);
	}

	public List<Leave> getLeavesByQueryCriteria(int start, int pageSize,
			LeaveQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Leave> rows = sqlSessionTemplate.selectList("getLeaves", query,
				rowBounds);
		return rows;
	}

	public Leave getLeave(Long leaveid) {
		if (leaveid == 0L) {
			return null;
		}
		Leave leave = leaveMapper.getLeaveById(leaveid);
		return leave;
	}

	@Transactional
	public void save(Leave leave) {
		if (leave.getLeaveid() == 0L) {
			leave.setLeaveid(idGenerator.nextId("oa_leave"));
			// leave.setCreateDate(new Date());
			// leave.setDeleteFlag(0);
			leaveMapper.insertLeave(leave);
		} else {
			leaveMapper.updateLeave(leave);
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
	public void setLeaveMapper(LeaveMapper leaveMapper) {
		this.leaveMapper = leaveMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public int getReviewLeaveCountByQueryCriteria(LeaveQuery query) {
		return leaveMapper.getReviewLeaveCount(query);
	}

	public List<Leave> getReviewLeavesByQueryCriteria(int start, int pageSize,
			LeaveQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Leave> rows = sqlSessionTemplate.selectList("getReviewLeaves",
				query, rowBounds);
		return rows;
	}

}
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

package com.glaf.core.service.impl;

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
import com.glaf.core.mapper.*;
import com.glaf.core.domain.*;
import com.glaf.core.query.*;
import com.glaf.core.service.MembershipService;

@Service("membershipService")
@Transactional(readOnly = true)
public class MxMembershipServiceImpl implements MembershipService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MembershipMapper membershipMapper;

	public MxMembershipServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			membershipMapper.deleteMembershipById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			MembershipQuery query = new MembershipQuery();
			query.rowIds(rowIds);
			membershipMapper.deleteMemberships(query);
		}
	}

	public int count(MembershipQuery query) {
		query.ensureInitialized();
		return membershipMapper.getMembershipCount(query);
	}

	public List<Membership> list(MembershipQuery query) {
		query.ensureInitialized();
		List<Membership> list = membershipMapper.getMemberships(query);
		return list;
	}

	public int getMembershipCountByQueryCriteria(MembershipQuery query) {
		return membershipMapper.getMembershipCount(query);
	}

	public List<Membership> getMembershipsByQueryCriteria(int start,
			int pageSize, MembershipQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Membership> rows = sqlSessionTemplate.selectList("getMemberships",
				query, rowBounds);
		return rows;
	}

	public Membership getMembership(Long id) {
		if (id == null) {
			return null;
		}
		Membership membership = membershipMapper.getMembershipById(id);
		return membership;
	}

	@Transactional
	public void save(Membership membership) {
		if (membership.getId() == 0L) {
			membership.setId(idGenerator.nextId());
			membership.setModifyDate(new Date());
			membershipMapper.insertMembership(membership);
		} else {
			membership.setModifyDate(new Date());
			membershipMapper.updateMembership(membership);
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
	public void setMembershipMapper(MembershipMapper membershipMapper) {
		this.membershipMapper = membershipMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}

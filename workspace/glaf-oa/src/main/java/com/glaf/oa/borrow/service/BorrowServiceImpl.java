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
package com.glaf.oa.borrow.service;

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
import com.glaf.oa.borrow.mapper.*;
import com.glaf.oa.borrow.model.*;
import com.glaf.oa.borrow.query.*;

@Service("borrowService")
@Transactional(readOnly = true)
public class BorrowServiceImpl implements BorrowService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected BorrowMapper borrowMapper;

	protected BorrowadderssMapper borrowadderssMapper;

	protected BorrowmoneyMapper borrowmoneyMapper;

	public BorrowServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			borrowadderssMapper.deleteBorrowadderssByParentId(id);
			borrowmoneyMapper.deleteBorrowmoneyByParentId(id);
			borrowMapper.deleteBorrowById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> borrowids) {
		if (borrowids != null && !borrowids.isEmpty()) {
			for (Long id : borrowids) {
				borrowMapper.deleteBorrowById(id);
			}
		}
	}

	public int count(BorrowQuery query) {
		query.ensureInitialized();
		return borrowMapper.getBorrowCount(query);
	}

	public List<Borrow> list(BorrowQuery query) {
		query.ensureInitialized();
		List<Borrow> list = borrowMapper.getBorrows(query);
		return list;
	}

	public int getBorrowCountByQueryCriteria(BorrowQuery query) {
		return borrowMapper.getBorrowCount(query);
	}

	public List<Borrow> getBorrowsByQueryCriteria(int start, int pageSize,
			BorrowQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Borrow> rows = sqlSessionTemplate.selectList("getBorrows", query,
				rowBounds);
		return rows;
	}

	public Borrow getBorrow(Long id) {
		if (id == null) {
			return null;
		}
		Borrow borrow = borrowMapper.getBorrowById(id);
		return borrow;
	}

	@Transactional
	public void save(Borrow borrow) {
		if (borrow.getBorrowid() == null) {
			borrow.setBorrowid(idGenerator.nextId("oa_borrow"));
			// borrow.setCreateDate(new Date());
			// borrow.setDeleteFlag(0);
			borrowMapper.insertBorrow(borrow);
		} else {
			borrowMapper.updateBorrow(borrow);
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
	public void setBorrowadderssMapper(BorrowadderssMapper borrowadderssMapper) {
		this.borrowadderssMapper = borrowadderssMapper;
	}

	@Resource
	public void setBorrowmoneyMapper(BorrowmoneyMapper borrowmoneyMapper) {
		this.borrowmoneyMapper = borrowmoneyMapper;
	}

	@Resource
	public void setBorrowMapper(BorrowMapper borrowMapper) {
		this.borrowMapper = borrowMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<Borrow> getBorrowsApproveByQueryCriteria(int start,
			int pageSize, BorrowQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Borrow> rows = sqlSessionTemplate.selectList("getBorrowsApprove",
				query, rowBounds);
		return rows;
	}

	public int getBorrowApproveCountByQueryCriteria(BorrowQuery query) {
		return borrowMapper.getBorrowApproveCount(query);
	}

}
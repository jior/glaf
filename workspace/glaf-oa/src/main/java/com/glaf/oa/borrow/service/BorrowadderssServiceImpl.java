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

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.borrow.mapper.BorrowadderssMapper;
import com.glaf.oa.borrow.model.Borrowadderss;
import com.glaf.oa.borrow.query.BorrowadderssQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("borrowadderssService")
@Transactional(readOnly = true)
public class BorrowadderssServiceImpl implements BorrowadderssService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected BorrowadderssMapper borrowadderssMapper;

	public BorrowadderssServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			borrowadderssMapper.deleteBorrowadderssById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> addressids) {
		if (addressids != null && !addressids.isEmpty()) {
			for (Long id : addressids) {
				borrowadderssMapper.deleteBorrowadderssById(id);
			}
		}
	}

	public int count(BorrowadderssQuery query) {
		query.ensureInitialized();
		return borrowadderssMapper.getBorrowadderssCount(query);
	}

	public List<Borrowadderss> list(BorrowadderssQuery query) {
		query.ensureInitialized();
		List<Borrowadderss> list = borrowadderssMapper.getBorrowaddersss(query);
		return list;
	}

	public int getBorrowadderssCountByQueryCriteria(BorrowadderssQuery query) {
		return borrowadderssMapper.getBorrowadderssCount(query);
	}

	public List<Borrowadderss> getBorrowaddersssByQueryCriteria(int start,
			int pageSize, BorrowadderssQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Borrowadderss> rows = sqlSessionTemplate.selectList(
				"getBorrowaddersss", query, rowBounds);
		return rows;
	}

	public Borrowadderss getBorrowadderss(Long id) {
		if (id == null) {
			return null;
		}
		Borrowadderss borrowadderss = borrowadderssMapper
				.getBorrowadderssById(id);
		return borrowadderss;
	}

	@Transactional
	public void save(Borrowadderss borrowadderss) {
		if (borrowadderss.getAddressid() == null) {
			borrowadderss.setAddressid(idGenerator.nextId("oa_borrowadderss"));
			// borrowadderss.setCreateDate(new Date());
			// borrowadderss.setDeleteFlag(0);
			borrowadderssMapper.insertBorrowadderss(borrowadderss);
		} else {
			borrowadderssMapper.updateBorrowadderss(borrowadderss);
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
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Transactional
	public void deleteByParentId(Long id) {
		if (id != null) {
			borrowadderssMapper.deleteBorrowadderssByParentId(id);
		}
	}

	public List<Borrowadderss> getBorrowadderssByParentId(Long id) {
		return borrowadderssMapper.getBorrowadderssByParentId(id);
	}

}
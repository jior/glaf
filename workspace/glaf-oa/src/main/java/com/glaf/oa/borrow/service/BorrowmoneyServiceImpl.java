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



import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.borrow.mapper.BorrowmoneyMapper;
import com.glaf.oa.borrow.model.Borrowmoney;
import com.glaf.oa.borrow.query.BorrowmoneyQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("borrowmoneyService")
@Transactional(readOnly = true)
public class BorrowmoneyServiceImpl implements BorrowmoneyService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected BorrowmoneyMapper borrowmoneyMapper;

	public BorrowmoneyServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			borrowmoneyMapper.deleteBorrowmoneyById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> borrowmoneyids) {
		if (borrowmoneyids != null && !borrowmoneyids.isEmpty()) {
			for (Long id : borrowmoneyids) {
				borrowmoneyMapper.deleteBorrowmoneyById(id);
			}
		}
	}

	public int count(BorrowmoneyQuery query) {
		query.ensureInitialized();
		return borrowmoneyMapper.getBorrowmoneyCount(query);
	}

	public List<Borrowmoney> list(BorrowmoneyQuery query) {
		query.ensureInitialized();
		List<Borrowmoney> list = borrowmoneyMapper.getBorrowmoneys(query);
		return list;
	}

	public int getBorrowmoneyCountByQueryCriteria(BorrowmoneyQuery query) {
		return borrowmoneyMapper.getBorrowmoneyCount(query);
	}

	public List<Borrowmoney> getBorrowmoneysByQueryCriteria(int start,
			int pageSize, BorrowmoneyQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Borrowmoney> rows = sqlSessionTemplate.selectList(
				"getBorrowmoneys", query, rowBounds);
		return rows;
	}

	public Borrowmoney getBorrowmoney(Long id) {
		if (id == null) {
			return null;
		}
		Borrowmoney borrowmoney = borrowmoneyMapper.getBorrowmoneyById(id);
		return borrowmoney;
	}

	@Transactional
	public void save(Borrowmoney borrowmoney) {
		if (borrowmoney.getBorrowmoneyid() == null) {
			borrowmoney.setBorrowmoneyid(idGenerator.nextId("oa_borrowmoney"));
			// borrowmoney.setCreateDate(new Date());
			// borrowmoney.setDeleteFlag(0);
			borrowmoneyMapper.insertBorrowmoney(borrowmoney);
		} else {
			borrowmoneyMapper.updateBorrowmoney(borrowmoney);
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
	public void setBorrowmoneyMapper(BorrowmoneyMapper borrowmoneyMapper) {
		this.borrowmoneyMapper = borrowmoneyMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<Borrowmoney> getBorrowmoneyByParentId(Long id) {
		if (id == null) {
			return null;
		}
		List<Borrowmoney> borrowmoneys = borrowmoneyMapper
				.getBorrowmoneyByParentId(id);
		return borrowmoneys;
	}

}
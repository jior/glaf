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
package com.glaf.oa.salescontract.service;

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
import com.glaf.oa.salescontract.mapper.*;
import com.glaf.oa.salescontract.model.*;
import com.glaf.oa.salescontract.query.*;

@Service("salescontractService")
@Transactional(readOnly = true)
public class SalescontractServiceImpl implements SalescontractService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SalescontractMapper salescontractMapper;

	public SalescontractServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			salescontractMapper.deleteSalescontractById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				salescontractMapper.deleteSalescontractById(id);
			}
		}
	}

	public int count(SalescontractQuery query) {
		query.ensureInitialized();
		return salescontractMapper.getSalescontractCount(query);
	}

	public List<Salescontract> list(SalescontractQuery query) {
		query.ensureInitialized();
		List<Salescontract> list = salescontractMapper.getSalescontracts(query);
		return list;
	}

	public int getSalescontractCountByQueryCriteria(SalescontractQuery query) {
		return salescontractMapper.getSalescontractCount(query);
	}

	public List<Salescontract> getSalescontractsByQueryCriteria(int start,
			int pageSize, SalescontractQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Salescontract> rows = sqlSessionTemplate.selectList(
				"getSalescontracts", query, rowBounds);
		return rows;
	}

	public Salescontract getSalescontract(Long id) {
		if (id == null) {
			return null;
		}
		Salescontract salescontract = salescontractMapper
				.getSalescontractById(id);
		return salescontract;
	}

	@Transactional
	public void save(Salescontract salescontract) {
		if (salescontract.getId() == null) {
			salescontract.setId(idGenerator.nextId("oa_salescontract"));
			// salescontract.setCreateDate(new Date());
			// salescontract.setDeleteFlag(0);
			salescontractMapper.insertSalescontract(salescontract);
		} else {
			salescontractMapper.updateSalescontract(salescontract);
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
	public void setSalescontractMapper(SalescontractMapper salescontractMapper) {
		this.salescontractMapper = salescontractMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public int getReviewSalescontractCountByQueryCriteria(
			SalescontractQuery query) {
		return salescontractMapper.getReviewSalescontractCount(query);
	}

	public List<Salescontract> getReviewSalescontractsByQueryCriteria(
			int start, int pageSize, SalescontractQuery query) {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Salescontract> rows = sqlSessionTemplate.selectList(
				"getReviewSalescontracts", query, rowBounds);
		return rows;
	}

}
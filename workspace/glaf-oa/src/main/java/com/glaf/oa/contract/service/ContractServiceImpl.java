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
package com.glaf.oa.contract.service;

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
import com.glaf.oa.contract.mapper.*;
import com.glaf.oa.contract.model.*;
import com.glaf.oa.contract.query.*;

@Service("contractService")
@Transactional(readOnly = true)
public class ContractServiceImpl implements ContractService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ContractMapper contractMapper;

	public ContractServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			contractMapper.deleteContractById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				contractMapper.deleteContractById(id);
			}
		}
	}

	public int count(ContractQuery query) {
		query.ensureInitialized();
		return contractMapper.getContractCount(query);
	}

	public List<Contract> list(ContractQuery query) {
		query.ensureInitialized();
		List<Contract> list = contractMapper.getContracts(query);
		return list;
	}

	public int getContractCountByQueryCriteria(ContractQuery query) {
		return contractMapper.getContractCount(query);
	}

	public List<Contract> getContractsByQueryCriteria(int start, int pageSize,
			ContractQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Contract> rows = sqlSessionTemplate.selectList("getContracts",
				query, rowBounds);
		return rows;
	}

	public int getReviewContractCountByQueryCriteria(ContractQuery query) {
		return contractMapper.getReviewContractCount(query);
	}

	public List<Contract> getReviewContractsByQueryCriteria(int start,
			int pageSize, ContractQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Contract> rows = sqlSessionTemplate.selectList(
				"getReviewContracts", query, rowBounds);
		return rows;
	}

	public Contract getContract(Long id) {
		if (id == null) {
			return null;
		}
		Contract contract = contractMapper.getContractById(id);
		return contract;
	}

	@Transactional
	public void save(Contract contract) {
		if (contract.getId() == null) {
			contract.setId(idGenerator.nextId("contract"));
			// contract.setCreateDate(new Date());
			// contract.setDeleteFlag(0);
			contractMapper.insertContract(contract);
		} else {
			contractMapper.updateContract(contract);
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
	public void setContractMapper(ContractMapper contractMapper) {
		this.contractMapper = contractMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
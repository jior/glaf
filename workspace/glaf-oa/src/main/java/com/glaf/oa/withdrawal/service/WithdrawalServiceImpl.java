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
package com.glaf.oa.withdrawal.service;

import java.util.*;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.oa.withdrawal.mapper.*;
import com.glaf.oa.withdrawal.model.*;
import com.glaf.oa.withdrawal.query.*;

@Service("withdrawalService")
@Transactional(readOnly = true)
public class WithdrawalServiceImpl implements WithdrawalService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected WithdrawalMapper withdrawalMapper;

	public WithdrawalServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			withdrawalMapper.deleteWithdrawalById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> withdrawalids) {
		if (withdrawalids != null && !withdrawalids.isEmpty()) {
			for (Long id : withdrawalids) {
				withdrawalMapper.deleteWithdrawalById(id);
			}
		}
	}

	public int count(WithdrawalQuery query) {
		query.ensureInitialized();
		return withdrawalMapper.getWithdrawalCount(query);
	}

	public List<Withdrawal> list(WithdrawalQuery query) {
		query.ensureInitialized();
		List<Withdrawal> list = withdrawalMapper.getWithdrawals(query);
		return list;
	}

	public int getWithdrawalCountByQueryCriteria(WithdrawalQuery query) {
		return withdrawalMapper.getWithdrawalCount(query);
	}

	public List<Withdrawal> getWithdrawalsByQueryCriteria(int start,
			int pageSize, WithdrawalQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Withdrawal> rows = sqlSessionTemplate.selectList("getWithdrawals",
				query, rowBounds);
		return rows;
	}

	public Withdrawal getWithdrawal(Long id) {
		if (id == null) {
			return null;
		}
		Withdrawal withdrawal = withdrawalMapper.getWithdrawalById(id);
		return withdrawal;
	}

	@Transactional
	public void save(Withdrawal withdrawal) {
		if (withdrawal.getWithdrawalid() == null) {
			withdrawal.setWithdrawalid(idGenerator.nextId("Withdrawal"));
			// withdrawal.setCreateDate(new Date());
			// withdrawal.setDeleteFlag(0);
			withdrawalMapper.insertWithdrawal(withdrawal);
		} else {
			withdrawalMapper.updateWithdrawal(withdrawal);
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
	public void setWithdrawalMapper(WithdrawalMapper withdrawalMapper) {
		this.withdrawalMapper = withdrawalMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public int getWithdrawalApproveCountByQueryCriteria(WithdrawalQuery query) {

		return withdrawalMapper.getWithdrawalApproveCount(query);
	}

	public List<Withdrawal> getWithdrawalsApproveByQueryCriteria(int start,
			int pageSize, WithdrawalQuery query) {

		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Withdrawal> rows = sqlSessionTemplate.selectList(
				"getWithdrawalsApprove", query, rowBounds);

		return rows;
	}

}
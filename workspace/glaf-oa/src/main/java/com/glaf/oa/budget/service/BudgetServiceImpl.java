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
package com.glaf.oa.budget.service;

import java.util.List;



import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.budget.mapper.BudgetMapper;
import com.glaf.oa.budget.model.Budget;
import com.glaf.oa.budget.query.BudgetQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("budgetService")
@Transactional(readOnly = true)
public class BudgetServiceImpl implements BudgetService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected BudgetMapper budgetMapper;

	public BudgetServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			budgetMapper.deleteBudgetById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> budgetids) {
		if (budgetids != null && !budgetids.isEmpty()) {
			for (Long id : budgetids) {
				budgetMapper.deleteBudgetById(id);
			}
		}
	}

	public int count(BudgetQuery query) {
		query.ensureInitialized();
		return budgetMapper.getBudgetCount(query);
	}

	public List<Budget> list(BudgetQuery query) {
		query.ensureInitialized();
		List<Budget> list = budgetMapper.getBudgets(query);
		return list;
	}

	public int getBudgetCountByQueryCriteria(BudgetQuery query) {
		return budgetMapper.getBudgetCount(query);
	}

	public List<Budget> getBudgetsByQueryCriteria(int start, int pageSize,
			BudgetQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Budget> rows = sqlSessionTemplate.selectList("getBudgets", query,
				rowBounds);
		return rows;
	}

	public Budget getBudget(Long id) {
		if (id == null) {
			return null;
		}
		Budget budget = budgetMapper.getBudgetById(id);
		return budget;
	}

	@Transactional
	public void save(Budget budget) {
		if (budget.getBudgetid() == null) {
			budget.setBudgetid(idGenerator.nextId("oa_budget"));
			// budget.setCreateDate(new Date());
			// budget.setDeleteFlag(0);
			budgetMapper.insertBudget(budget);
		} else {
			budgetMapper.updateBudget(budget);
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
	public void setBudgetMapper(BudgetMapper budgetMapper) {
		this.budgetMapper = budgetMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int getReviewBudgetCountByQueryCriteria(BudgetQuery query) {
		return budgetMapper.getReviewBudgetCount(query);
	}

	@Override
	public List<Budget> getReviewBudgetsByQueryCriteria(int start,
			int pageSize, BudgetQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Budget> rows = sqlSessionTemplate.selectList("getReviewBudgets",
				query, rowBounds);
		return rows;
	}

}
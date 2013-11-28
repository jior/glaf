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
package com.glaf.oa.paymentplan.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.oa.paymentplan.mapper.*;
import com.glaf.oa.paymentplan.model.*;
import com.glaf.oa.paymentplan.query.*;

@Service("paymentplanService")
@Transactional(readOnly = true)
public class PaymentplanServiceImpl implements PaymentplanService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected PaymentplanMapper paymentplanMapper;

	public PaymentplanServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			paymentplanMapper.deletePaymentplanById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> planids) {
		if (planids != null && !planids.isEmpty()) {
			for (Long id : planids) {
				paymentplanMapper.deletePaymentplanById(id);
			}
		}
	}

	public int count(PaymentplanQuery query) {
		query.ensureInitialized();
		return paymentplanMapper.getPaymentplanCount(query);
	}

	public List<Paymentplan> list(PaymentplanQuery query) {
		query.ensureInitialized();
		List<Paymentplan> list = paymentplanMapper.getPaymentplans(query);
		return list;
	}

	public int getPaymentplanCountByQueryCriteria(PaymentplanQuery query) {
		return paymentplanMapper.getPaymentplanCount(query);
	}

	public List<Paymentplan> getPaymentplansByQueryCriteria(int start,
			int pageSize, PaymentplanQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Paymentplan> rows = sqlSessionTemplate.selectList(
				"getPaymentplans", query, rowBounds);
		return rows;
	}

	public Paymentplan getPaymentplan(Long id) {
		if (id == null) {
			return null;
		}
		Paymentplan paymentplan = paymentplanMapper.getPaymentplanById(id);
		return paymentplan;
	}

	@Transactional
	public void save(Paymentplan paymentplan) {
		if (paymentplan.getPlanid() == null) {
			paymentplan.setPlanid(idGenerator.nextId("oa_paymentplan"));
			// paymentplan.setCreateDate(new Date());
			// paymentplan.setDeleteFlag(0);
			paymentplanMapper.insertPaymentplan(paymentplan);
		} else {
			paymentplanMapper.updatePaymentplan(paymentplan);
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
	public void setPaymentplanMapper(PaymentplanMapper paymentplanMapper) {
		this.paymentplanMapper = paymentplanMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
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
package com.glaf.oa.payment.service;

import java.util.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.oa.payment.mapper.*;
import com.glaf.oa.payment.model.*;
import com.glaf.oa.payment.query.*;

@Service("paymentService")
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected PaymentMapper paymentMapper;

	public PaymentServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			paymentMapper.deletePaymentById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> paymentids) {
		if (paymentids != null && !paymentids.isEmpty()) {
			for (Long id : paymentids) {
				paymentMapper.deletePaymentById(id);
			}
		}
	}

	public int count(PaymentQuery query) {
		query.ensureInitialized();
		return paymentMapper.getPaymentCount(query);
	}

	public List<Payment> list(PaymentQuery query) {
		query.ensureInitialized();
		List<Payment> list = paymentMapper.getPayments(query);
		return list;
	}

	public int getPaymentCountByQueryCriteria(PaymentQuery query) {
		return paymentMapper.getPaymentCount(query);
	}

	public List<Payment> getPaymentsByQueryCriteria(int start, int pageSize,
			PaymentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Payment> rows = sqlSessionTemplate.selectList("getPayments",
				query, rowBounds);
		return rows;
	}

	public Payment getPayment(Long id) {
		if (id == null) {
			return null;
		}
		Payment payment = paymentMapper.getPaymentById(id);
		return payment;
	}

	@Transactional
	public void save(Payment payment) {
		if (payment.getPaymentid() == null) {
			payment.setPaymentid(idGenerator.nextId("oa_payment"));
			// payment.setCreateDate(new Date());
			// payment.setDeleteFlag(0);
			paymentMapper.insertPayment(payment);
		} else {
			paymentMapper.updatePayment(payment);
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
	public void setPaymentMapper(PaymentMapper paymentMapper) {
		this.paymentMapper = paymentMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int getReviewPaymentCountByQueryCriteria(PaymentQuery query) {

		return paymentMapper.getReviewPaymentCount(query);
	}

	@Override
	public List<Payment> getReviewPaymentsByQueryCriteria(int start,
			int pageSize, PaymentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Payment> rows = sqlSessionTemplate.selectList("getReviewPayments",
				query, rowBounds);
		return rows;
	}

}
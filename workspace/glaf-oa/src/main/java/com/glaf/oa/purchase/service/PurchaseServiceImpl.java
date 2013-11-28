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
package com.glaf.oa.purchase.service;

import java.util.List;



import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.purchase.mapper.PurchaseMapper;
import com.glaf.oa.purchase.model.Purchase;
import com.glaf.oa.purchase.query.PurchaseQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.ProcessContext;

@Service("purchaseService")
@Transactional(readOnly = true)
public class PurchaseServiceImpl implements PurchaseService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected PurchaseMapper purchaseMapper;

	protected PurchaseitemService purchaseitemService;

	public PurchaseServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			purchaseMapper.deletePurchaseById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> purchaseids) {
		if (purchaseids != null && !purchaseids.isEmpty()) {
			for (Long id : purchaseids) {
				purchaseMapper.deletePurchaseById(id);
			}
		}
	}

	public int count(PurchaseQuery query) {
		query.ensureInitialized();
		return purchaseMapper.getPurchaseCount(query);
	}

	public List<Purchase> list(PurchaseQuery query) {
		query.ensureInitialized();
		List<Purchase> list = purchaseMapper.getPurchases(query);
		return list;
	}

	public int getPurchaseCountByQueryCriteria(PurchaseQuery query) {
		return purchaseMapper.getPurchaseCount(query);
	}

	public List<Purchase> getPurchasesByQueryCriteria(int start, int pageSize,
			PurchaseQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Purchase> rows = sqlSessionTemplate.selectList("getPurchases",
				query, rowBounds);
		return rows;
	}

	public Purchase getPurchase(Long id) {
		if (id == null) {
			return null;
		}
		Purchase purchase = purchaseMapper.getPurchaseById(id);
		return purchase;
	}

	@Transactional
	public void save(Purchase purchase) {
		if (purchase.getPurchaseid() == null) {
			purchase.setPurchaseid(idGenerator.nextId("oa_purchase"));
			// purchase.setCreateDate(new Date());
			// purchase.setDeleteFlag(0);
			purchaseMapper.insertPurchase(purchase);
		} else {
			purchaseMapper.updatePurchase(purchase);
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
	public void setPurchaseMapper(PurchaseMapper purchaseMapper) {
		this.purchaseMapper = purchaseMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setPurchaseitemService(PurchaseitemService purchaseitemService) {
		this.purchaseitemService = purchaseitemService;
	}

	@Transactional
	public void delete(List<Long> purchaseids) {

		if (purchaseids != null && !purchaseids.isEmpty()) {
			for (Long id : purchaseids) {
				Purchase purchase = purchaseMapper.getPurchaseById(id);
				if (purchase.getProcessinstanceid() != null) {
					ProcessContext ctx = new ProcessContext();
					ctx.setProcessInstanceId(purchase.getProcessinstanceid());
					ProcessContainer.getContainer().abortProcess(ctx);
				}

				purchaseitemService.deleteByParentId(id);// 先删除明细

				purchaseMapper.deletePurchaseById(id);// 删除主表
			}
		}

	}

	@Transactional
	public void submit(List<Long> purchaseids) {
		if (purchaseids != null && !purchaseids.isEmpty()) {
			for (Long id : purchaseids) {
				purchaseMapper.submitById(id);// 提交
			}
		}
	}

	public int getPurchaseApproveCountByQueryCriteria(PurchaseQuery query) {
		return purchaseMapper.getPurchaseApproveCount(query);
	}

	public List<Purchase> getPurchasesApproveByQueryCriteria(int start,
			int pageSize, PurchaseQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Purchase> rows = sqlSessionTemplate.selectList(
				"getPurchasesApprove", query, rowBounds);
		return rows;
	}

}
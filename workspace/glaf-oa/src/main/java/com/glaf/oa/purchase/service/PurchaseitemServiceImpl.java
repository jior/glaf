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
import com.glaf.oa.purchase.mapper.PurchaseitemMapper;
import com.glaf.oa.purchase.model.Purchase;
import com.glaf.oa.purchase.model.Purchaseitem;
import com.glaf.oa.purchase.query.PurchaseitemQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("purchaseitemService")
@Transactional(readOnly = true)
public class PurchaseitemServiceImpl implements PurchaseitemService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected PurchaseitemMapper purchaseitemMapper;

	protected PurchaseMapper purchaseMapper;

	public PurchaseitemServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			purchaseitemMapper.deletePurchaseitemById(id);
		}
	}

	@Transactional
	public void deleteById(Long id, Long parentId) {
		if (id != null) {
			purchaseitemMapper.deletePurchaseitemById(id);
		}
		this.updateSumPrice(parentId);
	}

	@Transactional
	public void deleteByIds(List<Long> purchaseitemids) {
		if (purchaseitemids != null && !purchaseitemids.isEmpty()) {
			for (Long id : purchaseitemids) {
				purchaseitemMapper.deletePurchaseitemById(id);
			}
		}
	}

	public int count(PurchaseitemQuery query) {
		query.ensureInitialized();
		return purchaseitemMapper.getPurchaseitemCount(query);
	}

	public List<Purchaseitem> list(PurchaseitemQuery query) {
		query.ensureInitialized();
		List<Purchaseitem> list = purchaseitemMapper.getPurchaseitems(query);
		return list;
	}

	public int getPurchaseitemCountByQueryCriteria(PurchaseitemQuery query) {
		return purchaseitemMapper.getPurchaseitemCount(query);
	}

	public List<Purchaseitem> getPurchaseitemsByQueryCriteria(int start,
			int pageSize, PurchaseitemQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Purchaseitem> rows = sqlSessionTemplate.selectList(
				"getPurchaseitems", query, rowBounds);
		return rows;
	}

	public Purchaseitem getPurchaseitem(Long id) {
		if (id == null) {
			return null;
		}
		Purchaseitem purchaseitem = purchaseitemMapper.getPurchaseitemById(id);
		return purchaseitem;
	}

	@Transactional
	public void save(Purchaseitem purchaseitem) {
		if (purchaseitem.getPurchaseitemid() == null) {
			purchaseitem.setPurchaseitemid(idGenerator
					.nextId("oa_purchaseitem"));
			// purchaseitem.setCreateDate(new Date());
			// purchaseitem.setDeleteFlag(0);
			purchaseitemMapper.insertPurchaseitem(purchaseitem);
		} else {
			purchaseitemMapper.updatePurchaseitem(purchaseitem);
		}

		this.updateSumPrice(purchaseitem.getPurchaseid());
	}

	/**
	 * 更新价格
	 * 
	 * @param purchaseid
	 */
	@Transactional
	public void updateSumPrice(Long purchaseid) {
		List<Purchaseitem> list = purchaseitemMapper
				.getPurchaseitemByParentId(purchaseid);
		double sumPrice = 0D;
		for (Purchaseitem purchaseitem2 : list) {
			sumPrice += purchaseitem2.getQuantity()
					* purchaseitem2.getReferenceprice();
		}
		Purchase purchase = new Purchase();
		purchase.setPurchaseid(purchaseid);
		purchase.setPurchasesum(sumPrice);
		purchaseMapper.updatePurchase(purchase);
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
	public void setPurchaseitemMapper(PurchaseitemMapper purchaseitemMapper) {
		this.purchaseitemMapper = purchaseitemMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Transactional
	public void deleteByParentId(Long longValue) {
		purchaseitemMapper.deleteByParentId(longValue);

	}

	public List<Purchaseitem> getPurchaseitemByParentId(Long longValue) {
		List<Purchaseitem> list = purchaseitemMapper
				.getPurchaseitemByParentId(longValue);
		return list;
	}

}
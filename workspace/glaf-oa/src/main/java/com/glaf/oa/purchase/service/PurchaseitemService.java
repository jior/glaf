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

import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.purchase.model.Purchaseitem;
import com.glaf.oa.purchase.query.PurchaseitemQuery;

@Transactional(readOnly = true)
public interface PurchaseitemService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id, Long parentId);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> purchaseitemids);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<Purchaseitem> list(PurchaseitemQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getPurchaseitemCountByQueryCriteria(PurchaseitemQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Purchaseitem> getPurchaseitemsByQueryCriteria(int start, int pageSize,
			PurchaseitemQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	Purchaseitem getPurchaseitem(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(Purchaseitem purchaseitem);

	/**
	 * 根据主表 ID 删除所有信息
	 * 
	 * @param longValue
	 */
	void deleteByParentId(Long longValue);

	/**
	 * 根据主表 ID 删除所有信息
	 * 
	 * @param longValue
	 */
	List<Purchaseitem> getPurchaseitemByParentId(Long longValue);

}
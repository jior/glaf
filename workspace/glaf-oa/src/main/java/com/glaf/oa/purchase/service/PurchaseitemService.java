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
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id, Long parentId);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> purchaseitemids);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Purchaseitem> list(PurchaseitemQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getPurchaseitemCountByQueryCriteria(PurchaseitemQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Purchaseitem> getPurchaseitemsByQueryCriteria(int start, int pageSize,
			PurchaseitemQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Purchaseitem getPurchaseitem(Long id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Purchaseitem purchaseitem);

	/**
	 * �������� ID ɾ��������Ϣ
	 * 
	 * @param longValue
	 */
	void deleteByParentId(Long longValue);

	/**
	 * �������� ID ɾ��������Ϣ
	 * 
	 * @param longValue
	 */
	List<Purchaseitem> getPurchaseitemByParentId(Long longValue);

}
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
package com.glaf.oa.borrow.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.borrow.model.Borrowadderss;
import com.glaf.oa.borrow.query.BorrowadderssQuery;

@Transactional(readOnly = true)
public interface BorrowadderssService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	@Transactional
	void deleteByParentId(Long id);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> addressids);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Borrowadderss> list(BorrowadderssQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getBorrowadderssCountByQueryCriteria(BorrowadderssQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Borrowadderss> getBorrowaddersssByQueryCriteria(int start,
			int pageSize, BorrowadderssQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Borrowadderss getBorrowadderss(Long id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Borrowadderss borrowadderss);

	List<Borrowadderss> getBorrowadderssByParentId(Long id);
}
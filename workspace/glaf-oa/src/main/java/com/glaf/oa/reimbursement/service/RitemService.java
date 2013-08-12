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
package com.glaf.oa.reimbursement.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.reimbursement.model.Ritem;
import com.glaf.oa.reimbursement.query.RitemQuery;

@Transactional(readOnly = true)
public interface RitemService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	@Transactional
	void deleteById(Long id, Long reimbursementid);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> ritemids);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Ritem> list(RitemQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getRitemCountByQueryCriteria(RitemQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Ritem> getRitemsByQueryCriteria(int start, int pageSize,
			RitemQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Ritem getRitem(Long id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Ritem ritem);

	List<Ritem> getRitemByParentId(Long reimbursementid);
}
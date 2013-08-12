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
package com.glaf.oa.contract.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.contract.model.*;
import com.glaf.oa.contract.query.*;

@Transactional(readOnly = true)
public interface ContractService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> ids);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Contract> list(ContractQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getContractCountByQueryCriteria(ContractQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Contract> getContractsByQueryCriteria(int start, int pageSize,
			ContractQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Contract getContract(Long id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Contract contract);

	/**
	 * ��ȡ��Ҫ��������ͬ
	 * 
	 * @return
	 */
	List<Contract> getReviewContractsByQueryCriteria(int start, int limit,
			ContractQuery query);

	/**
	 * ��ȡ��Ҫ��������ͬ������
	 * 
	 * @return
	 */
	int getReviewContractCountByQueryCriteria(ContractQuery query);

}
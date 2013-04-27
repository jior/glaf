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

package com.glaf.core.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.domain.*;
import com.glaf.core.query.*;

@Transactional(readOnly = true)
public interface MembershipService {

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
	void deleteByIds(List<Long> rowIds);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Membership> list(MembershipQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getMembershipCountByQueryCriteria(MembershipQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Membership> getMembershipsByQueryCriteria(int start, int pageSize,
			MembershipQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Membership getMembership(Long id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Membership membership);

	/**
	 * �����Ա��ϵ
	 * 
	 * @param nodeId
	 *            �ڵ���
	 * @param type
	 *            ����
	 * @param memberships
	 *            ��Ա����
	 */
	@Transactional
	void saveMemberships(Long nodeId, String type, List<Membership> memberships);

	/**
	 * �����Ա��ϵ
	 * 
	 * @param nodeId
	 *            �ڵ���
	 * @param roleId
	 *            ��ɫ���
	 * @param type
	 *            ����
	 * @param memberships
	 *            ��Ա����
	 */
	@Transactional
	void saveMemberships(Long nodeId, Long roleId, String type,
			List<Membership> memberships);

}

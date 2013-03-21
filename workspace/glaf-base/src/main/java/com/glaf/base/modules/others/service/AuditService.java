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

package com.glaf.base.modules.others.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.others.model.Audit;
import com.glaf.base.modules.sys.model.SysUser;

@Transactional(readOnly = true)
public interface AuditService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	@Transactional
	boolean create(Audit bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	@Transactional
	boolean update(Audit bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	@Transactional
	boolean delete(Audit bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	@Transactional
	boolean delete(long id);

	/**
	 * ����ɾ��
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	boolean deleteAll(long[] ids);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	Audit findById(long id);

	/**
	 * �������������б���type
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List<Audit> getAuditList(long referId, int referType);

	/**
	 * ���زɹ���������������б�(������������˵����ᣬ��ֹ)
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List<Audit> getAuditList(long referId, String referTypes);

	/**
	 * �������������б������������б�ȥ���ظ���
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List<Audit> getAuditUserList(long referId, int referType);

	/**
	 * ���ز������һ�ε�������¼
	 * 
	 * @param referId
	 * @param referType
	 * @param leaderId
	 * @return
	 */
	List<Audit> getAuditDeptList(long referId, int referType, long deptId);

	/**
	 * ����������¼
	 * 
	 * @param user
	 * @param referId
	 * @param referType
	 * @param confirm
	 * @return
	 */
	@Transactional
	boolean saveAudit(SysUser user, long referId, int referType, boolean confirm);

	/**
	 * �������һ����ͨ����������¼
	 * 
	 * @param referId
	 * @param referType
	 * @param leaderId
	 * @return
	 */
	List<Audit> getAuditNotList(long referId);
}
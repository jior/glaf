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
	 * 保存
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	@Transactional
	boolean create(Audit bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	@Transactional
	boolean update(Audit bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            Audit
	 * @return boolean
	 */
	@Transactional
	boolean delete(Audit bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	@Transactional
	boolean delete(long id);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	boolean deleteAll(long[] ids);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	Audit findById(long id);

	/**
	 * 返回所有审批列表，分type
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List<Audit> getAuditList(long referId, int referType);

	/**
	 * 返回采购申请的所有审批列表(新增，变更，退单重提，废止)
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List<Audit> getAuditList(long referId, String referTypes);

	/**
	 * 返回所有审批列表最终审批人列表（去除重复）
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List<Audit> getAuditUserList(long referId, int referType);

	/**
	 * 返回部门最后一次的审批记录
	 * 
	 * @param referId
	 * @param referType
	 * @param leaderId
	 * @return
	 */
	List<Audit> getAuditDeptList(long referId, int referType, long deptId);

	/**
	 * 创建审批记录
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
	 * 返回最后一个不通过的审批记录
	 * 
	 * @param referId
	 * @param referType
	 * @param leaderId
	 * @return
	 */
	List<Audit> getAuditNotList(long referId);
}
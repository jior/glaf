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

package com.glaf.base.modules.sys.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.core.base.TreeModel;

@Transactional(readOnly = true)
public interface ComplexUserService {

	/**
	 * 创建用户
	 * 
	 * @param user
	 * @param roleCodes
	 * @return
	 */
	@Transactional
	boolean createUser(SysUser user, List<String> roleCodes);

	/**
	 * 获取用户管理的分支机构(sys_tree的封装)
	 * 
	 * @param actorId
	 * @return
	 */
	List<TreeModel> getUserManageBranch(String actorId);
	
	/**
	 * 获取用户管理的分支机构(SYS_DEPARTMENT的封装)
	 * 
	 * @param actorId
	 * @return
	 */
	List<SysDepartment> getUserManageDeptments(String actorId);

	/**
	 * 获取用户管理的分支机构的编号集合(sys_tree的id封装)
	 * 
	 * @param actorId
	 * @return
	 */
	List<Long> getUserManageBranchNodeIds(String actorId);

}

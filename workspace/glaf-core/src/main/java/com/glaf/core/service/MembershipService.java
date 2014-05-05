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
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> rowIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<Membership> list(MembershipQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getMembershipCountByQueryCriteria(MembershipQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Membership> getMembershipsByQueryCriteria(int start, int pageSize,
			MembershipQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	Membership getMembership(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(Membership membership);

	/**
	 * 保存成员关系
	 * 
	 * @param actorId
	 *            用户编号
	 * @param type
	 *            类型
	 * @param nodeIds
	 *            节点集合
	 */
	@Transactional
	void saveMemberships(String actorId, String type, List<Long> nodeIds);

	/**
	 * 保存成员关系
	 * 
	 * @param nodeId
	 *            节点编号
	 * @param type
	 *            类型
	 * @param memberships
	 *            成员集合
	 */
	@Transactional
	void saveMemberships(Long nodeId, String type, List<Membership> memberships);

	/**
	 * 保存成员关系
	 * 
	 * @param nodeId
	 *            节点编号
	 * @param roleId
	 *            角色编号
	 * @param type
	 *            类型
	 * @param memberships
	 *            成员集合
	 */
	@Transactional
	void saveMemberships(Long nodeId, Long roleId, String type,
			List<Membership> memberships);

}

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

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.core.util.PageResult;

@Transactional(readOnly = true)
public interface GroupService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> rowIds);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	Group getGroup(String id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getGroupCountByQueryCriteria(GroupQuery query);

	/**
	 * 获取一页群组
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getGroupList(String type, String createBy, int pageNo,
			int pageSize);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Group> getGroupsByQueryCriteria(int start, int pageSize,
			GroupQuery query);

	/**
	 * 通过用户账号获取群组
	 * 
	 * @param userId
	 * @return
	 */
	List<Group> getGroupsByUserId(String userId);

	/**
	 * 通过用户账号及组类型获取群组
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	List<Group> getGroupsByUserIdAndType(String userId, String type);

	/**
	 * 获取某个群组的用户
	 * 
	 * @param groupId
	 * @return
	 */
	List<String> getUserIdsByGroupId(String groupId);
	
	/**
	 * 根据群组名称及类型获取群组用户
	 * @param groupName
	 * @param groupType
	 * @return
	 */
	List<String> getUserIdsByGroupNameAndType(String groupName, String groupType);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<Group> list(GroupQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(Group group);

	/**
	 * 保存群组用户
	 * 
	 * @param groupId
	 * @param userIds
	 */
	@Transactional
	void saveGroupUsers(String groupId, Set<String> userIds);

	/**
	 * 排序
	 * 
	 * @param bean
	 *            Group
	 * @param operate
	 *            int 操作
	 */
	@Transactional
	void sort(Group bean, int operate);

}

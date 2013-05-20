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
package com.glaf.base.modules.sys.service.mybatis;

import java.util.*;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.UUID32;
import com.glaf.core.dao.*;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.mapper.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.GroupService;

@Service("groupService")
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {
	protected EntityDAO entityDAO;

	protected GroupMapper groupMapper;

	protected GroupUserMapper groupUserMapper;

	protected IdGenerator idGenerator;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected SqlSessionTemplate sqlSessionTemplate;

	public GroupServiceImpl() {

	}

	public int count(GroupQuery query) {
		query.ensureInitialized();
		return groupMapper.getGroupCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			groupMapper.deleteGroupById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			GroupQuery query = new GroupQuery();
			query.rowIds(rowIds);
			groupMapper.deleteGroups(query);
		}
	}

	public Group getGroup(String id) {
		if (id == null) {
			return null;
		}
		Group group = groupMapper.getGroupById(id);
		return group;
	}

	public int getGroupCountByQueryCriteria(GroupQuery query) {
		return groupMapper.getGroupCount(query);
	}

	public PageResult getGroupList(String type, String createBy, int pageNo,
			int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		GroupQuery query = new GroupQuery();
		query.setType(type);
		query.setCreateBy(createBy);

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT desc");

		int start = pageSize * (pageNo - 1);
		List<Group> list = this
				.getGroupsByQueryCriteria(start, pageSize, query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<Group> getGroupsByQueryCriteria(int start, int pageSize,
			GroupQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Group> rows = sqlSessionTemplate.selectList("getGroups", query,
				rowBounds);
		return rows;
	}

	/**
	 * 通过用户账号获取群组
	 * 
	 * @param userId
	 * @return
	 */
	public List<Group> getGroupsByUserId(String userId) {
		return groupMapper.getGroupsByUserId(userId);
	}

	/**
	 * 通过用户账号及组类型获取群组
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<Group> getGroupsByUserIdAndType(String userId, String type) {
		GroupQuery query = new GroupQuery();
		query.setUserId(userId);
		query.setType(type);
		return groupMapper.getGroupsByUserIdAndType(query);
	}

	public List<String> getUserIdsByGroupId(String groupId) {
		return groupUserMapper.getUserIdsByGroupId(groupId);
	}

	/**
	 * 根据群组名称及类型获取群组用户
	 * 
	 * @param groupName
	 * @param groupType
	 * @return
	 */
	public List<String> getUserIdsByGroupNameAndType(String groupName,
			String groupType) {
		GroupQuery query = new GroupQuery();
		query.name(groupName);
		query.type(groupType);
		List<Group> list = groupMapper.getGroups(query);
		if (list != null && list.isEmpty()) {
			Group group = list.get(0);
			return this.getUserIdsByGroupId(group.getGroupId());
		}
		return null;
	}

	public List<Group> list(GroupQuery query) {
		List<Group> list = groupMapper.getGroups(query);
		return list;
	}

	@Transactional
	public void save(Group group) {
		if (StringUtils.isEmpty(group.getGroupId())) {
			group.setGroupId(UUID32.getUUID());
			group.setSort(1);
			group.setCreateDate(new Date());
			groupMapper.insertGroup(group);
		} else {
			if (groupMapper.getGroupById(group.getGroupId()) == null) {
				group.setCreateDate(new Date());
				groupMapper.insertGroup(group);
			} else {
				group.setUpdateDate(new Date());
				groupMapper.updateGroup(group);
			}
		}
	}

	/**
	 * 保存群组用户
	 * 
	 * @param groupId
	 * @param userIds
	 */
	@Transactional
	public void saveGroupUsers(String groupId, Set<String> userIds) {
		groupUserMapper.deleteGroupUsersByGroupId(groupId);
		if (userIds != null && !userIds.isEmpty()) {
			for (String userId : userIds) {
				GroupUser gu = new GroupUser();
				gu.setGroupId(groupId);
				gu.setUserId(userId);
				groupUserMapper.insertGroupUser(gu);
			}
		}
	}

	@Resource(name = "myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Resource
	public void setGroupMapper(GroupMapper groupMapper) {
		this.groupMapper = groupMapper;
	}

	@Resource
	public void setGroupUserMapper(GroupUserMapper groupUserMapper) {
		this.groupUserMapper = groupUserMapper;
	}

	@Resource(name = "myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	/**
	 * 排序
	 * 
	 * @param bean
	 *            Group
	 * @param operate
	 *            int 操作
	 */
	@Transactional
	public void sort(Group bean, int operate) {
		if (bean == null)
			return;
		if (operate == SysConstants.SORT_PREVIOUS) {// 前移
			sortByPrevious(bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// 后移
			sortByForward(bean);
		}
	}

	/**
	 * 向后移动排序
	 * 
	 * @param bean
	 */
	private void sortByForward(Group bean) {
		GroupQuery query = new GroupQuery();
		query.setSortLessThan(bean.getSort());
		query.setOrderBy(" E.SORT desc ");
		List<Group> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			Group temp = (Group) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(Group bean) {
		GroupQuery query = new GroupQuery();
		query.setSortGreaterThan(bean.getSort());
		// 查找前一个对象
		List<Group> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			Group temp = (Group) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	@Transactional
	public void update(Group group) {
		group.setUpdateDate(new Date());
		groupMapper.updateGroup(group);
	}

}

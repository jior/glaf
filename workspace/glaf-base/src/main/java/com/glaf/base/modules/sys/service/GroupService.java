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
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> rowIds);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Group> list(GroupQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getGroupCountByQueryCriteria(GroupQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Group> getGroupsByQueryCriteria(int start, int pageSize,
			GroupQuery query);

	/**
	 * ��ȡһҳȺ��
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getGroupList(String type, String createBy, int pageNo,
			int pageSize);

	List<String> getUserIdsByGroupId(String groupId);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Group getGroup(String id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Group group);

	/**
	 * ����
	 * 
	 * @param bean
	 *            Group
	 * @param operate
	 *            int ����
	 */
	@Transactional
	void sort(Group bean, int operate);

}

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
package com.glaf.oa.leave.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.leave.model.*;
import com.glaf.oa.leave.query.*;

@Transactional(readOnly = true)
public interface LeaveService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long leaveid);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> leaveids);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Leave> list(LeaveQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getLeaveCountByQueryCriteria(LeaveQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Leave> getLeavesByQueryCriteria(int start, int pageSize,
			LeaveQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Leave getLeave(Long leaveid);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Leave leave);

	int getReviewLeaveCountByQueryCriteria(LeaveQuery query);

	List<Leave> getReviewLeavesByQueryCriteria(int start, int pageSize,
			LeaveQuery query);

}
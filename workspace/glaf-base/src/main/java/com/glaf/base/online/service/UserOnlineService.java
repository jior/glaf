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

package com.glaf.base.online.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.online.domain.*;
import com.glaf.base.online.query.*;

@Transactional(readOnly = true)
public interface UserOnlineService {

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
	 * ɾ����ʱ�������û�
	 * @param timeoutSeconds
	 */
	@Transactional
	void deleteTimeoutUsers(int timeoutSeconds);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<UserOnline> list(UserOnlineQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getUserOnlineCountByQueryCriteria(UserOnlineQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<UserOnline> getUserOnlinesByQueryCriteria(int start, int pageSize,
			UserOnlineQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	UserOnline getUserOnline(String actorId);

	/**
	 * ��¼�����û�
	 * @param model
	 */
	@Transactional
	void login(UserOnline model);
	
	/**
	 * ���������û�
	 * @param actorId
	 */
	@Transactional
	void remain(String actorId);

	/**
	 * �˳�ϵͳ
	 * 
	 * @param actorId
	 */
	@Transactional
	void logout(String actorId);

}

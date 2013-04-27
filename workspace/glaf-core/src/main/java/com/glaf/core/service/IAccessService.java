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

import java.util.List;

import com.glaf.core.base.AccessEntry;
import com.glaf.core.base.Accessable;
import com.glaf.core.base.DataAccess;
import com.glaf.core.base.ModuleAccess;
import com.glaf.core.query.AccessEntryQuery;

public interface IAccessService {

	/**
	 * ��ȡ�����б�
	 * 
	 * @param serviceKey
	 * @param businessKey
	 * @return
	 */
	List<Accessable> getAccessableList(String serviceKey, String businessKey);

	/**
	 * ͨ��������ȡ����ʵ��
	 * 
	 * @param params
	 * @return
	 */
	List<AccessEntry> getAccessEntries(AccessEntryQuery query);

	/**
	 * ͨ���������ƻ�ȡ����ʵ��
	 * 
	 * @param processName
	 * @return
	 */
	List<AccessEntry> getAccessEntries(String processName);

	/**
	 * ��ȡ���ݷ��ʶ���
	 * 
	 * @param serviceKey
	 * @param dataInstanceId
	 * @return
	 */
	List<DataAccess> getDataAccesses(String serviceKey, String dataInstanceId);

	/**
	 * ͨ��Ӧ�����ƻ�ȡ����ʵ��
	 * 
	 * @param applicationName
	 * @return
	 */
	AccessEntry getLatestAccessEntry(String applicationName);

	/**
	 * ͨ���������Ƽ��������ƻ�ȡ����ʵ��
	 * 
	 * @param processName
	 * @param taskName
	 * @return
	 */
	AccessEntry getLatestAccessEntry(String processName, String taskName);

	/**
	 * ��ȡģ����ʶ���
	 * 
	 * @param serviceKey
	 * @return
	 */
	List<ModuleAccess> getModuleAccesses(String serviceKey);

	/**
	 * �������ʵ��
	 * 
	 * @param accessEntry
	 */

	void saveAccessEntry(AccessEntry accessEntry);

	/**
	 * ����������ʵ��
	 * 
	 * @param rows
	 */

	void saveAll(List<AccessEntry> rows);
}
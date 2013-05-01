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
public interface ISystemParamService {

	/**
	 * ���ݲ������崴������ʵ��
	 * 
	 * @param serviceKey
	 * @param businessKey
	 */
	@Transactional
	void createSystemParams(String serviceKey, String businessKey);

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> rowIds);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	SystemParam getSystemParam(String id);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getSystemParamCount(Map<String, Object> parameter);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getSystemParamCountByQueryCriteria(SystemParamQuery query);

	/**
	 * ���ݷ���������ȡ����
	 * 
	 * @param serviceKey
	 * @return
	 */
	List<InputDefinition> getInputDefinitions(String serviceKey);
	
	/**
	 * ���ݷ���������ȡ����
	 * 
	 * @param serviceKey
	 * @return
	 */
	List<InputDefinition> getInputDefinitions(String serviceKey,String typeCd);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<SystemParam> getSystemParams(Map<String, Object> parameter);

	/**
	 * ���ݷ���������ȡ����
	 * 
	 * @param serviceKey
	 * @param businessKey
	 * @return
	 */
	List<SystemParam> getSystemParams(String serviceKey, String businessKey);
	
	/**
	 * ��ȡϵͳ����
	 * @param serviceKey
	 * @param businessKey
	 * @param keyName
	 * @return
	 */
	SystemParam getSystemParam(String serviceKey, String businessKey, String keyName);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<SystemParam> getSystemParamsByQueryCriteria(int start, int pageSize,
			SystemParamQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<SystemParam> list(SystemParamQuery query);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(SystemParam systemParam);

	/**
	 * ���������¼
	 * 
	 * @param serviceKey
	 * @param businessKey
	 * @return
	 */
	@Transactional
	void saveAll(String serviceKey, String businessKey, List<SystemParam> rows);

	/**
	 * �޸Ķ�����������
	 * 
	 * @param serviceKey
	 * @param rows
	 */
	@Transactional
	void updateAll(String serviceKey, List<InputDefinition> rows);

}

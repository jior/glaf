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

import com.glaf.core.domain.*;
import com.glaf.core.query.*;

public interface IQueryDefinitionService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	void deleteById(String id);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	QueryDefinition getQueryDefinition(String queryId);

	/**
	 * �������ƻ�ȡ��ѯ����
	 * 
	 * @param serviceKey
	 * @return
	 */
	QueryDefinition getQueryDefinitionByName(String serviceKey);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	QueryDefinition getQueryDefinitionWithColumns(String queryId);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	QueryDefinition getQueryDefinitionWithParameters(String queryId);

	/**
	 * ��ȡĳ��Ŀ����ȫ����ѯ
	 * 
	 * @param targetTableName
	 * @return
	 */
	List<QueryDefinition> getQueryDefinitionByTableName(String targetTableName);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getQueryDefinitionCountByQueryCriteria(QueryDefinitionQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<QueryDefinition> getQueryDefinitionsByQueryCriteria(int start,
			int pageSize, QueryDefinitionQuery query);

	/**
	 * ��ȡĳ����ѯ��ȫ�����Ȳ�ѯ,��ѯ�ӽڵ�����ջ����ѯ���ڵ����ջ
	 * 
	 * @param queryId
	 * @return
	 */
	Stack<QueryDefinition> getQueryDefinitionStack(String queryId);

	/**
	 * ��ȡĳ����ѯ��ȫ�����Ȳ�ѯ
	 * 
	 * @param queryId
	 * @return
	 */
	QueryDefinition getQueryDefinitionWithAncestors(String queryId);

	/**
	 * �Ƿ����ӽڵ�
	 * 
	 * @param queryId
	 * @return
	 */
	boolean hasChildren(String queryId);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<QueryDefinition> list(QueryDefinitionQuery query);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	void save(QueryDefinition queryDefinition);

}
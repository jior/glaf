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

public interface ITableDefinitionService {

	/**
	 * ɾ���ж���
	 * 
	 * @param columnId
	 */
	void deleteColumn(String columnId);

	/**
	 * ɾ�����弰�����ѯ
	 * 
	 * @param tableName
	 */
	void deleteTable(String tableName);

	/**
	 * 
	 * @param columnId
	 * @return
	 */
	ColumnDefinition getColumnDefinition(String columnId);

	/**
	 * ���ݱ�����ȡ�����
	 * 
	 * @return
	 */
	TableDefinition getTableDefinition(String tableName);

	List<ColumnDefinition> getColumnDefinitionsByTableName(String tableName);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getTableDefinitionCountByQueryCriteria(TableDefinitionQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<TableDefinition> getTableDefinitionsByQueryCriteria(int start,
			int pageSize, TableDefinitionQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<TableDefinition> list(TableDefinitionQuery query);

	List<TableDefinition> getTableColumnsCount(TableDefinitionQuery query);

	/**
	 * ���������Ϣ
	 * 
	 * @return
	 */
	void save(TableDefinition tableDefinition);

	/**
	 * �����ֶ���Ϣ
	 * 
	 * @param tableName
	 * @param columnDefinition
	 */
	void saveColumn(String tableName, ColumnDefinition columnDefinition);

	/**
	 * ���涨��
	 * 
	 * @param tableName
	 * @param rows
	 */
	void saveSystemTable(String tableName, List<ColumnDefinition> rows);

}
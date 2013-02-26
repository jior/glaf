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

package com.glaf.dts.service;

import java.util.*;

import com.glaf.dts.domain.*;
import com.glaf.dts.query.*;

public interface ITransformTaskService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */

	void deleteById(String id);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */

	void deleteByIds(List<String> rowIds);

	void deleteByQueryId(String queryId);

	/**
	 * ɾ��ĳ���������
	 * 
	 * @param tableName
	 */
	void deleteByTableName(String tableName);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<TransformTask> list(TransformTaskQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getTransformTaskCountByQueryCriteria(TransformTaskQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<TransformTask> getTransformTasksByQueryCriteria(int start,
			int pageSize, TransformTaskQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	TransformTask getTransformTask(String id);

	/**
	 * ���������¼
	 * 
	 */

	void insertAll(String queryId, List<TransformTask> transformTasks);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */

	void save(TransformTask transformTask);

}
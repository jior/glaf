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

@Transactional(readOnly = true)
public interface DictoryDefinitionService {

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
	void deleteByIds(List<Long> rowIds);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<DictoryDefinition> list(DictoryDefinitionQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getDictoryDefinitionCountByQueryCriteria(DictoryDefinitionQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<DictoryDefinition> getDictoryDefinitionsByQueryCriteria(int start,
			int pageSize, DictoryDefinitionQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	DictoryDefinition getDictoryDefinition(Long id);

	/**
	 * ���ݽڵ��Ż�ȡ������Ϣ
	 * 
	 * @param nodeId
	 * @param target
	 * @return
	 */
	List<DictoryDefinition> getDictoryDefinitions(Long nodeId, String target);

	/**
	 * ���������¼
	 * 
	 * @return
	 */
	@Transactional
	void saveAll(Long nodeId, String target,
			List<DictoryDefinition> dictoryDefinitions);

}

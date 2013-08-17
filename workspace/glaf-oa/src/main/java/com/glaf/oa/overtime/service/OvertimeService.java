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
package com.glaf.oa.overtime.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.overtime.model.*;
import com.glaf.oa.overtime.query.*;

@Transactional(readOnly = true)
public interface OvertimeService {

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
	void deleteByIds(List<String> ids);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Overtime> list(OvertimeQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getOvertimeCountByQueryCriteria(OvertimeQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Overtime> getOvertimesByQueryCriteria(int start, int pageSize,
			OvertimeQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Overtime getOvertime(Long id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Overtime overtime);

	int getReviewOvertimeCountByQueryCriteria(OvertimeQuery query);

	List<Overtime> getReviewOvertimesByQueryCriteria(int start, int pageSize,
			OvertimeQuery query);

}
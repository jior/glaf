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
package com.glaf.oa.assesscontent.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.assesscontent.model.*;
import com.glaf.oa.assesscontent.query.*;
import com.glaf.oa.assessscore.query.AssessscoreQuery;

@Transactional(readOnly = true)
public interface AssesscontentService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByParentId(Long parentId);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> contentids);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Assesscontent> list(AssesscontentQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getAssesscontentCountByQueryCriteria(AssesscontentQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Assesscontent> getAssesscontentsByQueryCriteria(int start,
			int pageSize, AssesscontentQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Assesscontent getAssesscontent(Long id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Assesscontent assesscontent);

	List<AssesscontentAndScore> getAssesscontentAndScoreList(
			AssessscoreQuery scoreQuery);
}
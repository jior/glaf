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
package com.glaf.oa.assesssort.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.assessquestion.model.AssessortTree;
import com.glaf.oa.assesssort.model.Assesssort;
import com.glaf.oa.assesssort.model.AssesssortType;
import com.glaf.oa.assesssort.query.AssesssortQuery;
import com.glaf.base.modules.sys.model.BaseDataInfo;

@Transactional(readOnly = true)
public interface AssesssortService {

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
	void deleteByIds(List<Long> assesssortids);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Assesssort> list(AssesssortQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getAssesssortCountByQueryCriteria(AssesssortQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Assesssort> getAssesssortsByQueryCriteria(int start, int pageSize,
			AssesssortQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Assesssort getAssesssort(Long id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Assesssort assesssort);

	/**
	 * ������з�������
	 * 
	 * @return
	 */
	@Transactional
	List<AssesssortType> getAssesssortsType(String typeCode);

	/**
	 * ��ID����ϲ����Ϣ
	 * 
	 * @param typeCode
	 * @return
	 */
	@Transactional
	List<AssessortTree> getParentsInfoByDictId(Integer dictId);

	/**
	 * ��CODE���һ��ָ�����
	 * 
	 * @param typeCode
	 * @return
	 */
	List<BaseDataInfo> getAssessTypeByCode(String typeCode);

	List<BaseDataInfo> getAssessTypeById(long id);

	List<BaseDataInfo> getAssessTypeByStandardAndSortIds(
			AssesssortQuery sortQuery);

}
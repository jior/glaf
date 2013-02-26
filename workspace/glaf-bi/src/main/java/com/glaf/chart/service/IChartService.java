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

package com.glaf.chart.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.chart.domain.*;
import com.glaf.chart.query.*;

@Transactional(readOnly = true)
public interface IChartService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> rowIds);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Chart> list(ChartQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getChartCountByQueryCriteria(ChartQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Chart> getChartsByQueryCriteria(int start, int pageSize,
			ChartQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Chart getChart(String id);

	/**
	 * �������ƻ�ȡͼ����
	 * 
	 * @param name
	 * @return
	 */
	Chart getChartByName(String name);

	/**
	 * ��ȡͼ���岢ȡ��
	 * 
	 * @param id
	 * @param paramMap
	 * @return
	 */
	Chart getChartAndFetchDataById(String id, Map<String, Object> paramMap);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Chart chart);

}
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
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> rowIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<Chart> list(ChartQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getChartCountByQueryCriteria(ChartQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Chart> getChartsByQueryCriteria(int start, int pageSize,
			ChartQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	Chart getChart(String id);

	/**
	 * 根据名称获取图表定义
	 * 
	 * @param name
	 * @return
	 */
	Chart getChartByName(String name);

	/**
	 * 根据别名获取图表定义
	 * 
	 * @param mapping
	 * @return
	 */
	Chart getChartByMapping(String mapping);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(Chart chart);

}
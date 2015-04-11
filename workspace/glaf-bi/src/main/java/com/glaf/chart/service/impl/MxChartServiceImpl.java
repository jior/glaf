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

package com.glaf.chart.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.chart.domain.Chart;
import com.glaf.chart.mapper.ChartMapper;
import com.glaf.chart.query.ChartQuery;
import com.glaf.chart.service.IChartService;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.ColumnDefinitionMapper;
import com.glaf.core.mapper.QueryDefinitionMapper;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITablePageService;

@Service("chartService")
@Transactional(readOnly = true)
public class MxChartServiceImpl implements IChartService {
	protected final static Log logger = LogFactory
			.getLog(MxChartServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ChartMapper chartMapper;

	protected QueryDefinitionMapper queryDefinitionMapper;

	protected ColumnDefinitionMapper columnDefinitionMapper;

	protected IQueryDefinitionService queryDefinitionService;

	protected ITableDataService tableDataService;

	protected ITablePageService tablePageService;

	public MxChartServiceImpl() {

	}

	public int count(ChartQuery query) {
		query.ensureInitialized();
		return chartMapper.getChartCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			chartMapper.deleteChartById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			ChartQuery query = new ChartQuery();
			query.rowIds(rowIds);
			chartMapper.deleteCharts(query);
		}
	}

	public Chart getChart(String id) {
		if (id == null) {
			return null;
		}
		Chart chart = chartMapper.getChartById(id);
		return chart;
	}

	/**
	 * 根据名称获取图表定义
	 * 
	 * @param name
	 * @return
	 */
	public Chart getChartByName(String name) {
		Chart chart = null;
		ChartQuery query = new ChartQuery();
		query.chartName(name);
		List<Chart> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			chart = list.get(0);
		}
		return chart;
	}

	/**
	 * 根据别名获取图表定义
	 * 
	 * @param mapping
	 * @return
	 */
	public Chart getChartByMapping(String mapping) {
		Chart chart = null;
		if (StringUtils.isNotEmpty(mapping)) {
			ChartQuery query = new ChartQuery();
			query.setMapping(mapping);
			List<Chart> list = this.list(query);
			if (list != null && !list.isEmpty()) {
				chart = list.get(0);
			}
		}
		return chart;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getChartCountByQueryCriteria(ChartQuery query) {
		return chartMapper.getChartCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<Chart> getChartsByQueryCriteria(int start, int pageSize,
			ChartQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Chart> rows = sqlSessionTemplate.selectList("getCharts", query,
				rowBounds);
		return rows;
	}

	public List<Chart> list(ChartQuery query) {
		query.ensureInitialized();
		List<Chart> list = chartMapper.getCharts(query);
		return list;
	}

	@Transactional
	public void save(Chart chart) {
		if (StringUtils.isEmpty(chart.getId())) {
			chart.setId(idGenerator.getNextId());
			chart.setCreateDate(new Date());
			chartMapper.insertChart(chart);
		} else {
			chartMapper.updateChart(chart);
		}
		if (chart.getQuerySQL() != null) {
			QueryDefinition queryDefinition = new QueryDefinition();
			queryDefinition.setId(chart.getId());
			queryDefinition.setName(chart.getChartName());
			queryDefinition.setSql(chart.getQuerySQL());
			queryDefinition.setTitle(chart.getSubject());
			queryDefinition.setType("CHART");
			queryDefinitionService.save(queryDefinition);
		}
	}

	@javax.annotation.Resource
	public void setChartMapper(ChartMapper chartMapper) {
		this.chartMapper = chartMapper;
	}

	@javax.annotation.Resource
	public void setColumnDefinitionMapper(
			ColumnDefinitionMapper columnDefinitionMapper) {
		this.columnDefinitionMapper = columnDefinitionMapper;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setQueryDefinitionMapper(
			QueryDefinitionMapper queryDefinitionMapper) {
		this.queryDefinitionMapper = queryDefinitionMapper;
	}

	@javax.annotation.Resource
	public void setQueryDefinitionService(
			IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

}
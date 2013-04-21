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

package com.glaf.dts.web.springmvc;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.query.QueryDefinitionQuery;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;

@Controller("/dts/table")
@RequestMapping("/dts/table")
public class MxDtsTableController {

	protected static final Log logger = LogFactory
			.getLog(MxDtsTableController.class);

	protected IQueryDefinitionService queryDefinitionService;

	protected ITableDefinitionService tableDefinitionService;

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		String jx_view = request.getParameter("jx_view");
		RequestUtils.setRequestParameterToAttribute(request);

		String tableName = request.getParameter("tableName");
		if (StringUtils.isNotEmpty(tableName)) {
			TableDefinition table = tableDefinitionService
					.getTableDefinition(tableName);
			if (table != null && StringUtils.isNotEmpty(table.getQueryIds())) {
				QueryDefinitionQuery query = new QueryDefinitionQuery();
				List<String> queryIds = StringTools.split(table.getQueryIds());
				query.queryIds(queryIds);
				List<QueryDefinition> list = queryDefinitionService.list(query);
				StringBuffer sb01 = new StringBuffer();
				StringBuffer sb02 = new StringBuffer();
				for (QueryDefinition queryDefinition : list) {
					if (queryDefinition != null) {
						sb01.append(queryDefinition.getId()).append(",");
						sb02.append(queryDefinition.getTitle()).append("[")
								.append(queryDefinition.getId()).append("],");
					}
				}
				if (sb01.toString().endsWith(",")) {
					sb01.delete(sb01.length() - 1, sb01.length());
				}
				if (sb02.toString().endsWith(",")) {
					sb02.delete(sb02.length() - 1, sb02.length());
				}
				request.setAttribute("queryIds", sb01.toString());
				request.setAttribute("queryNames", sb02.toString());
			}
		}

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("dts_table.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/bi/dts/table/edit", modelMap);
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		String jx_view = request.getParameter("jx_view");
		RequestUtils.setRequestParameterToAttribute(request);
		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("dts_table.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/bi/dts/table/list", modelMap);
	}

	@RequestMapping("/resultList")
	public ModelAndView resultList(HttpServletRequest request, ModelMap modelMap) {
		String jx_view = request.getParameter("jx_view");
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String tableName = request.getParameter("tableName");
		Connection connection = null;
		List<ColumnDefinition> columns = null;
		try {
			if (StringUtils.isNotEmpty(tableName)) {
				connection = DBConnectionFactory.getConnection();
				String sql = "select * from " + tableName + " where 1=0 ";
				columns = DBUtils.getColumns(connection, sql, params);
				modelMap.put("tableName_enc",
						RequestUtils.encodeString(tableName));
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			JdbcUtils.close(connection);
		}

		if (columns != null) {
			modelMap.put("columns", columns);
		}

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("dts_table.resultList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/bi/dts/table/resultList", modelMap);
	}

	@javax.annotation.Resource
	public void setQueryDefinitionService(
			IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
	}

	@javax.annotation.Resource
	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	@RequestMapping("/tableData")
	public ModelAndView tableData(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		String tableName = request.getParameter("tableName");
		if (StringUtils.isNotEmpty(tableName)) {
			List<ColumnDefinition> columns = DBUtils
					.getColumnDefinitions(tableName);
			request.setAttribute("columns", columns);
			request.setAttribute("x_tableName",
					RequestUtils.encodeString(tableName));
		}

		String jx_view = request.getParameter("jx_view");

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view);
		}

		String x_view = ViewProperties.getString("etl.tableData");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/bi/dts/table/tableData");
	}

}
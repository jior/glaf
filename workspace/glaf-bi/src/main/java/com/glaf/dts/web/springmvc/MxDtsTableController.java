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

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.query.QueryDefinitionQuery;
import com.glaf.core.query.TableDefinitionQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

@Controller("/dts/table")
@RequestMapping("/dts/table")
public class MxDtsTableController {

	protected static final Log logger = LogFactory
			.getLog(MxDtsTableController.class);

	protected IQueryDefinitionService queryDefinitionService;

	protected ITableDefinitionService tableDefinitionService;

	@RequestMapping("/editColumn")
	public ModelAndView editColumn(HttpServletRequest request, ModelMap modelMap) {
		String jx_view = request.getParameter("jx_view");
		RequestUtils.setRequestParameterToAttribute(request);

		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}

		if (StringUtils.isNotEmpty(tableName)) {
			TableDefinition table = tableDefinitionService
					.getTableDefinition(tableName);
			request.setAttribute("table", table);
		}

		String columnId = request.getParameter("columnId");
		String columnId_enc = request.getParameter("columnId_enc");
		if (StringUtils.isNotEmpty(columnId_enc)) {
			columnId = RequestUtils.decodeString(columnId_enc);
		}

		if (StringUtils.isNotEmpty(columnId)) {
			ColumnDefinition column = tableDefinitionService
					.getColumnDefinition(columnId);
			request.setAttribute("column", column);
		}

		if (StringUtils.isNotEmpty(jx_view)) {
			return new ModelAndView(jx_view, modelMap);
		}

		String x_view = ViewProperties.getString("dts_table.editColumn");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/bi/dts/table/editColumn", modelMap);

	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		String jx_view = request.getParameter("jx_view");
		RequestUtils.setRequestParameterToAttribute(request);

		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}

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

			if (table != null
					&& StringUtils.isNotEmpty(table.getAggregationQueryIds())) {
				QueryDefinitionQuery query = new QueryDefinitionQuery();
				List<String> queryIds = StringTools.split(table
						.getAggregationQueryIds());
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
				request.setAttribute("aggregationQueryIds", sb01.toString());
				request.setAttribute("queryNames2", sb02.toString());
			}

			if (table != null) {
				request.setAttribute("table", table);
				request.setAttribute("nodeId", table.getNodeId());
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

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableDefinitionQuery query = new TableDefinitionQuery();
		Tools.populate(query, params);

		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		query.type(com.glaf.dts.util.Constants.DTS_TASK_TYPE);
		Long nodeId = RequestUtils.getLong(request, "nodeId");
		if (nodeId != null && nodeId > 0) {
			query.nodeId(nodeId);
		}

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = tableDefinitionService
				.getTableDefinitionCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<TableDefinition> list = tableDefinitionService
					.getTableDefinitionsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableDefinition tableDefinition : list) {
					JSONObject rowJSON = tableDefinition.toJsonObject();
					rowJSON.put("id", tableDefinition.getTableName());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}
			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		LogUtils.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
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
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		QueryHelper helper = new QueryHelper();
		Connection connection = null;
		List<ColumnDefinition> columns = null;
		try {
			if (StringUtils.isNotEmpty(tableName)) {
				connection = DBConnectionFactory.getConnection();
				String sql = "select * from " + tableName + " where 1=0 ";
				columns = helper.getColumns(connection, sql, params);
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
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
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
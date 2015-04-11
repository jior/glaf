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

package com.glaf.dts.web.rest;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.config.Environment;
import com.glaf.core.db.TransformTable;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.query.TableDefinitionQuery;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.dts.bean.TransformBean;
import com.glaf.dts.transform.MxTransformManager;
import com.glaf.dts.util.Constants;

@Controller("/rs/dts/table")
@Path("/rs/dts/table")
public class MxTableResource {

	protected static final Log logger = LogFactory
			.getLog(MxTableResource.class);

	protected IDatabaseService databaseService;

	protected IQueryDefinitionService queryDefinitionService;

	protected ITableDefinitionService tableDefinitionService;

	protected ITablePageService tablePageService;

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] delete(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = tableName.toLowerCase();
			if (StringUtils.startsWith(tableName, "mx_")
					|| StringUtils.startsWith(tableName, "sys_")
					|| StringUtils.startsWith(tableName, "jbpm_")
					|| StringUtils.startsWith(tableName, "act_")) {
				return ResponseUtils.responseJsonResult(false);
			}
			tableDefinitionService.deleteTable(tableName);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@POST
	@Path("/deleteColumn")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteColumn(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String columnId = request.getParameter("columnId");
		if (StringUtils.isNotEmpty(columnId)) {
			columnId = RequestUtils.decodeString(columnId);
			columnId = columnId.toLowerCase();
			if (StringUtils.equalsIgnoreCase("ID", columnId)
					|| StringUtils.equalsIgnoreCase("AGGREGATIONKEY", columnId)) {
				return ResponseUtils.responseJsonResult(false);
			}
			tableDefinitionService.deleteColumn(columnId);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@POST
	@Path("/deleteTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteTable(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = tableName.toLowerCase();
			if (StringUtils.startsWith(tableName, "mx_")
					|| StringUtils.startsWith(tableName, "sys_")
					|| StringUtils.startsWith(tableName, "jbpm_")
					|| StringUtils.startsWith(tableName, "act_")) {
				return ResponseUtils.responseJsonResult(false);
			}
			tableDefinitionService.deleteTable(tableName);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@GET
	@POST
	@Path("/headers")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] headers(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String tableName = ParamUtils.getString(params, "tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		QueryHelper helper = new QueryHelper();
		Connection connection = null;
		List<ColumnDefinition> columns = null;
		try {
			connection = DBConnectionFactory.getConnection();
			String sql = "select * from " + tableName + " where 1=0 ";
			columns = helper.getColumns(connection, sql, params);
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			JdbcUtils.close(connection);
		}

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
		if (columns != null && !columns.isEmpty()) {
			ObjectNode rowJSON = new ObjectMapper().createObjectNode();
			for (ColumnDefinition column : columns) {
				if (column.getColumnName() != null) {
					rowJSON.put("columnName", column.getColumnName());
				}
				if (column.getTitle() != null) {
					rowJSON.put("title", column.getTitle());
				}
				if (column.getName() != null) {
					rowJSON.put("name", column.getName());
				}
				if (column.getJavaType() != null) {
					rowJSON.put("javaType", column.getJavaType());
				}
			}
			rowsJSON.add(rowJSON);
		}

		responseJSON.set("rows", rowsJSON);

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableDefinitionQuery query = new TableDefinitionQuery();
		Tools.populate(query, params);
		query.setType(Constants.DTS_TASK_TYPE);
		List<TableDefinition> tables = tableDefinitionService.list(query);
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		ArrayNode tablesJSON = new ObjectMapper().createArrayNode();
		responseJSON.set("tables", tablesJSON);

		for (TableDefinition table : tables) {
			ObjectNode tableJSON = new ObjectMapper().createObjectNode();
			tableJSON.put("tableName", table.getTableName());
			tableJSON.put("tableName_enc",
					RequestUtils.encodeString(table.getTableName()));
			tableJSON.put("title", table.getTitle());
			if (table.getDescription() != null) {
				tableJSON.put("description", table.getDescription());
			}
			tableJSON.put("locked", table.getLocked());
			tableJSON.put("revision", table.getRevision());
			if (table.getCreateBy() != null) {
				tableJSON.put("createBy", table.getCreateBy());
			}
			tableJSON.put("createTime",
					DateUtils.getDateTime(table.getCreateTime()));
			tablesJSON.add(tableJSON);

			table = tableDefinitionService.getTableDefinition(table
					.getTableName());

			ArrayNode columnsJSON = new ObjectMapper().createArrayNode();

			for (ColumnDefinition column : table.getColumns()) {
				ObjectNode columnJSON = new ObjectMapper().createObjectNode();
				columnJSON.put("columnName", column.getColumnName());
				if (column.getTitle() != null) {
					columnJSON.put("title", column.getTitle());
				}
				if (column.getValueExpression() != null) {
					columnJSON.put("valueExpression",
							column.getValueExpression());
				}
				if (column.getFormula() != null) {
					columnJSON.put("formula", column.getFormula());
				}
				if (column.getJavaType() != null) {
					columnJSON.put("javaType", column.getJavaType());
				}
				if (column.getTranslator() != null) {
					columnJSON.put("translator", column.getTranslator());
				}
				if (column.getName() != null) {
					columnJSON.put("name", column.getName());
				}
				if (column.getRegex() != null) {
					columnJSON.put("regex", column.getRegex());
				}
				columnJSON.put("length", column.getLength());
				columnJSON.put("ordinal", column.getOrdinal());
				columnJSON.put("precision", column.getPrecision());
				columnJSON.put("scale", column.getScale());
				columnsJSON.add(columnJSON);
			}

			tableJSON.set("columns", columnsJSON);
		}

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@POST
	@Path("/rebuild")
	public void rebuild(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String tableName = ParamUtils.getString(params, "tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		String actionType = ParamUtils.getString(params, "actionType");
		if (tableName != null) {
			TableDefinition tableDefinition = tableDefinitionService
					.getTableDefinition(tableName);
			MxTransformManager manager = new MxTransformManager();
			if ("alterTable".equals(actionType)) {
				logger.debug("alterTable...");
				TransformTable tbl = new TransformTable();
				tbl.createOrAlterTable(tableDefinition);
			} else if ("transformAll".equals(actionType)) {
				TransformTable tbl = new TransformTable();
				tbl.createOrAlterTable(tableDefinition);
				manager.transformTable(tableName);
			}
		}
	}

	@POST
	@Path("/saveColumn")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveColumn(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}

		if (StringUtils.isNotEmpty(tableName)) {
			List<String> columnNames = new ArrayList<String>();
			TableDefinition tableDefinition = tableDefinitionService
					.getTableDefinition(tableName);
			for (ColumnDefinition column : tableDefinition.getColumns()) {
				String col = column.getColumnName();
				columnNames.add(col.toUpperCase());
			}
			ColumnDefinition columnDefinition = new ColumnDefinition();
			Tools.populate(columnDefinition, params);
			tableDefinitionService.saveColumn(tableName, columnDefinition);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@POST
	@Path("/saveTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveTable(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		String actionType = request.getParameter("actionType");
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		Tools.populate(tableDefinition, params);
		tableDefinition.setTitle(request.getParameter("title"));
		tableDefinition.setDescription(request.getParameter("description"));
		tableDefinition.setPrimaryKey(request.getParameter("primaryKey"));
		for (ColumnDefinition column : tableDefinition.getColumns()) {
			String columnName = column.getColumnName();
			String param = columnName + "_length";
			column.setLength(RequestUtils.getInt(request, param));
			param = columnName + "_name";
			column.setName(RequestUtils.getParameter(request, param));
			param = columnName + "_title";
			column.setTitle(RequestUtils.getParameter(request, param));
		}

		logger.debug("save table...");
		tableDefinition.setType(Constants.DTS_TASK_TYPE);
		tableDefinitionService.save(tableDefinition);

		MxTransformManager manager = new MxTransformManager();

		if ("alterTable".equals(actionType)) {
			logger.debug("alterTable...");
			TransformTable tbl = new TransformTable();
			tbl.createOrAlterTable(tableDefinition);
		} else if ("transformAll".equals(actionType)) {
			TransformTable tbl = new TransformTable();
			tbl.createOrAlterTable(tableDefinition);
			manager.transformTable(tableName);
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
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

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

	@GET
	@POST
	@Path("/tablePage")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] tablePage(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);

		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		if (!DBUtils.isAllowedTable(tableName)) {
			try {
				return responseJSON.toString().getBytes("UTF-8");
			} catch (IOException e) {
				return responseJSON.toString().getBytes();
			}
		}

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}

		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;
		if ("easyui".equals(gridType)) {
			int pageNo = ParamUtils.getInt(params, "page");
			limit = ParamUtils.getInt(params, "rows");
			start = (pageNo - 1) * limit;
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "order");
		} else if ("extjs".equals(gridType)) {
			start = ParamUtils.getInt(params, "start");
			limit = ParamUtils.getInt(params, "limit");
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "dir");
		} else if ("yui".equals(gridType)) {
			start = ParamUtils.getInt(params, "startIndex");
			limit = ParamUtils.getInt(params, "results");
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "dir");
		}

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0 || limit > 10000) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		TablePageQuery query = new TablePageQuery();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.tableName(tableName);
		if (orderName != null) {
			if (StringUtils.equals(order, "asc")) {
				query.orderAsc(orderName);
			} else {
				query.orderDesc(orderName);
			}
		}

		int total = -1;
		List<Map<String, Object>> rows = null;

		try {
			total = tablePageService.getTableCount(query);
			if (total > 0) {
				rows = tablePageService.getTableData(query);
			}
		} catch (Exception ex) {
			logger.error(ex);
		}

		ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
		if (rows != null && !rows.isEmpty()) {
			responseJSON.put("total", total);
			for (Map<String, Object> dataMap : rows) {
				ObjectNode rowJSON = new ObjectMapper().createObjectNode();
				if (dataMap != null && dataMap.size() > 0) {
					Set<Entry<String, Object>> entrySet = dataMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String name = entry.getKey();
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof Date) {
								Date date = (Date) value;
								rowJSON.put(name, DateUtils.getDateTime(date));
							} else {
								rowJSON.put(name, value.toString());
							}
						} else {
							rowJSON.put(name, "");
						}
					}
				}
				rowsJSON.add(rowJSON);
			}
		}

		if ("yui".equals(gridType)) {
			responseJSON.set("records", rowsJSON);
		} else {
			responseJSON.set("rows", rowsJSON);
		}

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@POST
	@Path("/transformAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] transformAll(@Context HttpServletRequest request) {
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		if (StringUtils.isNotEmpty(tableName)) {
			MxTransformManager manager = new MxTransformManager();
			try {
				manager.transformTable(tableName);
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils.responseJsonResult(false);
			}
		} else {
			TableDefinitionQuery query = new TableDefinitionQuery();
			query.type("DTS");
			List<TableDefinition> tables = tableDefinitionService.list(query);
			if (tables != null && !tables.isEmpty()) {
				Collections.sort(tables);
				MxTransformManager manager = new MxTransformManager();
				for (TableDefinition tableDefinition : tables) {
					try {
						manager.transformTable(tableDefinition.getTableName());
						Thread.sleep(200);
					} catch (Exception ex) {
						ex.printStackTrace();
						return ResponseUtils.responseJsonResult(false);
					}
				}
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/transformAllQueryToTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] transformAllQueryToTable(@Context HttpServletRequest request) {
		TransformBean bean = new TransformBean();
		boolean result = bean.transformAllQueryToTable();
		return ResponseUtils.responseJsonResult(result);
	}

	@POST
	@Path("/transformQueryToTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] transformQueryToTable(@Context HttpServletRequest request) {
		String queryId = request.getParameter("queryId");
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");

		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		if (StringUtils.isNotEmpty(tableName)
				&& StringUtils.isNotEmpty(queryId)) {
			tableName = tableName.toLowerCase();
			if (StringUtils.startsWith(tableName, "mx_")
					|| StringUtils.startsWith(tableName, "sys_")
					|| StringUtils.startsWith(tableName, "act_")
					|| StringUtils.startsWith(tableName, "jbpm_")) {
				return ResponseUtils.responseJsonResult(false, "目标表不正确！");
			}
			MxTransformManager manager = new MxTransformManager();
			TableDefinition tableDefinition = null;
			try {
				QueryDefinition queryDefinition = null;

				if (StringUtils.isNotEmpty(queryId)) {
					queryDefinition = queryDefinitionService
							.getQueryDefinition(queryId);
				}

				if (queryDefinition == null) {
					queryDefinition = new QueryDefinition();
				}

				Map<String, Object> params = RequestUtils
						.getParameterMap(request);
				Tools.populate(queryDefinition, params);
				queryDefinition.setTargetTableName(tableName);

				tableDefinition = manager.toTableDefinition(queryDefinition);
				tableDefinition.setTableName(tableName);
				tableDefinition.setType("DTS");
				tableDefinition.setNodeId(queryDefinition.getNodeId());

				if (!StringUtils.equalsIgnoreCase(
						queryDefinition.getRotatingFlag(), "R2C")) {
					TransformTable tbl = new TransformTable();
					tbl.createOrAlterTable(tableDefinition);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils
						.responseJsonResult(false, "查询失败，SQL语句不正确！");
			}

			Long databaseId = RequestUtils.getLong(request, "databaseId");
			TransformTable transformTable = new TransformTable();
			try {
				Database db = databaseService.getDatabaseById(databaseId);
				if (db != null) {
					transformTable.transformQueryToTable(tableName, queryId,
							db.getName());
				} else {
					transformTable.transformQueryToTable(tableName, queryId,
							Environment.DEFAULT_SYSTEM_NAME);
				}
				return ResponseUtils.responseJsonResult(true);
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils.responseJsonResult(false);
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@GET
	@POST
	@Path("/transformTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] transformTable(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		logger.debug("->tableName:" + tableName);
		if (StringUtils.isNotEmpty(tableName)) {
			MxTransformManager manager = new MxTransformManager();
			try {
				manager.transformTable(tableName);
				return ResponseUtils.responseJsonResult(true);
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils.responseJsonResult(false);
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@POST
	@Path("/transformToTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] transformToTable(@Context HttpServletRequest request) {
		TransformBean bean = new TransformBean();
		String queryId = request.getParameter("queryId");
		logger.debug("queryId:" + queryId);
		boolean result = bean.transformQueryToTable(queryId);
		return ResponseUtils.responseJsonResult(result);
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableDefinitionQuery query = new TableDefinitionQuery();
		Tools.populate(query, params);
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		TableDefinition tableDefinition = tableDefinitionService
				.getTableDefinition(tableName);
		ObjectNode responseJSON = tableDefinition.toObjectNode();
		ArrayNode columnsJSON = new ObjectMapper().createArrayNode();
		responseJSON.set("columns", columnsJSON);

		for (ColumnDefinition column : tableDefinition.getColumns()) {
			ObjectNode columnJSON = column.toObjectNode();
			columnsJSON.add(columnJSON);
		}

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

}
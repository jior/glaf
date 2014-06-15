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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.query.QueryDefinitionQuery;
import com.glaf.core.query.TableDefinitionQuery;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.dts.transform.MxTransformManager;
import com.glaf.dts.util.Constants;

@Controller("/rs/dts/query")
@Path("/rs/dts/query")
public class MxQueryResource {
	private static Log logger = LogFactory.getLog(MxQueryResource.class);

	protected ITableDefinitionService tableDefinitionService;

	protected IQueryDefinitionService queryDefinitionService;

	protected ITreeModelService treeModelService;

	@POST
	@Path("/delete")
	public void delete(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String queryIds = request.getParameter("queryIds");
		if (StringUtils.isNotEmpty(queryIds)) {
			List<String> ids = StringTools.split(queryIds);
			if (ids != null && !ids.isEmpty()) {
				for (String queryId : ids) {
					queryDefinitionService.deleteById(queryId);
				}
			}
		}
	}

	@POST
	@Path("/delete/{queryId}")
	public void delete(@PathParam("queryId") String queryId,
			@Context UriInfo uriInfo) {
		if (queryDefinitionService.hasChildren(queryId)) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		queryDefinitionService.deleteById(queryId);
	}

	@GET
	@POST
	@Path("/treeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] treeJson(@Context HttpServletRequest request)
			throws IOException {
		JSONArray array = new JSONArray();
		Long nodeId = RequestUtils.getLong(request, "nodeId");
		String nodeCode = request.getParameter("nodeCode");
		String selected = request.getParameter("selected");

		logger.debug(RequestUtils.getParameterMap(request));
		List<TreeModel> treeModels = new java.util.ArrayList<TreeModel>();
		List<String> chooseList = new java.util.ArrayList<String>();
		if (StringUtils.isNotEmpty(selected)) {
			chooseList = StringTools.split(selected);
		}

		TreeModel treeNode = null;

		if (nodeId != null && nodeId > 0) {
			treeNode = treeModelService.getTreeModel(nodeId);
		} else if (StringUtils.isNotEmpty(nodeCode)) {
			treeNode = treeModelService.getTreeModelByCode(nodeCode);
		}
		if (treeNode != null) {
			QueryDefinitionQuery query = new QueryDefinitionQuery();
			List<TreeModel> subTrees = treeModelService
					.getSubTreeModels(treeNode.getId());
			if (subTrees != null && !subTrees.isEmpty()) {
				for (TreeModel tree : subTrees) {
					tree.getDataMap().put("nocheck", "true");
					tree.getDataMap().put("iconSkin", "tree_folder");
					tree.getDataMap().put("isParent", "true");
					tree.setIconCls("folder");
					tree.setLevel(0);
					treeModels.add(tree);
					query.nodeId(tree.getId());
					List<QueryDefinition> queries = queryDefinitionService
							.list(query);
					for (QueryDefinition q : queries) {
						if (StringUtils.isNumeric(q.getId())) {
							TreeModel t = new BaseTree();
							t.setId(Long.parseLong(q.getId()));
							t.setParentId(tree.getId());
							t.setName(q.getTitle());
							t.setCode(q.getId());
							t.setTreeId(q.getId());
							t.setIconCls("leaf");
							t.getDataMap().put("iconSkin", "tree_leaf");
							if (chooseList.contains(q.getId())) {
								t.setChecked(true);
							}
							treeModels.add(t);
						}
					}
				}
			}
			TreeHelper treeHelper = new TreeHelper();
			array = treeHelper.getTreeJSONArray(treeModels);
		}

		return array.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		QueryDefinitionQuery query = new QueryDefinitionQuery();
		Tools.populate(query, params);
		List<QueryDefinition> queries = queryDefinitionService.list(query);
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		ArrayNode arrayJSON = new ObjectMapper().createArrayNode();

		for (QueryDefinition q : queries) {
			ObjectNode json = q.toObjectNode();
			arrayJSON.add(json);
		}

		responseJSON.set("data", arrayJSON);
		responseJSON.set("rows", arrayJSON);
		responseJSON.put("total", queries.size());
		responseJSON.put("totalCount", queries.size());
		responseJSON.put("totalRecords", queries.size());

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@POST
	@Path("/saveQuery")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveQuery(@Context HttpServletRequest request) {
		String queryId = request.getParameter("queryId");
		QueryDefinition query = null;

		if (StringUtils.isNotEmpty(queryId)) {
			query = queryDefinitionService.getQueryDefinition(queryId);
		}

		if (query == null) {
			query = new QueryDefinition();
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Tools.populate(query, params);

		logger.debug("sql:" + query.getSql());

		logger.debug("targetTableName:" + query.getTargetTableName());

		logger.debug("parentId:" + query.getParentId());

		MxTransformManager manager = new MxTransformManager();
		if (StringUtils.isNotEmpty(query.getSql())) {

			if (!DBUtils.isLegalQuerySql(query.getSql())) {
				return ResponseUtils.responseJsonResult(false,
						"SQL查询非法，包含不合法指令！");
			}
			if (StringUtils.containsIgnoreCase(query.getSql(), "UserInfo")
					|| StringUtils
							.containsIgnoreCase(query.getSql(), "MX_USER")) {
				return ResponseUtils.responseJsonResult(false,
						"SQL查询非法，不允许访问用户信息表！");
			}

			if (StringUtils.isNotEmpty(query.getTargetTableName())) {
				String tableName = query.getTargetTableName();
				tableName = tableName.toLowerCase();
				if (tableName.length() > 26) {
					return ResponseUtils.responseJsonResult(false,
							"目标表长度不能超过26个字符！");
				}
				if (StringUtils.startsWith(tableName, "mx_")
						|| StringUtils.startsWith(tableName, "sys_")
						|| StringUtils.startsWith(tableName, "jbpm_")
						|| StringUtils.startsWith(tableName, "act_")) {
					return ResponseUtils.responseJsonResult(false, "目标表不正确！");
				}
			}

			TableDefinition newTable = null;
			try {
				newTable = manager.toTableDefinition(query);
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils
						.responseJsonResult(false, "查询失败，SQL语句不正确！");
			}

			if (StringUtils.isNotEmpty(query.getTargetTableName())) {
				newTable.setTableName(query.getTargetTableName());
				TableDefinition table = tableDefinitionService
						.getTableDefinition(query.getTargetTableName());
				if (table == null) {
					table = newTable;
				} else {
					if (newTable != null && newTable.getColumns() != null) {
						for (ColumnDefinition column : newTable.getColumns()) {
							if (!table.getColumns().contains(column)) {
								table.addColumn(column);
							}
						}
					}
				}
				tableDefinitionService.save(table);
			}

			if (newTable != null && newTable.getColumns() != null) {

			}
			query.setType(Constants.DTS_TASK_TYPE);
			queryDefinitionService.save(query);
		}

		return ResponseUtils.responseJsonResult(true);
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
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

	@GET
	@POST
	@Path("/view/{queryId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("queryId") String queryId,
			@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableDefinitionQuery query = new TableDefinitionQuery();
		Tools.populate(query, params);
		QueryDefinition q = queryDefinitionService.getQueryDefinition(queryId);
		ObjectNode responseJSON = q.toObjectNode();
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

}
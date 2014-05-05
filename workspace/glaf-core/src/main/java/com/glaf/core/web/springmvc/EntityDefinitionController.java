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

package com.glaf.core.web.springmvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.EntityDefinition;
import com.glaf.core.identity.User;
import com.glaf.core.query.EntityDefinitionQuery;
import com.glaf.core.res.MessageUtils;
import com.glaf.core.res.ViewMessage;
import com.glaf.core.res.ViewMessages;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.EntityDefinitionService;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.core.xml.XmlMappingReader;

@Controller("/sys/entity")
@RequestMapping("/sys/entity")
public class EntityDefinitionController {
	protected static final Log logger = LogFactory
			.getLog(EntityDefinitionController.class);

	protected EntityDefinitionService entityDefinitionService;

	public EntityDefinitionController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowId = ParamUtils.getString(params, "rowId");
		String rowIds = request.getParameter("rowIds");
		if (StringUtils.isNotEmpty(rowIds)) {
			StringTokenizer token = new StringTokenizer(rowIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					EntityDefinition entityDefinition = entityDefinitionService
							.getEntityDefinition(x);
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (entityDefinition != null
							&& (StringUtils.equals(
									entityDefinition.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// entityDefinition.setDeleteFlag(1);
						entityDefinitionService.save(entityDefinition);
					}
				}
			}
		} else if (StringUtils.isNotEmpty(rowId)) {
			EntityDefinition entityDefinition = entityDefinitionService
					.getEntityDefinition(rowId);
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (entityDefinition != null
					&& (StringUtils.equals(entityDefinition.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// entityDefinition.setDeleteFlag(1);
				entityDefinitionService.save(entityDefinition);
			}
		}
	}

	@RequestMapping("/deploy")
	public ModelAndView deploy(HttpServletRequest request,
			@RequestParam("file") MultipartFile mFile) throws IOException {
		boolean ret = false;
		String type = request.getParameter("type");
		Long nodeId = RequestUtils.getLong(request, "nodeId");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (type != null && mFile != null) {
			if (mFile.getOriginalFilename().endsWith(".mapping.xml")) {
				XmlMappingReader reader = new XmlMappingReader();
				byte[] bytes = mFile.getBytes();
				TableModel tableModel = reader.read(mFile.getInputStream());
				EntityDefinition entity = new EntityDefinition();
				entity.setAggregationKeys(tableModel.getAggregationKey());
				entity.setCreateBy(loginContext.getActorId());
				entity.setUpdateBy(loginContext.getActorId());
				entity.setFileContent(new String(bytes));
				entity.setTablename(tableModel.getTableName());
				entity.setName(tableModel.getEntityName());
				entity.setParseType(tableModel.getParseType());
				entity.setStartRow(tableModel.getStartRow());
				entity.setStopWord(tableModel.getStopWord());
				entity.setTitle(tableModel.getTitle());
				entity.setType(type);
				entity.setNodeId(nodeId);
				entity.setId(entity.getTablename());
				if (mFile.getOriginalFilename() != null) {
					entity.setFilename(mFile.getOriginalFilename());
				}
				if (entity.getType() != null) {
					entity.setId(entity.getTablename() + "_" + entity.getType());
				}
				entity.setData(bytes);
				entityDefinitionService.save(entity);
				ret = true;
			}
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"entity.save_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage(
					"entity.save_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("show_msg2");
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {
		// RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		EntityDefinition entityDefinition = entityDefinitionService
				.getEntityDefinition(request.getParameter("rowId"));
		JSONObject rowJSON = entityDefinition.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		EntityDefinition entityDefinition = entityDefinitionService
				.getEntityDefinition(request.getParameter("rowId"));
		if (entityDefinition != null) {
			request.setAttribute("entityDefinition", entityDefinition);
			JSONObject rowJSON = entityDefinition.toJsonObject();
			request.setAttribute("x_json", rowJSON.toJSONString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (entityDefinition != null) {

			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("entityDefinition.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/entity/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EntityDefinitionQuery query = new EntityDefinitionQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
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
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = entityDefinitionService
				.getEntityDefinitionCountByQueryCriteria(query);
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

			List<EntityDefinition> list = entityDefinitionService
					.getEntityDefinitionsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (EntityDefinition entityDefinition : list) {
					JSONObject rowJSON = entityDefinition.toJsonObject();
					rowJSON.put("id", entityDefinition.getId());
					rowJSON.put("entityDefinitionId", entityDefinition.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}
			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils
					.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/entity/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("entityDefinition.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/entity/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		EntityDefinition entityDefinition = new EntityDefinition();
		// Tools.populate(entityDefinition, params);

		entityDefinition.setName(request.getParameter("name"));
		entityDefinition.setType(request.getParameter("type"));
		entityDefinition.setTitle(request.getParameter("title"));
		entityDefinition.setTablename(request.getParameter("tablename"));
		entityDefinition.setParseType(request.getParameter("parseType"));
		entityDefinition.setPrimaryKey(request.getParameter("primaryKey"));
		entityDefinition.setFilePrefix(request.getParameter("filePrefix"));
		entityDefinition.setStopWord(request.getParameter("stopWord"));
		entityDefinition.setJavaType(request.getParameter("javaType"));
		entityDefinition.setAggregationKeys(request
				.getParameter("aggregationKeys"));
		entityDefinition.setStartRow(RequestUtils.getInt(request, "startRow"));
		entityDefinition.setInsertOnly(request.getParameter("insertOnly"));
		entityDefinition.setFileContent(request.getParameter("fileContent"));
		entityDefinition.setCreateDate(RequestUtils.getDate(request,
				"createDate"));
		entityDefinition.setCreateBy(request.getParameter("createBy"));
		entityDefinition.setUpdateBy(request.getParameter("updateBy"));
		entityDefinition.setUpdateDate(RequestUtils.getDate(request,
				"updateDate"));

		entityDefinition.setCreateBy(actorId);

		entityDefinitionService.save(entityDefinition);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveEntityDefinition")
	public byte[] saveEntityDefinition(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EntityDefinition entityDefinition = new EntityDefinition();
		try {
			Tools.populate(entityDefinition, params);
			entityDefinition.setName(request.getParameter("name"));
			entityDefinition.setType(request.getParameter("type"));
			entityDefinition.setTitle(request.getParameter("title"));
			entityDefinition.setTablename(request.getParameter("tablename"));
			entityDefinition.setParseType(request.getParameter("parseType"));
			entityDefinition.setPrimaryKey(request.getParameter("primaryKey"));
			entityDefinition.setFilePrefix(request.getParameter("filePrefix"));
			entityDefinition.setStopWord(request.getParameter("stopWord"));
			entityDefinition.setJavaType(request.getParameter("javaType"));
			entityDefinition.setAggregationKeys(request
					.getParameter("aggregationKeys"));
			entityDefinition.setStartRow(RequestUtils.getInt(request,
					"startRow"));
			entityDefinition.setInsertOnly(request.getParameter("insertOnly"));
			entityDefinition
					.setFileContent(request.getParameter("fileContent"));
			entityDefinition.setCreateDate(RequestUtils.getDate(request,
					"createDate"));
			entityDefinition.setCreateBy(request.getParameter("createBy"));
			entityDefinition.setUpdateBy(request.getParameter("updateBy"));
			entityDefinition.setUpdateDate(RequestUtils.getDate(request,
					"updateDate"));
			entityDefinition.setCreateBy(actorId);
			this.entityDefinitionService.save(entityDefinition);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setEntityDefinitionService(
			EntityDefinitionService entityDefinitionService) {
		this.entityDefinitionService = entityDefinitionService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		EntityDefinition entityDefinition = entityDefinitionService
				.getEntityDefinition(request.getParameter("rowId"));

		entityDefinition.setName(request.getParameter("name"));
		entityDefinition.setType(request.getParameter("type"));
		entityDefinition.setTitle(request.getParameter("title"));
		entityDefinition.setTablename(request.getParameter("tablename"));
		entityDefinition.setParseType(request.getParameter("parseType"));
		entityDefinition.setPrimaryKey(request.getParameter("primaryKey"));
		entityDefinition.setFilePrefix(request.getParameter("filePrefix"));
		entityDefinition.setStopWord(request.getParameter("stopWord"));
		entityDefinition.setJavaType(request.getParameter("javaType"));
		entityDefinition.setAggregationKeys(request
				.getParameter("aggregationKeys"));
		entityDefinition.setStartRow(RequestUtils.getInt(request, "startRow"));
		entityDefinition.setInsertOnly(request.getParameter("insertOnly"));
		entityDefinition.setFileContent(request.getParameter("fileContent"));
		entityDefinition.setCreateDate(RequestUtils.getDate(request,
				"createDate"));
		entityDefinition.setCreateBy(request.getParameter("createBy"));
		entityDefinition.setUpdateBy(request.getParameter("updateBy"));
		entityDefinition.setUpdateDate(RequestUtils.getDate(request,
				"updateDate"));

		entityDefinitionService.save(entityDefinition);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		EntityDefinition entityDefinition = entityDefinitionService
				.getEntityDefinition(request.getParameter("rowId"));
		request.setAttribute("entityDefinition", entityDefinition);
		JSONObject rowJSON = entityDefinition.toJsonObject();
		request.setAttribute("x_json", rowJSON.toJSONString());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("entityDefinition.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/entity/view");
	}

}

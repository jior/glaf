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
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.*;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.core.domain.*;
import com.glaf.core.query.*;
import com.glaf.core.service.*;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/sys/database")
@RequestMapping("/sys/database")
public class DatabaseController {
	protected static final Log logger = LogFactory
			.getLog(DatabaseController.class);

	protected static AtomicBoolean running = new AtomicBoolean(false);

	protected IDatabaseService databaseService;

	public DatabaseController() {

	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Database repository = databaseService.getDatabaseById(RequestUtils
				.getLong(request, "id"));
		if (repository != null) {
			request.setAttribute("database", repository);
			request.setAttribute("nodeId", repository.getNodeId());
		} else {
			request.setAttribute("nodeId",
					RequestUtils.getLong(request, "nodeId"));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("database.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/database/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/initDB")
	public byte[] initDB(HttpServletRequest request) {
		if (running.get()) {
			return ResponseUtils.responseJsonResult(false,
					"不能执行初始化，已经有任务在执行中，请等待其他任务完成再执行。");
		}

		try {
			running.set(true);
			Database repository = null;
			if (StringUtils.isNotEmpty(request.getParameter("id"))) {
				repository = databaseService.getDatabaseById(RequestUtils
						.getLong(request, "id"));
				if (repository != null) {

					String name = repository.getName();
					String dbType = repository.getType();
					String host = repository.getHost();

					int port = repository.getPort();
					String databaseName = repository.getDbname();
					String user = repository.getUser();
					String password = SecurityUtils.decode(repository.getKey(),
							repository.getPassword());

					DBConfiguration.addDataSourceProperties(name, dbType, host,
							port, databaseName, user, password);
					if (DBConnectionFactory.checkConnection(name)) {

						repository.setVerify("Y");
						repository.setInitFlag("Y");
						databaseService.update(repository);

						return ResponseUtils.responseJsonResult(true,
								"数据库已经成功初始化。");
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			running.set(false);
		}
		return ResponseUtils.responseJsonResult(false, "服务器配置错误。");
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DatabaseQuery query = new DatabaseQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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
		int total = databaseService.getDatabaseCountByQueryCriteria(query);
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

			List<Database> list = databaseService.getDatabasesByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Database repository : list) {
					JSONObject rowJSON = repository.toJsonObject();
					rowJSON.put("id", repository.getId());
					rowJSON.put("rowId", repository.getId());
					rowJSON.put("datamgrId", repository.getId());
					rowJSON.put("startIndex", ++start);
					rowJSON.remove("key");
					rowJSON.remove("user");
					rowJSON.remove("password");
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

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute(
					"fromUrl",
					RequestUtils.encodeURL(requestURI + "?"
							+ request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/database/list", modelMap);
	}

	@RequestMapping("/permission")
	public ModelAndView permission(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		logger.debug("->params:" + RequestUtils.getParameterMap(request));
		String nameLike = request.getParameter("nameLike");

		String op_view = request.getParameter("op_view");
		if (StringUtils.isEmpty(op_view)) {
			op_view = "user";
		}

		request.setAttribute("op_view", op_view);

		DatabaseQuery databaseQuery = new DatabaseQuery();
		databaseQuery.setActive("1");
		if (StringUtils.isNotEmpty(nameLike)
				&& StringUtils.equals(op_view, "database")) {
			databaseQuery.setTitleLike(nameLike);
		}
		List<Database> databaseList = databaseService.list(databaseQuery);
		request.setAttribute("databaseList", databaseList);

		List<DatabaseAccess> accesses = databaseService
				.getAllDatabaseAccesses();

		UserQuery query = new UserQuery();

		if (StringUtils.isNotEmpty(nameLike)
				&& StringUtils.equals(op_view, "user")) {
			query.nameLike(nameLike);
		}
		List<User> users = null;
		if (StringUtils.isNotEmpty(nameLike)
				&& StringUtils.equals(op_view, "user")) {
			users = IdentityFactory.searchUsers(nameLike);
		} else {
			users = IdentityFactory.getUsers();
		}

		if (users != null && !users.isEmpty()) {
			for (User user : users) {
				if (accesses != null && !accesses.isEmpty()) {
					for (DatabaseAccess access : accesses) {
						if (StringUtils.equalsIgnoreCase(user.getActorId(),
								access.getActorId())) {
							user.addRowKey(String.valueOf(access
									.getDatabaseId()));
						}
					}
				}
			}
			request.setAttribute("users", users);
		}

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

		String x_view = ViewProperties.getString("database.permission");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/database/permission", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("database.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/database/query", modelMap);
	}

	@ResponseBody
	@RequestMapping(value = "/reloadDB", method = RequestMethod.POST)
	public byte[] reloadDB(HttpServletRequest request) {
		return ResponseUtils.responseJsonResult(true);
	}

	@ResponseBody
	@RequestMapping("/saveAccessor")
	public byte[] saveAccessor(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		long databaseId = RequestUtils.getLong(request, "databaseId");
		String actorId = request.getParameter("actorId");
		String operation = request.getParameter("operation");
		if (databaseId > 0 && actorId != null) {
			/**
			 * 保证添加的部门是分级管理员管辖的部门
			 */
			if (loginContext.isSystemAdministrator()) {
				if (StringUtils.equals(operation, "revoke")) {
					databaseService.deleteAccessor(databaseId, actorId);
				} else {
					databaseService.createAccessor(databaseId, actorId);
				}
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveDB")
	public byte[] saveDB(HttpServletRequest request) {
		Database database = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			database = databaseService.getDatabaseById(RequestUtils.getLong(
					request, "id"));
		}
		if (database == null) {
			database = new Database();
		}

		String user = request.getParameter("user");
		String password = request.getParameter("password");
		database.setUser(user);
		database.setPassword(password);
		database.setTitle(request.getParameter("title"));
		database.setCode(request.getParameter("code"));
		database.setNodeId(RequestUtils.getLong(request, "nodeId"));
		database.setHost(request.getParameter("host"));
		database.setPort(RequestUtils.getInt(request, "port"));
		database.setName(request.getParameter("name"));
		database.setType(request.getParameter("type"));
		database.setLevel(RequestUtils.getInt(request, "level"));
		database.setPriority(RequestUtils.getInt(request, "priority"));
		database.setOperation(RequestUtils.getInt(request, "operation"));
		database.setDbname(request.getParameter("dbname"));
		database.setProviderClass(request.getParameter("providerClass"));
		database.setActive(request.getParameter("active"));
		if (StringUtils.isEmpty(database.getType())) {
			database.setType("sqlserver");
		}

		try {
			this.databaseService.save(database);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@ResponseBody
	@RequestMapping("/verify")
	public byte[] verify(HttpServletRequest request) {
		try {
			Database database = null;
			if (StringUtils.isNotEmpty(request.getParameter("id"))) {
				database = databaseService.getDatabaseById(RequestUtils
						.getLong(request, "id"));
			}
			if (database == null) {
				database = new Database();
			}

			String user = request.getParameter("user");
			String password = request.getParameter("password");

			if (!"88888888".equals(password)) {
				String key = SecurityUtils.genKey();
				String pass = SecurityUtils.encode(key, password);
				database.setKey(key);
				database.setPassword(pass);
			}

			database.setUser(user);
			database.setTitle(request.getParameter("title"));
			database.setNodeId(RequestUtils.getLong(request, "nodeId"));
			database.setHost(request.getParameter("host"));
			database.setPort(RequestUtils.getInt(request, "port"));
			database.setType(request.getParameter("type"));
			database.setLevel(RequestUtils.getInt(request, "level"));
			database.setPriority(RequestUtils.getInt(request, "priority"));
			database.setOperation(RequestUtils.getInt(request, "operation"));
			database.setDbname(request.getParameter("dbname"));
			database.setProviderClass(request.getParameter("providerClass"));
			database.setActive(request.getParameter("active"));

			String name = database.getName();
			String dbType = database.getType();
			String host = database.getHost();
			int port = database.getPort();
			String databaseName = database.getDbname();
			if ("88888888".equals(password)) {
				password = SecurityUtils.decode(database.getKey(),
						database.getPassword());
			}
			DBConfiguration.addDataSourceProperties(name, dbType, host, port,
					databaseName, user, password);
			if (DBConnectionFactory.checkConnection(name)) {
				database.setVerify("Y");
				databaseService.update(database);
				return ResponseUtils.responseJsonResult(true, "数据库配置正确。");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false, "服务器配置错误。");
	}

	@ResponseBody
	@RequestMapping("/verify2")
	public byte[] verify2(HttpServletRequest request) {
		try {
			Database database = null;
			if (StringUtils.isNotEmpty(request.getParameter("id"))) {
				database = databaseService.getDatabaseById(RequestUtils
						.getLong(request, "id"));
				if (database != null) {

					String name = database.getName();

					if (DBConnectionFactory.checkConnection(name)) {
						database.setVerify("Y");
						databaseService.update(database);
						return ResponseUtils.responseJsonResult(true,
								"数据库配置正确。");
					}

					String dbType = database.getType();
					String host = database.getHost();

					int port = database.getPort();
					String databaseName = database.getDbname();
					String user = database.getUser();
					String password = SecurityUtils.decode(database.getKey(),
							database.getPassword());
					// logger.debug("->password:" + password);
					DBConfiguration.addDataSourceProperties(name, dbType, host,
							port, databaseName, user, password);
					logger.debug("->systemName:" + name);
					if (DBConnectionFactory.checkConnection(name)) {
						database.setVerify("Y");
						databaseService.update(database);
						return ResponseUtils.responseJsonResult(true,
								"数据库配置正确。");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false, "服务器配置错误。");
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Database database = databaseService.getDatabaseById(RequestUtils
				.getLong(request, "id"));
		request.setAttribute("database", database);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("database.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/database/view");
	}

}

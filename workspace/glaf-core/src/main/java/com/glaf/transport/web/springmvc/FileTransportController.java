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

package com.glaf.transport.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.transport.domain.*;
import com.glaf.transport.provider.TransportProviderFactory;
import com.glaf.transport.query.*;
import com.glaf.transport.service.*;
import com.glaf.transport.util.FileTransportUtils;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/sys/transport")
@RequestMapping("/sys/transport")
public class FileTransportController {
	protected static final Log logger = LogFactory
			.getLog(FileTransportController.class);

	protected FileTransportService fileTransportService;

	public FileTransportController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {

				}
			}
		} else if (id != null) {

		}
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		FileTransport fileTransport = fileTransportService
				.getFileTransport(RequestUtils.getLong(request, "id"));
		if (fileTransport != null) {
			request.setAttribute("transport", fileTransport);
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("transport.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/transport/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FileTransportQuery query = new FileTransportQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
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
		int total = fileTransportService
				.getFileTransportCountByQueryCriteria(query);
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

			List<FileTransport> list = fileTransportService
					.getFileTransportsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FileTransport fileTransport : list) {
					JSONObject rowJSON = fileTransport.toJsonObject();
					rowJSON.put("id", fileTransport.getId());
					rowJSON.put("rowId", fileTransport.getId());
					rowJSON.put("transportId", fileTransport.getId());
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

		return new ModelAndView("/modules/sys/transport/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("transport.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/transport/query", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveTransport")
	public byte[] saveTransport(HttpServletRequest request) {
		FileTransport fileTransport = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			fileTransport = fileTransportService.getFileTransport(RequestUtils
					.getLong(request, "id"));
		}
		if (fileTransport == null) {
			fileTransport = new FileTransport();
		}

		String user = request.getParameter("user");
		String password = request.getParameter("password");
		fileTransport.setUser(user);
		fileTransport.setPassword(password);
		fileTransport.setTitle(request.getParameter("title"));
		fileTransport.setCode(request.getParameter("code"));
		fileTransport.setNodeId(RequestUtils.getLong(request, "nodeId"));
		fileTransport.setHost(request.getParameter("host"));
		fileTransport.setPort(RequestUtils.getInt(request, "port"));
		fileTransport.setPath(request.getParameter("path"));
		fileTransport.setType(request.getParameter("type"));
		fileTransport.setProviderClass(request.getParameter("providerClass"));
		fileTransport.setActive(request.getParameter("active"));

		try {

			this.fileTransportService.save(fileTransport);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setFileTransportService(
			FileTransportService fileTransportService) {
		this.fileTransportService = fileTransportService;
	}

	@ResponseBody
	@RequestMapping("/verify")
	public byte[] verify(HttpServletRequest request) {
		try {
			FileTransport fileTransport = null;
			if (StringUtils.isNotEmpty(request.getParameter("id"))) {
				fileTransport = fileTransportService
						.getFileTransport(RequestUtils.getLong(request, "id"));
			}
			if (fileTransport == null) {
				fileTransport = new FileTransport();
			}

			String user = request.getParameter("user");
			String password = request.getParameter("password");

			if (!"88888888".equals(password)) {
				String key = FileTransportUtils.genKey();
				String pass = SecurityUtils.encode(key, password);
				fileTransport.setKey(key);
				fileTransport.setPassword(pass);
			}

			fileTransport.setUser(user);
			fileTransport.setTitle(request.getParameter("title"));
			fileTransport.setCode(request.getParameter("code"));
			fileTransport.setNodeId(RequestUtils.getLong(request, "nodeId"));
			fileTransport.setHost(request.getParameter("host"));
			fileTransport.setPort(RequestUtils.getInt(request, "port"));
			fileTransport.setPath(request.getParameter("path"));
			fileTransport.setType(request.getParameter("type"));
			fileTransport.setProviderClass(request.getParameter("providerClass"));
			fileTransport.setActive(request.getParameter("active"));
			TransportProviderFactory providerFactory = TransportProviderFactory
					.getInstance();
			String uuid = UUID32.getUUID();
			providerFactory
					.saveFile(fileTransport, "test.txt", uuid.getBytes());
			String str = new String(providerFactory.getBytes(fileTransport,
					"test.txt"));
			logger.debug("str:"+str);
			if (StringUtils.equals(uuid, str)) {
				logger.debug("验证通过！");
				return ResponseUtils.responseJsonResult(true, "服务器配置正确。");
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
		FileTransport fileTransport = fileTransportService
				.getFileTransport(RequestUtils.getLong(request, "id"));
		request.setAttribute("transport", fileTransport);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("transport.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/transport/view");
	}

}

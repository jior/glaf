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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.*;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.core.xml.XmlReader;
import com.glaf.core.domain.*;
import com.glaf.core.domain.util.SysDataTableDomainFactory;
import com.glaf.core.query.*;
import com.glaf.core.service.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/system/datatable")
@RequestMapping("/system/datatable")
public class SysDataTableController {
	protected static final Log logger = LogFactory
			.getLog(SysDataTableController.class);

	protected ISysDataTableService sysDataTableService;

	public SysDataTableController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SysDataTable sysDataTable = sysDataTableService
							.getDataTableById(String.valueOf(x));

					if (sysDataTable != null
							&& (StringUtils.equals(sysDataTable.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						sysDataTable.setDeleteFlag(1);
						sysDataTableService.saveDataTable(sysDataTable);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SysDataTable sysDataTable = sysDataTableService
					.getDataTableById(String.valueOf(id));

			if (sysDataTable != null
					&& (StringUtils.equals(sysDataTable.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				sysDataTable.setDeleteFlag(1);
				sysDataTableService.saveDataTable(sysDataTable);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SysDataTable sysDataTable = sysDataTableService
				.getDataTableById(request.getParameter("id"));
		if (sysDataTable != null) {
			request.setAttribute("sysDataTable", sysDataTable);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysDataTable.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/datatable/edit", modelMap);
	}

	@RequestMapping(value = "/importMapping", method = RequestMethod.POST)
	public ModelAndView importMapping(HttpServletRequest request,
			ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = req.getFileMap();
		XmlReader reader = new XmlReader();
		Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			MultipartFile mFile = entry.getValue();
			if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
				String filename = mFile.getOriginalFilename();
				if (filename.endsWith(".zip")) {
					ZipInputStream zipInputStream = null;
					try {
						zipInputStream = new ZipInputStream(
								mFile.getInputStream());
						Map<String, byte[]> zipMap = ZipUtils
								.getZipBytesMap(zipInputStream);
						if (zipMap != null && !zipMap.isEmpty()) {
							Set<Entry<String, byte[]>> entrySet2 = zipMap
									.entrySet();
							for (Entry<String, byte[]> entry2 : entrySet2) {
								String name = entry2.getKey();
								if (name.endsWith(".mapping.xml")) {
									byte[] bytes = entry2.getValue();
									SysDataTable dataTable = reader
											.getSysDataTable(new ByteArrayInputStream(
													bytes));
									if (sysDataTableService
											.getDataTableByTable(dataTable
													.getTablename()) == null) {
										dataTable.setCreateBy(loginContext
												.getActorId());
										sysDataTableService
												.saveDataTable(dataTable);
									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						IOUtils.closeStream(zipInputStream);
					}
				} else if (filename.endsWith(".mapping.xml")) {
					try {
						SysDataTable dataTable = reader.getSysDataTable(mFile
								.getInputStream());
						if (sysDataTableService.getDataTableByTable(dataTable
								.getTablename()) == null) {
							dataTable.setCreateBy(loginContext.getActorId());
							sysDataTableService.saveDataTable(dataTable);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return this.list(request, modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataTableQuery query = new SysDataTableQuery();
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
		int total = sysDataTableService.getDataTableCountByQueryCriteria(query);
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SysDataTable> list = sysDataTableService
					.getDataTablesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysDataTable sysDataTable : list) {
					JSONObject rowJSON = sysDataTable.toJsonObject();
					rowJSON.put("id", sysDataTable.getId());
					rowJSON.put("rowId", sysDataTable.getId());
					rowJSON.put("datatableId", sysDataTable.getId());
					rowJSON.put("startIndex", ++start);
					if (userMap.get(sysDataTable.getCreateBy()) != null) {
						rowJSON.put("createByName",
								userMap.get(sysDataTable.getCreateBy())
										.getName());
					}
					if (userMap.get(sysDataTable.getUpdateBy()) != null) {
						rowJSON.put("updateByName",
								userMap.get(sysDataTable.getUpdateBy())
										.getName());
					}
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

		return new ModelAndView("/modules/sys/datatable/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sysDataTable.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/datatable/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataTableQuery query = new SysDataTableQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SysDataTableDomainFactory.processDataRequest(dataRequest);

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = dataRequest.getPage();
		limit = dataRequest.getPageSize();

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = sysDataTableService.getDataTableCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			String orderName = null;
			String order = null;

			if (dataRequest.getSort() != null
					&& !dataRequest.getSort().isEmpty()) {
				SortDescriptor sort = dataRequest.getSort().get(0);
				orderName = sort.getField();
				order = sort.getDir();
				logger.debug("orderName:" + orderName);
				logger.debug("order:" + order);
			}

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SysDataTable> list = sysDataTableService
					.getDataTablesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysDataTable sysDataTable : list) {
					JSONObject rowJSON = sysDataTable.toJsonObject();
					rowJSON.put("id", sysDataTable.getId());
					rowJSON.put("datatableId", sysDataTable.getId());
					rowJSON.put("startIndex", ++start);
					if (userMap.get(sysDataTable.getCreateBy()) != null) {
						rowJSON.put("createByName",
								userMap.get(sysDataTable.getCreateBy())
										.getName());
					}
					if (userMap.get(sysDataTable.getUpdateBy()) != null) {
						rowJSON.put("updateByName",
								userMap.get(sysDataTable.getUpdateBy())
										.getName());
					}
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

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SysDataTable sysDataTable = new SysDataTable();
		Tools.populate(sysDataTable, params);

		sysDataTable.setServiceKey(request.getParameter("serviceKey"));
		sysDataTable.setTablename(request.getParameter("tablename"));
		sysDataTable.setTitle(request.getParameter("title"));
		sysDataTable.setType(RequestUtils.getInt(request, "type"));
		sysDataTable.setMaxUser(RequestUtils.getInt(request, "maxUser"));
		sysDataTable.setMaxSys(RequestUtils.getInt(request, "maxSys"));
		sysDataTable.setCreateBy(actorId);
		sysDataTable.setUpdateBy(actorId);
		sysDataTable.setContent(request.getParameter("content"));
		sysDataTable.setIsSubTable(request.getParameter("isSubTable"));

		sysDataTableService.saveDataTable(sysDataTable);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SysDataTable saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		SysDataTable sysDataTable = new SysDataTable();
		try {
			Tools.populate(sysDataTable, model);
			sysDataTable.setServiceKey(ParamUtils
					.getString(model, "serviceKey"));
			sysDataTable.setTablename(ParamUtils.getString(model, "tablename"));
			sysDataTable.setTitle(ParamUtils.getString(model, "title"));
			sysDataTable.setType(ParamUtils.getInt(model, "type"));
			sysDataTable.setMaxUser(ParamUtils.getInt(model, "maxUser"));
			sysDataTable.setMaxSys(ParamUtils.getInt(model, "maxSys"));
			sysDataTable.setCreateBy(actorId);
			sysDataTable.setUpdateBy(actorId);
			sysDataTable.setContent(ParamUtils.getString(model, "content"));
			sysDataTable.setIsSubTable(ParamUtils
					.getString(model, "isSubTable"));

			this.sysDataTableService.saveDataTable(sysDataTable);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return sysDataTable;
	}

	@ResponseBody
	@RequestMapping("/saveSysDataTable")
	public byte[] saveSysDataTable(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataTable sysDataTable = new SysDataTable();
		try {
			Tools.populate(sysDataTable, params);
			sysDataTable.setServiceKey(request.getParameter("serviceKey"));
			sysDataTable.setTablename(request.getParameter("tablename"));
			sysDataTable.setTitle(request.getParameter("title"));
			sysDataTable.setType(RequestUtils.getInt(request, "type"));
			sysDataTable.setMaxUser(RequestUtils.getInt(request, "maxUser"));
			sysDataTable.setMaxSys(RequestUtils.getInt(request, "maxSys"));
			sysDataTable.setCreateBy(actorId);
			sysDataTable.setUpdateBy(actorId);
			sysDataTable.setContent(request.getParameter("content"));
			sysDataTable.setIsSubTable(request.getParameter("isSubTable"));

			this.sysDataTableService.saveDataTable(sysDataTable);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSysDataTableService(ISysDataTableService sysDataTableService) {
		this.sysDataTableService = sysDataTableService;
	}

	@RequestMapping("/showImport")
	public ModelAndView showImport(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysDataTable.showImport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/datatable/showImport", modelMap);
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SysDataTable sysDataTable = sysDataTableService
				.getDataTableById(request.getParameter("id"));

		Tools.populate(sysDataTable, params);

		sysDataTable.setServiceKey(request.getParameter("serviceKey"));
		sysDataTable.setTablename(request.getParameter("tablename"));
		sysDataTable.setTitle(request.getParameter("title"));
		sysDataTable.setType(RequestUtils.getInt(request, "type"));
		sysDataTable.setMaxUser(RequestUtils.getInt(request, "maxUser"));
		sysDataTable.setMaxSys(RequestUtils.getInt(request, "maxSys"));
		sysDataTable.setUpdateBy(user.getActorId());
		sysDataTable.setContent(request.getParameter("content"));
		sysDataTable.setIsSubTable(request.getParameter("isSubTable"));

		sysDataTableService.saveDataTable(sysDataTable);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SysDataTable sysDataTable = sysDataTableService
				.getDataTableById(request.getParameter("id"));
		request.setAttribute("sysDataTable", sysDataTable);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("sysDataTable.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/datatable/view");
	}

}

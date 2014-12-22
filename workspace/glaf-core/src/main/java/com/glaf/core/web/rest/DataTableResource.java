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

package com.glaf.core.web.rest;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.db.DataTableBean;
import com.glaf.core.domain.SysDataTable;
import com.glaf.core.domain.util.SysDataTableDomainFactory;
import com.glaf.core.query.SysDataTableQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.ISysDataTableService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/rs/table/service")
@Path("/rs/table/service")
public class DataTableResource {

	protected static final Log logger = LogFactory
			.getLog(DataTableResource.class);

	protected ISysDataTableService sysDataTableService;

	@POST
	@Path("/data/{datatableId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@PathParam("datatableId") String datatableId,
			@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		String ipAddress = RequestUtils.getIPAddress(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataTableQuery query = new SysDataTableQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		SysDataTableDomainFactory.processDataRequest(dataRequest);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		DataTableBean bean = new DataTableBean();
		bean.setSysDataTableService(sysDataTableService);

		SysDataTable sysDataTable = sysDataTableService
				.getDataTableById(datatableId);
		query.setTablename(sysDataTable.getTablename());

		bean.checkPermission(sysDataTable, loginContext, ipAddress);

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

		JSONObject result = sysDataTableService.getPageTableData(start, limit,
				query);
		logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/response/{datatableId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] response(@PathParam("datatableId") String datatableId,
			@Context HttpServletRequest request) {
		String ipAddress = RequestUtils.getIPAddress(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		SysDataTableQuery query = new SysDataTableQuery();
		Tools.populate(query, params);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		String dataType = request.getParameter("dataType");
		String systemName = request.getParameter("systemName");
		if (dataType == null) {
			dataType = "json";
		}
		if (systemName == null) {
			systemName = com.glaf.core.config.Environment.DEFAULT_SYSTEM_NAME;
		}

		params.put("systemName", systemName);

		DataTableBean bean = new DataTableBean();
		bean.setSysDataTableService(sysDataTableService);

		SysDataTable sysDataTable = sysDataTableService
				.getDataTableById(datatableId);
		query.setTablename(sysDataTable.getTablename());

		bean.checkPermission(sysDataTable, loginContext, ipAddress);

		if (StringUtils.equals(dataType, "json")) {
			return bean.responseJson(systemName, datatableId,
					loginContext.getActorId(), ipAddress, query);
		}
		return null;
	}

	@POST
	@Path("/saveData/{datatableId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveData(@PathParam("datatableId") String datatableId,
			@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		params.put("actorId", loginContext.getActorId());
		params.put("createBy", loginContext.getActorId());
		params.put("updateBy", loginContext.getActorId());
		params.put("loginContext", loginContext);
		params.put("createDate", new Date());
		params.put("updateDate", new Date());
		params.remove("deleteFlag");

		String json = request.getParameter("json");
		SysDataTable sysDataTable = sysDataTableService
				.getDataTableById(datatableId);
		// logger.debug("sysDataTable:"+sysDataTable);
		try {
			if (sysDataTable != null && sysDataTable.getLocked() == 0) {
				DataTableBean bean = new DataTableBean();
				bean.setSysDataTableService(sysDataTableService);
				String ipAddress = RequestUtils.getIPAddress(request);
				bean.checkPermission(sysDataTable, loginContext, ipAddress);
				if (StringUtils.isNotEmpty(json)) {
					JSONObject jsonObject = JSON.parseObject(json);
					sysDataTableService.saveJsonData(sysDataTable.getId(),
							jsonObject);
				} else {
					sysDataTableService.saveData(sysDataTable.getId(), params);
				}
				return ResponseUtils.responseJsonResult(true);
			}
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

}

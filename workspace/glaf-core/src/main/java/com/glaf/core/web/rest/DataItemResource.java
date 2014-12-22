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
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.*;

import com.glaf.core.service.ISysDataItemService;
import com.glaf.core.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/dataitem")
public class DataItemResource {
	protected static final Log logger = LogFactory
			.getLog(DataItemResource.class);

	protected ISysDataItemService sysDataItemService;

	@GET
	@POST
	@Path("/jsonArray")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] jsonArray(@Context HttpServletRequest request)
			throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		long id = RequestUtils.getLong(request, "id");
		JSONArray result = sysDataItemService.getJsonData(id, params);
		logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/jsonp")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] jsonp(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		long id = RequestUtils.getLong(request, "id");
		JSONArray result = sysDataItemService.getJsonData(id, params);
		logger.debug(result.toJSONString());
		return ("callback(" + result.toJSONString() + ")").getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setSysDataItemService(ISysDataItemService sysDataItemService) {
		this.sysDataItemService = sysDataItemService;
	}

}
